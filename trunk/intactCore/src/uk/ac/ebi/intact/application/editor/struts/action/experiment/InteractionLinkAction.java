/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.action.experiment;

import org.apache.struts.action.*;
import uk.ac.ebi.intact.application.editor.business.EditUserI;
import uk.ac.ebi.intact.application.editor.struts.action.SubmitDispatchAction;
import uk.ac.ebi.intact.application.editor.struts.view.experiment.InteractionBean;
import uk.ac.ebi.intact.application.editor.struts.view.interaction.InteractionViewBean;
import uk.ac.ebi.intact.application.editor.util.LockManager;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.Interaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Links to an interaction button from an experiment.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class InteractionLinkAction extends SubmitDispatchAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        // The dyna form.
        DynaActionForm dynaform = (DynaActionForm) form;

        // The index position of the annotation.
        int idx = ((Integer) dynaform.get("idx")).intValue();

        // The protein we are editing at the moment.
        InteractionBean ib = ((InteractionBean[]) dynaform.get("ints"))[idx];

        // We must have this Interaction bean.
        assert ib != null;

        // Save the experiment first.
        ActionForward forward = save(mapping, form, request, response);

//        System.out.println("Forward path: " + forward.getPath() + " name: " + forward.getName());
        // Check for any errors to display in the input page.
        if (forward.getPath().equals(mapping.getInputForward().getPath())) {
            return forward;
        }
        // No errors. Linking starts from here.

        // Check the lock.
        LockManager lmr = LockManager.getInstance();

        // Handler to the Intact User.
        EditUserI user = getIntactUser(request);

        // Cache the this object's intAc because we need to set it later.
        String expAc = user.getView().getAc();

        // The intAc to search
        String intAc = ib.getAc();

        // Try to acuire the lock.
        if (!lmr.acquire(intAc, user.getUserName())) {
            ActionErrors errors = new ActionErrors();
            // The owner of the lock (not the current user).
            errors.add(ActionErrors.GLOBAL_ERROR,
                    new ActionError("error.lock", intAc, lmr.getOwner(intAc)));
            saveErrors(request, errors);
            return mapping.findForward(FAILURE);
        }
        // The selected Annotated object.
        AnnotatedObject annobj = (AnnotatedObject) user.getObjectByAc(
                Interaction.class, intAc);
        // The object we are editing presently.
        user.setView(annobj);

        // Set the experiment AC, so we can come back to this experiment again.
        ((InteractionViewBean) user.getView()).setSourceExperimentAc(expAc);

        // Update the form.
        return mapping.findForward(SUCCESS);
    }
}