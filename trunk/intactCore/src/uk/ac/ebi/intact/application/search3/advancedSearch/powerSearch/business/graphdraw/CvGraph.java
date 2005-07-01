/*
* Copyright (c) 2002 The European Bioinformatics Institute, and others.
* All rights reserved. Please see the file LICENSE
* in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.search3.advancedSearch.powerSearch.business.graphdraw;

import org.apache.log4j.Logger;
 import uk.ac.ebi.intact.application.search3.advancedSearch.powerSearch.business.graphdraw.graph.*;
import uk.ac.ebi.intact.application.search3.business.Constants;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.CvDagObject;
import uk.ac.ebi.intact.model.CvIdentification;
import uk.ac.ebi.intact.model.CvInteraction;
import uk.ac.ebi.intact.model.CvInteractionType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class provides methods to create graph images for the controlled vocabulary
 *
 * @author Anja Friedrichsen, Michael Kleen
 * @version $id$
 */
public class CvGraph {

    private static Logger logger = Logger.getLogger( Constants.LOGGER_NAME );

    /**
     * string that holds the imageMap corresponding to the image
     */
    private String imageMap;

    // Basic Caching parameters
    private static final long CACHE_TIMEOUT = 2 * 60 * 1000; // 2 minutes (in milliseconds)
    private long lastImageGeneratedTime = -1;
    private HashMap imageCache = new HashMap();


    /**
     * First this method retrieves all CVs, that belong to the CV class specified in the argument, out of the database.
     * Afterwards it creates for each Cv object one node for the graph and finds all parent relations between these
     * nodes.
     *
     * @param cvName name of the CV which should be drawn
     *
     * @return image showing the DAG of the CVs
     *
     * @throws IOException
     */
    public synchronized Image createImage( String cvName ) throws IOException {

        if ( lastImageGeneratedTime == -1 ) {
            lastImageGeneratedTime = System.currentTimeMillis();
        }

        long timeElapsed = System.currentTimeMillis() - lastImageGeneratedTime;
        boolean timeout = timeElapsed > CACHE_TIMEOUT;

        logger.info( "time elapsed: " + timeElapsed );
        logger.info( "timeout: " + timeout );

        // if the image is not in the cache, create a new one
        if ( !imageCache.containsKey( cvName ) || timeout ) {

            logger.info( "Generate the picture" );

            Stroke thinStroke = new BasicStroke( 1 );
            Graph g = new Graph();
            IntactHelper iahelper = null;
            // collection that holds all cv objects as nodes
            Collection nodes = null;
            Map cvobjectToNode = new HashMap();

            // get all CvObjects of the specified Cv out of the database
            try {
                iahelper = new IntactHelper();
                if ( cvName.equalsIgnoreCase( "CvInteraction" ) ) {
                    nodes = iahelper.search( CvInteraction.class.getName(), "ac", null );
                } else if ( cvName.equalsIgnoreCase( "CvInteractionType" ) ) {
                    nodes = iahelper.search( CvInteractionType.class.getName(), "ac", null );
                } else if ( cvName.equalsIgnoreCase( "CvIdentification" ) ) {
                    nodes = iahelper.search( CvIdentification.class.getName(), "ac", null );
                } else {
                    throw new IntactException( "unknown cvName: " + cvName );
                }

            } catch ( IntactException e ) {
                e.printStackTrace();
            }

            // create for all cvDagObject a node and add these to the graph
            for ( Iterator iterator = nodes.iterator(); iterator.hasNext(); ) {
                CvDagObject o = (CvDagObject) iterator.next();
                RectangularNode node = new RectangularNode( 40, 40, o.getFullName(), "javascript:SendInfo('" + o.getShortLabel() + "')", null, o.getShortLabel(), Color.white, Color.blue, thinStroke );
                g.nodes.add( node );
                cvobjectToNode.put( o, node );
            }
            // find all parent-relations between the cvDagObjects
            for ( Iterator iterator = nodes.iterator(); iterator.hasNext(); ) {
                CvDagObject o = (CvDagObject) iterator.next();
                findParentRelations( o, cvobjectToNode, g );
            }


            HierarchicalLayout layout = new HierarchicalLayout( g, HierarchicalLayout.PARENT_TOP );
            layout.layout();

            final int width = layout.getWidth();
            final int height = layout.getHeight();
            BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

            final Graphics2D g2 = image.createGraphics();

            g2.setColor( Color.white );

            g2.fillRect( 0, 0, width, height );

            g2.setColor( Color.black );

            g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

            for ( Iterator it = g.edges.iterator(); it.hasNext(); ) {
                StrokeEdge edge = (StrokeEdge) it.next();
                edge.render( g2 );
            }

            // string that builds the imageMap
            StringBuffer sb = new StringBuffer();

            // iterate through the nodes and build the imageMap
            for ( Iterator it = g.nodes.iterator(); it.hasNext(); ) {
                RectangularNode node = (RectangularNode) it.next();
                node.render( g2 );
                sb.append( node.getImageMap() + "\n" );
            }

            // Get current size of heap in bytes
            long heapSize = Runtime.getRuntime().totalMemory();

            // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
            // Any attempt will result in an OutOfMemoryException.
            long heapMaxSize = Runtime.getRuntime().maxMemory();

            // Get amount of free memory within the heap in bytes. This size will increase
            // after garbage collection and decrease as new objects are created.
            long heapFreeSize = Runtime.getRuntime().freeMemory();

            logger.info( "in Producer: current size of heap: " + heapSize );
            logger.info( "maximum heap size: " + heapMaxSize );
            logger.info( "free heap size: " + heapFreeSize );

            // put the image into cache
            imageCache.put( cvName, image );
            // put the imageMap into cache
            imageCache.put( cvName + "Map", sb.toString() );
        } else {
            logger.info( "Use the cache" );
        }
        // set the imageMap, so it can be retrieved from another class
        this.setImageMap( (String) imageCache.get( cvName + "Map" ) );

        return (Image) imageCache.get( cvName );
    }

    /**
     * this method find the parent relations between the nodes
     *
     * @param cv             cv object to find the parents for
     * @param cvobjectToNode Map with all nodes to get the nodes corresponding to the cv object
     * @param graph          graph to generate
     */
    public void findParentRelations( CvDagObject cv, Map cvobjectToNode, Graph graph ) {

        // get node from the map
        LayoutNode child = (LayoutNode) cvobjectToNode.get( cv );
        // no children ? get outta here !
        if ( child == null ) {
            return;
        }
        // get all parents
        Collection parents = cv.getParents();
        for ( Iterator iterator = parents.iterator(); iterator.hasNext(); ) {
            CvDagObject parentObject = (CvDagObject) iterator.next();
            LayoutNode parent = (LayoutNode) cvobjectToNode.get( parentObject );
            // no parents ? get outta here !
            if ( parent == null ) {
                return;
            }

            graph.edges.add( new StrokeEdge( parent, child, Color.blue, new BasicStroke( 1 ) ) );
        }
    }

    /**
     * getter method for the imageMap
     *
     * @return string with the imageMap corresponding to the image
     */
    public String getImageMap() {
        return imageMap;
    }

    /**
     * setter method for the imageMap
     *
     * @param imageMap image map to define
     */
    public void setImageMap( String imageMap ) {
        this.imageMap = imageMap;
    }
}