package uk.ac.ebi.intact.curationTools.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionName;
import uk.ac.ebi.intact.curationTools.model.actionReport.status.StatusLabel;
import uk.ac.ebi.intact.curationTools.model.results.UpdateResults;
import uk.ac.ebi.intact.curationTools.persistence.dao.UpdateResultsDao;

import javax.persistence.Query;
import java.util.List;

/**
 * The basic implementation of UpdateResultsDao
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21-May-2010</pre>
 */
@Repository
@Transactional(readOnly = true)
public class UpdateResultsDaoImpl extends UpdateBaseDaoImpl<UpdateResults> implements UpdateResultsDao {
    /**
     * Create a new UpdateResultsDaoImpl
     */
    public UpdateResultsDaoImpl() {
        super(UpdateResults.class);
    }

    /**
     *
     * @param id
     * @return
     */
    public UpdateResults getUpdateResultsWithId(long id) {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u where u.id = :id" );
        query.setParameter( "id", id);

        if (query.getResultList().isEmpty()){
            return null;
        }
        return (UpdateResults) query.getResultList().iterator().next();
    }

    /**
     *
     * @param proteinAc
     * @return
     */
    public UpdateResults getUpdateResultsForProteinAc(String proteinAc) {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u where u.intactAccession = :proteinAc" );
        query.setParameter( "proteinAc", proteinAc);

        if (query.getResultList().isEmpty()){
            return null;
        }
        return (UpdateResults) query.getResultList().iterator().next();
    }

    /**
     *
     * @param name
     * @return
     */
    public List<UpdateResults> getResultsContainingAction(ActionName name) {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.name = :name" );
        query.setParameter( "name", name);

        return query.getResultList();
    }

    /**
     *
     * @param label
     * @return
     */
    public List<UpdateResults> getResultsContainingActionWithLabel(StatusLabel label) {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.statusLabel = :status" );
        query.setParameter( "status", label);

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getUpdateResultsWithSwissprotRemapping() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.name = :name" );
        query.setParameter( "name", ActionName.BLAST_Swissprot_Remapping);

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getSuccessfulUpdateResults() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u where u.finalUniprotId <> null" );

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getUpdateResultsToBeReviewedByACurator() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.statusLabel = :status" );
        query.setParameter( "status", StatusLabel.TO_BE_REVIEWED);

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getProteinNotUpdatedBecauseNoSequenceAndNoIdentityXrefs() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.statusLabel = :status and a.name = :name" );
        query.setParameter( "status", StatusLabel.FAILED);
        query.setParameter( "name", ActionName.update_Checking);

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getUnsuccessfulUpdateResults() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where u.finalUniprotId = null and a.statusLabel = :status and a.name <> :name" );
        query.setParameter( "status", StatusLabel.FAILED);
        query.setParameter( "name", ActionName.update_Checking);

        return query.getResultList();
    }

    /**
     *
     * @return
     */
    public List<UpdateResults> getUpdateResultsWithConflictBetweenSequenceAndIdentityXRefs() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.statusLabel = :status and a.name = :name" );
        query.setParameter( "status", StatusLabel.TO_BE_REVIEWED);
        query.setParameter( "name", ActionName.update_Checking);

        return query.getResultList();
    }

    /**
     * 
     * @return
     */
    public List<UpdateResults> getUpdateResultsWithConflictBetweenSwissprotSequenceAndFeatureRanges() {
        final Query query = getEntityManager().createQuery( "select u from UpdateResults as u join u.listOfActions as a where a.statusLabel = :status and a.name = :name" );
        query.setParameter( "status", StatusLabel.TO_BE_REVIEWED);
        query.setParameter( "name", ActionName.feature_range_checking);

        return query.getResultList();
    }
}
