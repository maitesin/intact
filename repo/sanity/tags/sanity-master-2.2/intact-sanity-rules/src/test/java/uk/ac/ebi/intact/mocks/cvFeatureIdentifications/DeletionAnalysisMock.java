/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.mocks.cvFeatureIdentifications;

import uk.ac.ebi.intact.model.CvObjectXref;
import uk.ac.ebi.intact.model.CvFeatureIdentification;
import uk.ac.ebi.intact.mocks.CvObjectMock;
import uk.ac.ebi.intact.mocks.IntactObjectSetter;
import uk.ac.ebi.intact.mocks.XrefMock;
import uk.ac.ebi.intact.mocks.cvXrefQualifiers.IdentityMock;
import uk.ac.ebi.intact.mocks.cvDatabases.PsiMiMock;

/**
 * TODO comment this
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 * @since TODO
 */
public class DeletionAnalysisMock {
    public static CvFeatureIdentification getMock(){
        CvFeatureIdentification deletionAnalysis = CvObjectMock.getMock(CvFeatureIdentification.class,CvFeatureIdentification.DELETION_ANALYSIS, CvFeatureIdentification.DELETION_ANALYSIS);
        deletionAnalysis = (CvFeatureIdentification) IntactObjectSetter.setBasicObject(deletionAnalysis);
        CvObjectXref xref = XrefMock.getMock(CvObjectXref.class, PsiMiMock.getMock(), IdentityMock.getMock(),CvFeatureIdentification.DELETION_ANALYSIS_MI_REF);
        deletionAnalysis.addXref(xref);
        return deletionAnalysis;
    }
}