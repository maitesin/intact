package uk.ac.ebi.intact.psimitab;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.converter.xml2tab.Xml2Tab;
import psidev.psi.mi.tab.expansion.SpokeWithoutBaitExpansion;
import psidev.psi.mi.tab.model.*;
import uk.ac.ebi.intact.psimitab.model.Parameter;
import uk.ac.ebi.intact.psimitab.processor.IntactClusterInteractorPairProcessor;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;

public class IntactTabTest extends AbstractPsimitabTestCase {

    @Test
    public void binaryInteractionHandler() throws Exception {

        File xmlFile = getFileByResources( "/psi25-testset/9971739.xml", IntactTabTest.class );
        assertTrue( xmlFile.canRead() );

        // convert into Tab object model
        Xml2Tab xml2tab = new IntactXml2Tab();
        
        xml2tab.setExpansionStrategy( new SpokeWithoutBaitExpansion() );
        xml2tab.addOverrideSourceDatabase( CrossReferenceFactory.getInstance().build( "psi-mi", "MI:0469", "intact" ) );
        xml2tab.setPostProcessor( new IntactClusterInteractorPairProcessor() );

        Collection<BinaryInteraction> interactions = xml2tab.convert( xmlFile, false );

        PsimiTabWriter writer = new IntactPsimiTabWriter( false, false );

        File tabFile = new File( getTargetDirectory(), "9971739_expanded.txt" );
        assertTrue( tabFile.getParentFile().canWrite() );
        writer.write( interactions, tabFile );
        //assertEquals( 3, interactions.size() );

        for ( BinaryInteraction interaction : interactions ) {
            assertTrue( interaction instanceof IntactBinaryInteraction );
        }
    }

    @Test
    public void psimiTabReader() throws Exception {

        File tabFile = getFileByResources( "/mitab_samples/9971739_expanded.txt", IntactTabTest.class );

        boolean hasHeaderLine = true;
        PsimiTabReader reader = new IntactPsimiTabReader( hasHeaderLine );

        Collection<BinaryInteraction> bis = reader.read( tabFile );

        for ( BinaryInteraction bi : bis ) {
            IntactBinaryInteraction dbi = ( IntactBinaryInteraction ) bi;
            assertTrue( dbi.getAuthors().get( 0 ).getName().contains( "Leung" ) );
            assertTrue( dbi.hasExperimentalRolesInteractorA() );
            assertTrue( dbi.hasExperimentalRolesInteractorB() );
            assertTrue( dbi.hasPropertiesA() );
            assertTrue( dbi.hasPropertiesB() );
        }
    }

    @Test
    public void roundtrip() throws Exception {

        String mitab = "uniprotkb:P16884|intact:EBI-446344\tuniprotkb:Q60824|intact:EBI-446159\tuniprotkb:Nefh(gene name)\tuniprotkb:Dst(gene name)\tintact:Nfh\tintact:Bpag1\tpsi-mi:\"MI:0018\"(2 hybrid)\tLeung et al. (1999)\tpubmed:9971739\ttaxid:10116(rat)\ttaxid:10090(mouse)\tpsi-mi:\"MI:0218\"(physical interaction)\tpsi-mi:\"MI:0469\"(intact)\tintact:EBI-446356\tintact:high\tpsi-mi:\"MI:0498\"(prey)\tpsi-mi:\"MI:0496\"(bait)\tpsi-mi:\"MI:0499\"(unspecified role)\tpsi-mi:\"MI:0499\"(unspecified role)\tuniprotkb:O35482|rgd:3159|ensembl:ENSRNOG00000008716\tgo:\"GO:0005737\"(\"C:cytoplasm\")|interpro:IPR001589(Actbind_actnin)\tpsi-mi:\"MI:0326\"(protein)\tpsi-mi:\"MI:0326\"(protein)\tyeast:4932\tSpoke\tCancer|Apoptosis\tcomment:commentA\tcomment:commentB\tic50A:100(molar)\tic50B:200(molar)\tic50C:300(molar)";

        PsimiTabReader reader = new IntactPsimiTabReader( false );
        Collection<BinaryInteraction> bis = reader.read( mitab );
        Assert.assertNotNull( bis );
        Assert.assertEquals( 1, bis.size() );
        final IntactBinaryInteraction bi = (IntactBinaryInteraction) bis.iterator().next();

        // check all fields are present
        Assert.assertEquals(2, bi.getInteractorA().getIdentifiers().size());
        Assert.assertTrue(bi.getInteractorA().getIdentifiers().contains( new CrossReferenceImpl( "uniprotkb","P16884") ));
        Assert.assertTrue(bi.getInteractorA().getIdentifiers().contains( new CrossReferenceImpl( "intact","EBI-446344") ));
        Assert.assertEquals(1, bi.getInteractorA().getAlternativeIdentifiers().size());
        Assert.assertTrue(bi.getInteractorA().getAlternativeIdentifiers().contains( new CrossReferenceImpl( "uniprotkb","Nefh", "gene name") ));
        Assert.assertEquals(1, bi.getInteractorA().getAliases().size());
        Assert.assertTrue(bi.getInteractorA().getAliases().contains( new AliasImpl( "intact","Nfh" ) ));
        Assert.assertNotNull( bi.getInteractorA().getOrganism() );
        Assert.assertEquals( 1, bi.getInteractorA().getOrganism().getIdentifiers().size() );
        Assert.assertNotNull( bi.getInteractorA().getOrganism().getIdentifiers().contains( new CrossReferenceImpl( "taxid", "10116", "rat"  ) ));


        Assert.assertEquals(2, bi.getInteractorB().getIdentifiers().size());
        Assert.assertTrue(bi.getInteractorB().getIdentifiers().contains( new CrossReferenceImpl( "uniprotkb","Q60824") ));
        Assert.assertTrue(bi.getInteractorB().getIdentifiers().contains( new CrossReferenceImpl( "intact","EBI-446159") ));
        Assert.assertTrue(bi.getInteractorB().getAlternativeIdentifiers().contains( new CrossReferenceImpl( "uniprotkb","Dst", "gene name") ));
        Assert.assertEquals(1, bi.getInteractorB().getAliases().size());
        Assert.assertTrue(bi.getInteractorB().getAliases().contains( new AliasImpl( "intact","Bpag1" ) ));
        Assert.assertNotNull( bi.getInteractorB().getOrganism() );
        Assert.assertEquals( 1, bi.getInteractorB().getOrganism().getIdentifiers().size() );
        Assert.assertNotNull( bi.getInteractorB().getOrganism().getIdentifiers().contains( new CrossReferenceImpl( "taxid", "10090", "mouse"  ) ));        


        Assert.assertEquals( 1, bi.getDetectionMethods().size() );
        Assert.assertTrue( bi.getDetectionMethods().contains( new InteractionDetectionMethodImpl( "psi-mi", "MI:0018", "2 hybrid") ) );

        Assert.assertEquals( 1, bi.getAuthors().size() );
        Assert.assertTrue( bi.getAuthors().contains( new AuthorImpl( "Leung et al. (1999)") ) );

        Assert.assertEquals( 1, bi.getPublications().size() );
        Assert.assertTrue( bi.getPublications().contains( new CrossReferenceImpl( "pubmed", "9971739" ) ) );

        Assert.assertEquals( 1, bi.getInteractionTypes().size() );
        Assert.assertTrue( bi.getInteractionTypes().contains( new InteractionTypeImpl( "psi-mi", "MI:0218", "physical interaction" ) ) );

        Assert.assertEquals( 1, bi.getSourceDatabases().size() );
        Assert.assertTrue( bi.getSourceDatabases().contains( new CrossReferenceImpl( "psi-mi", "MI:0469", "intact" ) ) );

        Assert.assertEquals( 1, bi.getInteractionAcs().size() );
        Assert.assertTrue( bi.getInteractionAcs().contains( new CrossReferenceImpl( "intact", "EBI-446356" ) ) );

        Assert.assertEquals( 1, bi.getConfidenceValues().size() );
        Assert.assertTrue( bi.getConfidenceValues().contains( new ConfidenceImpl( "intact", "high" ) ) );

        // now onto intact specific fields

        Assert.assertEquals( 1, bi.getInteractorA().getExperimentalRoles().size() );
        Assert.assertTrue( bi.getInteractorA().getExperimentalRoles().contains( new CrossReferenceImpl( "psi-mi", "MI:0498", "prey" ) ) );

        Assert.assertEquals( 1, bi.getInteractorB().getExperimentalRoles().size() );
        Assert.assertTrue( bi.getInteractorB().getExperimentalRoles().contains( new CrossReferenceImpl( "psi-mi", "MI:0496", "bait" ) ) );

        Assert.assertEquals( 1, bi.getInteractorA().getBiologicalRoles().size() );
        Assert.assertTrue( bi.getInteractorA().getBiologicalRoles().contains( new CrossReferenceImpl( "psi-mi", "MI:0499", "unspecified role" ) ) );

        Assert.assertEquals( 1, bi.getInteractorB().getBiologicalRoles().size() );
        Assert.assertTrue( bi.getInteractorB().getBiologicalRoles().contains( new CrossReferenceImpl( "psi-mi", "MI:0499", "unspecified role" ) ) );

        Assert.assertEquals( 3, bi.getInteractorA().getProperties().size() );
        Assert.assertTrue( bi.getInteractorA().getProperties().contains( new CrossReferenceImpl( "uniprotkb", "O35482" ) ) );
        Assert.assertTrue( bi.getInteractorA().getProperties().contains( new CrossReferenceImpl( "rgd", "3159" ) ) );
        Assert.assertTrue( bi.getInteractorA().getProperties().contains( new CrossReferenceImpl( "ensembl", "ENSRNOG00000008716" ) ) );

        Assert.assertEquals( 2, bi.getInteractorB().getProperties().size() );
        Assert.assertTrue( bi.getInteractorB().getProperties().contains( new CrossReferenceImpl( "go", "GO:0005737", "C:cytoplasm" ) ) );
        Assert.assertTrue( bi.getInteractorB().getProperties().contains( new CrossReferenceImpl( "interpro", "IPR001589", "Actbind_actnin" ) ) );

        Assert.assertEquals( bi.getInteractorA().getInteractorType(), new CrossReferenceImpl( "psi-mi", "MI:0326", "protein" ) );
        Assert.assertEquals( bi.getInteractorB().getInteractorType(), new CrossReferenceImpl( "psi-mi", "MI:0326", "protein" ) );

        Assert.assertEquals( 1, bi.getHostOrganism().size() );
        Assert.assertTrue( bi.getHostOrganism().contains( new CrossReferenceImpl( "yeast", "4932" ) ) );

        Assert.assertEquals( 1, bi.getExpansionMethods().size() );
        Assert.assertTrue( bi.getExpansionMethods().contains( "Spoke" ) );

        Assert.assertEquals( 2, bi.getDataset().size() );
        Assert.assertTrue( bi.getDataset().contains( "Cancer" ) );
        Assert.assertTrue( bi.getDataset().contains( "Apoptosis" ) );

        Assert.assertEquals(1, bi.getInteractorA().getAnnotations().size());
        Assert.assertEquals("commentA", bi.getInteractorA().getAnnotations().iterator().next().getText());
        Assert.assertEquals(1, bi.getInteractorB().getAnnotations().size());
        Assert.assertEquals("commentB", bi.getInteractorB().getAnnotations().iterator().next().getText());

        Assert.assertEquals(1, bi.getInteractorA().getParameters().size());
        final Parameter parameterA = bi.getInteractorA().getParameters().iterator().next();
        Assert.assertEquals("ic50A", parameterA.getType());
        Assert.assertEquals("100", parameterA.getValue());
        Assert.assertEquals("molar", parameterA.getUnit());

        Assert.assertEquals(1, bi.getInteractorB().getParameters().size());
        final Parameter parameterB = bi.getInteractorB().getParameters().iterator().next();
        Assert.assertEquals("ic50B", parameterB.getType());
        Assert.assertEquals("200", parameterB.getValue());

        Assert.assertEquals(1, bi.getParameters().size());
        final Parameter interactionParameter = bi.getParameters().iterator().next();
        Assert.assertEquals("ic50C", interactionParameter.getType());
        Assert.assertEquals("300", interactionParameter.getValue());

        // now write it back into a String
        IntactPsimiTabWriter writer = new IntactPsimiTabWriter( false, false );
        writer.setHeaderEnabled( false );
        StringWriter sw = new StringWriter( );
        writer.write( bi, sw );
        String line = sw.getBuffer().toString();

        // a new line is automaticaly appended at the end of each interaction writen, remove it before comparing
        line = line.trim();

        Assert.assertEquals( mitab, line );
    }

    @Test
    public void expansion() throws Exception {

        File xmlFile = getFileByResources( "/psi25-testset/simple.xml", IntactTabTest.class );
        assertTrue( xmlFile.canRead() );

        // convert into Tab object model
        Xml2Tab xml2tab = new IntactXml2Tab();

        xml2tab.setExpansionStrategy( new SpokeWithoutBaitExpansion() );
        xml2tab.addOverrideSourceDatabase( CrossReferenceFactory.getInstance().build( "psi-mi", "MI:0469", "intact" ) );
        xml2tab.setPostProcessor( new IntactClusterInteractorPairProcessor() );

        Collection<BinaryInteraction> interactions = xml2tab.convert( xmlFile, false );

        PsimiTabWriter writer = new IntactPsimiTabWriter( false, false );

        StringWriter sw = new StringWriter();

        writer.write( interactions, sw );
        assertEquals( 2, interactions.size() );

        BinaryInteraction interaction = ( BinaryInteraction ) interactions.toArray()[1];
        assertTrue( interaction instanceof IntactBinaryInteraction );

        IntactBinaryInteraction ibi = ( IntactBinaryInteraction ) interaction;
        assertTrue( ibi.getAuthors().get( 0 ).getName().contains( "Liu et al." ) );

        assertTrue( ibi.getHostOrganism().size() == 2 );
        for ( CrossReference o : ibi.getHostOrganism() ) {
            assertTrue( o.getDatabase().contains( "yeast" ) );
        }

        assertTrue( ibi.getExperimentalRolesInteractorA().size() == 2 );
        assertTrue( ibi.getExperimentalRolesInteractorB().size() == 2 );

        assertTrue( ibi.getInteractorTypeA().size() == 1 );
        assertTrue( ibi.getInteractorTypeA().get( 0 ).getText().contains( "protein" ) );
        assertTrue( ibi.getInteractorTypeB().size() == 1 );
        assertTrue( ibi.getInteractorTypeB().get( 0 ).getText().contains( "protein" ) );

        assertTrue( ibi.hasPropertiesA() );
        assertTrue( ibi.hasPropertiesB() );
    }

    @Test
    public void ifAuthorIsCurator() throws Exception {
        // reading a file were all interactions inferred by currators
        File xmlFile = getFileByResources( "/psi25-testset/14681455.xml", IntactTabTest.class );
        assertTrue( xmlFile.canRead() );

        // convert into Tab object model
        Xml2Tab x2t = new IntactXml2Tab();

        x2t.setExpansionStrategy( new SpokeWithoutBaitExpansion() );
        x2t.addOverrideSourceDatabase( CrossReferenceFactory.getInstance().build( "psi-mi", "MI:0469", "intact" ) );
        x2t.setPostProcessor( new IntactClusterInteractorPairProcessor() );

        Collection<BinaryInteraction> interactions = x2t.convert( xmlFile, false );

        for ( BinaryInteraction<?> interaction : interactions ) {
            for ( Author author : interaction.getAuthors() ) {
                // if we know that all interactions inferred by currators,
                // alls authors should start with 'Curated complexes'
                assertTrue( author.getName().startsWith( "Curated complexes" ) );
            }
        }
    }
}
