/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.psixml.converter.shared;

import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.InteractionDetectionMethod;
import psidev.psi.mi.xml.model.Organism;
import psidev.psi.mi.xml.model.ParticipantIdentificationMethod;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.psixml.converter.AbstractIntactPsiConverter;
import uk.ac.ebi.intact.psixml.converter.util.ConverterUtils;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ExperimentConverter extends AbstractIntactPsiConverter<Experiment, ExperimentDescription> {

    public ExperimentConverter(Institution institution) {
        super(institution);
    }

    public Experiment psiToIntact(ExperimentDescription psiObject) {
        String shortLabel = psiObject.getNames().getShortLabel();

        Organism hostOrganism = psiObject.getHostOrganisms().iterator().next();
        BioSource bioSource = new OrganismConverter(getInstitution()).psiToIntact(hostOrganism);

        InteractionDetectionMethod idm = psiObject.getInteractionDetectionMethod();
        CvInteraction cvInteractionDetectionMethod = new InteractionDetectionMethodConverter(getInstitution()).psiToIntact(idm);

        Experiment experiment = new Experiment(getInstitution(), shortLabel, bioSource);
        ConverterUtils.populateNames(psiObject.getNames(), experiment);
        ConverterUtils.populateXref(psiObject.getXref(), experiment, new XrefConverter<ExperimentXref>(getInstitution(), ExperimentXref.class));
        experiment.setCvInteraction(cvInteractionDetectionMethod);

        ParticipantIdentificationMethod pim = psiObject.getParticipantIdentificationMethod();
        if (pim != null) {
            CvIdentification cvParticipantIdentification = new ParticipantIdentificationMethodConverter(getInstitution()).psiToIntact(pim);
            experiment.setCvIdentification(cvParticipantIdentification);
        }

        return experiment;
    }

    public ExperimentDescription intactToPsi(Experiment intactObject) {
        throw new UnsupportedOperationException();
    }
}