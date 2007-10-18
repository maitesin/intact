/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.sanity.rules.experiment;

import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.sanity.commons.SanityRuleException;
import uk.ac.ebi.intact.sanity.commons.annotation.SanityRule;
import uk.ac.ebi.intact.sanity.commons.rules.GeneralMessage;
import uk.ac.ebi.intact.sanity.commons.rules.MessageDefinition;
import uk.ac.ebi.intact.sanity.commons.rules.Rule;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 * @since TODO
 */

@SanityRule( target = Experiment.class )

public class ExperimentWithNoPubmedXref implements Rule<Experiment> {

    public Collection<GeneralMessage> check( Experiment experiment ) throws SanityRuleException {
        Collection<GeneralMessage> messages = new ArrayList<GeneralMessage>();
        if ( !hasPrimaryRefToPubmed( experiment ) ) {
            messages.add( new GeneralMessage( MessageDefinition.EXPERIMENT_WITHOUT_PUBMED, experiment ) );
        }
        return messages;
    }

    // TODO should we check that there is only one ?!
    private boolean hasPrimaryRefToPubmed( Experiment experiment ) {
        for ( ExperimentXref xref : experiment.getXrefs() ) {
            CvObjectXref cvDatabaseIdentity = CvObjectUtils.getPsiMiIdentityXref( xref.getCvDatabase() );
            if ( cvDatabaseIdentity != null && CvDatabase.PUBMED_MI_REF.equals( cvDatabaseIdentity.getPrimaryId() ) ) {
                CvObjectXref cvXrerQualifierIdentity = CvObjectUtils.getPsiMiIdentityXref( xref.getCvXrefQualifier() );
                if ( cvXrerQualifierIdentity != null && CvXrefQualifier.PRIMARY_REFERENCE_MI_REF.equals( cvXrerQualifierIdentity.getPrimaryId() ) ) {
                    return true;
                }
            }
        }
        return false;
    }
}