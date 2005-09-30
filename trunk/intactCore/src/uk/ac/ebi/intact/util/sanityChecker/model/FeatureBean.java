/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.util.sanityChecker.model;

/**
 * TODO comment it.
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 */
public class FeatureBean  extends IntactBean {

    private String component_ac;

    public String getComponent_ac() {
        return component_ac;
    }

    public void setComponent_ac(String component_ac) {
        this.component_ac = component_ac;
    }
}
