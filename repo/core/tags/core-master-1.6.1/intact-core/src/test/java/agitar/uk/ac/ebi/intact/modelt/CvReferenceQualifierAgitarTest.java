/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:27:14
 * Time to generate: 00:07.608 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvReferenceQualifierAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvReferenceQualifier.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvReferenceQualifierShortLabel" );
        CvReferenceQualifier cvReferenceQualifier = new CvReferenceQualifier( "testCvReferenceQualifierShortLabel", owner );
        assertEquals( "cvReferenceQualifier.xrefs.size()", 0, cvReferenceQualifier.xrefs.size() );
        assertEquals( "cvReferenceQualifier.getAliases().size()", 0, cvReferenceQualifier.getAliases().size() );
        assertEquals( "cvReferenceQualifier.getEvidences().size()", 0, cvReferenceQualifier.getEvidences().size() );
        assertEquals( "cvReferenceQualifier.shortLabel", "testCvReferenceQuali", cvReferenceQualifier.getShortLabel() );
        assertEquals( "cvReferenceQualifier.annotations.size()", 0, cvReferenceQualifier.annotations.size() );
        assertSame( "cvReferenceQualifier.getOwner()", owner, cvReferenceQualifier.getOwner() );
        assertEquals( "cvReferenceQualifier.references.size()", 0, cvReferenceQualifier.references.size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvReferenceQualifier( "", new Institution( "testCvReferenceQualifierShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvReferenceQualifier( null, new Institution( "testCvReferenceQualifierShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

