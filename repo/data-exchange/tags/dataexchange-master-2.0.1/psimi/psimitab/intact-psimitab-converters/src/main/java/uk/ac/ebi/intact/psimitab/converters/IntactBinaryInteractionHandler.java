/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
 *  limitations under the License.
 */
package uk.ac.ebi.intact.psimitab.converters;

import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceFactory;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.psimitab.IntactBinaryInteraction;
import uk.ac.ebi.intact.psimitab.IntactColumnHandler;
import uk.ac.ebi.intact.psimitab.converters.expansion.ExpansionStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Handles intact specific columns in the MITAB data import/export.
 *
 * @author Nadin Neuhauser
 * @version $Id$
 * @since 2.0.0
 */
public class IntactBinaryInteractionHandler extends IntactColumnHandler implements BinaryInteractionHandler<IntactBinaryInteraction> {

    private CrossReferenceConverter<InteractorXref> xConverter = new CrossReferenceConverter<InteractorXref>();
    private CvObjectConverter<CrossReferenceImpl, CvObject> cvObjectConverter = new CvObjectConverter<CrossReferenceImpl, CvObject>();
    private static final String TAXID = "taxid";


    public void process( IntactBinaryInteraction ibi, Interaction interaction ) {
        Iterator<Component> iterator = interaction.getComponents().iterator();
        uk.ac.ebi.intact.model.Interactor intactInteractorA = iterator.next().getInteractor();
        uk.ac.ebi.intact.model.Interactor intactInteractorB = iterator.next().getInteractor();

        // set properties of interactor A
        List<CrossReference> propertiesA = xConverter.toCrossReferences( intactInteractorA.getXrefs(), false, true );
        ibi.setPropertiesA( propertiesA );

        // set properties of interactor B
        List<CrossReference> propertiesB = xConverter.toCrossReferences( intactInteractorB.getXrefs(), false, true );
        ibi.setPropertiesB( propertiesB );

        // set type of interactor A
        List<CrossReference> interactorTypesA = new ArrayList<CrossReference>();
        interactorTypesA.add( cvObjectConverter.toCrossReference( intactInteractorA.getCvInteractorType() ) );
        ibi.setInteractorTypeA( interactorTypesA );

        // set type of interactor B
        List<CrossReference> interactorTypesB = new ArrayList<CrossReference>();
        interactorTypesB.add( cvObjectConverter.toCrossReference( intactInteractorB.getCvInteractorType() ) );
        ibi.setInteractorTypeB( interactorTypesB );

        // set host organism
        List<CrossReference> hostOrganisms = new ArrayList<CrossReference>();
        for ( Experiment experiment : interaction.getExperiments() ) {
            String id = experiment.getBioSource().getTaxId();
            if ( id != null ) {
                String text = experiment.getBioSource().getShortLabel();
                hostOrganisms.add( CrossReferenceFactory.getInstance().build( TAXID, id, text ) );
            }
        }
        ibi.setHostOrganism( hostOrganisms );

        // set expermimental role of interactor A and B
        List<CrossReference> experimentRolesA = new ArrayList<CrossReference>();
        List<CrossReference> experimentRolesB = new ArrayList<CrossReference>();
        List<CrossReference> biologicalRolesA = new ArrayList<CrossReference>();
        List<CrossReference> biologicalRolesB = new ArrayList<CrossReference>();
        for ( Component component : interaction.getComponents() ) {
            if ( component.getInteractor().equals( intactInteractorA ) ) {
                biologicalRolesA.add( cvObjectConverter.toCrossReference( component.getCvBiologicalRole() ) );
                experimentRolesA.add( cvObjectConverter.toCrossReference( component.getCvExperimentalRole() ) );
            }
            if ( component.getInteractor().equals( intactInteractorB ) ) {
                biologicalRolesB.add( cvObjectConverter.toCrossReference( component.getCvBiologicalRole() ) );
                experimentRolesB.add( cvObjectConverter.toCrossReference( component.getCvExperimentalRole() ) );
            }
        }
        ibi.setExperimentalRolesInteractorA( experimentRolesA );
        ibi.setExperimentalRolesInteractorB( experimentRolesB );
        ibi.setBiologicalRolesInteractorA( biologicalRolesA );
        ibi.setBiologicalRolesInteractorB( biologicalRolesB );

        // set dataset
        if ( interaction.getExperiments() != null ) {
            List<String> datasets = new ArrayList<String>();
            for ( Experiment experiment : interaction.getExperiments() ) {
                for ( Annotation annotation : experiment.getAnnotations() ) {
                    CvObjectXref idXref = CvObjectUtils.getPsiMiIdentityXref( annotation.getCvTopic() );
                    if ( idXref != null && idXref.getPrimaryId() != null && idXref.getPrimaryId().equals( CvTopic.DATASET_MI_REF ) ) {
                        datasets.add( annotation.getAnnotationText() );
                    }
                }
            }
            ibi.setDataset( datasets );
        }

    }

    ///////////////////////////////
    // IsExpansionStrategyAware

    public void process( IntactBinaryInteraction bi, Interaction interaction, ExpansionStrategy expansionStrategy )
             {
        // first, do the usual processing
        process( bi, interaction );

        // deal with expansion strategy now
        if ( bi instanceof IntactBinaryInteraction ) {
            ( ( IntactBinaryInteraction ) bi ).setExpansionMethod( expansionStrategy.getName() );
        }
    }

}
