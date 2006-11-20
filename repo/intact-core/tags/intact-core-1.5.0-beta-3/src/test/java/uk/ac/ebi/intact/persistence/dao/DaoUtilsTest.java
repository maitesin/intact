/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package uk.ac.ebi.intact.persistence.dao;

import junit.framework.TestCase;

/**
 * Test for <code>DaoUtilsTest</code>
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 10/10/2006
 */
public class DaoUtilsTest extends TestCase
{
    public DaoUtilsTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testReplaceWildCardsByPercent()
    {
        String value1 = "*this-is*-a-test*";
        assertEquals("%this-is*-a-test%", DaoUtils.replaceWildcardsByPercent(value1));

        String value2 = "this-is*-a-test*";
        assertEquals("this-is*-a-test%", DaoUtils.replaceWildcardsByPercent(value2));
    }

    public void testAddPercents()
    {
        String value1 = "*this-is*-a-test*";
        assertEquals("%this-is*-a-test%", DaoUtils.addPercents(value1));

        String value2 = "*this-is*-a-test";
        assertEquals("%this-is*-a-test%", DaoUtils.addPercents(value2));

        String value3 = "this-is*-a-test";
        assertEquals("%this-is*-a-test%", DaoUtils.addPercents(value3));
    }

    public void testAddEndPercent()
    {
        String value1 = "this-is*-a-test*";
        assertEquals("this-is*-a-test%", DaoUtils.addEndPercent(value1));

        String value2 = "this-is*-a-test";
        assertEquals("this-is*-a-test%", DaoUtils.addEndPercent(value2));
    }

    public void testAddStartPercent()
    {
        String value1 = "this-is*-a-test*";
        assertEquals("%this-is*-a-test%", DaoUtils.addStartPercent(value1));

        String value2 = "this-is*-a-test";
        assertEquals("%this-is*-a-test", DaoUtils.addStartPercent(value2));
    }
}
