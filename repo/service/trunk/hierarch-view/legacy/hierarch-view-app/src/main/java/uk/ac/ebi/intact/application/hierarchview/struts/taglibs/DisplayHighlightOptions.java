/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.hierarchview.struts.taglibs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.application.hierarchview.business.Constants;
import uk.ac.ebi.intact.application.hierarchview.business.IntactUserI;
import uk.ac.ebi.intact.application.hierarchview.business.graph.HVNetworkBuilder;
import uk.ac.ebi.intact.application.hierarchview.highlightment.source.edge.EdgeHighlightmentSource;
import uk.ac.ebi.intact.application.hierarchview.highlightment.source.node.NodeHighlightmentSource;
import uk.ac.ebi.intact.context.IntactContext;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * That class allows to include HTML code to Displays current highlight source options.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */

public class DisplayHighlightOptions extends TagSupport {

    private static final Log logger = LogFactory.getLog( DisplayHighlightOptions.class );

    /**
     * Skip the body content.
     */
    public int doStartTag() throws JspTagException {
        return SKIP_BODY;
    } // doStartTag


    /**
     * Called when the JSP encounters the end of a tag.
     */
    public int doEndTag() throws JspException {
        HttpSession session = pageContext.getSession();

        try {
            IntactUserI user = ( IntactUserI ) IntactContext.getCurrentInstance().getSession().getAttribute( Constants.USER_KEY );
            String methodClass = user.getMethodClass();

            // Search the list of protein to highlight
            String htmlCode = "";
            if ( HVNetworkBuilder.NODE_SOURCES.contains( methodClass ) ) {

                NodeHighlightmentSource nodeHighlightmentSource = NodeHighlightmentSource.getHighlightmentSource( methodClass );
                if ( null != nodeHighlightmentSource ) {
                    htmlCode = nodeHighlightmentSource.getHtmlCodeOption( session );
                } else {
                    logger.error( "Unable to instanciate the current source: " + methodClass );
                }
            }
            if ( HVNetworkBuilder.EDGE_SOURCES.contains( methodClass ) ) {
                EdgeHighlightmentSource edgeHighlightmentSource = EdgeHighlightmentSource.getHighlightmentSource( methodClass );

                if ( null != edgeHighlightmentSource ) {
                    htmlCode = edgeHighlightmentSource.getHtmlCodeOption( session );
                } else {
                    logger.error( "Unable to instanciate the current source: " + methodClass );
                }
            }

            // write it
            pageContext.getOut().write( htmlCode );
        } catch ( Exception ioe ) {
            throw new JspException( "Error: could not display highlight options." );
        }

        return EVAL_PAGE; // the rest of the calling JSP is evaluated
    } // doEndTag
}