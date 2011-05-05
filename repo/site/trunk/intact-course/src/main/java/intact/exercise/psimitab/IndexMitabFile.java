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

package intact.exercise.psimitab;

import psidev.psi.mi.search.index.PsimiIndexWriter;
import psidev.psi.mi.search.util.DefaultDocumentBuilder;

import java.io.File;
import java.io.InputStream;

/**
 * Build a Lucene index based on a given mitab file.
 *
 * @see psidev.psi.mi.search.index.PsimiIndexWriter
 * @see psidev.psi.mi.search.index.PsimiIndexWriter#index(org.apache.lucene.store.Directory, java.io.InputStream, boolean, boolean)
 */
public class IndexMitabFile {
    public static void main( String[] args ) throws Exception {

        // Load a MITAB2.5 file stored locally
        InputStream psimitabFileStream = IndexMitabFile.class.getResourceAsStream("/samples/mitab/17129783.txt");

        PsimiIndexWriter indexWriter = new PsimiIndexWriter(new DefaultDocumentBuilder());
        File indexDirectory = new File( "mitab.index" );

        final boolean createIndex = true;
        final boolean hasHeader = true;
        indexWriter.index(indexDirectory, psimitabFileStream, createIndex, hasHeader);

        System.out.println( "Created a lucene index in: " + indexDirectory.getAbsolutePath() );
    }
}