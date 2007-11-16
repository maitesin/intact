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
package uk.ac.ebi.intact.psimitab.converters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.model.CrossReferenceImpl;
import uk.ac.ebi.intact.model.CvObject;
import uk.ac.ebi.intact.model.CvObjectXref;
import uk.ac.ebi.intact.model.util.CvObjectUtils;

import java.lang.reflect.Constructor;

/**
 * CvObject Converter.
 *
 * @author Nadin Neuhauser
 * @version $Id$
 * @since 2.0.0
 */
public class CvObjectConverter<T extends CrossReferenceImpl, O extends CvObject> {

    public static final Log logger = LogFactory.getLog( CvObjectConverter.class );

    public T toMitab( Class<T> clazz, O cvObject ) throws Intact2TabException {
        if ( cvObject == null ) {
            throw new IllegalArgumentException( "CvObject must not be null. ");
        }

        if ( clazz == null ) {
            throw new IllegalArgumentException( "Class must not be null. ");
        }

        String text = cvObject.getShortLabel();
        CvObjectXref idxref = CvObjectUtils.getPsiMiIdentityXref( cvObject );
        String db = idxref.getPrimaryId().split( ":" )[0];
        String id = idxref.getPrimaryId().split( ":" )[1];

        try {
            Constructor<T> constructor = clazz.getConstructor( String.class, String.class, String.class );

            return constructor.newInstance( db, id, text );
        } catch ( Exception e ) {
            throw new Intact2TabException( "An error occured while converting " + clazz.getSimpleName() + ": " + text, e );
        }

    }

    public T toMitab( O cvObject ) throws Intact2TabException {
        return toMitab( ( Class<T> ) CrossReferenceImpl.class, cvObject );
    }

    public O fromMitab( Class<O> clazz, T reference ) {
        throw new UnsupportedOperationException();
    }
}
