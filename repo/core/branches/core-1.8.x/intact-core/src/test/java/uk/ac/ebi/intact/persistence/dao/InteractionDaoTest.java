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
package uk.ac.ebi.intact.persistence.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.core.persister.PersisterHelper;
import uk.ac.ebi.intact.core.persister.CorePersister;
import uk.ac.ebi.intact.core.persister.finder.DefaultFinder;
import uk.ac.ebi.intact.core.persister.finder.NonCrcLegacyFinder;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.core.unit.IntactMockBuilder;
import uk.ac.ebi.intact.core.util.SchemaUtils;
import uk.ac.ebi.intact.model.Interaction;

import java.util.Arrays;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class InteractionDaoTest extends IntactBasicTestCase {

    @Test
    public void getByInteractorsPrimaryId_exact() throws Exception {
        final IntactMockBuilder mockBuilder = getMockBuilder();
        Interaction mockInteraction = mockBuilder.createInteractionRandomBinary();
        mockInteraction.getComponents().clear();

        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A1", "prot1" ) ) );
        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A2", "prot2" ) ) );

        PersisterHelper.saveOrUpdate( mockInteraction );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().countAll() );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1", "A2" ).size() );
        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1" ).size() );
        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A2" ).size() );
    }

    @Test
    public void getByInteractorsPrimaryId_exact2() throws Exception {
        final IntactMockBuilder mockBuilder = getMockBuilder();
        Interaction mockInteraction = mockBuilder.createInteractionRandomBinary();
        mockInteraction.getComponents().clear();

        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A1", "prot1" ) ) );
        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A2", "prot2" ) ) );
        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A3", "prot3" ) ) );

        PersisterHelper.saveOrUpdate( mockInteraction );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().countAll() );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1", "A2", "A3" ).size() );
        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1", "A2" ).size() );
        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1", "B9" ).size() );
    }

    @Test
    public void getByInteractorsPrimaryId_notExact() throws Exception {
        final IntactMockBuilder mockBuilder = getMockBuilder();
        Interaction mockInteraction = mockBuilder.createInteractionRandomBinary();
        mockInteraction.getComponents().clear();

        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A1", "prot1" ) ) );
        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A2", "prot2" ) ) );
        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A3", "prot3" ) ) );

        PersisterHelper.saveOrUpdate( mockInteraction );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().countAll() );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( false, "A1", "A2", "A3" ).size() );
        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( false, "A1", "A2" ).size() );
        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( false, "A1", "B9" ).size() );
    }

    @Test
    public void getByInteractorsPrimaryId_self() throws Exception {
        final IntactMockBuilder mockBuilder = getMockBuilder();
        Interaction mockInteraction = mockBuilder.createInteractionRandomBinary();
        mockInteraction.getComponents().clear();

        mockInteraction.getComponents().add( mockBuilder
                .createComponentNeutral( mockInteraction, mockBuilder.createProtein( "A1", "prot1" ) ) );

        PersisterHelper.saveOrUpdate( mockInteraction );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().countAll() );

        Assert.assertEquals( 0, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1", "A1" ).size() );
        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId( true, "A1" ).size() );
    }

    @Test
    public void getByInteractorsPrimaryId_noComponents() throws Exception {
        final IntactMockBuilder mockBuilder = getMockBuilder();
        Interaction mockInteraction = mockBuilder.createInteractionRandomBinary();
        mockInteraction.getComponents().clear();

        PersisterHelper.saveOrUpdate( mockInteraction );

        Assert.assertEquals( 1, getDaoFactory().getInteractionDao().countAll() );
    }

    @Test
    public void getByInteractorsPrimaryId_14Component() throws Exception {
        String[] all = new String[]{"NP_013618", "NP_010928", "NP_015428", "NP_009512",
                "NP_012533", "NP_011651", "NP_014604", "NP_011769", "NP_014045", "NP_015007",
                "NP_011504", "NP_014800", "NP_011020", "NP_116708"};

        for (int i = 4; i < 14; i++) {
            String[] primaryIds = Arrays.asList(all).subList(0, i).toArray(new String[i]);
            Interaction interaction = getMockBuilder().createInteraction(primaryIds);

            PersisterHelper.saveOrUpdate(interaction);
            Assert.assertEquals(1, getDaoFactory().getInteractionDao().getByInteractorsPrimaryId(true, primaryIds).size());
        }
    }
}