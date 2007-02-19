/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package uk.ac.ebi.intact.persistence.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.intact.context.IntactSession;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.intact.persistence.dao.ProteinDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03-May-2006</pre>
 */
@SuppressWarnings( {"unchecked"} )
public class ProteinDaoImpl extends PolymerDaoImpl<ProteinImpl> implements ProteinDao {

    private static Log log = LogFactory.getLog( ProteinDaoImpl.class );

    public ProteinDaoImpl( Session session, IntactSession intactSession ) {
        super( ProteinImpl.class, session, intactSession );
    }

    /**
     * Gets the AC of the identity Xref
     */
    public String getIdentityXrefByProteinAc( String proteinAc ) {
        Criteria crit = getSession().createCriteria( ProteinImpl.class )
                .add( Restrictions.idEq( proteinAc ) )
                .createAlias( "xrefs", "xref" )
                .createAlias( "xref.cvXrefQualifier", "qual" )
                .add( Restrictions.eq( "qual.shortLabel", CvXrefQualifier.IDENTITY ) )
                .setProjection( Projections.property( "xref.ac" ) );

        return ( String ) crit.uniqueResult();
    }

    /**
     * Gets the AC of the identity Xref
     */
    public String getUniprotAcByProteinAc( String proteinAc ) {
        Criteria crit = getSession().createCriteria( ProteinImpl.class )
                .add( Restrictions.idEq( proteinAc ) )
                .createAlias( "xrefs", "xref" )
                .createAlias( "xref.cvXrefQualifier", "qual" )
                .add( Restrictions.eq( "qual.shortLabel", CvXrefQualifier.IDENTITY ) )
                .setProjection( Projections.property( "xref.primaryId" ) );

        return ( String ) crit.uniqueResult();
    }

    /**
     * Obtains the template of the url to be used in search.
     * Uses the uniprot Xref and then get hold of its annotation 'search-url'
     */
    public List<String> getUniprotUrlTemplateByProteinAc( String proteinAc ) {
        if ( proteinAc == null ) {
            throw new NullPointerException( "proteinAc" );
        }

        Criteria crit = getSession().createCriteria( ProteinImpl.class )
                .add( Restrictions.idEq( proteinAc ) )
                .createAlias( "xrefs", "xref" )
                .createAlias( "xref.cvXrefQualifier", "cvQual" )
                .createAlias( "xref.cvDatabase", "cvDb" )
                .createAlias( "cvDb.annotations", "annot" )
                .createAlias( "annot.cvTopic", "cvTopic" )
                .add( Restrictions.eq( "cvQual.shortLabel", CvXrefQualifier.IDENTITY ) )
                .add( Restrictions.eq( "cvTopic.shortLabel", CvTopic.SEARCH_URL ) )
                .setProjection( Projections.property( "annot.annotationText" ) );

        return crit.list();
    }

    public Map<String, Integer> getPartnersCountingInteractionsByProteinAc( String proteinAc ) {
        if ( proteinAc == null ) {
            throw new NullPointerException( "proteinAc" );
        }

        Criteria crit = getSession().createCriteria( ProteinImpl.class )
                .add( Restrictions.idEq( proteinAc ) )
                .createAlias( "activeInstances", "comp" )
                .createAlias( "comp.interaction", "int" )
                .createAlias( "int.components", "intcomp" )
                .createAlias( "intcomp.interactor", "prot" )
                .add( Restrictions.disjunction()
                        .add( Restrictions.ne( "prot.ac", proteinAc ) )
                        .add( Restrictions.eq( "comp.stoichiometry", 2f ) ) )
                .setProjection( Projections.projectionList()
                        .add( Projections.countDistinct( "int.ac" ) )
                        .add( Projections.groupProperty( "prot.ac" ) ) );

        List<Object[]> queryResults = crit.list();

        Map<String, Integer> results = new HashMap<String, Integer>( queryResults.size() );

        for ( Object[] res : queryResults ) {
            results.put( ( String ) res[1], ( Integer ) res[0] );
        }

        return results;
    }

    public Integer countPartnersByProteinAc( String proteinAc ) {
        return ( Integer ) partnersByProteinAcCriteria( proteinAc )
                .setProjection( Projections.countDistinct( "prot.ac" ) ).uniqueResult();
    }

    public List<ProteinImpl> getUniprotProteins( Integer firstResult, Integer maxResults ) {
        Criteria crit = criteriaForUniprotProteins()
                .addOrder( Order.asc( "xref.primaryId" ) );

        if ( firstResult != null && firstResult >= 0 ) {
            crit.setFirstResult( firstResult );
        }

        if ( maxResults != null && maxResults > 0 ) {
            crit.setMaxResults( maxResults );
        }

        return crit.list();
    }

    public List<ProteinImpl> getUniprotProteinsInvolvedInInteractions( Integer firstResult, Integer maxResults ) {
        Criteria crit = criteriaForUniprotProteins()
                .add( Restrictions.isNotEmpty( "activeInstances" ) )
                .addOrder( Order.asc( "xref.primaryId" ) );

        if ( firstResult != null && firstResult >= 0 ) {
            crit.setFirstResult( firstResult );
        }

        if ( maxResults != null && maxResults > 0 ) {
            crit.setMaxResults( maxResults );
        }

        return crit.list();
    }

    public Integer countUniprotProteins() {
        return ( Integer ) criteriaForUniprotProteins()
                .setProjection( Projections.rowCount() ).uniqueResult();
    }

    public Integer countUniprotProteinsInvolvedInInteractions() {
        return ( Integer ) criteriaForUniprotProteins()
                .add( Restrictions.isNotEmpty( "activeInstances" ) )
                .setProjection( Projections.rowCount() ).uniqueResult();
    }

    private Criteria criteriaForUniprotProteins() {
        return getSession().createCriteria( ProteinImpl.class )
                .createAlias( "xrefs", "xref" )
                .createAlias( "xref.cvDatabase", "cvDatabase" )
                .createAlias( "xref.cvXrefQualifier", "cvXrefQualifier" )
                .add( Restrictions.eq( "cvDatabase.shortLabel",
                                       CvDatabase.UNIPROT ) )
                .add( Restrictions.eq( "cvXrefQualifier.shortLabel",
                                       CvXrefQualifier.IDENTITY ) )

                .add( Restrictions.not( Restrictions.like( "xref.primaryId", "A%" ) ) )
                .add( Restrictions.not( Restrictions.like( "xref.primaryId", "B%" ) ) )
                .add( Restrictions.not( Restrictions.like( "xref.primaryId", "C%" ) ) );
    }

    public List<ProteinImpl> getByUniprotId( String uniprotId ) {
        return getSession().createCriteria( getEntityClass() )
                .createAlias( "xrefs", "xref" )
                .createAlias( "xref.cvXrefQualifier", "qual" )
                .createAlias( "xref.cvDatabase", "database" )
                .createCriteria( "qual.xrefs", "qualXref" )
                .createCriteria( "database.xrefs", "dbXref" )
                .add( Restrictions.eq( "qualXref.primaryId", CvXrefQualifier.IDENTITY_MI_REF ) )
                .add( Restrictions.eq( "dbXref.primaryId", CvDatabase.UNIPROT_MI_REF ) )
                .add( Restrictions.eq( "xref.primaryId", uniprotId ) ).list();
    }

    public Map<String, List<String>> getPartnersWithInteractionAcsByProteinAc( String proteinAc ) {
        Criteria crit = partnersByProteinAcCriteria( proteinAc )
                .setProjection( Projections.projectionList()
                        .add( Projections.distinct( Projections.property( "prot.ac" ) ) )
                        .add( Projections.property( "int.ac" ) ) )
                .addOrder( Order.asc( "prot.ac" ) );

        Map<String, List<String>> results = new HashMap<String, List<String>>();

        for ( Object[] res : ( List<Object[]> ) crit.list() ) {
            String partnerProtAc = ( String ) res[0];
            String interactionAc = ( String ) res[1];

            if ( results.containsKey( partnerProtAc ) ) {
                results.get( partnerProtAc ).add( interactionAc );
            } else {
                List<String> interactionAcList = new ArrayList<String>();
                interactionAcList.add( interactionAc );

                results.put( partnerProtAc, interactionAcList );
            }
        }

        return results;
    }

    /**
     * Returns the protein id of the parners
     *
     * @param proteinAc
     *
     * @return
     */
    public List<String> getPartnersUniprotIdsByProteinAc( String proteinAc ) {
        return partnersByProteinAcCriteria( proteinAc )
                .createAlias( "prot.xrefs", "xref" )
                .createAlias( "xref.cvXrefQualifier", "qual" )
                .createAlias( "xref.cvDatabase", "database" )
                .createCriteria( "qual.xrefs", "qualXref" )
                .createCriteria( "database.xrefs", "dbXref" )
                .add( Restrictions.eq( "qualXref.primaryId", CvXrefQualifier.IDENTITY_MI_REF ) )
                .add( Restrictions.eq( "dbXref.primaryId", CvDatabase.UNIPROT_MI_REF ) )
                .setProjection( Projections.distinct( Property.forName( "xref.primaryId" ) ) ).list();
    }

    private Criteria partnersByProteinAcCriteria( String proteinAc ) {
        if ( proteinAc == null ) {
            throw new NullPointerException( "proteinAc" );
        }

        return getSession().createCriteria( InteractorImpl.class )
                .add( Restrictions.idEq( proteinAc ) )
                .createAlias( "activeInstances", "comp" )
                .createAlias( "comp.interaction", "int" )
                .createAlias( "int.components", "intcomp" )
                .createAlias( "intcomp.interactor", "prot" )
                .add( Restrictions.disjunction()
                        .add( Restrictions.ne( "prot.ac", proteinAc ) )
                        .add( Restrictions.eq( "comp.stoichiometry", 2f ) ) );
    }


}
