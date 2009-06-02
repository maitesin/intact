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
package uk.ac.ebi.intact.dataexchange.enricher.fetch;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import uk.ac.ebi.intact.dataexchange.enricher.EnricherContext;
import uk.ac.ebi.intact.model.CvDagObject;
import uk.ac.ebi.intact.model.CvObject;

import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Controller
public class CvObjectFetcher {

    /**
     * Sets up a logger for that class.
     */
    private static final Log log = LogFactory.getLog(CvObjectFetcher.class);

    @Autowired
    private EnricherContext enricherContext;

    public CvObjectFetcher() {
    }

    public <T extends CvObject> T fetchByTermId(Class<T> objClass, String termId) {
        Cache cache = enricherContext.getCache("CvObject");

        T term = null;

        String key = objClass.getSimpleName()+"_"+termId;

        if (cache.isKeyInCache(termId)) {
            final Element element = cache.get(termId);

            if (element != null) {
                term = (T) element.getObjectValue();
            } else {
                if (log.isDebugEnabled())
                    log.debug("Term was found in the cache but the element returned was null: "+termId);
            }

        }

        if (term == null) {
            List<CvDagObject> ontology = enricherContext.getIntactOntology();
            term = searchById(ontology, objClass, termId);

            if (term != null) {
                cache.put(new Element(key, term));
            }
        }

        return term;
    }

    public <T extends CvObject> T fetchByShortLabel(Class<T> cvClass, String label) {
        if (cvClass == null || label == null) {
            return null;
        }

        Cache cache = enricherContext.getCache("CvObject");

        String key = cvClass.getSimpleName()+"_"+label;

        T term;

        if (cache.isKeyInCache(key)) {
            term = (T) cache.get(key).getObjectValue();
        } else {
            List<CvDagObject> ontology = enricherContext.getIntactOntology();

            term = searchByLabel(ontology, cvClass, label);

            if (term != null) {
                cache.put(new Element(key, term));
                cache.put(new Element(cvClass.getSimpleName()+"_"+term.getMiIdentifier(), term));
            }
        }

        return term;
    }

    private <T extends CvObject> T searchById(List<CvDagObject> ontology, Class<T> objClass, String termId) {
        for (CvObject cv : ontology) {
            if (objClass.getName().equals(cv.getObjClass()) && termId.equals(cv.getMiIdentifier())) {
                return (T) cv;
            }
        }

        return null;
    }

    private <T extends CvObject> T searchByLabel(List<CvDagObject> ontology, Class<T> objClass, String shortLabel) {
        for (CvObject cv : ontology) {

            if (objClass.getName().equals(cv.getObjClass()) && shortLabel.equals(cv.getShortLabel())) {
                return (T) cv;
            }
        }

        return null;
    }

}
