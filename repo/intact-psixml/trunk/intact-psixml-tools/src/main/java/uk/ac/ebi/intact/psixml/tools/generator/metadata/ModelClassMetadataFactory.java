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

import uk.ac.ebi.intact.psixml.tools.generator.SourceGeneratorHelper;
import uk.ac.ebi.intact.psixml.tools.generator.metadata.util.PsiReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Creates instances of ModelClassMetadata
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id:ModelClassMetadataFactory.java 8272 2007-04-25 10:20:12Z baranda $
 */
public class ModelClassMetadataFactory {

    public static ModelClassMetadata createModelClassMetadata(SourceGeneratorHelper helper, Class modelClass) {
        ModelClassMetadata mcm = new ModelClassMetadata(modelClass);
        mcm.setBooleansWithMetadata(PsiReflectionUtils.booleanFieldsFrom(mcm));
        mcm.setIndividuals(PsiReflectionUtils.individualsFrom(helper, mcm));
        mcm.setCollections(PsiReflectionUtils.collectionsFrom(helper, mcm));

        List<Method> extensionMethods = PsiReflectionUtils.discoverPsiExtensionMethodsForClass(modelClass);
        for (Method extMethos : extensionMethods) {
            checkPsiExtensionMethod(extMethos, modelClass);
        }
        mcm.setExtensions(extensionMethods);

        return mcm;
    }

    private static void checkPsiExtensionMethod(Method method, Class modelClass) {
        if (!method.getReturnType().toString().equals("void")) {
            throw new RuntimeException("PsiExtensionMethod must return void: " + method);
        }

        if (method.getParameterTypes().length != 1) {
            throw new RuntimeException("PsiExtensionMethod must accept only one parameter: " + method);
        }

        Class paramType = method.getParameterTypes()[0];

        if (!paramType.isAssignableFrom(modelClass)) {
            throw new RuntimeException("PsiExtensionMethod parameter must be assignable from '" + modelClass.getName() + "': " + method);
        }
    }

}