/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21-Jul-2006</pre>
 */
@Entity
@Table(name = "ia_feature_xref")
public class FeatureXref extends Xref
{

    private static final Log log = LogFactory.getLog(FeatureXref.class);


    public FeatureXref()
    {
    }

    public FeatureXref(Institution anOwner, CvDatabase aDatabase, String aPrimaryId, String aSecondaryId, String aDatabaseRelease, CvXrefQualifier aCvXrefQualifier)
    {
        super(anOwner, aDatabase, aPrimaryId, aSecondaryId, aDatabaseRelease, aCvXrefQualifier);
    }

    public FeatureXref(Institution anOwner, CvDatabase aDatabase, String aPrimaryId, CvXrefQualifier aCvXrefQualifier)
    {
        super(anOwner, aDatabase, aPrimaryId, aCvXrefQualifier);
    }

    @ManyToOne
    @JoinColumn(name = "parent_ac")
    public Feature getParent()
    {
        return (Feature) super.getParent();
    }

    @Column(name = "parent_ac", insertable = false, updatable = false)
    public String getParentAc() {
        return super.getParentAc();
    }
     
}
