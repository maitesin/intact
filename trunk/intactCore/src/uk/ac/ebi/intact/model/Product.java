/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.model;

import java.util.*;

/**
 * Describes products of an interaction.
 *
 * @intact.example In a phosphorylation, this object would link to the phosphorylated Protein.
 * @author hhe
 */
public class Product extends BasicObject {

    ///////////////////////////////////////
    //attributes

    //attributes used for mapping BasicObjects - project synchron
    public String interactionAc;
    public String interactorAc;
    public String cvProductRoleAc;

    /**
     * Represents ...
     */
    protected float stoichiometry = 1;

    ///////////////////////////////////////
    // associations

    /**
     *
     */
    public Interaction interaction;
    /**
     *
     */
    public Interactor interactor;
    /**
     *
     */
    public CvProductRole cvProductRole;


    ///////////////////////////////////////
    //access methods for attributes

    public float getStoichiometry() {
        return stoichiometry;
    }
    public void setStoichiometry(float stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    ///////////////////////////////////////
    // access methods for associations

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        if (this.interaction != interaction) {
            if (this.interaction != null) this.interaction.removeReleased(this);
            this.interaction = interaction;
            if (interaction != null) interaction.addReleased(this);
        }
    }
    public Interactor getInteractor() {
        return interactor;
    }

    public void setInteractor(Interactor interactor) {
        if (this.interactor != interactor) {
            if (this.interactor != null) this.interactor.removeProduct(this);
            this.interactor = interactor;
            if (interactor != null) interactor.addProduct(this);
        }
    }
    public CvProductRole getCvProductRole() {
        return cvProductRole;
    }

    public void setCvProductRole(CvProductRole cvProductRole) {
        this.cvProductRole = cvProductRole;
    }

    //attributes used for mapping BasicObjects - project synchron
    public String getInteractionAc() {
        return this.interactionAc;
    }
    public void getInteractionAc(String ac) {
        this.interactionAc= ac;
    }

    public String getInteractorAc() {
        return this.interactorAc;
    }
    public void getInteractorAc(String ac) {
        this.interactorAc= ac;
    }

    public String getCvProductRoleAc() {
        return this.cvProductRoleAc;
    }
    public void getcvProductRoleAc(String ac) {
        this.cvProductRoleAc= ac;
    }

} // end Product




