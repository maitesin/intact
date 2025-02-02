/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:30:20
 * Time to generate: 00:07.774 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvProteinFormAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvProteinForm.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvProteinFormShortLabel" );
        CvProteinForm cvProteinForm = new CvProteinForm( owner, "testCvProteinFormShortLabel" );
        assertEquals( "cvProteinForm.xrefs.size()", 0, cvProteinForm.xrefs.size() );
        assertEquals( "cvProteinForm.getAliases().size()", 0, cvProteinForm.getAliases().size() );
        assertEquals( "cvProteinForm.getEvidences().size()", 0, cvProteinForm.getEvidences().size() );
        assertEquals( "cvProteinForm.shortLabel", "testCvProteinFormSho", cvProteinForm.getShortLabel() );
        assertEquals( "cvProteinForm.annotations.size()", 0, cvProteinForm.annotations.size() );
        assertSame( "cvProteinForm.getOwner()", owner, cvProteinForm.getOwner() );
        assertEquals( "cvProteinForm.references.size()", 0, cvProteinForm.references.size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvProteinForm( new Institution( "testCvProteinFormShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvProteinForm( new Institution( "testCvProteinFormShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

