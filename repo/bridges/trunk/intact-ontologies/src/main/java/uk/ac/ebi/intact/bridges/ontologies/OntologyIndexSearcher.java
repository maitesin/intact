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
package uk.ac.ebi.intact.bridges.ontologies;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class OntologyIndexSearcher extends IndexSearcher {

    public OntologyIndexSearcher(String s) throws CorruptIndexException, IOException {
        super(s);
    }

    public OntologyIndexSearcher(Directory directory) throws CorruptIndexException, IOException {
        super(directory);
    }

    public OntologyIndexSearcher(IndexReader indexReader) {
        super(indexReader);
    }

    public OntologyHits searchByChildId(String childrenId) throws IOException {
        return new OntologyHits(search(new TermQuery(new Term(FieldName.CHILDREN_ID, childrenId))));
    }

     public OntologyHits searchByChildId(String childrenId, Sort sort) throws IOException {
        return new OntologyHits(search(new TermQuery(new Term(FieldName.CHILDREN_ID, childrenId)), sort));
    }

    public OntologyHits searchByParentId(String parentId) throws IOException {
        return new OntologyHits(search(new TermQuery(new Term(FieldName.PARENT_ID, parentId))));
    }

    public OntologyHits searchByParentId(String parentId, Sort sort) throws IOException {
        return new OntologyHits(search(new TermQuery(new Term(FieldName.PARENT_ID, parentId)), sort));
    }
}
