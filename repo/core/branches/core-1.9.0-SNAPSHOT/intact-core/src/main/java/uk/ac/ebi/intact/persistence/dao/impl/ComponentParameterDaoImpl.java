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
package uk.ac.ebi.intact.persistence.dao.impl;

import uk.ac.ebi.intact.model.ComponentParameter;
import uk.ac.ebi.intact.model.Range;
import uk.ac.ebi.intact.persistence.dao.ComponentParameterDao;
import uk.ac.ebi.intact.context.IntactSession;

import javax.persistence.EntityManager;

/**
 * TODO comment that class header
 *
 * @author Julie Bourbeillon (julie.bourbeillon@labri.fr)
 * @version $Id$
 * @since TODO specify the maven artifact version
 *        <pre>
 *        05-Dec-2007
 *        </pre>
 */
public class ComponentParameterDaoImpl  extends IntactObjectDaoImpl<ComponentParameter> implements ComponentParameterDao {

      public ComponentParameterDaoImpl( EntityManager entityManager, IntactSession intactSession ) {
        super( ComponentParameter.class, entityManager, intactSession );
    }
}
