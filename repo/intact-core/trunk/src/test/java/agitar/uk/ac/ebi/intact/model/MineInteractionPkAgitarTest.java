/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:26:10
 * Time to generate: 01:12.935 seconds
 *
 */

package agitar.uk.ac.ebi.intact.model; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import com.agitar.lib.mockingbird.Mockingbird;

import java.util.ArrayList;

public class MineInteractionPkAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = MineInteractionPk.class;

    public void testConstructor() throws Throwable {
        MineInteractionPk mineInteractionPk = new MineInteractionPk();
        assertNull( "mineInteractionPk.getInteraction()", mineInteractionPk.getInteraction() );
    }

    public void testConstructor1() throws Throwable {
        ProteinImpl protein1 = ( ProteinImpl ) Mockingbird.getProxyObject( ProteinImpl.class );
        ProteinImpl protein2 = ( ProteinImpl ) Mockingbird.getProxyObject( ProteinImpl.class );
        InteractionImpl interaction = ( InteractionImpl ) Mockingbird.getProxyObject( InteractionImpl.class );
        Mockingbird.enterTestMode();
        Mockingbird.getProxyObject( ProteinImpl.class );
        Mockingbird.enterTestMode();
        Mockingbird.getProxyObject( InteractionImpl.class );
        Mockingbird.enterTestMode();
        Mockingbird.getProxyObject( ProteinImpl.class );
        Mockingbird.enterTestMode();
        MineInteractionPk mineInteractionPk = new MineInteractionPk( protein1, protein2, interaction );
        assertSame( "mineInteractionPk.getProtein2()", protein2, mineInteractionPk.getProtein2() );
        assertSame( "mineInteractionPk.getProtein1()", protein1, mineInteractionPk.getProtein1() );
        assertSame( "mineInteractionPk.getInteraction()", interaction, mineInteractionPk.getInteraction() );
    }

    public void testEquals() throws Throwable {
        boolean result = new MineInteractionPk().equals( null );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        boolean result = new MineInteractionPk().equals( "" );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        MineInteractionPk o = new MineInteractionPk();
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testEquals3() throws Throwable {
        boolean result = new MineInteractionPk().equals( new MineInteractionPk() );
        assertTrue( "result", result );
    }

    public void testEquals4() throws Throwable {
        boolean result = new MineInteractionPk().equals( new MineInteractionPk( null, null, new InteractionImpl( new ArrayList( 100 ), null, "testMineInteractionPkShortLabel", new Institution( "testMineInteractionPkShortLabel" ) ) ) );
        assertFalse( "result", result );
    }

    public void testHashCode() throws Throwable {
        MineInteractionPk mineInteractionPk = new MineInteractionPk();
        int result = mineInteractionPk.hashCode();
        assertEquals( "result", 0, result );
        assertNull( "mineInteractionPk.getProtein2()", mineInteractionPk.getProtein2() );
        assertNull( "mineInteractionPk.getProtein1()", mineInteractionPk.getProtein1() );
        assertNull( "mineInteractionPk.getInteraction()", mineInteractionPk.getInteraction() );
    }

    public void testHashCode1() throws Throwable {
        ProteinImpl protein2 = new ProteinImpl( new Institution( "testMineInteractionPkShortLabel1" ), new BioSource( new Institution( "testMineInteractionPkShortLabel" ), "testMineInteractionPkShortLabel", "25718" ), "testMineInteractionPkShortLabel" );
        MineInteractionPk mineInteractionPk = new MineInteractionPk( null, protein2, null );
        int result = mineInteractionPk.hashCode();
        assertEquals( "result", -1742337792, result );
        assertSame( "mineInteractionPk.getProtein2()", protein2, mineInteractionPk.getProtein2() );
        assertNull( "mineInteractionPk.getProtein1()", mineInteractionPk.getProtein1() );
        assertNull( "mineInteractionPk.getInteraction()", mineInteractionPk.getInteraction() );
    }

    public void testHashCode2() throws Throwable {
        ProteinImpl protein1 = new ProteinImpl( null, null, "testMineInteractionPkShortLabel", null );
        InteractionImpl interaction = new InteractionImpl( new ArrayList(), new CvInteractionType( null, "testMineInteractionPkShortLabel" ), "testMineInteractionPkShortLabel", new Institution( "testMineInteractionPkShortLabel" ) );
        MineInteractionPk mineInteractionPk = new MineInteractionPk( protein1, protein1, interaction );
        int result = mineInteractionPk.hashCode();
        assertEquals( "result", 1262756091, result );
        assertSame( "mineInteractionPk.getProtein2()", protein1, mineInteractionPk.getProtein2() );
        assertSame( "mineInteractionPk.getProtein1()", protein1, mineInteractionPk.getProtein1() );
        assertSame( "mineInteractionPk.getInteraction()", interaction, mineInteractionPk.getInteraction() );
    }

    public void testSetInteraction() throws Throwable {
        MineInteractionPk mineInteractionPk = new MineInteractionPk();
        InteractionImpl interaction = ( InteractionImpl ) Mockingbird.getProxyObject( InteractionImpl.class );
        Mockingbird.enterTestMode();
        mineInteractionPk.setInteraction( interaction );
        assertSame( "mineInteractionPk.getInteraction()", interaction, mineInteractionPk.getInteraction() );
    }

    public void testSetProtein1() throws Throwable {
        MineInteractionPk mineInteractionPk = new MineInteractionPk( ( ProteinImpl ) Mockingbird.getProxyObject( ProteinImpl.class ), null, ( InteractionImpl ) Mockingbird.getProxyObject( InteractionImpl.class ) );
        ProteinImpl protein1 = ( ProteinImpl ) Mockingbird.getProxyObject( ProteinImpl.class );
        Mockingbird.enterTestMode();
        mineInteractionPk.setProtein1( protein1 );
        assertSame( "mineInteractionPk.getProtein1()", protein1, mineInteractionPk.getProtein1() );
    }

    public void testSetProtein2() throws Throwable {
        MineInteractionPk mineInteractionPk = new MineInteractionPk();
        ProteinImpl protein2 = ( ProteinImpl ) Mockingbird.getProxyObject( ProteinImpl.class );
        Mockingbird.enterTestMode();
        mineInteractionPk.setProtein2( protein2 );
        assertSame( "mineInteractionPk.getProtein2()", protein2, mineInteractionPk.getProtein2() );
    }
}

