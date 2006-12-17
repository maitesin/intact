/**
 * Copyright 2006 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.app.search.taglib;

import com.sun.facelets.tag.AbstractTagLibrary;
import uk.ac.ebi.intact.app.search.util.SearchUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: IntactSiteTagLibrary.java 7173 2006-12-08 17:08:00Z baranda $
 */
public class SearchTagLibrary extends AbstractTagLibrary
{
    public static final String NAMESPACE = "http://www.ebi.ac.uk/intact/jsf/search";

    public SearchTagLibrary()
    {
        super(NAMESPACE);

        try
        {
            Method[] methods = SearchUtil.class.getMethods();

            for (Method method : methods)
            {
                if (Modifier.isStatic(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers()))
                {
                    this.addFunction(method.getName(), method);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
}
