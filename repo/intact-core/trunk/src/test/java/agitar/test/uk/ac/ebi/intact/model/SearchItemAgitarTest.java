/**
 * Generated by Agitar build: Agitator Version 1.0.4.000276 (Build date: Mar 27, 2007) [1.0.4.000276]
 * JDK Version: 1.5.0_09
 *
 * Generated on 04-Apr-2007 08:24:25
 * Time to generate: 00:09.827 seconds
 *
 */

package uk.ac.ebi.intact.model;

import com.agitar.lib.junit.AgitarTestCase;


public class SearchItemAgitarTest extends AgitarTestCase {

    static Class TARGET_CLASS = SearchItem.class;

    public void testConstructor() throws Throwable {
        SearchItem searchItem = new SearchItem( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" );
        assertEquals( "searchItem.getPk().getAc()", "testSearchItemAc", searchItem.getPk().getAc() );
    }

    public void testConstructor1() throws Throwable {
        SearchItem searchItem = new SearchItem();
        assertNull( "searchItem.getPk()", searchItem.getPk() );
    }

    public void testEquals() throws Throwable {
        SearchItem searchItem = new SearchItem( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" );
        boolean result = searchItem.equals( "" );
        assertFalse( "result", result );
        assertEquals( "searchItem.getPk().getAc()", "testSearchItemAc", searchItem.getPk().getAc() );
    }

    public void testEquals1() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        boolean result = searchItem.equals( pk );
        assertTrue( "result", result );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testGetAc() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk() );
        String result = searchItem.getAc();
        assertNull( "result", result );
    }

    public void testGetAc1() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" ) );
        String result = searchItem.getAc();
        assertEquals( "result", "testSearchItemAc", result );
    }

    public void testGetObjClass() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk() );
        String result = searchItem.getObjClass();
        assertNull( "result", result );
    }

    public void testGetObjClass1() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" ) );
        String result = searchItem.getObjClass();
        assertEquals( "result", "testSearchItemObjClass", result );
    }

    public void testGetType() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" ) );
        String result = searchItem.getType();
        assertEquals( "result", "testSearchItemType", result );
    }

    public void testGetType1() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk() );
        String result = searchItem.getType();
        assertNull( "result", result );
    }

    public void testGetValue() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk() );
        String result = searchItem.getValue();
        assertNull( "result", result );
    }

    public void testGetValue1() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        pk.setValue( "testSearchItemValue" );
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        String result = searchItem.getValue();
        assertEquals( "result", "testSearchItemValue", result );
    }

    public void testHashCode() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        int result = searchItem.hashCode();
        assertEquals( "result", 0, result );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testHashCode1() throws Throwable {
        SearchItem searchItem = new SearchItem( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" );
        int result = searchItem.hashCode();
        assertEquals( "result", -1167595602, result );
        assertEquals( "searchItem.getPk().getAc()", "testSearchItemAc", searchItem.getPk().getAc() );
    }

    public void testSetAc() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        searchItem.setAc( "testSearchItemAc" );
        assertEquals( "searchItem.getPk().getAc()", "testSearchItemAc", searchItem.getPk().getAc() );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testSetObjClass() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        searchItem.setObjClass( "testSearchItemObjClass" );
        assertEquals( "searchItem.getPk().getObjClass()", "testSearchItemObjClass", searchItem.getPk().getObjClass() );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testSetPk() throws Throwable {
        SearchItem searchItem = new SearchItem();
        SearchItemPk pk = new SearchItemPk();
        searchItem.setPk( pk );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testSetType() throws Throwable {
        SearchItemPk pk = new SearchItemPk();
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( pk );
        searchItem.setType( "testSearchItemType" );
        assertEquals( "searchItem.getPk().getType()", "testSearchItemType", searchItem.getPk().getType() );
        assertSame( "searchItem.getPk()", pk, searchItem.getPk() );
    }

    public void testSetValue() throws Throwable {
        SearchItem searchItem = new SearchItem( "testSearchItemAc", "testSearchItemValue", "testSearchItemObjClass", "testSearchItemType" );
        searchItem.setValue( "testSearchItemValue" );
        assertEquals( "searchItem.getPk().getAc()", "testSearchItemAc", searchItem.getPk().getAc() );
    }

    public void testToString() throws Throwable {
        SearchItem searchItem = new SearchItem();
        searchItem.setPk( new SearchItemPk() );
        String result = searchItem.toString();
        assertEquals( "result", "SearchItemPk{ac='null', value='null', objClass='null', type='null'}", result );
    }

    public void testEqualsThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.equals( "" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testGetAcThrowsNullPointerException() throws Throwable {
        try {
            new SearchItem().getAc();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
        }
    }

    public void testGetObjClassThrowsNullPointerException() throws Throwable {
        try {
            new SearchItem().getObjClass();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
        }
    }

    public void testGetTypeThrowsNullPointerException() throws Throwable {
        try {
            new SearchItem().getType();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
        }
    }

    public void testGetValueThrowsNullPointerException() throws Throwable {
        try {
            new SearchItem().getValue();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
        }
    }

    public void testHashCodeThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.hashCode();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testSetAcThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.setAc( "testSearchItemAc" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testSetObjClassThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.setObjClass( "testSearchItemObjClass" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testSetTypeThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.setType( "testSearchItemType" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testSetValueThrowsNullPointerException() throws Throwable {
        SearchItem searchItem = new SearchItem();
        try {
            searchItem.setValue( "testSearchItemValue" );
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
            assertNull( "searchItem.getPk()", searchItem.getPk() );
        }
    }

    public void testToStringThrowsNullPointerException() throws Throwable {
        try {
            new SearchItem().toString();
            fail( "Expected NullPointerException to be thrown" );
        } catch ( NullPointerException ex ) {
            assertNull( "ex.getMessage()", ex.getMessage() );
            assertThrownBy( SearchItem.class, ex );
        }
    }
}

