/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/

package uk.ac.ebi.intact.application.graph2MIF;

import org.apache.log4j.Logger;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.Constants;
import uk.ac.ebi.intact.model.Interactor;
import uk.ac.ebi.intact.simpleGraph.Graph;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;


/**
 *  Graph2MIFServlet
 *
 *  This is a Servlet for retrieving a MIF Document using a URL-Call directly.
 *
 *  @author Henning Mersch <hmersch@ebi.ac.uk>
 *  @version $Id$
 */

public class Graph2MIFWSServlet extends HttpServlet {


    /**
     *  logger for proper information
     *  see config/log4j.properties for more informtation
     */
    static Logger logger = Logger.getLogger("graph2MIF");

    /**
     * doGet - the "main" method of a servlet.
     *
     * @param aRequest The HttpRequest - should include ac,depth and strict as parameters
     * @param aResponse HttpServletResponse for giving ansewer
     */
    public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse)
            throws ServletException, IOException {
        //parsing parameters
        PrintWriter out = null;

        try {
            String ac = aRequest.getParameter( "ac" );
            if (ac == null) {
                String msg = "You have to give an accession number in order to get an " +
                             "Interactor from the database";
                logger.error( msg );
                giveErrorMsg( msg, aResponse );

            } else {

                Boolean strictmif;
                if (aRequest.getParameter( "strict" ) != null &&
                    aRequest.getParameter( "strict" ).equalsIgnoreCase( "true" )) {
                    strictmif = Boolean.TRUE;
                } else {
                    strictmif = Boolean.FALSE;
                }

                Integer depth = new Integer(aRequest.getParameter( "depth" ));

                // call getMIF to retrieve and convert
                Graph graph = getInteractionNetwork( ac, depth ); //NoGraphRetrievedExceptioni, IntactException and NoInteractorFoundException possible
                logger.info("got graph:");
                logger.info( graph );

                //convert graph to DOM Object
                logger.info ( "Start creating the MIF folded version of that graph." );
                Graph2FoldedMIF convert = new Graph2FoldedMIF( strictmif );
                Document mifDOM = convert.getMIF( graph ); // GraphNotConvertableException possible
                logger.info ( "Convertion finished." );

                // serialize the DOMObject
                StringWriter w = new StringWriter( 4096 );
                OutputFormat of = new OutputFormat( mifDOM, "UTF-8", true ); //(true|false) for (un)formated  output
                XMLSerializer xmls = new XMLSerializer( w, of );
                try {
                    xmls.serialize( mifDOM );
                } catch (IOException e) {
                    logger.warn("IOException while serialize" + e.getMessage());
                    giveErrorMsg("ERROR: DOM-Object could not be serialized (" + e.toString() + ")",aResponse);
                }
                String mif = w.toString();

                logger.info ( "Set MIME type to: text/xml" );
                aResponse.setContentType( "text/xml" );

                logger.info ( "Printing XML data on the output." );
                out = aResponse.getWriter();
                out.println( mif );
                //or get errors and give back.
            }
        } catch (NumberFormatException e) {
            giveErrorMsg("depth should be an integer",aResponse);
            logger.error(e);
        } catch (IntactException e) {
            giveErrorMsg("ERROR: Search for interactor failed (" + e.toString() + ")",aResponse);
            logger.error(e);
        } catch (NoInteractorFoundException e) {
            giveErrorMsg("ERROR: No Interactor found for this ac (" + e.toString() + ")",aResponse);
            logger.error(e);
        } catch (NoGraphRetrievedException e) {
            giveErrorMsg("ERROR: Could not retrieve graph from interactor (" + e.toString() + ")",aResponse);
            logger.error(e);
        } catch (GraphNotConvertableException e) {
            giveErrorMsg("ERROR: Graph failed requirements of MIF. (" + e.toString() + ")",aResponse);
            logger.error(e);
        } catch (NullPointerException e) {
            giveErrorMsg("ERROR: wrong parameters:\n usage is: <host>/graph2mif/getXML?ac=<ac>&amp;depth=<int>&amp;strict=(true|false)\n" +
                         "\tac\taccession number\n" +
                         "\tdepth\tdepth of graph\n" +
                         "\tstrict\t(true|false) for retrieval of strict MIF or not.",aResponse);
            logger.error(e);
        } finally {
            if (out != null) out.close();
        }
    }

    /**
     * getInteractionNetwork retrieves a interactionnetwork (graph) from a given ac and depth
     * @param ac String ac in IntAct
     * @param depth Integer of the depth the graph should be expanded
     * @return graph of the ac with given depth
     * @exception IntactException thrown if search for interactor failed
     * @exception uk.ac.ebi.intact.application.graph2MIF.NoGraphRetrievedException thrown if DOM-Object could not be serialized
     * @exception uk.ac.ebi.intact.application.graph2MIF.NoInteractorFoundException thrown if no Interactor found for ac
     */
    private Graph getInteractionNetwork(String ac, Integer depth) throws IntactException, NoInteractorFoundException, NoGraphRetrievedException {

        //create helper
        IntactHelper helper = new IntactHelper();
        logger.info( "Helper created" );

        //for graph retrieval a interactor is necessary. So get the interactor of given ac.
        Collection interactors = null;
        try {
            logger.info( "Retrieve Interactor from AC("+ ac +")" );
            interactors = helper.search( Interactor.class.getName(), "ac", ac );
        } catch (IntactException e) {
            logger.error( "Could not search for Interactor AC: " + ac, e );
            throw e;
        }

        logger.info( interactors.size() + " Interactor found." );

        Interactor interactor = null;
        // just take the 1st element - there should be no 2nd if searche for an ac !
        Iterator interactorIterator = interactors.iterator();
        if (interactorIterator.hasNext()) {
            interactor = (Interactor) interactorIterator.next();
            logger.info ( "Get interactor: " + interactor );
        } else { //No Interactor found
            logger.warn("No Interactor found for: " + ac);
            throw new NoInteractorFoundException();
        }

        // retrieve the graph with interactor as root element and given depth
        Graph graph = new Graph();
        try {
            logger.info ( "Start building an Interaction Network from AC: "+ interactor.getAc() +
                          ", depth: "+ depth +"." );
            graph = helper.subGraph( interactor,
                    depth.intValue(),
                    null,
                    Constants.EXPANSION_BAITPREY,
                    graph );
        } catch (IntactException e) {
            logger.warn("IntActException while subgraph() call " + e.getMessage(), e);
            throw new NoGraphRetrievedException();
        }

        if (graph == null) {
            logger.warn("retrieved graph == null");
            throw new NoGraphRetrievedException();
        }

        logger.info ("Return the graph.");

        //return this graph
        return graph;
    }

    /**
     * giveErrorMsg will give return an error message to the user as text/HTML !
     *
     * @param errormsg The string included in the errormessage
     * @param res HttpResponse
     */
    private void giveErrorMsg(String errormsg, HttpServletResponse res) {
        res.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = res.getWriter();
        } catch (IOException e) {
            logger.error(e); // we cant give back an error ... give up.
        }
        out.println("<html><head><title>An error occoured ...</title></head><body>");
        out.println("<h1>Sorry - an error occoured during processing your request:<h1><pre>");
        out.println(errormsg);
        out.println("</pre></body></html>");
    }
}
