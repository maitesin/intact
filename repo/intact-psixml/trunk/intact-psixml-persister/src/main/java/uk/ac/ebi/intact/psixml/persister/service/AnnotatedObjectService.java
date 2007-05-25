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
package uk.ac.ebi.intact.psixml.persister.service;

import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.persistence.dao.AnnotatedObjectDao;
import uk.ac.ebi.intact.psixml.persister.PersisterException;
import uk.ac.ebi.intact.psixml.persister.key.AnnotatedObjectKey;
import uk.ac.ebi.intact.psixml.persister.key.Key;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class AnnotatedObjectService<A extends AnnotatedObject> extends AbstractService<A> {

    public AnnotatedObjectService(IntactContext intactContext) {
        super(intactContext);
    }

    public void persist(A objectToPersist) throws PersisterException {
        getDao().persist(objectToPersist);

        getCache(objectToPersist.getClass()).put(new AnnotatedObjectKey(objectToPersist).getElement());
    }

    protected A fetchFromDb(Key key) {
        return (A) getDao().getByShortLabel((String) key.getElement().getKey());
    }

    protected AnnotatedObjectDao getDao() {
        return getIntactContext().getDataContext().getDaoFactory().getAnnotatedObjectDao();
    }

}