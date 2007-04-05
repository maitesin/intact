/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:27:37
 * Time to generate: 01:00.278 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;


public class FeatureXrefAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = FeatureXref.class;

//    public void testConstructor() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "  X", "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "X", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertEquals( "featureXref.getDbRelease()", "testFeatureXrefADatabaseRelease", featureXref.getDbRelease() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//        assertEquals( "featureXref.getSecondaryId()", "testFeatureXrefASecondaryId", featureXref.getSecondaryId() );
//    }

    public void testConstructor1() throws Throwable {
        CvDatabase aDatabase = ( CvDatabase ) new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ).clone();
        Institution anOwner = new Institution( "testFeatureXrefShortLabel2" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel1" ), "testFeatureXrefShortLabel" );
        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "31CharactersXXXXXXXXXXXXXXXXXXX", "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", aCvXrefQualifier );
        assertEquals( "featureXref.getPrimaryId()", "31CharactersXXXXXXXXXXXXXXXXXX", featureXref.getPrimaryId() );
        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
        assertEquals( "featureXref.getDbRelease()", "testFeatureXrefADatabaseRelease", featureXref.getDbRelease() );
        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
        assertEquals( "featureXref.getSecondaryId()", "testFeatureXrefASecondaryId", featureXref.getSecondaryId() );
    }

//    public void testConstructor2() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "testFeatureXrefAPrimaryId", "XXXXXXXX\tXXXXXXXXXXX# X XXXXX\r", "testFeatureXrefADatabaseRelease", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "testFeatureXrefAPrimaryId", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertEquals( "featureXref.getDbRelease()", "testFeatureXrefADatabaseRelease", featureXref.getDbRelease() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//        assertEquals( "featureXref.getSecondaryId()", "XXXXXXXX\tXXXXXXXXXXX# X XXXXX", featureXref.getSecondaryId() );
//    }
//
//    public void testConstructor3() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "testFeatureXrefAPrimaryId", "30CharactersXXXXXXXXXXXXXXXXXX", "testFeatureXrefADatabaseRelease", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "testFeatureXrefAPrimaryId", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertEquals( "featureXref.getDbRelease()", "testFeatureXrefADatabaseRelease", featureXref.getDbRelease() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//        assertEquals( "featureXref.getSecondaryId()", "30CharactersXXXXXXXXXXXXXXXXXX", featureXref.getSecondaryId() );
//    }
//
//    public void testConstructor4() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "1", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "1", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//    }
//
//    public void testConstructor5() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "XXXXXXXXXXXX\nXXXXXXXXXXXXXXXX \r\r", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "XXXXXXXXXXXX\nXXXXXXXXXXXXXXXX", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//    }

    public void testConstructor6() throws Throwable {
        CvDatabase aDatabase = new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" );
        Institution anOwner = new Institution( "testFeatureXrefShortLabel1" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel2" ), "testFeatureXrefShortLabel" );
        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, "30CharactersXXXXXXXXXXXXXXXXXX", aCvXrefQualifier );
        assertEquals( "featureXref.getPrimaryId()", "30CharactersXXXXXXXXXXXXXXXXXX", featureXref.getPrimaryId() );
        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
    }

//    public void testConstructor7() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvDatabase aDatabase = ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class );
//        CvXrefQualifier aCvXrefQualifier = ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class );
//        Mockingbird.enterTestMode();
//        FeatureXref featureXref = new FeatureXref( anOwner, aDatabase, " \rXX XXXXXXXXXXXXX#XXXXXXXXXX\tXXX\t\t \t", aCvXrefQualifier );
//        assertEquals( "featureXref.getPrimaryId()", "XX XXXXXXXXXXXXX#XXXXXXXXXX\tXX", featureXref.getPrimaryId() );
//        assertEquals( "featureXref.getEvidences().size()", 0, featureXref.getEvidences().size() );
//        assertSame( "featureXref.getCvDatabase()", aDatabase, featureXref.getCvDatabase() );
//        assertSame( "featureXref.getCvXrefQualifier()", aCvXrefQualifier, featureXref.getCvXrefQualifier() );
//        assertSame( "featureXref.getOwner()", anOwner, featureXref.getOwner() );
//    }

    public void testGetParent() throws Throwable {
        Institution owner = new Institution( "testFeatureXrefShortLabel1" );
        CvDatabase aDatabase = new CvDatabase( owner, "testFeatureXrefShortLabel" );
        Feature result = new FeatureXref( new Institution( "testFeatureXrefShortLabel" ), aDatabase, "testFeatureXrefAPrimaryId", new CvXrefQualifier( owner, "testFeatureXrefShortLabel" ) ).getParent();
        assertNull( "result", result );
    }

    public void testGetParentAc() throws Throwable {
        FeatureXref featureXref = new FeatureXref( new Institution( "testFeatureXrefShortLabel2" ), new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ), "testFeatureXrefAPrimaryId", "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel1" ), "testFeatureXrefShortLabel" ) );
        String result = featureXref.getParentAc();
        assertNull( "result", result );
        assertNull( "featureXref.parentAc", getPrivateField( featureXref, "parentAc" ) );
        assertNull( "featureXref.getParent()", featureXref.getParent() );
    }

    public void testGetParentAc1() throws Throwable {
        FeatureXref featureXref = new FeatureXref( null, new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ), "testFeatureXrefAPrimaryId", new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel1" ), "testFeatureXrefShortLabel" ) );
        featureXref.setParentAc( "testFeatureXrefParentAc" );
        String result = featureXref.getParentAc();
        assertEquals( "result", "testFeatureXrefParentAc", result );
        assertEquals( "featureXref.parentAc", "testFeatureXrefParentAc", getPrivateField( featureXref, "parentAc" ) );
        assertNull( "featureXref.getParent()", featureXref.getParent() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new FeatureXref( new Institution( "testFeatureXrefShortLabel1" ), new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ), "", "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel2" ), "testFeatureXrefShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty primaryId for an Xref.", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsIllegalArgumentException1() throws Throwable {
        try {
            new FeatureXref( new Institution( "testFeatureXrefShortLabel2" ), new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ), "", new CvXrefQualifier( new Institution( "testFeatureXrefShortLabel1" ), "testFeatureXrefShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty primaryId for an Xref.", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new FeatureXref( new Institution( "testFeatureXrefShortLabel1" ), ( CvDatabase ) new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ).clone(), null, "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", new CvXrefQualifier( null, "testFeatureXrefShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have a primary ID!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException1() throws Throwable {
        try {
            new FeatureXref( new Institution( "testFeatureXrefShortLabel1" ), new CvDatabase( new Institution( "testFeatureXrefShortLabel" ), "testFeatureXrefShortLabel" ), null, new CvXrefQualifier( null, "testFeatureXrefShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "valid Xref must have a primary ID!", ex.getMessage() );
            assertThrownBy( Xref.class, ex );
        }
    }

//    public void testGetParentThrowsClassCastException() throws Throwable {
//        FeatureXref featureXref = new FeatureXref( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvDatabase ) Mockingbird.getProxyObject( CvDatabase.class ), "testFeatureXrefAPrimaryId", "testFeatureXrefASecondaryId", "testFeatureXrefADatabaseRelease", ( CvXrefQualifier ) Mockingbird.getProxyObject( CvXrefQualifier.class ) );
//        featureXref.setParent( ( AnnotatedObject ) Mockingbird.getProxyObject( AnnotatedObject.class ) );
//        Mockingbird.enterTestMode();
//        try {
//            featureXref.getParent();
//            fail( "Expected ClassCastException to be thrown" );
//        } catch ( ClassCastException ex ) {
//            assertEquals( "ex.getClass()", ClassCastException.class, ex.getClass() );
//            assertThrownBy( FeatureXref.class, ex );
//        }
//    }
}

