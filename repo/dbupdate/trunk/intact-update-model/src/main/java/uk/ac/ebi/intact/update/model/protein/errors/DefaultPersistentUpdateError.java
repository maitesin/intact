package uk.ac.ebi.intact.update.model.protein.errors;

import uk.ac.ebi.intact.dbupdate.prot.errors.ProteinUpdateError;
import uk.ac.ebi.intact.dbupdate.prot.errors.UpdateError;
import uk.ac.ebi.intact.update.model.HibernateUpdatePersistentImpl;
import uk.ac.ebi.intact.update.model.protein.ProteinUpdateProcess;

import javax.persistence.*;

/**
 * Abstract class for persistent update errors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/08/11</pre>
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType= DiscriminatorType.STRING)
@Table(name = "ia_protein_update_error")
public abstract class DefaultPersistentUpdateError extends HibernateUpdatePersistentImpl implements ProteinUpdateError {

    private ProteinUpdateProcess parent;
    private UpdateError errorLabel;
    protected String reason;

    public DefaultPersistentUpdateError(){
        super();
        this.parent = null;
        this.errorLabel = null;
        this.reason = null;
    }

    public DefaultPersistentUpdateError(ProteinUpdateProcess parent, UpdateError errorLabel, String reason){
        super();
        this.parent = parent;
        this.errorLabel = errorLabel;
        this.reason = reason;
    }

    @ManyToOne( targetEntity = ProteinUpdateProcess.class )
    @JoinColumn(name="parent_ac")
    public ProteinUpdateProcess getParent() {
        return parent;
    }

    public void setParent(ProteinUpdateProcess parent) {
        this.parent = parent;
    }

    @Override
    @Column(name = "label", nullable = false)
    @Enumerated(EnumType.STRING)
    public UpdateError getErrorLabel() {
        return this.errorLabel;
    }

    @Override
    @Column(name = "message")
    @Enumerated(EnumType.STRING)
    public String getErrorMessage() {
        return this.reason;
    }

    public void setErrorLabel(UpdateError errorLabel) {
        this.errorLabel = errorLabel;
    }

    public void setErrorMessage(String reason) {
        this.reason = reason;
    }


    @Override
    public boolean equals( Object o ) {
        if ( !super.equals(o) ) {
            return false;
        }

        final DefaultPersistentUpdateError process = (DefaultPersistentUpdateError) o;

        if ( errorLabel != null ) {
            if (!errorLabel.equals( process.getErrorLabel())){
                return false;
            }
        }
        else if (process.getErrorLabel()!= null){
            return false;
        }

        if ( reason != null ) {
            if (!reason.equals( process.getErrorMessage())){
                return false;
            }
        }
        else if (process.getErrorMessage()!= null){
            return false;
        }

        return true;
    }

    /**
     * This class overwrites equals. To ensure proper functioning of HashTable,
     * hashCode must be overwritten, too.
     *
     * @return hash code of the object.
     */
    @Override
    public int hashCode() {

        int code = 29;

        code = 29 * code + super.hashCode();

        if ( errorLabel != null ) {
            code = 29 * code + errorLabel.hashCode();
        }

        if ( reason != null ) {
            code = 29 * code + reason.hashCode();
        }

        return code;
    }

    @Override
    public boolean isIdenticalTo(Object o){

        if (!super.isIdenticalTo(o)){
            return false;
        }

        final DefaultPersistentUpdateError process = (DefaultPersistentUpdateError) o;

        if ( errorLabel != null ) {
            if (!errorLabel.equals( process.getErrorLabel())){
                return false;
            }
        }
        else if (process.getErrorLabel()!= null){
            return false;
        }

        if ( reason != null ) {
            if (!reason.equals( process.getErrorMessage())){
                return false;
            }
        }
        else if (process.getErrorMessage()!= null){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("Update error : [ label = " + errorLabel != null ? errorLabel.toString() : "none" + ", message = "+reason != null ? reason : "none"+"] \n");

        return buffer.toString();
    }
}
