/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.search.struts.controller;

import java.util.*;

import org.apache.struts.action.*;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.*;

import uk.ac.ebi.intact.application.search.struts.framework.util.*;
import uk.ac.ebi.intact.application.search.struts.framework.*;
import uk.ac.ebi.intact.application.search.business.*;
import uk.ac.ebi.intact.application.search.struts.view.*;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.persistence.*;
import uk.ac.ebi.intact.business.*;

/**
 * This class provides the actions required for the search page
 * for intact. The search criteria are obtained from a Form object
 * and then the search is carried out, via the IntactUser functionality,
 * which provides the business logic.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk), modified by Chris Lewington
 * @version $Id$
 */

public class SearchAction extends IntactBaseAction {

   /**
    * Process the specified HTTP request, and create the corresponding
    * HTTP response (or forward to another web component that will create
    * it). Return an ActionForward instance describing where and how
    * control should be forwarded, or null if the response has
    * already been completed.
    *
    * @param mapping - The <code>ActionMapping</code> used to select this instance
    * @param form - The optional <code>ActionForm</code> bean for this request (if any)
    * @param request - The HTTP request we are processing
    * @param response - The HTTP response we are creating
    *
    * @return - represents a destination to which the controller servlet,
    * <code>ActionServlet</code>, might be directed to perform a RequestDispatcher.forward()
    * or HttpServletResponse.sendRedirect() to, as a result of processing
    * activities of an <code>Action</code> class
    */
    public ActionForward perform (ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        // Clear any previous errors.
        super.clearErrors();

        // Set up variables used during searching
       //default the param to AC
        String searchParam = "ac";
        String searchValue = null;

        SearchForm theForm = (SearchForm) form;

        // Session to access various session objects.
        HttpSession session = super.getSession(request);

        // Handler to the Intact User.
        IntactUserIF user = super.getIntactUser(session);

        // The type of search object selected by the user.
       //NB need to change the "user" I/F so it is not CV specific...
        String searchClass = theForm.getClassName();

        // The class name associated with the topic.
        String classname = super.getIntactService().getClassName(searchClass);

       //now need to try searches based on AC, label or name (only
       //ones we will accept for now) and return as soon as we get a result
       //NB obviously can't distinguish between a zero return and a search
       //with garbage input using this approach...

       //convert to uppercase to be on the safe side...
        searchValue = theForm.getSearchString().toUpperCase();

        super.log("search action: search param is " + searchParam);
        super.log("search action: attempting to search by AC first...");

        // Holds the result from the search.
        Collection results = null;

        try {
            results = user.search(classname, searchParam, searchValue);
            if (results.isEmpty()) {
                // No matches found - try a search by label now...
                Object result = null;
                try {
                    result = user.getObjectByLabel(Class.forName(classname), searchValue);
                }
                catch(ClassNotFoundException ce) {
                    super.log("error - can't find class " + classname + ce.toString());
                }
                if(result == null) {

                    //no match on a label - try by xref (primary id)...
                    try {
                        super.log("looking for: " + classname + " with primary xref ID " + searchValue);
                    result = user.getObjectByXref(Class.forName(classname), searchValue);
                    }
                    catch(ClassNotFoundException ce) {
                        super.log("error - can't find class " + classname + ce.toString());
                    }
                    if(result == null) {

                        //no match by xref - try finally by name....
                        results = user.search(classname, "fullName", searchValue);
                        if(results.isEmpty()) {

                            //finished all current options - return a failure
                            super.log("No matches were found for the specified search criteria");
                            // Save the search parameters for results page to display.
                            session.setAttribute(WebIntactConstants.SEARCH_CRITERIA,
                            searchParam + "=" + searchValue);
                            session.setAttribute(WebIntactConstants.SEARCH_TYPE, searchClass);
                            return mapping.findForward(WebIntactConstants.FORWARD_NO_MATCHES);
                        }
                        else {

                            //got a match on name - flag it for info later
                            searchParam = "name";
                        }
                    }
                    else {

                        //got a match on xref - save for later info
                        searchParam = "primary ID";
                    }
                }
                else {

                    //matched on a label - flag it for later info
                    searchParam = "label";
                }

                //whatever way we got an object, add it to the collection
                if(result != null) {
                    results.add(result);
                }
            }
            super.log("search action: search results retrieved OK");

            // If we retrieved one object then we can go straight to edit page.
            if (results.size() == 1) {
                // Found a single match only; save it to determine which page to
                // return from edit.jsp; for example, results jsp or search jsp.
                session.setAttribute(WebIntactConstants.SINGLE_MATCH, Boolean.TRUE);

                // The object to display.
                Object obj = results.iterator().next();

                IntactViewBean viewbean = super.getViewBean(session);
                viewbean.initialise(obj);
                session.setAttribute(WebIntactConstants.VIEW_BEAN, viewbean);
                // Save the search parameters for results page to display.
                session.setAttribute(WebIntactConstants.SEARCH_CRITERIA,
                searchParam + "=" + searchValue);
                return mapping.findForward(WebIntactConstants.FORWARD_RESULTS);
            }
            else {
                // Found multiple results.
                session.setAttribute(WebIntactConstants.SINGLE_MATCH, Boolean.FALSE);

                // The collection to hold our List objects for display tag API.
                Collection container = new ArrayList();
                for (Iterator iter = results.iterator(); iter.hasNext();) {
                    container.add(new ListObject((AnnotatedObject)iter.next()));
                }
                // Save it in a session for a JSP to display.
                session.setAttribute(WebIntactConstants.FORWARD_MATCHES, container);

                // Move to the results page.
                // Save the search parameters for results page to display.
                session.setAttribute(WebIntactConstants.SEARCH_CRITERIA,
                searchParam + "=" + searchValue);
                return mapping.findForward(WebIntactConstants.FORWARD_RESULTS);
            }
        }

        catch (IntactException se) {
            // Something failed during search...
            super.log(ExceptionUtils.getStackTrace(se));
            // The errors to report back.
            super.addError("error.search", se.getNestedMessage());
            super.saveErrors(request);
            return mapping.findForward(WebIntactConstants.FORWARD_FAILURE);
        }
    }

}
