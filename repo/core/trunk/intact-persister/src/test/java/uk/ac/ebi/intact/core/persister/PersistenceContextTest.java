package uk.ac.ebi.intact.core.persister;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.model.AnnotatedObject;
import uk.ac.ebi.intact.model.CvObject;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class PersistenceContextTest
{
    @Before
    public void prepare() throws Exception {
        PersisterContext.getInstance().clear();
    }

    @Test
    public void clear_annotatedObject() throws Exception {
        AnnotatedObject ao = createNiceMock(AnnotatedObject.class);

        expect(ao.getShortLabel()).andReturn("aoLabel").anyTimes();
        replay(ao);

        PersisterContext.getInstance().addToPersist(ao);
        
        verify(ao);

        assertEquals(1, PersisterContext.getInstance().getAnnotatedObjectsToBePersisted().size());
        assertEquals(0, PersisterContext.getInstance().getCvObjectsToBePersisted().size());

        PersisterContext.getInstance().clear();
        assertEquals(0, PersisterContext.getInstance().getAnnotatedObjectsToBePersisted().size());
        assertEquals(0, PersisterContext.getInstance().getCvObjectsToBePersisted().size());
    }

    @Test
    public void clear_cvObject() throws Exception {
        CvObject ao = createNiceMock(CvObject.class);

        expect(ao.getShortLabel()).andReturn("cvLabel").anyTimes();
        replay(ao);

        PersisterContext.getInstance().addToPersist(ao);

        verify(ao);

        assertEquals(1, PersisterContext.getInstance().getCvObjectsToBePersisted().size());
        assertEquals(0, PersisterContext.getInstance().getAnnotatedObjectsToBePersisted().size());

        PersisterContext.getInstance().clear();
        assertEquals(0, PersisterContext.getInstance().getCvObjectsToBePersisted().size());
        assertEquals(0, PersisterContext.getInstance().getAnnotatedObjectsToBePersisted().size());
    }


}
