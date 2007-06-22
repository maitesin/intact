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
package uk.ac.ebi.intact.core.unit.mock;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.persistence.dao.impl.ComponentDaoImpl;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MockIntactContextTest {

    @Before
    public void before() throws Exception {
       MockIntactContext.initMockContext();
    }

    @Test
    public void instanceCreated() throws Exception {
        IntactContext.currentInstanceExists();
    }

    @Test()
    public void dao_Set() throws Exception {
        MockIntactContext.configureMockDaoFactory().setMockComponentDao(new ComponentDaoImpl(null, null));
        IntactContext.getCurrentInstance().getDataContext().getDaoFactory().getComponentDao();
    }

    @Test(expected = IllegalMockDaoException.class)
    public void dao_NotSet() throws Exception {
        IntactContext.getCurrentInstance().getDataContext().getDaoFactory().getComponentDao();

    }
}