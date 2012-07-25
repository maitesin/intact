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
package uk.ac.ebi.intact.dataexchange.psimi.solr.converter.impl;

import org.hupo.psi.calimocho.key.CalimochoKeys;
import org.hupo.psi.calimocho.model.Field;
import uk.ac.ebi.intact.dataexchange.psimi.solr.converter.FieldFilter;

/**
 * Accepts the fields that match the type passed to the constructor.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class TypeFieldFilter implements FieldFilter {

    private String type;

    public TypeFieldFilter(String type) {
        this.type = type;
    }


    public boolean acceptField(Field field) {
        if (type == null){
           return false;
        }
        return type.equalsIgnoreCase(field.get(CalimochoKeys.DB));
    }
}
