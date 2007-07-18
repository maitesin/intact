/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:29:51
 * Time to generate: 00:26.054 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt;
import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import com.agitar.lib.mockingbird.Mockingbird;

import java.util.ArrayList;
import java.util.Collection;

public class BasicObjectImplAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = BasicObjectImpl.class;

    public void testAddEvidence() throws Throwable {
        BasicObjectImpl cvAliasType = new CvAliasType( new Institution( "testBasicObjectImplShortLabel" ), "testBasicObjectImplShortLabel" );
        cvAliasType.addEvidence( new Evidence() );
        assertEquals( "(CvAliasType) cvAliasType.getEvidences().size()", 1, cvAliasType.getEvidences().size() );
    }

    public void testAddEvidence1() throws Throwable {
        BasicObjectImpl experiment = new Experiment( null, "testBasicObjectImplShortLabel", null );
        Evidence evidence = new Evidence();
        experiment.addEvidence( evidence );
        experiment.addEvidence( evidence );
        assertEquals( "(Experiment) experiment.getEvidences().size()", 1, experiment.getEvidences().size() );
    }

    public void testRemoveEvidence() throws Throwable {
        BasicObjectImpl cvDatabase = new CvDatabase( new Institution( "testBasicObjectImplShortLabel" ), "testBasicObjectImplShortLabel" );
        cvDatabase.removeEvidence( new Evidence() );
        assertEquals( "(CvDatabase) cvDatabase.getEvidences().size()", 0, cvDatabase.getEvidences().size() );
    }

    public void testSetEvidences() throws Throwable {
        Institution owner = new Institution( "testBasicObjectImplShortLabel1" );
        Collection someEvidence = new ArrayList( 100 );
        BasicObjectImpl experimentXref = new ExperimentXref( owner, new CvDatabase( new Institution( "testBasicObjectImplShortLabel" ), "testBasicObjectImplShortLabel" ), "testBasicObjectImplAPrimaryId", new CvXrefQualifier( owner, "testBasicObjectImplShortLabel" ) );
        experimentXref.setEvidences( someEvidence );
        assertSame( "(ExperimentXref) experimentXref.getEvidences()", someEvidence, experimentXref.getEvidences() );
    }

    public void testSetOwner() throws Throwable {
        Institution owner = new Institution( "testBasicObjectImplShortLabel" );
        BasicObjectImpl publication = new Publication( owner, "27326" );
        publication.setOwner( owner );
        assertSame( "(Publication) publication.getOwner()", owner, publication.getOwner() );
    }

    public void testSetOwnerAc() throws Throwable {
        BasicObjectImpl featureXref = new FeatureXref( new Institution( "testBasicObjectImplShortLabel" ), new CvDatabase( new Institution( "testBasicObjectImplShortLabel1" ), "testBasicObjectImplShortLabel" ), "testBasicObjectImplAPrimaryId", "testBasicObjectImplASecondaryId", "testBasicObjectImplADatabaseRelease", new CvXrefQualifier( new Institution( "testBasicObjectImplShortLabel2" ), "testBasicObjectImplShortLabel" ) );
        featureXref.setOwnerAc( "testBasicObjectImplAc" );
        assertEquals( "(FeatureXref) featureXref.getOwnerAc()", "testBasicObjectImplAc", featureXref.getOwnerAc() );
    }

    public void testToString() throws Throwable {
        BasicObjectImpl intactNode = new IntactNode();
        intactNode.setOwner( new Institution( "testBasicObjectImplShortLabel" ) );
        Mockingbird.enterTestMode();
        String result = intactNode.toString();
        assertEquals( "result", "null; owner=null", result );
    }

    public void testAddEvidenceThrowsNullPointerException() throws Throwable {
        BasicObjectImpl cvComponentRole = new CvExperimentalRole( null, "testBasicObjectImplShortLabel" );
        cvComponentRole.setEvidences( null );
        try {
            cvComponentRole.addEvidence( new Evidence() );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( BasicObjectImpl.class, ex );
            assertNull( "(CvComponentRole) cvComponentRole.getEvidences()", cvComponentRole.getEvidences() );
        }
    }

    public void testRemoveEvidenceThrowsNullPointerException() throws Throwable {
        BasicObjectImpl intactNode = new IntactNode();
        Evidence evidence = new Evidence();
        intactNode.setEvidences( null );
        Mockingbird.enterTestMode();
        try {
            intactNode.removeEvidence( evidence );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( BasicObjectImpl.class, ex );
            assertNull( "(IntactNode) intactNode.getEvidences()", intactNode.getEvidences() );
        }
    }

    public void testToStringThrowsNullPointerException() throws Throwable {
        BasicObjectImpl intactNode = new IntactNode();
        Mockingbird.enterTestMode();
        try {
            intactNode.toString();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( BasicObjectImpl.class, ex );
        }
    }
}

