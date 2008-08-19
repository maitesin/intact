/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
 *  limitations under the License.
 */
package uk.ac.ebi.intact.psimitab.search;

import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.Searcher;
import psidev.psi.mi.tab.model.builder.DocumentDefinition;
import uk.ac.ebi.intact.psimitab.IntactDocumentDefinition;

/**
 * IntActSearchEngine Tester.
 *
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id$
 * @since 2.0.0
 */
public class IntActSearchEngineTest {

    private DocumentDefinition docDef;

    @Before
    public void before() {
        this.docDef = new IntactDocumentDefinition();
    }

    @After
    public void after() {
        this.docDef = null;
    }

    @Test
    public void testExperimentalRole() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("experimentalRoleA:bait");
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(183, result.getInteractions().size());

        searchQuery = ("roles:\"unspecified role\"");
        result = searchEngine.search(searchQuery, null, null);
        assertEquals(200, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.EXPERIMENTAL_ROLE_B).getSortableColumnName());
        result = searchEngine.search(("experimentalRoleB:prey"), 20, 10, sort);
        assertEquals(10, result.getInteractions().size());
        assertEquals("Q9BZL6", result.getInteractions().get(0).getInteractorB().getIdentifiers().iterator().next().getIdentifier());
    }

    @Test
    public void testBiologicalRole() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("biologicalRoleA:bait");
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(0, result.getInteractions().size());

        searchQuery = ("roles:\"unspecified role\"");
        result = searchEngine.search(searchQuery, null, null);
        assertEquals(200, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.BIOLOGICAL_ROLE_B).getSortableColumnName());
        result = searchEngine.search(("biologicalRoleB:\"unspecified role\""), 20, 10, sort);
        assertEquals(10, result.getInteractions().size());
        assertEquals("Q08345", result.getInteractions().get(0).getInteractorB().getIdentifiers().iterator().next().getIdentifier());
    }

    @Test
    public void testProperties() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = "\"GO:0006928\"";
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(5, result.getInteractions().size());

        searchQuery = ("GO*");
        result = searchEngine.search(searchQuery, null, 10);
        assertEquals(10, result.getInteractions().size());

        searchQuery = ("propertiesB:interpro");
        result = searchEngine.search(searchQuery, null, 10);
        assertEquals(10, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.PROPERTIES_A).getSortableColumnName());
        result = searchEngine.search(("properties:IPR008271"), 20, 10, sort);
        assertEquals(10, result.getInteractions().size());
        assertEquals("Q9H0K1", result.getInteractions().get(0).getInteractorB().getIdentifiers().iterator().next().getIdentifier());
    }

    @Test
    public void testProperties_usingSearcher() throws Exception {
        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        SearchResult<?> result = Searcher.search("GO\\:0006928", searchEngine);
        assertEquals(5, result.getInteractions().size());
    }

    @Test
    public void testInteractorType() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("typeA:\"small molecule\"");
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(180, result.getInteractions().size());

        searchQuery = ("typeB:protein");
        result = searchEngine.search(searchQuery, null, 10);
        assertEquals(10, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.INTERACTOR_TYPE_B).getSortableColumnName());
        result = searchEngine.search(("interactor_types:\"small molecule\""), 10, 20, sort);
        assertEquals(20, result.getInteractions().size());
        assertEquals("P62993", result.getInteractions().get(0).getInteractorB().getIdentifiers().iterator().next().getIdentifier());
    }

    @Test
    public void testHostorganism() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("hostOrganism:\"in vitro\"");
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(39, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.HOST_ORGANISM).getSortableColumnName());
        result = searchEngine.search(("in*"), 50, 20, sort);
        assertEquals(20, result.getInteractions().size());
    }

    @Test
    public void testExpansionMethod() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("expansion:spoke");
        SearchResult<?> result = searchEngine.search(searchQuery, null, null);
        assertEquals(161, result.getInteractions().size());

        Sort sort = new Sort(docDef.getColumnDefinition(IntactDocumentDefinition.EXPANSION_METHOD).getSortableColumnName());
        result = searchEngine.search(("spoke"), 50, 20, sort);
        assertEquals(20, result.getInteractions().size());
    }

    @Test
    public void testDataSet() throws Exception {

        Directory indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/intact.sample-extra.txt");
        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("dataset:Cancer");
        SearchResult result = searchEngine.search(searchQuery, null, null);
        assertEquals(0, result.getInteractions().size());

        indexDirectory = TestHelper.createIndexFromResource("/mitab_samples/17292829.txt");
        searchEngine = new IntactSearchEngine(indexDirectory);

        //searchQuery = ("dataset:\"Cancer - Interactions investigated in the context of cancer\"");
        searchQuery = ("dataset:Cancer");
        result = searchEngine.search(searchQuery, null, null);
        assertEquals(1, result.getInteractions().size());
    }

    @Test
    public void testAnnotations() throws Exception {
        String psiMiTabLine = "uniprotkb:P16884|intact:EBI-446344\tuniprotkb:Q60824|intact:EBI-446159\tuniprotkb:Nefh(gene name)\tuniprotkb:Dst(gene name)\tintact:Nfh\tintact:Bpag1\tMI:0018(2 hybrid)\tLeung et al. (1999)\tpubmed:9971739\ttaxid:10116(rat)\ttaxid:10090(mouse)\tMI:0218(physical interaction)\tMI:0469(intact)\tintact:EBI-446356\t-\tMI:0498(prey)\tMI:0496(bait)\tMI:0499(unspecified role)\tMI:0499(unspecified role)\tinterpro:IPR004829|interpro:IPR010790|interpro:IPR001664|uniprotkb:O35482|rgd:3159|ensembl:ENSRNOG00000008716|uniprotkb:Q540Z7|uniprotkb:Q63368\tgo:0005737|go:0030056|go:0005200|go:0045104|interpro:IPR001589|interpro:IPR001715|interpro:IPR002048|interpro:IPR001101|uniprotkb:Q60845|uniprotkb:Q9WU50|go:0008090|go:0015629|go:0015630|go:0060053|go:0008017|go:0031122|go:0031110|ensembl:ENSMUSG00000026131\tMI:0326(protein)\tMI:0326(protein)\tyeast:4932\t-\t-\tcomment:commentA\t-";

        Directory indexDirectory = TestHelper.createIndexFromLine(psiMiTabLine);

        IntactSearchEngine searchEngine = new IntactSearchEngine(indexDirectory);

        String searchQuery = ("annotationA:lala");
        SearchResult result = searchEngine.search(searchQuery, null, null);
        assertEquals(0, result.getInteractions().size());

        searchQuery = ("annotationA:commentA");
        result = searchEngine.search(searchQuery, null, null);
        assertEquals(1, result.getInteractions().size());

        searchQuery = ("annotation:commentA");
        result = searchEngine.search(searchQuery, null, null);
        assertEquals(1, result.getInteractions().size());
    }
}
