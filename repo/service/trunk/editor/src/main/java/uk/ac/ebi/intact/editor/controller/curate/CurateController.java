package uk.ac.ebi.intact.editor.controller.curate;

import org.apache.myfaces.orchestra.conversation.annotations.ConversationName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.editor.controller.JpaAwareController;
import uk.ac.ebi.intact.editor.controller.curate.cvobject.CvObjectController;
import uk.ac.ebi.intact.editor.controller.curate.experiment.ExperimentController;
import uk.ac.ebi.intact.editor.controller.curate.feature.FeatureController;
import uk.ac.ebi.intact.editor.controller.curate.interaction.InteractionController;
import uk.ac.ebi.intact.editor.controller.curate.interactor.InteractorController;
import uk.ac.ebi.intact.editor.controller.curate.organism.BioSourceController;
import uk.ac.ebi.intact.editor.controller.curate.participant.ParticipantController;
import uk.ac.ebi.intact.editor.controller.curate.publication.PublicationController;
import uk.ac.ebi.intact.model.*;

/**
 * Helper controller on conversation scope, that helps to load/save objects within the same transaction as the other AnnotatedObjectControllers.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Controller
@Scope( "conversation.access" )
@ConversationName( "general" )
public class CurateController extends JpaAwareController {

    public String edit(IntactObject intactObject) {
        String suffix = (intactObject.getAc() != null)? "?faces-redirect=true&includeViewParams=true" : "";

        CurateObjectMetadata metadata = getMetadata(intactObject);
        return "/curate/"+metadata.getSlug()+suffix;
    }

    @Transactional(value = "core", propagation = Propagation.NEVER)
    public void save(IntactObject intactObject) {
        AnnotatedObjectController annotatedObjectController = getMetadata(intactObject).getAnnotatedObjectController();
        annotatedObjectController.doSave(null);
    }

    @Transactional(value = "core", propagation = Propagation.NEVER)
    public void discard(IntactObject intactObject) {
        CuratorContextController curatorContextController = (CuratorContextController) getSpringContext().getBean("curatorContextController");
        curatorContextController.removeFromUnsaved(intactObject);

        AnnotatedObjectController annotatedObjectController = getMetadata(intactObject).getAnnotatedObjectController();
        annotatedObjectController.doRevertChanges(null);
    }

    public CurateObjectMetadata getMetadata(IntactObject intactObject) {
        Class<?> iaClass = intactObject.getClass();

        if (Publication.class.isAssignableFrom(iaClass)) {
            PublicationController publicationController = (PublicationController) getSpringContext().getBean("publicationController");
            publicationController.setPublication((Publication)intactObject);
            return new CurateObjectMetadata(publicationController, "publication");
        } else if (Experiment.class.isAssignableFrom(iaClass)) {
            ExperimentController experimentController = (ExperimentController) getSpringContext().getBean("experimentController");
            experimentController.setExperiment((Experiment)intactObject);
            return new CurateObjectMetadata(experimentController, "experiment");
        } else if (Interaction.class.isAssignableFrom(iaClass)) {
            InteractionController interactionController = (InteractionController) getSpringContext().getBean("interactionController");
            interactionController.setInteraction((Interaction)intactObject);
            interactionController.refreshParticipants();
            interactionController.refreshExperimentLists();
            return new CurateObjectMetadata(interactionController, "interaction");
        } else if (Interactor.class.isAssignableFrom(iaClass)) {
            InteractorController interactorController = (InteractorController) getSpringContext().getBean("interactorController");
            interactorController.setInteractor((Interactor)intactObject);
            return new CurateObjectMetadata(interactorController, "interactor");
        } else if (Component.class.isAssignableFrom(iaClass)) {
            ParticipantController participantController = (ParticipantController) getSpringContext().getBean("participantController");
            participantController.setParticipant((Component) intactObject);
            return new CurateObjectMetadata(participantController, "participant");
        } else if (Feature.class.isAssignableFrom(iaClass)) {
            FeatureController featureController = (FeatureController) getSpringContext().getBean("featureController");
            featureController.setFeature((Feature) intactObject);
            return new CurateObjectMetadata(featureController, "feature");
        } else if (CvObject.class.isAssignableFrom(iaClass)) {
            CvObjectController cvObjectController = (CvObjectController) getSpringContext().getBean("cvObjectController");
            cvObjectController.setCvObject((CvObject) intactObject);
            return new CurateObjectMetadata(cvObjectController, "cvobject");
        } else if (BioSource.class.isAssignableFrom(iaClass)) {
            BioSourceController bioSourceController = (BioSourceController) getSpringContext().getBean("bioSourceController");
            bioSourceController.setBioSource((BioSource) intactObject);
            return new CurateObjectMetadata(bioSourceController, "organism");
        } else {
            throw new IllegalArgumentException("No view defined for object with type: "+iaClass);
        }
    }

    public class CurateObjectMetadata {
        private String slug;
        private AnnotatedObjectController annotatedObjectController;

        private CurateObjectMetadata(AnnotatedObjectController annotatedObjectController, String slug) {
            this.annotatedObjectController = annotatedObjectController;
            this.slug = slug;
        }

        public String getSlug() {
            return slug;
        }

        public AnnotatedObjectController getAnnotatedObjectController() {
            return annotatedObjectController;
        }
    }

}
