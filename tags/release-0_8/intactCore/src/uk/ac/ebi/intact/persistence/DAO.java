/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.persistence;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.apache.ojb.broker.accesslayer.LookupException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

/**
 *  <p> This interface defines the methods available from a Data
 * Access Object (DAO) implementation. It therefore allows clients
 * of this interface to use typical operations usually provided by
 * a connection class, such as submitting queries etc without
 * knowing the details of the specific DAO implementing this interface.
 * Each implementation of the interface will therefore be a wrapper
 * object around a particular DAO type (eg Castor, XML etc). </p>
 *
 * <p>Note that methods are included to either perform "encapsulated transactions"
 * (for example bulk object creation or simple searching), or to manage
 * transactions at the client level as one might usually do with, say, JDBC. </p>
 *
 * @author Chris Lewington
 *
 */
public interface DAO {


    /**
     * validates a username and password against the datastore.
     *
     * @return boolean true if user credentials are valid, false if not or if a null username is supplied.
     */
    public boolean isUserValid(String userName, String password);

    /**
     * Method to release result resources (eg result set). An IllegalArgumentException
     * is thrown if the iterator supplied has not been generated by use of iteratorFind.
     *
     * @param itemIterator The iterator over the results (should be obtained from iteratorFind)
     * @exception IllegalArgumentException thrown if the Iterator is an invalid type
     */
    public void closeResults(Iterator itemIterator);

    /**
         * Provides the database name that is being connected to.
         * @return String the database name, or an empty String if the query fails
         * @exception org.apache.ojb.broker.accesslayer.LookupException thrown on error
         * getting the Connection
         * @exception SQLException thrown if the metatdata can't be obtained
         */
        public String getDbName() throws LookupException, SQLException;
        /**
         * Provides the user name that is connecting to the DB.
         * @return String the user name, or an empty String if the query fails
         * @exception org.apache.ojb.broker.accesslayer.LookupException thrown on error
         * getting the Connection
         * @exception SQLException thrown if the metatdata can't be obtained
         */
        public String getDbUserName() throws LookupException, SQLException;

    /**
     *   Used to begin a transaction.
     * @param txType the Transaction type (eg object or JDBC level)
     *
     * @exception TransactionException - can be thrown if a transaction is already in progress
     *
     */
    public void begin(int txType) throws TransactionException;

    /**
     * Locks the given object for <b>write</b> access. <b>{@link #begin(int)} must
     * be called to prior to this method.</b>
     * @param obj the object to lock for <b>write</b> access.
     * @exception org.odmg.TransactionNotInProgressException thrown if no object TX is running
     */
    public void lock(Object obj)throws org.odmg.TransactionNotInProgressException;

    /**
     *   closes a DAO (connection).
     *
     * @exception DataSourceException - thrown if the DAO cannot be closed (details in specific errors)
     *
     */
    public void close() throws DataSourceException;

    /**
     *   opens a DAO (connection).
     *
     * @exception DataSourceException - thrown if the DAO cannot be closed (details in specific errors)
     *
     */
    public void open() throws DataSourceException;

    /**
     *   Commits (finishes) a transaction. Usual meaning.
     *
     * @exception TransactionException - thrown if, say, a transaction was not in progress for example
     *
     */
    public void commit()throws TransactionException;

    /**
     * creates a single object in persistent store.
     *
     * @param obj - the object to be saved
     *
     * @exception CreateException
     *
     */
    public void create(Object obj) throws CreateException;

    /**
     *   checks to see if a transaction is currently in progress
     *
     * @return boolean - true if a transaction isa ctive, false otherwise
     */
    public boolean isActive();

    /**
     *   checks to see if object saving automatically is turned on
     *
     * @return boolean - true if auto saving is on, false otherwise
     */
    public boolean isAutoSave();

    /**
     *   sets whether or not auto saving is turned on
     *
     * @param val - true to turn on, false for off
     */
    public void setAutoSave(boolean val);

    /**
     *   checks to see if a transaction is closed
     *
     * @return boolean - true if closed, false otherwise
     */
    public boolean isClosed();

    /**
     * checks to determine if a given object is persistent or not.
     * <b>Note</b> If the object has manually set primary key this method will
     * return true even if it isn't actually in the database.
     *
     * @param obj - the object to be checked
     *
     * @return boolean - true if the object is persistent (ie., exists in the DB);
     * false otherwise including object created but not yet committed.
     */
    public boolean isPersistent(Object obj);

    /**
     *   removes an object from persistent store.
     *
     * @param obj - the object to be removed
     *
     * @exception TransactionException - thrown usually if the operation is called outside a transaction
     *
     */
    public void remove(Object obj) throws TransactionException;

    /**
     *  rollback a transaction. Usual meaning...
     *
     * @exception TransactionException - thrown if  the transaction couldn't be rolled back
     */
    public void rollback() throws TransactionException;

    /**
     * This method performs an object creation operation for a collection of
     * objects provided as parameters.
     *
     * @param objs - the collection of objects to be persisted
     *
     * @exception CreateException - thrown if an object could not be stored
     * @exception TransactionException - thrown if errors resulted from invalid transaction operations or errors
     *
     */
    public void makePersistent(Collection objs) throws CreateException, TransactionException;

    /**
     * <p>This method performs a simple search based on the object type to search over,
     * the parameter name and the search value. This method will begin a new transaction
     * and commit it before returning if it is not called from within the caller's
     * own transaction
     * </p>
     *
     * Note that at present this only performs very simple "single" text queries - and
     * may be updated later..
     *
     * @param type - the persistent object type to be searched
     * @param col - the parameter to be searched through - NB assumed to be a DB column name
     * @param val - the value to be used for the search (all items for the type returned if null)
     *
     * @return a collection containing the search results
     *
     * @exception SearchException - thrown if there were problems during the search process itself
     */
     public Collection find(String type, String col, String val) throws SearchException;

    /**
     *   <p>This method provides a means to obtain an Iterator for search results. This
     * can be useful if you need direct control over a result set, because OJB iterators have access to
     * result sets which may then be closed by passing the iterator back to the
     * <code>closeResults</code> method. Note that this approach will not tidy up
     * open cursors on an oracle server - the only way to do this seems to be
     * to close the connection, which is done automatically via the <code>find</code>
     * method (this is OK if oracle connection pooling is used).</p>
     *
     * @param type - the class name (ie table) to be searched
     * @param col - the parameter (column name) to search on (usually ac number or name)
     * @param val - the value of the search parameter (all items for the type returned if null)
     *
     * @return an Iterator over the results set - null if no results found
     *
     * @exception SearchException -  thrown for errors during the search process, eg query problems
     * @exception NullPointerException thrown if the type is null
     *
     */
     public Iterator iteratorFind(String type, String col, String val) throws SearchException;


    /**
     * <p>This method performs an object-based search. Specifically it provides a way to
     * search for a "complete" object given a partially defined object, ie an object which does
     * not have all fields filled. It provides equivalent functionality to the simple text-based
     * search when only a single (String) field is filled in. Searches may be performed using an example
     * object which only has a single object reference set, or even an example object containing a Collection
     * that has only a single element set.
     * </p>
     * <p>
     * NOTE - RESTRICTIONS ON CURRENT USE
     * </p>
     * <ul>
     * <li>Any object references contained in the obj parameter must have, at least, their Primary Key defined</li>
     * <li>if the obj parameter contains a non-empty Collection, the Collection must have at least one element with its Primary Key set
     * </ul>
     *
     * @param obj - the object search "value"
     *
     * @return a collection containing the search results
     *
     * @exception SearchException - thrown if there were problems during the search process itself
     *
     */
     public Collection find(Object obj) throws SearchException;


    /**
     * This method provides a means of obtaining particular values of a persistent
     * object without retrieving the whole object itself. It therefore allows users
     * to obtain table column values.
     * @param type the object type you want details of (must be specified)
     * @param cols the table columns (NOT attribute names) you are interested in (null
     * will retrieve all of them)
     * @return Iterator an iterator over the requested table column values - NB
     * each item in the Iterator is of type Object, and must therefore be cast
     * to the correct type by the user of this method. If you do not exhaust the
     * iterator then to clean up DB resources you must pass the Iterator to the
     * <code>closeResults</code> method of this class. Null is returned if no results found
     *
     * @exception SearchException thrown if a problem occurs whilst searching the data store
     * @exception NullPointerException thrown if the type is null
     */
     public Iterator findColumnValues(String type, String[] cols) throws SearchException;



    /**
     * This method allows submission of a query by straight SQL string. If the string
     * is garbage then expect garbage back - no guarantees are made for the results.
     * @param type the object type you want details of (must be specified)
     * @param sqlString the string to be used for the query
     * @return A Collection of search results, or empty if none found
     * @exception SearchException thrown if a problem occurs whilst searching the data store
     * @exception NullPointerException thrown if the type is null
     */
     public Collection findBySQL(String type, String sqlString) throws SearchException;


    /**
     * Removes the given object from the cachce; hence, cancelling any changes
     * to the object.
     * @param obj the object to remove from the cache.
     */
    public void removeFromCache(Object obj);

    /**
     * <p>updates a given object which was oroginally obtained in
     * another transaction. This allows, for example, objects to be read
     * from persistent store in one transaction, modified elsewhere (eg via
     * a user interface object) and then have the changes reflected in persistent
     * store within the context of a <bold>different</bold> transaction. This therefore
     * removes the need to retrieve the object again in order to perform an update.
     * </p>
     *
     * @param obj - the object to be updated
     *
     * @exception UpdateException  - thrown if the object could not me modified
     * (eg called outside a transaction scope) or hasn't been persisted previously (
     * {@link #isPersistent(Object)} must return true).
     *
     * @see #isPersistent(Object)
     */
     public void update(Object obj) throws UpdateException;

    /**
     *  allows a logging destination to be specified
     *
     * @param p A <code>PrintWriter</code> for logging output
     */
    public void setLogger(Logger p);

    /**
     *  adds a class to a set of classes that should be cached. Note that this is
     * in addition to any standard "cache by primary key" facility provided by an implementation.
     *
     * @param clazz  class to be cached
     */
    public void addCachedClass(Class clazz);

    /**
     * wipe the whole cache.
     *
     * @throws PersistenceBrokerException
     */
    public void clearCache() throws PersistenceBrokerException;
}
