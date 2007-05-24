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
package uk.ac.ebi.intact.psixml.persister.shared;

import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.psixml.persister.PersisterException;
import uk.ac.ebi.intact.psixml.persister.util.PersisterConfig;

import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PersisterHelper {

    private PersisterHelper() {
    }

    public static void syncAnnotatedObject(AnnotatedObject intactObject, IntactContext context) throws PersisterException {
        CvPersister cvPersister = new CvPersister(context, PersisterConfig.isDryRun(context));

        for (Xref xref : (Collection<Xref>) intactObject.getXrefs()) {
            CvDatabase cvDb = (CvDatabase) cvPersister.saveOrUpdate(xref.getCvDatabase());
            xref.setCvDatabase(cvDb);

            if (xref.getCvXrefQualifier() != null) {
                CvXrefQualifier cvXrefQual = (CvXrefQualifier) cvPersister.saveOrUpdate(xref.getCvXrefQualifier());
                xref.setCvXrefQualifier(cvXrefQual);
            }
        }
        for (Alias alias : (Collection<Alias>) intactObject.getAliases()) {
            CvAliasType cvAliasType = (CvAliasType) cvPersister.saveOrUpdate(alias.getCvAliasType());
            alias.setCvAliasType(cvAliasType);
        }
        for (Annotation annotation : (Collection<Annotation>) intactObject.getAnnotations()) {
            CvTopic cvTopic = (CvTopic) cvPersister.saveOrUpdate(annotation.getCvTopic());
            annotation.setCvTopic(cvTopic);
        }
    }

}