/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.confidence.util;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.bridges.blast.model.UniprotAc;
import uk.ac.ebi.intact.confidence.model.*;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;

import java.util.*;

/**
 * TODO comment this
 *
 * @author Irina Armean (iarmean@ebi.ac.uk)
 * @version
 * @since <pre>14-Aug-2007</pre>
 */
public class InteractionGeneratorTest extends IntactBasicTestCase{

	private InteractionGenerator intGen;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		intGen = new InteractionGenerator();
		String filepath = InteractionGeneratorTest.class.getResource("swissprot_yeast_proteins.txt").getPath();  
		intGen.setProteinACs(new HashSet<String>(new DataMethods().readExact(filepath, false)));
		List<InteractionSimplified> highconf = initInteract(); 
		List<InteractionSimplified> lowconf = initInteract(); 
		List<InteractionSimplified> medconf = initInteract(); 
		intGen.setHighconfidence(highconf);
		intGen.setLowconfidence(lowconf);
		intGen.setMediumconfidence(medconf);
	}

	private List<InteractionSimplified> initInteract(){
		ProteinSimplified comp1 = new ProteinSimplified(new UniprotAc("P12345"), "prey");
		ProteinSimplified comp2 = new ProteinSimplified(new UniprotAc("Q12345"), "bait");
		ProteinSimplified comp3 = new ProteinSimplified(new UniprotAc("R12345"), "bait");
		ProteinSimplified comp4 = new ProteinSimplified(new UniprotAc("S12345"), "prey");
		
		InteractionSimplified int1 = new InteractionSimplified("int1", Arrays.asList(comp1, comp2));
		InteractionSimplified int2 = new InteractionSimplified("int1", Arrays.asList(comp3, comp4));
		
		return Arrays.asList(int1, int2);
	}
	
//	private List<Interaction> initInteract2(){
//		Interactor interactor1 = super.getMockBuilder().createProtein("P12345", "p1");
//		Interactor interactor2 = super.getMockBuilder().createProtein("Q12342", "q2");
//		Interactor interactor3 = super.getMockBuilder().createProtein("R12342", "r3");
//		Interactor interactor4 = super.getMockBuilder().createProtein("S12342",	"s4");
//		
//		Interaction interaction = super.getMockBuilder().createInteractionRandomBinary();
//		interaction.getComponents().clear();
//		interaction.addComponent(getMockBuilder().createComponentNeutral(interaction, interactor1));
//		interaction.addComponent(getMockBuilder().createComponentNeutral(interaction, interactor2));
//		
//		Interaction interaction1 = super.getMockBuilder().createInteractionRandomBinary();
//		interaction1.getComponents().clear();
//		interaction1.addComponent(getMockBuilder().createComponentNeutral(interaction1, interactor3));
//		interaction1.addComponent(getMockBuilder().createComponentNeutral(interaction1, interactor4));
//		
//		Interaction interaction2 = super.getMockBuilder().createInteractionRandomBinary();
//		return Arrays.asList(interaction, interaction1, interaction2);
//	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerate() throws Exception {
		List<InteractionSimplified> generated = intGen.generate(2);
		Assert.assertEquals(2, generated.size());
		//printOut(generated);
	}

	private void printOut(List<InteractionSimplified> generated) {
		for (InteractionSimplified interaction : generated) {
			String out = interaction.getAc() + " : ";
			List<ProteinSimplified> comps = interaction.getInteractors();
			out += ((ProteinSimplified)comps.toArray()[0]).getUniprotAc() + "<>";
			out += ((ProteinSimplified)comps.toArray()[1]).getUniprotAc();
			System.out.println(out);
		}
	}

    @Test
    public void testGenerateNewObjectSt() throws Exception {
        Set<Identifier> yeastProts = new HashSet<Identifier>();
        Identifier id1 = new UniprotIdentifierImpl("P12345");
        Identifier id2 = new UniprotIdentifierImpl("P12346");
        Identifier id3 = new UniprotIdentifierImpl("P12347");
        Identifier id4 = new UniprotIdentifierImpl("P12348");
        Identifier id5 = new UniprotIdentifierImpl("P12349");
        Identifier id6 = new UniprotIdentifierImpl("P12340");

        yeastProts.add( id1);
        yeastProts.add( id2);
        yeastProts.add( id3);
        yeastProts.add( id4);
        yeastProts.add( id5);
        yeastProts.add( id6);

        List<BinaryInteraction> forbidden = new ArrayList<BinaryInteraction>();
        forbidden.add( new BinaryInteraction(id1, id2, Confidence.HIGH));
        forbidden.add( new BinaryInteraction( id1, id3, Confidence.HIGH));
        forbidden.add( new BinaryInteraction( id1, id4, Confidence.UNKNOWN));
        forbidden.add( new BinaryInteraction( id1, id5, Confidence.LOW));
        forbidden.add( new BinaryInteraction( id1, id6, Confidence.LOW));

        List<BinaryInteraction> generated = intGen.generate( yeastProts,forbidden, 2);
        Assert.assertNotNull( generated); // should never be null
        Assert.assertEquals(2, generated.size());
        for(BinaryInteraction bi : generated){
            Assert.assertFalse( forbidden.contains( bi));
            Assert.assertFalse( bi.getFirstId().equals( id1));
        }
    }

}
