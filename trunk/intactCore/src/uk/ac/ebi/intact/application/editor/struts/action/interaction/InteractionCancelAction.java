/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.action.interaction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import uk.ac.ebi.intact.application.editor.business.EditUserI;
import uk.ac.ebi.intact.application.editor.struts.action.CancelFormAction;
import uk.ac.ebi.intact.application.editor.struts.view.interaction.InteractionViewBean;
import uk.ac.ebi.intact.business.IntactHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action handles the event when the user cancels an Interaction.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 *
 * @struts.action
 *      path="/int/cancel"
 *      validate="false"
 *
 * @struts.action-forward
 *      name="experiment"
 *      path="/do/exp/fill/form"
 */
public class InteractionCancelAction extends CancelFormAction {

    /**
     * Action for cancelling changes to the current Interaction.
     *
     * @param mapping  the <code>ActionMapping</code> used to select this instance
     * @param form     the optional <code>ActionForm</code> bean for this request
     *                 (if any).
     * @param request  the HTTP request we are processing
     * @param response the HTTP response we are creating
     * @return failure mapping for any errors in cancelling the CV object; search
     *         mapping if the cancel is successful and the previous search has only one
     *         result; results mapping if the cancel is successful and the previous
     *         search has produced multiple results.
     * @throws Exception for any uncaught errors.
     */
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        // Do the common cancel stuff.
        ActionForward forward = super.execute(mapping, form, request, response);

        // Handler to the Intact User.
        EditUserI user = getIntactUser(request);

        // The current view of the edit session.
        InteractionViewBean view = (InteractionViewBean) user.getView();

        IntactHelper helper = user.getIntactHelper();
        try {
            // Delete any features that have been added.
            view.delFeaturesAdded(helper);

            // Check and see if we have to go to the experiment page.
            if (view.isSourceFromAnExperiment()) {
                // Set the experiment to go back.
                setDestinationExperiment(request, helper);
                // Back to the experiment editor.
                forward = mapping.findForward(EXP);
            }
        }
        finally {
            helper.closeStore();
        }
        return forward;
    }
}
