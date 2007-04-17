/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:24:04
 * Time to generate: 00:07.667 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvModificationTypeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvModificationType.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvModificationTypeShortLabel" );
        CvModificationType cvModificationType = new CvModificationType( owner, "testCvModificationTypeShortLabel" );
        assertEquals( "cvModificationType.xrefs.size()", 0, cvModificationType.xrefs.size() );
        assertEquals( "cvModificationType.getAliases().size()", 0, cvModificationType.getAliases().size() );
        assertEquals( "cvModificationType.getEvidences().size()", 0, cvModificationType.getEvidences().size() );
        assertEquals( "cvModificationType.shortLabel", "testCvModificationTy", cvModificationType.getShortLabel() );
        assertEquals( "cvModificationType.annotations.size()", 0, cvModificationType.annotations.size() );
        assertSame( "cvModificationType.getOwner()", owner, cvModificationType.getOwner() );
        assertEquals( "cvModificationType.references.size()", 0, cvModificationType.references.size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvModificationType( new Institution( "testCvModificationTypeShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvModificationType( new Institution( "testCvModificationTypeShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

