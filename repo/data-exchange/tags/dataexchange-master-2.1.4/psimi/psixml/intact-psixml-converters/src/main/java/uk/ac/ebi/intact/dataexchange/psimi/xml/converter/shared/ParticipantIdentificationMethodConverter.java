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
package uk.ac.ebi.intact.dataexchange.psimi.xml.converter.shared;

import psidev.psi.mi.xml.model.ParticipantIdentificationMethod;
import uk.ac.ebi.intact.model.CvIdentification;
import uk.ac.ebi.intact.model.Institution;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ParticipantIdentificationMethodConverter extends CvObjectConverter<CvIdentification, ParticipantIdentificationMethod> {

    public ParticipantIdentificationMethodConverter(Institution institution) {
        super(institution, CvIdentification.class, ParticipantIdentificationMethod.class);
    }
}