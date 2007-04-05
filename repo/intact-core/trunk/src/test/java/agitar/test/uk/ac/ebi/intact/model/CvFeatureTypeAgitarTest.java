/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:27:50
 * Time to generate: 00:07.671 seconds
 *
 */

package uk.ac.ebi.intact.model;

import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;
import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;

public class CvFeatureTypeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvFeatureType.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvFeatureTypeShortLabel" );
        CvFeatureType cvFeatureType = new CvFeatureType( owner, "testCvFeatureTypeShortLabel" );
        assertEquals( "cvFeatureType.xrefs.size()", 0, cvFeatureType.xrefs.size() );
        assertEquals( "cvFeatureType.getAliases().size()", 0, cvFeatureType.getAliases().size() );
        assertEquals( "cvFeatureType.getEvidences().size()", 0, cvFeatureType.getEvidences().size() );
        assertEquals( "cvFeatureType.shortLabel", "testCvFeatureTypeSho", cvFeatureType.shortLabel );
        assertEquals( "cvFeatureType.getChildren().size()", 0, cvFeatureType.getChildren().size() );
        assertEquals( "cvFeatureType.getLeftBound()", -1L, cvFeatureType.getLeftBound() );
        assertEquals( "cvFeatureType.annotations.size()", 0, cvFeatureType.annotations.size() );
        assertEquals( "cvFeatureType.getParents().size()", 0, cvFeatureType.getParents().size() );
        assertSame( "cvFeatureType.getOwner()", owner, cvFeatureType.getOwner() );
        assertEquals( "cvFeatureType.references.size()", 0, cvFeatureType.references.size() );
        assertEquals( "cvFeatureType.getRightBound()", -1L, cvFeatureType.getRightBound() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvFeatureType( new Institution( "testCvFeatureTypeShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvFeatureType( new Institution( "testCvFeatureTypeShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

