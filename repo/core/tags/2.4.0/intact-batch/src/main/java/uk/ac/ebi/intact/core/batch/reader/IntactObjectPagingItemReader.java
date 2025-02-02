/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.core.batch.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.model.IntactObject;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class IntactObjectPagingItemReader extends JpaPagingItemReader {

    private String query;
    private Class<? extends IntactObject> intactObjectClass;

    public IntactObjectPagingItemReader() {
        super();
    }

    public void setIntactObjectClass(Class<? extends IntactObject> intactObjectClass) {
        this.intactObjectClass = intactObjectClass;
        this.query = "select intactObj from " + intactObjectClass.getName()+" intactObj";
        setQueryString(query);
    }
}
