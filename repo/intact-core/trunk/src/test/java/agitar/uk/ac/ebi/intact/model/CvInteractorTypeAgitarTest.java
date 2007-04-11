/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:26:59
 * Time to generate: 00:10.014 seconds
 *
 */

package agitar.uk.ac.ebi.intact.model; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

import java.util.List;

public class CvInteractorTypeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvInteractorType.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvInteractorTypeShortLabel" );
        CvInteractorType cvInteractorType = new CvInteractorType( owner, "testCvInteractorTypeShortLabel" );
        assertEquals( "cvInteractorType.xrefs.size()", 0, cvInteractorType.xrefs.size() );
        assertEquals( "cvInteractorType.getAliases().size()", 0, cvInteractorType.getAliases().size() );
        assertEquals( "cvInteractorType.getEvidences().size()", 0, cvInteractorType.getEvidences().size() );
        assertEquals( "cvInteractorType.shortLabel", "testCvInteractorType", cvInteractorType.getShortLabel() );
        assertEquals( "cvInteractorType.getChildren().size()", 0, cvInteractorType.getChildren().size() );
        assertEquals( "cvInteractorType.getLeftBound()", -1L, cvInteractorType.getLeftBound() );
        assertEquals( "cvInteractorType.annotations.size()", 0, cvInteractorType.annotations.size() );
        assertEquals( "cvInteractorType.getParents().size()", 0, cvInteractorType.getParents().size() );
        assertSame( "cvInteractorType.getOwner()", owner, cvInteractorType.getOwner() );
        assertEquals( "cvInteractorType.references.size()", 0, cvInteractorType.references.size() );
        assertEquals( "cvInteractorType.getRightBound()", -1L, cvInteractorType.getRightBound() );
    }

    public void testGetInteractionMI() throws Throwable {
        String result = CvInteractorType.getInteractionMI();
        assertEquals( "result", "MI:0317", result );
    }

    public void testGetNucleicAcidMIs() throws Throwable {
        List result = CvInteractorType.getNucleicAcidMIs();
        assertEquals( "result.size()", 16, result.size() );
        assertEquals( "(List) result.get(0)", "MI:0318", result.get( 0 ) );
    }

    public void testGetProteinMI() throws Throwable {
        String result = CvInteractorType.getProteinMI();
        assertEquals( "result", "MI:0326", result );
    }

    public void testGetProteinMIs() throws Throwable {
        List result = CvInteractorType.getProteinMIs();
        assertEquals( "result.size()", 2, result.size() );
        assertEquals( "(List) result.get(0)", "MI:0326", result.get( 0 ) );
    }

    public void testIsNucleicAcidMI() throws Throwable {
        boolean result = CvInteractorType.isNucleicAcidMI( "testCvInteractorTypeMi" );
        assertFalse( "result", result );
        assertEquals( "CvInteractorType.getNucleicAcidMIs().size()", 16, CvInteractorType.getNucleicAcidMIs().size() );
    }

    public void testIsNucleicAcidMI1() throws Throwable {
        boolean result = CvInteractorType.isNucleicAcidMI( "MI:0680" );
        assertTrue( "result", result );
        assertEquals( "CvInteractorType.getNucleicAcidMIs().size()", 16, CvInteractorType.getNucleicAcidMIs().size() );
    }

    public void testIsProteinMI() throws Throwable {
        boolean result = CvInteractorType.isProteinMI( "MI:0326" );
        assertTrue( "result", result );
        assertEquals( "CvInteractorType.getProteinMIs().size()", 2, CvInteractorType.getProteinMIs().size() );
    }

    public void testIsProteinMI1() throws Throwable {
        boolean result = CvInteractorType.isProteinMI( "testCvInteractorTypeMi" );
        assertFalse( "result", result );
        assertEquals( "CvInteractorType.getProteinMIs().size()", 2, CvInteractorType.getProteinMIs().size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvInteractorType( new Institution( "testCvInteractorTypeShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvInteractorType( new Institution( "testCvInteractorTypeShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

