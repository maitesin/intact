/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:32:14
 * Time to generate: 00:24.460 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

public class ProteinImplAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = ProteinImpl.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testProteinImplShortLabel" );
        BioSource source = new BioSource( owner, "testProteinImplShortLabel", "-8444" );
        ProteinImpl proteinImpl = new ProteinImpl( owner, source, "testProteinImplShortLabel" );
        assertSame( "proteinImpl.getBioSource()", source, proteinImpl.getBioSource() );
        assertEquals( "proteinImpl.xrefs.size()", 0, proteinImpl.xrefs.size() );
        assertEquals( "proteinImpl.getAliases().size()", 0, proteinImpl.getAliases().size() );
        assertEquals( "proteinImpl.getEvidences().size()", 0, proteinImpl.getEvidences().size() );
        assertEquals( "proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
        assertEquals( "proteinImpl.shortLabel", "testProteinImplShort", proteinImpl.getShortLabel() );
        assertEquals( "proteinImpl.annotations.size()", 0, proteinImpl.annotations.size() );
        assertSame( "proteinImpl.getOwner()", owner, proteinImpl.getOwner() );
        assertEquals( "proteinImpl.references.size()", 0, proteinImpl.references.size() );
        assertEquals( "proteinImpl.getActiveInstances().size()", 0, proteinImpl.getActiveInstances().size() );
        assertNull( "proteinImpl.getCvInteractorType()", proteinImpl.getCvInteractorType() );
    }

    public void testConstructor1() throws Throwable {
        Institution owner = new Institution( "testProteinImplShortLabel" );
        BioSource source = new BioSource( new Institution( "testProteinImplShortLabel1" ), "testProteinImplShortLabel", "-8444" );
        CvInteractorType type = new CvInteractorType( new Institution( "testProteinImplShortLabel2" ), "testProteinImplShortLabel" );
        ProteinImpl proteinImpl = new ProteinImpl( owner, source, "testProteinImplShortLabel", type );
        assertEquals( "proteinImpl.xrefs.size()", 0, proteinImpl.xrefs.size() );
        assertSame( "proteinImpl.getBioSource()", source, proteinImpl.getBioSource() );
        assertEquals( "proteinImpl.getAliases().size()", 0, proteinImpl.getAliases().size() );
        assertEquals( "proteinImpl.getEvidences().size()", 0, proteinImpl.getEvidences().size() );
        assertEquals( "proteinImpl.shortLabel", "testProteinImplShort", proteinImpl.getShortLabel() );
        assertEquals( "proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
        assertEquals( "proteinImpl.annotations.size()", 0, proteinImpl.annotations.size() );
        assertSame( "proteinImpl.getOwner()", owner, proteinImpl.getOwner() );
        assertEquals( "proteinImpl.references.size()", 0, proteinImpl.references.size() );
        assertSame( "proteinImpl.getCvInteractorType()", type, proteinImpl.getCvInteractorType() );
        assertEquals( "proteinImpl.getActiveInstances().size()", 0, proteinImpl.getActiveInstances().size() );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new ProteinImpl( new Institution( "testProteinImplShortLabel1" ), new BioSource( new Institution( "testProteinImplShortLabel" ), "testProteinImplShortLabel", "-8444" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsIllegalArgumentException1() throws Throwable {
        try {
            new ProteinImpl( new Institution( "testProteinImplShortLabel1" ), new BioSource( new Institution( "testProteinImplShortLabel" ), "testProteinImplShortLabel", "-8444" ), "", new CvInteractorType( new Institution( "testProteinImplShortLabel2" ), "testProteinImplShortLabel" ) );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        Institution owner = new Institution( "testProteinImplShortLabel" );
        try {
            new ProteinImpl( owner, new BioSource( owner, "testProteinImplShortLabel", "-8444" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException1() throws Throwable {
        try {
            new ProteinImpl( new Institution( "testProteinImplShortLabel2" ), new BioSource( new Institution( "testProteinImplShortLabel" ), "testProteinImplShortLabel", "-8444" ), null, new CvInteractorType( new Institution( "testProteinImplShortLabel1" ), "testProteinImplShortLabel" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }
}

