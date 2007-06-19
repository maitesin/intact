package uk.ac.ebi.intact.commons.dataset;

import java.io.InputStream;

/**
 * Implemented for those classes that are datasets (for testing)
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface TestDataset
{
    String getId();

    InputStream getSource();

    boolean containsAllCVs();
}
