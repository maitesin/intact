/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.hierarchView.highlightment;

import uk.ac.ebi.intact.application.hierarchView.business.graph.InteractionNetwork;
import uk.ac.ebi.intact.application.hierarchView.business.image.GraphToSVG;
import uk.ac.ebi.intact.application.hierarchView.business.image.ImageBean;
import uk.ac.ebi.intact.application.hierarchView.business.Constants;
import uk.ac.ebi.intact.application.hierarchView.business.IntactUserI;
import uk.ac.ebi.intact.application.hierarchView.highlightment.behaviour.HighlightmentBehaviour;
import uk.ac.ebi.intact.application.hierarchView.highlightment.source.HighlightmentSource;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;


/**
 * Allows to perfoem the highlightment according to data stored in the user session
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */

public class HighlightProteins {

    /** Constructor
     * Allow to modify the current graph to highlight a part of this.
     *
     * @param source The highlighting source
     * @param behaviourClass The highlighting behaviour class name
     * @param session The current session
     * @param in The interaction network
     */
    public static void perform (String source,
                                String behaviourClass,
                                HttpSession session,
                                InteractionNetwork in) {
        /*
         * Put the default color and default visibility in the
         * interaction network before to highlight this one.
         */
        in.initNodes();

        // Search the highlight source implementation
        HighlightmentSource highlightmentSource = HighlightmentSource.getHighlightmentSource(source);

        // Search the protein to highlight
        Collection proteinsToHighlight = highlightmentSource.proteinToHightlight (session, in);

        // Interaction network 's modification
        HighlightmentBehaviour highlightmentBehaviour;
        highlightmentBehaviour = HighlightmentBehaviour.getHighlightmentBehaviour (behaviourClass);

        // apply the highlight to the selected set of protein
        highlightmentBehaviour.apply (proteinsToHighlight, in);

        // Rebuild SVG data
        GraphToSVG svgProducer = new GraphToSVG (in);
        svgProducer.draw();
        ImageBean ib = svgProducer.getImageBean();

        // store data in the session
        IntactUserI user = (IntactUserI) session.getAttribute(Constants.USER_KEY);
        // TODO : test is user OK
        user.setImageBean(ib);

        // TODO: needed ?! have to be tested !
        user.setInteractionNetwork(in);
    }
}
