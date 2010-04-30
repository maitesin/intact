package uk.ac.ebi.intact.curationTools.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.bridges.picr.PicrClient;
import uk.ac.ebi.intact.bridges.picr.PicrClientException;
import uk.ac.ebi.intact.bridges.picr.PicrSearchDatabase;
import uk.ac.ebi.intact.curationTools.actions.exception.ActionProcessingException;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionName;
import uk.ac.ebi.intact.curationTools.model.actionReport.PICRReport;
import uk.ac.ebi.intact.curationTools.model.actionReport.status.Status;
import uk.ac.ebi.intact.curationTools.model.actionReport.status.StatusLabel;
import uk.ac.ebi.intact.curationTools.model.contexts.IdentificationContext;
import uk.ac.ebi.picr.model.CrossReference;
import uk.ac.ebi.picr.model.UPEntry;

import java.util.List;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29-Mar-2010</pre>
 */

public class PICRSearchProcessWithAccession extends IdentificationActionImpl {

    private PicrClient picrClient;

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog( PICRSearchProcessWithAccession.class );

    public PICRSearchProcessWithAccession(){
        this.picrClient = new PicrClient();
    }

    public String runAction(IdentificationContext context) throws ActionProcessingException {
        this.listOfReports.clear();

        String identifier = context.getIdentifier();
        String taxId = null;
        if (context.getOrganism() != null){
            taxId = context.getOrganism().getTaxId();
        }

        PICRReport report = new PICRReport(ActionName.PICR_accession);
        this.listOfReports.add(report);

        if (taxId == null){

            report.addWarning("No organism was given for the identifier " + identifier + ". We will process the identification without looking at the organism and choose the entry with the longest sequence.");
        }

        try {
            String [] idResults = this.picrClient.getUniprotBestGuessFor(identifier, taxId);
            String databaseName = idResults[0];
            String uniprotId = idResults[1];

            if (uniprotId != null && databaseName != null){

                if (databaseName.equals(PicrSearchDatabase.SWISSPROT.toString()) || databaseName.equals(PicrSearchDatabase.SWISSPROT_VARSPLIC.toString())){
                    Status status = new Status(StatusLabel.COMPLETED, "PICR successfully matched the identifier " + context.getIdentifier() + " to this Swissprot accession " + uniprotId);
                    report.setStatus(status);

                    report.setIsASwissprotEntry(true);
                    return uniprotId;
                }
                else if (databaseName.equals(PicrSearchDatabase.TREMBL.toString()) || databaseName.equals(PicrSearchDatabase.TREMBL_VARSPLIC.toString())){
                    Status status = new Status(StatusLabel.COMPLETED, "PICR successfully matched the identifier " + context.getIdentifier() + " to this Trembl accession " + uniprotId);
                    report.setStatus(status);

                    report.setIsASwissprotEntry(true);
                    return uniprotId;
                }
                else {
                    log.error("The database name " + databaseName + " is not expected. We are only expecting SWISSPROT, TREMBL, SWISSPROT_VARSPLIC, TREMBL_VARSPLIC");
                }
            }
            else {
                if (databaseName == null && uniprotId != null){
                    log.error("The database name of the result " + uniprotId + " returned by PICR is null and should not be.");
                }

                if (uniprotId == null){
                    Status status = new Status(StatusLabel.FAILED, "PICR couldn't match the identifier "+identifier+" to any Uniprot accession.");
                    report.setStatus(status);

                    List<UPEntry> upEntries = this.picrClient.getUPEntriesForAccession(identifier, taxId);

                    for (UPEntry e : upEntries){
                        for (CrossReference ref : e.getIdenticalCrossReferences()){
                            report.addCrossReference(ref.getDatabaseName(), ref.getAccession());
                        }
                    }
                }
            }
        } catch (PicrClientException e) {
            if (report.getCrossReferences().isEmpty()){
                Status status = new Status(StatusLabel.FAILED, "PICR couldn't match the identifier "+identifier+" to any Uniprot accession.");
                report.setStatus(status);

                List<UPEntry> upEntries = null;
                try {
                    upEntries = this.picrClient.getUPEntriesForAccession(identifier, taxId);
                    for (UPEntry entry : upEntries){
                        for (CrossReference ref : entry.getIdenticalCrossReferences()){
                            report.addCrossReference(ref.getDatabaseName(), ref.getAccession());
                        }
                    }
                } catch (PicrClientException e1) {
                    throw  new ActionProcessingException("PICR couldn't match the identifier " + identifier + " to any Uniprot accession. Check your identifier and/or organism.", e);
                }
            }
            else {
                throw  new ActionProcessingException("PICR couldn't match the identifier " + identifier + " to any Uniprot accession. Check your identifier and/or organism.", e);                
            }
        }
        return null;
    }
}
