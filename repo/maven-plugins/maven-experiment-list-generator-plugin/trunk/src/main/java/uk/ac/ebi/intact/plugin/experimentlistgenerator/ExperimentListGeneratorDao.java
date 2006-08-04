/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.plugin.experimentlistgenerator;

import uk.ac.ebi.intact.model.CvTopic;
import uk.ac.ebi.intact.model.Experiment;
import uk.ac.ebi.intact.model.AnnotatedObjectImpl;
import uk.ac.ebi.intact.model.CvDatabase;
import uk.ac.ebi.intact.model.CvXrefQualifier;
import uk.ac.ebi.intact.persistence.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

/**
 * TODO: comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/08/2006</pre>
 */
@SuppressWarnings("unchecked")
public class ExperimentListGeneratorDao
{

    /**
     * Query to get at the Experiment ACs containing negative interaction annotations
     */
    public static List<Experiment> getExpWithInteractionsContainingAnnotation(String cvShortLabel)
    {

        return getSession().createCriteria(Experiment.class)
                .createCriteria("interactions")
                .createCriteria("annotations")
                .createCriteria("cvTopic")
                .add(Restrictions.eq("shortLabel", cvShortLabel)).list();
    }

    /**
     * Query to obtain annotated objects by searching for an Annotation
     * with the cvTopic label provided
     */
    public static <T extends AnnotatedObjectImpl> List<T> getContainingAnnotation(Class<T> annObject, String cvShortLabel)
    {
         return getSession().createCriteria(annObject.getClass())
                .createCriteria("annotations")
                .createCriteria("cvTopic")
                .add(Restrictions.eq("shortLabel", cvShortLabel)).list();
    }

    public static List<String[]> getExperimentAcAndLabelWithoutPubmedId()
    {
        return getSession().createCriteria(Experiment.class, "exp")
                .createCriteria("xrefs")
                .createAlias("cvDatabase", "cvDb")
                .createAlias("cvXrefQualifier", "cvXrefQual")
                .add(Restrictions.not(Restrictions.conjunction()
                    .add(Restrictions.eq("cvDb.shortLabel", CvDatabase.PUBMED))
                    .add(Restrictions.eq("cvXrefQual.shortLabel", CvXrefQualifier.PRIMARY_REFERENCE))))
                .setProjection(Projections.projectionList()
                        .add(Projections.distinct(Projections.property("exp.ac")))
                        .add(Projections.property("exp.shortLabel")))
                .list();
    }

    private static Session getSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

}
