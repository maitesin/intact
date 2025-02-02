/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * This is the top level class for all intact model object.
 *
 * @author intact team
 * @version $Id$
 */
@MappedSuperclass
public abstract class IntactObjectImpl extends AbstractAuditable implements IntactObject, Cloneable {

    protected static final String NEW_LINE = System.getProperty( "line.separator" );

    /**
     * The unique accession number of an object. This is defined as protected to allow concrete subclasses to generate
     * copies if required.
     */
    protected String ac;

    private boolean deprecated;

    public IntactObjectImpl() {
    }

    ///////////////////////////////////////
    //access methods for attributes

    @Id
    @GeneratedValue( generator = "intact-id-generator" )
    @GenericGenerator( name = "intact-id-generator", strategy = "uk.ac.ebi.intact.model.IntactIdGenerator" )
    @Column( length = 30 )
    public String getAc() {
        return ac;
    }

    /**
     * This method should not be used by applications, as the AC is a primary key which is auto-generated. If we move to
     * an application server it may then be needed.
     *
     * @param ac
     */
    public void setAc( String ac ) {
        this.ac = ac;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated( boolean deprecated ) {
        this.deprecated = deprecated;
    }


    /**
     * Makes a clone of this intact object.
     *
     * @return a cloned version of the current instance.
     *
     * @throws CloneNotSupportedException to indicate that an instance cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        IntactObjectImpl copy = ( IntactObjectImpl ) super.clone();
        // Reset the AC.
        copy.setAc( null );

        // Sets the dates to the current date.
        copy.setCreated( null );
        copy.setUpdated( null );

        return copy;
    }

    ///////////////////////////////////////
    // instance methods
    @Override
    public String toString() {
        return this.ac;
    }
} // end IntactObject

