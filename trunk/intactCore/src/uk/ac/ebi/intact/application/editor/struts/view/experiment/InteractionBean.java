/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.view.experiment;

import uk.ac.ebi.intact.application.editor.struts.view.AbstractEditBean;
import uk.ac.ebi.intact.model.Interaction;

import java.io.Serializable;

/**
 * Bean to store data for an Interaction (in an Experiment).
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class InteractionBean extends AbstractEditBean implements Serializable {

    // Instance Data

    /**
     * Reference to the object this instance is created with.
     */
    private Interaction myInteraction;

    /**
     * Instantiate an object of this class from an Interaction instance.
     * @param interaction the <code>Interaction</code> object.
     */
    public InteractionBean(Interaction interaction) {
        myInteraction = interaction;
    }

    // Read only properties.

    public Interaction getInteraction() {
        return myInteraction;
    }

    public String getAc() {
        return myInteraction.getAc();
    }

    public String getShortLabel() {
        return myInteraction.getShortLabel();
    }

    public String getFullName() {
        return myInteraction.getFullName();
    }

    /**
     * Returns the topic with a link to show its contents in a window.
     * @return the topic as a browsable link.
     */
    public String getShortLabelLink() {
        return getLink("Interaction", myInteraction.getShortLabel());
    }

    // Override Objects's equal method.

    /**
     * Compares <code>obj</code> with this object according to
     * Java's equals() contract. Only returns <tt>true</tt> if the short labels
     * for both objects match.
     * @param obj the object to compare.
     */
    public boolean equals(Object obj) {
        // Identical to this?
        if (obj == this) {
            return true;
        }
        if ((obj != null) && (getClass() == obj.getClass())) {
            // Can safely cast it.
            InteractionBean other = (InteractionBean) obj;
            return myInteraction.getShortLabel().equals(
                    other.myInteraction.getShortLabel());
        }
        return false;
    }
}
