package uk.ac.ebi.intact.dbupdate.cv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import psidev.psi.tools.ontology_manager.impl.local.OntologyLoaderException;
import uk.ac.ebi.intact.bridges.ontology_manager.interfaces.IntactOntologyAccess;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.model.*;

import java.io.IOException;

/**
 * Unit tester of the CvUpdater
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/11/11</pre>
 */
@ContextConfiguration(locations = {"classpath*:/META-INF/intact.spring.xml",
        "classpath*:/META-INF/standalone/*-standalone.spring.xml"})
public class CvUpdaterTest extends IntactBasicTestCase{

    private CvUpdateManager cvManager;

    @Before
    public void clear() throws IOException, OntologyLoaderException {
        cvManager = new CvUpdateManager(CvUpdaterTest.class.getResource("/ontologies.xml"), "targets/reports");
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    public void test_update_wrongIdentifier_xrefsAdded_annotationsAdded_parentsMissing(){
        TransactionStatus status = getDataContext().beginTransaction();

        CvDagObject cv = getMockBuilder().createCvObject(CvTopic.class, CvTopic.COMMENT_MI_REF, CvTopic.COMMENT);
        getCorePersister().saveOrUpdate(cv);

        cv.setIdentifier("IA:xxxxx");
        getCorePersister().saveOrUpdate(cv);

        getDataContext().commitTransaction(status);

        IntactOntologyAccess access = cvManager.getIntactOntologyManager().getOntologyAccess("MI");

        TransactionStatus status2 = getDataContext().beginTransaction();

        CvDagObject term = getDaoFactory().getCvObjectDao(CvDagObject.class).getByAc(cv.getAc());

        CvUpdateContext context = new CvUpdateContext(this.cvManager);
        context.setOntologyAccess(access);
        context.setOntologyTerm(access.getTermForAccession(CvTopic.COMMENT_MI_REF));
        context.setIdentifier(CvTopic.COMMENT_MI_REF);
        context.setCvTerm(term);
        context.setIdentityXref(cv.getXrefs().iterator().next());

        cvManager.getCvUpdater().updateTerm(context);

        getDataContext().commitTransaction(status2);

        Assert.assertEquals(CvTopic.COMMENT_MI_REF, term.getIdentifier());
        Assert.assertEquals(2, term.getXrefs().size());

        for (CvObjectXref ref : term.getXrefs()){
            if (ref.getPrimaryId().equals("14755292")){
                Assert.assertEquals(CvDatabase.PUBMED_MI_REF, ref.getCvDatabase().getIdentifier());
                Assert.assertEquals(CvXrefQualifier.PRIMARY_REFERENCE_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            }
            else if (ref.getPrimaryId().equals(CvTopic.COMMENT_MI_REF)){
                Assert.assertEquals(CvDatabase.PSI_MI_MI_REF, ref.getCvDatabase().getIdentifier());
                Assert.assertEquals(CvXrefQualifier.IDENTITY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            }
            else {
                Assert.assertTrue(false);
            }
        }

        Assert.assertEquals(1, term.getAnnotations().size());
        Annotation def = term.getAnnotations().iterator().next();
        Assert.assertEquals(def.getCvTopic().getShortLabel(), CvTopic.DEFINITION);
        Assert.assertEquals(def.getAnnotationText(), "Comment for public view. This attribute can be associated to interaction, experiment, CV term, an organism and any participant.");

        Assert.assertTrue(term.getAliases().isEmpty());

        Assert.assertEquals(6, cvManager.getCvUpdater().getMissingParents().size());

        for (String t : cvManager.getCvUpdater().getMissingParents().keySet()){
            if (!"MI:0664".equals(t) && !"MI:0665".equals(t) && !"MI:0666".equals(t) && !"MI:0667".equals(t)
                    && !"MI:0668".equals(t) && !"MI:0669".equals(t)){
                Assert.assertTrue(false);
            }
        }
    }

    @Test
    @DirtiesContext
    @Transactional(propagation = Propagation.NEVER)
    public void test_update_noIdentityXref_xrefsRemoved_aliasRemoved_annotationsRemoved_existingParent(){
        TransactionStatus status = getDataContext().beginTransaction();

        CvDagObject parent = getMockBuilder().createCvObject(CvTopic.class, "MI:0664", "interaction attribute name");
        CvDagObject cv = getMockBuilder().createCvObject(CvTopic.class, CvTopic.COMMENT_MI_REF, CvTopic.COMMENT);
        cv.getXrefs().clear();

        cv.getAliases().add(getMockBuilder().createAlias(cv, "test", null, "go-synonyms"));
        cv.getXrefs().add(getMockBuilder().createXref(cv, "12345",
                getMockBuilder().createCvObject(CvXrefQualifier.class, CvXrefQualifier.PRIMARY_REFERENCE_MI_REF, CvXrefQualifier.PRIMARY_REFERENCE),
                getMockBuilder().createCvObject(CvDatabase.class, CvDatabase.PUBMED_MI_REF, CvDatabase.PUBMED)));
        cv.getAnnotations().add(getMockBuilder().createAnnotation("bla bla", CvTopic.COMMENT_MI_REF, CvTopic.COMMENT));
        cv.getAnnotations().add(getMockBuilder().createAnnotation("InteractionImpl", getDaoFactory().getCvObjectDao(CvTopic.class).getByShortLabel(CvTopic.USED_IN_CLASS)));

        cv.addParent(parent);
        getCorePersister().saveOrUpdate(parent, cv);

        getDataContext().commitTransaction(status);

        IntactOntologyAccess access = cvManager.getIntactOntologyManager().getOntologyAccess("MI");

        TransactionStatus status2 = getDataContext().beginTransaction();

        CvDagObject term = getDaoFactory().getCvObjectDao(CvDagObject.class).getByAc(cv.getAc());

        CvUpdateContext context = new CvUpdateContext(this.cvManager);
        context.setOntologyAccess(access);
        context.setOntologyTerm(access.getTermForAccession(CvTopic.COMMENT_MI_REF));
        context.setIdentifier(CvTopic.COMMENT_MI_REF);
        context.setCvTerm(term);

        cvManager.getCvUpdater().updateTerm(context);

        getDataContext().commitTransaction(status2);

        Assert.assertEquals(2, term.getXrefs().size());

        for (CvObjectXref ref : term.getXrefs()){
            if (ref.getPrimaryId().equals("14755292")){
                Assert.assertEquals(CvDatabase.PUBMED_MI_REF, ref.getCvDatabase().getIdentifier());
                Assert.assertEquals(CvXrefQualifier.PRIMARY_REFERENCE_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            }
            else if (ref.getPrimaryId().equals(CvTopic.COMMENT_MI_REF)){
                Assert.assertEquals(CvDatabase.PSI_MI_MI_REF, ref.getCvDatabase().getIdentifier());
                Assert.assertEquals(CvXrefQualifier.IDENTITY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            }
            else {
                Assert.assertTrue(false);
            }
        }

        Assert.assertEquals(2, term.getAnnotations().size());

        for (Annotation ann : term.getAnnotations()){
            if (ann.getCvTopic().getShortLabel().equalsIgnoreCase(CvTopic.DEFINITION)){
                Assert.assertEquals(ann.getAnnotationText(), "Comment for public view. This attribute can be associated to interaction, experiment, CV term, an organism and any participant.");
            }
            else if (ann.getCvTopic().getShortLabel().equalsIgnoreCase(CvTopic.USED_IN_CLASS)){
                Assert.assertEquals(ann.getAnnotationText(), "InteractionImpl");
            }
            else {
                Assert.assertTrue(false);
            }
        }

        Assert.assertTrue(term.getAliases().isEmpty());

        Assert.assertEquals(5, cvManager.getCvUpdater().getMissingParents().size());

        for (String t : cvManager.getCvUpdater().getMissingParents().keySet()){
            if (!"MI:0665".equals(t) && !"MI:0666".equals(t) && !"MI:0667".equals(t)
                    && !"MI:0668".equals(t) && !"MI:0669".equals(t)){
                Assert.assertTrue(false);
            }
        }

        Assert.assertEquals(0, getDaoFactory().getAliasDao(CvObjectAlias.class).getAll().size());
    }
}
