package uk.ac.ebi.intact.view.webapp.controller;

import uk.ac.ebi.intact.core.context.IntactContext;
import uk.ac.ebi.intact.core.persistence.dao.DaoFactory;
import org.apache.myfaces.orchestra.conversation.ConversationBindingListener;
import org.apache.myfaces.orchestra.conversation.ConversationBindingEvent;
import org.apache.myfaces.orchestra.conversation.ConversationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract controller giving access to IntAct database access via JPA.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public abstract class JpaBaseController extends BaseController implements ConversationBindingListener{

    private static final Log log = LogFactory.getLog( JpaBaseController.class );

    @Autowired
    private IntactContext intactContext;

    protected DaoFactory getDaoFactory() {
        return getIntactContext().getDataContext().getDaoFactory();
    }

    protected IntactContext getIntactContext() {
        return intactContext;
    }

    public void valueBound(ConversationBindingEvent event) {
        if (log.isDebugEnabled()) log.debug("Conversation event (value bound): conversation="+event.getConversation().getName()+", name="+event.getName());
       // IntactContext.getCurrentInstance().getDataContext().beginTransaction();
    }

    public void valueUnbound(ConversationBindingEvent event) {
        if (log.isDebugEnabled()) log.debug("Conversation event (value unbound): conversation="+event.getConversation().getName()+", name="+event.getName());
//        try {
//            IntactContext.getCurrentInstance().getDataContext().commitTransaction();
//        } catch (IntactTransactionException e) {
//            e.printStackTrace();
//        }
    }
}
