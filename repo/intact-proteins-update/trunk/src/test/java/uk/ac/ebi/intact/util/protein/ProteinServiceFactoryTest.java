package uk.ac.ebi.intact.util.protein;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ebi.intact.uniprot.service.AbstractUniprotService;
import uk.ac.ebi.intact.uniprot.model.UniprotProtein;

import java.util.Collection;
import java.util.Map;

/**
 * ProteinLoaderServiceFactory Tester.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version 1.0
 * @since <pre>02/09/2007</pre>
 */
public class ProteinServiceFactoryTest extends TestCase {

    public ProteinServiceFactoryTest( String name ) {
        super( name );
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public static Test suite() {
        return new TestSuite( ProteinServiceFactoryTest.class );
    }

    ////////////////////
    // Tests

    public void testGetInstance() throws Exception {
        ProteinServiceFactory f1 = ProteinServiceFactory.getInstance();
        assertNotNull( f1 );

        ProteinServiceFactory f2 = ProteinServiceFactory.getInstance();
        assertNotNull( f2 );

        // the factory is a singleton
        assertTrue( "The factory is not a singleton", f1 == f2 );
    }

    // implementation of the service for the sake of the test below.
    protected class DummyUniprotService extends AbstractUniprotService {
        public Collection<UniprotProtein> retreive( String ac ) {
            throw new UnsupportedOperationException();
        }

        public Map<String, Collection<UniprotProtein>> retreive( Collection<String> acs ) {
            throw new UnsupportedOperationException();
        }
    }

    public void testBuildProteinService() {
        ProteinServiceFactory factory = ProteinServiceFactory.getInstance();
        assertNotNull( factory );

        ProteinService service = factory.buildProteinService();
        assertNotNull( service );

        ProteinService dummy = factory.buildProteinService( new DummyUniprotService() );
        assertNotNull( dummy );

        try {
            factory.buildProteinService( null );
            fail( "null is not allowed." );
        } catch ( Exception e ) {
            // ok
        }
    }
}
