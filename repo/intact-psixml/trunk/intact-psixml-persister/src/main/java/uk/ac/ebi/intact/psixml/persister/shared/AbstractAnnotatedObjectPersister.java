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
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.psixml.persister.PersisterException;
import uk.ac.ebi.intact.psixml.persister.PersisterReport;
import uk.ac.ebi.intact.psixml.persister.key.AnnotatedObjectKey;
import uk.ac.ebi.intact.psixml.persister.service.AnnotatedObjectService;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class AbstractAnnotatedObjectPersister<T extends AnnotatedObject> extends AbstractPersister<T> {

    private AnnotatedObjectService service;

    public AbstractAnnotatedObjectPersister(IntactContext intactContext, boolean dryRun) {
        super(intactContext, dryRun);
        this.service = new AnnotatedObjectService(intactContext);
    }

    public PersisterReport saveOrUpdate(T intactObject) throws PersisterException {
        PersisterReport report = new PersisterReport();

        T ao = (T) service.get(new AnnotatedObjectKey(intactObject));

        if (ao == null) {
            ao = intactObject;
            super.persist(ao, service, report);
        }

        return report;
    }

}