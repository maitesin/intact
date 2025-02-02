package uk.ac.ebi.intact.jami.dao.impl;

import org.springframework.stereotype.Repository;
import psidev.psi.mi.jami.model.CooperativityEvidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.jami.context.DefaultSynchronizerContext;
import uk.ac.ebi.intact.jami.context.SynchronizerContext;
import uk.ac.ebi.intact.jami.dao.CooperativityEvidenceDao;
import uk.ac.ebi.intact.jami.model.extension.AbstractIntactCooperativeEffect;
import uk.ac.ebi.intact.jami.model.extension.IntactCooperativityEvidence;
import uk.ac.ebi.intact.jami.synchronizer.IntactDbSynchronizer;
import uk.ac.ebi.intact.jami.synchronizer.impl.CooperativityEvidenceSynchronizer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Implementation of cooperativity evidence dao
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/14</pre>
 */
public class CooperativityEvidenceDaoImpl extends AbstractIntactBaseDao<CooperativityEvidence, IntactCooperativityEvidence> implements CooperativityEvidenceDao {

    public CooperativityEvidenceDaoImpl(EntityManager entityManager, SynchronizerContext context) {
        super(IntactCooperativityEvidence.class, entityManager, context);
    }

    public Collection<IntactCooperativityEvidence> getByMethod(String methodName, String methodMI) {
        Query query;
        if (methodMI != null){
            query = getEntityManager().createQuery("select distinct c from IntactCooperativityEvidence c " +
                    "join c.evidenceMethods as t " +
                    "join t.persistentXrefs as x " +
                    "join x.database as d " +
                    "join x.qualifier as q " +
                    "where (q.shortName = :identity or q.shortName = :secondaryAc) " +
                    "and d.shortName = :psimi " +
                    "and x.id = :mi");
            query.setParameter("identity", Xref.IDENTITY);
            query.setParameter("secondaryAc", Xref.SECONDARY);
            query.setParameter("psimi", CvTerm.PSI_MI);
            query.setParameter("mi", methodMI);
        }
        else{
            query = getEntityManager().createQuery("select distinct c from IntactCooperativityEvidence c " +
                    "join c.evidenceMethods as t " +
                    "where t.shortName = :methodName");
            query.setParameter("methodName", methodName);
        }
        return query.getResultList();
    }

    public Collection<IntactCooperativityEvidence> getByPublicationPubmed(String pubmed) {
        Query query = getEntityManager().createQuery("select distinct c from IntactCooperativityEvidence c " +
                "join c.publication as p " +
                "join p.persistentXrefs as x " +
                "join x.database as d " +
                "join x.qualifier as q " +
                "where (q.shortName = :identity or q.shortName = :secondaryAc or q.shortName = :primary) " +
                "and d.shortName = :pubmed " +
                "and x.id = :identifier");
        query.setParameter("identity", Xref.IDENTITY);
        query.setParameter("secondaryAc", Xref.SECONDARY);
        query.setParameter("primary", Xref.PRIMARY);
        query.setParameter("pubmed", Xref.PUBMED);
        query.setParameter("identifier", pubmed);
        return query.getResultList();
    }

    public Collection<IntactCooperativityEvidence> getByPublicationDoi(String doi) {
        Query query = getEntityManager().createQuery("select distinct c from IntactCooperativityEvidence c " +
                "join c.publication as p " +
                "join p.persistentXrefs as x " +
                "join x.database as d " +
                "join x.qualifier as q " +
                "where (q.shortName = :identity or q.shortName = :secondaryAc or q.shortName = :primary) " +
                "and d.shortName = :doi " +
                "and x.id = :identifier");
        query.setParameter("identity", Xref.IDENTITY);
        query.setParameter("secondaryAc", Xref.SECONDARY);
        query.setParameter("primary", Xref.PRIMARY);
        query.setParameter("doi", Xref.DOI);
        query.setParameter("identifier", doi);
        return query.getResultList();
    }

    public Collection<IntactCooperativityEvidence> getByPublicationAc(String ac) {
        Query query = getEntityManager().createQuery("select c from IntactCooperativityEvidence c " +
                "join c.publication as p " +
                "where p.ac = :pubAc");
        query.setParameter("pubAc", ac);
        return query.getResultList();
    }

    @Override
    public IntactDbSynchronizer<CooperativityEvidence, IntactCooperativityEvidence> getDbSynchronizer() {
        return getSynchronizerContext().getCooperativityEvidenceSynchronizer();
    }
}