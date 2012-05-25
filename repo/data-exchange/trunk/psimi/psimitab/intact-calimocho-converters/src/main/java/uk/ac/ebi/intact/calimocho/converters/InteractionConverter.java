package uk.ac.ebi.intact.calimocho.converters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hupo.psi.calimocho.model.DefaultRow;
import org.hupo.psi.calimocho.model.Row;
import uk.ac.ebi.intact.model.Component;
import uk.ac.ebi.intact.model.Experiment;
import uk.ac.ebi.intact.model.Interaction;
import uk.ac.ebi.intact.model.Publication;
import uk.ac.ebi.intact.psimitab.converters.expansion.ExpansionStrategy;
import uk.ac.ebi.intact.psimitab.converters.expansion.NotExpandableInteractionException;
import uk.ac.ebi.intact.psimitab.converters.expansion.SpokeWithoutBaitExpansion;

import java.util.*;

/**
 * This converter will convert an Interaction in/from a list of Rows for calimocho
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/05/12</pre>
 */

public class InteractionConverter {

    private ExpansionStrategy expansionStrategy;
    private static final Log log = LogFactory.getLog(InteractionConverter.class);
    private InteractorConverter interactorConverter;
    private PublicationConverter publicationConverter;

    public InteractionConverter(ExpansionStrategy strategy){
        this.expansionStrategy = strategy;
        if (this.expansionStrategy == null){
            this.expansionStrategy = new SpokeWithoutBaitExpansion();
        }
        this.interactorConverter = new InteractorConverter();
    }

    public InteractionConverter(){
        this.expansionStrategy = new SpokeWithoutBaitExpansion();
        this.interactorConverter = new InteractorConverter();
    }

    public List<Row> toCalimocho(Interaction interaction) throws NotExpandableInteractionException {

        if ( interaction == null ) {
            throw new IllegalArgumentException( "Interaction must not be null" );
        }

        if (!expansionStrategy.isExpandable(interaction)) {
            if (log.isWarnEnabled()) log.warn("Filtered interaction: "+interaction.getAc()+" (not expandable)");
            return Collections.EMPTY_LIST;
        }

        Collection<Interaction> interactions = expansionStrategy.expand(interaction);

        boolean expanded = false;

        // if we have more than one interaction, it means that we have spoke expanded interactions
        if (interactions != null && interactions.size() > 1) {

            expanded = true;
        }
        else if (interactions == null){
            return Collections.EMPTY_LIST;
        }

        if (interactions.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("Expansion did not generate any interaction for: "+interaction);
                return Collections.EMPTY_LIST;
            }
        }
        List<Row> rows = new ArrayList<Row>();

        for (Interaction binary : interactions){
             
            Row row = processBinaryInteraction(binary);
            if (row != null){
                rows.add(row);
            }
        }

        return rows;
    }

    private Row processBinaryInteraction(Interaction binary) {
        Row row = new DefaultRow();
        final Collection<Component> components = binary.getComponents();
        Iterator<Component> iterator = components.iterator();
        final Component componentA = iterator.next();
        final Component componentB = iterator.next();

        interactorConverter.toCalimocho(componentA, row, true);
        interactorConverter.toCalimocho(componentB, row, false);
        
        for (Experiment exp : binary.getExperiments()){
            Publication pub = exp.getPublication();
            if (pub != null){
                publicationConverter.toCalimocho(pub, row);
            }
        }

        return row;
    }

}
