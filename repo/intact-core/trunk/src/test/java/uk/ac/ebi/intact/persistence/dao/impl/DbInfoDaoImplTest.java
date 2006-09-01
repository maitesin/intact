/**
 * Copyright (c) 2002-2006 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */

package uk.ac.ebi.intact.persistence.dao.impl;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import uk.ac.ebi.intact.persistence.dao.DbInfoDao;
import uk.ac.ebi.intact.context.IntactContext;
import uk.ac.ebi.intact.model.meta.DbInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Test for <code>DbInfoDaoImplTest</code>
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 09/01/2006
 */
public class DbInfoDaoImplTest extends TestCase {

    private static final Log log = LogFactory.getLog(DbInfoDaoImplTest.class);

    public DbInfoDaoImplTest(String name) {
        super(name);
    }

    private DbInfoDao dao;

    public void setUp() throws Exception {
        super.setUp();
        dao = IntactContext.getCurrentInstance().getDataContext().getDaoFactory().getDbInfoDao();
    }

    public void tearDown() throws Exception {
        super.tearDown();
        dao = null;
    }

    public void testGet() throws Exception {
        DbInfo version = dao.get(DbInfo.SCHEMA_VERSION);
        assertNotNull(version.getValue());

        System.out.println("Schema Version: "+version.getValue());
    }

    public void testGetAll() throws Exception {
        List<DbInfo> all = dao.getAll();

        assertEquals(1, all.size());
    }

    public static Test suite() {
        return new TestSuite(DbInfoDaoImplTest.class);
    }
}
