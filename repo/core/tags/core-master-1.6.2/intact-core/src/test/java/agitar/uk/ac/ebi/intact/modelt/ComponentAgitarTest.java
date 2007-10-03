/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:30:51
 * Time to generate: 03:11.883 seconds
 *
 */

package agitar.uk.ac.ebi.intact.modelt;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.Component;

public class ComponentAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = Component.class;

//    public void testConstructor() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        Interaction interaction = new InteractionImpl( new HashSet(), new CvInteractionType( owner, "testComponentShortLabel" ), new CvInteractorType( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null );
//        CvExperimentalRole role = new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" );
//        Interactor interactor = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", owner );
//        Institution owner2 = new Institution( "testComponent\nShortLabel" );
//        Component component = new Component( owner2, "testComponentShortLabel", interaction, interactor, role );
//        assertSame( "component.getInteraction()", interaction, component.getInteraction() );
//        assertEquals( "component.xrefs.size()", 0, component.xrefs.size() );
//        assertEquals( "component.getStoichiometry()", 0.0F, component.getStoichiometry(), 1.0E-6F );
//        assertEquals( "component.getAliases().size()", 0, component.getAliases().size() );
//        assertEquals( "component.getEvidences().size()", 0, component.getEvidences().size() );
//        assertEquals( "component.shortLabel", "N/A", component.getShortLabel() );
//        assertSame( "component.getCvComponentRole()", role, component.getCvComponentRole() );
//        assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//        assertEquals( "component.annotations.size()", 0, component.annotations.size() );
//        assertSame( "component.getOwner()", owner2, component.getOwner() );
//        assertEquals( "component.references.size()", 0, component.references.size() );
//        assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//    }

//    public void testConstructor1() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        CvInteractionType type = new CvInteractionType( owner, "testComponentShortLabel" );
//        CvExperimentalRole role = new CvExperimentalRole( owner, "testComponentShortLabel" );
//        Institution owner2 = new Institution( "testComponentShortLabel1" );
//        Interaction interaction = new InteractionImpl( new HashSet(), type, new CvInteractorType( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null );
//        Interactor interactor = new Complex();
//        Component component = new Component( owner2, interaction, interactor, role );
//        assertEquals( "component.xrefs.size()", 0, component.xrefs.size() );
//        assertSame( "component.getInteraction()", interaction, component.getInteraction() );
//        assertEquals( "component.getAliases().size()", 0, component.getAliases().size() );
//        assertEquals( "component.getStoichiometry()", 0.0F, component.getStoichiometry(), 1.0E-6F );
//        assertEquals( "component.getEvidences().size()", 0, component.getEvidences().size() );
//        assertEquals( "component.shortLabel", "N/A", component.getShortLabel() );
//        assertSame( "component.getCvComponentRole()", role, component.getCvComponentRole() );
//        assertEquals( "component.annotations.size()", 0, component.annotations.size() );
//        assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//        assertSame( "component.getOwner()", owner2, component.getOwner() );
//        assertEquals( "component.references.size()", 0, component.references.size() );
//        assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//    }

//    public void testClone() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        Institution owner2 = new Institution( "testComponentShortLabel1" );
//        Interaction interaction = new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", owner2 );
//        Interaction interaction2 = new InteractionImpl( new HashSet(), new CvInteractionType( owner, "testComponentShortLabel" ), new CvInteractorType( owner2, "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null );
//        Interactor interactor = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponent\nShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", owner );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel", interaction2, interactor, new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ) );
//        component.addBindingDomain( new Feature( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel", new Component( new Institution( "testComponent\nShortLabel" ), interaction, new InteractionImpl( new ArrayList( 100 ), new ArrayList( 1000 ), new CvInteractionType( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel2" ), "testComponentShortLabel", new Institution( "testComponent\tShortLabel" ) ), new CvExperimentalRole( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ) ), new CvFeatureType( new Institution( "testComponent\tShortLabel" ), "testComponentShortLabel" ) ) );
//        Component result = ( Component ) component.clone();
//        assertNull( "result.getCvComponentRoleAc()", result.getCvComponentRoleAc() );
//        assertNull( "component.getInteractionAc()", component.getInteractionAc() );
//        assertSame( "component.getInteraction()", interaction2, component.getInteraction() );
//        assertNull( "component.getInteractorAc()", component.getInteractorAc() );
//        assertEquals( "component.shortLabel", "N/A", component.getShortLabel() );
//        assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//        assertEquals( "component.annotations.size()", 0, component.annotations.size() );
//        assertEquals( "component.getBindingDomains().size()", 1, component.getBindingDomains().size() );
//    }

//    public void testClone1() throws Throwable {
//        Interaction interaction = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ), "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        Institution owner = new Institution( "testComponent\rShortLabel" );
//        Interactor interactor = new SmallMoleculeImpl( "testComponentShortLabel", new Institution( "testComponent\nShortLabel" ), new CvInteractorType( owner, "testComponentShortLabel" ) );
//        Component component = new Component( owner, interaction, interactor, new CvExperimentalRole( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ) );
//        Component result = ( Component ) component.clone();
//        assertNull( "result.getCvComponentRoleAc()", result.getCvComponentRoleAc() );
//        assertNull( "component.getInteractionAc()", component.getInteractionAc() );
//        assertSame( "component.getInteraction()", interaction, component.getInteraction() );
//        assertNull( "component.getInteractorAc()", component.getInteractorAc() );
//        assertEquals( "component.shortLabel", "N/A", component.getShortLabel() );
//        assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//        assertEquals( "component.annotations.size()", 0, component.annotations.size() );
//        assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//    }

//    public void testEquals() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ),
//                                             ( Interaction ) Mockingbird.getProxyObject( Interaction.class ),
//                                             ( Interactor ) Mockingbird.getProxyObject( Interactor.class ),
//                                             ( CvComponentRole ) Mockingbird.getProxyObject( CvComponentRole.class ) );
//        Component o = ( Component ) Mockingbird.getProxyObject( Component.class );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( o.getInteractor(), Mockingbird.getProxyObject( Interactor.class ) );
//        Mockingbird.enterTestMode();
//        boolean result = component.equals( o );
//        assertFalse( "result", result );
//        assertInvoked( o, "getInteractor" );
//    }

//    public void testEquals1() throws Throwable {
//        Interaction interaction = ( Interaction ) Mockingbird.getProxyObject( Interaction.class );
//        Interactor interactor = ( Interactor ) Mockingbird.getProxyObject( Interactor.class );
//        Component component = new Component( null, "testComponentShortLabel", interaction, interactor, ( CvComponentRole ) Mockingbird.getProxyObject( CvComponentRole.class ) );
//        Component o = ( Component ) Mockingbird.getProxyObject( Component.class );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( o.getInteractor(), interactor );
//        Mockingbird.setReturnValue( o.getInteraction(), interaction );
//        Mockingbird.enterTestMode();
//        boolean result = component.equals( o );
//        assertFalse( "result", result );
//        assertInvoked( o, "getInteractor" );
//        assertInvoked( o, "getInteraction" );
//    }

//    public void testGetAliases() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), "testComponentShortLabel", ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Mockingbird.enterTestMode();
//        ArrayList result = ( ArrayList ) component.getAliases();
//        assertEquals( "result.size()", 0, result.size() );
//    }
//
//    public void testGetAnnotations() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Mockingbird.enterTestMode();
//        ArrayList result = ( ArrayList ) component.getAnnotations();
//        assertEquals( "result.size()", 0, result.size() );
//    }
//
//    public void testHasStoichiometry() throws Throwable {
//        Component component = new Component( null, "testComponentShortLabel", ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Mockingbird.enterTestMode();
//        boolean result = component.hasStoichiometry();
//        assertFalse( "result", result );
//    }
//
//    public void testHashCode() throws Throwable {
//        CvExperimentalRole role = new CvExperimentalRole( new Institution( "testComponentShortLabel" ), "testComponentShortLabel" );
//        Interaction interaction = new InteractionImpl( new ArrayList( 100 ), new ArrayList( 1000 ), ( CvInteractionType ) null, "testComponentShortLabel", ( Institution ) null );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel", interaction, new InteractionImpl( new ArrayList( 0 ), ( CvInteractionType ) null, ( CvInteractorType ) null, "testComponentShortLabel", ( Institution ) null ), role );
//        int result = component.hashCode();
//        assertEquals( "result", -764982892, result );
//        assertSame( "component.getInteraction()", interaction, component.getInteraction() );
//        assertSame( "component.getCvComponentRole()", role, component.getCvComponentRole() );
//    }
//
//    public void testRemoveBindingDomain() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Feature feature = ( Feature ) Mockingbird.getProxyObject( Feature.class );
//        Mockingbird.enterTestMode();
//        component.removeBindingDomain( feature );
//        assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//    }
//
//    public void testSetBindingDomains() throws Throwable {
//        Collection someBindingDomain = new ArrayList( 100 );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), new InteractionImpl( new ArrayList( 1000 ), new CvInteractionType( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", new Institution( "testComponent\nShortLabel" ) ), new InteractionImpl( new ArrayList( 0 ), new CvInteractionType( ( Institution ) new Institution( "testComponentShortLabel" ).clone(), "testComponentShortLabel" ), "testComponentShortLabel1", null ), new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ) );
//        component.setBindingDomains( someBindingDomain );
//        assertSame( "component.getBindingDomains()", someBindingDomain, component.getBindingDomains() );
//    }
//
//    public void testSetCvComponentRole() throws Throwable {
//        Interaction interaction = new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), interaction, new Complex(), new CvExperimentalRole( null, "testComponentShortLabel" ) );
//        CvExperimentalRole cvComponentRole = new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel1" );
//        component.setCvComponentRole( cvComponentRole );
//        assertSame( "component.getCvComponentRole()", cvComponentRole, component.getCvComponentRole() );
//    }
//
//    public void testSetCvComponentRoleAc() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Mockingbird.enterTestMode();
//        component.setCvComponentRoleAc( "testComponentAc" );
//        assertEquals( "component.getCvComponentRoleAc()", "testComponentAc", component.getCvComponentRoleAc() );
//    }
//
//    public void testSetExpressedIn() throws Throwable {
//        Interaction interaction = new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        BioSource expressedIn = new BioSource( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel", "-46052" );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), interaction, new Complex(), new CvExperimentalRole( null, "testComponentShortLabel" ) );
//        component.setExpressedIn( expressedIn );
//        assertSame( "component.getExpressedIn()", expressedIn, component.getExpressedIn() );
//    }
//
//    public void testSetExpressedInAc() throws Throwable {
//        Interaction interaction = new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), interaction, new Complex(), new CvExperimentalRole( null, "testComponentShortLabel" ) );
//        component.setExpressedInAc( "testComponentAc" );
//        assertEquals( "component.getExpressedInAc()", "testComponentAc", component.getExpressedInAc() );
//    }
//
//    public void testSetInteraction() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        CvInteractionType type = new CvInteractionType( owner, "testComponentShortLabel" );
//        CvInteractorType interactorType = new CvInteractorType( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" );
//        Interaction interaction = new InteractionImpl( new HashSet(), type, interactorType, "testComponentShortLabel", ( Institution ) null );
//        Interactor interactor = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", owner );
//        Collection experiments = new ArrayList( 100 );
//        Interaction interaction2 = new InteractionImpl( experiments, type, interactorType, "testComponentShortLabel1", owner );
//        Component component = new Component( new Institution( "testComponent\nShortLabel" ), "testComponentShortLabel", interaction, interactor, new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ) );
//        component.setInteraction( interaction2 );
//        assertSame( "component.getInteraction().getExperiments()", experiments, ( ( InteractionImpl ) component.getInteraction() ).getExperiments() );
//        assertSame( "component.getInteraction()", interaction2, component.getInteraction() );
//    }
//
//    public void testSetInteractionAc() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        Component component = new Component( new Institution( "testComponent\nShortLabel" ), "testComponentShortLabel", new InteractionImpl( new HashSet(), new CvInteractionType( owner, "testComponentShortLabel" ), new CvInteractorType( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null ), new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", owner ), new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ) );
//        component.setInteractionAc( "testComponentAc" );
//        assertEquals( "component.getInteractionAc()", "testComponentAc", component.getInteractionAc() );
//    }
//
//    public void testSetInteractionForClone() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        Interaction interactor = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponent\rShortLabel" ), "testComponentShortLabel1" ), "testComponentShortLabel", owner );
//        Component component = new Component( new Institution( "testComponent\nShortLabel" ), "testComponentShortLabel", new InteractionImpl( new HashSet(), new CvInteractionType( owner, "testComponentShortLabel" ), new CvInteractorType( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null ), interactor, new CvExperimentalRole( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ) );
////        Object sequenceStartingFrom = callPrivateMethod( "uk.ac.ebi.intact.model.Range", "getSequenceStartingFrom", new Class[]{String.class, int.class}, null, new Object[]{"Y<", new Integer( 1 )} );
//
//        super.callPrivateMethod("uk.ac.ebi.intact.model.Component", "setInteractionForClone", new Class[]{Interaction.class}, component, new Object[]{interactor} );
//        assertSame( "component.getInteraction()", interactor, component.getInteraction() );
//    }
//
//    public void testSetInteractor() throws Throwable {
//        Interaction interaction = new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        Institution owner = new Institution( "testComponentShortLabel2" );
//        BioSource source = new BioSource( owner, "testComponentShortLabel", "-46052" );
//        Component component = new Component( new Institution( "testComponentShortLabel1" ), interaction, new Complex(), new CvExperimentalRole( null, "testComponentShortLabel" ) );
//        Interactor interactor = new NucleicAcidImpl( new Institution( "testComponent\rShortLabel" ), source, "testComponentShortLabel", new CvInteractorType( owner, "testComponentShortLabel" ) );
//        component.setInteractor( interactor );
//        assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//    }
//
//    public void testSetInteractorAc() throws Throwable {
//        Institution owner = new Institution( "testComponent\rShortLabel" );
//        Component component = new Component( owner, new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ), "testComponentShortLabel", new Institution( "testComponentShortLabel" ) ), new SmallMoleculeImpl( "testComponentShortLabel", new Institution( "testComponent\nShortLabel" ), new CvInteractorType( owner, "testComponentShortLabel" ) ), new CvExperimentalRole( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ) );
//        component.setInteractorAc( "testComponentAc" );
//        assertEquals( "component.getInteractorAc()", "testComponentAc", component.getInteractorAc() );
//    }
//
//    public void testSetStoichiometry() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        Institution owner2 = new Institution( "testComponentShortLabel1" );
//        CvInteractorType interactorType = new CvInteractorType( owner2, "testComponentShortLabel" );
//        Component component = new Component( new Institution( "testComponent\nShortLabel" ), "testComponentShortLabel", new InteractionImpl( new ArrayList( 100 ), new CvInteractionType( owner, "testComponentShortLabel" ), interactorType, "testComponentShortLabel", owner ), new ProteinImpl( new Institution( "testComponent\rShortLabel" ), new BioSource( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel", "-46052" ), "testComponentShortLabel" ), new CvExperimentalRole( owner2, "testComponentShortLabel" ) );
//        component.setStoichiometry( 100.0F );
//        assertEquals( "component.getStoichiometry()", 100.0F, component.getStoichiometry(), 1.0E-6F );
//    }

//    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
//        Institution owner = ( Institution ) Mockingbird.getProxyObject( Institution.class );
//        Interaction interaction = ( Interaction ) Mockingbird.getProxyObject( Interaction.class );
//        Interactor interactor = ( Interactor ) Mockingbird.getProxyObject( Interactor.class );
//        CvExperimentalRole role = ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class );
//        Mockingbird.enterTestMode();
//        try {
//            new Component( owner, "", interaction, interactor, role );
//            fail( "Expected IllegalArgumentException to be thrown" );
//        } catch ( IllegalArgumentException ex ) {
//            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
//            assertThrownBy( AnnotatedObjectUtils.class, ex );
//        }
//    }
//
//    public void testConstructorThrowsNullPointerException() throws Throwable {
//        try {
//            new Component( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel", null, new Complex(), new CvExperimentalRole( new Institution( "testComponentShortLabel" ), "testComponentShortLabel" ) );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertEquals( "ex.getMessage()", "valid Component must have an Interaction set!", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//        }
//    }
//
//    public void testConstructorThrowsNullPointerException1() throws Throwable {
//        try {
//            new Component( new Institution( "testComponentShortLabel" ), "testComponentShortLabel", new InteractionImpl( new ArrayList(), new ArrayList(), null, null, "testComponentShortLabel", new Institution( "testComponentShortLabel1" ) ), new Complex(), null );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertEquals( "ex.getMessage()", "valid Component must have a role set (ie a CvComponentRole)!", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//        }
//    }
//
//    public void testConstructorThrowsNullPointerException2() throws Throwable {
//        Institution owner = new Institution( "testComponentShortLabel" );
//        CvInteractionType type = new CvInteractionType( owner, "testComponentShortLabel" );
//        try {
//            new Component( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel", new InteractionImpl( new HashSet(), type, new CvInteractorType( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ), "testComponentShortLabel", ( Institution ) null ), null, new CvExperimentalRole( owner, "testComponentShortLabel" ) );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertEquals( "ex.getMessage()", "valid Component must have an Interactor (eg Protein) set!", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//        }
//    }
//
//    public void testConstructorThrowsNullPointerException3() throws Throwable {
//        try {
//            new Component( new Institution( "testComponentShortLabel1" ), null, new Complex(), new CvExperimentalRole( new Institution( "testComponentShortLabel" ), "testComponentShortLabel" ) );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertEquals( "ex.getMessage()", "valid Component must have an Interaction set!", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//        }
//    }
//
//    public void testCloneThrowsNullPointerException() throws Throwable {
//        Interaction interaction = new InteractionImpl( new HashSet(), new CvInteractionType( new Institution( "testComponentShortLabel2" ), "testComponentShortLabel" ), "testComponentShortLabel", new Institution( "testComponentShortLabel" ) );
//        Institution owner = new Institution( "testComponent\rShortLabel" );
//        Interactor interactor = new SmallMoleculeImpl( "testComponentShortLabel", new Institution( "testComponent\nShortLabel" ), new CvInteractorType( owner, "testComponentShortLabel" ) );
//        Component component = new Component( owner, interaction, interactor, new CvExperimentalRole( new Institution( "testComponentShortLabel1" ), "testComponentShortLabel" ) );
//        component.addAnnotation( null );
//        try {
//            component.clone();
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertNull( "ex.getMessage()", ex.getMessage() );
//            assertThrownBy( AnnotatedObjectTest.class, ex );
//            assertNull( "component.getInteractionAc()", component.getInteractionAc() );
//            assertSame( "component.getInteraction()", interaction, component.getInteraction() );
//            assertNull( "component.getInteractorAc()", component.getInteractorAc() );
//            assertEquals( "component.shortLabel", "N/A", component.getShortLabel() );
//            assertSame( "component.getInteractor()", interactor, component.getInteractor() );
//            assertEquals( "component.annotations.size()", 1, component.annotations.size() );
//            assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//        }
//    }

//    public void testRemoveBindingDomainThrowsNullPointerException() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvComponentRole ) Mockingbird.getProxyObject( CvComponentRole.class ) );
//        Collection someBindingDomain = ( Collection ) Mockingbird.getProxyObject( Collection.class );
//        component.setBindingDomains( someBindingDomain );
//        Mockingbird.enterRecordingMode();
//        Mockingbird.setReturnValue( false, someBindingDomain, "remove(Ljava/lang/Object;)Z", new Object[]{null}, Boolean.TRUE, 1 );
//        Mockingbird.enterTestMode();
//        try {
//            component.removeBindingDomain( null );
//            fail( "Expected NullPointerException to be thrown" );
//        } catch ( NullPointerException ex ) {
//            assertNull( "ex.getMessage()", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//            assertSame( "component.getBindingDomains()", someBindingDomain, component.getBindingDomains() );
//            assertInvoked( someBindingDomain, "remove", new Object[]{null} );
//        }
//    }

//    public void testSetBindingDomainsThrowsIllegalArgumentException() throws Throwable {
//        Component component = new Component( ( Institution ) Mockingbird.getProxyObject( Institution.class ), ( Interaction ) Mockingbird.getProxyObject( Interaction.class ), ( Interactor ) Mockingbird.getProxyObject( Interactor.class ), ( CvExperimentalRole ) Mockingbird.getProxyObject( CvExperimentalRole.class ) );
//        Mockingbird.enterTestMode();
//        try {
//            component.setBindingDomains( null );
//            fail( "Expected IllegalArgumentException to be thrown" );
//        } catch ( IllegalArgumentException ex ) {
//            assertEquals( "ex.getMessage()", "features cannot be null.", ex.getMessage() );
//            assertThrownBy( Component.class, ex );
//            assertEquals( "component.getBindingDomains().size()", 0, component.getBindingDomains().size() );
//        }
//    }

    public void testDummy() {
        // just so JUnit stops whinging ...
    }
}

