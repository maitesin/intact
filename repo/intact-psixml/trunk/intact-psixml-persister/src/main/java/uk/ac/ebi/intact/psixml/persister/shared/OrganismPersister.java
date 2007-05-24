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
package uk.ac.ebi.intact.psixml.persister.shared;

import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.psixml.persister.PersisterException;
import uk.ac.ebi.intact.psixml.persister.key.OrganismKey;
import uk.ac.ebi.intact.psixml.persister.service.OrganismService;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class OrganismPersister extends AbstractAnnotatedObjectPersister<BioSource> {

    private OrganismService service;

    public OrganismPersister(IntactContext intactContext, boolean dryRun) {
        super(intactContext, dryRun);
        this.service = new OrganismService(intactContext);
    }

    public BioSource saveOrUpdate(BioSource intactObject) throws PersisterException {
        PersisterHelper.syncAnnotatedObject(intactObject, getIntactContext());

        BioSource bioSource = service.get(new OrganismKey(intactObject));

        if (bioSource == null) {
            bioSource = intactObject;

            super.persist(bioSource, service, getReport());
        } else {
            intactObject = bioSource;
        }

        return intactObject;
    }

}