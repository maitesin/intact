package uk.ac.ebi.intact.util.uniprotExport.parameters.cclineparameters;

import java.util.List;

/**
 * CC parameters1 contains the master uniprot ac, gene name and taxId of the first interactor and a list of SecondCCparameters1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/02/11</pre>
 */

public interface CCParameters1 {

    /**
     * the uniprot AC of the first interactor (it is a master uniprot ac)
     */
    public String getMasterUniprotAc();

    /**
     * The gene name of the first interactor
     */
    public String getGeneName();

    /**
     * The taxid of the first interactor
     * @return
     */
    public String getTaxId();

    /**
     *
     * @return The list of second CC parameters
     */
    public List<SecondCCParameters1> getSecondCCParameters();
}
