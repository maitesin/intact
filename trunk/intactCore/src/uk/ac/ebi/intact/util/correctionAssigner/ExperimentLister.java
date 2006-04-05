/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.util.correctionAssigner;

import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.util.sanityChecker.SanityCheckerHelper;
import uk.ac.ebi.intact.util.sanityChecker.model.ControlledvocabBean;
//import uk.ac.ebi.intact.util.sanityChecker.model.ExperimentBean;
import uk.ac.ebi.intact.util.sanityChecker.model.AnnotationBean;
import uk.ac.ebi.intact.util.sanityChecker.model.Int2ExpBean;
import uk.ac.ebi.intact.model.CvTopic;
import uk.ac.ebi.intact.model.CvDatabase;
import uk.ac.ebi.intact.model.CvXrefQualifier;
//import uk.ac.ebi.intact.model.Experiment;

import java.util.*;
import java.sql.SQLException;

/**
 * TODO comment it.
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 */
public class ExperimentLister {

    IntactHelper helper;

    CvHolder cvHolder;

    /**Collection of experimentBeans :
     being created after september 2005
     having no annotation to-be-reviewed or accepted
     having no annotation reviewer
     In other words, experiment not yet corrected and not yet assigned to any super-curator for correction
     **/
    private Collection notAssignedExperiments = new ArrayList();


    /**Collection of experimentBeans :
     being created after september 2005
     having no annotation to-be-reviewed or accepted
     having an annotation reviewer
     In other words, experiment not yet corrected but already assigned to a super-curator for correction.
     **/
    private Collection assignedExperiments = new ArrayList();

    /**
     * HashMap permiting to map pubmed String to the corresponding Collection of ExperimentBean
     */
    private HashMap pmid2expColl = new HashMap();

    /**
     * HashMap permiting to map pubmed id of experiments(not assigned to any reviewer) to their creator.
     */
    private HashMap notAssignedPmid2creator = new HashMap();

    /**
     * HashMap permiting to map pubmed id of experiments (assigned to a reviewer) to their creator.
     */
    private HashMap assignedPmid2creator = new HashMap();

    /**
     * Constructor of ExperimentLister.
     *
     * @throws SQLException
     * @throws IntactException
     */
    public ExperimentLister() throws Exception, IntactException {
        initialize();
    }

    private IntactHelper getIntactHelper() throws IntactException {
        if (helper == null){
            helper = new IntactHelper();
        }
        return helper;
    }

    private CvHolder getCvHolder() throws Exception {
        if (cvHolder == null){
            cvHolder = new CvHolder();
        }
        return cvHolder;
    }

    private void initialize() throws Exception, SQLException {
        helper = getIntactHelper();
        cvHolder = getCvHolder();

        fillNotAssignedExpCollection();
        removeExpOnHoldAndWithNoInteraction(notAssignedExperiments);
        System.out.println(" number of not assigned Exp " + notAssignedExperiments.size());

        fillAssignedExpCollection();
        removeExpOnHoldAndWithNoInteraction(assignedExperiments);
        System.out.println(" number of  assigned Exp " + assignedExperiments.size());


        fillPmid2CreatorMaps();
        fillPmid2expColl();
        helper.closeStore();

    }

    /**
     * Getter for the HashMap pmid2expColl.
     * @return the HashMap pmid2expColl.
     */
    public HashMap getPmid2expColl() {
        return pmid2expColl;
    }

    /**
     * Getter for Collection notAssignedExperiments.
     * @return the collection notAssignedExperiments.
     */
    public Collection getNotAssignedExperiments() {
        return notAssignedExperiments;
    }

    /**
     * Getter for the Collection assignedExperiments.
     * @return the Collection assignedExperiments
     */
    public Collection getAssignedExperiments() {
        return assignedExperiments;
    }

    /**
     * Getter for the HashMap notAssignedPmid2creator.
     * @return the HashMap notAssignedPmid2creator
     */
    public HashMap getNotAssignedPmid2creator() {
        return notAssignedPmid2creator;
    }

    /**
         * Via the sanityCheckerHelper this method is using the dbUtils library to get the Collection notAssignedExperiments
         * of experimentBeans being to assigned to a reviewer for correction.
         *
         * @throws IntactException
         * @throws SQLException
         */
        private void fillNotAssignedExpCollection() throws Exception, SQLException {

            IntactHelper intactHelper = getIntactHelper();
            CvHolder holder = getCvHolder();

            SanityCheckerHelper sch = new SanityCheckerHelper( intactHelper );

            sch.addMapping(ComparableExperimentBean.class,  "select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId " +
                    "from ia_experiment e, ia_xref x " +
                    "where x.parent_ac = e.ac and " +
                    "x.database_ac = '" + holder.pubmed.getAc() + "' and " +
                    "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and " +
                    "e.ac not in ( " +
                    "select e.ac " +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                    "where e.ac=e2a.experiment_ac and " +
                    "e2a.annotation_ac=a.ac and " +
                    "a.topic_ac in  ('" + holder.accepted.getAc() + "','" + holder.toBeReviewed.getAc()+ "') " +
                    "union " +
                    "select e.ac " +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                    "where e.ac=e2a.experiment_ac and " +
                    "e2a.annotation_ac=a.ac and " +
                    "a.topic_ac in  ('" + holder.reviewer.getAc() + "') " +
                    ") " +
                    "and to_date(e.created,'DD-MON-YYYY HH24:MI:SS') >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? " +
                    "order by created_user");

            notAssignedExperiments = sch.getBeans(ComparableExperimentBean.class, "%");
       }

        /**
         * * Via the sanityCheckerHelper this method is using the dbUtils library to get the Collection assignedExperiments
         * of experimentBeans being already assigned in the past to a reviewer for correction.
         *
         * @throws IntactException
         * @throws SQLException
         */

        private void fillAssignedExpCollection() throws Exception, SQLException {

            CvHolder holder = getCvHolder();
            IntactHelper intactHelper = getIntactHelper();

            SanityCheckerHelper sch = new SanityCheckerHelper( intactHelper );

            sch.addMapping(ComparableExperimentBean.class,"select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId, a.description as reviewer " +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a, ia_xref x " +
                    "where e.ac=e2a.experiment_ac and " +
                    "x.parent_ac = e.ac and " +
                    "x.parent_ac = e.ac and " +
                    "x.database_ac = '" + holder.pubmed.getAc() + "' and " +
                    "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and " +
                    "e2a.annotation_ac=a.ac and " +
                    "a.topic_ac in  ('" + holder.reviewer.getAc() + "') " +
                    "and to_date(e.created,'DD-MON-YYYY HH24:MI:SS') >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? " +
                    "order by created_user");
            assignedExperiments = sch.getBeans(ComparableExperimentBean.class, "%");
        }



    /**
     * From the Collection of not yet asseigned ExperimentBean, build a the hashMap pmid2expColl.
     */
    private void fillPmid2expColl(){
        for (Iterator iterator = notAssignedExperiments.iterator(); iterator.hasNext();) {
            ComparableExperimentBean exp =  (ComparableExperimentBean) iterator.next();
            String pubmed = exp.getPubmedId();
            if ( pmid2expColl.containsKey(pubmed)){
                Collection experiments = (Collection) pmid2expColl.get(pubmed);
                experiments.add(exp);
                pmid2expColl.put(pubmed, experiments);
            }
            else {
                Collection experiments = new ArrayList();
                experiments.add(exp);
                pmid2expColl.put(pubmed, experiments);
            }
        }
    }

        /**
         * From the 2 collections of ExperimentBean (assignedExperiments and notAssignedExperiment) build a map the maps
         *      assignedPmid2creator
         *      notAssignedPmid2creator
         */
        private void fillPmid2CreatorMaps(){

            for (Iterator iterator = assignedExperiments.iterator(); iterator.hasNext();) {
                ComparableExperimentBean exp =  (ComparableExperimentBean) iterator.next();
                assignedPmid2creator.put(exp.getPubmedId(), exp.getCreated_user().toLowerCase());

            }

            for (Iterator iterator = notAssignedExperiments.iterator(); iterator.hasNext();) {
                ComparableExperimentBean exp =  (ComparableExperimentBean) iterator.next();
                if(!assignedPmid2creator.containsKey(exp.getPubmedId())){
                    notAssignedPmid2creator.put(exp.getPubmedId(), exp.getCreated_user().toLowerCase());
                }
            }


        }

    /**
     *
     * @param experiment
     * @return
     * @throws IntactException
     * @throws SQLException
     */
    private boolean isOnHold(ComparableExperimentBean experiment) throws Exception, SQLException {
        boolean onHold = true;

        CvHolder holder = getCvHolder();
        IntactHelper intactHelper = getIntactHelper();
        SanityCheckerHelper sch = new SanityCheckerHelper(intactHelper);
        sch.addMapping(AnnotationBean.class, "select a.ac " +
                "from ia_annotation a, ia_exp2annot e2a " +
                "where e2a.annotation_ac = a.ac and " +
                "a.topic_ac = '" + holder.onHold.getAc() + "' " +
                "and e2a.experiment_ac = ? ");
        Collection annotations = sch.getBeans(AnnotationBean.class, experiment.getAc());
        if(annotations.isEmpty()){
            System.out.println(experiment.getAc() + " is not on-hold");
            onHold = false;
        }else{
            System.out.println(experiment.getAc() + " is on hold");
        }
        return onHold;
    }

    private boolean hasNoInteractions(ComparableExperimentBean experiment) throws IntactException, SQLException {
        boolean hasNoInteractions = false;

        IntactHelper intactHelper = getIntactHelper();
        SanityCheckerHelper sch = new SanityCheckerHelper(intactHelper);
        sch.addMapping(Int2ExpBean.class, "select interaction_ac " +
                                          "from ia_int2exp " +
                                          "where experiment_ac = ? ");
        Collection int2exps = sch.getBeans(Int2ExpBean.class, experiment.getAc());
        if(int2exps.isEmpty()){
            System.out.println(experiment.getAc() + " has no interactions");
            hasNoInteractions = true;
        } else System.out.println(experiment.getAc() + " has interactions");
        return hasNoInteractions;
    }

    private void removeExpOnHoldAndWithNoInteraction(Collection expBeans) throws Exception, IntactException {
        for (Iterator iterator = expBeans.iterator(); iterator.hasNext();) {
            ComparableExperimentBean exp =  (ComparableExperimentBean) iterator.next();
            boolean removed = false;
            if(isOnHold(exp)){
                iterator.remove();
                removed = true;
            }
            if( hasNoInteractions(exp) && false == removed ){
                iterator.remove();
            }
        }
    }

    /**
     * Class holding the needed ControlledVocabBean to build the needed experiments Collection for the correction
     * assigments (pubmed, primaryRef, reviewer, toBeReviewed, accepted and onHold).
     */
    private class CvHolder {

        /**
         * A controlledvocabBean corresponding to the pubmed cv.
         */
        final ControlledvocabBean pubmed;
        /**
         * A controlledvocabBean corresponding to the primaryRef cv.
         */
        final ControlledvocabBean primaryRef;
        /**
         * A controlledvocabBean corresponding to the reviewer cv.
         */
        final ControlledvocabBean reviewer;
        /**
         * A controlledvocabBean corresponding to the toBeReviewed cv.
         */
        final ControlledvocabBean toBeReviewed;
        /**
         * A controlledvocabBean corresponding to the accepted cv.
         */
        final ControlledvocabBean accepted;
        /**
         * A controlledvocabBean corresponding to the onHold cv.
         */
        final ControlledvocabBean onHold;


        public CvHolder() throws Exception, SQLException {

            IntactHelper helper = new IntactHelper();

            SanityCheckerHelper sch = new SanityCheckerHelper( helper );
            sch.addMapping(ControlledvocabBean.class, "SELECT ac, objclass FROM ia_controlledvocab WHERE shortlabel = ?");

            pubmed = getCvBean(CvDatabase.PUBMED, sch);


            primaryRef = getCvBean( CvXrefQualifier.PRIMARY_REFERENCE, sch);
            reviewer = getCvBean( CvTopic.REVIEWER, sch);
            accepted = getCvBean( CvTopic.ACCEPTED, sch);
            toBeReviewed = getCvBean( CvTopic.TO_BE_REVIEWED, sch);
            onHold = getCvBean( CvTopic.ON_HOLD, sch);

            helper.closeStore();
        }

        /**
         *
         * @param shortlabel the shortlabel of the controlled vocabulary you want to load
         * @param sch A sanityCheckerHelper. The mapping for ControlledvocabBean.class should already be done as it's
         * not done inside the method.
         * @return ControlledvocabBean having as shortlabel the given shortlabel in argument.
         * @throws Exception if the controlledvocabBean corresponding to this shortlabel was not found.
         */
        private ControlledvocabBean getCvBean(String shortlabel, SanityCheckerHelper sch) throws Exception {
            ControlledvocabBean cvBean;

            List cvBeans = sch.getBeans(ControlledvocabBean.class,shortlabel);
            if( !cvBeans.isEmpty() ){
                cvBean = (ControlledvocabBean) cvBeans.get(0);
            }else throw new Exception("Couldn't create ControlledvocabBean for " + shortlabel);

            return cvBean;
        }

    }
}

