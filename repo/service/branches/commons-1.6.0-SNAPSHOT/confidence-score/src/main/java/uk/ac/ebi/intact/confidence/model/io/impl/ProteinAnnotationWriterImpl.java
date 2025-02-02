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
package uk.ac.ebi.intact.confidence.model.io.impl;

import uk.ac.ebi.intact.confidence.model.ProteinAnnotation;
import uk.ac.ebi.intact.confidence.model.io.ProteinAnnotationWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

/**
 * Writer implementation for ProteinAnnotation objects.
 *
 * @author Irina Armean (iarmean@ebi.ac.uk)
 * @version $Id$
 * @since 1.6.0
 *        <pre>
 *        10-Dec-2007
 *        </pre>
 */
public class ProteinAnnotationWriterImpl implements ProteinAnnotationWriter {

    public void append( ProteinAnnotation proteinAnnotation, File outFile ) throws IOException {
        if (proteinAnnotation != null){
            Writer writer = new FileWriter(outFile,true);
            writer.append(proteinAnnotation.convertToString() + "\n");
            writer.close();
        }
    }

    public void append( List<ProteinAnnotation> proteins, File outFile) throws IOException{
        Writer writer = new FileWriter(outFile,true);
        for ( Iterator<ProteinAnnotation> iterator = proteins.iterator(); iterator.hasNext(); ) {
            ProteinAnnotation proteinAnnotation = iterator.next();
            writer.append(proteinAnnotation.convertToString() + "\n");
        }
        writer.close();
    }

    public void write( Collection<ProteinAnnotation> proteinAnnotations, File inFile ) throws IOException{
        Writer writer = new FileWriter(inFile);
        for ( Iterator<ProteinAnnotation> proteinAnnotationIterator = proteinAnnotations.iterator(); proteinAnnotationIterator.hasNext(); )
        {
            ProteinAnnotation proteinAnnotation =  proteinAnnotationIterator.next();
            writer.append( proteinAnnotation.convertToString() + "\n");
        }
        writer.close();
    }
}
