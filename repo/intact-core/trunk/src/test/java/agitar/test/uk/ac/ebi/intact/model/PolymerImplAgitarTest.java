/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:30:23
 * Time to generate: 02:33.476 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class PolymerImplAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = PolymerImpl.class;

    public void testAddSequenceChunk() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), null, "testPolymerImplShortLabel", null );
        SequenceChunk sequenceChunk = new SequenceChunk();
        proteinImpl.addSequenceChunk( sequenceChunk );
        proteinImpl.addSequenceChunk( sequenceChunk );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
    }

    public void testAddSequenceChunk1() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.addSequenceChunk( new SequenceChunk() );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
    }

    public void testEquals() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        boolean result = proteinImpl.equals( new Integer( 1892797183 ) );
        assertFalse( "result", result );
        assertEquals( "(ProteinImpl) proteinImpl.getActiveInstances().size()", 0, proteinImpl.getActiveInstances().size() );
    }

//    public void testEquals1() throws Throwable {
//        PolymerImpl proteinImpl = new ProteinImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel" );
//        NucleicAcidImpl obj = ( NucleicAcidImpl ) Mockingbird.getProxyObject( NucleicAcidImpl.class );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( obj.getAc(), null );
//        Mockingbird.setReturnValue( obj.getShortLabel(), "testString" );
//        Mockingbird.enterTestMode();
//        boolean result = proteinImpl.equals( obj );
//        assertFalse( "result", result );
//        assertEquals( "(ProteinImpl) proteinImpl.getActiveInstances().size()", 0, proteinImpl.getActiveInstances().size() );
//        assertInvoked( obj, "getAc" );
//        assertInvoked( obj, "getShortLabel" );
//    }

    public void testEquals2() throws Throwable {
        PolymerImpl obj = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), null, "testPolymerImplShortLabel", new CvInteractorType( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel" ) );
        boolean result = obj.equals( obj );
        assertTrue( "result", result );
    }

    public void testGetSequence() throws Throwable {
        String result = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" ).getSequence();
        assertNull( "result", result );
    }

    public void testGetSequence1() throws Throwable {
        Institution owner = new Institution( "testPolymerImplShortLabel" );
        List sequenceChunks = new ArrayList( 100 );
        sequenceChunks.add( new SequenceChunk() );
        BioSource source = new BioSource( owner, "testPolymerImplShortLabel", "47508" );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), source, "testPolymerImplShortLabel", new CvInteractorType( owner, "testPolymerImplShortLabel" ) );
        proteinImpl.setSequenceChunks( sequenceChunks );
        String result = proteinImpl.getSequence();
        assertEquals( "result", "null", result );
        assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
    }

    public void testHashCode() throws Throwable {
        List sequenceChunks = new ArrayList( 100 );
        sequenceChunks.add( new SequenceChunk() );
        BioSource source = new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), source, "testPolymerImplShortLabel" );
        proteinImpl.setCrc64( "testPolymerImplCrc64" );
        proteinImpl.setSequenceChunks( sequenceChunks );
        int result = proteinImpl.hashCode();
        assertEquals( "result", 473968681, result );
        assertEquals( "(ProteinImpl) proteinImpl.xrefs.size()", 0, ( ( ProteinImpl ) proteinImpl ).xrefs.size() );
        assertSame( "(ProteinImpl) proteinImpl.getBioSource()", source, proteinImpl.getBioSource() );
        assertEquals( "(ProteinImpl) proteinImpl.getCrc64()", "testPolymerImplCrc64", proteinImpl.getCrc64() );
        assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
        assertEquals( "(ProteinImpl) proteinImpl.shortLabel", "testPolymerImplShort", ( ( ProteinImpl ) proteinImpl ).shortLabel );
        assertNull( "(ProteinImpl) proteinImpl.ac", ( ( ProteinImpl ) proteinImpl ).ac );
        assertNull( "(ProteinImpl) proteinImpl.fullName", ( ( ProteinImpl ) proteinImpl ).fullName );
        assertNull( "(ProteinImpl) proteinImpl.getCvInteractorType()", proteinImpl.getCvInteractorType() );
    }

    public void testHashCode1() throws Throwable {
        BioSource source = new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" );
        CvInteractorType type = new CvInteractorType( new Institution( "testPolymerImplShortLabel2" ), "testPolymerImplShortLabel" );
        PolymerImpl nucleicAcidImpl = new NucleicAcidImpl( new Institution( "testPolymerImplShortLabel1" ), source, "testPolymerImplShortLabel", type );
        nucleicAcidImpl.setCrc64( "testPolymerImplCrc64" );
        int result = nucleicAcidImpl.hashCode();
        assertEquals( "result", 1076679503, result );
        assertEquals( "(NucleicAcidImpl) nucleicAcidImpl.xrefs.size()", 0, ( ( NucleicAcidImpl ) nucleicAcidImpl ).xrefs.size() );
        assertSame( "(NucleicAcidImpl) nucleicAcidImpl.getBioSource()", source, nucleicAcidImpl.getBioSource() );
        assertEquals( "(NucleicAcidImpl) nucleicAcidImpl.getCrc64()", "testPolymerImplCrc64", nucleicAcidImpl.getCrc64() );
        assertEquals( "(NucleicAcidImpl) nucleicAcidImpl.shortLabel", "testPolymerImplShort", ( ( NucleicAcidImpl ) nucleicAcidImpl ).shortLabel );
        assertNull( "(NucleicAcidImpl) nucleicAcidImpl.ac", ( ( NucleicAcidImpl ) nucleicAcidImpl ).ac );
        assertNull( "(NucleicAcidImpl) nucleicAcidImpl.fullName", ( ( NucleicAcidImpl ) nucleicAcidImpl ).fullName );
        assertSame( "(NucleicAcidImpl) nucleicAcidImpl.getCvInteractorType()", type, nucleicAcidImpl.getCvInteractorType() );
    }

    public void testHashCode2() throws Throwable {
        BioSource source = new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), source, "testPolymerImplShortLabel" );
        int result = proteinImpl.hashCode();
        assertEquals( "result", -1151778168, result );
        assertEquals( "(ProteinImpl) proteinImpl.xrefs.size()", 0, ( ( ProteinImpl ) proteinImpl ).xrefs.size() );
        assertSame( "(ProteinImpl) proteinImpl.getBioSource()", source, proteinImpl.getBioSource() );
        assertEquals( "(ProteinImpl) proteinImpl.shortLabel", "testPolymerImplShort", ( ( ProteinImpl ) proteinImpl ).shortLabel );
        assertNull( "(ProteinImpl) proteinImpl.ac", ( ( ProteinImpl ) proteinImpl ).ac );
        assertNull( "(ProteinImpl) proteinImpl.fullName", ( ( ProteinImpl ) proteinImpl ).fullName );
        assertNull( "(ProteinImpl) proteinImpl.getCvInteractorType()", proteinImpl.getCvInteractorType() );
    }

    public void testHashCode3() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk( 100, "testPolymerImplASequenceChunk" );
        List sequenceChunks = new ArrayList( 100 );
        sequenceChunks.add( sequenceChunk );
        BioSource source = new BioSource( null, "testPolymerImplShortLabel", "47508" );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), source, "testPolymerImplShortLabel" );
        proteinImpl.setSequenceChunks( sequenceChunks );
        int result = proteinImpl.hashCode();
        assertEquals( "result", -1339727185, result );
        assertEquals( "(ProteinImpl) proteinImpl.xrefs.size()", 0, ( ( ProteinImpl ) proteinImpl ).xrefs.size() );
        assertSame( "(ProteinImpl) proteinImpl.getBioSource()", source, proteinImpl.getBioSource() );
        assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
        assertEquals( "(ProteinImpl) proteinImpl.shortLabel", "testPolymerImplShort", ( ( ProteinImpl ) proteinImpl ).shortLabel );
        assertNull( "(ProteinImpl) proteinImpl.ac", ( ( ProteinImpl ) proteinImpl ).ac );
        assertNull( "(ProteinImpl) proteinImpl.fullName", ( ( ProteinImpl ) proteinImpl ).fullName );
        assertNull( "(ProteinImpl) proteinImpl.getCvInteractorType()", proteinImpl.getCvInteractorType() );
    }

    public void testRemoveSequenceChunk() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel", new CvInteractorType( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel" ) );
        SequenceChunk sequenceChunk = new SequenceChunk();
        proteinImpl.addSequenceChunk( sequenceChunk );
        proteinImpl.removeSequenceChunk( sequenceChunk );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
    }

    public void testRemoveSequenceChunk1() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), null, "testPolymerImplShortLabel", null );
        proteinImpl.removeSequenceChunk( new SequenceChunk() );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
    }

    public void testSetCrc64() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.setCrc64( "testPolymerImplCrc64" );
        assertEquals( "(ProteinImpl) proteinImpl.getCrc64()", "testPolymerImplCrc64", proteinImpl.getCrc64() );
    }

    public void testSetSequence() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.addSequenceChunk( new SequenceChunk() );
        ArrayList result = ( ArrayList ) proteinImpl.setSequence( "2196CharactersXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" );
        assertEquals( "result.size()", 0, result.size() );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 3, proteinImpl.getSequenceChunks().size() );
    }

    public void testSetSequence1() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.setSequence( "XXXXXXXXXXXXXXXXX XX XXXXXXXXXXX\n X\rXXXXXXXXXXXXXXXXXX\tXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX \rXXXXXXXXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXXXXXXXXXXX$XXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXX XXXXXXXXXXXXXXXXXXXXXXXXXX XXXXXXX XXXXXXX\tXXXXXXXXXX XXXXXXXXXX#XXXX\nXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX$XXXXXXXXXXXXXXXXXXXXXXXXXXXX\rXXXXXXX#XXXXXXX XXXXXXXXXXXXXXXXXXXXXXXXXXX XX XXX XXXXXX XXXXXXXXXXXXXXXXXXXXXXXXXXX XXXXXXX XXXXXXXXXX XXXXXXXXXXXXXX\nXXXX\n\rX\rXXXXXXXXXX\rXXX XXXXXXXXXX\nXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX# XXXXXXXXXXXXXXXXXXX\nXXXXXXXXXX$XX#XXXXX \n\nXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXX\nXXXXX$XXXXXXXXXXXXX\tXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#XXXXXXXXXXXXXXXXXXX\nXXXXXX #XXXXXXXXX#XXXXXX\rXXXXXXXX$XXXXXXXX$XXXXX\tXXXXX\nXXXXXXXX\rXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX XXXXXXXX\rXXXXXXXXXXXXX\tXXXXXXXXXXXXXXXXXXX\rXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\tXXXXXXXXXXXX XXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXX\nXXXXXXXXX#XXXXXXXXXXXXXXXXXX\tXXXXXXXXXXXXXXXX\rXXXXXXXX XXXXXX\tXXXXXXXXXX\n$XX\r XXXXXXXXXXXXXXXXXXXXXXXX$XXXXXXXXXXXXXXXXXXXXXX\nXXX XXXXXX$XXXXXXX#XX\tXXXXX$XX XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\tXXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX\tXXXXX$X XXXXXXXXXXXXXXXX#XXXX\rXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX XXXXXXXXXX$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX$XXXXXX X\tXXXXXXXXXXX XXXXXX\tXXXXXXXXXXXXXX XXXX \rXXXXXXXXXXXXXX\rXXXXXXX\rXXX$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX$\rXXXXX XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#XXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX$XXXXXXXXXXXXXXXXXXX\rXXXXXXXXX#XXXX XXXXXX XXXXXXX XXXXXXXXXXX#XXXXX#X$XXXXXXXXXXXXXXXXX\nXX\nXXXXXXXXXX\rXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#XXXXXXXXX  X\tXXXXXXXXXXXXX\tXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX#XXX\rXXXXXXXXXXXX\t XXXXXXXXXXXX\tXXX$XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\tX$X\rXXXXXXXXXXXXXXXXXXXX XXXXXXXXXXXXXXXX XXXXXXXXXXXXXXX \nXXXXXXXXXXXXXXXXXXXXXXXXXXX X\rXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX#X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX #XXXXXXXXX \tXXXXXXXX  X XXXXXXXXXXXX XXXX$XXXXXXXXXXXXXXXXXX\nX\rXXXXXXXXXXXXXXXXXXXX\tXXXXXXXXXXXXXX XX$XXXXXXXX" );
        ArrayList result = ( ArrayList ) proteinImpl.setSequence( "testPolymerImplASequence" );
        assertEquals( "result.size()", 2, result.size() );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
    }

    public void testSetSequence2() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        AbstractList result = ( AbstractList ) proteinImpl.setSequence( "testPolymerImplASequence" );
        assertEquals( "result.size()", 0, result.size() );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
    }

    public void testSetSequence3() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        AbstractList sequence = ( AbstractList ) proteinImpl.setSequence( "testString" );
        AbstractList result = ( AbstractList ) proteinImpl.setSequence( "testString" );
        assertSame( "result", sequence, result );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
    }

    public void testSetSequence4() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        SequenceChunk sequenceChunk = new SequenceChunk();
        proteinImpl.addSequenceChunk( sequenceChunk );
        ArrayList result = ( ArrayList ) proteinImpl.setSequence( "" );
        assertEquals( "result.size()", 1, result.size() );
        assertSame( "(ArrayList) result.get(0)", sequenceChunk, result.get( 0 ) );
        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
    }

//    public void testSetSequence5() throws Throwable {
//        PolymerImpl nucleicAcidImpl = new NucleicAcidImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel", ( CvInteractorType ) Mockingbird.getProxyObject( CvInteractorType.class ) );
//        Mockingbird.enterTestMode();
//        AbstractList result = ( AbstractList ) nucleicAcidImpl.setSequence( " " );
//        assertEquals( "result.size()", 0, result.size() );
//        assertEquals( "(NucleicAcidImpl) nucleicAcidImpl.getSequenceChunks().size()", 1, nucleicAcidImpl.getSequenceChunks().size() );
//    }
//
//    public void testSetSequence6() throws Throwable {
//        PolymerImpl proteinImpl = new ProteinImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel" );
//        proteinImpl.setSequenceChunks( null );
//        Mockingbird.enterTestMode();
//        AbstractList result = ( AbstractList ) proteinImpl.setSequence( "" );
//        assertEquals( "result.size()", 0, result.size() );
//        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
//    }
//
//    public void testSetSequence7() throws Throwable {
//        PolymerImpl proteinImpl = new ProteinImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel" );
//        Mockingbird.enterTestMode();
//        AbstractList result = ( AbstractList ) proteinImpl.setSequence( "" );
//        assertEquals( "result.size()", 0, result.size() );
//        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
//    }
//
//    public void testSetSequence8() throws Throwable {
//        PolymerImpl proteinImpl = new ProteinImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel" );
//        proteinImpl.setSequenceChunks( null );
//        Mockingbird.enterTestMode();
//        AbstractList result = ( AbstractList ) proteinImpl.setSequence( "testPolymerImplASequence" );
//        assertEquals( "result.size()", 0, result.size() );
//        assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
//    }

    public void testSetSequenceChunks() throws Throwable {
        List sequenceChunks = new ArrayList( 100 );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), null, "testPolymerImplShortLabel", new CvInteractorType( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel" ) );
        proteinImpl.setSequenceChunks( sequenceChunks );
        assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
    }

    public void testToString() throws Throwable {
        String result = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" ).toString();
        assertEquals( "result", "AC: null Owner: testPolymerImplShort Label: testPolymerImplShort[] [ CRC64: null Sequence: null]", result );
    }

    public void testToString1() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        List sequenceChunks = new ArrayList( 100 );
        proteinImpl.setSequenceChunks( sequenceChunks );
        proteinImpl.setSequence( "testPolymerImplASequence" );
        String result = proteinImpl.toString();
        assertEquals( "result", "AC: null Owner: testPolymerImplShort Label: testPolymerImplShort[] [ CRC64: null Sequence: testPolymerImplASequence]", result );
        assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
    }

    public void testAddSequenceChunkThrowsNullPointerException() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), new BioSource( null, "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.setSequenceChunks( null );
        SequenceChunk sequenceChunk = new SequenceChunk( 100, "testPolymerImplASequenceChunk" );
        try {
            proteinImpl.addSequenceChunk( sequenceChunk );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PolymerImpl.class, ex );
            assertNull( "(ProteinImpl) proteinImpl.getSequenceChunks()", proteinImpl.getSequenceChunks() );
            assertNull( "sequenceChunk.getAc()", sequenceChunk.getAc() );
        }
    }

    public void testAddSequenceChunkThrowsNullPointerException1() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        try {
            proteinImpl.addSequenceChunk( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PolymerImpl.class, ex );
            assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 1, proteinImpl.getSequenceChunks().size() );
        }
    }

    public void testGetSequenceThrowsNullPointerException() throws Throwable {
        BioSource source = new BioSource( null, "testPolymerImplShortLabel", "47508" );
        List sequenceChunks = new ArrayList( 100 );
        sequenceChunks.add( null );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel" ), source, "testPolymerImplShortLabel" );
        proteinImpl.setSequenceChunks( sequenceChunks );
        try {
            proteinImpl.getSequence();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PolymerImpl.class, ex );
            assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
        }
    }

    public void testRemoveSequenceChunkThrowsNullPointerException() throws Throwable {
        List sequenceChunks = new ArrayList( 100 );
        sequenceChunks.add( null );
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        proteinImpl.setSequenceChunks( sequenceChunks );
        try {
            proteinImpl.removeSequenceChunk( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PolymerImpl.class, ex );
            assertSame( "(ProteinImpl) proteinImpl.getSequenceChunks()", sequenceChunks, proteinImpl.getSequenceChunks() );
        }
    }

//    public void testRemoveSequenceChunkThrowsNullPointerException1() throws Throwable {
//        PolymerImpl proteinImpl = new ProteinImpl( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( BioSource ) Mockingbird.getProxyObject( BioSource.class ), "testPolymerImplShortLabel" );
//        proteinImpl.setSequenceChunks( null );
//        SequenceChunk sequenceChunk = ( SequenceChunk ) Mockingbird.getProxyObject( SequenceChunk.class );
//        Mockingbird.enterTestMode();
//        try {
//            proteinImpl.removeSequenceChunk( sequenceChunk );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertNull( "ex.getMessage()", ex.getMessage() );
//            assertThrownBy( PolymerImpl.class, ex );
//            assertNull( "(ProteinImpl) proteinImpl.getSequenceChunks()", proteinImpl.getSequenceChunks() );
//        }
//    }

    public void testSetSequenceThrowsNullPointerException() throws Throwable {
        PolymerImpl proteinImpl = new ProteinImpl( new Institution( "testPolymerImplShortLabel1" ), new BioSource( new Institution( "testPolymerImplShortLabel" ), "testPolymerImplShortLabel", "47508" ), "testPolymerImplShortLabel" );
        try {
            proteinImpl.setSequence( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PolymerImpl.class, ex );
            assertEquals( "(ProteinImpl) proteinImpl.getSequenceChunks().size()", 0, proteinImpl.getSequenceChunks().size() );
        }
    }
}

