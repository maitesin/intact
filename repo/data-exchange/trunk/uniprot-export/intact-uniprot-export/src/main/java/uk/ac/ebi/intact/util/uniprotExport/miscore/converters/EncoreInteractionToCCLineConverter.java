package uk.ac.ebi.intact.util.uniprotExport.miscore.converters;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import psidev.psi.mi.tab.model.CrossReference;
import uk.ac.ebi.enfin.mi.cluster.EncoreInteraction;
import uk.ac.ebi.intact.util.uniprotExport.miscore.MiClusterContext;
import uk.ac.ebi.intact.util.uniprotExport.parameters.CCParametersImpl;
import uk.ac.ebi.intact.util.uniprotExport.parameters.InteractionDetails;
import uk.ac.ebi.intact.util.uniprotExport.writers.WriterUtils;

import java.util.*;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>31/01/11</pre>
 */

public class EncoreInteractionToCCLineConverter {

    private String [] extractOrganismFrom(Collection<CrossReference> references){

        String taxId = "-";
        String organismName = "-";

        for (CrossReference ref : references){
            if (WriterUtils.TAXID.equalsIgnoreCase(ref.getDatabase())){
                taxId = ref.getIdentifier();
                if (ref.getText() != null){
                    organismName = ref.getText();
                }
            }
        }

        return new String [] {taxId, organismName};
    }

    private SortedSet<InteractionDetails> sortInteractionDetails(EncoreInteraction interaction, MiClusterContext context){

        Map<String, String> interactionToPubmed = interaction.getExperimentToPubmed();
        Map<String, List<String>> pubmedToInteraction = WriterUtils.invertMapOfTypeStringToString(interactionToPubmed);

        Map<Map.Entry<String, String>, Set<String>> distinctInformationDetails = collectDistinctInteractionDetails(interaction, context);

        Map<Map.Entry<String, String>, List<String>> method_typeToInteractions = WriterUtils.invertMapFromKeySelection(context.getInteractionToType_Method(), interactionToPubmed.keySet());
        SortedSet<InteractionDetails> sortedInteractionDetails = new TreeSet<InteractionDetails>();

        for (Map.Entry<Map.Entry<String, String>, Set<String>> ip : distinctInformationDetails.entrySet()){
            String type = context.getMiTerms().get(ip.getKey().getValue());
            String method = context.getMiTerms().get(ip.getKey().getKey());

            Set<String> pubmedIds = ip.getValue();
            List<String> duplicatedPubmedIds = new ArrayList(pubmedIds);

            Set<String> pubmedSpokeExpanded = new HashSet<String>(duplicatedPubmedIds.size());
            Set<String> pubmedTrueBinary = new HashSet<String>(duplicatedPubmedIds.size());

            List<String> totalInteractions = method_typeToInteractions.get(ip);

            for (String pubmedId : duplicatedPubmedIds){
                List<String> interactionForPubmedAndTypeAndMethod = new ArrayList(CollectionUtils.intersection(pubmedToInteraction.get(pubmedId), totalInteractions));

                int numberSpokeExpanded = 0;

                for (String intAc : interactionForPubmedAndTypeAndMethod){
                    if (context.getSpokeExpandedInteractions().contains(intAc)){
                        numberSpokeExpanded++;
                    }
                }

                if (numberSpokeExpanded == interactionForPubmedAndTypeAndMethod.size()){
                    pubmedSpokeExpanded.add(pubmedId);
                }
                else {
                    pubmedTrueBinary.add(pubmedId);
                }
            }

            if (!pubmedSpokeExpanded.isEmpty()){
                InteractionDetails details = new InteractionDetails(type, method, true, pubmedSpokeExpanded);
                sortedInteractionDetails.add(details);
            }

            if (!pubmedTrueBinary.isEmpty()){
                InteractionDetails details = new InteractionDetails(type, method, false, pubmedSpokeExpanded);
                sortedInteractionDetails.add(details);
            }
        }

        return sortedInteractionDetails;
    }

    public Map<Map.Entry<String, String>, Set<String>> collectDistinctInteractionDetails(EncoreInteraction interaction, MiClusterContext context){
        Map<String, List<String>> typeToPubmed = interaction.getTypeToPubmed();
        Map<String, List<String>> methodToPubmed = interaction.getMethodToPubmed();
        Map<Map.Entry<String, String>, Set<String>> distinctLines = new HashMap<Map.Entry<String, String>, Set<String>>();

        for (Map.Entry<String, List<String>> methodEntry : methodToPubmed.entrySet()){
            List<String> pubmeds1 = methodEntry.getValue();
            String method = context.getMiTerms().get(methodEntry.getKey());

            for (Map.Entry<String, List<String>> typeEntry : typeToPubmed.entrySet()){
                List<String> pubmeds2 = typeEntry.getValue();
                String type = context.getMiTerms().get(typeEntry.getKey());

                Set<String> associatedPubmeds = new HashSet(CollectionUtils.intersection(pubmeds1, pubmeds2));

                if (!associatedPubmeds.isEmpty()){

                    distinctLines.put(new DefaultMapEntry(method, type), associatedPubmeds);
                }
            }
        }

        return distinctLines;
    }

    public CCParametersImpl convertInteractionsIntoCCLines(EncoreInteraction interaction, MiClusterContext context){

        String uniprot1 = interaction.getInteractorA(WriterUtils.UNIPROT);
        String uniprot2 = interaction.getInteractorB(WriterUtils.UNIPROT);

        if (uniprot1 != null && uniprot2 != null){
            // produce the CC lines for the 1st protein
            // extract gene names
            String geneName1 = context.getGeneNames().get(uniprot1);
            String geneName2 = context.getGeneNames().get(uniprot2);

            // extract organisms
            String [] organismsA;
            String [] organismsB;
            if (interaction.getInteractorA().equals(uniprot1)){
                organismsA = extractOrganismFrom(interaction.getOrganismsA());
                organismsB = extractOrganismFrom(interaction.getOrganismsB());
            }
            else {
                organismsA = extractOrganismFrom(interaction.getOrganismsB());
                organismsB = extractOrganismFrom(interaction.getOrganismsA());
            }
            String taxId1 = organismsA[0];
            String taxId2 = organismsB[0];

            String organism1 = organismsA[1];
            String organism2 = organismsB[1];

            // collect all pubmeds and spoke expanded information
            SortedSet<InteractionDetails> sortedInteractionDetails = sortInteractionDetails(interaction, context);

            return new CCParametersImpl(uniprot1, uniprot2, geneName1, geneName2, taxId1, taxId2, organism1, organism2, sortedInteractionDetails);
        }

        return null;
    }
}
