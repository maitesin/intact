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

import psidev.psi.mi.tab.model.Alias;
import psidev.psi.mi.tab.model.*;
import uk.ac.ebi.intact.model.*;
import static uk.ac.ebi.intact.model.CvAliasType.*;
import uk.ac.ebi.intact.model.util.CvObjectUtils;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;
import uk.ac.ebi.intact.model.util.ProteinUtils;
import uk.ac.ebi.intact.psimitab.model.ExtendedInteractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Interactor Converter.
 *
 * @author Nadin Neuhauser
 * @version $Id$
 * @since 2.0.0
 */
public class InteractorConverter {

    private CrossReferenceConverter<InteractorXref> xRefConverter = new CrossReferenceConverter<InteractorXref>();

    private static final List<String> uniprotKeys;

    static {
        uniprotKeys = new ArrayList<String>( Arrays.asList( GENE_NAME_MI_REF, GENE_NAME_SYNONYM_MI_REF,
                                                            ISOFORM_SYNONYM_MI_REF, LOCUS_NAME_MI_REF,
                                                            ORF_NAME_MI_REF ) );
    }

    public ExtendedInteractor toMitab( uk.ac.ebi.intact.model.Interactor intactInteractor, Interaction interaction ) {
        if ( intactInteractor == null ) {
            throw new IllegalArgumentException( "Interactor must not be null" );
        }

        ExtendedInteractor tabInteractor = new ExtendedInteractor();

        // identifiers
        Collection<CrossReference> identifiers = xRefConverter.toCrossReferences( intactInteractor.getXrefs(), true, false );
        identifiers.add( CrossReferenceFactory.getInstance().build( CvDatabase.INTACT, intactInteractor.getAc() ) );
        tabInteractor.setIdentifiers( identifiers );

        // alternative identifiers & aliases
        if ( intactInteractor.getAliases() != null ) {
            Collection<CrossReference> altIds = new ArrayList<CrossReference>();
            Collection<Alias> tabAliases = new ArrayList<Alias>();
            for ( InteractorAlias alias : intactInteractor.getAliases() ) {
                String id = alias.getName();
                String db = null;
                String mi = null;
                if ( alias.getCvAliasType() != null ) {
                    CvObjectXref idxref = CvObjectUtils.getPsiMiIdentityXref( alias.getCvAliasType() );
                    mi = idxref.getPrimaryId();
                    if ( uniprotKeys.contains( mi ) ) {
                        db = CvDatabase.UNIPROT;
                    } else {
                        db = CvDatabase.INTACT;
                    }
                }
                if ( id != null && db != null ) {
                    if ( alias.getCvAliasType() != null ) {
                        String text = alias.getCvAliasType().getShortLabel();
                        if ( mi.equals( GENE_NAME_MI_REF ) ) {
                            Alias tabAlias = new AliasImpl( db, id );
                            tabAliases.add( tabAlias );
                        } else {
                            if ( text != null ) {
                                CrossReference altId = CrossReferenceFactory.getInstance().build( db, id, text );
                                altIds.add( altId );
                            } else {
                                CrossReference altId = CrossReferenceFactory.getInstance().build( db, id );
                                altIds.add( altId );
                            }
                        }
                    } else {
                        CrossReference altId = CrossReferenceFactory.getInstance().build( db, id );
                        altIds.add( altId );
                    }
                }

            }
            tabInteractor.setAlternativeIdentifiers( altIds );
            tabInteractor.setAliases( tabAliases );
        }

        // if it is a protein from uniprot, assume the short label is the uniprot ID and add it to the
        // alternative identifiers
        if (intactInteractor instanceof Protein) {
            Protein prot = (Protein)intactInteractor;

            if (ProteinUtils.isFromUniprot(prot)) {
                CrossReference altId = CrossReferenceFactory.getInstance().build( CvDatabase.UNIPROT, prot.getShortLabel() );
                tabInteractor.getAlternativeIdentifiers().add(altId);
            }
        }

        // taxid
        if ( intactInteractor.getBioSource() != null ) {
            int taxid = Integer.parseInt( intactInteractor.getBioSource().getTaxId() );
            String name = intactInteractor.getBioSource().getShortLabel();
            Organism oragnism = new OrganismImpl( taxid, name );
            tabInteractor.setOrganism( oragnism );
        }

         // process extended
        CvObjectConverter cvObjectConverter = new CvObjectConverter();
        CrossReferenceConverter xConverter = new CrossReferenceConverter();

        // properties
        List<CrossReference> properties = xConverter.toCrossReferences( intactInteractor.getXrefs(), false, true );
        //get again the identifiers with text
        Collection<CrossReference> identifiersWithText = xRefConverter.toCrossReferences( intactInteractor.getXrefs(), true, true );
        properties.addAll(identifiersWithText);
        
        // if molecule type is small molecule, export all chebi ids to that list.
        if( CvObjectUtils.isSmallMoleculeType( intactInteractor.getCvInteractorType() ) ) {
            final Collection<InteractorXref> chebiXrefs =
                    AnnotatedObjectUtils.searchXrefs( intactInteractor, CvDatabase.CHEBI_MI_REF, null );
            for ( CrossReference chebiCrossReference : xRefConverter.toCrossReferences( chebiXrefs, true ) ) {
                if( !properties.contains( chebiCrossReference  ) ) {
                    properties.add( chebiCrossReference );
                }
            }
        }
        tabInteractor.setProperties( properties );

        // interactor type
        CrossReference interactorTypeA = cvObjectConverter.toCrossReference( intactInteractor.getCvInteractorType() );
        tabInteractor.setInteractorType( interactorTypeA );

        // expermimental role
        List<CrossReference> experimentRoles = new ArrayList<CrossReference>();
        List<CrossReference> biologicalRoles = new ArrayList<CrossReference>();

        //set parameters
        for ( Component component : interaction.getComponents() ) {
            if ( component.getInteractor().equals( intactInteractor ) ) {
                biologicalRoles.add( cvObjectConverter.toCrossReference( component.getCvBiologicalRole() ) );
                experimentRoles.add( cvObjectConverter.toCrossReference( component.getCvExperimentalRole() ) );

                //parameters
                for ( ComponentParameter componentParameter : component.getParameters() ) {
                    uk.ac.ebi.intact.psimitab.model.Parameter parameter =
                            new uk.ac.ebi.intact.psimitab.model.Parameter(componentParameter.getCvParameterType().getShortLabel(),
                                                                          componentParameter.getFactor(),
                                                                          componentParameter.getBase(),
                                                                          componentParameter.getExponent(),
                                                                          (componentParameter.getCvParameterUnit() != null? componentParameter.getCvParameterUnit().getShortLabel() : null));
                    tabInteractor.getParameters().add( parameter );
                }
            }
        }

        tabInteractor.setExperimentalRoles( experimentRoles );
        tabInteractor.setBiologicalRoles( biologicalRoles );

        // annotations
        for (Annotation intactAnnotation : intactInteractor.getAnnotations()) {
            uk.ac.ebi.intact.psimitab.model.Annotation annot =
                    new uk.ac.ebi.intact.psimitab.model.Annotation(intactAnnotation.getCvTopic().getShortLabel(), intactAnnotation.getAnnotationText());
            tabInteractor.getAnnotations().add(annot);
        }

        return tabInteractor;
    }

    public Component fromMitab() {
        throw new UnsupportedOperationException();
    }
}
