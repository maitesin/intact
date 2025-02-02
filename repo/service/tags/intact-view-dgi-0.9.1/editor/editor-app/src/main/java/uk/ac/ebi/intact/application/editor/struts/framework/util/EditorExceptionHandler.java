/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.framework.util;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.apache.struts.config.ExceptionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.application.editor.exception.BaseException;
import uk.ac.ebi.intact.application.editor.exception.validation.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Extends the struts default exception handler to Editor specific behaviour.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class EditorExceptionHandler extends ExceptionHandler {

    protected static final Log myLogger = LogFactory.getLog(EditorExceptionHandler.class);

    public static final String EXCEPTION_PARAM = EditorExceptionHandler.class.getName()+".EXCEPTION";

    public ActionForward execute(Exception ex,
                                 ExceptionConfig config,
                                 ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws ServletException {

        // The path for the forward either from the exception element or from
        // the input attribute.
        String path = (config.getPath() != null) ? config.getPath() : mapping.getInput();
        // The forward object.
        ActionForward forward = new ActionForward(path);

        // The error to store.
        ActionMessage error;
        // The prtoperty name for this error.
        String property = null;

        // Figure out what type of exception has been thrown.
        if (ex instanceof ValidationException) {
            // Logs the error.
            myLogger.info("a ValidationException occured", ex);
            ValidationException valex = (ValidationException) ex;
            property = valex.getFilterKey();
            error = new ActionMessage(valex.getMessageKey(), ex);

        } else if (ex instanceof BaseException) {

            myLogger.error("a BaseException occured", ex);
            // Editor specific exception.
            BaseException baseEx = (BaseException) ex;
            error = new ActionMessage(baseEx.getMessageKey(), baseEx);

        } else {

            error = new ActionMessage(config.getKey(), ex);
            property = error.getKey();
            // Unexpected error. Log it.
            myLogger.error("An unexpected exception occured", ex);
        }
        // Store the error in the proper action using the super method.
        storeException(request, property, error, forward, config.getScope());

        request.setAttribute(EXCEPTION_PARAM, ex);

        return forward;
    }
}
