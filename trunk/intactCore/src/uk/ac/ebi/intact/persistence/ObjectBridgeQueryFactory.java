/*
 Copyright (c) 2002-2005 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.persistence;

import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.Criteria;

/**
 * This factory class builds common queries for IntAct.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class ObjectBridgeQueryFactory {

    /** Only instance of this class */
    private static final ObjectBridgeQueryFactory OUR_INSTANCE = new ObjectBridgeQueryFactory();

    /**
     * @return returns the only instance of this class.
     */
    public static final ObjectBridgeQueryFactory getInstance() {
        return OUR_INSTANCE;
    }

    public Query getLikeQuery(Class clazz, String param, String value) {
        // Replace * with % for SQL
        String sqlValue = value.replaceAll("\\*", "%");

        Criteria crit = new Criteria();
        crit.addLike(param, sqlValue);
        return QueryFactory.newQuery(clazz, crit);
    }
}
