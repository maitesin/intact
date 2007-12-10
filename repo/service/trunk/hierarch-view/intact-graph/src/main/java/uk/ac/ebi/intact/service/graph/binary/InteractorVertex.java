/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.service.graph.binary;

import edu.uci.ics.jung.graph.impl.SimpleSparseVertex;
import edu.uci.ics.jung.utils.UserData;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.Interactor;
import uk.ac.ebi.intact.service.graph.Node;
import uk.ac.ebi.intact.service.graph.binary.label.IdentifierLabelStrategy;
import uk.ac.ebi.intact.service.graph.binary.label.LabelStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


/**
 * Implementation of Vertex that holds the interactor information
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class InteractorVertex extends SimpleSparseVertex implements Node<BinaryInteractionEdge> {

    private Interactor interactor;
    private Collection<BinaryInteractionEdge> edges;
    private Collection<CrossReference> experimentalRole;
    private Collection<CrossReference> properties;
    private LabelStrategy labelStrategy = new IdentifierLabelStrategy();

    private String nodeID;

    private boolean isCentral = false;

     protected InteractorVertex(Interactor interactor) {
        this.interactor = interactor;
        setId();
        addUserDatum("id", getId(), UserData.SHARED);
    }

    public Collection<BinaryInteractionEdge> getEdges() {
        if (edges == null) {
            edges = new HashSet<BinaryInteractionEdge>();
        }
        return edges;
    }

    public void setExperimentalRole( Collection<CrossReference> experimentalRole ) {
        this.experimentalRole = experimentalRole;
    }

    public void setProperties( Collection<CrossReference> properties ) {
        this.properties = properties;
    }

    public void setCentral( boolean central ) {
        isCentral = central;
    }

    public Collection<CrossReference> getProperties(){
        return properties;
    }

    public String getId(){
        return nodeID;
    }

    private void setId() {

        for ( CrossReference id : interactor.getIdentifiers() ) {
            if ( nodeID == null ) {
                nodeID = id.getIdentifier();
            }
            if ( id.getDatabase().equals( "intact" ) ) {
                nodeID =  id.getIdentifier();
            }
        }        
    }

    public void setLabelBuilder( LabelStrategy labelStrategy ) {
        this.labelStrategy = labelStrategy;
    }

    public boolean isCentralNode() {
        return isCentral;
    }

    public boolean isBait() {
        if ( experimentalRole != null && !experimentalRole.isEmpty() ) {
            for ( CrossReference xref : experimentalRole ) {
                if ( xref.getDatabase().equals("MI") && xref.getIdentifier().equals("0496")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPrey() {
        if ( experimentalRole != null && !experimentalRole.isEmpty() ) {
            for ( CrossReference xref : experimentalRole ) {
                if ( xref.getDatabase().equals("MI") && xref.getIdentifier().equals("0498")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNeutralComponent() {
        if ( experimentalRole != null && !experimentalRole.isEmpty() ) {
            for ( CrossReference xref : experimentalRole ) {
                if ( xref.getDatabase().equals("MI") && xref.getIdentifier().equals("0497")) {
                    return true;
                }
            }
        }
        return false;
    }



    public String getLabel() {
        return labelStrategy.buildLabel( interactor, "intact");
    }

    public Collection<String> getPropertiesIds(){
        Collection<String> propteriesIds = new ArrayList<String>(properties.size());
        for ( CrossReference property : properties) {
            propteriesIds.add(property.getIdentifier());
        }

        return propteriesIds;
    }

    public Interactor getInteractor() {
        return interactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InteractorVertex that = (InteractorVertex) o;

        return !( interactor != null ? !interactor.equals( that.interactor ) : that.interactor != null );

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (interactor != null ? interactor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getId()+getEdges();
    }
}