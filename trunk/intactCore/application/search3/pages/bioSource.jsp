<!--
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
-->

<!-- Page to display a single Object view.This page view will display single CvObjects and BioSource Objects.

    @author Michael Kleen
    @version $Id$
-->

<%@ page language="java" %>

<%-- Intact classes needed --%>
<%@ page import="uk.ac.ebi.intact.application.search3.struts.framework.util.SearchConstants,
                 java.util.Iterator,
                 uk.ac.ebi.intact.model.Annotation,
                 uk.ac.ebi.intact.model.Xref,
                 uk.ac.ebi.intact.application.search3.struts.view.beans.BioSourceViewBean,                 
                 uk.ac.ebi.intact.application.search3.struts.view.beans.XrefViewBean,
                 java.util.Collection,
                 uk.ac.ebi.intact.application.search3.struts.view.beans.AnnotationViewBean"
    %>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
   BioSourceViewBean bean = (BioSourceViewBean)session.getAttribute(SearchConstants.VIEW_BEAN);
%>

<span class="smalltext"> </span>

<h3>Search Results for
    <%=session.getAttribute(SearchConstants.SEARCH_CRITERIA)%>
</h3>
<!-- show the searched object ac -->
<span class="smalltext">(short labels of search criteria matches are
    <span style="color: rgb(255, 0, 0);">highlighted</span>
</span><span class="smalltext">)<br></span>
<span class="smalltext"><br></span>

<form name="viewForm">
   <!-- create a table with the object deatils -->
   <table style="width: 100%; background-color: rgb(51, 102, 102);" cellpadding="2">
        <tbody>
            <!-- header row -->
             <tr bgcolor="white">

                <!-- main label --->
                    <td class="headerdark">
                        <nobr>
                        <!-- set Help Link -->
                        <span class = "whiteheadertext">
                        <%=bean.getIntactType() %>
                        </span>
                        <a href="<%= bean.getHelpLink() + "BioSource"%>"
                        target="new"><sup><b><font color="white">?</font></b></sup></a></nobr>
                    </td>

                     <!-- ac -->
                     <td class="headerdarkmid">
                        <a href="<%= bean.getHelpLink() + "BasicObject.ac"%>"
                            target="new" class="tdlink">Ac:</a> <%= bean.getObjAc() %>
                     </td>


                    <!-- shortlabel -->
                    <td class="headerdarkmid" colspan="4">
                        <a href="<%= bean.getHelpLink() + "AnnotatedObject.shortLabel"%>"
                            class="tdlink"
                            target="new"><span style="color: rgb(102, 102, 204);">IntAct </span>name:</a>
                        <a href="<%= bean.getObjSearchURL() %>" class="tdlink" style="font-weight: bold;">
                        <b><span style="color: rgb(255, 0, 0);"><%= bean.getObjIntactName() %></span></b></a>
                    </td>

<%  Collection someXrefBeans =  bean.getXrefs();
    Collection someAnnotations = bean.getFilteredAnnotations();
    if(someXrefBeans.size() != 0 && someAnnotations.size() != 0 )  {
%>

                    <!-- space for the table -->
                    <td class="headerdarkmid">
                         &nbsp;
                     </td>


 <% } %>
                </tr>

                <!-- Shortname of the object -->
                 <tr bgcolor="white">
                 <td class="headerdarkmid">
                            <a href="<%=bean.getHelpLink() + "AnnotatedObject.shortLabel" %>" class="tdlink">
                               Name

                            </a>
                      </td>
                    <!-- Fullname of the object -->
                    <td colspan="4" >
                        <%= bean.getFullname() %>
                    </td>

                </tr>


<!-- list all the anotations -->
<%

    if(!someAnnotations.isEmpty())  {
    //get the first one and process it on its own - it seems we need it to put a search
    //link for it into the first cell of the row, then process the others as per
    //'usual'
    AnnotationViewBean firstAnnotation = (AnnotationViewBean) someAnnotations.iterator().next();
%>
     <tr bgcolor="white">
          <!-- Annotation Help Section -->
         <td class="headerdarkmid" rowspan="<%= someAnnotations.size() %>">
                    <a href="<%= bean.getHelpLink() + "ANNOT_HELP_SECTION" %>" class="tdlink">
                    Annotations <br>
                    </a>
                </td>

<%
        for(Iterator it = someAnnotations.iterator(); it.hasNext();) {
             AnnotationViewBean anAnnotation = (AnnotationViewBean) it.next();
            if(!anAnnotation.equals(firstAnnotation)) {
            //we need to have new rows for each Annotations OTHER THAN the first..
%>
               <tr bgcolor="white">
 <% } %>
                <!-- Annotation name -->
                <td td class="data">
                    <a href="<%=bean.getSearchUrl(anAnnotation.getObject().getCvTopic()) %>" >
                    <%=anAnnotation.getName()%>
                 </td>
                      <!-- todo -->
                      <td td class="data" colspan="4" >
                         <%=anAnnotation.getText()%>
                     </td>
               </tr>
        <% } %>
  <% } %>


    <!-- list all xref -->
<%  // get aall Xrefs from the beans
    // not very nice but its working should be done by the wrapped object not in the jsp
    if(!someXrefBeans.isEmpty())  {
        //get the first one and process it on its own - it seems we need it to put a search
        //link for it into the first cell of the row, then process the others as per
        //'usual'
        XrefViewBean firstXref = (XrefViewBean) someXrefBeans.iterator().next();
%>

     <tr bgcolor="white">
                       <!-- Xref Name + help secion -->
                       <td class="headerdarkmid" rowspan="<%= someXrefBeans.size() %>">
                            <a href="<%= bean.getHelpLink() + "XREF_HELP_SECTION" %>" class="tdlink">
                            Xref
                            </a>
                        </td>


    <%    for(Iterator it1 = someXrefBeans.iterator(); it1.hasNext();) {
            XrefViewBean aXref = (XrefViewBean) it1.next();
%>

<%
            if(!aXref.equals(firstXref)) {
            //we need to have new rows for each Xref OTHER THAN the first..
%>
                <tr bgcolor="white">
<%
            }    
%>
                  <!-- Xref Name -->
                  <td class="data">
                           <a href="<%= bean.getSearchUrl(aXref.getObject().getCvDatabase()) %>">
                           <%=aXref.getName()%>
                           </a>
                   </td>

                      <!-- Xref Primary Id -->
                      <td class="data">
                        <% if(!aXref.getSearchUrl().equalsIgnoreCase("-")) { %>
                                <a href="<%=aXref.getSearchUrl() %>">
                           <% } %>
                            <%=aXref.getPrimaryId()%>
                     </td>


           <% if(!aXref.getSecondaryId().equalsIgnoreCase("-")) { %>

                     <!-- Xref secondary Id -->
                     <td class="data">
                          <%=aXref.getSecondaryId()%>
                     </td>
          <%  }    %>

                      <!-- type -->
                     <td class="data">
                        <a href="<%=bean.getHelpLink() + "Xref.cvrefType" %>" target="new"/>
                        <%=aXref.getType()%>
                        </a>
                        &nbsp;
                        <% if(!aXref.getXrefQualifierName().equalsIgnoreCase("-")) { %>
                                <a href="<%=bean.getSearchUrl(aXref.getObject().getCvXrefQualifier())%>">
                                <% } %>
                        <%=aXref.getXrefQualifierName() %>
                        </a>
                     </td>
               </tr>
    <% }  %>

  <%   } %>

        </tbody>
    </table>
</form>