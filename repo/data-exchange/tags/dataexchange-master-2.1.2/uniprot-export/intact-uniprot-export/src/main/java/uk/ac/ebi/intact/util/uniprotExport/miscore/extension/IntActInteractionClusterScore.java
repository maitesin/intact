package uk.ac.ebi.intact.util.uniprotExport.miscore.extension;

import org.apache.log4j.Logger;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.Confidence;
import psidev.psi.mi.xml.converter.ConverterException;
import uk.ac.ebi.enfin.mi.cluster.Encore2Binary;
import uk.ac.ebi.enfin.mi.cluster.EncoreInteraction;
import uk.ac.ebi.enfin.mi.cluster.score.InteractionClusterScore;
import uk.ac.ebi.enfin.mi.score.ols.MIOntology;
import uk.ac.ebi.enfin.mi.score.scores.MIScore;
import uk.ac.ebi.intact.psimitab.IntactBinaryInteraction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Extension of the InteractionClusterScore : use a different format to export the scores
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16-Sep-2010</pre>
 */

public class IntActInteractionClusterScore extends InteractionClusterScore{

    private static final Logger logger = Logger.getLogger(IntActInteractionClusterScore.class);
    private String[] scoreList = null;
    private String scoreListCSV;

    private PsimiTabWriter writer;

    public IntActInteractionClusterScore(){
        super();
        setMappingIdDbNames("uniprotkb");
        writer = new PsimiTabWriter();

        setDirectInteractionWeight_3();
        //setDirectInteractionWeight_5();
    }

    @Override
    public void runService() {
        logger.debug("runService");
        super.runService();
    }

    public void setDirectInteractionWeight_5(){
        Map<String,Float> customOntologyTypeScores = new HashMap<String,Float>();
        customOntologyTypeScores.put("MI:0208", 0.05f);
        customOntologyTypeScores.put("MI:0403", 0.20f);
        customOntologyTypeScores.put("MI:0914", 0.20f);
        customOntologyTypeScores.put("MI:0915", 0.40f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.02f);
        super.setCustomOntologyTypeScores(customOntologyTypeScores);
    }

    public void setDirectInteractionWeight_3(){
        Map<String,Float> customOntologyTypeScores = new HashMap<String,Float>();
        customOntologyTypeScores.put("MI:0208", 0.08f);
        customOntologyTypeScores.put("MI:0403", 0.33f);
        customOntologyTypeScores.put("MI:0914", 0.33f);
        customOntologyTypeScores.put("MI:0915", 0.67f);
        customOntologyTypeScores.put("MI:0407", 1.00f);
        customOntologyTypeScores.put("unknown", 0.03f);
        super.setCustomOntologyTypeScores(customOntologyTypeScores);
    }

    /**
     *
     * @return a list of formatted scores for each interaction
     */
    public String[] getScoresPerInteraction(){
        if(this.getInteractionMapping() == null){
            runService();
        }
        if(scoreList == null){
            int scoreListSize = this.getInteractionMapping().size();
            scoreList = new String[scoreListSize];
            scoreListCSV = "";
            String delimiter = "\n";
            int i = 0;
            for(EncoreInteraction eI:this.getInteractionMapping().values()){
                List<Confidence> confidenceValues = eI.getConfidenceValues();
                Double score = null;
                for(Confidence confidenceValue:confidenceValues){
                    if(confidenceValue.getType().equalsIgnoreCase("intactPsiscore")){
                        score = Double.parseDouble(confidenceValue.getValue());
                    }
                }
                if(score == null){
                    logger.error("No score for this interaction: " + eI.getId());
                }
                scoreList[i] = eI.getId() + "-" +eI.getInteractorA() + "-" + eI.getInteractorB() + ":" + score;
                scoreListCSV = scoreListCSV + scoreList[i];
                i++;
                if(scoreListSize > i){
                    scoreListCSV = scoreListCSV + delimiter;
                }
            }
        }
        return scoreList;
    }

    /**
     *
     * @return a list of formatted scores for each interaction
     */
    public String getScoresPerInteraction(Collection<Integer> interactionIds, String scoreListCSV, String [] scoreList){
        if(this.getInteractionMapping() == null){
            runService();
        }
        if(scoreList == null){
            int scoreListSize = interactionIds.size();
            scoreList = new String[scoreListSize];
            scoreListCSV = "";
            String delimiter = "\n";

            int i = 0;
            for (Integer eId : interactionIds){
                EncoreInteraction eI = this.getInteractionMapping().get(eId);

                if (eI != null){
                    List<Confidence> confidenceValues = eI.getConfidenceValues();
                    Double score = null;
                    for(Confidence confidenceValue:confidenceValues){
                        if(confidenceValue.getType().equalsIgnoreCase("intactPsiscore")){
                            score = Double.parseDouble(confidenceValue.getValue());
                        }
                    }
                    if(score == null){
                        logger.error("No score for this interaction: " + eI.getId());
                    }
                    scoreList[i] = eI.getId() + "-" +eI.getInteractorA() + "-" + eI.getInteractorB() + ":" + score;
                    scoreListCSV = scoreListCSV + scoreList[i];
                    i++;
                    if(scoreListSize > i){
                        scoreListCSV = scoreListCSV + delimiter;
                    }
                }
            }
        }
        return scoreListCSV;
    }

    /**
     * Saves the score using a formatted String for each interaction
     */
    public void saveScores(){
        fileName = "scores.txt";
        saveScores(fileName);
    }

    @Override
    public void saveScores(String fileName){
        /* Retrieve results */
        Map<Integer, EncoreInteraction> interactionMapping = getInteractionMapping();
        Map<Integer, BinaryInteraction> binaryInteractionMapping = new HashMap<Integer,BinaryInteraction>();
        Encore2Binary iConverter = new Encore2Binary(getMappingIdDbNames());

        for(int mappingId:interactionMapping.keySet()){
            EncoreInteraction eI = interactionMapping.get(mappingId);
            BinaryInteraction bI = iConverter.getBinaryInteraction(eI);
            binaryInteractionMapping.put(mappingId,bI);
        }

        File file = new File(fileName);
        try {
            writer.write(binaryInteractionMapping.values(), file);
        } catch (Exception e) {
            logger.error("It is not possible to write the results in the mitab file " + fileName);
            e.printStackTrace();
        }

        if(scoreList == null){
            getScoresPerInteraction();
        }
        try{
            // Create file
            FileWriter fstream = new FileWriter(fileName + "_log.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(scoreListCSV);
            logger.info("Saving scores on ... " + fileName + "_log.txt");
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            logger.error("Error: " + e.getMessage());
        }
    }

    public void saveScoresForSpecificInteractions(String fileName, Collection<Integer> interactionIds){

        String scoreListCSV = getScoresPerInteraction(interactionIds, null, null);
        try{
            // Create file
            FileWriter fstream = new FileWriter(fileName + "_log.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(scoreListCSV);
            logger.info("Saving scores on ... " + fileName + "_log.txt");
            //Close the output stream
            out.close();
        }catch (Exception e){//Catch exception if any
            logger.error("Error: " + e.getMessage());
        }

        /* Retrieve results */
        Map<Integer, EncoreInteraction> interactionMapping = getInteractionMapping();
        Map<Integer, BinaryInteraction> binaryInteractionMapping = new HashMap<Integer,BinaryInteraction>();
        Encore2Binary iConverter = new Encore2Binary(getMappingIdDbNames());

        for(Integer mappingId:interactionIds){
            EncoreInteraction eI = interactionMapping.get(mappingId);
            BinaryInteraction bI = iConverter.getBinaryInteraction(eI);
            binaryInteractionMapping.put(mappingId,bI);
        }
        File file = new File(fileName);
        try {

            writer.write(binaryInteractionMapping.values(), file);

        } catch (Exception e) {
            logger.error("It is not possible to write the results in the mitab file " + fileName);
            e.printStackTrace();
        }
    }
}
