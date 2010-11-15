package uk.ac.ebi.intact.dbupdate.prot.actions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.commons.util.Crc64;
import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.dbupdate.prot.DuplicateReport;
import uk.ac.ebi.intact.dbupdate.prot.ProteinUpdateProcessor;
import uk.ac.ebi.intact.dbupdate.prot.event.DuplicatesFoundEvent;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.uniprot.model.UniprotFeatureChain;
import uk.ac.ebi.intact.uniprot.model.UniprotProtein;
import uk.ac.ebi.intact.uniprot.model.UniprotSpliceVariant;
import uk.ac.ebi.intact.util.protein.ComprehensiveCvPrimer;
import uk.ac.ebi.intact.util.protein.mock.MockUniprotProtein;

import java.util.*;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15-Nov-2010</pre>
 */
@ContextConfiguration(locations = {"classpath*:/META-INF/jpa.test.spring.xml"} )
public class DuplicateFixerTest extends IntactBasicTestCase{

    private DuplicatesFixer duplicateFixer;

    @Before
    public void setUp(){
        duplicateFixer = new DuplicatesFixer();
        TransactionStatus status = getDataContext().beginTransaction();

        ComprehensiveCvPrimer primer = new ComprehensiveCvPrimer(getDaoFactory());
        primer.createCVs();

        getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_same_sequence(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein isoform = getMockBuilder().createProteinSpliceVariant(primary, "P60953-1", "isoform");
        Protein isoform2 = getMockBuilder().createProteinSpliceVariant(prot, "P60953-1", "isoform3");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform2);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());

        Protein chain = getMockBuilder().createProteinChain(primary, "PRO-1", "chain");
        Protein chain2 = getMockBuilder().createProteinChain(prot, "PRO-1", "chain");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);

        Assert.assertEquals(8, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(1, primary.getActiveInstances().size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getSpliceVariants(primary).size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getProteinChains(primary).size());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertNull(report.getTranscript());
        Assert.assertTrue(report.getComponentsWithFeatureConflicts().isEmpty());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertEquals(2, report.getOriginalProtein().getActiveInstances().size());
        Assert.assertTrue(hasXRef(report.getOriginalProtein(), prot.getAc(), CvDatabase.INTACT, "intact-secondary"));
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getSpliceVariants(report.getOriginalProtein()).size());
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getProteinChains(report.getOriginalProtein()).size());

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_same_sequence_copy_several_intact_secondary(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";
        String previousSecondary = "EBI-XXXX";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        InteractorXref secondary = getMockBuilder().createXref(prot, previousSecondary,
                IntactContext.getCurrentInstance().getDaoFactory().getCvObjectDao(CvXrefQualifier.class).getByShortLabel("intact-secondary"),
                IntactContext.getCurrentInstance().getDaoFactory().getCvObjectDao(CvDatabase.class).getByPsiMiRef(CvDatabase.INTACT_MI_REF));

        IntactContext.getCurrentInstance().getDaoFactory().getXrefDao(InteractorXref.class).persist(secondary);
        prot.addXref(secondary);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein isoform = getMockBuilder().createProteinSpliceVariant(primary, "P60953-1", "isoform");
        Protein isoform2 = getMockBuilder().createProteinSpliceVariant(prot, "P60953-1", "isoform3");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform2);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());

        Protein chain = getMockBuilder().createProteinChain(primary, "PRO-1", "chain");
        Protein chain2 = getMockBuilder().createProteinChain(prot, "PRO-1", "chain");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);

        Assert.assertEquals(8, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(1, primary.getActiveInstances().size());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertTrue(report.getComponentsWithFeatureConflicts().isEmpty());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertTrue(hasXRef(report.getOriginalProtein(), prot.getAc(), CvDatabase.INTACT, "intact-secondary"));
        Assert.assertTrue(hasXRef(report.getOriginalProtein(), previousSecondary, CvDatabase.INTACT, "intact-secondary"));

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_same_sequence_several_parents(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";
        String previousSecondary = "EBI-XXXX";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein isoform = getMockBuilder().createProteinSpliceVariant(primary, "P60953-1", "isoform");
        Protein isoform2 = getMockBuilder().createProteinSpliceVariant(prot, "P60953-1", "isoform3");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform2);

        InteractorXref parent = getMockBuilder().createXref(isoform2, previousSecondary,
                IntactContext.getCurrentInstance().getDaoFactory().getCvObjectDao(CvXrefQualifier.class).getByPsiMiRef(CvXrefQualifier.ISOFORM_PARENT_MI_REF),
                IntactContext.getCurrentInstance().getDaoFactory().getCvObjectDao(CvDatabase.class).getByPsiMiRef(CvDatabase.INTACT_MI_REF));

        IntactContext.getCurrentInstance().getDaoFactory().getXrefDao(InteractorXref.class).persist(parent);
        isoform2.addXref(parent);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(isoform2);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());

        Protein chain = getMockBuilder().createProteinChain(primary, "PRO-1", "chain");
        Protein chain2 = getMockBuilder().createProteinChain(prot, "PRO-1", "chain");
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(chain2);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);

        Assert.assertEquals(8, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(1, primary.getActiveInstances().size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getSpliceVariants(primary).size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getProteinChains(primary).size());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertTrue(report.getComponentsWithFeatureConflicts().isEmpty());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertTrue(hasXRef(isoform2, previousSecondary, CvDatabase.INTACT, CvXrefQualifier.ISOFORM_PARENT));
        Assert.assertTrue(hasXRef(isoform2, report.getOriginalProtein().getAc(), CvDatabase.INTACT, CvXrefQualifier.ISOFORM_PARENT));

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_different_sequences_no_range_conflicts(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";
        String sequence2 = "AAPPPFFFLLLMMMMM";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence2);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);

        for (Component c : interactionPrimary.getComponents()){
             c.getBindingDomains().clear();
        }

        for (Component c : interactionToMove.getComponents()){
             c.getBindingDomains().clear();
        }

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(2, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(1, primary.getActiveInstances().size());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertTrue(report.getComponentsWithFeatureConflicts().isEmpty());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertEquals(2, report.getOriginalProtein().getActiveInstances().size());
        Assert.assertEquals(uniprot.getSequence(), report.getOriginalProtein().getSequence());

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_different_sequences_range_conflicts_no_transcript_found_duplicate(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";
        String sequence2 = "AAPPPFFFLLLMMMMM";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence2);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);
        Interaction interactionWithConflict = getMockBuilder().createInteraction(prot, random1);

        for (Component c : interactionPrimary.getComponents()){
             c.getBindingDomains().clear();
        }
        for (Component c : interactionToMove.getComponents()){
             c.getBindingDomains().clear();
        }

        for (Component c : interactionWithConflict.getComponents()){
             c.getBindingDomains().clear();

            if (prot.getAc().equalsIgnoreCase(c.getInteractor().getAc())){
                Feature f = getMockBuilder().createFeatureRandom();
                Range r = getMockBuilder().createRange(1, 1, 50, 50);

                f.getRanges().clear();
                f.addRange(r);
                c.addBindingDomain(f);
            }
        }

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionWithConflict);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(3, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(1, primary.getActiveInstances().size());
        Assert.assertEquals(2, prot.getActiveInstances().size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getFeatureDao().countAll());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getRangeDao().countAll());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertEquals(1, report.getComponentsWithFeatureConflicts().size());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNotNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertEquals(2, report.getOriginalProtein().getActiveInstances().size());
        Assert.assertEquals(uniprot.getSequence(), report.getOriginalProtein().getSequence());
        Assert.assertEquals(prot.getAc(), report.getComponentsWithFeatureConflicts().keySet().iterator().next().getAc());
        Assert.assertEquals(sequence2, prot.getSequence());
        Assert.assertEquals(1, prot.getActiveInstances().size());
        Assert.assertTrue(hasAnnotation(prot, null, CvTopic.NON_UNIPROT));
        Assert.assertTrue(hasAnnotation(prot, null, CvTopic.CAUTION));

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    /**
     * Create two different proteins. Both need to be updated and are primary proteins of a same uniprot protein.
     * Create four splice variants : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * Create four chains : 2 are attached to the same parent protein and 2 other are attached to another set of parents.
     * There are duplicates because several parent proteins are primary proteins of a same uniprot entry and
     * the parents are identical for the protein transcripts.
     * Here we have the case of two set of protein transcript duplicates
     */
    public void merge_duplicate_different_sequences_range_conflicts_no_transcript_found_originalProt(){
        UniprotProtein uniprot = MockUniprotProtein.build_CDC42_HUMAN();
        uniprot.getFeatureChains().add(new UniprotFeatureChain("PRO-1", uniprot.getOrganism(), "AAACCTA"));

        TransactionStatus status = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        String sequence = "AAFFSSPPAAMMYYLLLLLAAAAAAAAAA";
        String sequence2 = "AAPPPFFFLLLMMMMM";

        Protein primary = getMockBuilder().createProtein("P60953", "primary");
        primary.setSequence(sequence);
        Protein prot = getMockBuilder().createProtein("P12345", "protein");
        prot.setSequence(sequence2);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(primary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(prot);

        Collection<Protein> primaryProteins = new ArrayList<Protein>();
        primaryProteins.add(primary);
        primaryProteins.add(prot);

        Protein random1 = getMockBuilder().createProteinRandom();
        Protein random2 = getMockBuilder().createProteinRandom();
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(random1, random2);

        Interaction interactionPrimary = getMockBuilder().createInteraction(primary, random1);
        Interaction interactionToMove = getMockBuilder().createInteraction(prot, random2);
        Interaction interactionWithConflict = getMockBuilder().createInteraction(primary, random1);

        for (Component c : interactionPrimary.getComponents()){
             c.getBindingDomains().clear();
        }
        for (Component c : interactionToMove.getComponents()){
             c.getBindingDomains().clear();
        }

        for (Component c : interactionWithConflict.getComponents()){
             c.getBindingDomains().clear();

            if (primary.getAc().equalsIgnoreCase(c.getInteractor().getAc())){
                Feature f = getMockBuilder().createFeatureRandom();
                Range r = getMockBuilder().createRange(1, 1, 50, 50);

                f.getRanges().clear();
                f.addRange(r);
                c.addBindingDomain(f);
            }
        }

        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionPrimary);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionToMove);
        IntactContext.getCurrentInstance().getCorePersister().saveOrUpdate(interactionWithConflict);

        Assert.assertEquals(4, IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().countAll());
        Assert.assertEquals(3, IntactContext.getCurrentInstance().getDaoFactory().getInteractionDao().countAll());
        Assert.assertEquals(2, primary.getActiveInstances().size());
        Assert.assertEquals(1, prot.getActiveInstances().size());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getFeatureDao().countAll());
        Assert.assertEquals(1, IntactContext.getCurrentInstance().getDaoFactory().getRangeDao().countAll());

        // fix duplicates
        DuplicatesFoundEvent evt = new DuplicatesFoundEvent(new ProteinUpdateProcessor(), IntactContext.getCurrentInstance().getDataContext(), primaryProteins, uniprot.getSequence(), uniprot.getCrc64());

        DuplicateReport report = duplicateFixer.fixProteinDuplicates(evt);

        Assert.assertNotNull(report.getOriginalProtein());
        Assert.assertEquals(1, report.getComponentsWithFeatureConflicts().size());

        Assert.assertEquals(primary.getAc(), report.getOriginalProtein().getAc());
        Assert.assertNull(IntactContext.getCurrentInstance().getDaoFactory().getProteinDao().getByAc(prot.getAc()));
        Assert.assertEquals(2, report.getOriginalProtein().getActiveInstances().size());
        Assert.assertEquals(uniprot.getSequence(), report.getOriginalProtein().getSequence());

        Protein no_uniprot = report.getComponentsWithFeatureConflicts().keySet().iterator().next();
        Assert.assertEquals(sequence, no_uniprot.getSequence());
        Assert.assertEquals(1, no_uniprot.getActiveInstances().size());
        Assert.assertTrue(hasAnnotation(no_uniprot, null, CvTopic.NON_UNIPROT));
        Assert.assertTrue(hasAnnotation(no_uniprot, null, CvTopic.CAUTION));

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(status);
    }

    private boolean hasXRef( Protein p, String primaryAc, String databaseName, String qualifierName ) {
        final Collection<InteractorXref> refs = p.getXrefs();
        boolean hasXRef = false;

        for ( InteractorXref ref : refs ) {
            if (databaseName.equalsIgnoreCase(ref.getCvDatabase().getShortLabel())){
                if (qualifierName.equalsIgnoreCase(ref.getCvXrefQualifier().getShortLabel())){
                    if (primaryAc.equalsIgnoreCase(ref.getPrimaryId())){
                        hasXRef = true;
                    }
                }
            }
        }

        return hasXRef;
    }

    private boolean hasAnnotation( Protein p, String text, String cvTopic) {
        final Collection<Annotation> annotations = p.getAnnotations();
        boolean hasAnnotation = false;

        for ( Annotation a : annotations ) {
            if (cvTopic.equalsIgnoreCase(a.getCvTopic().getShortLabel())){
                if (text == null){
                     hasAnnotation = true;
                }
                else if (text != null && a.getAnnotationText() != null){
                     if (text.equalsIgnoreCase(a.getAnnotationText())){
                         hasAnnotation = true;
                     }
                }
            }
        }

        return hasAnnotation;
    }
}
