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
package uk.ac.ebi.intact.view.webapp.controller.search.facet;

import org.apache.solr.client.solrj.response.FacetField;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ExpansionCount {

    private FacetField facetField;

    public ExpansionCount(FacetField facetField) {
        this.facetField = facetField;
    }

    public long getSpokeCount() {
        return getCount("spoke");
    }
    
    public long getPhysicalCount() {
       return getCount(null);
    }

    private long getCount(String fieldName) {
        for (FacetField.Count c : facetField.getValues()) {
            if (fieldName == null) {
                if (c.getName() == null) {
                    return c.getCount();
                }
            } else if (fieldName.equals(c.getName())) {
                return c.getCount();
            }
        }
        return 0;
    }
}
