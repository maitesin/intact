/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.application.dataConversion.psiUpload.checker;

import uk.ac.ebi.intact.application.dataConversion.psiUpload.model.AnnotationTag;
import uk.ac.ebi.intact.application.dataConversion.psiUpload.util.report.Message;
import uk.ac.ebi.intact.application.dataConversion.psiUpload.util.report.MessageHolder;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.CvTopic;

import java.util.HashMap;
import java.util.Map;

/**
 * That class .
 * 
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */
public final class AnnotationChecker {

    // will avoid to have to search again later !
    private static final Map cache = new HashMap();

    public static CvTopic getCvTopic( String id ) {
        return (CvTopic) cache.get( id );
    }

    public static void check( final AnnotationTag annotation,
                              final IntactHelper helper ) {

        final String type = annotation.getType();

        if( "expressedIn".equalsIgnoreCase( type ) ) {
            /**
             * HACK: in order to get PSI to fit the IntAct model,
             * ----  we 'stuff' the expressedIn value (as a bioSource shortlabel)
             *       in a comment of the interaction as follow
             *
             *      eg.
             *          here the protein having the ID P12345 is expressed in the BioSource
             *          having the shortlabel: human-293t
             *
             *      ... code example ...
             *
             *      <attributeList>
             *         <attribute name="expressedIn">P12345:human-293t</attribute>
             *      </attributeList>
             */

            // check the protein ID


            // Check that the BioSource exists


        } else if( !cache.keySet().contains( type ) ) {
            CvTopic cvTopic = null;

            try {
                cvTopic = (CvTopic) helper.getObjectByLabel( CvTopic.class, type );

                if( cvTopic == null ) {
                    MessageHolder.getInstance().addCheckerMessage( new Message( "Could not find CvTopic having the " +
                                                                                "shortlabel: " + type ) );
                } else {
                    System.out.println( "Found CvTopic " + type + " as " + cvTopic.getShortLabel() );
                }
            } catch ( IntactException e ) {
                MessageHolder.getInstance().addCheckerMessage( new Message( "An error occured while searching for " +
                                                                            "CvTopic having shortlabel: " + type ) );
                e.printStackTrace();
            }

            cache.put( type, cvTopic );
        }
    }
}
