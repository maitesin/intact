/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.model;

import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.util.Utilities;

import java.util.*;

/**
 * Represents a crossreference to another database.
 *
 * @author hhe
 */
public class Xref extends BasicObject {

    ///////////////////////////////////////
    //attributes

    //private String qualifierAc;
    //protected String databaseAc;
    protected String parentAc;

    //attributes used for mapping BasicObjects - project synchron
    protected String cvXrefQualifierAc;
    protected String cvDatabaseAc;


    /**
     * Primary identifier of the database referred to.
     */
    protected String primaryId;

    /**
     * Secondary identifier of the database. This will usually be
     * a meaningful name, for example a domain name.
     */
    protected String secondaryId;
    /**
     * The release number of the external database from which the object
     * has been updated.
     */
    protected String dbRelease;

    ///////////////////////////////////////
    // constructors
    public Xref() {
        super();
    }


    public Xref (Institution anOwner,
                 CvDatabase aDatabase,
                 String aPrimaryId,
                 String aSecondaryId,
                 String aDatabaseRelease) throws IntactException {

        super();
        this.owner = anOwner;
        this.cvDatabase = aDatabase;
        this.primaryId = aPrimaryId;
        this.secondaryId = aSecondaryId;
        this.dbRelease = aDatabaseRelease;
    }

    ///////////////////////////////////////
    // associations

    /**
     *
     */
    public CvXrefQualifier cvXrefQualifier;

    /**
     *
     */
    public CvDatabase cvDatabase;


    ///////////////////////////////////////
    //access methods for attributes

    public String getPrimaryId() {
        return primaryId;
    }
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }
    public String getSecondaryId() {
        return secondaryId;
    }
    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }
    public String getDbRelease() {
        return dbRelease;
    }
    public void setDbRelease(String dbRelease) {
        this.dbRelease = dbRelease;
    }
    public String getParentAc() {
        return parentAc;
    }
    public void setParentAc(String parentAc) {
        this.parentAc = parentAc;
    }

    ///////////////////////////////////////
    // access methods for associations

    public CvXrefQualifier getCvXrefQualifier() {
        return cvXrefQualifier;
    }

    public void setCvXrefQualifier(CvXrefQualifier cvXrefQualifier) {
        this.cvXrefQualifier = cvXrefQualifier;
    }
    public CvDatabase getCvDatabase() {
        return cvDatabase;
    }

    public void setCvDatabase(CvDatabase cvDatabase) {
        this.cvDatabase = cvDatabase;
    }

    //attributes used for mapping BasicObjects - project synchron
    public String getCvXrefQualifierAc() {
        return cvXrefQualifierAc;
    }
    public void setCvXrefQualifierAc(String ac) {
        this.cvXrefQualifierAc = ac;
    }

    public String getCvDatabaseAc() {
        return cvDatabaseAc;
    }
    public void setCvDatabaseAc(String ac) {
        this.cvDatabaseAc = ac;
    }


    ///////////////////////////////////////
    // instance methods

    /** Returns true if the "important" attributes are equal.
     */
    public boolean equals(Object obj){

        return (super.equals(obj) &&
                Utilities.equals(this.cvDatabase, ((Xref)obj).getCvDatabase()) &&
                Utilities.equals(this.primaryId, ((Xref)obj).getPrimaryId()));
    }

    /** This class overwrites equals. To ensure proper functioning of HashTable,
     * hashCode must be overwritten, too.
     * @return  hash code of the object.
     */
    public int hashCode(){

        int code = super.hashCode();

        if (null != cvDatabase) code += cvDatabase.hashCode();
        if (null != primaryId) code += primaryId.hashCode();

        return code;
    }

    public String toString(){
        return " Xref: " + this.getAc()
                + "; Owner: " + this.getOwner().getShortLabel()
                + "; DB: " + getCvDatabase().getShortLabel()
                + "; PrimaryId: "+ this.primaryId
                + "\n";
    }
} // end Xref




