package uk.ac.ebi.intact.persistence.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.context.IntactSession;
import uk.ac.ebi.intact.model.Component;
import uk.ac.ebi.intact.persistence.dao.ComponentDao;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * DAO for components
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-jul-2006</pre>
 */
@Repository
@Transactional
@SuppressWarnings( {"unchecked"} )
public class ComponentDaoImpl extends AnnotatedObjectDaoImpl<Component> implements ComponentDao {

    public ComponentDaoImpl() {
        super (Component.class);
    }

    public ComponentDaoImpl( EntityManager entityManager, IntactSession intactSession ) {
        super( Component.class, entityManager, intactSession );
    }


    public List<Component> getByInteractorAc( String interactorAc ) {
        return getSession().createCriteria( getEntityClass() )
                .createCriteria( "interactor" )
                .add( Restrictions.idEq( interactorAc ) ).list();
    }
}
