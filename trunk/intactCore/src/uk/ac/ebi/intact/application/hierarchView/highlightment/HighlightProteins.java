/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.hierarchView.highlightment;

import uk.ac.ebi.intact.application.hierarchView.business.graph.InteractionNetwork;
import uk.ac.ebi.intact.application.hierarchView.business.image.GraphToSVG;
import uk.ac.ebi.intact.application.hierarchView.business.image.ImageBean;
import uk.ac.ebi.intact.application.hierarchView.highlightment.behaviour.HighlightmentBehaviour;
import uk.ac.ebi.intact.application.hierarchView.highlightment.source.HighlightmentSource;
import uk.ac.ebi.intact.application.hierarchView.struts.StrutsConstants;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;


public class HighlightProteins {

    /** Constructor
     * Allow to modify the current graph to highlight a part of this.
     *
     * @param source The highlighting source
     * @param behaviourClass The highlighting behaviour class name
     * @param session The current session
     * @param in The interaction network
     */
    public HighlightProteins (String source,
                              String behaviourClass,
                              HttpSession session,
                              InteractionNetwork in)
            throws IOException {
        // Put the default color and default visibility in the
        // interaction network before to highlight this one
        in.initNodes();

        // Search the list of protein to highlight
        HighlightmentSource highlightmentSource = HighlightmentSource.getHighlightmentSource(source);

        // Search the protein to highlight
        Collection proteinsToHighlight = highlightmentSource.proteinToHightlight (session, in);

        // Interaction network 's modification
        HighlightmentBehaviour highlightmentBehaviour;
        highlightmentBehaviour = HighlightmentBehaviour.getHighlightmentBehaviour (behaviourClass);

        highlightmentBehaviour.apply (proteinsToHighlight, in);

        // Rebuild the image SVG data
        GraphToSVG svgProducer = new GraphToSVG (in);
        if (null == svgProducer) throw new IOException ("Unable to create the image data");
        svgProducer.draw();

        ImageBean ib = svgProducer.getImageBean();

        // store data in the session
        session.setAttribute (StrutsConstants.ATTRIBUTE_IMAGE_BEAN, ib);
        session.setAttribute (StrutsConstants.ATTRIBUTE_GRAPH, in);

    } // highlightProteins

} // HighlightProteins
