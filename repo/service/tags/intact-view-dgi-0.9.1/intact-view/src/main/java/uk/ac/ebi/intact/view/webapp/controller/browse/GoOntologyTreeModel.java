/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.view.webapp.controller.browse;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import uk.ac.ebi.intact.bridges.ontologies.OntologyIndexSearcher;
import uk.ac.ebi.intact.bridges.ontologies.term.LazyLoadedOntologyTerm;
import uk.ac.ebi.intact.bridges.ontologies.term.OntologyTerm;
import uk.ac.ebi.intact.psimitab.search.IntactSearchEngine;
import uk.ac.ebi.intact.view.webapp.controller.SearchWebappException;

import java.util.*;

/**
 * Data model for the GO browser.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class GoOntologyTreeModel extends ChildPropertyTreeModel {

    private String[] INTERACTOR_COLOURS = new String[] {"#73b360", "#84bc73", "#96c688", "#a7cf9b","#cae2c3", "#dcecd7"};
    private String[] INTERACTION_COLOURS = new String[] {"#006666", "#1f7979", "#408c8c", "#5e9e9e","#a1c7c7", "#bdd7d7"};

    private Set<String> processedTermCounts;

    private IndexSearcher interactionIndexSearcher;
    private IndexSearcher interactorIndexSearcher;

    private OntologyTermWrapper disclosed;

    public GoOntologyTreeModel(final OntologyIndexSearcher ontologyIndexSearcher,
                               final IndexSearcher interactionIndexSearcher,
                               final IndexSearcher interactorIndexSearcher,
                               final String baseQuery,
                               final String luceneQuery) {
        setChildProperty("children");

        this.interactionIndexSearcher = interactionIndexSearcher;
        this.interactorIndexSearcher = interactorIndexSearcher;

        processedTermCounts = new HashSet<String>();

        OntologyTerm root = new OntologyTerm() {
            public String getId() {
                return "";
            }

            public String getName() {
                return "GO Ontology";
            }

            public List<OntologyTerm> getParents() {
                return Collections.EMPTY_LIST;
            }

            public List<OntologyTerm> getChildren() {
                List<OntologyTerm> children = new ArrayList<OntologyTerm>( 3 );

                children.add(new LazyLoadedOntologyTerm(ontologyIndexSearcher, "GO:0008150", "Biological process"));
                children.add(new LazyLoadedOntologyTerm(ontologyIndexSearcher, "GO:0003674", "Molecular function"));
                children.add(new LazyLoadedOntologyTerm(ontologyIndexSearcher, "GO:0005575", "Cellular component"));

                return children;
            }

            public List<OntologyTerm> getParents(boolean includeCyclic) {
                return getParents();
            }

            public List<OntologyTerm> getChildren(boolean includeCyclic) {
                return getChildren();
            }

            public Set<OntologyTerm> getAllParentsToRoot() {
                return Collections.EMPTY_SET;
            }

            public Collection<OntologyTerm> getChildrenAtDepth(int depth) {
                throw new UnsupportedOperationException("Root does not support this operation");
            }
        };

        OntologyTermWrapper otwRoot = new OntologyTermWrapper(root, interactionIndexSearcher, interactorIndexSearcher, baseQuery,luceneQuery);

        setWrappedData(otwRoot);
    }

    @Override
    protected Object getChildData(Object parentData) {
        OntologyTermWrapper parent = (OntologyTermWrapper) parentData;

        List<OntologyTermWrapper> children = new ArrayList<OntologyTermWrapper>();

        String childrenInteractorColour;
        String childrenInteractionColour;

        if (parent.getInteractorColour() == null) {
            childrenInteractorColour = INTERACTOR_COLOURS[0];
            childrenInteractionColour = INTERACTION_COLOURS[0];
        } else {
            childrenInteractorColour = nextColour(INTERACTOR_COLOURS, parent.getInteractorColour());
            childrenInteractionColour = nextColour(INTERACTION_COLOURS, parent.getInteractionColour());
        }

        for (OntologyTermWrapper child : parent.getChildren()) {
                child.setInteractorColour(childrenInteractorColour);
                child.setInteractionColour(childrenInteractionColour);

                children.add(child);
        }

        return children;
    }

    private OntologyTermWrapper getSelectedData(Integer[] indexes) {
        OntologyTermWrapper root = (OntologyTermWrapper) getWrappedData();

        List<OntologyTermWrapper> listChildren = (List<OntologyTermWrapper>) getChildData(root);

        OntologyTermWrapper parent = null;

        for (int i=1; i<indexes.length; i++) {
            parent = listChildren.get(indexes[i]);
            listChildren = (List<OntologyTermWrapper>) getChildData(parent);
        }

        return parent;
    }

    private String nextColour(String[] colourArray, String interactorColour) {
        for (int i=0; i<colourArray.length; i++) {
            if (interactorColour.equals(colourArray[i])) {
                if (i+1<colourArray.length) {
                    return colourArray[i+1];
                } else {
                    return colourArray[0];
                }
            }
        }
        return colourArray[0];
    }
}

