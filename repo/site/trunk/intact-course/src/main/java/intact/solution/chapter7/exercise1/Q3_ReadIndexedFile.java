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

package intact.solution.chapter7.exercise1;

import psidev.psi.mi.xml.PsimiXmlLightweightReader;
import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.Interaction;
import psidev.psi.mi.xml.xmlindex.IndexedEntry;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Question 3: You can read files in a memory-efficient way by using the psidev.psi.mi.xml.PsimiXmlLightweightReader,
 * which internally indexes the file to read one interaction at a time.
 * Could you write a class using this reader and printing some information about the interactions in the file?
 *
 * @see psidev.psi.mi.xml.PsimiXmlLightweightReader
 * @see psidev.psi.mi.xml.PsimiXmlLightweightReader#getIndexedEntries()
 * @see psidev.psi.mi.xml.xmlindex.IndexedEntry
 * @see psidev.psi.mi.xml.xmlindex.IndexedEntry#unmarshallInteractionIterator()
 */
public class Q3_ReadIndexedFile {
    public static void main( String[] args ) throws PsimiXmlReaderException {

        // The data file to be parsed by the API
        File inputFile = new File( Q3_ReadIndexedFile.class.getResource( "/samples/psixml25/16705748.xml" ).getFile() );

        // Create a reader
        PsimiXmlLightweightReader reader = new PsimiXmlLightweightReader( inputFile );

        // Read the whole file into an EntrySet
        final List<IndexedEntry> indexedEntries = reader.getIndexedEntries();

        // Show all interactions, their respective id and label
        for ( IndexedEntry entry : indexedEntries ) {
            final Iterator<Interaction> iterator = entry.unmarshallInteractionIterator();
            while ( iterator.hasNext() ) {
                Interaction interaction = iterator.next();

                final String label = interaction.getNames().getShortLabel();
                final int id = interaction.getId();

                System.out.println( "Interaction " + id + ": " + label );
            }
        }
    }
}