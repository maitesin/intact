/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
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

package intact.solution.chapter2.exercise3;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import psidev.psi.mi.search.SearchResult;
import psidev.psi.mi.search.Searcher;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.File;

/**
 * Question 2:  Write a program that queries the local Lucene index to search for interaction evidences involving specific molecules. For instance by uniprot identifier O45406 or pubmed id 17129783.
 *
 * @see org.apache.lucene.store.FSDirectory#getDirectory(String)
 * @see org.apache.lucene.store.Directory
 * @see psidev.psi.mi.search.Searcher#search(String, org.apache.lucene.store.Directory)
 * @see psidev.psi.mi.search.SearchResult
 * @see psidev.psi.mi.tab.model.BinaryInteraction
 * @see psidev.psi.mi.tab.model.BinaryInteraction#getInteractorA()
 * @see psidev.psi.mi.tab.model.BinaryInteraction#getInteractorB()
 */
public class Q2_QueryLocalIndexUsingMIQL {
    public static void main( String[] args ) throws Exception {

        // Prepare the local index
        File indexLocation = new File( "mitab.index" );
        if( !indexLocation.exists() || !indexLocation.canRead() ) {
            throw new IllegalStateException( "Could not read index: " + indexLocation.getAbsolutePath() );
        }
        Directory directory = FSDirectory.getDirectory(indexLocation );

        // Other queries you can test:
        //   - id:O45406
        //   - pubid:17129783
        //   - type:\"physical interaction\"
        //   - type:\"MI:0218\"

        final String query = "id:O45406";

        // After a directory has been created, you can use this directory (or any existing directory
        // in the filesystem) to execute your searches.
        SearchResult<?> result = Searcher.search( query, directory );

        // We print some information about the interactions found
        System.out.println( "Interactions found: " + result.getTotalCount() );

        for ( BinaryInteraction binaryInteraction : result.getData() ) {
            String interactorIdA = binaryInteraction.getInteractorA().getIdentifiers().iterator().next().getIdentifier();
            String interactorIdB = binaryInteraction.getInteractorB().getIdentifiers().iterator().next().getIdentifier();

            System.out.println( "\t" + interactorIdA + " interacts with " + interactorIdB );
        }
    }
}