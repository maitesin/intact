/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.persistence.dao;

import uk.ac.ebi.intact.model.IntactObject;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.Interactor;
import uk.ac.ebi.intact.persistence.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Factory for all the intact DAOs
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24-Apr-2006</pre>
 */
public class DaoFactory
{
    private DaoFactory(){}

    public static AliasDao getAliasDao()
    {
        return new AliasDao(getCurrentSession());
    }

    public static <T extends AnnotatedObject> AnnotatedObjectDao<T> getAnnotatedObjectDao(Class<T> entityType)
    {
        HibernateDao.validateEntity(entityType);

        return new AnnotatedObjectDao<T>(entityType, getCurrentSession());
    }

    public static ExperimentDao getExperimentDao()
    {
        return new ExperimentDao(getCurrentSession());
    }

    public static <T extends IntactObject> IntactObjectDao<T> getIntactObjectDao(Class<T> entityType)
    {
        HibernateDao.validateEntity(entityType);

        return new IntactObjectDao<T>(entityType, getCurrentSession());
    }

    public static InteractorDao getInteractorDao()
    {
        return new InteractorDao<Interactor>(Interactor.class, getCurrentSession());
    }

    public static SearchItemDao getSearchItemDao()
    {
        return new SearchItemDao(getCurrentSession());
    }

    private static Session getCurrentSession()
    {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

}
