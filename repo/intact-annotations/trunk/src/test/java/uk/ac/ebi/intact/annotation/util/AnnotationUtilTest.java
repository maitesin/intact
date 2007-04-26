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
package uk.ac.ebi.intact.annotation.util;

import junit.framework.Assert;
import org.junit.Test;
import uk.ac.ebi.intact.annotation.EditorTopic;

import java.io.File;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class AnnotationUtilTest {

    @Test
    public void getClassesWithAnnotationFromDir() throws Exception {
        File dir = new File(AnnotationUtilTest.class.getResource("/").getFile());
        List<Class> classes = AnnotationUtil.getClassesWithAnnotationFromDir(EditorTopic.class, dir);

        Assert.assertEquals(1, classes.size());
    }

    @Test
    public void getClassesWithAnnotationFromClasspathDirs() throws Exception {
        List<Class> classes = AnnotationUtil.getClassesWithAnnotationFromClasspathDirs(EditorTopic.class);

        Assert.assertEquals(1, classes.size());
    }

    @Test
    public void getClassesWithAnnotationFromClasspath() throws Exception {
        List<Class> classes = AnnotationUtil.getClassesWithAnnotationFromClasspath(EditorTopic.class);

        Assert.assertEquals(1, classes.size());
    }
}