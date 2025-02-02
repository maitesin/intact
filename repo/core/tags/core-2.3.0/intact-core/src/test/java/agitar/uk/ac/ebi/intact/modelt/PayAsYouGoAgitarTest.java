/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:24:16
 * Time to generate: 00:12.712 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;


public class PayAsYouGoAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = PayAsYouGo.class;

    public void testConstructor() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
    }

    public void testEquals() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEconf( 37 );
        payAsYouGo.setInDegree( 68 );
        PayAsYouGo o = new PayAsYouGo();
        o.setEconf( 37 );
        o.setOutDegree( 100.0F );
        o.setInDegree( 68 );
        boolean result = payAsYouGo.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setPk( new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" ) );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setOutDegree( -1.0F );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals3() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        boolean result = payAsYouGo.equals( new PayAsYouGo() );
        assertFalse( "result", result );
    }

    public void testEquals4() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setNid( "testPayAsYouGoNid" );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals5() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setSpecies( "testPayAsYouGoSpecies" );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals6() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setReallyUsedAsBait( true );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals7() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setPrey( 100 );
        boolean result = payAsYouGo.equals( new PayAsYouGo() );
        assertFalse( "result", result );
    }

    public void testEquals8() throws Throwable {
        boolean result = new PayAsYouGo().equals( new PayAsYouGo() );
        assertTrue( "result", result );
    }

    public void testEquals9() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setBait( 2 );
        payAsYouGo.setEseen( -15 );
        payAsYouGo.setPk( new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" ) );
        payAsYouGo.setInDegree( 34 );
        PayAsYouGo o = new PayAsYouGo();
        o.setBait( 2 );
        o.setEseen( -15 );
        o.setInDegree( 34 );
        boolean result = payAsYouGo.equals( o );
        assertFalse( "result", result );
    }

    public void testEquals10() throws Throwable {
        boolean result = new PayAsYouGo().equals( null );
        assertFalse( "result", result );
    }

    public void testEquals11() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setEseen( 100 );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testEquals12() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEconf( 100 );
        boolean result = payAsYouGo.equals( new PayAsYouGo() );
        assertFalse( "result", result );
    }

    public void testEquals13() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals14() throws Throwable {
        boolean result = new PayAsYouGo().equals( "" );
        assertFalse( "result", result );
    }

    public void testEquals15() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setBait( 100 );
        boolean result = payAsYouGo.equals( new PayAsYouGo() );
        assertFalse( "result", result );
    }

    public void testEquals16() throws Throwable {
        PayAsYouGo o = new PayAsYouGo();
        o.setInDegree( 100 );
        boolean result = new PayAsYouGo().equals( o );
        assertFalse( "result", result );
    }

    public void testHashCode() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEseen( 100 );
        payAsYouGo.setNid( "" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 96100, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode1() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" );
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        payAsYouGo.setPk( pk );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode2() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode3() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setReallyUsedAsBait( true );
        payAsYouGo.setPk( pk );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 1, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode4() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk();
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        payAsYouGo.setReallyUsedAsBait( true );
        payAsYouGo.setPk( pk );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 1, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode5() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode6() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode7() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" );
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEconf( 100 );
        payAsYouGo.setPk( pk );
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 3100, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode8() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" );
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        payAsYouGo.setPk( pk );
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode9() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode10() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setReallyUsedAsBait( true );
        payAsYouGo.setOutDegree( 100.0F );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 1748500481, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode11() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        payAsYouGo.setReallyUsedAsBait( true );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 1, result );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testHashCode12() throws Throwable {
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" );
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setPk( pk );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertNull( "payAsYouGo.getNid()", payAsYouGo.getNid() );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testHashCode13() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setReallyUsedAsBait( true );
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        int result = payAsYouGo.hashCode();
        assertEquals( "result", 1, result );
        assertNull( "payAsYouGo.getSpecies()", payAsYouGo.getSpecies() );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
        assertNull( "payAsYouGo.getPk()", payAsYouGo.getPk() );
    }

    public void testSetBait() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setBait( 100 );
        assertEquals( "payAsYouGo.getBait()", 100, payAsYouGo.getBait() );
    }

    public void testSetEconf() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEconf( 100 );
        assertEquals( "payAsYouGo.getEconf()", 100, payAsYouGo.getEconf() );
    }

    public void testSetEseen() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setEseen( 100 );
        assertEquals( "payAsYouGo.getEseen()", 100, payAsYouGo.getEseen() );
    }

    public void testSetInDegree() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setInDegree( 100 );
        assertEquals( "payAsYouGo.getInDegree()", 100, payAsYouGo.getInDegree() );
    }

    public void testSetNid() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setNid( "testPayAsYouGoNid" );
        assertEquals( "payAsYouGo.getNid()", "testPayAsYouGoNid", payAsYouGo.getNid() );
    }

    public void testSetOutDegree() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setOutDegree( 100.0F );
        assertEquals( "payAsYouGo.getOutDegree()", 100.0F, payAsYouGo.getOutDegree(), 1.0E-6F );
    }

    public void testSetPk() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        PayAsYouGoPk pk = new PayAsYouGoPk( "testPayAsYouGoNid", "testPayAsYouGoSpecies" );
        payAsYouGo.setPk( pk );
        assertSame( "payAsYouGo.getPk()", pk, payAsYouGo.getPk() );
    }

    public void testSetPrey() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setPrey( 100 );
        assertEquals( "payAsYouGo.getPrey()", 100, payAsYouGo.getPrey() );
    }

    public void testSetReallyUsedAsBait() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setReallyUsedAsBait( true );
        assertTrue( "payAsYouGo.isReallyUsedAsBait()", payAsYouGo.isReallyUsedAsBait() );
    }

    public void testSetSpecies() throws Throwable {
        PayAsYouGo payAsYouGo = new PayAsYouGo();
        payAsYouGo.setSpecies( "testPayAsYouGoSpecies" );
        assertEquals( "payAsYouGo.getSpecies()", "testPayAsYouGoSpecies", payAsYouGo.getSpecies() );
    }
}

