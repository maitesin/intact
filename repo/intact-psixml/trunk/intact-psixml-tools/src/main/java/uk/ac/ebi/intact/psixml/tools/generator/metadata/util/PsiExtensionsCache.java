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
package uk.ac.ebi.intact.psixml.tools.generator.metadata.util;

import uk.ac.ebi.intact.annotation.util.AnnotationUtil;
import uk.ac.ebi.intact.psixml.tools.extension.annotation.PsiExtension;
import uk.ac.ebi.intact.psixml.tools.generator.SourceGeneratorContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class used to maintain the found psiExtension classes in cache
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PsiExtensionsCache {

    private static ThreadLocal<List<Class>> extensionsLocal = null;

    public static List<Class> getPsiExtensionClasses(SourceGeneratorContext context) {
        if (extensionsLocal == null) {
            List<Class> psiExtensionClasses = AnnotationUtil.getClassesWithAnnotationFromClasspathDirs(PsiExtension.class);

            ClassLoader classLoader = context.getDependencyClassLoader();
            File[] jars = context.getDependencyJars();


            for (File jar : jars) {
                try {
                    List<Class> psiExtClassesFromJars = AnnotationUtil.getClassesWithAnnotationFromJar(PsiExtension.class, jar.toString(), classLoader);
                    psiExtensionClasses.addAll(psiExtClassesFromJars);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            extensionsLocal = new ThreadLocal<List<Class>>();
            extensionsLocal.set(psiExtensionClasses);
        }

        return extensionsLocal.get();
    }
}