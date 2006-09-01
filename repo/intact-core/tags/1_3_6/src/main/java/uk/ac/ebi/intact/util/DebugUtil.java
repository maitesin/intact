/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.IntactObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for debugging / logging purposes
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29-Aug-2006</pre>
 */
public class DebugUtil
{

    private static final Log log = LogFactory.getLog(DebugUtil.class);

    /**
     * Returns the list of labels for the annotated objects provided
     * @param intactObjects list of annotated objects
     * @return a list of labels
     */
    public static List<String> labelList(Collection<? extends AnnotatedObject> intactObjects)
    {
        List<String> labels = new ArrayList<String>(intactObjects.size());

        for (AnnotatedObject io : intactObjects)
        {
            labels.add(io.getShortLabel());
        }

        return labels;
    }

    /**
     * Returns the list of ACs for the annotated objects provided
     * @param intactObjects list of intact objects
     * @return a list of Acs
     */
    public static List<String> acList(Collection<? extends IntactObject> intactObjects)
    {
        List<String> acs = new ArrayList<String>(intactObjects.size());

        for (IntactObject io : intactObjects)
        {
            acs.add(io.getAc());
        }

        return acs;
    }

}
