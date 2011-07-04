/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:25:57
 * Time to generate: 03:25.671 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

import java.util.ArrayList;
import java.util.Collection;

public class ExperimentAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = Experiment.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testExptLabel" );
        BioSource source = new BioSource( null, "testExptLabel", "-100000" );
        Experiment experiment = new Experiment( owner, "testExptLabel", source );
        assertEquals( "experiment.xrefs.size()", 0, experiment.xrefs.size() );
        assertEquals( "experiment.getAliases().size()", 0, experiment.getAliases().size() );
        assertEquals( "experiment.shortLabel", "testExptLabel", experiment.getShortLabel() );
        assertSame( "experiment.getBioSource()", source, experiment.getBioSource() );
        assertEquals( "experiment.getInteractions().size()", 0, experiment.getInteractions().size() );
        assertEquals( "experiment.annotations.size()", 0, experiment.annotations.size() );
        assertSame( "experiment.getOwner()", owner, experiment.getOwner() );
    }

    public void testClone() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        Experiment result = ( Experiment ) shallowCopy.clone();
        assertNull( "result.getBioSourceAc()", result.getBioSourceAc() );
        assertEquals( "shallowCopy.shortLabel", "testExptLabel", shallowCopy.getShortLabel() );
        assertEquals( "shallowCopy.getInteractions().size()", 0, shallowCopy.getInteractions().size() );
        assertEquals( "shallowCopy.annotations.size()", 0, shallowCopy.annotations.size() );
    }

    public void testEquals() throws Throwable {
        boolean result = new Experiment( ( Institution ) new Institution( "testExptLabel" ).clone(), "testExptLabel", null ).equals( null );
        assertFalse( "result", result );
    }

    public void testEquals1() throws Throwable {
        boolean result = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( null, "testExptLabel", null ) ) ).equals( "testString" );
        assertFalse( "result", result );
    }

    public void testEquals2() throws Throwable {
        Experiment o = new Experiment( ( Institution ) new Institution( "testExptLabel" ).clone(), "testExptLabel", null );
        boolean result = o.equals( o );
        assertTrue( "result", result );
    }

    public void testGetAliases() throws Throwable {
        ArrayList result = ( ArrayList ) Experiment.getShallowCopy( Experiment.getShallowCopy( Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( null, "testExptLabel", null ) ) ) ) ).getAliases();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetAnnotations() throws Throwable {
        ArrayList result = ( ArrayList ) Experiment.getShallowCopy( Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( ( Institution ) new Institution( "testExptLabel" ).clone(), "testExptLabel", null ) ) ) ).getAnnotations();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testGetShallowCopy() throws Throwable {
        Experiment result = Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        assertNull( "result.getBioSourceAc()", result.getBioSourceAc() );
    }

    public void testGetXrefs() throws Throwable {
        ArrayList result = ( ArrayList ) Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ).getXrefs();
        assertEquals( "result.size()", 0, result.size() );
    }

    public void testHashCode() throws Throwable {
        BioSource source = new BioSource( new Institution( "testExptLabel" ), "testExptLabel", "-100000" );
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel1" ), "testExptLabel", source ) ) );
        int result = shallowCopy.hashCode();
        assertEquals( "result", -1908986819, result );
        assertEquals( "shallowCopy.xrefs.size()", 0, shallowCopy.xrefs.size() );
        assertEquals( "shallowCopy.shortLabel", "testExptLabel", shallowCopy.getShortLabel() );
        assertSame( "shallowCopy.getBioSource()", source, shallowCopy.getBioSource() );
        assertNull( "shallowCopy.ac", shallowCopy.getAc());
        assertNull( "shallowCopy.fullName", shallowCopy.getFullName());
        assertNull( "shallowCopy.getPublication()", shallowCopy.getPublication() );
    }

    public void testSetBioSource() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel1" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ) );
        BioSource bioSource = new BioSource( new Institution( "testExptLabel" ), "testExptLabel1", "-41825" );
        shallowCopy.setBioSource( bioSource );
        assertEquals( "shallowCopy.getBioSource().getTaxId()", "-41825", shallowCopy.getBioSource().getTaxId() );
        assertSame( "shallowCopy.getBioSource()", bioSource, shallowCopy.getBioSource() );
    }

    public void testSetBioSourceAc() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        shallowCopy.setBioSourceAc( "testExperimentAc" );
        assertEquals( "shallowCopy.getBioSourceAc()", "testExperimentAc", shallowCopy.getBioSourceAc() );
    }

    public void testSetCvIdentification() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        CvIdentification cvIdentification = new CvIdentification( new Institution( "testExptLabel1" ), "testExptLabel" );
        shallowCopy.setCvIdentification( cvIdentification );
        assertSame( "shallowCopy.getCvIdentification()", cvIdentification, shallowCopy.getCvIdentification() );
    }

    public void testSetCvIdentificationAc() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ) );
        shallowCopy.setCvIdentificationAc( "testExperimentAc" );
        assertEquals( "shallowCopy.cvIdentificationAc", "testExperimentAc", getPrivateField( shallowCopy, "cvIdentificationAc" ) );
    }

    public void testSetCvInteraction() throws Throwable {
        CvInteraction cvInteraction = new CvInteraction( new Institution( "testExptLabel" ), "testExptLabel" );
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel1" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ) );
        shallowCopy.setCvInteraction( cvInteraction );
        assertSame( "shallowCopy.getCvInteraction()", cvInteraction, shallowCopy.getCvInteraction() );
    }

    public void testSetCvInteractionAc() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ) );
        shallowCopy.setCvInteractionAc( "testExperimentAc" );
        assertEquals( "shallowCopy.getCvInteractionAc()", "testExperimentAc", shallowCopy.getCvInteractionAc() );
    }

    public void testSetInteractions() throws Throwable {
        Collection someInteraction = new ArrayList( 100 );
        Experiment shallowCopy = Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        shallowCopy.setInteractions( someInteraction );
        assertSame( "shallowCopy.getInteractions()", someInteraction, shallowCopy.getInteractions() );
    }

    public void testSetPublication() throws Throwable {
        Institution owner = new Institution( "testExptLabel" );
        Experiment shallowCopy = Experiment.getShallowCopy( new Experiment( owner, "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) );
        Publication publication = new Publication( owner, "22773" );
        shallowCopy.setPublication( publication );
        assertSame( "shallowCopy.getPublication()", publication, shallowCopy.getPublication() );
    }

    public void testSetRelatedExperiment() throws Throwable {
        Experiment shallowCopy = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( new Institution( "testExptLabel1" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) ) ) );
        Experiment experiment = Experiment.getShallowCopy( Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( null, "testExptLabel1", new BioSource( new Institution( "testExptLabel" ), "testExptLabel1", "-41825" ) ) ) ) );
        shallowCopy.setRelatedExperiment( experiment );
        assertSame( "shallowCopy.getRelatedExperiment()", experiment, shallowCopy.getRelatedExperiment() );
    }

//    public void testToString() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", null );
//        CvInteraction cvInteraction = ( CvInteraction ) Mockingbird.getProxyObject( CvInteraction.class );
//        experiment.setCvInteraction( cvInteraction );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvInteraction.getShortLabel(), "identity" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: -\nCvIdentification: -\nCvInteraction: identity\n]", result );
//        assertInvoked( cvInteraction, "getShortLabel" );
//    }

//    public void testToString1() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", ( BioSource ) Mockingbird.getProxyObject( BioSource.class ) );
//        BioSource bioSource = ( BioSource ) Mockingbird.getProxyObject( BioSource.class );
//        experiment.setBioSource( bioSource );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( bioSource.getShortLabel(), "19CharactersXXXXXXX" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: 19CharactersXXXXXXX\nCvIdentification: -\nCvInteraction: NOT SPECIFIED\n]", result );
//        assertInvoked( bioSource, "getShortLabel" );
//    }

//    public void testToString2() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", null );
//        CvIdentification cvIdentification = ( CvIdentification ) Mockingbird.getProxyObject( CvIdentification.class );
//        experiment.setCvIdentification( cvIdentification );
//        BioSource bioSource = ( BioSource ) Mockingbird.getProxyObject( BioSource.class );
//        experiment.setBioSource( bioSource );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( bioSource.getShortLabel(), "20CharactersXXXXXXXX" );
//        Mockingbird.setReturnValue( cvIdentification.getShortLabel(), ":" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: 20CharactersXXXXXXXX\nCvIdentification: :\nCvInteraction: NOT SPECIFIED\n]", result );
//        assertInvoked( bioSource, "getShortLabel" );
//        assertInvoked( cvIdentification, "getShortLabel" );
//    }

//    public void testToString3() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", ( BioSource ) Mockingbird.getProxyObject( BioSource.class ) );
//        CvIdentification cvIdentification = ( CvIdentification ) Mockingbird.getProxyObject( CvIdentification.class );
//        experiment.setCvIdentification( cvIdentification );
//        experiment.setBioSource( null );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvIdentification.getShortLabel(), ":" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: -\nCvIdentification: :\nCvInteraction: NOT SPECIFIED\n]", result );
//        assertInvoked( cvIdentification, "getShortLabel" );
//    }

//    public void testToString4() throws Throwable {
//        String result = Experiment.getShallowCopy( Experiment.getShallowCopy( new Experiment( null, "testExptLabel", null ) ) ).toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: -\nCvIdentification: -\nCvInteraction: NOT SPECIFIED\n]", result );
//    }

//    public void testToString5() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", ( BioSource ) Mockingbird.getProxyObject( BioSource.class ) );
//        BioSource bioSource = ( BioSource ) Mockingbird.getProxyObject( BioSource.class );
//        experiment.setBioSource( bioSource );
//        CvInteraction cvInteraction = ( CvInteraction ) Mockingbird.getProxyObject( CvInteraction.class );
//        experiment.setCvInteraction( cvInteraction );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( bioSource.getShortLabel(), "; fullnIme=" );
//        Mockingbird.setReturnValue( cvInteraction.getShortLabel(), "Illegal Capacity:" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: ; fullnIme=\nCvIdentification: -\nCvInteraction: Illegal Capacity:\n]", result );
//        assertInvoked( bioSource, "getShortLabel" );
//        assertInvoked( cvInteraction, "getShortLabel" );
//    }

//    public void testToString6() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", ( BioSource ) Mockingbird.getProxyObject( BioSource.class ) );
//        CvIdentification cvIdentification = ( CvIdentification ) Mockingbird.getProxyObject( CvIdentification.class );
//        experiment.setCvIdentification( cvIdentification );
//        BioSource bioSource = ( BioSource ) Mockingbird.getProxyObject( BioSource.class );
//        experiment.setBioSource( bioSource );
//        CvInteraction cvInteraction = ( CvInteraction ) Mockingbird.getProxyObject( CvInteraction.class );
//        experiment.setCvInteraction( cvInteraction );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( bioSource.getShortLabel(), "; fullnIme=" );
//        Mockingbird.setReturnValue( cvIdentification.getShortLabel(), ":" );
//        Mockingbird.setReturnValue( cvInteraction.getShortLabel(), "Illegal Capacity:" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: ; fullnIme=\nCvIdentification: :\nCvInteraction: Illegal Capacity:\n]", result );
//        assertInvoked( bioSource, "getShortLabel" );
//        assertInvoked( cvInteraction, "getShortLabel" );
//        assertInvoked( cvIdentification, "getShortLabel" );
//    }

//    public void testToString7() throws Throwable {
//        Experiment experiment = new Experiment( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testExptLabel", null );
//        CvIdentification cvIdentification = ( CvIdentification ) Mockingbird.getProxyObject( CvIdentification.class );
//        experiment.setCvIdentification( cvIdentification );
//        CvInteraction cvInteraction = ( CvInteraction ) Mockingbird.getProxyObject( CvInteraction.class );
//        experiment.setCvInteraction( cvInteraction );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( cvIdentification.getShortLabel(), null );
//        Mockingbird.setReturnValue( cvInteraction.getShortLabel(), "identity" );
//        Mockingbird.enterTestMode();
//        String result = experiment.toString();
//        assertEquals( "result", "Experiment [AC: null Shortlabel: testExperimentShortL BioSource: -\nCvIdentification: null\nCvInteraction: identity\n]", result );
//        assertInvoked( cvInteraction, "getShortLabel" );
//        assertInvoked( cvIdentification, "getShortLabel" );
//    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new Experiment( new Institution( "testExptLabel" ), null, new BioSource( null, "testExptLabel", "-100000" ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testSetInteractionsThrowsIllegalArgumentException() throws Throwable {
        Experiment experiment = new Experiment( new Institution( "testExptLabel" ), "testExptLabel", new BioSource( null, "testExptLabel", "-100000" ) );
        try {
            experiment.setInteractions( null );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Interactions must not be null.", ex.getMessage() );
            assertThrownBy( Experiment.class, ex );
            assertEquals( "experiment.getInteractions().size()", 0, experiment.getInteractions().size() );
        }
    }
}

