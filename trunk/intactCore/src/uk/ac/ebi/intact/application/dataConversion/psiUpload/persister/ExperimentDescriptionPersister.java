/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.application.dataConversion.psiUpload.persister;

import uk.ac.ebi.intact.application.dataConversion.psiUpload.checker.*;
import uk.ac.ebi.intact.application.dataConversion.psiUpload.model.ExperimentDescriptionTag;
import uk.ac.ebi.intact.application.dataConversion.psiUpload.model.XrefTag;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * That class make the data persitent in the Intact database.
 * <br>
 * That class takes care of an Experiments object.
 * <br>
 * It assumes that the data are already parsed and passed the validity check successfully.
 * 
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */
public class ExperimentDescriptionPersister {

    // cache created experiment
    // shortlabel -> Experiment
    private static Map cache = new HashMap();


    /**
     * Make an ExperimentTag persistent as an Intact Experiemnt.
     * <br>
     * (1) check if the experiment is not existing in IntAct, if so, reuse it
     * <br>
     * (2) check if it has been made paersistent already, if so, reuse it.
     * <br>
     * (3) else, make it persistent.
     *
     * @param experimentDescription the data from which we want to make an Experiment persistent
     * @param helper                the access to the intact database
     * @return either an already existing Experiment in IntAct or a brand new one created out of the data present
     *         in the PSI file
     * @throws IntactException
     */
    public static Experiment persist( final ExperimentDescriptionTag experimentDescription,
                                      final IntactHelper helper )
            throws IntactException {

        Experiment experiment;
        final String shortlabel = experimentDescription.getShortlabel();

        // (1) check if the experiment is not existing in IntAct, if so, reuse it
        experiment = ExperimentDescriptionChecker.getIntactExperiment( shortlabel );
        if( experiment != null ) {
            // we made the choice to trust the IntAct database content, hence not to alter it.
            // altimately, this can be manually edited through the editor afterward.
            return experiment;
        }

        
        // (2) check if it has been made paersistent already, if so, reuse it.
        experiment = (Experiment) cache.get( shortlabel );

        if( experiment != null ) {
            // already created ... reuse it !
            return experiment;
        }


        // (3) Create a new Experiment
        final String taxid = experimentDescription.getHostOrganism().getTaxId();
        final BioSource biosource = HostOrganismChecker.getBioSource( taxid );

        experiment = new Experiment( helper.getInstitution(),
                                     shortlabel,
                                     biosource );

        helper.create( experiment );

        experiment.setFullName( experimentDescription.getFullname() );

        // CvIdentification
        final String participantDetectionId =
                experimentDescription.getParticipantDetection().getPsiDefinition().getId();
        final CvIdentification cvIdentification =
                ParticipantDetectionChecker.getCvIdentification( participantDetectionId );
        experiment.setCvIdentification( cvIdentification );

        // CvInteraction
        final String interactionDetectionId =
                experimentDescription.getInteractionDetection().getPsiDefinition().getId();
        final CvInteraction cvInteraction =
                InteractionDetectionChecker.getCvInteraction( interactionDetectionId );
        experiment.setCvInteraction( cvInteraction );

        // Primary Xrefs: pubmed
        final XrefTag bibRef = experimentDescription.getBibRef();
        final Xref primaryXref = new Xref( helper.getInstitution(),
                                           XrefChecker.getCvDatabase( bibRef.getDb() ),
                                           bibRef.getId(),
                                           bibRef.getSecondary(),
                                           bibRef.getVersion(),
                                           ControlledVocabularyRepository.getPrimaryXrefQualifier() );

        experiment.addXref( primaryXref );
        helper.create( primaryXref );

        // other xrefs
        final Collection xrefs = experimentDescription.getXrefs();
        for ( Iterator iterator = xrefs.iterator(); iterator.hasNext(); ) {
            XrefTag xrefTag = (XrefTag) iterator.next();

            Xref xref = new Xref( helper.getInstitution(),
                                  XrefChecker.getCvDatabase( xrefTag.getDb() ),
                                  xrefTag.getId(),
                                  xrefTag.getSecondary(),
                                  xrefTag.getVersion(),
                                  null );

            experiment.addXref( xref );
            helper.create( xref );
        }

        helper.update( experiment );

        // cache it.
        cache.put( experiment.getShortLabel(), experiment );

        return experiment;
    }
}
