/*
 * Copyright 2006 The European Bioinformatics Institute.
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
package uk.ac.ebi.intact.model.util;

import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.CvDatabase;
import uk.ac.ebi.intact.model.CvXrefQualifier;
import uk.ac.ebi.intact.model.Xref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Util methods for annotatedObject.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14-Aug-2006</pre>
 */
public class AnnotatedObjectUtils {

    private AnnotatedObjectUtils() {
    }

    /**
     * Trims a shortlabel if it is too long to be inserted in the database.
     *
     * @return the shortlabel.
     */
    public static String prepareShortLabel( String shortLabel ) {
        boolean modified = false;

        if ( shortLabel == null ) {

            throw new NullPointerException( "Must define a non null short label" );

        } else {
            // delete leading and trailing spaces.
            shortLabel = shortLabel.trim();

            if ( "".equals( shortLabel ) ) {
                throw new IllegalArgumentException(
                        "Must define a non empty short label" );
            }

            if ( shortLabel.length() >= AnnotatedObject.MAX_SHORT_LABEL_LEN ) {
                shortLabel = shortLabel.substring( 0, AnnotatedObject.MAX_SHORT_LABEL_LEN );
                modified = true;
            }
        }

        return shortLabel;
    }

    /**
     * Search for all Xrefs having Xref with the given CvDatabase.
     *
     * @param ao the non null AnnotatedObject to search on.
     * @param db the non null CvDatabase filter.
     *
     * @return a non null Collection of Xref, may be empty.
     */
    public static Collection<Xref> searchXrefs( AnnotatedObject ao, CvDatabase db ) {

        if ( ao == null ) {
            throw new NullPointerException( "AnnotatedObject must not be null." );
        }
        if ( db == null ) {
            throw new NullPointerException( "CvDatabase must not be null." );
        }

        Collection<Xref> xrefs = new ArrayList<Xref>( ao.getXrefs().size() );

        for ( Iterator<Xref> iterator = ao.getXrefs().iterator(); iterator.hasNext(); ) {
            Xref xref = iterator.next();
            if ( db.equals( xref.getCvDatabase() ) ) {
                xrefs.add( xref );
            }
        }

        return xrefs;
    }

    /**
     * Search for all Xrefs having Xref with both the given CvDatabase and CvXrefQualifier.
     *
     * @param ao the non null AnnotatedObject to search on.
     * @param db the non null CvDatabase filter.
     * @param qu the non null CvXrefQualifier filter.
     *
     * @return a non null Collection of Xref, may be empty.
     */
    public static Collection<Xref> searchXrefs( AnnotatedObject ao, CvDatabase db, CvXrefQualifier qu ) {

        if ( ao == null ) {
            throw new NullPointerException( "AnnotatedObject must not be null." );
        }
        if ( db == null ) {
            throw new NullPointerException( "CvDatabase must not be null." );
        }
        if ( qu == null ) {
            throw new NullPointerException( "CvXrefQualifier must not be null." );
        }

        Collection<Xref> xrefs = new ArrayList<Xref>( ao.getXrefs().size() );

        for ( Iterator<Xref> iterator = ao.getXrefs().iterator(); iterator.hasNext(); ) {
            Xref xref = iterator.next();
            if ( db.equals( xref.getCvDatabase() ) && qu.equals( xref.getCvXrefQualifier() ) ) {
                xrefs.add( xref );
            }
        }

        return xrefs;
    }

    /**
     * Search for all Xrefs having Xref with the given CvXrefQualifier.
     *
     * @param ao the non null AnnotatedObject to search on.
     * @param qu the non null CvXrefQualifier filter.
     *
     * @return a non null Collection of Xref, may be empty.
     */
    public static Collection<Xref> searchXrefs( AnnotatedObject ao, CvXrefQualifier qu ) {

        if ( ao == null ) {
            throw new NullPointerException( "AnnotatedObject must not be null." );
        }
        if ( qu == null ) {
            throw new NullPointerException( "CvXrefQualifier must not be null." );
        }

        Collection<Xref> xrefs = new ArrayList<Xref>( ao.getXrefs().size() );

        for ( Iterator<Xref> iterator = ao.getXrefs().iterator(); iterator.hasNext(); ) {
            Xref xref = iterator.next();
            if ( qu.equals( xref.getCvXrefQualifier() ) ) {
                xrefs.add( xref );
            }
        }

        return xrefs;
    }
}
