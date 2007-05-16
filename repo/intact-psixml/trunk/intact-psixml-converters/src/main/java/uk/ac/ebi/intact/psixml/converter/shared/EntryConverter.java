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

import psidev.psi.mi.xml.model.Entry;
import psidev.psi.mi.xml.model.ExperimentDescription;
import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.model.Experiment;
import uk.ac.ebi.intact.psixml.converter.AbstractIntactPsiConverter;
import uk.ac.ebi.intact.psixml.converter.model.IntactEntry;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class EntryConverter extends AbstractIntactPsiConverter<IntactEntry, Entry> {

    public EntryConverter(IntactContext intactContext) {
        super(intactContext, null);
    }

    public IntactEntry psiToIntact(Entry psiObject) {

        Collection<Experiment> experiments = new ArrayList<Experiment>();

        ExperimentConverter expConverter = new ExperimentConverter(getIntactContext(), psiObject);

        for (ExperimentDescription expDescription : psiObject.getExperiments()) {
            Experiment experiment = expConverter.psiToIntact(expDescription);
            experiments.add(experiment);
        }

        IntactEntry ientry = new IntactEntry(experiments);

        return ientry;
    }

    public Entry intactToPsi(IntactEntry intactObject) {
        throw new UnsupportedOperationException();
    }
}