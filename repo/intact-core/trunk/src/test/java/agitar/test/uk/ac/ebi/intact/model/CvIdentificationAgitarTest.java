/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:26:01
 * Time to generate: 00:08.316 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvIdentificationAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvIdentification.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvIdentificationShortLabel" );
        CvIdentification cvIdentification = new CvIdentification( owner, "testCvIdentificationShortLabel" );
        assertEquals( "cvIdentification.xrefs.size()", 0, cvIdentification.xrefs.size() );
        assertEquals( "cvIdentification.getAliases().size()", 0, cvIdentification.getAliases().size() );
        assertEquals( "cvIdentification.getEvidences().size()", 0, cvIdentification.getEvidences().size() );
        assertEquals( "cvIdentification.shortLabel", "testCvIdentification", cvIdentification.shortLabel );
        assertEquals( "cvIdentification.getChildren().size()", 0, cvIdentification.getChildren().size() );
        assertEquals( "cvIdentification.getLeftBound()", -1L, cvIdentification.getLeftBound() );
        assertEquals( "cvIdentification.annotations.size()", 0, cvIdentification.annotations.size() );
        assertEquals( "cvIdentification.getParents().size()", 0, cvIdentification.getParents().size() );
        assertSame( "cvIdentification.getOwner()", owner, cvIdentification.getOwner() );
        assertEquals( "cvIdentification.references.size()", 0, cvIdentification.references.size() );
        assertEquals( "cvIdentification.getRightBound()", -1L, cvIdentification.getRightBound() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvIdentification( new Institution( "testCvIdentificationShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvIdentification( new Institution( "testCvIdentificationShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

