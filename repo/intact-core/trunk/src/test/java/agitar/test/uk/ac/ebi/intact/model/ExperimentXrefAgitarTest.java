/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:23:51
 * Time to generate: 00:47.087 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;
import com.agitar.lib.mockingbird.Mockingbird;


public class ExperimentXrefAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = ExperimentXref.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testExperimentXrefShortLabel" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" );
        ExperimentXref experimentXref = new ExperimentXref( owner, aDatabase, "testExperimentXrefAPrimaryId1", "", "testExperimentXrefADatabaseRelease", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "testExperimentXrefAPrimaryId1", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertEquals( "experimentXref.getDbRelease()", "testExperimentXrefADatabaseRelease", experimentXref.getDbRelease() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getOwner()", owner, experimentXref.getOwner() );
        assertEquals( "experimentXref.getSecondaryId()", "", experimentXref.getSecondaryId() );
    }

    public void testConstructor1() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testExperimentXrefShortLabel" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" );
        ExperimentXref experimentXref = new ExperimentXref( owner, aDatabase, "X XX\nX  XXXXXXXX XX XXXXXXX XX ", "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "X XX\nX  XXXXXXXX XX XXXXXXX XX", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertEquals( "experimentXref.getDbRelease()", "testExperimentXrefADatabaseRelease", experimentXref.getDbRelease() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getOwner()", owner, experimentXref.getOwner() );
        assertEquals( "experimentXref.getSecondaryId()", "testExperimentXrefASecondaryId", experimentXref.getSecondaryId() );
    }

    public void testConstructor2() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" );
        Institution anOwner = new Institution( "testExperimentXrefShortLabel1" );
        CvDatabase aDatabase = new CvDatabase( new Institution( "testExperimentXrefShortLabel2" ), "testExperimentXrefShortLabel" );
        ExperimentXref experimentXref = new ExperimentXref( anOwner, aDatabase, "\tXXXX XXXXXXXXXXXXXX\tXXXXXXXXXXX", "testExperimentXrefASecondaryId1", "testExperimentXrefADatabaseRelease", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "XXXX XXXXXXXXXXXXXX\tXXXXXXXXXX", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertEquals( "experimentXref.getDbRelease()", "testExperimentXrefADatabaseRelease", experimentXref.getDbRelease() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getOwner()", anOwner, experimentXref.getOwner() );
        assertEquals( "experimentXref.getSecondaryId()", "testExperimentXrefASecondaryId", experimentXref.getSecondaryId() );
    }

    public void testConstructor3() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" );
        Institution anOwner = new Institution( "testExperimentXrefShortLabel2" );
        ExperimentXref experimentXref = new ExperimentXref( anOwner, aDatabase, "X XX XXXX XXX XXXXXX  XX X X X ", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "X XX XXXX XXX XXXXXX  XX X X X", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getOwner()", anOwner, experimentXref.getOwner() );
    }

    public void testConstructor4() throws Throwable {
        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
        Mockingbird.enterTestMode();
        ExperimentXref experimentXref = new ExperimentXref( anOwner, aDatabase, "testExperimentXrefAPrimaryId1", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "testExperimentXrefAPrimaryId1", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getOwner()", anOwner, experimentXref.getOwner() );
    }

    public void testConstructor5() throws Throwable {
        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
        Mockingbird.enterTestMode();
        ExperimentXref experimentXref = new ExperimentXref( anOwner, aDatabase, "1", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "1", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getOwner()", anOwner, experimentXref.getOwner() );
    }

    public void testConstructor6() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" );
        Institution anOwner = new Institution( "testExperimentXrefShortLabel1" );
        CvDatabase aDatabase = new CvDatabase( new Institution( "testExperimentXrefShortLabel2" ), "testExperimentXrefShortLabel" );
        ExperimentXref experimentXref = new ExperimentXref( anOwner, aDatabase, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\r\t\r", aCvXrefQualifier );
        assertEquals( "experimentXref.getPrimaryId()", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", experimentXref.getPrimaryId() );
        assertEquals( "experimentXref.getEvidences().size()", 0, experimentXref.getEvidences().size() );
        assertSame( "experimentXref.getCvDatabase()", aDatabase, experimentXref.getCvDatabase() );
        assertSame( "experimentXref.getCvXrefQualifier()", aCvXrefQualifier, experimentXref.getCvXrefQualifier() );
        assertSame( "experimentXref.getOwner()", anOwner, experimentXref.getOwner() );
    }

    public void testGetParent() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        AnnotatedObject result = new ExperimentXref( owner, new CvDatabase( owner, "testExperimentXrefShortLabel" ), "testExperimentXrefAPrimaryId", "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" ) ).getParent();
        assertNull( "result", result );
    }

    public void testGetParent1() throws Throwable {
        ExperimentXref experimentXref = new ExperimentXref( new Institution( "testExperimentXrefShortLabel2" ), new CvDatabase( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" ), "testExperimentXrefAPrimaryId", "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" ) );
        AnnotatedObject parent = new CvGoNode();
        experimentXref.setParent( parent );
        AnnotatedObject result = experimentXref.getParent();
        assertSame( "result", parent, result );
    }

    public void testGetParentAc() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        ExperimentXref experimentXref = new ExperimentXref( owner, new CvDatabase( owner, "testExperimentXrefShortLabel" ), "testExperimentXrefAPrimaryId", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" ) );
        String result = experimentXref.getParentAc();
        assertNull( "result", result );
        assertNull( "experimentXref.parentAc", getPrivateField( experimentXref, "parentAc" ) );
        assertNull( "experimentXref.getParent()", experimentXref.getParent() );
    }

    public void testGetParentAc1() throws Throwable {
        ExperimentXref experimentXref = new ExperimentXref( new Institution( "testExperimentXrefShortLabel1" ), new CvDatabase( new Institution( "testExperimentXrefShortLabel2" ), "testExperimentXrefShortLabel" ), "testExperimentXrefAPrimaryId", "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" ) );
        experimentXref.setParentAc( "testExperimentXrefParentAc" );
        String result = experimentXref.getParentAc();
        assertEquals( "result", "testExperimentXrefParentAc", result );
        assertEquals( "experimentXref.parentAc", "testExperimentXrefParentAc", getPrivateField( experimentXref, "parentAc" ) );
        assertNull( "experimentXref.getParent()", experimentXref.getParent() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new ExperimentXref( new Institution( "testExperimentXrefShortLabel2" ), new CvDatabase( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" ), "", "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty primaryId for an Xref.", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsIllegalArgumentException1() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        CvDatabase aDatabase = new CvDatabase( owner, "testExperimentXrefShortLabel" );
        try {
            new ExperimentXref( owner, aDatabase, "", new CvXrefQualifier( owner, "testExperimentXrefShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty primaryId for an Xref.", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new ExperimentXref( new Institution( "testExperimentXrefShortLabel2" ), new CvDatabase( new Institution( "testExperimentXrefShortLabel" ), "testExperimentXrefShortLabel" ), null, "testExperimentXrefASecondaryId", "testExperimentXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testExperimentXrefShortLabel1" ), "testExperimentXrefShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have a primary ID!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException1() throws Throwable {
        Institution owner = new Institution( "testExperimentXrefShortLabel" );
        try {
            new ExperimentXref( owner, new CvDatabase( null, "testExperimentXrefShortLabel" ), null, new CvXrefQualifier( owner, "testExperimentXrefShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have a primary ID!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }
}

