package uk.ac.ebi.intact.curationTools.persistence.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uk.ac.ebi.intact.curationTools.model.actionReport.ActionReport;
import uk.ac.ebi.intact.curationTools.persistence.dao.impl.ActionReportDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * The DaoFactory for the annotated element in the CurationTools project
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19-May-2010</pre>
 */
@Component
public class CurationToolsDaoFactory implements Serializable{
    /**
     * The entuty manager
     */
    @PersistenceContext( unitName = "intact-curationTools-default" )
    private EntityManager currentEntityManager;

    /**
     * The application context
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * The BlastReportDao instance
     */
    @Autowired
    private BlastReportDao blastReportDao;

    /**
     * The BlastResultsDao instance
     */
    @Autowired
    private BlastResultsDao blastResultsDao;

    /**
     * The PICRCrossReferences instance
     */
    @Autowired
    private PICRCrossReferencesDao picrCrossReferencesDao;

    /**
     * The PICRReportDao instance
     */
    @Autowired
    private PICRReportDao picrReportDao;

    /**
     * The updateResultsDao instance
     */
    @Autowired
    private UpdateResultsDao updateResultsDao;

    /**
     * Creates a CurationToolsDaoFactory
     */
    public CurationToolsDaoFactory() {
    }

    /**
     *
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return currentEntityManager;
    }

    /**
     *
     * @param entityType
     * @param <T>
     * @return the ActionReportDao instance
     */
    public <T extends ActionReport> ActionReportDao<T> getActionReportDao( Class<T> entityType) {
        ActionReportDao actionReportDao = getBean(ActionReportDaoImpl.class);
        actionReportDao.setEntityClass(entityType);
        return actionReportDao;
    }

    /**
     *
     * @return the BlastReportDao
     */
    public BlastResultsDao getBlastResultsDao() {
        return blastResultsDao;
    }

    /**
     *
     * @return the PicrCrossReferencesDao
     */
    public PICRCrossReferencesDao getPicrCrossReferencesDao() {
        return picrCrossReferencesDao;
    }

    /**
     *
     * @return the UpdateResultsDao
     */
    public UpdateResultsDao getUpdateResultsDao() {
        return updateResultsDao;
    }

    /**
     *
     * @return the BlastReportDao
     */
    public BlastReportDao getBlastReportDao() {
        return blastReportDao;
    }

    /**
     *
     * @return the PICRReportDao
     */
    public PICRReportDao getPICRReportDao() {
        return picrReportDao;
    }

    /**
     *
     * @param beanType
     * @param <T>
     * @return the Bean
     */
    private <T> T getBean(Class<T> beanType) {
        return (T) applicationContext.getBean(StringUtils.uncapitalize(beanType.getSimpleName()));
    }

}
