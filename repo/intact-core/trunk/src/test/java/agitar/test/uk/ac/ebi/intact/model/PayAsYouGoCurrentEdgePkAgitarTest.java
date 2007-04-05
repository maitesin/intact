/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:29:40
 * Time to generate: 00:11.359 seconds
 *
 */

package uk.ac.ebi.intact.model;

import agitar.test.uk.ac.ebi.intact.model.AgitarTestCase;


public class PayAsYouGoCurrentEdgePkAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = PayAsYouGoCurrentEdgePk.class;

    public void testConstructor() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "testPayAsYouGoCurrentEdgePkSpecies", payAsYouGoCurrentEdgePk.getSpecies() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "testPayAsYouGoCurrentEdgePkNidB", payAsYouGoCurrentEdgePk.getNidB() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "testPayAsYouGoCurrentEdgePkNidA", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testConstructor1() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk();
        assertNull( "payAsYouGoCurrentEdgePk.getNidA()", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testEquals() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" ).equals( new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA1", "testPayAsYouGoCurrentEdgePkNidB1", "testPayAsYouGoCurrentEdgePkSpecies1" ) );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk( null, "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" ).equals( new PayAsYouGoCurrentEdgePk() );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testString", null, "testPayAsYouGoCurrentEdgePkSpecies" );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "testString", null, "testPayAsYouGoCurrentEdgePkSpecies1" ) );
        assertFalse( "result", result );
    }

    public void testEquals3() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" ).equals( null );
        assertFalse( "result", result );
    }

    public void testEquals4() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testString", null, "testPayAsYouGoCurrentEdgePkSpecies" );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "testString", "testString", "testPayAsYouGoCurrentEdgePkSpecies1" ) );
        assertFalse( "result", result );
    }

    public void testEquals5() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk().equals( new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" ) );
        assertFalse( "result", result );
    }

    public void testEquals6() throws Throwable {
        PayAsYouGoCurrentEdgePk o = new PayAsYouGoCurrentEdgePk( "testString", "testString", "testString" );
        boolean result = new PayAsYouGoCurrentEdgePk( "testString", "testString", "testString" ).equals( o );
        assertTrue( "result", result );
    }

    public void testEquals7() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testString", "testString", null );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "testString", "testString", null ) );
        assertTrue( "result", result );
    }

    public void testEquals8() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( null, null, "testString" );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( null, null, "testString" ) );
        assertTrue( "result", result );
    }

    public void testEquals9() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk().equals( new PayAsYouGoCurrentEdgePk() );
        assertTrue( "result", result );
    }

    public void testEquals10() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" ).equals( "testString" );
        assertFalse( "result", result );
    }

    public void testEquals11() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testString", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "testString", "testPayAsYouGoCurrentEdgePkNidB1", "testPayAsYouGoCurrentEdgePkSpecies1" ) );
        assertFalse( "result", result );
    }

    public void testEquals12() throws Throwable {
        boolean result = new PayAsYouGoCurrentEdgePk( null, null, "testPayAsYouGoCurrentEdgePkSpecies" ).equals( new PayAsYouGoCurrentEdgePk() );
        assertFalse( "result", result );
    }

    public void testEquals13() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testString", "testString", "testPayAsYouGoCurrentEdgePkSpecies" );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "testString", "testString", "testPayAsYouGoCurrentEdgePkSpecies1" ) );
        assertFalse( "result", result );
    }

    public void testEquals14() throws Throwable {
        PayAsYouGoCurrentEdgePk o = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals15() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "[jQ.P?(pspe\f\"Ud2@()5!W<v!$Li.9z]VVE(", "testString", null );
        boolean result = payAsYouGoCurrentEdgePk.equals( new PayAsYouGoCurrentEdgePk( "[jQ.P?(pspe\f\"Ud2@()5!W<v!$Li.9z]VVE(", "testString", "testString" ) );
        assertFalse( "result", result );
    }

    public void testHashCode() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "", null, null );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGoCurrentEdgePk.getSpecies()", payAsYouGoCurrentEdgePk.getSpecies() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidB()", payAsYouGoCurrentEdgePk.getNidB() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode1() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( null, "", null );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGoCurrentEdgePk.getSpecies()", payAsYouGoCurrentEdgePk.getSpecies() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "", payAsYouGoCurrentEdgePk.getNidB() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidA()", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode2() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", -232289591, result );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "testPayAsYouGoCurrentEdgePkSpecies", payAsYouGoCurrentEdgePk.getSpecies() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "testPayAsYouGoCurrentEdgePkNidB", payAsYouGoCurrentEdgePk.getNidB() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "testPayAsYouGoCurrentEdgePkNidA", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode3() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( null, null, "" );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "", payAsYouGoCurrentEdgePk.getSpecies() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidB()", payAsYouGoCurrentEdgePk.getNidB() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidA()", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode4() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", null );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", -1294550305, result );
        assertNull( "payAsYouGoCurrentEdgePk.getSpecies()", payAsYouGoCurrentEdgePk.getSpecies() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "testPayAsYouGoCurrentEdgePkNidB", payAsYouGoCurrentEdgePk.getNidB() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "testPayAsYouGoCurrentEdgePkNidA", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode5() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk();
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGoCurrentEdgePk.getSpecies()", payAsYouGoCurrentEdgePk.getSpecies() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidB()", payAsYouGoCurrentEdgePk.getNidB() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidA()", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode6() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( null, "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", 1961330143, result );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "testPayAsYouGoCurrentEdgePkSpecies", payAsYouGoCurrentEdgePk.getSpecies() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "testPayAsYouGoCurrentEdgePkNidB", payAsYouGoCurrentEdgePk.getNidB() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidA()", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testHashCode7() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", null, "testPayAsYouGoCurrentEdgePkSpecies" );
        int result = payAsYouGoCurrentEdgePk.hashCode();
        assertEquals( "result", -1131359020, result );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "testPayAsYouGoCurrentEdgePkSpecies", payAsYouGoCurrentEdgePk.getSpecies() );
        assertNull( "payAsYouGoCurrentEdgePk.getNidB()", payAsYouGoCurrentEdgePk.getNidB() );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "testPayAsYouGoCurrentEdgePkNidA", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testSetNidA() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        payAsYouGoCurrentEdgePk.setNidA( "testPayAsYouGoCurrentEdgePkNidA" );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidA()", "testPayAsYouGoCurrentEdgePkNidA", payAsYouGoCurrentEdgePk.getNidA() );
    }

    public void testSetNidB() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk();
        payAsYouGoCurrentEdgePk.setNidB( "testPayAsYouGoCurrentEdgePkNidB" );
        assertEquals( "payAsYouGoCurrentEdgePk.getNidB()", "testPayAsYouGoCurrentEdgePkNidB", payAsYouGoCurrentEdgePk.getNidB() );
    }

    public void testSetSpecies() throws Throwable {
        PayAsYouGoCurrentEdgePk payAsYouGoCurrentEdgePk = new PayAsYouGoCurrentEdgePk( "testPayAsYouGoCurrentEdgePkNidA", "testPayAsYouGoCurrentEdgePkNidB", "testPayAsYouGoCurrentEdgePkSpecies" );
        payAsYouGoCurrentEdgePk.setSpecies( "testPayAsYouGoCurrentEdgePkSpecies1" );
        assertEquals( "payAsYouGoCurrentEdgePk.getSpecies()", "testPayAsYouGoCurrentEdgePkSpecies1", payAsYouGoCurrentEdgePk.getSpecies() );
    }
}

