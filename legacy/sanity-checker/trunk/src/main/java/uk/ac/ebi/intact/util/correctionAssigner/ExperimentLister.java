/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.util.correctionAssigner;

import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.util.sanityChecker.SanityCheckerHelper;
import uk.ac.ebi.intact.util.sanityChecker.model.AnnotationBean;
import uk.ac.ebi.intact.util.sanityChecker.model.ControlledvocabBean;
import uk.ac.ebi.intact.util.sanityChecker.model.ExperimentBean;
import uk.ac.ebi.intact.util.sanityChecker.model.Int2ExpBean;
import uk.ac.ebi.intact.persistence.dao.DaoFactory;
import uk.ac.ebi.intact.sanity.SuperCurator;

import java.sql.SQLException;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO comment it.
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 */
public class ExperimentLister {

    private static final Log log = LogFactory.getLog(ExperimentLister.class);

    CvHolder cvHolder;

    /**
     * Collection of experimentBeans : being created after september 2005 having no annotation to-be-reviewed or
     * accepted having no annotation reviewer In other words, experiment not yet corrected and not yet assigned to any
     * super-curator for correction
     */
    private Collection notAssignedExperiments = new ArrayList();


    /**
     * Collection of experimentBeans : being created after september 2005 having no annotation to-be-reviewed or
     * accepted having an annotation reviewer In other words, experiment not yet corrected but already assigned to a
     * super-curator for correction.
     */
    private Collection assignedExperiments = new ArrayList();

    private Collection onHoldExperiments = new ArrayList();

    private Collection toBeReviewedExperiments = new ArrayList();

    private Collection notAcceptedNotToBeReviewed = new ArrayList();

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

    private Collection<SuperCurator> superCurators;

//    public void removeElementFromAssignedPmid2creator(String pmid){
//        assignedPmid2creator.remove(pmid);
//    }

    /**
     * Constructor of ExperimentLister.
     *
     * @throws SQLException
     * @throws IntactException
     */
    public ExperimentLister(Collection<SuperCurator> superCurators) throws Exception {
        this.superCurators = superCurators;
        initialize();
    }

    private CvHolder getCvHolder() throws Exception {
        if ( cvHolder == null ) {
            cvHolder = new CvHolder();
        }
        return cvHolder;
    }

    private void initialize() throws Exception {

        cvHolder = getCvHolder( );

        removeCorrectionForSuperCuratorAway();

        fillNotAssignedExpCollection();
        removeExpOnHoldAndWithNoInteraction( notAssignedExperiments );

        fillAssignedExpCollection();
        removeExpOnHoldAndWithNoInteraction( assignedExperiments );

        fillPmid2CreatorMaps();
        fillPmid2expColl();
        fillNotAcceptedNotToBeReviewedExperiments();
        fillOnHoldAndToBeReviewedExperiments();
    }

    /**
     * Getter for the HashMap pmid2expColl.
     *
     * @return the HashMap pmid2expColl.
     */
    public HashMap getPmid2expColl() {
        return pmid2expColl;
    }

    /**
     * Getter for Collection notAssignedExperiments.
     *
     * @return the collection notAssignedExperiments.
     */
    public Collection getNotAssignedExperiments() {
        return notAssignedExperiments;
    }

    /**
     * Getter for the Collection assignedExperiments.
     *
     * @return the Collection assignedExperiments
     */
    public Collection getAssignedExperiments() {
        return assignedExperiments;
    }

    /**
     * Getter for the HashMap notAssignedPmid2creator.
     *
     * @return the HashMap notAssignedPmid2creator
     */
    public HashMap getNotAssignedPmid2creator() {
        return notAssignedPmid2creator;
    }

//    public void addElementInNotAssignedPmid2creator(String pmid, String creator){
//        notAssignedPmid2creator.put(pmid, creator.toLowerCase());
//    }

    public Collection getOnHoldExperiments() {
        return onHoldExperiments;
    }

    public Collection getToBeReviewedExperiments() {
        return toBeReviewedExperiments;
    }

    public Collection getNotAcceptedNotToBeReviewed() {
        return notAcceptedNotToBeReviewed;
    }

//    public void addExp2NotAcceptedNotToBeReviewed(ComparableExperimentBean exp){
//        notAcceptedNotToBeReviewed.add(exp);
//    }

    /**
     * Via the sanityCheckerHelper this method is using the dbUtils library to get the Collection notAssignedExperiments
     * of experimentBeans being to assigned to a reviewer for correction.
     *
     * @throws IntactException
     * @throws SQLException
     */
    private void fillNotAssignedExpCollection() throws Exception, SQLException {

        if(log.isDebugEnabled()){
            log.debug("Searching for experiment not accepted, to-be-reviewed and not assigned : ");
        }

        CvHolder holder = getCvHolder();

        SanityCheckerHelper sch = new SanityCheckerHelper();

        sch.addMapping(  ComparableExperimentBean.class, "select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId " +
                                                                "from ia_experiment e, ia_xref x " +
                                                                "where x.parent_ac = e.ac and " +
                                                                "x.database_ac = '" + holder.pubmed.getAc() + "' and " +
                                                                "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and " +
                                                                "e.ac not in ( " +
                                                                "select e.ac " +
                                                                "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                                                                "where e.ac=e2a.experiment_ac and " +
                                                                "e2a.annotation_ac=a.ac and " +
                                                                "a.topic_ac in  ('" + holder.accepted.getAc() + "','" + holder.toBeReviewed.getAc() + "') " +
                                                                "union " +
                                                                "select e.ac " +
                                                                "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                                                                "where e.ac=e2a.experiment_ac and " +
                                                                "e2a.annotation_ac=a.ac and " +
                                                                "a.topic_ac in  ('" + holder.reviewer.getAc() + "') " +
                                                                ") " +
                                                                "and e.created >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? " +
                                                                "order by created_user" );
        if(log.isDebugEnabled()){
            log.debug("... Here is the request done\n\n");
            log.debug("select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId \n" +
                    "from ia_experiment e, ia_xref x \n" +
                    "where x.parent_ac = e.ac and \n" +
                    "x.database_ac = '" + holder.pubmed.getAc() + "' and \n" +
                    "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and \n" +
                    "e.ac not in ( \n" +
                    "select e.ac \n" +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a \n" +
                    "where e.ac=e2a.experiment_ac and \n" +
                    "e2a.annotation_ac=a.ac and \n" +
                    "a.topic_ac in  ('" + holder.accepted.getAc() + "','" + holder.toBeReviewed.getAc() + "') \n" +
                    "union \n" +
                    "select e.ac \n" +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a \n" +
                    "where e.ac=e2a.experiment_ac and \n" +
                    "e2a.annotation_ac=a.ac and \n" +
                    "a.topic_ac in  ('" + holder.reviewer.getAc() + "') \n" +
                    ") \n" +
                    "and e.created >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? \n" +
                    "order by created_user\n\n");
    }
        notAssignedExperiments = sch.getBeans( ComparableExperimentBean.class, "%" );
        if (log.isDebugEnabled()) {
            log.debug("..." + notAssignedExperiments.size() + " experiments found.");
        }
    }

    /**
     * * Via the sanityCheckerHelper this method is using the dbUtils library to get the Collection assignedExperiments
     * of experimentBeans being already assigned in the past to a reviewer for correction.
     *
     * @throws IntactException
     * @throws SQLException
     */

    private void fillAssignedExpCollection() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Searching for experiments assigned and not accepted or to-be-reviewed : ");
        }
        CvHolder holder = getCvHolder();

        SanityCheckerHelper sch = new SanityCheckerHelper();
        sch.addMapping( ComparableExperimentBean.class, "select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId, a.description as reviewer " +
                                                                "from ia_experiment e, ia_exp2annot e2a, ia_annotation a, ia_xref x " +
                                                                "where e.ac=e2a.experiment_ac and " +
                                                                "x.parent_ac = e.ac and " +
                                                                "x.parent_ac = e.ac and " +
                                                                "x.database_ac = '" + holder.pubmed.getAc() + "' and " +
                                                                "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and " +
                                                                "e2a.annotation_ac=a.ac and " +
                                                                "e.ac not in ("+
                                                                    "select e.ac " +
                                                                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                                                                    "where e.ac=e2a.experiment_ac and " +
                                                                    "e2a.annotation_ac=a.ac and " +
                                                                    "a.topic_ac in  ('" + holder.accepted.getAc() + "','" + holder.toBeReviewed.getAc() + "') " +
                                                                    ") and " +
                                                                "a.topic_ac in  ('" + holder.reviewer.getAc() + "') " +
                                                                "and e.created >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? " +
                                                                "order by created_user" );

        assignedExperiments = sch.getBeans( ComparableExperimentBean.class, "%" );
        if (log.isDebugEnabled()) {
            log.debug("..." + assignedExperiments.size() + " experiments found.");
            log.debug("... Here was the request done : \n\n");
            log.debug("select e.ac, e.created_user, e.created, e.shortlabel, x.primaryId as pubmedId, a.description as reviewer \n" +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a, ia_xref x \n" +
                    "where e.ac=e2a.experiment_ac and \n" +
                    "x.parent_ac = e.ac and \n" +
                    "x.parent_ac = e.ac and \n" +
                    "x.database_ac = '" + holder.pubmed.getAc() + "' and \n" +
                    "x.qualifier_ac = '" + holder.primaryRef.getAc() + "' and \n" +
                    "e2a.annotation_ac=a.ac and \n" +
                    "e.ac not in (\n"+
                    "select e.ac \n" +
                    "from ia_experiment e, ia_exp2annot e2a, ia_annotation a \n" +
                    "where e.ac=e2a.experiment_ac and \n" +
                    "e2a.annotation_ac=a.ac and \n" +
                    "a.topic_ac in  ('" + holder.accepted.getAc() + "','" + holder.toBeReviewed.getAc() + "')\n " +
                    ") and " +
                    "a.topic_ac in  ('" + holder.reviewer.getAc() + "') \n" +
                    "and e.created >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and e.ac like ? \n" +
                    "order by created_user\n\n");
    }
    }


    /**
     * If a superCurator is away we should re-assigne its corrections to somebody else and stoppe assigning him new
     * corrections. The fact that a superCurator is away can be seen in the fact that it's method getPercentage return 0
     * . To re-assign it's correction to somebody else we just remove all the annotation on experiment having as
     * topic_ac the ac of the reviewer controlled vocabulary and as description the name of the superCurator being away.
     * Then the assigner will automatically detect its former-assigned experiments as not assigned experiments and will
     * automatically re-assign them.
     *
     * @throws Exception
     */
    public void removeCorrectionForSuperCuratorAway() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Removing assignment to curators being away");
        }

        for ( Iterator iterator = superCurators.iterator(); iterator.hasNext(); ) {
            SuperCurator sc = (SuperCurator) iterator.next();
            if ( sc.getPercentage() == 0 ) {
                SanityCheckerHelper sch = new SanityCheckerHelper();
                sch.addMapping( ComparableExperimentBean.class, "select e.ac, e.shortlabel, e.created, e.created_user " +
                                                                        "from ia_experiment e, ia_exp2annot e2a , ia_annotation a " +
                                                                        "where e.ac = e2a.experiment_ac " +
                                                                        "and a.ac = e2a.annotation_ac " +
                                                                        "and a.topic_ac = '" + cvHolder.reviewer.getAc() + "' " +
                                                                        "and a.description = ? " );
                Collection experiments = sch.getBeans( ComparableExperimentBean.class, sc.getId().toLowerCase() );
                if( log.isDebugEnabled() ) {
                    log.debug("... curator " + sc.getId() + "is away and have " + experiments.size() +
                    " experiments to review : ");
                }
                for ( Iterator iterator1 = experiments.iterator(); iterator1.hasNext(); ) {
                    ComparableExperimentBean comparableExperimentBean = (ComparableExperimentBean) iterator1.next();

                    if (log.isDebugEnabled()) {
                        log.debug("...... " + comparableExperimentBean.getAc() + "," + comparableExperimentBean.getShortlabel() );
                    }
                    removeReviewerAnnotation( comparableExperimentBean.getAc() );
                }
                if (log.isDebugEnabled()) {
                    log.debug("They will be re-assigned to the others super-curators.");
            }
        }
    }
    }

    /**
     * Remove the reviewer annotation linked to this experiment having the ac given in paremeter.
     *
     * @param expAc ac of the experiment from which we need to remove the reviewer annotation(s).
     *
     * @throws IntactException
     */
    public void removeReviewerAnnotation( String expAc ) throws IntactException {


        //Get the util.model.Experiment object corresponding to this experiment ac.
        Experiment experiment = DaoFactory.getExperimentDao().getByAc(expAc);

        Collection annotations = experiment.getAnnotations();
        for ( Iterator iterator = annotations.iterator(); iterator.hasNext(); ) {
            Annotation annotation = (Annotation) iterator.next();
            if ( annotation.getCvTopic().getShortLabel().equals( CvTopic.REVIEWER ) ) {
                iterator.remove();
                DaoFactory.getAnnotationDao().delete( annotation );
                experiment.removeAnnotation( annotation );

            }
        }

        DaoFactory.getExperimentDao().update( experiment );

        
    }

    /**
     * From the Collection of not yet asseigned ExperimentBean, build a the hashMap pmid2expColl.
     */
    private void fillPmid2expColl() {
        for ( Iterator iterator = notAssignedExperiments.iterator(); iterator.hasNext(); ) {
            ComparableExperimentBean exp = (ComparableExperimentBean) iterator.next();
            String pubmed = exp.getPubmedId();
            if ( pmid2expColl.containsKey( pubmed ) ) {
                Collection experiments = (Collection) pmid2expColl.get( pubmed );
                experiments.add( exp );
                pmid2expColl.put( pubmed, experiments );
            } else {
                Collection experiments = new ArrayList();
                experiments.add( exp );
                pmid2expColl.put( pubmed, experiments );
            }
        }
    }

    /**
     * From the 2 collections of ExperimentBean (assignedExperiments and notAssignedExperiment) build a map the maps
     * assignedPmid2creator notAssignedPmid2creator
     */
    private void fillPmid2CreatorMaps() {
        for ( Iterator iterator = assignedExperiments.iterator(); iterator.hasNext(); ) {
            ComparableExperimentBean exp = (ComparableExperimentBean) iterator.next();
            assignedPmid2creator.put( exp.getPubmedId(), exp.getCreated_user().toLowerCase() );

        }

        for ( Iterator iterator = notAssignedExperiments.iterator(); iterator.hasNext(); ) {
            ComparableExperimentBean exp = (ComparableExperimentBean) iterator.next();
            if ( !assignedPmid2creator.containsKey( exp.getPubmedId() ) ) {
                notAssignedPmid2creator.put( exp.getPubmedId(), exp.getCreated_user().toLowerCase() );
            }
        }


    }

    private void fillOnHoldAndToBeReviewedExperiments() throws IntactException, SQLException {
        SanityCheckerHelper sch = new SanityCheckerHelper();

        sch.addMapping( ExperimentBean.class, "select e.ac, e.created_user, e.created, e.shortlabel " +
                                                      "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                                                      "where e2a.annotation_ac = a.ac " +
                                                      "and e2a.experiment_ac = e.ac " +
                                                      "and a.topic_ac = ? " +
                                                      "order by e.shortlabel" );
        onHoldExperiments = sch.getBeans( ExperimentBean.class, cvHolder.onHold.getAc() );
        toBeReviewedExperiments = sch.getBeans( ExperimentBean.class, cvHolder.toBeReviewed.getAc() );

    }

    private void fillNotAcceptedNotToBeReviewedExperiments() throws IntactException, SQLException {
        SanityCheckerHelper sch = new SanityCheckerHelper();

        sch.addMapping( ExperimentBean.class, "select ac, created_user, created, shortlabel from ia_experiment where ac not in " +
                                                      "(select e.ac " +
                                                      "from ia_experiment e, ia_exp2annot e2a, ia_annotation a " +
                                                      "where e.ac=e2a.experiment_ac and " +
                                                      "e2a.annotation_ac=a.ac and " +
                                                      "a.topic_ac in ('" + cvHolder.accepted.getAc() + "','" + cvHolder.toBeReviewed.getAc() + "')) " +
                                                      "and created >  to_date('01-Sep-2005:00:00:00','DD-MON-YYYY:HH24:MI:SS') and ac like ? " );
        notAcceptedNotToBeReviewed = sch.getBeans( ExperimentBean.class, "%" );

    }

    /**
     * @param experiment
     *
     * @return
     *
     * @throws IntactException
     * @throws SQLException
     */
    private boolean isOnHold(  ComparableExperimentBean experiment ) throws Exception, SQLException {
        boolean onHold = true;

        CvHolder holder = getCvHolder();

        SanityCheckerHelper sch = new SanityCheckerHelper();
        sch.addMapping( AnnotationBean.class, "select a.ac " +
                                                      "from ia_annotation a, ia_exp2annot e2a " +
                                                      "where e2a.annotation_ac = a.ac and " +
                                                      "a.topic_ac = '" + holder.onHold.getAc() + "' " +
                                                      "and e2a.experiment_ac = ? " );
        Collection annotations = sch.getBeans( AnnotationBean.class, experiment.getAc() );
        if ( annotations.isEmpty() ) {
            onHold = false;
        }
        return onHold;
    }

    private boolean hasNoInteractions(  ComparableExperimentBean experiment ) throws IntactException, SQLException {
        boolean hasNoInteractions = false;


        SanityCheckerHelper sch = new SanityCheckerHelper();
        sch.addMapping( Int2ExpBean.class, "select interaction_ac " +
                                                   "from ia_int2exp " +
                                                   "where experiment_ac = ? " );
        Collection int2exps = sch.getBeans( Int2ExpBean.class, experiment.getAc() );
        if ( int2exps.isEmpty() ) {
            hasNoInteractions = true;
        }
        return hasNoInteractions;
    }

    private void removeExpOnHoldAndWithNoInteraction(  Collection expBeans ) throws Exception, IntactException {
        if (log.isDebugEnabled()) {
            log.debug("Filtering out experiments being on-hold or without interactions : ");
        }
        for ( Iterator iterator = expBeans.iterator(); iterator.hasNext(); ) {
            ComparableExperimentBean exp = (ComparableExperimentBean) iterator.next();
            boolean removed = false;
            if ( isOnHold( exp ) ) {
                if (log.isDebugEnabled()) {
                    log.debug("..." + exp.getAc() + ", " + exp.getShortlabel() + " is on hold.");
                }
                iterator.remove();
                removed = true;
            }
            if ( hasNoInteractions( exp ) && false == removed ) {
                if (log.isDebugEnabled()) {
                    log.debug("..." + exp.getAc() + ", " + exp.getShortlabel() + " has no interactions.");
                }
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

            SanityCheckerHelper sch = new SanityCheckerHelper();
            sch.addMapping( ControlledvocabBean.class, "SELECT ac, objclass FROM ia_controlledvocab WHERE shortlabel = ?" );

            pubmed = getCvBean( CvDatabase.PUBMED, sch );


            primaryRef = getCvBean( CvXrefQualifier.PRIMARY_REFERENCE, sch );
            reviewer = getCvBean( CvTopic.REVIEWER, sch );
            accepted = getCvBean( CvTopic.ACCEPTED, sch );
            toBeReviewed = getCvBean( CvTopic.TO_BE_REVIEWED, sch );
            onHold = getCvBean( CvTopic.ON_HOLD, sch );
        }

        /**
         * @param shortlabel the shortlabel of the controlled vocabulary you want to load
         * @param sch        A sanityCheckerHelper. The mapping for ControlledvocabBean.class should already be done as
         *                   it's not done inside the method.
         *
         * @return ControlledvocabBean having as shortlabel the given shortlabel in argument.
         *
         * @throws Exception if the controlledvocabBean corresponding to this shortlabel was not found.
         */
        private ControlledvocabBean getCvBean(  String shortlabel, SanityCheckerHelper sch ) throws Exception {
            ControlledvocabBean cvBean;

            List cvBeans = sch.getBeans( ControlledvocabBean.class, shortlabel );
            if ( !cvBeans.isEmpty() ) {
                cvBean = (ControlledvocabBean) cvBeans.get( 0 );
            } else {
                throw new Exception( "Couldn't create ControlledvocabBean for " + shortlabel );
            }

            return cvBean;
        }

    }
}

