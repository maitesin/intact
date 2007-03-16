/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.mocks.components;

import uk.ac.ebi.intact.model.Component;
import uk.ac.ebi.intact.model.Interaction;
import uk.ac.ebi.intact.mocks.InstitutionMock;
import uk.ac.ebi.intact.mocks.IntactObjectSetter;
import uk.ac.ebi.intact.mocks.bioSources.EcoliMock;
import uk.ac.ebi.intact.mocks.cvComponentRoles.BaitMock;
import uk.ac.ebi.intact.mocks.proteins.P08050Mock;

/**
 * TODO comment this
 *
 * @author Catherine Leroy (cleroy@ebi.ac.uk)
 * @version $Id$
 * @since TODO
 */
public class P08050ComponentMock {
    public static Component getMock(Interaction interaction){
        Component component = new Component(InstitutionMock.getMock(),interaction, P08050Mock.getMock(), BaitMock.getMock());
        component.setExpressedIn(EcoliMock.getMock());
        component.setStoichiometry(0);
        component = (Component) IntactObjectSetter.setBasicObject(component);

        component.setOwner(InstitutionMock.getMock());
        return component;
    }
}