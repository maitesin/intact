/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:28:13
 * Time to generate: 00:07.563 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvEvidenceTypeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvEvidenceType.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvEvidenceTypeShortLabel" );
        CvEvidenceType cvEvidenceType = new CvEvidenceType( owner, "testCvEvidenceTypeShortLabel" );
        assertEquals( "cvEvidenceType.xrefs.size()", 0, cvEvidenceType.xrefs.size() );
        assertEquals( "cvEvidenceType.getAliases().size()", 0, cvEvidenceType.getAliases().size() );
        assertEquals( "cvEvidenceType.getEvidences().size()", 0, cvEvidenceType.getEvidences().size() );
        assertEquals( "cvEvidenceType.shortLabel", "testCvEvidenceTypeSh", cvEvidenceType.shortLabel );
        assertEquals( "cvEvidenceType.annotations.size()", 0, cvEvidenceType.annotations.size() );
        assertSame( "cvEvidenceType.getOwner()", owner, cvEvidenceType.getOwner() );
        assertEquals( "cvEvidenceType.references.size()", 0, cvEvidenceType.references.size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvEvidenceType( new Institution( "testCvEvidenceTypeShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvEvidenceType( new Institution( "testCvEvidenceTypeShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

