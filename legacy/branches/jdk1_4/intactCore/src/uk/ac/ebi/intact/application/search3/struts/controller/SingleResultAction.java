/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.search3.struts.controller;

import uk.ac.ebi.intact.application.search3.struts.util.SearchConstants;
import uk.ac.ebi.intact.application.search3.struts.view.beans.BioSourceViewBean;
import uk.ac.ebi.intact.application.search3.struts.view.beans.CvObjectViewBean;
import uk.ac.ebi.intact.application.search3.struts.view.beans.InteractorViewBean;
import uk.ac.ebi.intact.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * This Action class performs the construction of view beans that will be used for every  single view for Interactions,
 * Proteins, and CvObjects. It will give back the code to forward to the coresponding JSP site for the representation of
 * the results.
 *
 * @author Michael Kleen
 * @version $Id$
 */
public class SingleResultAction extends AbstractResultAction {

    /**
     * Used to process a 'single item' view on Interaction, Proteins and CvObjects.
     *
     * @param request  The request object containing the data we want
     * @param helpLink The help link to use
     * @return String the return code for forwarding use by the execute method
     */
    protected String processResults(HttpServletRequest request, String helpLink) {
        logger.info("Single Result Action");
        //get the search results from the request
        Collection results = (Collection) request.getAttribute(SearchConstants.SEARCH_RESULTS);

        if (results.isEmpty()) {
            logger.info("SingleResultAction result is empty");
            return SearchConstants.FORWARD_FAILURE;   //something failed - no results
        }

        // check now which type is it, create the matched viewbean
        final String searchURL = super.getSearchURL();
        final Object result = results.iterator().next();

        if (Interactor.class.isAssignableFrom(result.getClass())) {
            logger.info("Creating a new InteractorViewBean");
            InteractorViewBean bean = new InteractorViewBean((Interactor) result, helpLink, searchURL,
                                                             request.getContextPath());
            logger.info("Forward to single Protein View");
            request.getSession().setAttribute(SearchConstants.VIEW_BEAN, bean);
            return SearchConstants.FORWARD_INTERACTOR;

        }
        else if (BioSource.class.isAssignableFrom(result.getClass())) {
            logger.info("Creating a new BioSourceViewBean");
            BioSourceViewBean bean = new BioSourceViewBean((BioSource) result, helpLink, searchURL,
                                                           request.getContextPath());
            logger.info("Forward to single BioSource View");
            request.getSession().setAttribute(SearchConstants.VIEW_BEAN, bean);
            return SearchConstants.FORWARD_BIOSOURCE;

        }
        else if (CvObject.class.isAssignableFrom(result.getClass())) {
            logger.info("Creating a new CvObjectViewBean");
            CvObjectViewBean bean = new CvObjectViewBean((CvObject) result, helpLink, searchURL,
                                                         request.getContextPath());
            logger.info("Forward to single CvObject View");
            request.getSession().setAttribute(SearchConstants.VIEW_BEAN, bean);
            return SearchConstants.FORWARD_CVOBJECT;

        }
        else {
            //something wrong here - wrong result type for this Action...
            logger.info("wrong result type ");
            logger.info("Forward to Error Page");
            return SearchConstants.FORWARD_FAILURE;
        }


    }
}