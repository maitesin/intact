package uk.ac.ebi.intact.update.model.protein.mapping.actions;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.intact.update.model.protein.mapping.results.PICRCrossReferences;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * This report aims at storing the information and results of a query on PICR
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01-Apr-2010</pre>
 */
@Entity
@DiscriminatorValue("PICRReport")
public class PICRReport extends MappingReport{

    /**
     * the list of cross references that PICR could collect
     */
    private Set<PICRCrossReferences> crossReferences = new HashSet<PICRCrossReferences>();

    /**
     * Create a new PICRReport
     * @param name : name of the action
     */
    public PICRReport(ActionName name) {
        super(name);
    }

    /**
     *
     * @return the cross references
     */
    @OneToMany(mappedBy = "picrReport", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH} )
    public Set<PICRCrossReferences> getCrossReferences(){
        return this.crossReferences;
    }

    /**
     * add a new cross reference
     * @param databaseName : database name
     * @param accession : accessions in the database
     */
    public void addCrossReference(String databaseName, String accession){
        boolean isADatabaseNamePresent = false;

        for (PICRCrossReferences c : this.crossReferences){
            if (c.getDatabase() != null){
                if (c.getDatabase().equalsIgnoreCase(databaseName)){
                    isADatabaseNamePresent = true;
                    c.addAccession(accession);
                }
            }
        }

        if (!isADatabaseNamePresent){
            PICRCrossReferences picrRefs = new PICRCrossReferences();
            picrRefs.setPicrReport(this);
            picrRefs.setDatabase(databaseName);
            picrRefs.addAccession(accession);
        }
    }

    /**
     * Add a new PICRCrossReference instance to the list of references
     * @param refs : the PICRCrossReference instance to add
     */
    public void addPICRCrossReference(PICRCrossReferences refs){
         if (refs != null){
             refs.setPicrReport(this);
            this.crossReferences.add(refs);   
         }
    }

    /**
     * Set the PICR cross references
     * @param crossReferences : set containing the PICR cross references
     */
    public void setCrossReferences(Set<PICRCrossReferences> crossReferences) {
        this.crossReferences = crossReferences;
    }

    @Override
    public boolean equals( Object o ) {
        return super.equals(o);
    }

    /**
     * This class overwrites equals. To ensure proper functioning of HashTable,
     * hashCode must be overwritten, too.
     *
     * @return hash code of the object.
     */
    @Override
    public int hashCode() {

        return super.hashCode();
    }

    @Override
    public boolean isIdenticalTo(Object o){

        if (!super.isIdenticalTo(o)){
            return false;
        }

        final PICRReport report = ( PICRReport ) o;

        return CollectionUtils.isEqualCollection(this.crossReferences, report.getCrossReferences());
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.toString() + "\n");

        if (!crossReferences.isEmpty()){
            buffer.append("PICR references : [");

            for (PICRCrossReferences ref : crossReferences) {
                buffer.append(ref.toString() + " ; ");
            }

            buffer.append("\n");
        }

        return buffer.toString();
    }
}
