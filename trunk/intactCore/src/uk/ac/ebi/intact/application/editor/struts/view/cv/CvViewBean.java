/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.view.cv;

import uk.ac.ebi.intact.application.editor.business.EditUser;
import uk.ac.ebi.intact.application.editor.struts.framework.util.AbstractEditViewBean;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.CvObject;
import uk.ac.ebi.intact.model.Institution;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.HashMap;

/**
 * The CV edit view bean. Currently, this class does not provide any additional
 * functionalities (simply extend the super abstract class to allow to create
 * an instance of this class).
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class CvViewBean extends AbstractEditViewBean {

    /**
     * The map of menus for this view.
     */
    private transient Map myMenus = new HashMap();

    // Implements abstract methods

    protected void updateAnnotatedObject(IntactHelper helper) throws IntactException {
        // The current CV object.
        CvObject cvobj = (CvObject) getAnnotatedObject();

        // Have we set the annotated object for the view?
        if (cvobj == null) {
            // Not persisted; create a new cv object.
            try {
                Constructor ctr = getEditClass().getDeclaredConstructor(
                        new Class[]{Institution.class, String.class});
                cvobj = (CvObject) ctr.newInstance(
                        new Object[]{EditUser.getInstitution(), getShortLabel()});
            }
            catch (NoSuchMethodException ne) {
                // Shouldn't happen.
                throw new IntactException(ne.getMessage());
            }
            catch (SecurityException se) {
                throw new IntactException(se.getMessage());
            }
            catch (InstantiationException ie) {
                throw new IntactException(ie.getMessage());
            }
            catch (IllegalAccessException le) {
                throw new IntactException(le.getMessage());
            }
            catch (InvocationTargetException te) {
                throw new IntactException(te.getMessage());
            }
            setAnnotatedObject(cvobj);
        }
    }

    protected void clearMenus() {
        myMenus.clear();
    }

    /**
     * Override to provide the menus for this view.
     * @return a map of menus for this view. It consists of common menus for
     * annotation/xref.
     * @throws IntactException for errors in accessing the persistent system.
     */
    public Map getMenus() throws IntactException {
        if (!myMenus.isEmpty()) {
            return myMenus;
        }
        // The Intact helper to construct menus.
        IntactHelper helper = new IntactHelper();

        try {
            return super.getMenus(helper);
        }
        finally {
            helper.closeStore();
        }
    }
}
