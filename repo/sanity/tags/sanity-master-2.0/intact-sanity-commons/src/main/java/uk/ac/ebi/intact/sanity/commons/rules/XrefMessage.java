/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.sanity.commons.rules;

import uk.ac.ebi.intact.model.IntactObject;
import uk.ac.ebi.intact.model.Xref;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.sanity.commons.report.Field;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO
 */
public class XrefMessage extends GeneralMessage {

    private Xref xref;

    public XrefMessage(MessageDefinition messageDefinition, IntactObject outLaw, Xref xref) {
        super(messageDefinition, outLaw);
        this.xref = xref;

        getInsaneObject().getFields().addAll(fieldsForXref(xref));
    }

    public Collection<Field> fieldsForXref(Xref xref) {
        Collection<Field> fields = new ArrayList<Field>();

        Field primaryIdField = new Field();
        primaryIdField.setName("Primary ID");
        primaryIdField.setValue(xref.getPrimaryId());

        fields.add(primaryIdField);

        Field dbField = new Field();
        dbField.setName("Xref Database Name");
        dbField.setValue(xref.getCvDatabase().getShortLabel());

        fields.add(dbField);


        Field qualField = new Field();
        qualField.setName("Xref Qualifier Name");

        if (xref.getCvXrefQualifier() != null) {
            qualField.setValue(xref.getCvXrefQualifier().getShortLabel());
        } else {
            qualField.setValue("");
        }

        fields.add(qualField);


        return fields;
    }

    public Xref getXref() {
        return xref;
    }

    public void setXref(Xref xref) {
        this.xref = xref;
    }
}