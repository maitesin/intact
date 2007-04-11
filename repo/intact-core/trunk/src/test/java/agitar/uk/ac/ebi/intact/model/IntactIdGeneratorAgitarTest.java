/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:29:39
 * Time to generate: 01:20.277 seconds
 *
 */

package agitar.uk.ac.ebi.intact.model; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import com.mchange.v2.c3p0.impl.AuthMaskingProperties;
import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.dialect.MckoiDialect;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.ByteType;
import org.hibernate.type.CharArrayType;
import org.hibernate.type.TextType;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import java.util.Properties;

public class IntactIdGeneratorAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = IntactIdGenerator.class;

    public void testConstructor() throws Throwable {
        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
        assertEquals( "intactIdGenerator.getSequenceName()", "intact_ac", intactIdGenerator.getSequenceName() );
    }

    public void testConfigure() throws Throwable {
        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
        Properties properties = new Properties();
        Type type = new CharArrayType();
        intactIdGenerator.configure( type, properties, new MckoiDialect() );
        assertEquals( "properties.size()", 1, properties.size() );
        assertEquals( "properties.get(\"sequence\")", "intact_ac", properties.get( "sequence" ) );
        assertEquals( "intactIdGenerator.generatorKey()", "intact_ac", intactIdGenerator.generatorKey() );
        assertEquals( "intactIdGenerator.sql", "select nextval('intact_ac')", getPrivateField( intactIdGenerator, "sql" ) );
        assertNull( "intactIdGenerator.parameters", getPrivateField( intactIdGenerator, "parameters" ) );
        assertSame( "intactIdGenerator.identifierType", type, getPrivateField( intactIdGenerator, "identifierType" ) );
    }

    public void testConfigure1() throws Throwable {
        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
        Properties properties = new Properties();
        properties.put( "sequence", "testString" );
        Type type = new TextType();
        intactIdGenerator.configure( type, properties, new DB2400Dialect() );
        assertEquals( "intactIdGenerator.generatorKey()", "testString", intactIdGenerator.generatorKey() );
        assertEquals( "intactIdGenerator.sql", "values nextval for testString", getPrivateField( intactIdGenerator, "sql" ) );
        assertNull( "intactIdGenerator.parameters", getPrivateField( intactIdGenerator, "parameters" ) );
        assertSame( "intactIdGenerator.identifierType", type, getPrivateField( intactIdGenerator, "identifierType" ) );
    }

    public void testGetSequenceName() throws Throwable {
        String result = new IntactIdGenerator().getSequenceName();
        assertEquals( "result", "intact_ac", result );
    }

    public void testConfigureThrowsNullPointerException() throws Throwable {
        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
        Properties properties = new AuthMaskingProperties();
        Type type = new ByteType();
        try {
            intactIdGenerator.configure( type, properties, null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "(AuthMaskingProperties) properties.size()", 1, properties.size() );
            assertEquals( "(AuthMaskingProperties) properties.get(\"sequence\")", "intact_ac", properties.get( "sequence" ) );
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SequenceGenerator.class, ex );
            assertEquals( "intactIdGenerator.generatorKey()", "intact_ac", intactIdGenerator.generatorKey() );
            assertNull( "intactIdGenerator.sql", getPrivateField( intactIdGenerator, "sql" ) );
            assertNull( "intactIdGenerator.parameters", getPrivateField( intactIdGenerator, "parameters" ) );
            assertSame( "intactIdGenerator.identifierType", type, getPrivateField( intactIdGenerator, "identifierType" ) );
        }
    }

    public void testConfigureThrowsNullPointerException1() throws Throwable {
        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
        try {
            intactIdGenerator.configure( new ByteType(), null, new DerbyDialect() );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( PropertiesHelper.class, ex );
            assertNull( "intactIdGenerator.generatorKey()", intactIdGenerator.generatorKey() );
            assertNull( "intactIdGenerator.sql", getPrivateField( intactIdGenerator, "sql" ) );
            assertNull( "intactIdGenerator.parameters", getPrivateField( intactIdGenerator, "parameters" ) );
            assertNull( "intactIdGenerator.identifierType", getPrivateField( intactIdGenerator, "identifierType" ) );
        }
    }

//    public void testGenerateThrowsConfigurationException() throws Throwable {
//        IntactIdGenerator intactIdGenerator = new IntactIdGenerator();
//        try {
//            intactIdGenerator.generate( null, "WOd5B& DPfqYpz?.>M\rZ" );
//            fail( "Expected ConfigurationException to be thrown" );
//        } catch ( ConfigurationException ex ) {
//            assertEquals( "ex.getMessage()", "Couldn't configure hibernate using default file", ex.getMessage() );
//            assertThrownBy( AbstractHibernateDataConfig.class, ex );
//            assertFalse( "intactIdGeneratorIntactIdGenerator.log.isDebugEnabled()", ( ( Log ) getPrivateField( IntactIdGenerator.class, "log" ) ).isDebugEnabled() );
//            assertFalse( "intactIdGeneratorSequenceGenerator.log.isDebugEnabled()", ( ( Log ) getPrivateField( IntactIdGenerator.class, "log" ) ).isDebugEnabled() );
//        }
//    }
}

