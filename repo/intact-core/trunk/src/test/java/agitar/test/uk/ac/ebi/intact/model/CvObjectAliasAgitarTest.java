/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:28:45
 * Time to generate: 00:42.971 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;


public class CvObjectAliasAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvObjectAlias.class;

//    public void testConstructor() throws Throwable {
//        Institution anOwner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        CvObject cvObject = ( CvObject ) Mockingbird.getProxyObject( CvObject.class );
//        CvAliasType cvAliasType = ( CvAliasType ) Mockingbird.getProxyObject( CvAliasType.class );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvObject.getAc(), "testString" );
//        Mockingbird.enterTestMode();
//        CvObjectAlias cvObjectAlias = new CvObjectAlias( anOwner, cvObject, cvAliasType, "31CharactersXXXXXXXXXXXXXXXXXXX" );
//        assertEquals( "cvObjectAlias.getEvidences().size()", 0, cvObjectAlias.getEvidences().size() );
//        assertSame( "cvObjectAlias.getCvAliasType()", cvAliasType, cvObjectAlias.getCvAliasType() );
//        assertEquals( "cvObjectAlias.getName()", "31CharactersXXXXXXXXXXXXXXXXXX", cvObjectAlias.getName() );
//        assertSame( "cvObjectAlias.getOwner()", anOwner, cvObjectAlias.getOwner() );
//        assertEquals( "cvObjectAlias.parentAc", "testString", getPrivateField( cvObjectAlias, "parentAc" ) );
//        assertInvoked( cvObject, "getAc" );
//    }

    public void testConstructor1() throws Throwable {
        Institution anOwner = new Institution( "testCvObjectAliasShortLabel1" );
        CvAliasType cvAliasType = new CvAliasType( new Institution( "testCvObjectAliasShortLabel" ), "testCvObjectAliasShortLabel" );
        CvObjectAlias cvObjectAlias = new CvObjectAlias( anOwner, new CvGoNode(), cvAliasType, "30CharactersXXXXXXXXXXXXXXXXXX" );
        assertEquals( "cvObjectAlias.getEvidences().size()", 0, cvObjectAlias.getEvidences().size() );
        assertSame( "cvObjectAlias.getCvAliasType()", cvAliasType, cvObjectAlias.getCvAliasType() );
        assertEquals( "cvObjectAlias.getName()", "30CharactersXXXXXXXXXXXXXXXXXX", cvObjectAlias.getName() );
        assertSame( "cvObjectAlias.getOwner()", anOwner, cvObjectAlias.getOwner() );
        assertNull( "cvObjectAlias.parentAc", getPrivateField( cvObjectAlias, "parentAc" ) );
    }

    public void testConstructor2() throws Throwable {
        Institution anOwner = new Institution( "testCvObjectAliasShortLabel" );
        CvAliasType cvAliasType = new CvAliasType( new Institution( "testCvObjectAliasShortLabel1" ), "testCvObjectAliasShortLabel" );
        CvObjectAlias cvObjectAlias = new CvObjectAlias( anOwner, new CvJournal( null, "testCvObjectAliasShortLabel" ), cvAliasType, "X X \n \r    XXXXXXXXX\rXXXXXX\nX " );
        assertEquals( "cvObjectAlias.getEvidences().size()", 0, cvObjectAlias.getEvidences().size() );
        assertSame( "cvObjectAlias.getCvAliasType()", cvAliasType, cvObjectAlias.getCvAliasType() );
        assertEquals( "cvObjectAlias.getName()", "X X \n \r    XXXXXXXXX\rXXXXXX\nX", cvObjectAlias.getName() );
        assertSame( "cvObjectAlias.getOwner()", anOwner, cvObjectAlias.getOwner() );
        assertNull( "cvObjectAlias.parentAc", getPrivateField( cvObjectAlias, "parentAc" ) );
    }

    public void testGetParent() throws Throwable {
        CvObjectAlias cvObjectAlias = new CvObjectAlias( new Institution( "testCvObjectAliasShortLabel1" ), new CvEvidenceType( new Institution( "testCvObjectAliasShortLabel" ), "testCvObjectAliasShortLabel" ), new CvAliasType( new Institution( "testCvObjectAliasShortLabel2" ), "testCvObjectAliasShortLabel" ), "testCvObjectAliasName" );
        AnnotatedObject parent = new CvInteraction( new Institution( "testCvObjectAlias\rShortLabel" ), "testCvObjectAliasShortLabel" );
        cvObjectAlias.setParent( parent );
        AnnotatedObject result = cvObjectAlias.getParent();
        assertSame( "result", parent, result );
    }

    public void testGetParent1() throws Throwable {
        AnnotatedObject result = new CvObjectAlias( null, new CvDatabase( new Institution( "testCvObjectAliasShortLabel" ), "testCvObjectAliasShortLabel" ), new CvAliasType( new Institution( "testCvObjectAliasShortLabel1" ), "testCvObjectAliasShortLabel" ), "testCvObjectAliasName" ).getParent();
        assertNull( "result", result );
    }

    public void testGetParentAc() throws Throwable {
        CvObject cvObject = new CvFeatureIdentification( new Institution( "testCvObjectAliasShortLabel" ), "testCvObjectAliasShortLabel" );
        cvObject.setAc( "testCvObjectAliasAc" );
        CvObjectAlias cvObjectAlias = new CvObjectAlias( new Institution( "testCvObjectAliasShortLabel2" ), cvObject, new CvAliasType( new Institution( "testCvObjectAliasShortLabel1" ), "testCvObjectAliasShortLabel" ), "testCvObjectAliasName" );
        String result = cvObjectAlias.getParentAc();
        assertEquals( "result", "testCvObjectAliasAc", result );
        assertNull( "cvObjectAlias.getParent()", cvObjectAlias.getParent() );
        assertEquals( "cvObjectAlias.parentAc", "testCvObjectAliasAc", getPrivateField( cvObjectAlias, "parentAc" ) );
    }

    public void testGetParentAc1() throws Throwable {
        CvObjectAlias cvObjectAlias = new CvObjectAlias( new Institution( "testCvObjectAliasShortLabel2" ), new CvIdentification( new Institution( "testCvObjectAliasShortLabel" ), "testCvObjectAliasShortLabel" ), new CvAliasType( new Institution( "testCvObjectAliasShortLabel1" ), "testCvObjectAliasShortLabel" ), "testCvObjectAliasName" );
        String result = cvObjectAlias.getParentAc();
        assertNull( "result", result );
        assertNull( "cvObjectAlias.getParent()", cvObjectAlias.getParent() );
        assertNull( "cvObjectAlias.parentAc", getPrivateField( cvObjectAlias, "parentAc" ) );
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvObjectAlias( new Institution( "testCvObjectAliasShortLabel" ), null, new CvAliasType( new Institution( "testCvObjectAliasShortLabel1" ), "testCvObjectAliasShortLabel" ), "testCvObjectAliasName" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( Alias.class, ex );
        }
    }
}

