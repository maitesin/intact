package uk.ac.ebi.intact.psimitab;

import org.junit.*;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.model.*;

import java.util.Collection;

/**
 * IntactTab2Xml Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @since 2.0.2
 * @version $Id$
 */
public class IntactTab2XmlTest {

    @Test
    public void convert() throws Exception {
        IntactPsimiTabReader reader = new IntactPsimiTabReader( true );
        final Collection<BinaryInteraction> mitabInteractions =
                reader.read( IntactTab2XmlTest.class.getResourceAsStream( "/mitab_samples/sample_dgi.tsv" ) );

        IntactTab2Xml tab2xml = new IntactTab2Xml();
        final EntrySet entrySet = tab2xml.convert( mitabInteractions );

        Assert.assertNotNull( entrySet );
        Assert.assertEquals(1, entrySet.getEntries().size());
        final Entry entry = entrySet.getEntries().iterator().next();

        final Collection<Interaction> interactions = entry.getInteractions();
        Assert.assertNotNull( interactions );
        Assert.assertEquals( 2, interactions.size() );

        for ( Interaction interaction : interactions ) {
            for ( Participant participant : interaction.getParticipants() ) {
                final Interactor interactor = participant.getInteractor();
                Assert.assertFalse( interactor.getId() == 0 );

                if( "CHEBI:45906".equals( interactor.getNames().getShortLabel() ) ) {
                    Assert.assertEquals( 15, interactor.getAttributes().size() );
                }
            }
        }
    }
}
