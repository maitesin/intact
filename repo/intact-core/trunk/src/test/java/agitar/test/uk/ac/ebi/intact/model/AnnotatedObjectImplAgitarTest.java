/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:25:48
 * Time to generate: 03:50.184 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;
import com.agitar.lib.mockingbird.Mockingbird;

import java.util.ArrayList;
import java.util.Collection;

public class AnnotatedObjectImplAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = AnnotatedObjectImpl.class;

    public void testAddAlias() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        cvGoNode.addAlias(/* (CvObjectAlias)? */ new BioSourceAlias( new Institution( "testAnnotatedObjectImplShortLabel1" ), new BioSource( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel", "20561" ), new CvAliasType( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplName" ) );
        assertEquals( "(CvGoNode) cvGoNode.getAliases().size()", 1, cvGoNode.getAliases().size() );
    }

    public void testAddReference() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        Reference reference = new Reference();
        cvTopic.addReference( reference );
        cvTopic.addReference( reference );
        assertEquals( "(CvTopic) cvTopic.references.size()", 1, ( ( CvTopic ) cvTopic ).references.size() );
    }

    public void testAddXref() throws Throwable {
        AnnotatedObjectImpl cvComponentRole = new CvComponentRole( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" );
        cvComponentRole.addXref(/* (CvObjectXref)? */ new PublicationXref( new Institution( "testAnnotatedObjectImplShortLabel" ), new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", "testAnnotatedObjectImplASecondaryId", "testAnnotatedObjectImplADatabaseRelease", new CvXrefQualifier( null, "testAnnotatedObjectImplShortLabel" ) ) );
        assertEquals( "(CvComponentRole) cvComponentRole.xrefs.size()", 1, ( ( CvComponentRole ) cvComponentRole ).xrefs.size() );
    }

    public void testClone() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( null, "testAnnotatedObjectImplShortLabel" );
        Collection arrayList = new ArrayList( 100 );
        arrayList.add( new BioSourceXref( new Institution( "testAnnotatedObjectImplShortLabel" ), new CvDatabase( null, "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", aCvXrefQualifier ) );
        Collection arrayList2 = new ArrayList( 1000 );
        arrayList2.add( new Annotation( new Institution( "testAnnotatedObjectImplShortLabel1" ), new CvTopic( null, "testAnnotatedObjectImplShortLabel" ) ) );
        AnnotatedObjectImpl cvFuzzyType = new CvFuzzyType( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" );
        cvFuzzyType.xrefs = arrayList;
        cvFuzzyType.annotations = arrayList2;
        CvFuzzyType result = ( CvFuzzyType ) cvFuzzyType.clone();
        assertFalse( "result.isCTerminal()", result.isCTerminal() );
        assertEquals( "(CvFuzzyType) cvFuzzyType.shortLabel", "testAnnotatedObjectI", ( ( CvFuzzyType ) cvFuzzyType ).shortLabel );
        assertSame( "(CvFuzzyType) cvFuzzyType.annotations", arrayList2, ( ( CvFuzzyType ) cvFuzzyType ).annotations );
    }

    public void testClone1() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        cvGoNode.addAlias(/* (CvObjectAlias)? */ new BioSourceAlias( new Institution( "testAnnotatedObjectImplShortLabel1" ), new BioSource( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel", "20561" ), new CvAliasType( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplName" ) );
        CvGoNode result = ( CvGoNode ) cvGoNode.clone();
        assertNotNull( "result", result );
        assertNull( "(CvGoNode) cvGoNode.shortLabel", ( ( CvGoNode ) cvGoNode ).shortLabel );
        assertEquals( "(CvGoNode) cvGoNode.annotations.size()", 0, ( ( CvGoNode ) cvGoNode ).annotations.size() );
    }

    public void testClone2() throws Throwable {
        Institution anOwner = new Institution( "testAnnotatedObjectImpl\rShortLabel" );
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( null, "testAnnotatedObjectImplShortLabel" );
        AnnotatedObjectImpl cvCompartment = new CvCompartment( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvCompartment.addAnnotation( new Annotation( null, new CvTopic( null, "testAnnotatedObjectImplShortLabel" ) ) );
        cvCompartment.addXref(/* (CvObjectXref)? */ new ExperimentXref( anOwner, new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", aCvXrefQualifier ) );
        cvCompartment.addAlias(/* (CvObjectAlias)? */ new InteractorAlias( new Institution( "testAnnotatedObjectImplShortLabel2" ), new SmallMoleculeImpl( "testAnnotatedObjectImplShortLabel", anOwner, null ), null, "testAnnotatedObjectImplName" ) );
        CvCompartment result = ( CvCompartment ) cvCompartment.clone();
        assertNotNull( "result", result );
        assertEquals( "(CvCompartment) cvCompartment.shortLabel", "testAnnotatedObjectI", ( ( CvCompartment ) cvCompartment ).shortLabel );
        assertEquals( "(CvCompartment) cvCompartment.annotations.size()", 1, ( ( CvCompartment ) cvCompartment ).annotations.size() );
    }

    public void testClone3() throws Throwable {
        Collection arrayList = new ArrayList( 100 );
        arrayList.add( new BioSourceXref( new Institution( "testAnnotatedObjectImplShortLabel" ), new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", "testAnnotatedObjectImplASecondaryId", "testAnnotatedObjectImplADatabaseRelease", new CvXrefQualifier( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ) ) );
        AnnotatedObjectImpl cvComponentRole = new CvComponentRole( new Institution( "testAnnotatedObjectImpl\rShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvComponentRole.xrefs = arrayList;
        CvComponentRole result = ( CvComponentRole ) cvComponentRole.clone();
        assertNotNull( "result", result );
        assertEquals( "(CvComponentRole) cvComponentRole.shortLabel", "testAnnotatedObjectI", ( ( CvComponentRole ) cvComponentRole ).shortLabel );
        assertEquals( "(CvComponentRole) cvComponentRole.annotations.size()", 0, ( ( CvComponentRole ) cvComponentRole ).annotations.size() );
    }

    public void testClone4() throws Throwable {
        Institution owner = new Institution( "testAnnotatedObjectImplShortLabel" );
        Collection arrayList = new ArrayList( 100 );
        arrayList.add( new Annotation( owner, new CvTopic( owner, "testAnnotatedObjectImplShortLabel" ) ) );
        AnnotatedObjectImpl cvJournal = new CvJournal( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" );
        cvJournal.annotations = arrayList;
        CvJournal result = ( CvJournal ) cvJournal.clone();
        assertNotNull( "result", result );
        assertEquals( "(CvJournal) cvJournal.shortLabel", "testAnnotatedObjectI", ( ( CvJournal ) cvJournal ).shortLabel );
        assertSame( "(CvJournal) cvJournal.annotations", arrayList, ( ( CvJournal ) cvJournal ).annotations );
    }

    public void testClone5() throws Throwable {
        AnnotatedObjectImpl cvFuzzyType = new CvFuzzyType( new Institution( "testAnnotatedObjectImpl\tShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvFuzzyType.addAlias(/* (CvObjectAlias)? */ new BioSourceAlias( new Institution( "testAnnotatedObjectImplShortLabel1" ), new BioSource( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel", "20561" ), new CvAliasType( new Institution( "testAnnotatedObjectImpl\nShortLabel" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplName" ) );
        cvFuzzyType.addXref(/* (CvObjectXref)? */ new PublicationXref( new Institution( "testAnnotatedObjectImplShortLabel" ), new CvDatabase( new Institution( "testAnnotatedObjectImpl\rShortLabel" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", "testAnnotatedObjectImplASecondaryId", "testAnnotatedObjectImplADatabaseRelease", new CvXrefQualifier( null, "testAnnotatedObjectImplShortLabel" ) ) );
        CvFuzzyType result = ( CvFuzzyType ) cvFuzzyType.clone();
        assertFalse( "result.isCTerminal()", result.isCTerminal() );
        assertEquals( "(CvFuzzyType) cvFuzzyType.shortLabel", "testAnnotatedObjectI", ( ( CvFuzzyType ) cvFuzzyType ).shortLabel );
        assertEquals( "(CvFuzzyType) cvFuzzyType.annotations.size()", 0, ( ( CvFuzzyType ) cvFuzzyType ).annotations.size() );
    }

    public void testClone6() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        CvGoNode result = ( CvGoNode ) cvGoNode.clone();
        assertNotNull( "result", result );
        assertNull( "(CvGoNode) cvGoNode.shortLabel", ( ( CvGoNode ) cvGoNode ).shortLabel );
        assertEquals( "(CvGoNode) cvGoNode.annotations.size()", 0, ( ( CvGoNode ) cvGoNode ).annotations.size() );
    }

    public void testGetFullName() throws Throwable {
        String result = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ).getFullName();
        assertNull( "result", result );
    }

    public void testGetFullName1() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvTopic.setFullName( "testAnnotatedObjectImplFullName" );
        String result = cvTopic.getFullName();
        assertEquals( "result", "testAnnotatedObjectImplFullName", result );
    }

    public void testGetReferences() throws Throwable {
        Collection arrayList = new ArrayList( 100 );
        AnnotatedObjectImpl complex = new Complex();
        complex.references = arrayList;
        Collection result = complex.getReferences();
        assertSame( "result", arrayList, result );
    }

    public void testGetShortLabel() throws Throwable {
        AnnotatedObjectImpl cvFuzzyType = new CvFuzzyType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvFuzzyType.setShortLabel( null );
        String result = cvFuzzyType.getShortLabel();
        assertNull( "result", result );
    }

    public void testGetShortLabel1() throws Throwable {
        String result = new CvFuzzyType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ).getShortLabel();
        assertEquals( "result", "testAnnotatedObjectI", result );
    }

    public void testRemoveAlias() throws Throwable {
        AnnotatedObjectImpl cvDatabase = new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" );
        cvDatabase.removeAlias(/* (CvObjectAlias)? */ new ComponentAlias( new Institution( "testAnnotatedObjectImpl\rShortLabel" ), new Component( new Institution( "testAnnotatedObjectImpl\tShortLabel" ), new InteractionImpl( new ArrayList(), new CvInteractionType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel", new Institution( "testAnnotatedObjectImpl\nShortLabel" ) ), new Complex(), new CvComponentRole( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ) ), new CvAliasType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplName" ) );
        assertEquals( "(CvDatabase) cvDatabase.getAliases().size()", 0, cvDatabase.getAliases().size() );
    }

    public void testRemoveAnnotation() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        cvGoNode.removeAnnotation( new Annotation( new Institution( "testAnnotatedObjectImplShortLabel" ), new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel1" ), "testAnnotatedObjectImplShortLabel" ) ) );
        assertEquals( "(CvGoNode) cvGoNode.annotations.size()", 0, ( ( CvGoNode ) cvGoNode ).annotations.size() );
    }

    public void testRemoveReference() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( null, "testAnnotatedObjectImplShortLabel" );
        Reference reference = new Reference();
        cvTopic.addReference( reference );
        cvTopic.removeReference( reference );
        assertEquals( "reference.getAnnotatedObjects().size()", 0, reference.getAnnotatedObjects().size() );
        assertFalse( "reference.getAnnotatedObjects().contains(cvTopic)", reference.getAnnotatedObjects().contains( cvTopic ) );
        assertEquals( "(CvTopic) cvTopic.references.size()", 0, ( ( CvTopic ) cvTopic ).references.size() );
    }

    public void testRemoveReference1() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvTopic.removeReference( new SubmissionRef() );
        assertEquals( "(CvTopic) cvTopic.references.size()", 0, ( ( CvTopic ) cvTopic ).references.size() );
    }

    public void testSetAliases() throws Throwable {
        Collection someAliases = new ArrayList( 100 );
        AnnotatedObjectImpl cvJournal = new CvJournal( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvJournal.setAliases( someAliases );
        assertSame( "(CvJournal) cvJournal.getAliases()", someAliases, cvJournal.getAliases() );
    }

    public void testSetAnnotations() throws Throwable {
        Collection someAnnotation = new ArrayList( 100 );
        AnnotatedObjectImpl cvDatabase = new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvDatabase.setAnnotations( someAnnotation );
        assertSame( "(CvDatabase) cvDatabase.annotations", someAnnotation, ( ( CvDatabase ) cvDatabase ).annotations );
    }

    public void testSetFullName() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvTopic.setFullName( "testAnnotatedObjectImplFullName" );
        assertEquals( "(CvTopic) cvTopic.fullName", "testAnnotatedObjectImplFullName", ( ( CvTopic ) cvTopic ).fullName );
    }

    public void testSetReferences() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        Collection someReferences = new ArrayList( 100 );
        cvGoNode.setReferences( someReferences );
        assertSame( "(CvGoNode) cvGoNode.references", someReferences, ( ( CvGoNode ) cvGoNode ).references );
    }

    public void testSetShortLabel() throws Throwable {
        AnnotatedObjectImpl cvTopic = new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvTopic.setShortLabel( "19CharactersXXXXXXX" );
        assertEquals( "(CvTopic) cvTopic.shortLabel", "19CharactersXXXXXXX", ( ( CvTopic ) cvTopic ).shortLabel );
    }

    public void testSetShortLabel1() throws Throwable {
        AnnotatedObjectImpl cvJournal = new CvJournal( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvJournal.setShortLabel( "20CharactersXXXXXXXX" );
        assertEquals( "(CvJournal) cvJournal.shortLabel", "20CharactersXXXXXXXX", ( ( CvJournal ) cvJournal ).shortLabel );
    }

    public void testSetShortLabel2() throws Throwable {
        AnnotatedObjectImpl cvIdentification = new CvIdentification( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvIdentification.setShortLabel( null );
        assertNull( "(CvIdentification) cvIdentification.shortLabel", ( ( CvIdentification ) cvIdentification ).shortLabel );
    }

    public void testSetXrefs() throws Throwable {
        AnnotatedObjectImpl cvGoNode = new CvGoNode();
        Collection someXrefs = new ArrayList( 100 );
        cvGoNode.setXrefs( someXrefs );
        assertSame( "(CvGoNode) cvGoNode.xrefs", someXrefs, ( ( CvGoNode ) cvGoNode ).xrefs );
    }

    public void testToString() throws Throwable {
        String result = new CvIdentification( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ).toString();
        assertEquals( "result", "null; owner=null; name=testAnnotatedObjectI; fullname=null", result );
    }

    public void testAddAliasThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl complex = new Complex();
        try {
            complex.addAlias( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(Complex) complex.getAliases().size()", 1, complex.getAliases().size() );
        }
    }

    public void testAddAnnotationThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl cvInteraction = new CvInteraction( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvInteraction.annotations = null;
        try {
            cvInteraction.addAnnotation( new Annotation( new Institution( "testAnnotatedObjectImplShortLabel1" ), new CvTopic( null, "testAnnotatedObjectImplShortLabel" ) ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertNull( "(CvInteraction) cvInteraction.annotations", ( ( CvInteraction ) cvInteraction ).annotations );
        }
    }

    public void testAddReferenceThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl cvFeatureIdentification = new CvFeatureIdentification( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvFeatureIdentification.references = null;
        Reference reference = new SubmissionRef();
        try {
            cvFeatureIdentification.addReference( reference );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertNull( "(CvFeatureIdentification) cvFeatureIdentification.references", ( ( CvFeatureIdentification ) cvFeatureIdentification ).references );
            assertNull( "(SubmissionRef) reference.getReferenceAc()", ( ( SubmissionRef ) reference ).getReferenceAc() );
        }
    }

    public void testAddXrefThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl complex = new Complex();
        try {
            complex.addXref( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(Complex) complex.xrefs.size()", 1, ( ( Complex ) complex ).xrefs.size() );
        }
    }

    public void testCloneThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl cvFuzzyType = new CvFuzzyType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvFuzzyType.addAnnotation( null );
        try {
            cvFuzzyType.clone();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(CvFuzzyType) cvFuzzyType.shortLabel", "testAnnotatedObjectI", ( ( CvFuzzyType ) cvFuzzyType ).shortLabel );
            assertEquals( "(CvFuzzyType) cvFuzzyType.annotations.size()", 1, ( ( CvFuzzyType ) cvFuzzyType ).annotations.size() );
        }
    }

    public void testCloneThrowsNullPointerException1() throws Throwable {
        AnnotatedObjectImpl cvInteractionType = new CvInteractionType((Institution) Mockingbird.getProxyObject(Institution.class), "testAnnotatedObjectImplShortLabel");
        cvInteractionType.setAliases(null);
        Mockingbird.enterTestMode();
        try {
            cvInteractionType.clone();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertThrownBy(AnnotatedObjectImpl.class, ex);
            assertEquals("(CvInteractionType) cvInteractionType.shortLabel", "testAnnotatedObjectI", ((CvInteractionType) cvInteractionType).shortLabel);
            assertEquals("(CvInteractionType) cvInteractionType.annotations.size()", 0, ((CvInteractionType) cvInteractionType).annotations.size());
        }
    }

    public void testCloneThrowsNullPointerException2() throws Throwable {
        AnnotatedObjectImpl cvInteraction = new CvInteraction( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvInteraction.annotations = null;
        try {
            cvInteraction.clone();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(CvInteraction) cvInteraction.shortLabel", "testAnnotatedObjectI", ( ( CvInteraction ) cvInteraction ).shortLabel );
            assertNull( "(CvInteraction) cvInteraction.annotations", ( ( CvInteraction ) cvInteraction ).annotations );
        }
    }

    public void testCloneThrowsNullPointerException3() throws Throwable {
        Collection someAliases = new ArrayList( 100 );
        someAliases.add( null );
        AnnotatedObjectImpl cvDatabase = new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvDatabase.setAliases( someAliases );
        try {
            cvDatabase.clone();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(CvDatabase) cvDatabase.shortLabel", "testAnnotatedObjectI", ( ( CvDatabase ) cvDatabase ).shortLabel );
            assertEquals( "(CvDatabase) cvDatabase.annotations.size()", 0, ( ( CvDatabase ) cvDatabase ).annotations.size() );
        }
    }

    public void testCloneThrowsNullPointerException4() throws Throwable {
        AnnotatedObjectImpl cvModificationType = new CvModificationType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvModificationType.xrefs = null;
        try {
            cvModificationType.clone();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(CvModificationType) cvModificationType.shortLabel", "testAnnotatedObjectI", ( ( CvModificationType ) cvModificationType ).shortLabel );
            assertEquals( "(CvModificationType) cvModificationType.annotations.size()", 0, ( ( CvModificationType ) cvModificationType ).annotations.size() );
        }
    }

    public void testRemoveAnnotationThrowsNullPointerException() throws Throwable {
        AnnotatedObjectImpl cvFuzzyType = new CvFuzzyType( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" );
        cvFuzzyType.annotations = null;
        try {
            cvFuzzyType.removeAnnotation( new Annotation( new Institution( "testAnnotatedObjectImplShortLabel1" ), new CvTopic( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" ) ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertNull( "(CvFuzzyType) cvFuzzyType.annotations", ( ( CvFuzzyType ) cvFuzzyType ).annotations );
        }
    }

    public void testRemoveReferenceThrowsNullPointerException() throws Throwable {
        Collection arrayList = new ArrayList( 100 );
        arrayList.add( null );
        AnnotatedObjectImpl complex = new Complex();
        complex.references = arrayList;
        try {
            complex.removeReference( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertSame( "(Complex) complex.references", arrayList, ( ( Complex ) complex ).references );
        }
    }

    public void testRemoveReferenceThrowsNullPointerException1() throws Throwable {
        AnnotatedObjectImpl complex = new Complex();
        complex.references = null;
        Reference reference = new SubmissionRef();
        try {
            complex.removeReference( reference );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertNull( "(Complex) complex.references", ( ( Complex ) complex ).references );
            assertNull( "(SubmissionRef) reference.getReferenceAc()", ( ( SubmissionRef ) reference ).getReferenceAc() );
        }
    }

    public void testRemoveXrefThrowsNullPointerException() throws Throwable {
        CvXrefQualifier aCvXrefQualifier = new CvXrefQualifier( null, "testAnnotatedObjectImplShortLabel" );
        AnnotatedObjectImpl cvAliasType = new CvAliasType( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        cvAliasType.xrefs = null;
        try {
            cvAliasType.removeXref(/* (CvObjectXref)? */ new BioSourceXref( new Institution( "testAnnotatedObjectImplShortLabel1" ), new CvDatabase( new Institution( "testAnnotatedObjectImplShortLabel2" ), "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplAPrimaryId", aCvXrefQualifier ) );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertNull( "(CvAliasType) cvAliasType.xrefs", ( ( CvAliasType ) cvAliasType ).xrefs );
        }
    }

    public void testSetShortLabelThrowsFieldTooLongException() throws Throwable {
        AnnotatedObjectImpl cvCompartment = new CvCompartment( new Institution( "testAnnotatedObjectImplShortLabel" ), "testAnnotatedObjectImplShortLabel" );
        try {
            cvCompartment.setShortLabel( "21CharactersXXXXXXXXX" );
            fail( "Expected FieldTooLongException to be thrown" );
        } catch ( FieldTooLongException ex ) {
            assertEquals( "ex.getMessage()", "Value too long for field shortLabel(20): 21CharactersXXXXXXXXX", ex.getMessage() );
            assertThrownBy( AnnotatedObjectImpl.class, ex );
            assertEquals( "(CvCompartment) cvCompartment.shortLabel", "testAnnotatedObjectI", ( ( CvCompartment ) cvCompartment ).shortLabel );
        }
    }
}

