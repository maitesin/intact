package uk.ac.ebi.intact.util.uniprotExport.miscore.filter;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.xml.converter.ConverterException;
import uk.ac.ebi.enfin.mi.cluster.EncoreInteraction;
import uk.ac.ebi.intact.model.CvAliasType;
import uk.ac.ebi.intact.psimitab.IntactBinaryInteraction;
import uk.ac.ebi.intact.psimitab.IntactPsimiTabReader;
import uk.ac.ebi.intact.psimitab.model.ExtendedInteractor;
import uk.ac.ebi.intact.util.uniprotExport.miscore.results.MiScoreResults;
import uk.ac.ebi.intact.util.uniprotExport.miscore.UniprotExportException;
import uk.ac.ebi.intact.util.uniprotExport.miscore.exporter.InteractionExporter;
import uk.ac.ebi.intact.util.uniprotExport.miscore.exporter.QueryFactory;
import uk.ac.ebi.intact.util.uniprotExport.miscore.results.IntActInteractionClusterScore;
import uk.ac.ebi.intact.util.uniprotExport.miscore.results.MiClusterContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class will process uniprot-export of binary interactions (both spoke expanded and truly binary interactions)
 * The rules are simpler than for the CCline export :
 * - Starts with the MITAB file generated during the release. It contains all the released binary interactions in IntAct (contains spoke expanded as well)
 * - Computes the mi cluster score for all the interactions passing the dr-export constraint at the level of the experiment and which are composed of two uniprot proteins.
 * - Excludes the binary interactions with a score lower than the EXPORT_THRESHOLD
 * - Export only binary interactions containing at least one truly binary interaction in IntAct (not only spoke expanded)
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22-Oct-2010</pre>
 */

public class MitabInteractionFilter implements InteractionFilter{

    private QueryFactory queryProvider;
    private Set<String> eligibleInteractionsForUniprotExport;
    private IntactPsimiTabReader mitabReader;
    private static final String CONFIDENCE_NAME = "intactPsiscore";
    private static final String INTACT = "intact";
    private static final String UNIPROT = "uniprotkb";
    private final static String FEATURE_CHAIN = "-PRO_";

    private InteractionExporter exporter;

    private String mitab;

    public MitabInteractionFilter(InteractionExporter exporter, String mitab){
        queryProvider = new QueryFactory();
        eligibleInteractionsForUniprotExport = new HashSet<String>();
        mitabReader = new IntactPsimiTabReader(true);
        this.exporter = exporter;

        this.mitab = mitab;

        eligibleInteractionsForUniprotExport.addAll(this.queryProvider.getInteractionAcsFromReleasedExperimentsContainingNoUniprotProteinsToBeProcessedForUniprotExport());
    }

    private MiScoreResults computeMiScoreInteractionEligibleUniprotExport(String mitabFile) throws IOException, ConverterException {
        IntActInteractionClusterScore clusterScore = new IntActInteractionClusterScore();
        MiClusterContext context = new MiClusterContext();
        Set<Integer> interactionsToExport = new HashSet<Integer>();

        File mitab = new File(mitabFile);
        Iterator<BinaryInteraction> iterator = mitabReader.iterate(new FileInputStream(mitab));

        List<BinaryInteraction> interactionToProcess = new ArrayList<BinaryInteraction>();

        while (iterator.hasNext()){
            interactionToProcess.clear();
            while (interactionToProcess.size() < 200 && iterator.hasNext()){
                IntactBinaryInteraction interaction = (IntactBinaryInteraction) iterator.next();
                String intactAc = null;

                for (CrossReference ref : interaction.getInteractionAcs()){
                    if (ref.getDatabase().equalsIgnoreCase(INTACT)){
                        intactAc = ref.getIdentifier();
                        break;
                    }
                }

                ExtendedInteractor interactorA = interaction.getInteractorA();
                String uniprotA = null;
                ExtendedInteractor interactorB = interaction.getInteractorB();
                String uniprotB = null;

                if (interactorA != null){
                    for (CrossReference refA : interactorA.getIdentifiers()){
                        if (UNIPROT.equalsIgnoreCase(refA.getDatabase())){
                            uniprotA = refA.getIdentifier();
                            if (uniprotA.contains(FEATURE_CHAIN)){
                                uniprotA = refA.getIdentifier().substring(0, uniprotA.indexOf(FEATURE_CHAIN));
                            }

                            break;
                        }
                    }
                }
                if (interactorB != null){
                    for (CrossReference refB : interactorB.getIdentifiers()){
                        if (UNIPROT.equalsIgnoreCase(refB.getDatabase())){
                            uniprotB = refB.getIdentifier();

                            if (uniprotB.contains(FEATURE_CHAIN)){
                                uniprotB = refB.getIdentifier().substring(0, uniprotB.indexOf(FEATURE_CHAIN));
                            }

                            break;
                        }
                    }
                }

                if (intactAc != null && uniprotA != null && uniprotB != null){

                    if (this.eligibleInteractionsForUniprotExport.contains(intactAc)){
                        interactionToProcess.add(interaction);

                        processGeneNames(interactorA, uniprotA, interactorB, uniprotB, context);
                        processMiTerms(interaction, context);

                        List<InteractionDetectionMethod> detectionMethods = interaction.getDetectionMethods();
                        String detectionMI = detectionMethods.iterator().next().getIdentifier();

                        List<InteractionType> interactionTypes = interaction.getInteractionTypes();
                        String typeMi = interactionTypes.iterator().next().getIdentifier();

                        Map.Entry<String, String> entry = new DefaultMapEntry(detectionMI, typeMi);
                        context.getInteractionToType_Method().put(intactAc, entry);

                        if (!interaction.getExpansionMethods().isEmpty()){

                            context.getSpokeExpandedInteractions().add(intactAc);
                        }
                    }
                }
            }
            clusterScore.setBinaryInteractionList(interactionToProcess);
            clusterScore.runService();
        }

        return new MiScoreResults(clusterScore, context, interactionsToExport);
    }

    private void processMiTerms(BinaryInteraction interaction, MiClusterContext context){
        List<InteractionDetectionMethod> detectionMethods = interaction.getDetectionMethods();

        Map<String, String> miTerms = context.getMiTerms();

        for (InteractionDetectionMethod method : detectionMethods){
            if (!miTerms.containsKey(method.getIdentifier())){
                String methodName = method.getText() != null ? method.getText() : "-";
                miTerms.put(method.getIdentifier(), methodName);
            }
        }

        List<InteractionType> types = interaction.getInteractionTypes();

        for (InteractionType type : types){
            if (!miTerms.containsKey(type.getIdentifier())){
                String methodName = type.getText() != null ? type.getText() : "-";
                miTerms.put(type.getIdentifier(), methodName);
            }
        }
    }
    private void processGeneNames(ExtendedInteractor interactorA, String intactA, ExtendedInteractor interactorB, String intactB, MiClusterContext context) {
        String geneNameA = retrieveInteractorGeneName(interactorA);
        String geneNameB = retrieveInteractorGeneName(interactorB);

        Map<String, String> geneNames = context.getGeneNames();

        if (!geneNames.containsKey(geneNameA)){
            context.getGeneNames().put(intactA, geneNameA);
        }
        if (!geneNames.containsKey(geneNameB)){
            context.getGeneNames().put(intactB, geneNameB);
        }
    }

    private String retrieveInteractorGeneName(ExtendedInteractor interactor){
        Collection<Alias> aliases = interactor.getAliases();
        String geneName = null;

        if (aliases.isEmpty()) {

            Collection<CrossReference> otherIdentifiers = interactor.getAlternativeIdentifiers();
            // then look for locus
            String locusName = null;
            String orf = null;

            for (CrossReference ref : otherIdentifiers){
                if (UNIPROT.equalsIgnoreCase(ref.getDatabase())){
                    if (CvAliasType.LOCUS_NAME.equalsIgnoreCase(ref.getText())){
                        locusName = ref.getIdentifier();
                    }
                    else if (CvAliasType.ORF_NAME.equalsIgnoreCase(ref.getText())){
                        orf = ref.getIdentifier();
                    }
                }
            }

            geneName = locusName != null ? locusName : orf;

        } else {
            geneName = aliases.iterator().next().getName();
        }

        if( geneName == null ) {
            geneName = "-";
        }

        return geneName;
    }

    private double getMiClusterScoreFor(EncoreInteraction interaction){
        List<Confidence> confidenceValues = interaction.getConfidenceValues();
        double score = 0;
        for(Confidence confidenceValue:confidenceValues){
            if(confidenceValue.getType().equalsIgnoreCase(CONFIDENCE_NAME)){
                score = Double.parseDouble(confidenceValue.getValue());
            }
        }

        return score;
    }

    public MiScoreResults exportInteractionsFrom(String mitab) throws UniprotExportException {
        try {

            MiScoreResults clusterResults = computeMiScoreInteractionEligibleUniprotExport(mitab);
            exporter.exportInteractionsFrom(clusterResults);

            //this.interactionClusterScore.saveScoresForSpecificInteractions(fileExport, this.interactionsToBeExported);

            return clusterResults;
        } catch (IOException e) {
            throw new UniprotExportException("It was not possible to convert the data in the mitab file " + mitab + " in an InputStream", e);
        } catch (ConverterException e) {
            throw new UniprotExportException("It was not possible to iterate the binary interactions in the mitab file " + mitab, e);
        }
    }

    public Set<String> getEligibleInteractionsForUniprotExport() {
        return eligibleInteractionsForUniprotExport;
    }

    public QueryFactory getQueryProvider() {
        return queryProvider;
    }

    @Override
    public MiScoreResults exportInteractions() throws UniprotExportException {
        return exportInteractionsFrom(mitab);
    }

    @Override
    public InteractionExporter getInteractionExporter() {
        return this.exporter;
    }

    @Override
    public void setInteractionExporter(InteractionExporter exporter) {
        this.exporter = exporter;
    }
}
