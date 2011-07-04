/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:30:59
 * Time to generate: 02:17.031 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class InteractorImplAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = InteractorImpl.class;

//    public void testAddActiveInstance() throws Throwable {
//        InteractorImpl interactionImpl = new InteractionImpl( ( Collection ) Mockingbird.getProxyObject( Collection.class ), null, "testIntLabel", ( Institution ) Mockingbird.getProxyObject( Institution.class ) );
//        Collection someActiveInstance = ( Collection ) Mockingbird.getProxyObject( Collection.class );
//        interactionImpl.setActiveInstances( someActiveInstance );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( false, someActiveInstance, "contains(Ljava/lang/Object;)Z", new Object[]{null}, Boolean.TRUE, 1 );
//        Mockingbird.enterTestMode();
//        interactionImpl.addActiveInstance( null );
//        assertSame( "(InteractionImpl) interactionImpl.getActiveInstances()", someActiveInstance, interactionImpl.getActiveInstances() );
//        assertInvoked( someActiveInstance, "contains", new Object[]{null} );
//    }

    public void testEquals() throws Throwable {
        boolean result = new Complex().equals( new Integer( 68 ) );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        InteractorImpl o = new Complex();
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

//    public void testGetAliases() throws Throwable {
//        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( CvInteractorType ) Mockingbird.getProxyObject( CvInteractorType.class ) );
//        Mockingbird.enterTestMode();
//        ArrayList result = ( ArrayList ) smallMoleculeImpl.getAliases();
//        assertEquals( "result.size()", 0, result.size() );
//    }

    public void testGetAnnotations() throws Throwable {
        ArrayList result = ( ArrayList ) new Complex().getAnnotations();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetAnnotations1() throws Throwable {
        InteractorImpl proteinImpl = new ProteinImpl( new Institution( "testIntLabel1" ), new BioSource( new Institution( "testIntLabel" ), "testIntLabel", "-100000" ), "testIntLabel" );
        proteinImpl.addAnnotation( null );
        ArrayList result = ( ArrayList ) proteinImpl.getAnnotations();
        assertEquals( "result.size()", 1, result.size() );
        assertTrue( "(ArrayList) result.contains(null)", result.contains( null ) );
    }

    public void testGetXrefs() throws Throwable {
        CvDatabase aDatabase = new CvDatabase( null, "testIntLabel" );
        InteractorImpl proteinImpl = new ProteinImpl( null, null, "testIntLabel", null );
        Xref aXref = new InteractorXref( new Institution( "testIntLabel" ), aDatabase, "testInteractorImplAPrimaryId", "testInteractorImplASecondaryId", "testInteractorImplADatabaseRelease", new CvXrefQualifier( null, "testIntLabel" ) );
        proteinImpl.addXref( ( InteractorXref ) aXref );
        ArrayList result = ( ArrayList ) proteinImpl.getXrefs();
        assertEquals( "result.size()", 1, result.size() );
        assertTrue( "(ArrayList) result.contains(aXref)", result.contains( aXref ) );
    }

    public void testGetXrefs1() throws Throwable {
        ArrayList result = ( ArrayList ) new Complex().getXrefs();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testHashCode() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testIntLabel" ), "testIntLabel", "-100000" );
        CvInteractorType type = new CvInteractorType( new Institution( "testIntLabel1" ), "testIntLabel" );
        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", new Institution( "testIntLabel2" ), type );
        smallMoleculeImpl.setBioSource( bioSource );
        int result = smallMoleculeImpl.hashCode();
        assertEquals( "result", 853113368, result );
        assertSame( "(SmallMoleculeImpl) smallMoleculeImpl.getBioSource()", bioSource, smallMoleculeImpl.getBioSource() );
        assertEquals( "(SmallMoleculeImpl) smallMoleculeImpl.xrefs.size()", 0, ( ( SmallMoleculeImpl ) smallMoleculeImpl ).xrefs.size() );
        assertEquals( "(SmallMoleculeImpl) smallMoleculeImpl.shortLabel", "testIntLabel", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getShortLabel() );
        assertNull( "(SmallMoleculeImpl) smallMoleculeImpl.ac", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getAc());
        assertNull( "(SmallMoleculeImpl) smallMoleculeImpl.fullName", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getFullName());
        assertSame( "(SmallMoleculeImpl) smallMoleculeImpl.getCvInteractorType()", type, smallMoleculeImpl.getCvInteractorType() );
    }

    public void testHashCode1() throws Throwable {
        InteractorImpl complex = new Complex();
        int result = complex.hashCode();
        assertEquals( "result", 24389, result );
        assertNull( "(Complex) complex.getBioSource()", complex.getBioSource() );
        assertEquals( "(Complex) complex.xrefs.size()", 0, ( ( Complex ) complex ).xrefs.size() );
        assertNull( "(Complex) complex.shortLabel", ( ( Complex ) complex ).getShortLabel() );
        assertNull( "(Complex) complex.ac", ( ( Complex ) complex ).getAc());
        assertNull( "(Complex) complex.fullName", ( ( Complex ) complex ).getFullName());
        assertNull( "(Complex) complex.getCvInteractorType()", complex.getCvInteractorType() );
    }

    public void testHashCode2() throws Throwable {
        CvInteractorType type = new CvInteractorType( new Institution( "testIntLabel" ), "testIntLabel" );
        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", new Institution( "testIntLabel1" ), type );
        int result = smallMoleculeImpl.hashCode();
        assertEquals( "result", -1053755273, result );
        assertNull( "(SmallMoleculeImpl) smallMoleculeImpl.getBioSource()", smallMoleculeImpl.getBioSource() );
        assertEquals( "(SmallMoleculeImpl) smallMoleculeImpl.xrefs.size()", 0, ( ( SmallMoleculeImpl ) smallMoleculeImpl ).xrefs.size() );
        assertEquals( "(SmallMoleculeImpl) smallMoleculeImpl.shortLabel", "testIntLabel", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getShortLabel() );
        assertNull( "(SmallMoleculeImpl) smallMoleculeImpl.ac", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getAc());
        assertNull( "(SmallMoleculeImpl) smallMoleculeImpl.fullName", ( ( SmallMoleculeImpl ) smallMoleculeImpl ).getFullName());
        assertSame( "(SmallMoleculeImpl) smallMoleculeImpl.getCvInteractorType()", type, smallMoleculeImpl.getCvInteractorType() );
    }

    public void testHashCode3() throws Throwable {
        InteractorImpl complex = new Complex();
        BioSource bioSource = new BioSource( new Institution( "testIntLabel" ), "testIntLabel", "-100000" );
        complex.setBioSource( bioSource );
        int result = complex.hashCode();
        assertEquals( "result", 1906893030, result );
        assertSame( "(Complex) complex.getBioSource()", bioSource, complex.getBioSource() );
        assertEquals( "(Complex) complex.xrefs.size()", 0, ( ( Complex ) complex ).xrefs.size() );
        assertNull( "(Complex) complex.shortLabel", ( ( Complex ) complex ).getShortLabel() );
        assertNull( "(Complex) complex.ac", ( ( Complex ) complex ).getAc());
        assertNull( "(Complex) complex.fullName", ( ( Complex ) complex ).getFullName());
        assertNull( "(Complex) complex.getCvInteractorType()", complex.getCvInteractorType() );
    }

    public void testSetActiveInstances() throws Throwable {
        Collection someActiveInstance = new ArrayList( 100 );
        Institution owner = new Institution( "testIntLabel" );
        InteractorImpl proteinImpl = new ProteinImpl( owner, new BioSource( owner, "testIntLabel", "-100000" ), "testIntLabel" );
        proteinImpl.setActiveInstances( someActiveInstance );
        assertSame( "(ProteinImpl) proteinImpl.getActiveInstances()", someActiveInstance, proteinImpl.getActiveInstances() );
    }

    public void testSetBioSource() throws Throwable {
        BioSource source = new BioSource( new Institution( "testIntLabel" ), "testIntLabel", "-100000" );
        InteractorImpl nucleicAcidImpl = new NucleicAcidImpl( ( Institution ) new Institution( "testIntLabel1" ).clone(), source, "testIntLabel", new CvInteractorType( new Institution( "testIntLabel2" ), "testIntLabel" ) );
        nucleicAcidImpl.setBioSource( source );
        assertSame( "(NucleicAcidImpl) nucleicAcidImpl.getBioSource()", source, nucleicAcidImpl.getBioSource() );
    }

    public void testSetCvInteractorType() throws Throwable {
        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", new Institution( "testIntLabel2" ), new CvInteractorType( new Institution( "testIntLabel" ), "testIntLabel" ) );
        CvInteractorType type = new CvInteractorType( new Institution( "testIntLabel1" ), "testIntLabel1" );
        smallMoleculeImpl.setCvInteractorType( type );
        assertSame( "(SmallMoleculeImpl) smallMoleculeImpl.getCvInteractorType()", type, smallMoleculeImpl.getCvInteractorType() );
    }

    public void testSetObjClass() throws Throwable {
        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", new Institution( "testIntLabel1" ), new CvInteractorType( new Institution( "testIntLabel" ), "testIntLabel" ) );
        smallMoleculeImpl.setObjClass( "testInteractorImplObjClass" );
        assertEquals( "(SmallMoleculeImpl) smallMoleculeImpl.getObjClass()", "testInteractorImplObjClass", smallMoleculeImpl.getObjClass() );
    }

    public void testToString() throws Throwable {
        InteractorImpl complex = new Complex();
        complex.setOwner( ( Institution ) new Institution( "testIntLabel" ).clone() );
        String result = complex.toString();
        assertEquals( "result", "AC: null Owner: testIntLabel-x Label: null[]", result );
    }

    public void testAddActiveInstanceThrowsNullPointerException() throws Throwable {
        InteractorImpl complex = new Complex();
        try {
            complex.addActiveInstance( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( InteractorImpl.class, ex );
            assertEquals( "(Complex) complex.getActiveInstances().size()", 1, complex.getActiveInstances().size() );
        }
    }

    public void testRemoveActiveInstanceThrowsNullPointerException() throws Throwable {
        InteractorImpl smallMoleculeImpl = new SmallMoleculeImpl( "testIntLabel", new Institution( "testIntLabel1" ), new CvInteractorType( new Institution( "testIntLabel" ), "testIntLabel" ) );
        Collection someActiveInstance = new ArrayList( 100 );
        someActiveInstance.add( null );
        smallMoleculeImpl.setActiveInstances( someActiveInstance );
        try {
            smallMoleculeImpl.removeActiveInstance( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( InteractorImpl.class, ex );
            assertSame( "(SmallMoleculeImpl) smallMoleculeImpl.getActiveInstances()", someActiveInstance, smallMoleculeImpl.getActiveInstances() );
        }
    }

    public void testSetActiveInstancesThrowsIllegalArgumentException() throws Throwable {
        InteractorImpl complex = new Complex();
        try {
            complex.setActiveInstances( null );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Active instances cannot be null.", ex.getMessage() );
            assertThrownBy( InteractorImpl.class, ex );
            assertEquals( "(Complex) complex.getActiveInstances().size()", 0, complex.getActiveInstances().size() );
        }
    }
    
    public void testToStringThrowsNullPointerException() throws Throwable {
        try {
            new Complex().toString();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( InteractorImpl.class, ex );
        }
    }
}

