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
package uk.ac.ebi.intact.psixml.tools.generator.metadata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id:ModelClassMetadata.java 8272 2007-04-25 10:20:12Z baranda $
 */
public class ModelClassMetadata {

    private Class modelClass;
    private List<FieldMetadata> collections;
    private Set<Class> importedClasses;
    private List<FieldMetadata> individuals;

    public ModelClassMetadata(Class modelClass) {
        this.modelClass = modelClass;

        this.collections = new ArrayList<FieldMetadata>();
        this.individuals = new ArrayList<FieldMetadata>();
        this.importedClasses = new HashSet<Class>();

        addImportedClass(modelClass);
    }

    public Class getModelClass() {
        return modelClass;
    }

    public List<FieldMetadata> getCollections() {
        return collections;
    }

    public void setCollections(List<FieldMetadata> collections) {
        this.collections = collections;

        for (FieldMetadata col : collections) {
            addImportedClass(col.getType());
        }
    }

    public List<FieldMetadata> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<FieldMetadata> individuals) {
        this.individuals = individuals;

        for (FieldMetadata ind : individuals) {
            addImportedClass(ind.getType());
        }
    }

    public Set<Class> getImportedClasses() {
        return importedClasses;
    }

    public boolean addImportedClass(Class o) {
        return importedClasses.add(o);
    }
}