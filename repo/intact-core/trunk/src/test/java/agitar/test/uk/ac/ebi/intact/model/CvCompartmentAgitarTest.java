/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:26:23
 * Time to generate: 00:07.676 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvCompartmentAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvCompartment.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvCompartmentShortLabel" );
        CvCompartment cvCompartment = new CvCompartment( owner, "testCvCompartmentShortLabel" );
        assertEquals( "cvCompartment.xrefs.size()", 0, cvCompartment.xrefs.size() );
        assertEquals( "cvCompartment.getAliases().size()", 0, cvCompartment.getAliases().size() );
        assertEquals( "cvCompartment.getEvidences().size()", 0, cvCompartment.getEvidences().size() );
        assertEquals( "cvCompartment.shortLabel", "testCvCompartmentSho", cvCompartment.shortLabel );
        assertEquals( "cvCompartment.annotations.size()", 0, cvCompartment.annotations.size() );
        assertSame( "cvCompartment.getOwner()", owner, cvCompartment.getOwner() );
        assertEquals( "cvCompartment.references.size()", 0, cvCompartment.references.size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvCompartment( new Institution( "testCvCompartmentShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvCompartment( new Institution( "testCvCompartmentShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

