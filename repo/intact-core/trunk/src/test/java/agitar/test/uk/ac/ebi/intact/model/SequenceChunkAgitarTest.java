/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:30:01
 * Time to generate: 00:08.024 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;


public class SequenceChunkAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = SequenceChunk.class;

    public void testConstructor() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk( 100, "testSequenceChunkASequenceChunk" );
        assertEquals( "sequenceChunk.getSequenceChunk()", "testSequenceChunkASequenceChunk", sequenceChunk.getSequenceChunk() );
        assertEquals( "sequenceChunk.getSequenceIndex()", 100, sequenceChunk.getSequenceIndex() );
    }

    public void testConstructor1() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        assertNull( "sequenceChunk.getAc()", sequenceChunk.getAc() );
    }

    public void testSetAc() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        sequenceChunk.setAc( "testSequenceChunkAc" );
        assertEquals( "sequenceChunk.getAc()", "testSequenceChunkAc", sequenceChunk.getAc() );
    }

    public void testSetParent() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        Polymer parent = new NucleicAcidImpl( null, null, "testSequenceChunkShortLabel", null );
        sequenceChunk.setParent( parent );
        assertSame( "sequenceChunk.getParent()", parent, sequenceChunk.getParent() );
    }

    public void testSetParentAc() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        sequenceChunk.setParentAc( "testSequenceChunkParentAc" );
        assertEquals( "sequenceChunk.getParentAc()", "testSequenceChunkParentAc", sequenceChunk.getParentAc() );
    }

    public void testSetSequenceChunk() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        sequenceChunk.setSequenceChunk( "testSequenceChunkSequenceChunk" );
        assertEquals( "sequenceChunk.getSequenceChunk()", "testSequenceChunkSequenceChunk", sequenceChunk.getSequenceChunk() );
    }

    public void testSetSequenceIndex() throws Throwable {
        SequenceChunk sequenceChunk = new SequenceChunk();
        sequenceChunk.setSequenceIndex( 100 );
        assertEquals( "sequenceChunk.getSequenceIndex()", 100, sequenceChunk.getSequenceIndex() );
    }
}

