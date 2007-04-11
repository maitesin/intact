/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:25:14
 * Time to generate: 01:53.235 seconds
 *
 */

package agitar.uk.ac.ebi.intact.model;

import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;

import java.util.ArrayList;
import java.util.Collection;

public class CvDagObjectAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvDagObject.class;

    public void testAddChild() throws Throwable {
        CvDagObject cvDagObject = new CvIdentification( null, "testString" );
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        cvFeatureIdentification.addChild( new CvFeatureType( null, "testString" ) );
        cvFeatureIdentification.addChild( cvDagObject );
        assertEquals( "(CvFeatureIdentification) cvFeatureIdentification.getChildren().size()", 1, cvFeatureIdentification.getChildren().size() );
    }

    public void testAddChild1() throws Throwable {
        Institution owner = new Institution( "testCvDagObjectShortLabel" );
        CvDagObject cvInteraction = new CvInteraction( new Institution( "testCvDagObjectShortLabel1" ), "testCvDagObjectShortLabel" );
        cvInteraction.addChild( new CvFeatureIdentification( owner, "testCvDagObjectShortLabel" ) );
        cvInteraction.addChild( new CvFeatureType( owner, "testCvDagObject\rShortLabel" ) );
        assertEquals( "(CvInteraction) cvInteraction.getChildren().size()", 2, cvInteraction.getChildren().size() );
    }

    public void testAddParent() throws Throwable {
        CvDagObject cvGoNode = new CvGoNode();
        cvGoNode.addParent( new CvIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" ) );
        assertEquals( "(CvGoNode) cvGoNode.getParents().size()", 1, cvGoNode.getParents().size() );
    }

    public void testAddParent1() throws Throwable {
        CvDagObject cvGoNode = new CvGoNode();
        CvDagObject cvDagObject = new CvIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        cvGoNode.addParent( cvDagObject );
        cvGoNode.addParent( cvDagObject );
        assertEquals( "(CvGoNode) cvGoNode.getParents().size()", 1, cvGoNode.getParents().size() );
    }

    public void testAncestors() throws Throwable {
        CvDagObject cvFeatureType = new CvFeatureType( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        ArrayList result = ( ArrayList ) cvFeatureType.ancestors();
        assertEquals( "result.size()", 1, result.size() );
        assertTrue( "(ArrayList) result.contains(cvFeatureType)", result.contains( cvFeatureType ) );
    }

    public void testGetRoot() throws Throwable {
        CvDagObject cvInteraction = new CvInteraction( null, "testCvDagObjectShortLabel" );
        CvDagObject result = cvInteraction.getRoot();
        assertSame( "result", cvInteraction, result );
        assertEquals( "(CvInteraction) cvInteraction.getParents().size()", 0, cvInteraction.getParents().size() );
    }

    public void testGetRoot1() throws Throwable {
        Collection parents = new ArrayList( 100 );
        CvFeatureType cvFeatureType = new CvFeatureType( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        parents.add( cvFeatureType );
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel1" ), "testCvDagObjectShortLabel" );
        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setParents", new Class[]{Collection.class}, cvFeatureIdentification, new Object[]{parents} );

//        cvFeatureIdentification.setParents( parents );
        CvFeatureType result = ( CvFeatureType ) cvFeatureIdentification.getRoot();
        assertSame( "result", cvFeatureType, result );
        assertSame( "(CvFeatureIdentification) cvFeatureIdentification.getParents()", parents, cvFeatureIdentification.getParents() );
    }

    public void testHasChildren() throws Throwable {
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        Collection children = new ArrayList( 100 );
        boolean add = children.add( new CvInteraction( new Institution( "testCvDagObjectShortLabel1" ), "testCvDagObjectShortLabel" ) );

        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setChildren", new Class[]{Collection.class}, cvFeatureIdentification, new Object[]{children} );

//        cvFeatureIdentification.setChildren( children );
        boolean result = cvFeatureIdentification.hasChildren();
        assertTrue( "result", result );
    }

    public void testHasChildren1() throws Throwable {
        boolean result = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" ).hasChildren();
        assertFalse( "result", result );
    }

    public void testRemoveParent() throws Throwable {
        CvDagObject cvDagObject = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        CvDagObject cvDagObject2 = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel1" ), "testCvDagObjectShortLabel1" );
        cvDagObject.addChild( cvDagObject2 );
        cvDagObject2.removeParent( cvDagObject );
        assertEquals( "(CvFeatureIdentification) cvDagObject2.getParents().size()", 0, cvDagObject2.getParents().size() );
    }

    public void testSetChildren() throws Throwable {
        Collection children = new ArrayList( 100 );
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setChildren", new Class[]{Collection.class}, cvFeatureIdentification, new Object[]{children} );

//        cvFeatureIdentification.setChildren( children );
        assertSame( "(CvFeatureIdentification) cvFeatureIdentification.getChildren()", children, cvFeatureIdentification.getChildren() );
    }

    public void testSetLeftBound() throws Throwable {
        CvDagObject cvGoNode = new CvGoNode();
        cvGoNode.setLeftBound( 100L );
        assertEquals( "(CvGoNode) cvGoNode.getLeftBound()", 100L, cvGoNode.getLeftBound() );
    }

    public void testSetParents() throws Throwable {
        Collection parents = new ArrayList( 100 );
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
//        cvFeatureIdentification.setParents( parents );
        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setParents", new Class[]{Collection.class}, cvFeatureIdentification, new Object[]{parents} );

        assertSame( "(CvFeatureIdentification) cvFeatureIdentification.getParents()", parents, cvFeatureIdentification.getParents() );
    }

    public void testSetRightBound() throws Throwable {
        CvDagObject cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        cvFeatureIdentification.setRightBound( 100L );
        assertEquals( "(CvFeatureIdentification) cvFeatureIdentification.getRightBound()", 100L, cvFeatureIdentification.getRightBound() );
    }

    public void testAddParentThrowsNullPointerException() throws Throwable {
        CvDagObject cvDagObject = new CvGoNode();
        CvDagObject cvGoNode = new CvGoNode();
        cvGoNode.addParent( new CvInteractorType( null, "testCvDagObjectShortLabel" ) );
        try {
            cvGoNode.addParent( cvDagObject );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( CvObject.class, ex );
            assertEquals( "(CvGoNode) cvGoNode.getParents().size()", 2, cvGoNode.getParents().size() );
        }
    }

    public void testGetRootThrowsNullPointerException() throws Throwable {
        Collection parents = new ArrayList( 100 );
        parents.add( null );
        CvDagObject cvInteraction = new CvInteraction( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setParents", new Class[]{Collection.class}, cvInteraction, new Object[]{parents} );

//        cvInteraction.setParents( parents );
        try {
            cvInteraction.getRoot();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( CvDagObject.class, ex );
            assertSame( "(CvInteraction) cvInteraction.getParents()", parents, cvInteraction.getParents() );
        }
    }

    public void testRemoveParentThrowsNullPointerException() throws Throwable {
        Collection parents = new ArrayList( 100 );
        CvDagObject cvDagObject = new CvGoNode();
        parents.add( new CvInteractionType( new Institution( "testCvDagObjectShortLabel1" ), "testCvDagObjectShortLabel" ) );
        parents.add( cvDagObject );
        CvDagObject cvFeatureType = new CvFeatureType( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setParents", new Class[]{Collection.class}, cvFeatureType, new Object[]{parents} );

//        cvFeatureType.setParents( parents );
        try {
            cvFeatureType.removeParent( cvDagObject );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( CvObject.class, ex );
            assertSame( "(CvFeatureType) cvFeatureType.getParents()", parents, cvFeatureType.getParents() );
        }
    }

    public void testSetChildrenThrowsIllegalArgumentException() throws Throwable {
        CvDagObject cvGoNode = new CvGoNode();
        try {
            super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setChildren", new Class[]{Collection.class}, cvGoNode, new Object[]{null} );
//            cvGoNode.setChildren( null );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Children cannot be null.", ex.getMessage() );
            assertThrownBy( CvDagObject.class, ex );
            assertEquals( "(CvGoNode) cvGoNode.getChildren().size()", 0, cvGoNode.getChildren().size() );
        }
    }

    public void testSetParentsThrowsIllegalArgumentException() throws Throwable {
        CvDagObject cvInteractionType = new CvInteractionType( new Institution( "testCvDagObjectShortLabel" ), "testCvDagObjectShortLabel" );
        try {
            super.callPrivateMethod("uk.ac.ebi.intact.model.CvDagObject", "setParents", new Class[]{Collection.class}, cvInteractionType, new Object[]{null} );
//            cvInteractionType.setParents( null );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Parents cannot be null.", ex.getMessage() );
            assertThrownBy( CvDagObject.class, ex );
            assertEquals( "(CvInteractionType) cvInteractionType.getParents().size()", 0, cvInteractionType.getParents().size() );
        }
    }
}

