/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.framework.util;

import java.util.Map;
import java.util.HashMap;

import uk.ac.ebi.intact.application.editor.struts.viewx.*;
import uk.ac.ebi.intact.application.editor.struts.view.interaction.InteractionViewBean;
import uk.ac.ebi.intact.application.editor.struts.view.experiment.ExperimentROViewBean;
import uk.ac.ebi.intact.application.editor.struts.view.cv.CvROViewBean;
import uk.ac.ebi.intact.model.*;

/**
 * The factory class to create non edit (read only) view beans.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class ROViewBeanFactory {

    /**
     * Maps: name -> views. Cache for view beans. It is safe to reuse the view
     *  beans as only one can be used at any time. We cache them to reuse
     *  rather than creating a new instance for each selection.
     */
    private Map nameToView = new HashMap();

    /**
     * CV editor is stored under this key.
     */
    private static final String CV_EDITOR = "0";

    /**
     * BioSource editor is stored under this key.
     */
    private static final String BIOSRC_EDITOR = "1";

    /**
     * Experiment editor is stored under this key.
     */
    private static final String EXPERIMENT_EDITOR = "2";

    /**
     * Interaction editor is stored under this key.
     */
    private static final String INTERACTION_EDITOR = "3";

    /**
     * Factory method to return an instance of an non edit (read only) view bean.
     * @param annot the Annotated object to initialize the edit view bean.
     * @return an instance of <code>AbstractROViewBean</code>; the type to return
     * is determined by <code>annot</code>. This method does not create new
     * instances of beans; instead it recyles them.
     */
    public AbstractROViewBean factory(AnnotatedObject annot) {
        // The view bean to return.
        AbstractROViewBean viewbean;
        Class clazz = annot.getClass();
//        if (clazz.isAssignableFrom(BioSource.class)) {
//            viewbean = getBioSourceEditor((BioSource) annot);
//        }
        if (clazz.isAssignableFrom(Experiment.class)) {
            viewbean = getExperimentEditor((Experiment) annot);
        }
//        else if (clazz.isAssignableFrom(Interaction.class)) {
//            viewbean = getInteractionEditor((Interaction) annot);
//        }
        else {
            // Assume it is an CV object.
            viewbean = getCvEditor((CvObject) annot);
        }
        return viewbean;
    }

    // Helper methods.

//    private AbstractEditViewBean getBioSourceEditor(BioSource biosrc) {
//        String name = BIOSRC_EDITOR;
//        if (!nameToView.containsKey(name)) {
//            nameToView.put(name, new BioSourceViewBean());
//        }
//        BioSourceViewBean view = (BioSourceViewBean) nameToView.get(name);
//        view.setAnnotatedObject(biosrc);
//        return view;
//    }

    private AbstractROViewBean getExperimentEditor(Experiment exp) {
        String name = EXPERIMENT_EDITOR;
        if (!nameToView.containsKey(name)) {
            nameToView.put(name, new ExperimentROViewBean());
        }
        ExperimentROViewBean view = (ExperimentROViewBean) nameToView.get(name);
        view.setAnnotatedObject(exp);
        return view;
    }

//    private AbstractEditViewBean getInteractionEditor(Interaction intact) {
//        String name = INTERACTION_EDITOR;
//        if (!nameToView.containsKey(name)) {
//            nameToView.put(name, new InteractionViewBean());
//        }
//        InteractionViewBean view = (InteractionViewBean) nameToView.get(name);
//        view.setAnnotatedObject(intact);
//        return view;
//    }

    private AbstractROViewBean getCvEditor(CvObject cvobj) {
        String name = CV_EDITOR;
        if (!nameToView.containsKey(name)) {
            nameToView.put(name, new CvROViewBean());
        }
        CvROViewBean view = (CvROViewBean) nameToView.get(name);
        view.setAnnotatedObject(cvobj);
        return view;
    }
}
