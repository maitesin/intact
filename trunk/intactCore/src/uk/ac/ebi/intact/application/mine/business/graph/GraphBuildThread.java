/*
 * Created on 26.07.2004
 */

package uk.ac.ebi.intact.application.mine.business.graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import jdsl.graph.api.Vertex;
import jdsl.graph.ref.IncidenceListGraph;

import org.apache.log4j.Logger;
import org.shiftone.cache.Cache;

import uk.ac.ebi.intact.application.mine.business.Constants;
import uk.ac.ebi.intact.application.mine.business.IntactUserI;
import uk.ac.ebi.intact.application.mine.business.graph.model.EdgeObject;
import uk.ac.ebi.intact.application.mine.business.graph.model.GraphData;
import uk.ac.ebi.intact.application.mine.business.graph.model.NodeObject;

/**
 * @author Andreas Groscurth
 */
public class GraphBuildThread extends Thread {
	private static final String SELECT_QUERY =
		"SELECT * FROM ia_interactions WHERE graphid=";

	static transient Logger logger = Logger.getLogger(Constants.LOGGER_NAME);

	// the static values are set by the GraphManager !
	private Cache cache;
	private Set running;

	private Integer toProcceed;
	private IntactUserI user;

	/**
	 * Creates new Thread to build a graph for the given ID
	 * 
	 * @param i the graphID
	 */
	public GraphBuildThread(Integer i, IntactUserI user, Cache cache, Set running) {
		toProcceed = i;
		this.user = user;
		this.cache = cache;
		this.running = running;
	}

	public void run() {
		GraphData gd;
		try {
			// build the graph
			gd = buildGraph(toProcceed);
			// the removal of the graphid and the adding of the graph is
			// synchronized to avoid that another thread wants to read the
			// running structure and build a graph whilst this thread removed it
			// and pushed it into the cache
			synchronized (running) {
				// the data is stored in the cache
				cache.addObject(toProcceed, gd);
				logger.info(toProcceed + " is added to the cache");
				// work is done -> remove it from the running structure
				running.remove(toProcceed);
			}
		} catch (SQLException e) {
			logger.warn(e);
		}

	}

	/**
	 * Method to build the graph for the provided graphid - works on database
	 * level
	 * 
	 * @param graphid the graphID
	 * @return the graphData
	 * @throws SQLException
	 */
	private GraphData buildGraph(Integer graphid) throws SQLException {
		logger.info("build graph for " + graphid);
		Statement stm = user.getDBConnection().createStatement();
		ResultSet set = null;
		IncidenceListGraph graph = null;
		Vertex v1, v2;
		String protein1_ac,
			protein2_ac,
			interaction_ac,
			shortLabel1,
			shortLabel2;

		Map nodeLabelMap = new Hashtable();

		set = stm.executeQuery(SELECT_QUERY + graphid);
		// the graph is initialised
		graph = new IncidenceListGraph();
		while (set.next()) {
			// the two interactors are fetched
			protein1_ac = set.getString(1).trim().toUpperCase();
			shortLabel1 = set.getString(2);
			protein2_ac = set.getString(3).trim().toUpperCase();
			shortLabel2 = set.getString(4);
			// the interaction_ac of the interactions
			interaction_ac = set.getString(6);
			// if the map does not contain the interactor_ac
			// this means the interactor is not yet in the graph
			if (!nodeLabelMap.containsKey(protein1_ac)) {
				v1 =
					graph.insertVertex(
						new NodeObject(protein1_ac, shortLabel1));
				nodeLabelMap.put(protein1_ac, v1);
			}

			// if the map does not contain the interactor_ac
			// this means the interactor is not yet in the graph
			if (!nodeLabelMap.containsKey(protein2_ac)) {
				v2 =
					graph.insertVertex(
						new NodeObject(protein2_ac, shortLabel2));
				nodeLabelMap.put(protein2_ac, v2);
			}

			// because it can happens that just one of the if tests
			// is succesful which means the protein1_ac may be old and
			// different
			// to the node v1 - the correct node for the given
			// interactor_ac has to be fetched from the map
			v1 = (Vertex) nodeLabelMap.get(protein1_ac);
			v2 = (Vertex) nodeLabelMap.get(protein2_ac);

			// the edge between these two nodes is inserted
			graph.insertEdge(
				v1,
				v2,
				new EdgeObject(interaction_ac, set.getDouble(7)));
		}
		set.close();
		stm.close();
		return new GraphData(graph, nodeLabelMap);
	}
}