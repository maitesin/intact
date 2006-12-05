/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.persistence.dao.DaoFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Application-wide context to cache the loaded CVs, so it is faster to
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15-Aug-2006</pre>
 */
public final class CvContext implements Serializable
{

    private static final Log log = LogFactory.getLog(CvContext.class);

    private static final String APPLICATION_PARAM_NAME = CvContext.class.getName() + ".CONFIG";

    private Map<String, CvObject> cachedByAc;
    private Map<LabelKey, CvObject> cachedByLabel;
    private Map<MiRefKey, CvObject> cachedByMiRef;

    private IntactSession session;

    private CvContext(IntactSession session)
    {
        this.session = session;

        this.cachedByAc = new HashMap<String, CvObject>();
        this.cachedByLabel = new HashMap<LabelKey, CvObject>();
        this.cachedByMiRef = new HashMap<MiRefKey, CvObject>();
    }

    public static CvContext getCurrentInstance(IntactSession session)
    {
        CvContext cvContext
                = (CvContext) session.getApplicationAttribute(APPLICATION_PARAM_NAME);
        if (cvContext == null)
        {
            log.debug("Creating new CvContext");
            cvContext = new CvContext(session);
            session.setApplicationAttribute(APPLICATION_PARAM_NAME, cvContext);
        }
        return cvContext;
    }

    public CvObject getByAc(String ac)
    {
        return getByAc(ac, false);
    }

    public CvObject getByAc(String ac, boolean forceReload)
    {
        if (!forceReload && cachedByAc.containsKey(ac))
        {
            return cachedByAc.get(ac);
        }

        CvObject cvObject = getDaoFactory().getCvObjectDao().getByAc(ac);

        if (cvObject == null)
        {
            return null;
        }

        putCv(cvObject);

        return cvObject;
    }

    public <T extends CvObject> T getByLabel(Class<T> cvType, String label)
    {
        return getByLabel(cvType, label, false);
    }

    public <T extends CvObject> T getByLabel(Class<T> cvType, String label, boolean forceReload)
    {
        if (cvType == null)
        {
            throw new NullPointerException("To get a CV using its label, you need the CV type");
        }

        LabelKey labelKey = new LabelKey(label, cvType);

        if (!forceReload && cachedByLabel.containsKey(labelKey))
        {
            return (T) cachedByLabel.get(labelKey);
        }

        T cvObject = getDaoFactory().getCvObjectDao(cvType).getByShortLabel(cvType, label);

        if (cvObject == null)
        {
            return null;
        }

        putCv(cvObject);

        return cvObject;
    }

    public <T extends CvObject> T getByMiRef(Class<T> cvType, String miRef)
    {
        return getByMiRef(cvType, miRef, false);
    }

    public <T extends CvObject> T getByMiRef(Class<T> cvType, String miRef, boolean forceReload)
    {
        if (miRef == null)
        {
            throw new NullPointerException("miRef");
        }

        MiRefKey key = new MiRefKey(miRef, cvType);

        if (!forceReload && cachedByMiRef.containsKey(key))
        {
            return (T) cachedByMiRef.get(key);
        }

        T cvObject = getDaoFactory().getCvObjectDao().getByPrimaryId(cvType, miRef);

        if (cvObject == null)
        {
            return null;
        }

        putCv(cvObject);
        putCvInMiRef(miRef, cvObject);

        return cvObject;
    }

    public void clearCache()
    {
        cachedByAc.clear();
        cachedByLabel.clear();
        cachedByMiRef.clear();
    }

    public void loadCommonCvObjects()
    {
        getByMiRef(CvDatabase.class, CvDatabase.INTACT_MI_REF);
        getByMiRef(CvDatabase.class, CvDatabase.GO_MI_REF);
        getByMiRef(CvDatabase.class, CvDatabase.INTERPRO_MI_REF);
        getByMiRef(CvDatabase.class, CvDatabase.FLYBASE_MI_REF);
        getByMiRef(CvDatabase.class, CvDatabase.REACTOME_PROTEIN_PSI_REF);
        getByMiRef(CvDatabase.class, CvDatabase.HUGE_MI_REF);
        getByMiRef(CvXrefQualifier.class, CvXrefQualifier.IDENTITY_MI_REF);
        getByMiRef(CvXrefQualifier.class, CvXrefQualifier.SECONDARY_AC_MI_REF);
        getByMiRef(CvXrefQualifier.class, CvXrefQualifier.ISOFORM_PARENT_MI_REF);

        // only one search by shortlabel as it still doesn't have MI number.
        getByLabel(CvTopic.class, CvTopic.ISOFORM_COMMENT);
        getByLabel(CvTopic.class, CvTopic.NON_UNIPROT);

        getByMiRef(CvAliasType.class, CvAliasType.GENE_NAME_MI_REF);
        getByMiRef(CvAliasType.class, CvAliasType.GENE_NAME_SYNONYM_MI_REF);
        getByMiRef(CvAliasType.class, CvAliasType.ISOFORM_SYNONYM_MI_REF);
        getByMiRef(CvAliasType.class, CvAliasType.LOCUS_NAME_MI_REF);
        getByMiRef(CvAliasType.class, CvAliasType.ORF_NAME_MI_REF);
        getByMiRef(CvAliasType.class, CvInteractorType.getProteinMI());
    }

    public CvComponentRole getBait() throws IntactException
    {
        return getByMiRef(CvComponentRole.class, CvComponentRole.BAIT_PSI_REF);
    }

    public CvComponentRole getPrey() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.PREY_PSI_REF);
    }

    public CvComponentRole getNeutral() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.NEUTRAL_PSI_REF);
    }

    public CvComponentRole getSelf() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.SELF_PSI_REF);
    }

    public CvComponentRole getEnzyme() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.ENZYME_PSI_REF);
    }

    public CvComponentRole getEnzymeTarget() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.ENZYME_TARGET_PSI_REF);
    }

    public CvComponentRole getUnspecified() throws IntactException
    {

        return getByMiRef(CvComponentRole.class, CvComponentRole.UNSPECIFIED_PSI_REF);
    }

    private void putCv(CvObject cv)
    {
        cachedByAc.put(cv.getAc(), cv);
        cachedByLabel.put(new LabelKey(cv.getShortLabel(), cv.getClass()), cv);
    }

    private void putCvInMiRef(String miRef, CvObject cv)
    {
          cachedByMiRef.put(new MiRefKey(miRef, cv.getClass()), cv);
    }

    private DaoFactory getDaoFactory()
    {
        return DaoFactory.getCurrentInstance(session, RuntimeConfig.getCurrentInstance(session).getDefaultDataConfig());
    }

    /**
     * Updates a CvObject already present in the context
     *
     * @param cvObject The new value for the cvObject. Note that it must be equal to the existing one in order to be
     *                 updated
     */
    public void updateCvObject(CvObject cvObject)
    {
        if (cvObject == null)
        {
            throw new IllegalArgumentException("The cvObject is null.");
        }
        else if (cvObject.getAc() == null)
        {
            throw new IllegalArgumentException("The cvObject does not have an ac.");
        }
        else if (cvObject.getShortLabel() == null)
        {
            throw new IllegalArgumentException("The cvObject does not have a shortlabel.");
        }

        //Remove the old cv from the cachedByLabel map
        Iterator it = cachedByLabel.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, CvObject> pairs = (Map.Entry) it.next();
            if (pairs.getValue().getAc().equals(cvObject.getAc()))
            {
                it.remove();
            }
        }
        //Add the new one.
        cachedByLabel.put(new LabelKey(cvObject.getShortLabel(), cvObject.getClass()), cvObject);

        // Update the cvObject in cachedByAc (we don't need to remove it first as it's quite unlikely that the
        // ac changed whereas the shortlabel could change.
        cachedByAc.put(cvObject.getAc(), cvObject);
    }

    /**
     * Convenient class to be stored as the label key, to avoid having returned more than one result per label.
     * The combination of cv object class and label is unique
     */
    private class LabelKey
    {
        private String label;
        private Class<? extends CvObject> cvType;


        public LabelKey(String label, Class<? extends CvObject> cvType)
        {
            this.label = label;
            this.cvType = cvType;
        }

        public String getLabel()
        {
            return label;
        }

        public Class<? extends CvObject> getCvType()
        {
            return cvType;
        }


        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            LabelKey labelKey = (LabelKey) o;

            if (cvType != null ? !cvType.equals(labelKey.cvType) : labelKey.cvType != null)
            {
                return false;
            }
            if (label != null ? !label.equals(labelKey.label) : labelKey.label != null)
            {
                return false;
            }

            return true;
        }

        public int hashCode()
        {
            int result;
            result = (label != null ? label.hashCode() : 0);
            result = 31 * result + (cvType != null ? cvType.hashCode() : 0);
            return result;
        }
    }

    /**
     * Convenient class to be stored as the label key, to avoid having returned more than one result per label.
     * The combination of cv object class and label is unique
     */
    private class MiRefKey
    {
        private String miRef;
        private Class<? extends CvObject> cvType;


        public MiRefKey(String miRef, Class<? extends CvObject> cvType)
        {
            this.miRef = miRef;
            this.cvType = cvType;
        }

        public String getMiRef()
        {
            return miRef;
        }

        public Class<? extends CvObject> getCvType()
        {
            return cvType;
        }


        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            MiRefKey miRefKey = (MiRefKey) o;

            if (cvType != null ? !cvType.equals(miRefKey.cvType) : miRefKey.cvType != null)
            {
                return false;
            }
            if (miRef != null ? !miRef.equals(miRefKey.miRef) : miRefKey.miRef != null)
            {
                return false;
            }

            return true;
        }

        public int hashCode()
        {
            int result;
            result = (miRef != null ? miRef.hashCode() : 0);
            result = 31 * result + (cvType != null ? cvType.hashCode() : 0);
            return result;
        }
    }

}
