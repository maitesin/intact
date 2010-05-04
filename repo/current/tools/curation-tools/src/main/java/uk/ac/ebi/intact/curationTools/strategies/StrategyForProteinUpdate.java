package uk.ac.ebi.intact.curationTools.strategies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.bridges.ncbiblast.model.BlastProtein;
import uk.ac.ebi.intact.curationTools.actions.exception.ActionProcessingException;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionName;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionReport;
import uk.ac.ebi.intact.curationTools.model.actionReport.BlastReport;
import uk.ac.ebi.intact.curationTools.model.actionReport.status.Status;
import uk.ac.ebi.intact.curationTools.model.actionReport.status.StatusLabel;
import uk.ac.ebi.intact.curationTools.model.contexts.IdentificationContext;
import uk.ac.ebi.intact.curationTools.model.contexts.UpdateContext;
import uk.ac.ebi.intact.curationTools.model.results.IdentificationResults;
import uk.ac.ebi.intact.curationTools.strategies.exceptions.StrategyException;

import java.util.Set;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27-Apr-2010</pre>
 */

public class StrategyForProteinUpdate extends IdentificationStrategyImpl {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( StrategyForProteinUpdate.class );

    public StrategyForProteinUpdate(){
        super();
    }


    @Override
    public void enableIsoforms(boolean enableIsoformId){
        super.enableIsoforms(enableIsoformId);
        ((StrategyWithSequence) this.listOfActions.get(0)).enableIsoforms(enableIsoformId);
        ((StrategyWithIdentifier) this.listOfActions.get(1)).enableIsoforms(enableIsoformId);
    }

    private boolean checkIdentifierResults(IdentificationResults result, IdentificationContext context, ActionReport updateReport) throws ActionProcessingException {
        if (result.getUniprotId() != null){
            String otherResultFromIdentifier = this.listOfActions.get(1).runAction(context);
            otherResultFromIdentifier = processIsoforms(otherResultFromIdentifier);
            result.getListOfActions().addAll(this.listOfActions.get(1).getListOfActionReports());

            String uniprot1 = result.getUniprotId();

            if (otherResultFromIdentifier != null){
                if (!uniprot1.equals(otherResultFromIdentifier)){
                    updateReport.addPossibleAccession(otherResultFromIdentifier);
                }
                return uniprot1.equals(otherResultFromIdentifier);
            }
        }
        else {
            String otherResultFromIdentifier = this.listOfActions.get(1).runAction(context);
            otherResultFromIdentifier = processIsoforms(otherResultFromIdentifier);
            result.getListOfActions().addAll(this.listOfActions.get(1).getListOfActionReports());

            if (otherResultFromIdentifier != null){
                Set<BlastProtein> blastProteins = ((BlastReport) result.getLastAction()).getBlastMatchingProteins();

                if (blastProteins.isEmpty()){
                    result.setUniprotId(otherResultFromIdentifier);
                    return true;
                }
                else {
                    for (BlastProtein p : blastProteins){
                        if (p.getAccession().equals(otherResultFromIdentifier)){
                            result.setUniprotId(otherResultFromIdentifier);
                            return true;
                        }
                    }
                    updateReport.addPossibleAccession(otherResultFromIdentifier);
                }
            }
            else {
                return true;
            }
        }
        return false;
    }

    @Override
    public IdentificationResults identifyProtein(IdentificationContext context) throws StrategyException {

        if (! (context instanceof UpdateContext)){
            throw new StrategyException("The context of a StrategyForProteinUpdate should be an instance of UpdateContext and not " + context.getClass().getSimpleName());
        }
        String sequence = context.getSequence();
        Set<String> identifiers = ((UpdateContext) context).getIdentifiers();

        IdentificationResults result = new IdentificationResults();

        try {
            if (context.getSequence() == null && context.getIdentifier() == null){
                throw new StrategyException("Either the sequence of the protein or an identifier must be not null.");
            }
            else if (sequence != null) {
                result = new IdentificationResults();

                String uniprot = null;

                uniprot = this.listOfActions.get(0).runAction(context);
                result.getListOfActions().addAll(this.listOfActions.get(0).getListOfActionReports());
                processIsoforms(uniprot, result);

                if (!identifiers.isEmpty()){
                    ActionReport report = new ActionReport(ActionName.update_Checking);
                    report.addPossibleAccession(result.getUniprotId());

                    boolean isMatchingIdentifierResults = false;

                    for (String identifier : identifiers){
                        IdentificationContext c = new IdentificationContext(context);
                        c.setIdentifier(identifier);
                        isMatchingIdentifierResults = checkIdentifierResults(result, context, report);
                    }

                    if (isMatchingIdentifierResults){
                        Status status = new Status(StatusLabel.COMPLETED, "There is no result conflicts when we tried to identify the protein using the sequence then using the identifiers " + identifiers);
                        report.setStatus(status);
                        result.addActionReport(report);
                    }
                    else {
                        Status status = new Status(StatusLabel.TO_BE_REVIEWED, "There is a conflict in the results when we tried to identify the protein using the sequence then using the identifiers " + identifiers);
                        report.setStatus(status);
                        if (result.getUniprotId() != null){
                            report.addPossibleAccession(result.getUniprotId());
                        }
                        result.addActionReport(report);
                        result.setUniprotId(null);
                    }
                }
            }
            else{
                String uniprot = this.listOfActions.get(1).runAction(context);
                result.getListOfActions().addAll(this.listOfActions.get(1).getListOfActionReports());
                processIsoforms(uniprot, result);
            }
        } catch (ActionProcessingException e) {
            throw  new StrategyException("An error occured while trying to update the protein using the sequence " + context.getSequence(), e);
        }
        return result;
    }

    @Override
    protected void initialiseSetOfActions() {
        StrategyWithSequence firstAction = new StrategyWithSequence();
        firstAction.enableIsoforms(this.isIsoformEnabled());
        this.listOfActions.add(firstAction);

        StrategyWithIdentifier secondAction = new StrategyWithIdentifier();
        secondAction.enableIsoforms(this.isIsoformEnabled());
        this.listOfActions.add(secondAction);
    }
}
