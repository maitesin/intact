/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.action;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import uk.ac.ebi.intact.application.editor.struts.framework.AbstractEditorAction;
import uk.ac.ebi.intact.application.editor.struts.framework.util.AbstractEditViewBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets up the editor form type using the selected editor topic. The common forms
 * for all the topics (such as annotations, xrefs) are populated.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class FillCvFormAction  extends AbstractEditorAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        // The dyna form.
        DynaActionForm dynaform = (DynaActionForm) form;

        // The view of the current object we are editing at the moment.
        AbstractEditViewBean view = getIntactUser(request).getView();
        dynaform.set("ac", view.getAc());
        dynaform.set("shortLabel", view.getShortLabel());
        dynaform.set("fullName", view.getFullName());

        // Straight to the editor.
        return mapping.findForward("success");
    }

    /**
     * Creates a new DynaBean.
     * @param formName the name of the form configured in the struts
     * configuration file.
     * @param request the HTTP request to get the application configuration.
     * @return new <code>DynaBean</code> instance.
     * @throws InstantiationException errors in creating the bean
     * @throws IllegalAccessException errors in creating the bean
     */
//    private DynaBean createForm(String formName, HttpServletRequest request)
//            throws InstantiationException, IllegalAccessException {
//        // Fill the form to edit short label and full name.
//        ModuleConfig appConfig = (ModuleConfig) request.getAttribute(
//            Globals.MODULE_KEY);
//        FormBeanConfig config = appConfig.findFormBeanConfig(formName);
//        DynaActionFormClass dynaClass =
//                DynaActionFormClass.createDynaActionFormClass(config);
//        return dynaClass.newInstance();
//    }
}
