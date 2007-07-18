/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:33:44
 * Time to generate: 01:18.319 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import com.agitar.lib.mockingbird.Mockingbird;
import uk.ac.ebi.intact.model.util.CvObjectUtils;

public class XrefAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = Xref.class;

    public void testClone() throws Throwable {
        Xref publicationXref = new PublicationXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        Mockingbird.enterTestMode();
        PublicationXref result = ( PublicationXref ) publicationXref.clone();
        assertNull( "result.getParent()", result.getParent() );
        assertNull( "(PublicationXref) publicationXref.parentAc", getPrivateField( publicationXref, "parentAc" ) );
        assertNull( "(PublicationXref) publicationXref.getParent()", publicationXref.getParent() );
    }

    public void testEquals() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" );
        BioSourceXref o = new BioSourceXref( new Institution( "testXrefShortLabel" ), new CvDatabase( null, "testXrefShortLabel" ), "testString", aCvXrefQualifier );
        boolean result = new InteractorXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXref\rShortLabel" ), "testXrefShortLabel1" ), "testString", aCvXrefQualifier ).equals( o );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        CvDatabase cvDatabase = new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" );
        Xref o = new FeatureXref( new Institution( "testXrefShortLabel" ), null, "testString", "testXrefASecondaryId", "testXrefADatabaseRelease", null );
        o.setCvDatabase( cvDatabase );
        Xref publicationXref = new PublicationXref( null, new CvDatabase( new Institution( "testXrefShortLabel2" ), "testXrefShortLabel1" ), "testXrefAPrimaryId", null );
        publicationXref.setPrimaryId( "testString" );
        publicationXref.setCvDatabase( cvDatabase );
        boolean result = publicationXref.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        Xref interactorXref = new InteractorXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        InteractorXref o = ( InteractorXref ) Mockingbird.getProxyObject( InteractorXref.class );
        Mockingbird.enterTestMode();
        boolean result = interactorXref.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals3() throws Throwable {
        boolean result = new InteractorXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ), "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ) ).equals( "testString" );
        assertFalse( "result", result );
    }

    public void testEquals4() throws Throwable {
        Institution owner = new Institution( "testXrefShortLabel2" );
        CvDatabase aDatabase = new CvDatabase( owner, "testXrefShortLabel1" );
        CvXrefQualifier cvXrefQualifier = new CvXrefQualifier( new Institution( "testXref\rShortLabel" ), "testXrefShortLabel2" );
        Xref experimentXref = new ExperimentXref( null, aDatabase, "testString", "testXrefASecondaryId", null, new CvXrefQualifier( owner, "testXrefShortLabel1" ) );
        experimentXref.setCvXrefQualifier( cvXrefQualifier );
        Xref o = new ComponentXref( new Institution( "testXref\nShortLabel" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) );
        o.setCvXrefQualifier( cvXrefQualifier );
        o.setPrimaryId( "testString" );
        o.setCvDatabase( aDatabase );
        boolean result = experimentXref.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals5() throws Throwable {
        Xref o = new ComponentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        Mockingbird.enterTestMode();
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testHasValidPrimaryId() throws Throwable {
        boolean result = new ComponentXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) ).hasValidPrimaryId();
        assertTrue( "result", result );
    }

    public void testHashCode() throws Throwable {
        CvDatabase aDatabase = new CvDatabase( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( null, "testXrefShortLabel" );
        Xref interactorXref = new InteractorXref( null, aDatabase, "testXrefAPrimaryId", aCvXrefQualifier );
        int result = interactorXref.hashCode();
        assertEquals( "result", 928040367, result );
        assertEquals( "(InteractorXref) interactorXref.getPrimaryId()", "testXrefAPrimaryId", interactorXref.getPrimaryId() );
        assertNull( "(InteractorXref) interactorXref.getDbRelease()", interactorXref.getDbRelease() );
        assertSame( "(InteractorXref) interactorXref.getCvXrefQualifier()", aCvXrefQualifier, interactorXref.getCvXrefQualifier() );
        assertSame( "(InteractorXref) interactorXref.getCvDatabase()", aDatabase, interactorXref.getCvDatabase() );
    }

    public void testHashCode1() throws Throwable {
        Institution owner = new Institution( "testXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testXrefShortLabel" );
        Xref cvObjectXref = new CvObjectXref( owner, aDatabase, "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ) );
        cvObjectXref.setCvXrefQualifier( null );
        int result = cvObjectXref.hashCode();
        assertEquals( "result", 1699909344, result );
        assertEquals( "(CvObjectXref) cvObjectXref.getPrimaryId()", "testXrefAPrimaryId", cvObjectXref.getPrimaryId() );
        assertNull( "(CvObjectXref) cvObjectXref.getDbRelease()", cvObjectXref.getDbRelease() );
        assertNull( "(CvObjectXref) cvObjectXref.getCvXrefQualifier()", cvObjectXref.getCvXrefQualifier() );
        assertSame( "(CvObjectXref) cvObjectXref.getCvDatabase()", aDatabase, cvObjectXref.getCvDatabase() );
    }

    public void testSetCvDatabase() throws Throwable {
        CvDatabase cvDatabase = new CvDatabase( null, "testXrefShortLabel1" );
        Xref componentXref = new ComponentXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) );
        componentXref.setCvDatabase( cvDatabase );
        assertSame( "(ComponentXref) componentXref.getCvDatabase()", cvDatabase, componentXref.getCvDatabase() );
    }

    public void testSetCvXrefQualifier() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" );
        Xref bioSourceXref = new BioSourceXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", aCvXrefQualifier );
        bioSourceXref.setCvXrefQualifier( aCvXrefQualifier );
        assertSame( "(BioSourceXref) bioSourceXref.getCvXrefQualifier()", aCvXrefQualifier, bioSourceXref.getCvXrefQualifier() );
    }

    public void testSetDbRelease() throws Throwable {
        Xref experimentXref = new ExperimentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", null );
        Mockingbird.enterTestMode();
        experimentXref.setDbRelease( "9Characte" );
        assertEquals( "(ExperimentXref) experimentXref.getDbRelease()", "9Characte", experimentXref.getDbRelease() );
    }

    public void testSetDbRelease1() throws Throwable {
        Institution owner = new Institution( "testXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testXrefShortLabel" );
        Xref featureXref = new FeatureXref( new Institution( "testXrefShortLabel1" ), aDatabase, "testXrefAPrimaryId", new CvXrefQualifier( owner, "testXrefShortLabel" ) );
        featureXref.setDbRelease( "10Characte" );
        assertEquals( "(FeatureXref) featureXref.getDbRelease()", "10Characte", featureXref.getDbRelease() );
    }

    public void testSetDbRelease2() throws Throwable {
        Xref publicationXref = new PublicationXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        Mockingbird.enterTestMode();
        publicationXref.setDbRelease( "11Character" );
        assertEquals( "(PublicationXref) publicationXref.getDbRelease()", "11Characte", publicationXref.getDbRelease() );
    }

    public void testSetDbRelease3() throws Throwable {
        Institution owner = new Institution( "testXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testXrefShortLabel" );
        Xref featureXref = new FeatureXref( new Institution( "testXrefShortLabel1" ), aDatabase, "testXrefAPrimaryId", new CvXrefQualifier( owner, "testXrefShortLabel" ) );
        featureXref.setDbRelease( null );
        assertNull( "(FeatureXref) featureXref.getDbRelease()", featureXref.getDbRelease() );
    }

    public void testSetParent() throws Throwable {
        Xref interactorXref = new InteractorXref( new Institution( "testXref\rShortLabel" ), new CvDatabase( new Institution( "testXrefShortLabel2" ), "testXrefShortLabel" ), "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ) );
        AnnotatedObject parent = new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel1" );
        interactorXref.setParent( parent );
        assertSame( "(InteractorXref) interactorXref.getParent()", parent, interactorXref.getParent() );
    }

    public void testSetParentAc() throws Throwable {
        Xref componentXref = new ComponentXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) );
        componentXref.setParentAc( "testXrefParentAc" );
        assertEquals( "(ComponentXref) componentXref.parentAc", "testXrefParentAc", getPrivateField( componentXref, "parentAc" ) );
    }

    public void testSetPrimaryId() throws Throwable {
        Xref componentXref = new ComponentXref( new Institution( "testXrefShortLabel1" ), new CvDatabase( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ), "testXrefAPrimaryId", null );
        componentXref.setPrimaryId( "testXrefAPrimaryId" );
        assertEquals( "(ComponentXref) componentXref.getPrimaryId()", "testXrefAPrimaryId", componentXref.getPrimaryId() );
    }

    public void testSetSecondaryId() throws Throwable {
        Xref componentXref = new ComponentXref( null, null, "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) );
        componentXref.setSecondaryId( "testXrefASecondaryId" );
        assertEquals( "(ComponentXref) componentXref.getSecondaryId()", "testXrefASecondaryId", componentXref.getSecondaryId() );
    }

//    public void testToString() throws Throwable {
//        Xref interactorXref = new InteractorXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
//        interactorXref.setCvXrefQualifier( null );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        interactorXref.setCvDatabase( cvDatabase );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), "shortLacbel" );
//        Mockingbird.enterTestMode();
//        String result = interactorXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=shortLacbel, cvXrefQualifier=-, primaryId='testXrefAPrimaryId', secondaryId='testXrefASecondaryId', dbRelease='testXrefADatabaseRelease'}", result );
//        assertInvoked( cvDatabase, "getShortLabel" );
//    }

//    public void testToString1() throws Throwable {
//        Xref experimentXref = new ExperimentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
//        experimentXref.setCvXrefQualifier( null );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        experimentXref.setCvDatabase( cvDatabase );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), "shortLacbel" );
//        Mockingbird.enterTestMode();
//        String result = experimentXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=shortLacbel, cvXrefQualifier=-, primaryId='testXrefAPrimaryId', secondaryId='-', dbRelease='-'}", result );
//        assertInvoked( cvDatabase, "getShortLabel" );
//    }

//    public void testToString2() throws Throwable {
//        Xref experimentXref = new ExperimentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
//        experimentXref.setCvXrefQualifier( null );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        experimentXref.setCvDatabase( cvDatabase );
//        experimentXref.setSecondaryId( "testXrefASecondaryId" );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), "MI:04/5" );
//        Mockingbird.enterTestMode();
//        String result = experimentXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=MI:04/5, cvXrefQualifier=-, primaryId='testXrefAPrimaryId', secondaryId='testXrefASecondaryId', dbRelease='-'}", result );
//        assertInvoked( cvDatabase, "getShortLabel" );
//    }

//    public void testToString3() throws Throwable {
//        Xref componentXref = new ComponentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
//        CvXrefQualifier cvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        componentXref.setCvXrefQualifier( cvXrefQualifier );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        componentXref.setCvDatabase( cvDatabase );
//        componentXref.setSecondaryId( "testXrefASecondaryId" );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), "\u0014<%X}-H+k60\u0001]U$\n\u0013c1a" );
//        Mockingbird.setReturnValue( cvXrefQualifier.getShortLabel(), "testString" );
//        Mockingbird.enterTestMode();
//        String result = componentXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=\u0014<%X}-H+k60\u0001]U$\n\u0013c1a, cvXrefQualifier=testString, primaryId='testXrefAPrimaryId', secondaryId='testXrefASecondaryId', dbRelease='-'}", result );
//        assertInvoked( cvDatabase, "getShortLabel" );
//        assertInvoked( cvXrefQualifier, "getShortLabel" );
//    }

    public void testToString4() throws Throwable {
        String result = new BioSourceXref( new Institution( "testXrefShortLabel2" ), new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ) ).toString();
        assertEquals( "result", "Xref{cvDatabase=testXrefShortLabel, cvXrefQualifier=testXrefShortLabel, primaryId='testXrefAPrimaryId', secondaryId='-', dbRelease='-'}", result );
    }

//    public void testToString5() throws Throwable {
//        Xref experimentXref = new ExperimentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", null );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        experimentXref.setCvDatabase( cvDatabase );
//        experimentXref.setSecondaryId( null );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), null );
//        Mockingbird.enterTestMode();
//        String result = experimentXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=null, cvXrefQualifier=-, primaryId='testXrefAPrimaryId', secondaryId='-', dbRelease='testXrefADatabaseRelease'}", result );
//        assertInvoked( cvDatabase, "getShortLabel" );
//    }

    public void testToString6() throws Throwable {
        Institution owner = new Institution( "testXrefShortLabel" );
        String result = new FeatureXref( owner, new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" ), "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", new CvXrefQualifier( owner, "testXrefShortLabel" ) ).toString();
        assertEquals( "result", "Xref{cvDatabase=testXrefShortLabel, cvXrefQualifier=testXrefShortLabel, primaryId='testXrefAPrimaryId', secondaryId='testXrefASecondaryId', dbRelease='testXrefADatabaseRelease'}", result );
    }

//    public void testToString7() throws Throwable {
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Xref componentXref = new ComponentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", aCvXrefQualifier );
//        componentXref.setDbRelease( "testXrefADbRelease" );
//        CvDatabase cvDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        componentXref.setCvDatabase( cvDatabase );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvDatabase.getShortLabel(), "MI:04/5" );
//        Mockingbird.setReturnValue( aCvXrefQualifier.getShortLabel(), "testString" );
//        Mockingbird.enterTestMode();
//        String result = componentXref.toString();
//        assertEquals( "result", "Xref{cvDatabase=MI:04/5, cvXrefQualifier=testString, primaryId='testXrefAPrimaryId', secondaryId='-', dbRelease='testXrefAD'}", result );
//        assertInvoked( aCvXrefQualifier, "getShortLabel" );
//        assertInvoked( cvDatabase, "getShortLabel" );
//    }

    public void testHasValidPrimaryIdThrowsNullPointerException() throws Throwable {
        CvDatabase cvDatabase = new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel1" );
        cvDatabase.addAnnotation( null );
        Xref featureXref = new FeatureXref( null, new CvDatabase( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ), "testXrefAPrimaryId", new CvXrefQualifier( new Institution( "testXrefShortLabel2" ), "testXrefShortLabel" ) );
        featureXref.setCvDatabase( cvDatabase );
        try {
            featureXref.hasValidPrimaryId();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testHasValidPrimaryIdThrowsNullPointerException1() throws Throwable {
        Xref cvObjectXref = new CvObjectXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), null, "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        Mockingbird.enterTestMode();
        try {
            cvObjectXref.hasValidPrimaryId();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testHashCodeThrowsNullPointerException() throws Throwable {
        CvDatabase aDatabase = new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" );
        aCvXrefQualifier.addXref( new CvObjectXref( null, null, "testXrefAPrimaryId", null ) );
        Xref componentXref = new ComponentXref( new Institution( "testXrefShortLabel2" ), aDatabase, "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", aCvXrefQualifier );
        try {
            componentXref.hashCode();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "cvObject should not be null", ex.getMessage() );
            assertThrownBy( CvObjectUtils.class, ex );
            assertEquals( "(ComponentXref) componentXref.getPrimaryId()", "testXrefAPrimaryId", componentXref.getPrimaryId() );
            assertSame( "(ComponentXref) componentXref.getCvDatabase()", aDatabase, componentXref.getCvDatabase() );
            assertEquals( "(ComponentXref) componentXref.getDbRelease()", "testXrefADatabaseRelease", componentXref.getDbRelease() );
            assertSame( "(ComponentXref) componentXref.getCvXrefQualifier()", aCvXrefQualifier, componentXref.getCvXrefQualifier() );
        }
    }

    public void testSetCvDatabaseThrowsNullPointerException() throws Throwable {
        CvDatabase aDatabase = new CvDatabase( new Institution( "testXrefShortLabel1" ), "testXrefShortLabel" );
        Xref experimentXref = new ExperimentXref( new Institution( "testXrefShortLabel" ), aDatabase, "testXrefAPrimaryId", "testXrefASecondaryId", "testXrefADatabaseRelease", null );
        try {
            experimentXref.setCvDatabase( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have non-null database details!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
            assertSame( "(ExperimentXref) experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        }
    }

    public void testSetPrimaryIdThrowsIllegalArgumentException() throws Throwable {
        Xref cvObjectXref = new CvObjectXref( new Institution( "testXrefShortLabel1" ), new CvDatabase( new Institution( "testXrefShortLabel" ), "testXrefShortLabel" ), "testXrefAPrimaryId", null );
        try {
            cvObjectXref.setPrimaryId( "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty primaryId for an Xref.", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
            assertEquals( "(CvObjectXref) cvObjectXref.getPrimaryId()", "testXrefAPrimaryId", cvObjectXref.getPrimaryId() );
        }
    }

    public void testSetPrimaryIdThrowsNullPointerException() throws Throwable {
        Xref experimentXref = new ExperimentXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testXrefAPrimaryId", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
        Mockingbird.enterTestMode();
        try {
            experimentXref.setPrimaryId( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have a primary ID!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
            assertEquals( "(ExperimentXref) experimentXref.getPrimaryId()", "testXrefAPrimaryId", experimentXref.getPrimaryId() );
        }
    }

    public void testToStringThrowsNullPointerException() throws Throwable {
        try {
            new CvObjectXref( null, null, "testXrefAPrimaryId", null ).toString();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }
}

