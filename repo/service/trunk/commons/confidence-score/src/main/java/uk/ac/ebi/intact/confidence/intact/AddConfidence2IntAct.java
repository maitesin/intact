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
 *  limitations under the License.
 */
package uk.ac.ebi.intact.confidence.intact;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.bridges.blast.BlastConfig;
import uk.ac.ebi.intact.bridges.blast.model.UniprotAc;
import uk.ac.ebi.intact.confidence.maxent.OpenNLPMaxEntClassifier;
import uk.ac.ebi.intact.confidence.utils.ParserUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Class to for adding confidence values to interactions in IntAct.
 *
 * @author Irina Armean (iarmean@ebi.ac.uk)
 * @version $Id$
 * @since 1.6.0-SNAPSHOT
 */
public class AddConfidence2IntAct {
    /**
	 * Sets up a logger for that class.
	 */
	public static final Log log	= LogFactory.getLog(AddConfidence2IntAct.class);
    private File workDir;

    private IntactScoreCalculator scoreCalculator;


    public AddConfidence2IntAct( File workDir, BlastConfig config, File highconfFile, File gisModelFile, File goaFile ) throws IOException {
        workDir = workDir;
        if (log.isTraceEnabled()){
            log.trace("goaFile: "+ goaFile.getPath());
        }
        OpenNLPMaxEntClassifier classifier = new OpenNLPMaxEntClassifier( gisModelFile );
        
        Set<UniprotAc> againstProts = ParserUtils.parseProteins( highconfFile );

        scoreCalculator = new IntactConfidenceCalculator( classifier, config, againstProts, goaFile, workDir );
    }
}
