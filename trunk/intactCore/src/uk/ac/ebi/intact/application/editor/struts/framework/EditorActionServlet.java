/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.struts.action.ActionServlet;

import uk.ac.ebi.intact.application.editor.business.EditorService;
import uk.ac.ebi.intact.application.editor.exception.EmptyTopicsException;
import uk.ac.ebi.intact.application.editor.struts.framework.util.EditorConstants;
import uk.ac.ebi.intact.application.editor.util.LockManager;

/**
 * This is Intact editor specific action servlet class. This class is
 * responsible for initializing application wide resources.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class EditorActionServlet extends ActionServlet {

    public void init() throws ServletException {
        // Make sure to call super's init().
        super.init();

        // Save the context to avoid repeat calls.
        ServletContext ctx = super.getServletContext();

        // Create an instance of EditorService.
        EditorService service = null;
        try {
            // Load resources.
            service = EditorService.getInstance(ctx.getInitParameter("resources"));
        }
        catch (MissingResourceException mre) {
            // Unable to find the resource file.
            log(ExceptionUtils.getStackTrace(mre));
            throw new ServletException();
        }
        catch (EmptyTopicsException mite) {
            // An empty topic resource file.
            log(ExceptionUtils.getStackTrace(mite));
            throw new ServletException();
        }
        // Make them accessible for any servlets within the server.
        ctx.setAttribute(EditorConstants.EDITOR_SERVICE, service);
        ctx.setAttribute(EditorConstants.EDITOR_TOPICS, service.getIntactTypes());
        ctx.setAttribute(EditorConstants.LOCK_MGR, LockManager.getInstance());

        // Resource bundle to access the message resources to set keys.
        ResourceBundle msgres = ResourceBundle.getBundle(
                "uk.ac.ebi.intact.application.editor.MessageResources");

        // Set the maps for all the users. These are read only maps.
        ctx.setAttribute(EditorConstants.ACTION_MAP, getActionMap(msgres));
        ctx.setAttribute(EditorConstants.ANCHOR_MAP, getAnchorMap(msgres));
    }

    private Map getActionMap(ResourceBundle rb) {
        // The map to return.
        Map map = new HashMap();

        // Actions related to the buttons at the bottom of edit page. Common
        // to all the editors. First three are all directed to
        map.put(rb.getString("button.submit"), "submit");
        map.put(rb.getString("button.save.continue"), "submit");
        map.put(rb.getString("button.clone"), "submit");

        map.put(rb.getString("button.cancel"), "cancel");
        map.put(rb.getString("button.delete"), "delete");

        // Actions related to annotaions. Common to all the editors.
        map.put(rb.getString("annotations.button.add"), "submit");
        map.put(rb.getString("annotations.button.edit"), "annotation");
        map.put(rb.getString("annotations.button.save"), "annotation");
        map.put(rb.getString("annotations.button.delete"), "annotation");

        // Actions related to xrefs. Common to all the editors.
        map.put(rb.getString("xrefs.button.add"), "submit");
        map.put(rb.getString("xrefs.button.edit"), "xref");
        map.put(rb.getString("xrefs.button.save"), "xref");
        map.put(rb.getString("xrefs.button.delete"), "xref");

        // Actions related Interaction.
        map.put(rb.getString("int.exp.button.del"), "int.exp.del");
        map.put(rb.getString("int.exp.button.add"), "int.exp.hold");
        map.put(rb.getString("int.exp.button.hide"), "int.exp.hold");
        map.put(rb.getString("int.exp.button.recent"), "int.exp.search");
        map.put(rb.getString("int.exp.button.search"), "int.exp.search");

        // Actions related to edit/save/delete of proteins.
        map.put(rb.getString("int.proteins.button.edit"), "int.prot");
        map.put(rb.getString("int.proteins.button.save"), "int.prot");
        map.put(rb.getString("int.proteins.button.delete"), "int.prot");

        // Related to add/edit feature.
        map.put(rb.getString("int.proteins.button.feature.add"), "int.feature");
        map.put(rb.getString("int.proteins.button.feature.edit"), "int.feature");
        map.put(rb.getString("int.proteins.button.feature.save"), "int.feature");

        // Feature delete/link/unlink.
        map.put(rb.getString("int.proteins.button.feature.delete"), "int.feature.link");
        map.put(rb.getString("int.proteins.button.feature.link"), "int.feature.link");
        map.put(rb.getString("int.proteins.button.feature.unlink"), "int.feature.link");

        // Actions related protein search.
        map.put(rb.getString("int.proteins.button.search"), "int.prot.search");

        // Actions related to Experiment.
        map.put(rb.getString("exp.int.button.edit"), "interaction");
        map.put(rb.getString("exp.int.button.del"), "interaction");
        map.put(rb.getString("exp.int.button.add"), "exp.int.hold");
        map.put(rb.getString("exp.int.button.hide"), "exp.int.hold");
        map.put(rb.getString("exp.int.button.recent"), "exp.int.search");
        map.put(rb.getString("exp.int.button.search"),"exp.int.search");

        // Actions related to BioSource.
        map.put(rb.getString("biosource.button.taxid"), "taxid");

        // Actions related to Feature.
        map.put(rb.getString("feature.clone.button"), "feature.clone");
        map.put(rb.getString("feature.range.button.add"), "feature.range.new");
        map.put(rb.getString("feature.range.button.edit"), "feature.range");
        map.put(rb.getString("feature.range.button.save"), "feature.range");
        map.put(rb.getString("feature.range.button.delete"), "feature.range");

        return map;
    }

    private Map getAnchorMap(ResourceBundle rb) {
        // The map to return (map key -> anchor name).
        Map map = new HashMap();

        // Editing short label.
        map.put("error.label", "info");

        // Adding annotation.
        map.put("error.annotation.topic", "annotation");
        map.put("error.annotation.exists", "annotation");
        map.put(rb.getString("annotations.button.add"), "annotation");
        // Editing annotation.
        map.put("error.edit.annotation.exists", "annot.edit");
        map.put(rb.getString("annotations.button.edit"), "annot.edit");
        map.put(rb.getString("annotations.button.save"), "annot.edit");
        map.put(rb.getString("annotations.button.delete"), "annot.edit");

        // Adding Xrefs.
        map.put("error.xref.database", "xref");
        map.put("error.xref.pid", "xref");
        map.put("error.xref.exists", "xref");

        map.put(rb.getString("xrefs.button.add"), "xref");
        // Editing xref
        map.put("error.edit.xref.exists", "xref.edit");
        map.put(rb.getString("xrefs.button.edit"), "xref.edit");
        map.put(rb.getString("xrefs.button.save"), "xref.edit");
        map.put(rb.getString("xrefs.button.delete"), "xref.edit");

        // Experiment page anchors
        map.put("error.exp.biosrc", "info");
        map.put("error.exp.inter", "info");
        map.put("error.exp.ident", "info");
        // Anchors related search in an Interaction from an experiment.
        map.put("error.exp.int.search.input", "exp.int.search");
        map.put("error.exp.int.search.empty", "exp.int.search");
        map.put("error.exp.int.search.many", "exp.int.search");

        // Saving proteins.
        map.put(rb.getString("int.proteins.button.save"), "int.protein.search");

        // Protein search in the Interaction editor.
        map.put("error.int.protein.edit.role", "int.protein.search");
        map.put(rb.getString("int.proteins.button.search"), "int.protein.search");
        map.put("error.int.sanity.unsaved.prot", "int.protein.search");
        map.put("error.int.protein.search.input", "int.protein.search");
        map.put("error.int.protein.search.ac", "int.protein.search");
        map.put("error.int.protein.search.sp", "int.protein.search");
        map.put("error.int.protein.search.empty", "int.protein.search");
        map.put("error.int.protein.search.many", "int.protein.search");
        // Experiment search in the Interaction editor.
        map.put("error.int.sanity.exp", "int.exp.search");
        map.put("error.int.exp.search.input", "int.exp.search");
        map.put("error.int.exp.search.empty", "int.exp.search");
        map.put("error.int.exp.search.many", "int.exp.search");
        // General Interaction page anchors.
        map.put("int.interaction", "info");
        map.put("int.organism", "info");

        // Biosource anchors
        map.put("error.taxid.mask", "info");
		map.put("error.bs.sanity.taxid", "info");
		map.put("error.bs.sanity.taxid.dup", "info");

        // Linking Features - buttons
        map.put(rb.getString("int.proteins.button.feature.link"), "feature.link");
        map.put(rb.getString("int.proteins.button.feature.unlink"), "feature.link");
        // Linking Features - errors
        map.put("error.int.feature.link.error", "feature.link");
        map.put("error.int.feature.unlink.error", "feature.link");

        // Deleting a feature
        map.put(rb.getString("int.proteins.button.feature.delete"), "feature.link");

        return map;
    }
}
