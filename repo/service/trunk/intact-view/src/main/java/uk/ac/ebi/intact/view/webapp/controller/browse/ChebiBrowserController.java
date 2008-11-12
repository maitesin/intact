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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import uk.ac.ebi.intact.bridges.ontologies.term.OntologyTerm;
import uk.ac.ebi.intact.view.webapp.controller.BaseController;
import uk.ac.ebi.intact.view.webapp.controller.application.IndexRequestController;
import uk.ac.ebi.intact.view.webapp.controller.search.SearchController;
import uk.ac.ebi.intact.view.webapp.controller.search.UserQuery;
import uk.ac.ebi.intact.view.webapp.util.ChebiOntologyTerm;

import javax.annotation.PostConstruct;


/**
 * TODO comment that class header
 *
 * @author Prem Anand (prem@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.1-SNAPSHOT
 */
@Controller( "chebiBrowser" )
@Scope( "request" )
public class ChebiBrowserController extends BaseController {

    @Autowired
    private IndexRequestController indexRequestController;

    @Autowired
    private SearchController searchController;

    private OntologyTreeModel chebiOntologyTreeModel;


    public ChebiBrowserController() {
    }

    @PostConstruct
    public void init() {
        final UserQuery userQuery = searchController.getUserQuery();

        String searchQuery = userQuery.getCurrentQuery();
        String luceneQuery = searchController.getResults().getResult().getLuceneQuery().toString();

        final OntologyTerm chebiOntologyRoot = new ChebiOntologyTerm( indexRequestController.getOntologyIndexSearcher() );
        chebiOntologyTreeModel = new OntologyTreeModel( chebiOntologyRoot,
                                                        null,
                                                        null,
                                                        searchQuery,
                                                        luceneQuery );

    }

    public OntologyTreeModel getOntologyTreeModel() {
        return chebiOntologyTreeModel;
    }


}
