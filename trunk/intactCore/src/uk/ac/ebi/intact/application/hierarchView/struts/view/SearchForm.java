/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.hierarchView.struts.view;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import uk.ac.ebi.intact.application.hierarchView.struts.framework.IntactBaseForm;
import uk.ac.ebi.intact.application.hierarchView.business.Constants;

/**
 * Form bean for the main form of the view.jsp page.  
 * This form has the following fields, with default values in square brackets:
 * <ul>
 * <li><b>AC</b> - Entered AC value
 * <li><b>method</b> - Selected method value
 * </ul>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */

public final class SearchForm extends IntactBaseForm {

    public static Logger logger = Logger.getLogger (Constants.LOGGER_NAME);

    // --------------------------------------------------- Instance Variables

    // the AC of the protein to search.
    private String AC = null;

    /**
     * The highlight method
     * That attribute can be null because in case only one method is available,
     * the form dont show the choice to make easier the user interface.
     */
    private String method = null;

    // ----------------------------------------------------------- Getters and Setters

    public String getAC() {
        return (this.AC);
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getMethod() {
        return (this.method);
    }

    public void setMethod (String method) {
        this.method = method;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        this.AC     = null;
        this.method = null;
    } // reset


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        if ((AC == null) || (AC.trim().length() == 0)) {
            addError ("error.AC.required");
        }

        if ((method == null) || (method.trim().length() == 0))
            addError ("error.method.required");

        return getErrors();
    }


    public String toString () {
        StringBuffer sb = new StringBuffer("SearchForm[AC=");
        sb.append(AC);
        if (method != null) {
            sb.append(", method=");
            sb.append(method);
        }
        sb.append("]");
        return (sb.toString());
    }
}
