/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.action.experiment;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import uk.ac.ebi.intact.application.editor.struts.action.FillCvFormAction;
import uk.ac.ebi.intact.application.editor.struts.view.experiment.ExperimentViewBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Populates experiment form for display.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class FillExperimentFormAction extends FillCvFormAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        // Fill the form with common info.
        super.execute(mapping, form, request, response);

        // The view of the current object we are editing at the moment.
        ExperimentViewBean view =
                (ExperimentViewBean) getIntactUser(request).getView();
        // Poplulate with experiment data.
        DynaActionForm dynaForm = (DynaActionForm) form;

        // Assumption: We are creating a new record if the current view has no
        // short label. Reset the previous values.
        if (view.getShortLabel() == null) {
            dynaForm.set("organism", null);
            dynaForm.set("inter", null);
            dynaForm.set("ident", null);
        }
        else {
            // Preserve existing values of the form - only for editing.
//            if (isPropertyNullOrEmpty(dynaForm, "organism")) {
                dynaForm.set("organism", view.getOrganism());
//            }
//            if (isPropertyNullOrEmpty(dynaForm, "inter")) {
                dynaForm.set("inter", view.getSelectedInter());
//            }
//            if (isPropertyNullOrEmpty(dynaForm, "ident")) {
                dynaForm.set("ident", view.getSelectedIdent());
//            }
        }
        return mapping.findForward(FORWARD_SUCCESS);
    }
}
