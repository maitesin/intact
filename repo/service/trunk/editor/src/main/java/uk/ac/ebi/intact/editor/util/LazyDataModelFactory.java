/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.editor.util;

import org.primefaces.model.LazyDataModel;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class LazyDataModelFactory {

    private LazyDataModelFactory() {

    }

    public static LazyDataModel createLazyDataModel(EntityManager entityManager, String query, String countQuery) {
//        final TransactionStatus transactionStatus = IntactContext.getCurrentInstance().getDataContext()
//                .beginTransaction(TransactionDefinition.PROPAGATION_MANDATORY);

            Query q = entityManager.createQuery(countQuery);
            int totalNumRows = ((Long) q.getSingleResult()).intValue();

           // IntactContext.getCurrentInstance().getDataContext().commitTransaction(transactionStatus);

        return createLazyDataModel(entityManager, query, totalNumRows);
    }

    public static LazyDataModel createLazyDataModel(EntityManager entityManager, String query, int totalNumRows) {
        return new HqlLazyDataModel(entityManager, query, totalNumRows);
    }

}
