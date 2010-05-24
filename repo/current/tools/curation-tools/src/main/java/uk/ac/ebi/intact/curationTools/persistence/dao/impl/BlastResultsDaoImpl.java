package uk.ac.ebi.intact.curationTools.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.curationTools.model.results.BlastResults;
import uk.ac.ebi.intact.curationTools.persistence.dao.BlastResultsDao;

import javax.persistence.Query;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20-May-2010</pre>
 */
@Repository
@Transactional(readOnly = true)
public class BlastResultsDaoImpl extends UpdateBaseDaoImpl<BlastResults> implements BlastResultsDao{

    public BlastResultsDaoImpl() {
        super(BlastResults.class);
    }

    public List<BlastResults> getResultsByIdentitySuperior(float identity) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults as br where br.identity >= :identity" );
        query.setParameter( "identity", identity);

        return query.getResultList();
    }

    public List<BlastResults> getResultsByActionIdAndIdentitySuperior(float identity, long actionId) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br join br.blastReport as res where br.identity >= :identity and res.id = :id" );
        query.setParameter( "identity", identity);
        query.setParameter( "id", actionId);

        return query.getResultList();
    }

    public List<BlastResults> getAllSwissprotRemappingResults() {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br where br.tremblAccession <> null" );

        return query.getResultList();
    }

    public List<BlastResults> getAllSwissprotRemappingResultsFor(long actionId) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br join br.blastReport as res where br.tremblAccession <> null and res.id = :id" );
        query.setParameter( "id", actionId);

        return query.getResultList();
    }

    public List<BlastResults> getSwissprotRemappingResultsByTremblAc(String tremblAc) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br where br.tremblAccession = :tremblAc" );
        query.setParameter( "tremblAc", tremblAc);

        return query.getResultList();
    }

    public List<BlastResults> getBlastResultsByProteinAc(String proteinAc) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br join br.blastReport as blast join blast.updateResult as ur where ur.intactAccession = :ac" );
        query.setParameter( "ac", proteinAc);

        return query.getResultList();
    }

    public BlastResults getResultsById(long id) {
        final Query query = getEntityManager().createQuery( "select br from BlastResults br where br.id = :id" );
        query.setParameter( "id", id);

        if (query.getResultList().isEmpty()){
             return null;
        }

        return (BlastResults) query.getResultList().iterator().next();
    }
}
