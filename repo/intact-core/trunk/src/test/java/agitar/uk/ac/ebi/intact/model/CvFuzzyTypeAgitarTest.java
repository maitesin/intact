/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:22:15
 * Time to generate: 00:21.168 seconds
 *
 */

package agitar.uk.ac.ebi.intact.model; import uk.ac.ebi.intact.model.*;

import com.agitar.lib.junit.AgitarTestCase;
import uk.ac.ebi.intact.model.util.AnnotatedObjectUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CvFuzzyTypeAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = CvFuzzyType.class;

    public void testConstructor() throws Throwable {
        Institution owner = new Institution( "testCvFuzzyTypeShortLabel" );
        CvFuzzyType cvFuzzyType = new CvFuzzyType( owner, "testCvFuzzyTypeShortLabel" );
        assertEquals( "cvFuzzyType.xrefs.size()", 0, cvFuzzyType.xrefs.size() );
        assertEquals( "cvFuzzyType.getAliases().size()", 0, cvFuzzyType.getAliases().size() );
        assertEquals( "cvFuzzyType.getEvidences().size()", 0, cvFuzzyType.getEvidences().size() );
        assertEquals( "cvFuzzyType.shortLabel", "testCvFuzzyTypeShort", cvFuzzyType.getShortLabel() );
        assertEquals( "cvFuzzyType.annotations.size()", 0, cvFuzzyType.annotations.size() );
        assertSame( "cvFuzzyType.getOwner()", owner, cvFuzzyType.getOwner() );
        assertEquals( "cvFuzzyType.references.size()", 0, cvFuzzyType.references.size() );
    }

    public void testConverterConstructor() throws Throwable {
        new CvFuzzyType.Converter();
        assertTrue( "Test completed without Exception", true );
    }

    public void testConverterGetDisplayValue() throws Throwable {
        CvFuzzyType.Converter instance = CvFuzzyType.Converter.getInstance();
        String result = instance.getDisplayValue( "testConverterShortLabel" );
        assertEquals( "result", "", result );
        assertEquals( "instanceCvFuzzyType.Converter.ourNormalMap.size()", 6, ( ( Map ) getPrivateField( CvFuzzyType.Converter.class, "ourNormalMap" ) ).size() );
    }

    public void testConverterGetDisplayValue1() throws Throwable {
        CvFuzzyType.Converter converter = new CvFuzzyType.Converter();
        String result = converter.getDisplayValue( "less-than" );
        assertEquals( "result", "<", result );
        assertEquals( "converterCvFuzzyType.Converter.ourNormalMap.size()", 6, ( ( Map ) getPrivateField( CvFuzzyType.Converter.class, "ourNormalMap" ) ).size() );
    }

    public void testConverterGetFuzzyShortLabel() throws Throwable {
        CvFuzzyType.Converter instance = CvFuzzyType.Converter.getInstance();
        String result = instance.getFuzzyShortLabel( ".." );
        assertEquals( "result", "range", result );
        assertEquals( "instanceCvFuzzyType.Converter.ourNormalMap.size()", 6, ( ( Map ) getPrivateField( CvFuzzyType.Converter.class, "ourNormalMap" ) ).size() );
    }

    public void testConverterGetFuzzyShortLabel1() throws Throwable {
        CvFuzzyType.Converter converter = new CvFuzzyType.Converter();
        String result = converter.getFuzzyShortLabel( "testConverterValue" );
        assertNull( "result", result );
        assertEquals( "converterCvFuzzyType.Converter.ourNormalMap.size()", 6, ( ( Map ) getPrivateField( CvFuzzyType.Converter.class, "ourNormalMap" ) ).size() );
    }

    public void testConverterGetInstance() throws Throwable {
        CvFuzzyType.Converter result = CvFuzzyType.Converter.getInstance();
        assertNotNull( "result", result );
    }

    public void testIsCTerminal() throws Throwable {
        boolean result = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" ).isCTerminal();
        assertFalse( "result", result );
    }

    public void testIsCTerminal1() throws Throwable {
        CvFuzzyType cvFuzzyType = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" );
        cvFuzzyType.setShortLabel( "c-terminal" );
        boolean result = cvFuzzyType.isCTerminal();
        assertTrue( "result", result );
    }

    public void testIsNTerminal() throws Throwable {
        CvFuzzyType cvFuzzyType = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" );
        cvFuzzyType.setShortLabel( "n-terminal" );
        boolean result = cvFuzzyType.isNTerminal();
        assertTrue( "result", result );
    }

    public void testIsNTerminal1() throws Throwable {
        boolean result = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" ).isNTerminal();
        assertFalse( "result", result );
    }

    public void testIsSingleType() throws Throwable {
        boolean result = CvFuzzyType.isSingleType( "n-terminal" );
        assertTrue( "result", result );
    }

    public void testIsSingleType1() throws Throwable {
        boolean result = CvFuzzyType.isSingleType( "testCvFuzzyTypeType" );
        assertFalse( "result", result );
    }

    public void testIsSingleType2() throws Throwable {
        boolean result = CvFuzzyType.isSingleType( "c-terminal" );
        assertTrue( "result", result );
    }

    public void testIsSingleType3() throws Throwable {
        boolean result = CvFuzzyType.isSingleType( "undetermined" );
        assertTrue( "result", result );
    }

    public void testIsUndetermined() throws Throwable {
        boolean result = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" ).isUndetermined();
        assertFalse( "result", result );
    }

    public void testIsUndetermined1() throws Throwable {
        CvFuzzyType cvFuzzyType = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" );
        cvFuzzyType.setShortLabel( "undetermined" );
        boolean result = cvFuzzyType.isUndetermined();
        assertTrue( "result", result );
    }

    public void testConstructorThrowsIllegalArgumentException() throws Throwable {
        try {
            new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "" );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non empty short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertEquals( "ex.getMessage()", "Must define a non null short label", ex.getMessage() );
            assertThrownBy( AnnotatedObjectUtils.class, ex );
        }
    }

    public void testConverterGetFuzzyShortLabelThrowsIllegalArgumentException() throws Throwable {
        Matcher matcher = Pattern.compile( "testConverterParam1", 100 ).matcher( new StringBuilder() );
        matcher.reset();
        try {
            CvFuzzyType.Converter.getInstance().getFuzzyShortLabel( matcher );
            fail( "Expected IllegalArgumentException to be thrown" );
        } catch ( IllegalArgumentException ex ) {
            assertEquals( "ex.getMessage()", "No match found", ex.getMessage() );
            assertThrownBy( CvFuzzyType.Converter.class, ex );
            assertEquals( "matcher.groupCount()", 0, matcher.groupCount() );
        }
    }

    public void testConverterGetFuzzyShortLabelThrowsIndexOutOfBoundsException() throws Throwable {
        Matcher matcher = Pattern.compile( "testString", 100 ).matcher( "testString" );
        matcher.reset();
        try {
            CvFuzzyType.Converter.getInstance().getFuzzyShortLabel( matcher );
            fail( "Expected IndexOutOfBoundsException to be thrown" );
        } catch ( IndexOutOfBoundsException ex ) {
            assertEquals( "ex.getMessage()", "No group 1", ex.getMessage() );
            assertThrownBy( Matcher.class, ex );
            assertEquals( "matcher.groupCount()", 0, matcher.groupCount() );
        }
    }

    public void testIsCTerminalThrowsNullPointerException() throws Throwable {
        CvFuzzyType cvFuzzyType = new CvFuzzyType( new Institution( "testCvFuzzyTypeShortLabel" ), "testCvFuzzyTypeShortLabel" );
        cvFuzzyType.setShortLabel( null );
        try {
            cvFuzzyType.isCTerminal();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( CvFuzzyType.class, ex );
        }
    }

    public void testIsSingleTypeThrowsNullPointerException() throws Throwable {
        try {
            CvFuzzyType.isSingleType( null );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( CvFuzzyType.class, ex );
        }
    }
}

