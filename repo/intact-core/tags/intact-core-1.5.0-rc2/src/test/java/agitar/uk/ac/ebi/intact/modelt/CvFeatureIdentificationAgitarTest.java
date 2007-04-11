/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:23:44
 * Time to generate: 00:07.821 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class CvFeatureIdentificationAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvFeatureIdentification.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvFeatureIdentificationShortLabel" );
        CvFeatureIdentification cvFeatureIdentification = new CvFeatureIdentification( owner, "testCvFeatureIdentificationShortLabel" );
        assertEquals( "cvFeatureIdentification.xrefs.size()", 0, cvFeatureIdentification.xrefs.size() );
        assertEquals( "cvFeatureIdentification.getAliases().size()", 0, cvFeatureIdentification.getAliases().size() );
        assertEquals( "cvFeatureIdentification.getEvidences().size()", 0, cvFeatureIdentification.getEvidences().size() );
        assertEquals( "cvFeatureIdentification.shortLabel", "testCvFeatureIdentif", cvFeatureIdentification.getShortLabel() );
        assertEquals( "cvFeatureIdentification.getChildren().size()", 0, cvFeatureIdentification.getChildren().size() );
        assertEquals( "cvFeatureIdentification.getLeftBound()", -1L, cvFeatureIdentification.getLeftBound() );
        assertEquals( "cvFeatureIdentification.annotations.size()", 0, cvFeatureIdentification.annotations.size() );
        assertEquals( "cvFeatureIdentification.getParents().size()", 0, cvFeatureIdentification.getParents().size() );
        assertSame( "cvFeatureIdentification.getOwner()", owner, cvFeatureIdentification.getOwner() );
        assertEquals( "cvFeatureIdentification.references.size()", 0, cvFeatureIdentification.references.size() );
        assertEquals( "cvFeatureIdentification.getRightBound()", -1L, cvFeatureIdentification.getRightBound() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvFeatureIdentification( new Institution( "testCvFeatureIdentificationShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvFeatureIdentification( new Institution( "testCvFeatureIdentificationShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

