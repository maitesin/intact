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

package intact.solution.psimitab;

import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Read a whole MITAB file into a Collection of BinaryInteraction.
 *
 * @see psidev.psi.mi.tab.PsimiTabReader
 * @see psidev.psi.mi.tab.model.BinaryInteraction
 */
public class ReadWholeFile {
    public static void main( String[] args ) throws ConverterException, IOException {

        // Prepare the input MITAB file
        File intputFile = new File( ReadWholeFile.class.getResource( "/samples/mitab/18189341.txt" ).getFile() );

        System.out.println( "Reading MITAB data from: " + intputFile.getAbsolutePath() );

        // Instanciate the reader class that supports the Standard MITAB data format
        PsimiTabReader reader = new PsimiTabReader( true );

        // Read the whole file into a collection
        final Collection<BinaryInteraction> interactions = reader.read( intputFile );

        System.out.println( "Read "+ interactions.size() +" binary interactions" );
    }
}