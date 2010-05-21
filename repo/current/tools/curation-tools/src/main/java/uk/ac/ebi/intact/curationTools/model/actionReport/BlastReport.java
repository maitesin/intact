package uk.ac.ebi.intact.curationTools.model.actionReport;

import org.hibernate.annotations.Cascade;
import uk.ac.ebi.intact.curationTools.model.results.BlastResults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This specific report aims at storing the results of a BLAST
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01-Apr-2010</pre>
 */
@Entity
@DiscriminatorValue("uk.ac.ebi.intact.curationTools.model.actionReport.BlastReport")
public class BlastReport extends ActionReport{

    /**
     * The list of BLASTProteins
     */
    protected Set<BlastResults> listOfProteins = new HashSet<BlastResults>();

    /**
     * The sequence used for the blast
     */
    protected String querySequence;

    /**
     * Create a new BlastReport
     * @param name : the name of the action
     */
    public BlastReport(ActionName name){
         super(name);
        this.querySequence = null;
    }

    /**
     *
     * @return the list of Blast results
     */
    @OneToMany(mappedBy = "blastReport", cascade = CascadeType.ALL)
    @Cascade( value = org.hibernate.annotations.CascadeType.SAVE_UPDATE )
    public Set<BlastResults> getBlastMatchingProteins(){
        return this.listOfProteins;
    }

    public void setBlastMatchingProteins(Set<BlastResults> blastResults){
        this.listOfProteins = blastResults;
    }

    /**
     *  add a blast protein
     * @param prot : new blast result
     */
    public void addBlastMatchingProtein(BlastResults prot){
        prot.setBlastReport(this);
        this.listOfProteins.add(prot);
    }

    /**
     *
     * @return the sequence used for the blast
     */
    @Transient
    public String getQuerySequence() {
        return querySequence;
    }

    /**
     * set the sequence used for the blast
     * @param querySequence : the sequence
     */
    public void setQuerySequence(String querySequence) {
        this.querySequence = querySequence;
    }
}
