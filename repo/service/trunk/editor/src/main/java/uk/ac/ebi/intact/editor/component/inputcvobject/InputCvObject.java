/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.editor.component.inputcvobject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.TransactionStatus;
import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.core.persistence.dao.CvObjectDao;
import uk.ac.ebi.intact.core.persistence.dao.DaoFactory;
import uk.ac.ebi.intact.model.CvDagObject;
import uk.ac.ebi.intact.model.CvObject;
import uk.ac.ebi.intact.model.CvTopic;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@FacesComponent("uk.ac.ebi.intact.editor.InputCvObject")
public class InputCvObject extends UIInput implements NamingContainer, Serializable {

    private static final Log log = LogFactory.getLog( InputCvObject.class );

    private TreeNode root;

    public InputCvObject() {
    }

    @Override
    public String getFamily() {
      return UINamingContainer.COMPONENT_FAMILY;
   }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        load((String) getAttributes().get("cvIdentifier"));
        super.encodeBegin(context);
    }

    public void load( String id ) {
        log.trace( "Loading CvObject with id '"+id+"'" );

        if (id == null) {
            throw new NullPointerException("cvClass is null");
        }

        final TransactionStatus transactionStatus = IntactContext.getCurrentInstance().getDataContext().beginTransaction();

        DaoFactory daoFactory = IntactContext.getCurrentInstance().getDaoFactory();
        CvObjectDao cvDAO = daoFactory.getCvObjectDao();
        CvDagObject rootCv = (CvDagObject) cvDAO.getByPsiMiRef(id);

        if (rootCv == null) {
            throw new IllegalArgumentException("Root does not exist: " + id);
        }

        root = buildTreeNode(rootCv, null);

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(transactionStatus);

        log.trace( "\tLoading completed. Root: "+root+ "(children="+root.getChildCount()+")" );

    }

    private TreeNode buildTreeNode( CvDagObject cv, TreeNode node ) {
       TreeNode childNode = new DefaultTreeNode(cv, node);

        for ( CvDagObject child : cv.getChildren() ) {
            buildTreeNode( child, childNode );
        }

        return childNode;
    }

    public String getDescription(CvObject cvObject) {
        if (cvObject == null) return null;
        
        final TransactionStatus transactionStatus = IntactContext.getCurrentInstance().getDataContext().beginTransaction();
        Query query = IntactContext.getCurrentInstance().getDaoFactory().getEntityManager()
                .createQuery("select a.annotationText from CvObject cv join cv.annotations as a where cv.ac = :cvAc and " +
                        "a.cvTopic.shortLabel = :cvTopicLabel");
        query.setParameter("cvAc", cvObject.getAc());
        query.setParameter("cvTopicLabel", CvTopic.DEFINITION);

        List<String> results = query.getResultList();

        String annot = null;

        if (!results.isEmpty()) {
            annot = results.iterator().next();
        }

        IntactContext.getCurrentInstance().getDataContext().commitTransaction(transactionStatus);

        return annot;
    }

    public TreeNode getRoot() {
        if (root == null) {
            return new DefaultTreeNode(null, null);
        }
        return root;
    }

    //    public TreeModel getRoot() {
////        return root;
//        return (TreeModel) getStateHelper().eval("treeModel");
////
////        if (treeModel != null) {
////            return (TreeNode) treeModel.getWrappedData();
////        }
////
////        return null);
//    }
//
//    public void setRoot(TreeModel root) {
//        getStateHelper().put("treeModel", root);
////        this.root = root;
//    }

    public CvObject getSelected() {
        return (CvObject) getStateHelper().eval("selectedCv");
    }

    public void setSelected(CvObject selected) {
        getStateHelper().put("selectedCv", selected);
    }

    public String getFriendlyWidgetId() {
        return getClientId().replaceAll(":", "__")+"__dlg";
    }

}
