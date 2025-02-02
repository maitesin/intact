/*
 * Copyright 2001-2008 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.view.webapp.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.hupo.psi.calimocho.io.IllegalRowException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import uk.ac.ebi.intact.dataexchange.psimi.solr.params.UrlSolrParams;
import uk.ac.ebi.intact.view.webapp.IntactViewException;
import uk.ac.ebi.intact.view.webapp.controller.config.IntactViewConfiguration;
import uk.ac.ebi.intact.view.webapp.controller.search.UserQuery;
import uk.ac.ebi.intact.view.webapp.io.BinaryInteractionsExporter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;


/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ExportServlet extends HttpServlet {

    private static final Log log = LogFactory.getLog( ExportServlet.class );

    public static final String PARAM_SORT = "sort";
    public static final String PARAM_SORT_ASC = "asc";
    public static final String PARAM_QUERY = "query";
    public static final String PARAM_FORMAT = "format";

    private WebApplicationContext applicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = getServletContext();
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IntactViewConfiguration intactViewConfiguration = (IntactViewConfiguration) applicationContext.getBean("intactViewConfiguration");

        SolrServer solrServer = intactViewConfiguration.getInteractionSolrServer();

        String searchQuery = request.getParameter(PARAM_QUERY);
        String format = request.getParameter(PARAM_FORMAT);

        if( searchQuery != null ) {
            searchQuery = URLDecoder.decode( searchQuery, "UTF-8" );
        } else {
            searchQuery = (String) request.getSession().getAttribute(UserQuery.SESSION_SOLR_QUERY_KEY);
            searchQuery = URLDecoder.decode( searchQuery, "UTF-8" );
        }

        if (searchQuery == null) {
            throw new IntactViewException("No query provided");
        }
        String extension = getExtension(format);
        
        String fixedQuery = searchQuery;
        if (fixedQuery.contains("&")){
            fixedQuery = searchQuery.substring(0, searchQuery.indexOf("&"));
        }
        fixedQuery = fixedQuery.replaceAll("q=", "");
        fixedQuery = fixedQuery.replaceAll(":","_");
        fixedQuery = fixedQuery.replaceAll(" ","_");
        fixedQuery = fixedQuery.replaceAll("\\(","_");
        fixedQuery = fixedQuery.replaceAll("\\)","_");

        String name = fixedQuery.substring(0, Math.min(10, fixedQuery.length())) + "." + extension;

        String sortColumn = request.getParameter(PARAM_SORT);
        String sortAsc = request.getParameter(PARAM_SORT_ASC);

        boolean sort = Boolean.parseBoolean(sortAsc);

        SolrQuery solrQuery = convertToSolrQuery(searchQuery);
        solrQuery.setStart( 0 );

        if (sortColumn != null) {
            solrQuery.setSortField(sortColumn, sort ? SolrQuery.ORDER.asc : SolrQuery.ORDER.desc );
        }

        BinaryInteractionsExporter exporter = new BinaryInteractionsExporter(solrServer);

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename="+name);

        try {
            String contentType = exporter.searchAndExport(response.getOutputStream(), solrQuery, format);
        } catch (IllegalRowException e) {
            log.error("Cannot export in XGMML", e);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));

            try{
                writer.write("Impossible to export the results in XGMML, an internal error occurred.");
            }
            finally {
                writer.close();
            }

        }
    }

    public String getExtension( String format ) throws IOException {
        if ( BinaryInteractionsExporter.MITAB.equals( format ) || BinaryInteractionsExporter.MITAB_INTACT.equals( format ) || BinaryInteractionsExporter.RDF_N3.equals(format) || BinaryInteractionsExporter.RDF_N3_PP.equals(format)
                || BinaryInteractionsExporter.RDF_TRIPLE.equals(format) || BinaryInteractionsExporter.RDF_TURTLE.equals(format)) {
            return "txt";
        } else if ( BinaryInteractionsExporter.XML_2_53.equals( format ) || BinaryInteractionsExporter.XML_2_54.equals( format ) || BinaryInteractionsExporter.BIOPAX_L2.equals(format) || BinaryInteractionsExporter.BIOPAX_L3.equals(format) || BinaryInteractionsExporter.RDF_XML.equals(format)
                || BinaryInteractionsExporter.RDF_XML_ABBREV.equals(format) || BinaryInteractionsExporter.XGMML.equals(format)) {
            return "xml";
        } else if ( BinaryInteractionsExporter.XML_HTML.equals( format ) ) {
            return "html";
        } else {
            throw new IntactViewException( "Format is not correct: " + format + ". Possible values: mitab, mitab_intact." );
        }

    }

    private SolrQuery convertToSolrQuery(String searchQuery) {
         if (searchQuery == null) {
             throw new NullPointerException("You must give a non null searchQuery");
         }

         SolrQuery solrQuery = new SolrQuery();
         solrQuery.add(new UrlSolrParams(searchQuery));

        solrQuery.set(org.apache.solr.common.params.CommonParams.SORT, solrQuery.getSortField().replaceAll("\\+", " "));

         if (log.isDebugEnabled()) {
             log.debug("Given Solr query:     " + searchQuery);
             log.debug("converted Solr Query: " + solrQuery.toString());
         }

         return solrQuery;
     }
}