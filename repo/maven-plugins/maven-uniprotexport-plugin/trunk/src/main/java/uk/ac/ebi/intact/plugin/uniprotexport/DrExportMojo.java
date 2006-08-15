/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.plugin.uniprotexport;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.util.uniprotExport.DRLineExport;
import uk.ac.ebi.intact.util.uniprotExport.LineExport;
import uk.ac.ebi.intact.util.MemoryMonitor;
import uk.ac.ebi.intact.model.Protein;

import java.sql.SQLException;
import java.io.*;
import java.util.Set;
import java.util.Iterator;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14-Aug-2006</pre>
 *
 * @goal dr
 * @phase process-resources
 */
public class DrExportMojo extends UniprotExportAbstractMojo
{
    private static final String NEW_LINE = System.getProperty("line.separator");

    /**
    * Max proteins to export. Only export this amount of proteins (only useful for testing)
    * @parameter
    */
    protected Integer maxProteinsToExport;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        getLog().info("DrExportMojo in action");

        initialize();

        File drExportFile = getUniprotLinksFile();

        getLog().info( "DR export (uniprot links) will be saved in: " + drExportFile );

        if (drExportFile.exists() && !overwrite)
        {
            throw new MojoExecutionException("DR Export file already exist and overwrite is set to false: "+drExportFile);
        }

        new MemoryMonitor();

        try
        {
            FileWriter fw = new FileWriter(drExportFile);
            BufferedWriter out = new BufferedWriter(fw);

            // get the set of Uniprot ID to be exported to Swiss-Prot, using a paginated query
            // to avoid OutOfMemory errors
            Set<String> proteinEligible = null;
            int firstResult = 0;
            int maxResults = 200;

            if (isExportLimited()) {
                maxResults = Math.min(200,maxProteinsToExport);

                getLog().info("Limited export. Only "+maxProteinsToExport+" will be checked for eligibility");
            }

            int eligibleProteinsCount = 0;

            do
            {

                IntactContext.getCurrentInstance().getDataContext().beginTransaction();

                DRLineExport exporter = new DRLineExport();
                proteinEligible = exporter.getEligibleProteins(firstResult, maxResults);

                if (proteinEligible != null)
                {
                    eligibleProteinsCount = eligibleProteinsCount + proteinEligible.size();

                    getLog().debug(proteinEligible.size() + " protein(s) selected for export using a paginated query. First result: " + firstResult);

                    // save it to a file.
                    writeToFile(proteinEligible, out);

                }

                IntactContext.getCurrentInstance().getDataContext().commitTransaction();

                firstResult = firstResult + maxResults;

                if (isExportLimited() && firstResult >= maxProteinsToExport)
                {
                    break;
                }

            } while (proteinEligible != null && !proteinEligible.isEmpty());

            getLog().info("Eligible proteins found: "+eligibleProteinsCount);
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Problem writing eligible proteins", e);
        }

    }

    private static void writeToFile( Set<String> proteins, Writer out ) throws IOException {
        for (String uniprotID : proteins)
        {
            out.write(DRLineExport.formatProtein(uniprotID));
            out.write(NEW_LINE);
            out.flush();
        }
    }

    private boolean isExportLimited()
    {
        return maxProteinsToExport != null;
    }
}
