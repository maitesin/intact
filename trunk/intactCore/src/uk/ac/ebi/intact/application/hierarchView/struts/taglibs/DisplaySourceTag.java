/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.hierarchView.struts.taglibs;

import uk.ac.ebi.intact.application.hierarchView.business.Constants;
import uk.ac.ebi.intact.application.hierarchView.business.IntactUserI;
import uk.ac.ebi.intact.application.hierarchView.highlightment.source.HighlightmentSource;
import uk.ac.ebi.intact.application.hierarchView.struts.view.utils.LabelValueBean;
import uk.ac.ebi.intact.business.IntactException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;



/**
 * That class allow to initialize properly the HTTPSession object
 * with what will be neededlater by the user of the web application.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */
public class DisplaySourceTag extends TagSupport {

    private static Logger logger = Logger.getLogger(Constants.LOGGER_NAME);

    /**
     * Skip the body content.
     */
    public int doStartTag() throws JspTagException {
        return SKIP_BODY;
    } // doStartTag


    /**
     * Called when the JSP encounters the end of a tag. This will create the
     * option list.
     */
    public int doEndTag() throws JspException {
        HttpSession session = pageContext.getSession();

        try {
            IntactUserI user = (IntactUserI) session.getAttribute (Constants.USER_KEY);
            String AC = user.getAC();
            String method_class = user.getMethodClass();

            if (null != AC) {
                // get the implementation class of the selected source
                HighlightmentSource source = HighlightmentSource.getHighlightmentSource (method_class);

                if (null == source) {
                    pageContext.getOut().write ("An error occured when trying to retreive source.<br>");
                } else {
                    logger.info ("Display highlight source items for AC = " + AC +
                                 " SourceClass = " + method_class);

                    List urls = null;

                    try {
                        urls = source.getSourceUrls(AC, session);
                    } catch (IntactException ie) {
                        String msg = "ERROR<br>The hierarchView system is not properly configured. Please warn your administrator.";
                        pageContext.getOut().write (msg);
                        return EVAL_PAGE;
                    }

                    /**
                     * We store the source collection in the session in order to display it
                     * with a dedicated tag (display:*).
                     */
                    session.setAttribute("sources", urls);

                    if (urls.size() == 1) {
                        // only one source element, let's display automatically the relevant page.
                        LabelValueBean url = (LabelValueBean) urls.get(0);
                        String absoluteUrl = url.getValue();
                        user.setSourceURL (absoluteUrl);
                    }
                } // else
            } // if

        } catch (Exception ioe) {
            ioe.printStackTrace();
            throw new JspException ("Fatal error: could not display protein associated source.");
        }
        return EVAL_PAGE;
    } // doEndTag

}