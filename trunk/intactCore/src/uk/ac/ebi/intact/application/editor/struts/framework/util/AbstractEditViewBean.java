/*
Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.struts.framework.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.tiles.ComponentContext;
import uk.ac.ebi.intact.application.editor.business.EditUserI;
import uk.ac.ebi.intact.application.editor.exception.SearchException;
import uk.ac.ebi.intact.application.editor.exception.validation.ValidationException;
import uk.ac.ebi.intact.application.editor.struts.framework.EditorActionForm;
import uk.ac.ebi.intact.application.editor.struts.view.CommentBean;
import uk.ac.ebi.intact.application.editor.struts.view.XreferenceBean;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.Annotation;
import uk.ac.ebi.intact.model.Xref;

import java.io.Serializable;
import java.util.*;

/**
 * This super bean encapsulates behaviour for a common editing session. This
 * class must be extended to provide editor specific behaviour.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public abstract class AbstractEditViewBean implements Serializable {

    /**
     * The Annotated object to wrap this bean around.
     */
    private AnnotatedObject myAnnotObject;

    /**
     * The class we are editing.
     */
    private Class myEditClass;

    /**
     * The short label of the current edit object.
     */
    private String myShortLabel;

    /**
     * The full name of the current edit object.
     */
    private String myFullName;

    /**
     * The current anchor.
     */
    private String myAnchor;

    /**
     * The annotations to display.
     */
    private List myAnnotations = new ArrayList();

    /**
     * The Xreferences to display.
     */
    private List myXrefs = new ArrayList();

    /**
     * List of annotations to add. This collection is cleared once the user
     * commits the transaction.
     */
    private List myAnnotsToAdd = new ArrayList();

    /**
     * List of annotations to del. This collection is cleared once the user
     * commits the transaction.
     */
    private List myAnnotsToDel = new ArrayList();

    /**
     * Set of annotations to update. This collection is cleared once the user
     * commits the transaction.
     */
    private Set myAnnotsToUpdate = new HashSet();

    /**
     * List of xrefs to add. This collection is cleared once the user commits
     * the transaction.
     */
    private List myXrefsToAdd = new ArrayList();

    /**
     * List of xrefs to del. This collection is cleared once the user commits
     * the transaction.
     */
    private List myXrefsToDel = new ArrayList();

    /**
     * Set of xrefs to update. This collection is cleared once the user
     * commits the transaction.
     */
    private Set myXrefsToUpdate = new HashSet();

    /**
     * The factory to create various menus; this is transient as it is set from
     * the view bean factory when it creates a new instance of a view bean.
     */
    private transient EditorMenuFactory myMenuFactory;

    // Override Objects's equal method.

    /**
     * Compares <code>obj</code> with this object according to
     * Java's equals() contract. Only used for testing a serialized
     * objects.
     * @param obj the object to compare.
     * @return true only if <code>obj</code> is an instance of this class
     * and all non transient fields are equal to given object's non tranient
     * fields. For all other instances, false is returned.
     */
    public boolean equals(Object obj) {
        // Identical to this?
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractEditViewBean)) {
            return false;
        }
        // Can safely cast it.
        AbstractEditViewBean other = (AbstractEditViewBean) obj;

        // Annotated object must match if it exists
        if (!equals(myAnnotObject, other.myAnnotObject)) {
            return false;
        }
        // Edit clas smust match; no need to check for null as edit class must
        // exist at all times.
        if (!myEditClass.equals(other.myEditClass)) {
            return false;
        }
        // Short labels must match.
        if (!equals(myShortLabel, other.myShortLabel)) {
            return false;
        }
        // Fullname must match.
        if (!equals(myFullName, other.myFullName)) {
            return false;
        }
        // Annotations must equal.
        if (!myAnnotations.containsAll(other.myAnnotations)) {
            return false;
        }
        if (!myAnnotsToAdd.containsAll(other.myAnnotsToAdd)) {
            return false;
        }
        if (!myAnnotsToDel.containsAll(other.myAnnotsToDel)) {
            return false;
        }
        if (!myAnnotsToUpdate.containsAll(other.myAnnotsToUpdate)) {
            return false;
        }

        // Xrefs must equal.
        if (!myXrefs.containsAll(other.myXrefs)) {
            return false;
        }
        if (!myXrefsToAdd.containsAll(other.myXrefsToAdd)) {
            return false;
        }
        if (!myXrefsToDel.containsAll(other.myXrefsToDel)) {
            return false;
        }
        if (!myXrefsToUpdate.containsAll(other.myXrefsToUpdate)) {
            return false;
        }
        return true;
    }

    /**
     * Resets the bean with given object.
     * @param obj either an Annotated object or a Class. The class type is used
     * when creating a view bean for a new object. For an existing object,
     * AnnotatedObject is used.
     */
    public void reset(Object obj) {
        // Clear any left overs from previous transaction.
        clearTransactions();

        // Check for annotated object.
        if (AnnotatedObject.class.isAssignableFrom(obj.getClass())) {
            reset((AnnotatedObject) obj);
        }
        else {
            reset((Class) obj);
        }
    }

    /**
     * Resets the bean with the current edit class. This method is called when
     * creating a new annotated object (only the class or the type is known at
     * that time).
     * @param clazz the Class of the new annotated object.
     */
    protected void reset(Class clazz) {
        myEditClass = clazz;

        // Clear annotations and xrefs.
        myAnnotations.clear();
        myXrefs.clear();

        // Set them to null as they may have previous values.
        setAnnotatedObject(null);
        setShortLabel(null);
        setFullName(null);
    }

    /**
     * Resets with the bean using an existing Annotated object.
     * @param annobj <code>AnnotatedObject</code> object to set this bean.
     */
    protected void reset(AnnotatedObject annobj) {
        setShortLabel(annobj.getShortLabel());
        resetAnnotatedObject(annobj);
        // Cache the annotations and xrefs here to save it from loading
        // multiple times with each invocation to getAnnotations()
        // or getXrefs() methods.
        makeCommentBeans(annobj.getAnnotations());
        makeXrefBeans(annobj.getXrefs());
    }

    /**
     * Resets the view with cloned object.
     * @param copy the cloned object to set as the new view. This method is
     * similar to {@link #reset(AnnotatedObject)} except for annotations and
     * xrefs are added as new objects (so, upon persisting this object, new
     * annotations and xrefs will be created).
     * @param user the handler to the user to set the available short label.
     */
    public void resetClonedObject(AnnotatedObject copy, EditUserI user) {
        // Clear previous transactions.
        clearTransactions();

        // Clear existing annotations & xrefs (don't want to share them).
        myAnnotations.clear();
        myXrefs.clear();

        // Set it with most likely next short label from the database.
        String newSL = user.getNextAvailableShortLabel(copy.getClass(),
                    copy.getShortLabel());
        setShortLabel(newSL);

        // Reset the cloned object with values given by parameter.
        resetAnnotatedObject(copy);

        // Add the annotations in the cloned as new annotations to add.
        for (Iterator iter = copy.getAnnotations().iterator(); iter.hasNext();) {
            addAnnotation(new CommentBean((Annotation) iter.next()));
        }
        // This is not persisted yet and there aren't any previous annots or
        // xrefs. Annotations need to be cleared or else deleting an annotation
        // after cloning causes persistence problems. see bug: 1011416
        copy.getAnnotations().clear();

        // Add the xrefs in the cloned as new xrefs to add.
        for (Iterator iter = copy.getXrefs().iterator(); iter.hasNext();) {
            addXref(new XreferenceBean((Xref) iter.next()));
        }
        copy.getXrefs().clear();
    }

    /**
     * Sets the menu factory to create menus.
     * @param factory the factory to create menus.
     */
    public final void setMenuFactory(EditorMenuFactory factory) {
        myMenuFactory = factory;
    }

    /**
     * Returns the Annotated object. Could be null if the object is not persisted.
     * @return <code>AnnotatedObject</code> this instace is wrapped around.
     */
    public final AnnotatedObject getAnnotatedObject() {
        return myAnnotObject;
    }

    /**
     * Returns accession number.
     * @return the accession number as a <code>String</code> instance; null if
     * the current view is not persisted.
     */
    public final String getAc() {
        if (myAnnotObject != null) {
            return myAnnotObject.getAc();
        }
        return null;
    }

    /**
     * Returns a link to the search application.
     * @return ac as a link to the seatch application or an empty string if this
     * object is not yet persisted (i.r., ac is not yet set).
     */
    public final String getAcLink() {
        if (getAc() == null) {
            return "";
        }
        String className = getEditClass().getName();
        int lastPos = className.lastIndexOf('.');
        String type = className.substring(lastPos + 1);
        return "<a href=\"" + "javascript:show('" + type + "', " + "'"
                + getShortLabel() + "')\"" + ">" + getAc() + "</a>";
    }

    /**
     * Returns the edit class.
     * @return the class name of the current edit object.
     */
    public final Class getEditClass() {
        return myEditClass;
    }

    /**
     * Sets the short label.
     * @param shortLabel the short label to set.
     */
    public final void setShortLabel(String shortLabel) {
        myShortLabel = shortLabel;
    }

    /**
     * Returns the short label.
     * @return the short label as a <code>String</code> instance.
     */
    public final String getShortLabel() {
        return myShortLabel;
    }

    /**
     * Sets the full name.
     * @param fullName the full name to set for the current edit object.
     * An empty name (set by tag library when submitting the form) is set
     * to null to avoid equals method returning false for identical objects
     * apart from the full name.
     */
    public final void setFullName(String fullName) {
        // It is improtant for this check or else the full name will be set to
        // an empty string thus causing equals method to return false.
        if (fullName != null && fullName.equals("")) {
            fullName = null;
        }
        myFullName = fullName;
    }

    /**
     * Return the full name.
     * @return the full name as a <code>String</code> instance.
     */
    public String getFullName() {
        return myFullName;
    }


    public String getAnchor() {
        return myAnchor;
    }

    public void setAnchor(String anchor) {
        myAnchor = anchor;
    }

    public void resetAnchor() {
        setAnchor("");
    }

    /**
     * Returns a collection of <code>CommentBean</code> objects.
     *
     * <pre>
     * post: return != null
     * post: return->forall(obj : Object | obj.oclIsTypeOf(CommentBean))
     * </pre>
     */
    public List getAnnotations() {
        return myAnnotations;
    }

    /**
     * Adds an annotation.
     * @param cb the bean to add.
     *
     * <pre>
     * post: myAnnotsToAdd = myAnnotsToAdd@pre + 1
     * post: myAnnotations = myAnnotations@pre + 1
     * </pre>
     */
    public void addAnnotation(CommentBean cb) {
        // Add to the container to add an Annotation.
        myAnnotsToAdd.add(cb);
        // Add to the view as well.
        myAnnotations.add(cb);
    }

    /**
     * Removes an annotation
     * @param cb the comment bean to remove.
     *
     * <pre>
     * post: myAnnotsToDel = myAnnotsToDel@pre - 1
     * post: myAnnotations = myAnnotations@pre - 1
     * </pre>
     */
    public void delAnnotation(CommentBean cb) {
        // Add to the container to delete annotations.
        myAnnotsToDel.add(cb);
        // Remove from the view as well.
        myAnnotations.remove(cb);
    }

    /**
     * True if given Annotation bean exists in the current display.
     * @param bean the Annotation bean to compare.
     * @return true if <code>bean</code> exists in the current display.
     *
     * @see CommentBean#isEquivalent(CommentBean)
     */
    public boolean annotationExists(CommentBean bean) {
        for (Iterator iter = myAnnotations.iterator(); iter.hasNext();) {
            CommentBean cb = (CommentBean) iter.next();
            // Avoid comparing to itself.
            if (cb.getKey() == bean.getKey()) {
                continue;
            }
            if (cb.isEquivalent(bean)) {
                return true;
            }
        }
        // Not equivalent; false is returned.
        return false;
    }

    /**
     * Returns a collection <code>Xref</code> objects.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(XreferenceBean))
     * </pre>
     */
    public List getXrefs() {
        return myXrefs;
    }

    /**
     * Adds an xref.
     * @param xb the bean to add.
     *
     * <pre>
     * post: myXrefsToAdd = myXrefsToAdd@pre + 1
     * post: myXrefs = myXrefs@pre + 1
     * </pre>
     */
    public void addXref(XreferenceBean xb) {
        // Xref to add.
        myXrefsToAdd.add(xb);
        // Add to the view as well.
        myXrefs.add(xb);
    }

    /**
     * Removes a xref.
     * @param xb the X'reference bean to remove.
     *
     * <pre>
     * post: myXrefsToDel = myXrefsToDel@pre + 1
     * post: myXrefs = myXrefs@pre - 1
     * </pre>
     */
    public void delXref(XreferenceBean xb) {
        // Add to the container to delete the xref.
        myXrefsToDel.add(xb);
        // Remove from the view as well.
        myXrefs.remove(xb);
    }

    /**
     * True if given xref bean exists in the current display.
     * @param bean the Xref bean to compare.
     * @return true if <code>bean</code> exists in the current display.
     *
     * @see XreferenceBean#isEquivalent(XreferenceBean)
     */
    public boolean xrefExists(XreferenceBean bean) {
        for (Iterator iter = myXrefs.iterator(); iter.hasNext();) {
            XreferenceBean xb = (XreferenceBean) iter.next();
            // Avoid comparing to itself.
            if (xb.getKey() == bean.getKey()) {
                continue;
            }
            if (xb.isEquivalent(bean)) {
                return true;
            }
        }
        // Not equivalent; false is returned.
        return false;
    }

    /**
     * Saves the bean in the update list. Replaces any previous bean with same
     * key (can perform many edits to a single bean). The bean is not added if
     * it is a new bean.
     * @param cb the bean to update.
     */
    public void saveAnnotation(CommentBean cb) {
        if (!myAnnotsToAdd.contains(cb)) {
            myAnnotsToUpdate.add(cb);
        }
    }

    /**
     * Saves the bean in the update list. Replaces any previous bean with same
     * key (can perform many edits to a single bean). The bean is not added if
     * it is a new bean.
     * @param xb the bean to update.
     */
    public void saveXref(XreferenceBean xb) {
        if (!myXrefsToAdd.contains(xb)) {
            myXrefsToUpdate.add(xb);
        }
    }

    /**
     * Clears any pending xrefs and annotations stored in the transaction
     * containers.
     * <pre>
     * post: myAnnotsToAdd->isEmpty
     * post: myAnnotsToDel->isEmpty
     * post: myAnnotsToUpdate->isEmpty
     * post: myXrefsToAdd->isEmpty
     * post: myXrefsToDel->isEmpty
     * post: myXrefsToUpdate->isEmpty
     * </pre>
     */
    public void clearTransactions() {
        this.clearTransAnnotations();
        this.clearTransXrefs();
    }

    /**
     * Persists the current state to the persistent system. After this
     * method is called the persistent view is as same as the current view.
     * @param user handler to access the persistent method calls.
     * @exception IntactException for errors in updating the persistent system.
     */
    public void persist(EditUserI user) throws IntactException, SearchException {
        try {
            // Begin the transaction.
            user.begin();

            // Persiste the current view.
            persistCurrentView(user);

            // Commit the transaction.
            user.commit();
        }
        catch (IntactException ie1) {
            try {
                user.rollback();
            }
            catch (IntactException ie2) {
                // Oops! Problems with rollback; ignore this as this
                // error is reported via the main exception (ie1).
            }
            // Rethrow the exception to be logged.
            throw ie1;
        }
    }

    /**
     * Deletes all the links to sub objects of the current edit object.
     */
    public void clear() {
        // Clear Transaction containers.
        clearTransactions();
    }

    /**
     * Returns the topic menu for editing an existing annotation.
     * @return the topic menu for annotations
     * @throws SearchException thrown for failures with database access.
     */
    public List getEditTopicMenu() throws SearchException {
        return getTopicMenu(0);
    }

    /**
     * Returns the add menu for annotations.
     * @return the add menu for annotations
     * @throws SearchException thrown for failures with database access.
     */
    public List getAddTopicMenu() throws SearchException {
        return getTopicMenu(1);
    }

    public List getEditDatabaseMenu() throws SearchException {
        return getDatabaseMenu(0);
    }

    public List getAddDatabaseMenu() throws SearchException {
        return getDatabaseMenu(1);
    }

    /**
     * Returns the edit menu for xrefs.
     * @return the edit menu for xrefs.
     * @throws SearchException thrown for failures with database access.
     */
    public Map getEditXrefMenus() throws SearchException {
        return getXrefMenus(0);
    }

    /**
     * Returns the add menu for xrefs.
     * @return the add menu for xrefs
     * @throws SearchException thrown for failures with database access.
     */
    public Map getAddXrefMenus() throws SearchException {
        return getXrefMenus(1);
    }

    /**
     * Returns the editor specific menus. All the editors must override this
     * method to provide its own implementation.
     * @return the editor specific menus.
     * @throws SearchException thrown for failures with database access.
     */
    public Map getEditorMenus() throws SearchException {
        return null;
    }

    /**
     * Sets the layout in given context. This method is currently empty as
     * the layout defaults to cv layout. Override this method to provide editor
     * specific layout.
     * @param context the Tiles context to set the layout.
     */
    public void setLayout(ComponentContext context) {
        // Empty
    }

    /**
     * Returns the help tag link for the current view bean; subclasses must
     * override this method to return the help tag (if necessary) or else the
     * link to the CV editor is returned.
     * @return the tag as a String for the current view bean.
     */
    public String getHelpTag() {
        return "editor.cv.editors";
    }

    /**
     * Copies properties from given form to the bean.
     * @param form the form to update the internal data.
     */
    public void copyPropertiesFrom(EditorActionForm form) {
        setShortLabel(form.getShortLabel());
        setFullName(form.getFullName());
    }

    /**
     * Copies properties from bean to given form.
     * @param form the form to copy properties to.
     */
    public void copyPropertiesTo(EditorActionForm form) {
        form.setAc(getAcLink());
        form.setShortLabel(getShortLabel());
        form.setFullName(getFullName());
        form.setAnnotations(getAnnotations());
        form.setXrefs(getXrefs());
    }

    // Empty methods to be overriden by sub classes.

    /**
     * Persist any sub objects of the edited object. For example, proteins
     * need to be persisted in a separate transaction for an Interaction
     * @param user handler to the user to persist sub objects.
     * @throws IntactException for errors in persisting.
     * @throws SearchException for errors in searching for objects in the
     * persistent system.
     */
    public void persistOthers(EditUserI user) throws IntactException,
            SearchException {
    }

    /**
     * Adds the current edit object to the recent edited item list.
     * Interaction or Experiment beans must override this method.
     * @param user the user handle to add to the recent list.
     */
    public void addToRecentList(EditUserI user) {}

    /**
     * Removes the current edit object from the recent edited item list.
     * Interaction or Experiment beans must override this method.
     * @param user the user handle to remove from the recent list.
     */
    public void removeFromRecentList(EditUserI user) {}

    /**
     * Performs sanity check on a bean. Currently this method doesn't provide
     * checks. Subclass must override this method to provide checks relevant to
     * a view bean.
     * @throws ValidationException if sanity check fails.
     * @throws SearchException for errors in searching for objects in the
     * persistent system.
     */
    public void sanityCheck(EditUserI user) throws ValidationException,
            SearchException {
    }

    /**
     * False as this object is editable. Sublcasses such as ExperimentViewBean
     * must override this method if it has a large number of interactions.
     * This method is used by actions.jsp to disable saving a large Intact object.
     * @return false as all the edit beans are editable by default.
     */
    public Boolean getReadOnly() {
        return Boolean.FALSE;
    }

    /**
     * By default editor objects are not cloneable. Views such as Experiment
     * and Interaction must override this method to true as these objects
     * are cloneable.
     * @return false as by default editor objects are not cloneable.
     */
    public boolean getCloneState() {
        return false;
    }

    /**
     * By default editor objects can be deleted by selecting Delete button from
     * edit screen. However, Features are deleted from the Interaction editor.
     * @return true as by default editor objects are deletable.
     */
    public boolean getDeleteState() {
        return true;
    }

    // Protected Methods

    /**
     * Sets the annotated object for the bean.
     * @param annot AnnotatedObject to set the bean.
     */
    protected void setAnnotatedObject(AnnotatedObject annot) {
        myAnnotObject = annot;
    }

    // Abstract method

    /**
     * Gathers values in the view bean and updates the existing AnnotatedObject
     * if it exists or create a new annotated object for the view and sets the
     * annotated object.
     * @param user to access the persistent system.
     * @throws SearchException for errors in searching the persistent system.
     *
     * <pre>
     * post: getAnnotatedObject() != null
     * </pre>
     */
    protected abstract void updateAnnotatedObject(EditUserI user)
            throws SearchException;

    /**
     * Allows access to menu factory.
     * @return the menu factory for sub classes to create menus.
     */
    protected EditorMenuFactory getMenuFactory() {
        return myMenuFactory;
    }

    // Helper Methods

    /**
     * Creates a collection of <code>CommentBean</code> created from given
     * collection of annotations.
     * @param annotations a collection of <code>Annotation</code> objects.
     */
    private void makeCommentBeans(Collection annotations) {
        // Clear previous annotations.
        myAnnotations.clear();
        for (Iterator iter = annotations.iterator(); iter.hasNext();) {
            Annotation annot = (Annotation) iter.next();
            myAnnotations.add(new CommentBean(annot));
        }
    }

    /**
     * Creates a collection of <code>XrefBean</code> created from given
     * collection of xreferences.
     * @param xrefs a collection of <code>Xref</code> objects.
     */
    private void makeXrefBeans(Collection xrefs) {
        // Clear previous xrefs.
        myXrefs.clear();
        for (Iterator iter = xrefs.iterator(); iter.hasNext();) {
            Xref xref = (Xref) iter.next();
            myXrefs.add(new XreferenceBean(xref));
        }
    }

    /**
     * Returns a collection of annotations to add.
     * @return the collection of annotations to add to the current CV object.
     * Could be empty if there are no annotations to add.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(CommentBean)
     * </pre>
     */
    private Collection getAnnotationsToAdd() {
        // Annotations common to both add and delete.
        Collection common = CollectionUtils.intersection(myAnnotsToAdd, myAnnotsToDel);
        // All the annotations only found in annotations to add collection.
        return CollectionUtils.subtract(myAnnotsToAdd, common);
    }

    /**
     * Returns a collection of annotations to remove.
     * @return the collection of annotations to remove from the current CV object.
     * Could be empty if there are no annotations to delete.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(CommentBean)
     * </pre>
     */
    private Collection getAnnotationsToDel() {
        // Annotations common to both add and delete.
        Collection common = CollectionUtils.intersection(myAnnotsToAdd, myAnnotsToDel);
        // All the annotations only found in annotations to delete collection.
        return CollectionUtils.subtract(myAnnotsToDel, common);
    }

    /**
     * Returns a collection of annotations to update.
     * @return the collection of annotations to update from the current CV object.
     * Could be empty if there are no annotations to update.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(CommentBean)
     * </pre>
     */
    private Collection getAnnotationsToUpdate() {
        return myAnnotsToUpdate;
    }

    /**
     * Returns a collection of xrefs to add.
     * @return the collection of xrefs to add to the current CV object.
     * Could be empty if there are no xrefs to add.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(XreferenceBean))
     * </pre>
     */
    private Collection getXrefsToAdd() {
        // Xrefs common to both add and delete.
        Collection common = CollectionUtils.intersection(myXrefsToAdd, myXrefsToDel);
        // All the xrefs only found in xrefs to add collection.
        return CollectionUtils.subtract(myXrefsToAdd, common);
    }

    /**
     * Returns a collection of xrefs to remove.
     * @return the collection of xrefs to remove from the current CV object.
     * Could be empty if there are no xrefs to delete.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(XreferenceBean))
     * </pre>
     */
    private Collection getXrefsToDel() {
        // Xrefs common to both add and delete.
        Collection common = CollectionUtils.intersection(myXrefsToAdd, myXrefsToDel);
        // All the xrefs only found in xrefs to delete collection.
        return CollectionUtils.subtract(myXrefsToDel, common);
    }

    /**
     * Returns a collection of xrefs to update.
     * @return the collection of xrefs to update from the current CV object.
     * Could be empty if there are no xrefs to update.
     *
     * <pre>
     * post: return->forall(obj: Object | obj.oclIsTypeOf(XreferenceBean)
     * </pre>
     */
    private Collection getXrefsToUpdate() {
        return myXrefsToUpdate;
    }

    /**
     * Clears annotations stored transaction containers.
     */
    private void clearTransAnnotations() {
        myAnnotsToAdd.clear();
        myAnnotsToDel.clear();
        myAnnotsToUpdate.clear();
    }

    /**
     * Clears xrefs stored in transaction containers.
     */
    private void clearTransXrefs() {
        myXrefsToAdd.clear();
        myXrefsToDel.clear();
        myXrefsToUpdate.clear();
    }

    private List getTopicMenu(int mode) throws SearchException {
        return getMenu(EditorMenuFactory.TOPIC, getShortLabel(), mode);
    }

    private List getDatabaseMenu(int mode) throws SearchException {
        return getMenu(EditorMenuFactory.DATABASE, getShortLabel(), mode);
    }

    private Map getXrefMenus(int mode) throws SearchException {
        Map map = new HashMap();
        String name;
        // The short label to remove from the list.
        String label = getShortLabel();

        // The database menu.
        name = EditorMenuFactory.DATABASE;
        map.put(name, getMenu(name, label, mode));

        // The qualifier menu.
        name = EditorMenuFactory.QUALIFIER;
        map.put(name, getMenu(name, label, mode));
        return map;
    }

    /**
     * Returns a menu for <code>name</code> without <code>label</code>; the menu
     * type is dependent on the <code>mode</code>
     * @param name the name of the menu to get.
     * @param label the label to strip off from the returning menu.
     * @param mode 0 for edit or 1 for add.
     * @return menu for <code>name</code> without <code>label</code> if applicable.
     * @throws SearchException for database search error when constructing the
     * menu.
     */
    private List getMenu(String name, String label, int mode) throws SearchException {
        // Remove the current label from menus (to stop recursive calls to db)!
        if (myMenuFactory.isMenuType(getEditClass())) {
            // Remove my short label to avoid circular reference. We create a
            // new list so the remove method wouldn't affect the original list.
            List list = new ArrayList(myMenuFactory.getMenu(name, mode));
            list.remove(label);
            return list;
        }
        return myMenuFactory.getMenu(name, mode);
    }

    // Persist the current annotated object.

    private void persistCurrentView(EditUserI user) throws IntactException,
            SearchException {
        // First create/update the annotated object by the view.
        updateAnnotatedObject(user);

        // Update the short label and full name as they are common to all.
        myAnnotObject.setShortLabel(getShortLabel());
        myAnnotObject.setFullName(getFullName());

        // Save the annotation size for comparision.
        int initSize = myAnnotObject.getAnnotations().size();
        // Flag to track whether the annotations are changed or not.
        boolean changed = false;

        // Don't care whether annotated object exists or not because we don't
        // need an AC in the annotation table.

        // Create annotations and add them to CV object.
        for (Iterator iter = getAnnotationsToAdd().iterator(); iter.hasNext();) {
            Annotation annot = ((CommentBean) iter.next()).getAnnotation(user);
            // Need this to generate the PK for the indirection table.
            user.create(annot);
            myAnnotObject.addAnnotation(annot);
            changed = true;
        }
        // Delete annotations and remove them from CV object.
        for (Iterator iter = getAnnotationsToDel().iterator(); iter.hasNext();) {
            Annotation annot = ((CommentBean) iter.next()).getAnnotation(user);
            user.delete(annot);
            myAnnotObject.removeAnnotation(annot);
            changed = true;
        }
        // Update annotations; update the object with values from the bean.
        // The update of annotated object ensures the sub objects are updated as well.
        for (Iterator iter = getAnnotationsToUpdate().iterator(); iter.hasNext();) {
            Annotation annot = ((CommentBean) iter.next()).getAnnotation(user);
            user.update(annot);
            changed = true;
        }
        // Xref has a parent_ac column which is not a foreign key. So, the parent needs
        // to be persistent before we can create the Xrefs.
        if (!user.isPersistent(myAnnotObject)) {
            user.create(myAnnotObject);
        }

        // Create xrefs and add them to CV object.
        for (Iterator iter = getXrefsToAdd().iterator(); iter.hasNext();) {
            Xref xref = ((XreferenceBean) iter.next()).getXref(user);
            user.create(xref);
            myAnnotObject.addXref(xref);
        }
        // Delete xrefs and remove them from CV object.
        for (Iterator iter = getXrefsToDel().iterator(); iter.hasNext();) {
            Xref xref = ((XreferenceBean) iter.next()).getXref(user);
            user.delete(xref);
            myAnnotObject.removeXref(xref);
        }
        // Update xrefs; see the comments for annotation update above.
        for (Iterator iter = getXrefsToUpdate().iterator(); iter.hasNext();) {
            Xref xref = ((XreferenceBean) iter.next()).getXref(user);
            user.update(xref);
        }
        // Update the cv object only for an object already persisted.
        if (user.isPersistent(myAnnotObject)) {
            // If collection sizes are same but modified; force update. Need
            // this OJB update strategy doesn't mark the object as dirty.
            if ((myAnnotations.size() == initSize) && changed) {
                user.forceUpdate(myAnnotObject);
            }
            else {
                // Ordinary update
                user.update(myAnnotObject);
            }
        }
    }

    private void resetAnnotatedObject(AnnotatedObject annobj) {
        // Need to get the real object for a proxy type.
        setAnnotatedObject((AnnotatedObject) IntactHelper.getRealIntactObject(annobj));
        myEditClass = IntactHelper.getRealClassName(annobj);
        setFullName(annobj.getFullName());
    }

    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 != null) {
            return obj1.equals(obj2);
        }
        return false;
    }
}
