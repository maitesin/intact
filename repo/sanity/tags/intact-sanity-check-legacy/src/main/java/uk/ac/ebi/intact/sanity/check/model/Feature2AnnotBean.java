/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.sanity.check.model;

/**
 * TODO comment it.
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id: Feature2AnnotBean.java,v 1.1 2005/07/28 16:13:29 catherineleroy Exp $
 */
public class Feature2AnnotBean extends IntactBean {

    private String feature_ac;

    private String annotation_ac;

    public Feature2AnnotBean() {
    }

    public String getFeature_ac() {
        return feature_ac;
    }

    public void setFeature_ac(String feature_ac) {
        this.feature_ac = feature_ac;
    }

    public String getAnnotation_ac() {
        return annotation_ac;
    }

    public void setAnnotation_ac(String annotation_ac) {
        this.annotation_ac = annotation_ac;
    }
}
