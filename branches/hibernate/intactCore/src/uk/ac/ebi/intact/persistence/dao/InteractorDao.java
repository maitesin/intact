/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.persistence.dao;

import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Property;
import uk.ac.ebi.intact.model.Component;
import uk.ac.ebi.intact.model.Interactor;
import uk.ac.ebi.intact.model.InteractorImpl;
import uk.ac.ebi.intact.model.ProteinImpl;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>27-Apr-2006</pre>
 */
public class InteractorDao<T extends InteractorImpl> extends AnnotatedObjectDao<T>
{
    /**
     * Filter to provide filtering on GeneNames
     */
    private static List<String> geneNameFilter = new ArrayList<String>();

    // nested implementation for providing the gene filter
    static {
        // TODO somehow find a way to use MI references that are stable
        geneNameFilter.add( "gene name" );
        geneNameFilter.add( "gene name-synonym" );
        geneNameFilter.add( "orf name" );
        geneNameFilter.add( "locus name" );
    }

    public InteractorDao(Class<T> entityClass, Session session)
    {
        super(entityClass, session);
    }

    public int countInteractionsForInteractorWithAc(String ac)
    {
        return (Integer) getSession().createCriteria(Component.class)
                    .createAlias("interactor", "interactor")
                    .createAlias("interaction", "interaction")
                    .add(Restrictions.eq("interactor.ac", ac))
                    .setProjection(Projections.countDistinct("interaction.ac")).uniqueResult();
    }

    public Collection test()
    {
        return getSession().createCriteria(Component.class)
                    .createAlias("interactor", "interactor")
                    .createAlias("interaction", "interaction")
                    .setProjection(Projections.projectionList()
                        .add(Projections.countDistinct("interaction.ac").as("dist"))
                        .add(Projections.groupProperty("interaction.ac")))
                    .add(Restrictions.ge("dist", 1)).list();
    }

     public List<String> getGeneNamesByInteractorAc(String proteinAc)
    {
        //the gene names are obtained from the Aliases for the Protein
        //which are of type 'gene name'...
        Criteria crit = getSession().createCriteria(ProteinImpl.class)
                .add(Restrictions.idEq(proteinAc))
                .createAlias("aliases", "alias")
                .createAlias("alias.cvAliasType", "aliasType")
                .add(Restrictions.in("aliasType.shortLabel", geneNameFilter))
                .setProjection(Property.forName("alias.name"));

        List<String> geneNames = crit.list();

        if ( geneNames.isEmpty() ) {
            geneNames.add( "-" );
        }

        return geneNames;
    }
}
