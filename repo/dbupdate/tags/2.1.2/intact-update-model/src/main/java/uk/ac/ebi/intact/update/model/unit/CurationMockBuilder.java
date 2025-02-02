package uk.ac.ebi.intact.update.model.unit;

import uk.ac.ebi.intact.core.unit.IntactMockBuilder;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.ActionName;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.BlastReport;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.MappingReport;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.PICRReport;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.status.Status;
import uk.ac.ebi.intact.update.model.protein.mapping.actions.status.StatusLabel;
import uk.ac.ebi.intact.update.model.protein.mapping.results.BlastResults;
import uk.ac.ebi.intact.update.model.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.update.model.protein.mapping.results.PICRCrossReferences;
import uk.ac.ebi.intact.update.model.protein.update.*;
import uk.ac.ebi.intact.update.model.protein.update.events.DeadProteinEvent;
import uk.ac.ebi.intact.update.model.protein.update.events.EventName;
import uk.ac.ebi.intact.update.model.protein.update.events.ProteinEvent;
import uk.ac.ebi.intact.update.model.protein.update.events.range.InvalidRange;
import uk.ac.ebi.intact.update.model.protein.update.events.range.UpdatedRange;

import java.util.Date;

/**
 * This class contains a set of methods to create objects for testing
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20-May-2010</pre>
 */
public class CurationMockBuilder extends IntactMockBuilder {

    private final static String invalidRangeAc = "EBI-2907496";
    private final static String outOfDateRangeAc = "EBI-3058809";

    /**
     *
     * @return auto-generated Swissprot remapping results
     */
    public BlastResults createSwissprotRemappingResults(){
        BlastResults results = new BlastResults();

        results.setTremblAccession("Q8R3H6");
        results.setAccession("P01867");
        results.setDatabase("SP");
        results.setDescription("Ig gamma-2B chain C region");
        results.setStartQuery(140);
        results.setEndQuery(174);
        results.setStartMatch(1);
        results.setEndQuery(335);
        results.setIdentity(99);

        return results;
    }

    /**
     *
     * @return auto-generated blast results
     */
    public BlastResults createBlastResults(){
        BlastResults results = new BlastResults();

        results.setAccession("Q8R3H6");
        results.setDatabase("TR");
        results.setStartQuery(140);
        results.setEndQuery(174);
        results.setStartMatch(1);
        results.setEndQuery(335);
        results.setIdentity(99);

        return results;
    }

    /**
     *
     * @param trembl
     * @param swissprotAc
     * @param sartQuery
     * @param endQuery
     * @param startMatch
     * @param endMatch
     * @param identity
     * @return a Blast results instance with trembl. swissprotAc. start and end query, start and end match, identity
     */
    public BlastResults createSwissprotRemappingResults(String trembl, String swissprotAc, int sartQuery, int endQuery, int startMatch, int endMatch, float identity){
        BlastResults results = new BlastResults();

        results.setTremblAccession(trembl);
        results.setAccession(swissprotAc);
        results.setDatabase("SP");
        results.setStartQuery(sartQuery);
        results.setEndQuery(endQuery);
        results.setStartMatch(startMatch);
        results.setEndQuery(endMatch);
        results.setIdentity(identity);

        return results;
    }

    /**
     *
     * @return An auto-generated BlastReport for swissprot remapping
     */
    public BlastReport createSwissprotRemappingReport(){
        BlastReport report = new BlastReport(ActionName.BLAST_Swissprot_Remapping);

        report.setASwissprotEntry(true);
        report.setStatus(new Status(StatusLabel.COMPLETED, "mapping successful"));
        report.setQuerySequence("GCAGGT");
        return report;
    }

    /**
     *
     * @return an auto-generated PICRCrossReference instance
     */
    public PICRCrossReferences createPICRCrossReferences(){
        PICRCrossReferences pc = new PICRCrossReferences();

        pc.setDatabase("Ensembl");
        pc.addAccession("ENSG0007777");

        return pc;
    }

    /**
     *
     * @return auto-generated PICRReport
     */
    public PICRReport createPICRReport(){
        PICRReport report = new PICRReport(ActionName.PICR_accession);

        report.setASwissprotEntry(false);
        report.setStatus(new Status(StatusLabel.COMPLETED, null));
        return report;
    }

    /**
     *
     * @return auto-generated MappingReport containing warnings
     */
    public MappingReport createActionReportWithWarning(){
        MappingReport report = new MappingReport(ActionName.BLAST_uniprot);

        report.setStatus(new Status(StatusLabel.TO_BE_REVIEWED, null));
        report.setASwissprotEntry(false);
        report.addWarning("To be reviewed by a curator");
        report.addPossibleAccession("P02134");
        report.addPossibleAccession("P12345");

        return report;
    }

    /**
     *
     * @return auto-generated MappingReport without any warnings
     */
    public MappingReport createActionReportWithoutWarning(){
        MappingReport report = new MappingReport(ActionName.BLAST_uniprot);

        report.setStatus(new Status(StatusLabel.TO_BE_REVIEWED, null));
        report.setASwissprotEntry(false);
        report.addPossibleAccession("P02134");
        report.addPossibleAccession("P12345");

        return report;
    }

    /**
     *
     * @return auto-generated MappingReport without any possible uniprot ac
     */
    public MappingReport createActionReportWithoutPossibleUniprot(){
        MappingReport report = new MappingReport(ActionName.BLAST_uniprot);

        report.setStatus(new Status(StatusLabel.TO_BE_REVIEWED, null));
        report.setASwissprotEntry(false);
        report.addWarning("To be reviewed by a curator");

        return report;
    }

    /**
     *
     * @return auto-generated BlastReport
     */
    public BlastReport createBlastReport(){
        BlastReport report = new BlastReport(ActionName.BLAST_uniprot);

        report.setASwissprotEntry(true);
        report.setQuerySequence("GCAGGT");
        report.setStatus(new Status(StatusLabel.COMPLETED, "mapping successful"));
        return report;
    }

    /**
     *
     * @return auto-generated updateResult
     */
    public IdentificationResults createUpdateResult(){
        IdentificationResults results = new IdentificationResults();

        results.setFinalUniprotId("P01234");
        return results;
    }

    /**
     *
     * @return auto-generated update result without a final uniprot ac
     */
    public IdentificationResults createUnsuccessfulUpdateResult(){
        IdentificationResults results = new IdentificationResults();

        return results;
    }

    /**
     *
     * @return auto-generated action report for a protein without any sequences and without any identity cross references
     */
    public MappingReport createUpdateReportWithNoSequenceNoIdentityXRef(){
        MappingReport report = new MappingReport(ActionName.update_checking);

        report.setASwissprotEntry(false);
        report.setStatus(new Status(StatusLabel.FAILED, "There is neither a sequence nor an identity xref"));
        return report;
    }

    /**
     *
     * @return auto-generated action report with a conflict during the update
     */
    public MappingReport createUpdateReportWithConflict(){
        MappingReport report = new MappingReport(ActionName.update_checking);

        report.setASwissprotEntry(false);
        report.setStatus(new Status(StatusLabel.TO_BE_REVIEWED, "There is a conflict"));
        return report;
    }

    /**
     *
     * @return auto-generated MappingReport containing feature range conflicts
     */
    public MappingReport createFeatureRangeCheckingReportWithConflict(){
        MappingReport report = new MappingReport(ActionName.feature_range_checking);

        report.setASwissprotEntry(false);
        report.setStatus(new Status(StatusLabel.FAILED, "There is a conflict"));
        return report;
    }

    /**
     *
     * @return auto-generated MappingReport with a status FAILED
     */
    public MappingReport createReportWithStatusFailed(){
        MappingReport report = new MappingReport(ActionName.PICR_accession);

        report.setASwissprotEntry(false);
        report.setStatus(new Status(StatusLabel.FAILED, "PICR couldn't match the accession to any Uniprot entries"));
        return report;
    }

    public UpdatedAnnotation createInvalidRangeAnnotation(){
        UpdatedAnnotation feature = new UpdatedAnnotation();

        feature.setTopic(invalidRangeAc);
        feature.setText("2..3-7..8");
        feature.setStatus(UpdateStatus.added);

        return feature;
    }

    public UpdatedAnnotation createOutOfDateRangeAnnotation(){
        UpdatedAnnotation feature = new UpdatedAnnotation();

        feature.setTopic(outOfDateRangeAc);
        feature.setText("2-8");
        feature.setStatus(UpdateStatus.added);

        return feature;
    }

    public UpdateProcess createUpdateProcess(){
        UpdateProcess process = new UpdateProcess();

        process.setDate(new Date(System.currentTimeMillis()));

        return process;
    }

    public InvalidRange createInvalidRange(){

        InvalidRange invalid = new InvalidRange();

        invalid.setRangeAc("EBI-xxxxx1");
        invalid.setComponentAc("EBI-xxxxx2");
        invalid.setFromStatus("EBI-xxxxxx3");
        invalid.setToStatus("EBI-xxxxxx4");

        invalid.setOldSequence("MAAM");
        invalid.setNewSequence("SSPP");
        invalid.setOldPositions("2..3-7..8");
        invalid.setNewSequence("0-0");
        invalid.setSequenceVersion(-1);

        invalid.setErrorMessage("certain position cannot be a range");

        invalid.addUpdatedAnnotation(createInvalidRangeAnnotation());

        return invalid;
    }

    public UpdatedRange createUpdatedRange(){

        UpdatedRange updated = new UpdatedRange();

        updated.setRangeAc("EBI-xxxxx1");
        updated.setComponentAc("EBI-xxxxx2");

        updated.setOldSequence("MAAM");
        updated.setNewSequence("MAAM");
        updated.setOldPositions("2-8");
        updated.setNewSequence("4-10");

        return updated;
    }

    public InvalidRange createOutOfDateRangeWithoutSequenceVersion(){

        InvalidRange invalid = new InvalidRange();

        invalid.setRangeAc("EBI-xxxxx1");
        invalid.setComponentAc("EBI-xxxxx2");
        invalid.setFromStatus("EBI-xxxxxx3");
        invalid.setToStatus("EBI-xxxxxx4");

        invalid.setOldSequence("MAAM");
        invalid.setNewSequence("SSPP");
        invalid.setOldPositions("2-8");
        invalid.setNewSequence("0-0");
        invalid.setSequenceVersion(-1);

        invalid.setErrorMessage("Out of date range");

        invalid.addUpdatedAnnotation(createOutOfDateRangeAnnotation());

        return invalid;
    }

    public InvalidRange createOutOfDateRangeWithSequenceVersion(){

        InvalidRange invalid = new InvalidRange();

        invalid.setRangeAc("EBI-xxxxx1");
        invalid.setComponentAc("EBI-xxxxx2");
        invalid.setFromStatus("EBI-xxxxxx3");
        invalid.setToStatus("EBI-xxxxxx4");

        invalid.setOldSequence("MAAM");
        invalid.setNewSequence("SSPP");
        invalid.setOldPositions("2-8");
        invalid.setNewSequence("0-0");
        invalid.setSequenceVersion(2);

        invalid.setErrorMessage("Out of date range");

        invalid.addUpdatedAnnotation(createOutOfDateRangeAnnotation());

        return invalid;
    }

    public UpdatedAnnotation createDefaultUpdatedAnnotation(){
        UpdatedAnnotation ann = new UpdatedAnnotation();

        ann.setStatus(UpdateStatus.added);
        ann.setTopic("EBI-xxxxx6");
        ann.setText("bla");

        return ann;
    }

    public UpdatedCrossReference createDefaultUpdatedCrossReference(){
        UpdatedCrossReference ref = new UpdatedCrossReference();

        ref.setStatus(UpdateStatus.added);
        ref.setDatabase("EBI-xxxxx7");
        ref.setIdentifier("blax");
        ref.setQualifier("EBI-xxxxx8");

        return ref;
    }

    public UpdatedAlias createDefaultUpdatedAlias(){
        UpdatedAlias al = new UpdatedAlias();

        al.setStatus(UpdateStatus.added);
        al.setName("GP2");
        al.setType("EBI-xxxxx9");

        return al;
    }

    public ProteinEvent createDefaultProteinEvent(){
        ProteinEvent event = new ProteinEvent();

        event.setName(EventName.uniprot_update);
        event.setIndex(1);
        event.setProteinAc("EBI-xx10xx10xx");

        return event;
    }

    public ProteinEvent createDefaultProteinEventWithCollection(){
        ProteinEvent event = createDefaultProteinEvent();

        event.addUpdatedAnnotation(createDefaultUpdatedAnnotation());
        event.addUpdatedAlias(createDefaultUpdatedAlias());
        event.addUpdatedXRef(createDefaultUpdatedCrossReference());

        return event;
    }

    public DeadProteinEvent createDefaultDeadProteinEvent(){
        DeadProteinEvent proteinEvent = new DeadProteinEvent();

        proteinEvent.setIndex(1);
        proteinEvent.setName(EventName.dead_protein);
        proteinEvent.setProteinAc("EBI-xxxxx10");
        proteinEvent.setUniprotReference("P12345");

        return proteinEvent;
    }

}
