/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.mocks;

import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.model.BioSourceXref;
import uk.ac.ebi.intact.model.Annotation;
import uk.ac.ebi.intact.mocks.cvDatabases.NewtMock;
import uk.ac.ebi.intact.mocks.cvXrefQualifiers.IdentityMock;
import uk.ac.ebi.intact.mocks.cvTopics.UrlMock;

/**
 * TODO comment this
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 * @since TODO
 */
public class BioSourceMock {
    public static final String SHORTLABEL = "human-peer";
    public static final String FULLNAME = "Human Peer T lymphocytes";
    public static final String URL_ANNOT_DESCRIPTION = "http://www.dsmz.de/human_and_animal_cell_lines/info.php?dsmz_nr=6&from=cell_line_index&select=search_for_term&term=peer&preselect=human&firstload=0";
    public static final String DEF_ANNOT_DESCRIPTION = "DSMZ_MUTZ:ACC6 established from the peripheral blood of a 4-year-old girl with T cell acute lymphoblastic leukemia (T-ALL)";
    public static final String NEWT_ID = "9606";

    public static BioSource getMock(){
        BioSource bioSource = new BioSource(InstitutionMock.getMock(), SHORTLABEL, NEWT_ID);

        BioSourceXref xref = XrefMock.getMock(BioSourceXref.class, NewtMock.getMock(), IdentityMock.getMock(),NEWT_ID);
        bioSource.addXref(xref);

        Annotation annotation = AnnotationMock.getMock(UrlMock.getMock(),"http://www.dsmz.de/human_and_animal_cell_lines/info.php?dsmz_nr=6&from=cell_line_index&select=search_for_term&term=peer&preselect=human&firstload=0");
        bioSource.addAnnotation(annotation);

        return bioSource;
    }
}