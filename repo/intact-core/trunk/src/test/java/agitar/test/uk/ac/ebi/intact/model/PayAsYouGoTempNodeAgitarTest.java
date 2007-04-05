/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:25:36
 * Time to generate: 00:09.499 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;


public class PayAsYouGoTempNodeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = PayAsYouGoTempNode.class;

    public void testConstructor() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        assertNull( "payAsYouGoTempNode.getNid()", payAsYouGoTempNode.getNid() );
    }

    public void testEquals() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        payAsYouGoTempNode.setPk( pk );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        o.setPk( pk );
        boolean result = payAsYouGoTempNode.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testPayAsYouGoTempNodeNid" );
        boolean result = payAsYouGoTempNode.equals( new PayAsYouGoTempNode() );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        o.setPk( new PayAsYouGoPk() );
        boolean result = payAsYouGoTempNode.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals3() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals4() throws Throwable {
        boolean result = new PayAsYouGoTempNode().equals( null );
        assertFalse( "result", result );
    }

    public void testEquals5() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setPk( pk );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        o.setPk( pk );
        boolean result = payAsYouGoTempNode.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals6() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setSpecies( "Fws" );
        payAsYouGoTempNode.setPk( pk );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        o.setSpecies( "Fws" );
        o.setPk( pk );
        boolean result = payAsYouGoTempNode.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals7() throws Throwable {
        boolean result = new PayAsYouGoTempNode().equals( new PayAsYouGoTempNode() );
        assertTrue( "result", result );
    }

    public void testEquals8() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        boolean result = new PayAsYouGoTempNode().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals9() throws Throwable {
        boolean result = new PayAsYouGoTempNode().equals( new Integer( 0 ) );
        assertFalse( "result", result );
    }

    public void testEquals10() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testPayAsYouGoTempNodeNid" );
        boolean result = new PayAsYouGoTempNode().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals11() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setPk( new PayAsYouGoPk() );
        boolean result = payAsYouGoTempNode.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals12() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        boolean result = payAsYouGoTempNode.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals13() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        boolean result = payAsYouGoTempNode.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals14() throws Throwable {
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setPk( new PayAsYouGoPk() );
        boolean result = new PayAsYouGoTempNode().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals15() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testString" );
        payAsYouGoTempNode.setPk( pk );
        PayAsYouGoTempNode o = new PayAsYouGoTempNode();
        o.setNid( "testString" );
        o.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        o.setPk( pk );
        boolean result = payAsYouGoTempNode.equals( o );
        assertFalse( "result", result );
    }

    public void testHashCode() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGoTempNode.getSpecies()", payAsYouGoTempNode.getSpecies() );
        assertNull( "payAsYouGoTempNode.getNid()", payAsYouGoTempNode.getNid() );
        assertNull( "payAsYouGoTempNode.getPk()", payAsYouGoTempNode.getPk() );
    }

    public void testHashCode1() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoTempNodeNid", "testPayAsYouGoTempNodeSpecies" );
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        payAsYouGoTempNode.setPk( pk );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 1313133850, result );
        assertEquals( "payAsYouGoTempNode.getSpecies()", "testPayAsYouGoTempNodeSpecies", payAsYouGoTempNode.getSpecies() );
        assertNull( "payAsYouGoTempNode.getNid()", payAsYouGoTempNode.getNid() );
        assertSame( "payAsYouGoTempNode.getPk()", pk, payAsYouGoTempNode.getPk() );
    }

    public void testHashCode2() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setPk( pk );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGoTempNode.getSpecies()", payAsYouGoTempNode.getSpecies() );
        assertNull( "payAsYouGoTempNode.getNid()", payAsYouGoTempNode.getNid() );
        assertSame( "payAsYouGoTempNode.getPk()", pk, payAsYouGoTempNode.getPk() );
    }

    public void testHashCode3() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "" );
        payAsYouGoTempNode.setSpecies( "" );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGoTempNode.getSpecies()", "", payAsYouGoTempNode.getSpecies() );
        assertEquals( "payAsYouGoTempNode.getNid()", "", payAsYouGoTempNode.getNid() );
        assertNull( "payAsYouGoTempNode.getPk()", payAsYouGoTempNode.getPk() );
    }

    public void testHashCode4() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testPayAsYouGoTempNodeNid" );
        payAsYouGoTempNode.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        payAsYouGoTempNode.setPk( pk );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 1581983795, result );
        assertEquals( "payAsYouGoTempNode.getSpecies()", "testPayAsYouGoTempNodeSpecies", payAsYouGoTempNode.getSpecies() );
        assertEquals( "payAsYouGoTempNode.getNid()", "testPayAsYouGoTempNodeNid", payAsYouGoTempNode.getNid() );
        assertSame( "payAsYouGoTempNode.getPk()", pk, payAsYouGoTempNode.getPk() );
    }

    public void testHashCode5() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testPayAsYouGoTempNodeNid" );
        payAsYouGoTempNode.setPk( pk );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 136854156, result );
        assertNull( "payAsYouGoTempNode.getSpecies()", payAsYouGoTempNode.getSpecies() );
        assertEquals( "payAsYouGoTempNode.getNid()", "testPayAsYouGoTempNodeNid", payAsYouGoTempNode.getNid() );
        assertSame( "payAsYouGoTempNode.getPk()", pk, payAsYouGoTempNode.getPk() );
    }

    public void testHashCode6() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setSpecies( "" );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGoTempNode.getSpecies()", "", payAsYouGoTempNode.getSpecies() );
        assertNull( "payAsYouGoTempNode.getNid()", payAsYouGoTempNode.getNid() );
        assertNull( "payAsYouGoTempNode.getPk()", payAsYouGoTempNode.getPk() );
    }

    public void testHashCode7() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testPayAsYouGoTempNodeNid" );
        int result = payAsYouGoTempNode.hashCode();
        assertEquals( "result", 136854156, result );
        assertNull( "payAsYouGoTempNode.getSpecies()", payAsYouGoTempNode.getSpecies() );
        assertEquals( "payAsYouGoTempNode.getNid()", "testPayAsYouGoTempNodeNid", payAsYouGoTempNode.getNid() );
        assertNull( "payAsYouGoTempNode.getPk()", payAsYouGoTempNode.getPk() );
    }

    public void testSetNid() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setNid( "testPayAsYouGoTempNodeNid" );
        assertEquals( "payAsYouGoTempNode.getNid()", "testPayAsYouGoTempNodeNid", payAsYouGoTempNode.getNid() );
    }

    public void testSetPk() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        PayAsYouGoPk pk = new PayAsYouGoPk();
        payAsYouGoTempNode.setPk( pk );
        assertSame( "payAsYouGoTempNode.getPk()", pk, payAsYouGoTempNode.getPk() );
    }

    public void testSetSpecies() throws Throwable {
        PayAsYouGoTempNode payAsYouGoTempNode = new PayAsYouGoTempNode();
        payAsYouGoTempNode.setSpecies( "testPayAsYouGoTempNodeSpecies" );
        assertEquals( "payAsYouGoTempNode.getSpecies()", "testPayAsYouGoTempNodeSpecies", payAsYouGoTempNode.getSpecies() );
    }
}

