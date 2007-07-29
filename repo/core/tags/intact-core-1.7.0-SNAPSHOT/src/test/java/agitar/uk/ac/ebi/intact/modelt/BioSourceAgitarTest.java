/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:28:58
 * Time to generate: 01:35.910 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt;
import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

import java.util.ArrayList;
import java.util.Collection;

public class BioSourceAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = BioSource.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testBioSourceShortLabel" );
        BioSource bioSource = new BioSource( owner, "testBioSourceShortLabel", "-52002" );
        assertEquals( "bioSource.xrefs.size()", 0, bioSource.xrefs.size() );
        assertEquals( "bioSource.getAliases().size()", 0, bioSource.getAliases().size() );
        assertEquals( "bioSource.getEvidences().size()", 0, bioSource.getEvidences().size() );
        assertEquals( "bioSource.shortLabel", "testBioSourceShortLa", bioSource.getShortLabel() );
        assertEquals( "bioSource.getTaxId()", "-52002", bioSource.getTaxId() );
        assertEquals( "bioSource.annotations.size()", 0, bioSource.annotations.size() );
        assertSame( "bioSource.getOwner()", owner, bioSource.getOwner() );
        assertEquals( "bioSource.references.size()", 0, bioSource.references.size() );
    }

    public void testEquals() throws Throwable {
        boolean result = new BioSource( new Institution( "testBioSourceShortLabel1" ), "testString", "-52002" ).equals( new BioSource( new Institution( "testBioSourceShortLabel" ), "\ttestString   ", "100000" ) );
        assertFalse( "result", result );
    }

//    public void testEquals1() throws Throwable {
//        BioSource bioSource = new BioSource( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testString", "-52002" );
//        BioSource o = ( BioSource ) Mockingbird.getProxyObject( BioSource.class );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( o.getAc(), "line.separator" );
//        Mockingbird.setReturnValue( o.getShortLabel(), "testString" );
//        Mockingbird.setReturnValue( o.getFullName(), null );
//        Collection arrayList = ( Collection ) Mockingbird.getProxyObject( Collection.class );
//        Mockingbird.setReturnValue( o.getXrefs(), arrayList );
//        Mockingbird.setReturnValue( true, CollectionUtils.isEqualCollection( null, arrayList ), false );
//        Mockingbird.enterTestMode();
//        boolean result = bioSource.equals( o );
//        assertFalse( "result", result );
//        assertInvoked( o, "getAc" );
//        assertInvoked( o, "getShortLabel" );
//        assertInvoked( o, "getFullName" );
//        assertInvoked( o, "getXrefs" );
//    }

    public void testEquals2() throws Throwable {
        boolean result = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).equals( new Object() );
        assertFalse( "result", result );
    }

    public void testEquals3() throws Throwable {
        BioSource o = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testGetAliases() throws Throwable {
        ArrayList result = ( ArrayList ) new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).getAliases();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetAliases1() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel2" ), "testBioSourceShortLabel1", "100000" );
        Alias alias = new BioSourceAlias( new Institution( "testBioSource\rShortLabel" ), new BioSource( new Institution( "testBioSourceShortLabel1" ), "testBioSourceShortLabel", "-52002" ), new CvAliasType( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel" ), "testBioSourceName" );
        bioSource.addAlias( ( BioSourceAlias ) alias );
        ArrayList result = ( ArrayList ) bioSource.getAliases();
        assertEquals( "result.size()", 1, result.size() );
        assertTrue( "(ArrayList) result.contains(alias)", result.contains( alias ) );
    }

    public void testGetAnnotations() throws Throwable {
        ArrayList result = ( ArrayList ) new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).getAnnotations();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetAnnotations1() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        Collection someAnnotation = new ArrayList( 100 );
        bioSource.setAnnotations( someAnnotation );
        bioSource.addAnnotation( null );
        Collection result = bioSource.getAnnotations();
        assertSame( "result", someAnnotation, result );
        assertTrue( "(ArrayList) someAnnotation.contains(null)", someAnnotation.contains( null ) );
    }

    public void testGetCvCellTypeAc() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.cvCellTypeAc = "]Z\f<@R`uIoL]";
        String result = bioSource.getCvCellTypeAc();
        assertEquals( "result", "]Z\f<@R`uIoL]", result );
    }

    public void testGetCvCellTypeAc1() throws Throwable {
        String result = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).getCvCellTypeAc();
        assertNull( "result", result );
    }

    public void testGetCvTissueAc() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.cvTissueAc = "testString";
        String result = bioSource.getCvTissueAc();
        assertEquals( "result", "testString", result );
    }

    public void testGetCvTissueAc1() throws Throwable {
        String result = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).getCvTissueAc();
        assertNull( "result", result );
    }

    public void testGetXrefs() throws Throwable {
        ArrayList result = ( ArrayList ) new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" ).getXrefs();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetXrefs1() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel2" ), "testBioSourceShortLabel", "-52002" );
        Xref aXref = new BioSourceXref( new Institution( "testBioSourceShortLabel1" ), new CvDatabase( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel" ), "testBioSourceAPrimaryId", "testBioSourceASecondaryId", "testBioSourceADatabaseRelease", new CvXrefQualifier( new Institution( "testBioSource\rShortLabel" ), "testBioSourceShortLabel" ) );
        bioSource.addXref( ( BioSourceXref ) aXref );
        ArrayList result = ( ArrayList ) bioSource.getXrefs();
        assertEquals( "result.size()", 1, result.size() );
        assertTrue( "(ArrayList) result.contains(aXref)", result.contains( aXref ) );
    }

    public void testHashCode() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        int result = bioSource.hashCode();
        assertEquals( "result", 1338796465, result );
        assertEquals( "bioSource.getTaxId()", "-52002", bioSource.getTaxId() );
    }

    public void testSetCvCellType() throws Throwable {
        CvCellType cvCellType = new CvCellType( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel" );
        BioSource bioSource = new BioSource( null, "testBioSourceShortLabel", "-52002" );
        bioSource.setCvCellType( cvCellType );
        assertSame( "bioSource.getCvCellType()", cvCellType, bioSource.getCvCellType() );
    }

    public void testSetCvCellTypeAc() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.setCvCellTypeAc( "testBioSourceAc" );
        assertEquals( "bioSource.cvCellTypeAc", "testBioSourceAc", bioSource.cvCellTypeAc );
    }

    public void testSetCvTissue() throws Throwable {
        CvTissue cvTissue = new CvTissue( new Institution( "testBioSourceShortLabel1" ), "testBioSourceShortLabel" );
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.setCvTissue( cvTissue );
        assertSame( "bioSource.getCvTissue()", cvTissue, bioSource.getCvTissue() );
    }

    public void testSetCvTissueAc() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.setCvTissueAc( "testBioSourceAc" );
        assertEquals( "bioSource.cvTissueAc", "testBioSourceAc", bioSource.cvTissueAc );
    }

    public void testSetTaxId() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        bioSource.setTaxId( "-84061" );
        assertEquals( "bioSource.getTaxId()", "-84061", bioSource.getTaxId() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new BioSource( new Institution( "testBioSourceShortLabel" ), "", "testBioSourceTaxId" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Valid BioSource must have a non-null taxId!", ex.getMessage() );
            assertThrownBy( BioSource.class, ex );
        }
    }

    public void testSetTaxIdThrowsIllegalArgumentException() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        try {
            bioSource.setTaxId( "testBioSourceTaxId" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "A BioSource's taxid has to be an integer value.", ex.getMessage() );
            assertThrownBy( BioSource.class, ex );
            assertEquals( "bioSource.getTaxId()", "-52002", bioSource.getTaxId() );
        }
    }

    public void testSetTaxIdThrowsNullPointerException() throws Throwable {
        BioSource bioSource = new BioSource( new Institution( "testBioSourceShortLabel" ), "testBioSourceShortLabel", "-52002" );
        try {
            bioSource.setTaxId( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Valid BioSource must have a non-null taxId!", ex.getMessage() );
            assertThrownBy( BioSource.class, ex );
            assertEquals( "bioSource.getTaxId()", "-52002", bioSource.getTaxId() );
        }
    }
}

