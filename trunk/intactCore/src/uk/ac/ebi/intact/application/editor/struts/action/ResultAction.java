/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.action;

import org.apache.struts.action.*;
import uk.ac.ebi.intact.application.editor.business.EditUserI;
import uk.ac.ebi.intact.application.editor.struts.framework.AbstractEditorAction;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.Experiment;
import uk.ac.ebi.intact.model.IntactObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The action when the the user selects an entry from a list to edit an object.
 * Short label is passed as a request paramter.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 *
 * @struts.action
 *      path="/secure/edit"
 *      name="dummyForm"
 *      validate="false"
 *
 * @struts.action-forward
 *      name="success"
 *      path="/do/choose"
 */
public class ResultAction extends AbstractEditorAction {

    /**
     * Process the specified HTTP request, and create the corresponding
     * HTTP response (or forward to another web component that will create
     * it). Return an ActionForward instance describing where and how
     * control should be forwarded, or null if the response has
     * already been completed.
     *
     * @param mapping  - The <code>ActionMapping</code> used to select this instance
     * @param form     - The optional <code>ActionForm</code> bean for this request (if any)
     * @param request  - The HTTP request we are processing
     * @param response - The HTTP response we are creating
     * @return - represents a destination to which the action servlet,
     *         <code>ActionServlet</code>, might be directed to perform a RequestDispatcher.forward()
     *         or HttpServletResponse.sendRedirect() to, as a result of processing
     *         activities of an <code>Action</code> class
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        // The ac to search
        String ac = request.getParameter("ac");
        // The type to edit.
        String type = request.getParameter("type");

        // At this point we should have valid ac and type. Validate them. It is
        // possible for these parameters to contain invalid characters (as a result
        // of allowing to access pages directly).
        if ((ac == null) || (type == null) || !getService().isValidTopic(type)) {
            LOGGER.error("Invalid values submitted for ac=" + ac + " and type=" + type);
            ActionErrors errors = new ActionErrors();
            // The owner of the lock (not the current user).
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.invalid.edit.inputs"));
            saveErrors(request, errors);
            return mapping.findForward(FAILURE);
        }

        LOGGER.info("AC: " + ac + " class: " + type);

        // Handler to the Intact User.
        EditUserI user = getIntactUser(request);

        // Try to acquire the lock.
        ActionErrors errors = acquire(ac, user.getUserName());
        if (errors != null) {
            saveErrors(request, errors);
            return mapping.findForward(FAILURE);
        }
        IntactHelper helper = new IntactHelper();
        // The selected Annotated object.
        AnnotatedObject annobj;
        // The class for the search.
        Class clazz = getModelClass(type);
        try {
            // We need to do this because interactions can be added or removed from
            // an experiment via the interaction editor. Those interactions that
            // have been added or removed this way will not be visible until it
            // is removed and reloaded again.
            if ((clazz == Experiment.class) && helper.isInCache(clazz, ac)) {
                helper.removeFromCache(clazz, ac);
            }
            annobj = (AnnotatedObject) helper.getObjectByAc(clazz, ac);
        }
        finally {
            helper.closeStore();
        }
        // Set the object and the type we are about to edit.
        user.setSelectedTopic(type);
        user.setView(annobj);

        LOGGER.info("Number of annotations: " + annobj.getAnnotations().size());
        LOGGER.info("Number of xrefs: " + annobj.getXrefs().size());

        return mapping.findForward(SUCCESS);
    }

    private Class getModelClass(String type) throws ClassNotFoundException {
        // The intact model package name.
        String packageName = IntactObject.class.getPackage().getName();
        return Class.forName(packageName + "." + type);
    }
}
