/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.editor.business;

import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;

import uk.ac.ebi.intact.application.editor.exception.EmptyTopicsException;
import org.apache.commons.collections.CollectionUtils;

/**
 * This class provides the general editor services common to all the editors.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class EditorService {

    /**
     * Intact topic Types.
     */
    private ResourceBundle myTopicProps;

    /**
     * Reference to Newt server instance.
     */
    private NewtServer myNewtServer;

    // ------------------------------------------------------------------------
    // Inner class to store Newt Server properties

    private static class NewtServer {

        /**
         * Newt server URL
         */
        private URL myNewtURL;

        /**
         * Newt server query parameters.
         */
        private String myNewtQueryParas;

        private NewtServer(String name)
                throws MissingResourceException, MalformedURLException {
            ResourceBundle rb = ResourceBundle.getBundle(name);
            myNewtURL = new URL(rb.getString("newt.server.url"));
            myNewtQueryParas = rb.getString("newt.server.request.params");
        }

        // Getter methods.
        private URL getURL() {
            return myNewtURL;
        }

        private String getQueryParameters() {
            return myNewtQueryParas;
        }

    }
    // End of Inner class
    // ------------------------------------------------------------------------

    /**
     * Loads editor topic properties from a resources file.
     * @param name the name of the Intact types resource file.
     * @exception MissingResourceException thrown when the resource file is
     * not found.
     * @exception EmptyTopicsException thrown for an empty resource file.
     */
    public void loadTopicProperties(String name)
            throws MissingResourceException, EmptyTopicsException {
        myTopicProps = ResourceBundle.getBundle(name);
        // We must have Intact Types to search for; in other words, resource
        // bundle can't be empty.
        if (!myTopicProps.getKeys().hasMoreElements()) {
            throw new EmptyTopicsException(
                    "Editor topic resource file can't be empty");
        }
    }

    /**
     * Loads Newt properties from a resources file.
     * @param name the name of the Newt resource file.
     * @exception MissingResourceException thrown when the resource file is
     * not found.
     */
    public void loadNewtProperties(String name)
            throws MissingResourceException, MalformedURLException {
        myNewtServer = new NewtServer(name);
    }

    /**
     * Returns the class name associated with the given topic.
     * @param topic the topic to search in the Intact types resource.
     * @return the classname saved under <code>topic</code>.
     */
    public String getClassName(String topic) {
        return myTopicProps.getString(topic);
    }

    /**
     * Returns a collection of Intact types.
     * @return an <code>ArrayList</code> of Intact types. The list sorted in
     * alphabetical order.
     */
    public Collection getIntactTypes() {
        // The collection to return.
        List types = new ArrayList();
        CollectionUtils.addAll(types, myTopicProps.getKeys());
        Collections.sort(types);
        return types;
    }

    /**
     * Returns the URL of the Newt server.
     * @return the URL of the Newt server as a <code>String</code>.
     */
    public URL getNewtURL() {
        return myNewtServer.getURL();
    }

    /**
     * Returns the Newt server query parameters
     * @return the Newt server query parameters as a <code>String</code>.
     */
    public String getNewtQueryParameters() {
        return myNewtServer.getQueryParameters();
    }
}
