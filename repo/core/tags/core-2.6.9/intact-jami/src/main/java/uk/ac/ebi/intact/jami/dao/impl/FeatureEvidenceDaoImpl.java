package uk.ac.ebi.intact.jami.dao.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.jami.context.DefaultSynchronizerContext;
import uk.ac.ebi.intact.jami.context.SynchronizerContext;
import uk.ac.ebi.intact.jami.dao.FeatureEvidenceDao;
import uk.ac.ebi.intact.jami.model.extension.AbstractIntactFeature;
import uk.ac.ebi.intact.jami.model.extension.IntactFeatureEvidence;
import uk.ac.ebi.intact.jami.synchronizer.IntactDbSynchronizer;
import uk.ac.ebi.intact.jami.synchronizer.impl.FeatureEvidenceSynchronizer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Implementation for feature evidence dao
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27/01/14</pre>
 */

public class FeatureEvidenceDaoImpl extends FeatureDaoImpl<FeatureEvidence, IntactFeatureEvidence> implements FeatureEvidenceDao{

    public FeatureEvidenceDaoImpl(EntityManager entityManager, SynchronizerContext context) {
        super(IntactFeatureEvidence.class, entityManager, context);
    }

    public Collection<IntactFeatureEvidence> getByFeatureDetectionMethod(String methodName, String methodMI) {
        Query query;
        if (methodName == null && methodMI == null){
            query = getEntityManager().createQuery("select f from IntactFeatureEvidence f "  +
                    "where f.dbDetectionMethods is empty");
        }
        else if (methodMI != null){
            query = getEntityManager().createQuery("select distinct f from IntactFeatureEvidence f "  +
                    "join f.dbDetectionMethods as det " +
                    "join det.dbXrefs as xref " +
                    "join xref.database as d " +
                    "join xref.qualifier as q " +
                    "where (q.shortName = :identity or q.shortName = :secondaryAc) " +
                    "and d.shortName = :psimi " +
                    "and xref.id = :mi");
            query.setParameter("identity", Xref.IDENTITY);
            query.setParameter("secondaryAc", Xref.SECONDARY);
            query.setParameter("psimi", CvTerm.PSI_MI);
            query.setParameter("mi", methodMI);
        }
        else{
            query = getEntityManager().createQuery("select distinct f from IntactFeatureEvidence f "  +
                    "join f.dbDetectionMethods as det " +
                    "where det.shortName = :methodName");
            query.setParameter("methodName", methodName);
        }
        return query.getResultList();
    }

    @Override
    public IntactDbSynchronizer<FeatureEvidence, IntactFeatureEvidence> getDbSynchronizer() {
        return getSynchronizerContext().getFeatureEvidenceSynchronizer();
    }
}
