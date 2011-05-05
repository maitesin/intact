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

package intact.solution.chapter2.exercise2;

import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.CrossReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Iterate through a MITAB file using the MITAB library.
 *
 * @see psidev.psi.mi.tab.PsimiTabReader#iterate(java.io.File)
 * @see psidev.psi.mi.tab.model.BinaryInteraction
 */
public class Q2_ClientAndMitabBetterMemory {

    public static void main(String[] args) throws Exception {

        // Prepare the input MITAB file
        File intputFile = new File( Q1_ReadWholeFile.class.getResource( "/samples/mitab/18189341.txt" ).getFile() );

        System.out.println( "Reading MITAB data from: " + intputFile.getAbsolutePath() );

        // Instanciate the reader class that supports the Standard MITAB data format
        PsimiTabReader reader = new PsimiTabReader( true );

        try {
            InputStream is = new FileInputStream( intputFile );
            final Iterator<BinaryInteraction> interactionIterator = reader.iterate( is );

            while ( interactionIterator.hasNext() ) {
                BinaryInteraction binaryInteraction = interactionIterator.next();

                printBinaryInteraction( binaryInteraction );
            }

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printBinaryInteraction(BinaryInteraction<?> binaryInteraction) {
        // print first ids for interactors and interaction
        CrossReference idA = binaryInteraction.getInteractorA().getIdentifiers().iterator().next();
        CrossReference idB = binaryInteraction.getInteractorB().getIdentifiers().iterator().next();
        CrossReference interactionAc = binaryInteraction.getInteractionAcs().iterator().next();

        System.out.println("Interaction "+interactionAc.getIdentifier()+": "+idA.getIdentifier()+" - "+idB.getIdentifier());
    }
}
