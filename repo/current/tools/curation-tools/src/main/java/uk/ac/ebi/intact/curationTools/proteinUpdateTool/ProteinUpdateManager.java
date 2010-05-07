package uk.ac.ebi.intact.curationTools.proteinUpdateTool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import uk.ac.ebi.intact.bridges.ncbiblast.model.BlastProtein;
import uk.ac.ebi.intact.core.IntactTransactionException;
import uk.ac.ebi.intact.core.context.DataContext;
import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.core.persistence.dao.DaoFactory;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionReport;
import uk.ac.ebi.intact.curationTools.model.actionReport.BlastReport;
import uk.ac.ebi.intact.curationTools.model.actionReport.PICRReport;
import uk.ac.ebi.intact.curationTools.model.contexts.UpdateContext;
import uk.ac.ebi.intact.curationTools.model.results.IdentificationResults;
import uk.ac.ebi.intact.curationTools.strategies.StrategyForProteinUpdate;
import uk.ac.ebi.intact.curationTools.strategies.exceptions.StrategyException;
import uk.ac.ebi.intact.dbupdate.prot.ProteinUpdateProcessor;
import uk.ac.ebi.intact.dbupdate.prot.ProteinUpdateProcessorConfig;
import uk.ac.ebi.intact.dbupdate.prot.report.FileReportHandler;
import uk.ac.ebi.intact.dbupdate.prot.report.UpdateReportHandler;
import uk.ac.ebi.intact.model.*;

import javax.persistence.Query;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23-Mar-2010</pre>
 */

public class ProteinUpdateManager {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( ProteinUpdateManager.class );

    /**
     * the list of proteins to update
     */
    private List<ProteinImpl> proteinToUpdate = new ArrayList<ProteinImpl>();

    /**
     * the strategy used to update the proteins
     */
    private StrategyForProteinUpdate strategy;

    /**
     * the intact context
     */
    private IntactContext intactContext;

    /**
     * create a new ProteinUpdate manager. The intact context is null and
     * should be set later. The strategy for update doesn't take into account the isoforms and keep the canonical sequence.
     */
    public ProteinUpdateManager(){
        this.strategy = new StrategyForProteinUpdate();
        this.strategy.enableIsoforms(false);
        this.strategy.setBasicBlastProcessRequired(false);
    }

    /**
     * Create a new ProteinUpdateMananger with a specific Intact context. The strategy for update doesn't take into account
     * the isoforms and keep the canonical sequence.
     * @param context : the intact context
     */
    public ProteinUpdateManager(IntactContext context){
        this.strategy = new StrategyForProteinUpdate();
        this.strategy.enableIsoforms(false);
        this.strategy.setBasicBlastProcessRequired(false);
        setIntactContext(context);
    }

    /**
     * set the intact context
     * @param intactContext
     */
    public void setIntactContext(IntactContext intactContext) {
        this.intactContext = intactContext;
        this.strategy.setIntactContextForFeatureRangeChecking(intactContext);
    }

    /**
     * This method query IntAct to get the list of protein to update and for each one create an updateContext
     * @return the list of UpdateContext created from the protein to update
     * @throws ProteinUpdateException
     * @throws StrategyException
     */
    private HashMap<String, UpdateContext> getListOfContextsForProteinToUpdate() throws ProteinUpdateException, StrategyException {

        // can't query Intact without any intact context
        if (this.intactContext == null){
            throw new StrategyException("We can't update the proteins without any IntactContext instance. please set the intactContext of the ProteinUpdateManager.");
        }

        // set the intact data context
        final DataContext dataContext = this.intactContext.getDataContext();
        TransactionStatus transactionStatus = dataContext.beginTransaction();

        // get all the intact entries without any uniprot cross reference or with uniprot cross reference with a qualifier different from 'identity' and which can only be uniprot-removed-ac
        final DaoFactory daoFactory = dataContext.getDaoFactory();
        final Query query = daoFactory.getEntityManager().createQuery("select distinct p from InteractorImpl p "+
                    "left join p.sequenceChunks as seq " +
                    "left join p.xrefs as xrefs " +
                    "left join p.annotations as annotations " +
                    "where p.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and p not in ( "+
                    "select p2 "+
                    "from InteractorImpl p2 join p2.xrefs as xrefs "+
                    "where p2.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and xrefs.cvDatabase.ac = 'EBI-31' " +
                    "and xrefs.cvXrefQualifier.shortLabel <> 'uniprot-removed-ac' )");

        HashMap<String, UpdateContext> contexts = new HashMap<String, UpdateContext>();

        proteinToUpdate = query.getResultList();
        log.info(proteinToUpdate.size());

        for (ProteinImpl prot : proteinToUpdate){
            String accession = prot.getAc();
            Collection<InteractorXref> refs = prot.getXrefs();
            String sequence = prot.getSequence();
            BioSource organism = prot.getBioSource();

            UpdateContext context = new UpdateContext();
            contexts.put(accession, context);

            context.setSequence(sequence);
            context.setOrganism(organism);
            context.setIntactAccession(accession);
            addIdentityCrossreferencesToContext(refs, context);
        }

        try {
            dataContext.commitTransaction(transactionStatus);
        } catch (IntactTransactionException e) {
            throw new ProteinUpdateException(e);
        }
        return contexts;
    }

    /**
     *
     * @param qualifier : the qualifier of the cross reference
     * @return true if the qualifier is 'identity'
     */
    private boolean isIdentityCrossReference(CvXrefQualifier qualifier){
        if (qualifier.getIdentifier() != null){
            if (qualifier.getIdentifier().equals(CvXrefQualifier.IDENTITY_MI_REF)){
                return true;
            }

        }
        else {
            if (qualifier.getShortLabel().equals(CvXrefQualifier.IDENTITY)){
                return true;
            }
        }
        return false;
    }

    /**
     * Add all the cross references with qualifier 'identity' to the list of identifiers of the protein
     * @param refs : the refs of the protein
     * @param context : the context of the protein
     */
    private void addIdentityCrossreferencesToContext(Collection<InteractorXref> refs, UpdateContext context){
        for (InteractorXref ref : refs){
            if (ref.getPrimaryId() != null){
                if (ref.getCvXrefQualifier() != null){
                    CvXrefQualifier qualifier = ref.getCvXrefQualifier();

                    if (isIdentityCrossReference(qualifier)){
                        context.addIdentifier(ref.getPrimaryId());
                    }
                }
            }
        }
    }

    /**
     * get the annotation 'no-uniprot-update' if there is one in the list of annotations
     * @param annotations : the annotations of the protein
     * @return the annotation 'no-uniprot-update' if there is one in the list of annotations, null otherwise
     */
    private Annotation collectNo_Uniprot_UpdateAnnotation(Collection<Annotation> annotations){
        for (Annotation a : annotations){
            if (a.getCvTopic() != null){
                CvTopic topic = a.getCvTopic();

                if (topic.getIdentifier() != null){
                    if (topic.getIdentifier().equals("IA:0280")){
                        return a;
                    }
                }
                else if (topic.getShortLabel() != null){
                    if (topic.getShortLabel().equals(CvTopic.NON_UNIPROT)){
                        return a;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Create a new InteractorXref for the protein
     * @param uniprotAc : the uniprot accession
     * @return the InteractorXref with the uniprot ac and qualifier identity
     */
    private InteractorXref createIdentityInteractorXrefForUniprotAc(String uniprotAc){
        if (uniprotAc == null){
            return null;
        }

        final CvDatabase uniprot = this.intactContext.getDaoFactory().getCvObjectDao(CvDatabase.class).getByPsiMiRef( CvDatabase.UNIPROT_MI_REF );
        final CvXrefQualifier identity = this.intactContext.getDaoFactory().getCvObjectDao(CvXrefQualifier.class).getByPsiMiRef(CvXrefQualifier.IDENTITY_MI_REF);

        InteractorXref xRef = new InteractorXref(this.intactContext.getInstitution(), uniprot, uniprotAc, identity);

        return xRef;
    }

    /*private ProteinImpl getProteinWithIdentityUniprotCrossReference(String uniprotAc){
        List<ProteinImpl> existingProt = this.intactContext.getDaoFactory().getProteinDao().getByUniprotId(uniprotAc);

        if (existingProt.isEmpty()){
            return null;
        }
        else if (existingProt.size() > 1){
            System.err.println(existingProt.size() + "proteins already exist with the same uniprot cross reference " + uniprotAc + ". We will take only the first protein and remap the identified protein to this protein.");
            return existingProt.get(0);
        }
        else {
            return existingProt.get(0);
        }
    }*/

    /**
     * add a new uniprot cross reference with qualifier identity to the list of cross references of the protein
     * @param prot : the protein
     * @param uniprotAc : the uniprot accession
     * @param factory : the Intact factory (not used)
     */
    private void addUniprotCrossReferenceTo(ProteinImpl prot, String uniprotAc, DaoFactory factory){
        InteractorXref ref = createIdentityInteractorXrefForUniprotAc(uniprotAc);

        if (ref != null){
            log.info("cross reference to uniprot "+ uniprotAc +" added to the cross references of " + prot.getAc());
            //factory.getXrefDao(InteractorXref.class).persist( ref );
            prot.addXref(ref);
        }
    }

    /*private void replaceInInteractions (ProteinImpl proteinToReplace, ProteinImpl replacingProtein){
        List<Interaction> interactions = this.intactContext.getDaoFactory().getInteractionDao().getInteractionsByInteractorAc(proteinToReplace.getAc());

        for (Interaction interaction : interactions){
            Collection<Component> components = interaction.getComponents();

            for (Component component : components){
                if (component.getInteractorAc() != null){
                    if (component.getInteractorAc().equals(proteinToReplace.getAc())){
                        component.setInteractorAc(replacingProtein.getAc());
                        component.setInteractor(replacingProtein);
                        this.intactContext.getCorePersister().saveOrUpdate(component);
                        break;
                    }
                }
            }
            this.intactContext.getCorePersister().saveOrUpdate(interaction);
        }
    }*/

    /**
     * Update the proteins with no uniprot cross references and the proteins with uniprot cross references set to 'uniprot-removed-ac'
     * @throws ProteinUpdateException
     */
    public void updateProteins() throws ProteinUpdateException {
        // get the data context
        final DataContext dataContext = this.intactContext.getDataContext();
        TransactionStatus transactionStatus = dataContext.beginTransaction();
        try {

            // create a new file where the results are stored in
            File file = new File("updateReportTest.txt");
            FileWriter writer = new FileWriter(file);

            final DaoFactory daoFactory = dataContext.getDaoFactory();
            final Query query = daoFactory.getEntityManager().createQuery("select distinct p from InteractorImpl p "+
                    "left join p.sequenceChunks as seq " +
                    "left join p.xrefs as xrefs " +
                    "left join p.annotations as annotations " +
                    "where p.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and p not in ( "+
                    "select p2 "+
                    "from InteractorImpl p2 join p2.xrefs as xrefs "+
                    "where p2.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and xrefs.cvDatabase.ac = 'EBI-31' " +
                    "and xrefs.cvXrefQualifier.shortLabel <> 'uniprot-removed-ac' )");

            proteinToUpdate = query.getResultList();
            log.info(proteinToUpdate.size());

            ArrayList<String> accessionsToUpdate = new ArrayList<String>();

            for (ProteinImpl prot : proteinToUpdate){

                String accession = prot.getAc();
                String shortLabel = prot.getShortLabel();
                log.info("Protein AC = " + accession + " shortLabel = " + shortLabel);

                Collection<InteractorXref> refs = prot.getXrefs();
                Collection<Annotation> annotations = prot.getAnnotations();
                String sequence = prot.getSequence();
                BioSource organism = prot.getBioSource();

                // context
                UpdateContext context = new UpdateContext();
                context.setSequence(sequence);
                context.setOrganism(organism);
                context.setIntactAccession(accession);
                addIdentityCrossreferencesToContext(refs, context);

                // result
                IdentificationResults result = this.strategy.identifyProtein(context);
                writeResultReports(accession, result, writer);

                // update
                if (result != null && result.getUniprotId() != null){
                    Annotation a = collectNo_Uniprot_UpdateAnnotation(annotations);

                    if (a != null){
                        log.info("annotation no_uniprot_update removed from the annotations of " + accession);
                        prot.removeAnnotation(a);
                        //daoFactory.getAnnotationDao().delete(a);
                    }
                    addUniprotCrossReferenceTo(prot, result.getUniprotId(), daoFactory);
                    //daoFactory.getProteinDao().update( prot );
                    accessionsToUpdate.add(accession);
                }
            }

            // commit the changes
            log.info("commit the change in the database.");
            log.info(accessionsToUpdate.size() + " proteins have been modified.");
            dataContext.commitTransaction(transactionStatus);
            writer.close();

            // update the database
            log.info("Processing the update of the proteins in Intact");
            UpdateReportHandler reportHandler = new FileReportHandler(new File("target"));
            ProteinUpdateProcessorConfig configUpdate = new ProteinUpdateProcessorConfig(reportHandler);

            ProteinUpdateProcessor protUpdateProcessor = new ProteinUpdateProcessor(configUpdate);
            protUpdateProcessor.updateByACs(accessionsToUpdate);

        } catch (IntactTransactionException e) {
            throw new ProteinUpdateException(e);
        } catch (StrategyException e) {
            throw new ProteinUpdateException("There is a problem when executing the protein update strategy. Check the protein contexts.", e);
        } catch (IOException e) {
            throw new ProteinUpdateException("We can't write the results in a file.", e);
        } catch (Exception e){
            throw new ProteinUpdateException( e);
        }
    }

    /**
     * Write a report for the identification of the proteins without any uniprot cross references set to identity but another uniprot cross reference
     * @throws ProteinUpdateException
     */
    public void witeUpdateReportForProteinsWithUniprotCrossReferences() throws ProteinUpdateException {
        // create the data context
        final DataContext dataContext = this.intactContext.getDataContext();
        TransactionStatus transactionStatus = dataContext.beginTransaction();
        try {

            // create the file where to write the report
            File file = new File("updateReportForProteinWithUniprotCrossReferences.txt");
            FileWriter writer = new FileWriter(file);

            final DaoFactory daoFactory = dataContext.getDaoFactory();
            final Query query = daoFactory.getEntityManager().createQuery("select distinct p from InteractorImpl p "+
                    "left join p.sequenceChunks as seq " +
                    "left join p.xrefs as xrefs " +
                    "left join p.annotations as annotations " +
                    "where p.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and p not in ( "+
                    "select p2 "+
                    "from InteractorImpl p2 join p2.xrefs as xrefs "+
                    "where p2.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' "+
                    "and xrefs.cvDatabase.ac = 'EBI-31' " +
                    "and xrefs.cvXrefQualifier.shortLabel = 'identity') " +
                    "and p in ( " +
                    "select p2 " +
                    "from InteractorImpl p2 join p2.xrefs as xrefs " +
                    "where p2.objClass = 'uk.ac.ebi.intact.model.ProteinImpl' " +
                    "and xrefs.cvDatabase.ac = 'EBI-31' " +
                    "and xrefs.cvXrefQualifier.shortLabel <> 'uniprot-removed-ac')");

            proteinToUpdate = query.getResultList();
            log.info(proteinToUpdate.size());

            ArrayList<String> accessionsToUpdate = new ArrayList<String>();

            for (ProteinImpl prot : proteinToUpdate){

                String accession = prot.getAc();
                String shortLabel = prot.getShortLabel();
                log.info("Protein AC = " + accession + " shortLabel = " + shortLabel);

                Collection<InteractorXref> refs = prot.getXrefs();
                String sequence = prot.getSequence();
                BioSource organism = prot.getBioSource();

                // context
                UpdateContext context = new UpdateContext();
                context.setSequence(sequence);
                context.setOrganism(organism);
                context.setIntactAccession(accession);
                addIdentityCrossreferencesToContext(refs, context);

                // result
                IdentificationResults result = this.strategy.identifyProtein(context);
                writeResultReports(accession, result, writer);

                // update
                if (result != null && result.getUniprotId() != null){
                    accessionsToUpdate.add(accession);
                }
            }
            log.info(accessionsToUpdate.size() + " proteins have been identified and could be updated.");
            dataContext.commitTransaction(transactionStatus);
            writer.close();

        } catch (IntactTransactionException e) {
            throw new ProteinUpdateException(e);
        } catch (StrategyException e) {
            throw new ProteinUpdateException("There is a problem when executing the protein update strategy. Check the protein contexts.", e);
        } catch (IOException e) {
            throw new ProteinUpdateException("We can't write the results in a file.", e);
        } catch (Exception e){
            throw new ProteinUpdateException( e);
        }
    }

    /**
     * Write the results of the protein update process
     * @throws ProteinUpdateException
     * @throws StrategyException
     */
    public void writeResultsOfProteinUpdate() throws ProteinUpdateException, StrategyException {

        HashMap<String, UpdateContext> contexts= getListOfContextsForProteinToUpdate();
        File file = new File("updateReport.txt");

        try {
            FileWriter writer = new FileWriter(file);
            for (String protAc : contexts.keySet()){
                log.info("protAc = " + protAc);
                UpdateContext context = contexts.get(protAc);
                IdentificationResults result = this.strategy.identifyProtein(context);

                writeResultReports(protAc, result, writer);
            }
            writer.close();
        } catch (IOException e) {
            throw new ProteinUpdateException("We can't write the results in a file.", e);
        } catch (StrategyException e) {
            throw new ProteinUpdateException("There is a problem when executing the protein update strategy. Check the protein contexts.", e);
        }
    }

    /**
     * write the results in a file
     * @param protAc : the intact accession
     * @param result : the result
     * @param writer : the file writer
     * @throws ProteinUpdateException
     */
    private void writeResultReports(String protAc, IdentificationResults result, FileWriter writer) throws ProteinUpdateException {
        try {
            writer.write("************************" + protAc + "************************************ \n");

            writer.write("Uniprot accession found : " + result.getUniprotId() + "\n");
            for (ActionReport report : result.getListOfActions()){
                writer.write(report.getName().toString() + " : " + report.getStatus().getLabel() + ", " + report.getStatus().getDescription() + "\n");

                for (String warn : report.getWarnings()){
                    writer.write(warn + "\n");
                }

                for (String ac : report.getPossibleAccessions()){
                    writer.write("possible accession : " + ac + "\n");
                }

                if (report instanceof PICRReport){
                    PICRReport picr = (PICRReport) report;
                    writer.write("Is a Swissprot entry : " + picr.isAswissprotEntry() + "\n");

                    for (String database : picr.getCrossReferences().keySet()){
                        writer.write(database + " cross reference : " + picr.getCrossReferences().get(database) + "\n");
                    }
                }
                else if (report instanceof BlastReport){
                    BlastReport blast = (BlastReport) report;

                    for (BlastProtein prot : blast.getBlastMatchingProteins()){
                        writer.write("BLAST Protein " + prot.getAccession() + " : identity = " + prot.getIdentity() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new ProteinUpdateException("We can't write the results of the protein " + protAc, e);
        }
    }
}
