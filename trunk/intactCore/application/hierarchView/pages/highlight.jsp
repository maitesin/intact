<%@ page language="java" %>

<!--
   - Copyright (c) 2002 The European Bioinformatics Institute, and others.
   - All rights reserved. Please see the file LICENSE
   - in the root directory of this distribution.
   -
   - @author Samuel Kerrien (skerrien@ebi.ac.uk)
   - @version $Id$
-->

<%@ taglib uri="/WEB-INF/tld/hierarchView.tld" prefix="hierarchView" %>
<%@ taglib uri="/WEB-INF/tld/display.tld" prefix="display" %>

<%-- hierarchView highlight page

     This should be displayed in the content part of the IntAct layout,
     it displays the highlightment sources.

     author : Samuel Kerrien (skerrien@ebi.ac.uk)
 --%>

<table border="0" cellspacing="5" cellpadding="5" width="100%" heigth="100%">

      <tr>
             <td valign="top">
                   <%-- Prepare available highlightment source for the selected protein in the session --%>
                   <hierarchView:displaySource/>

                   <!-- Displays the available highlightment source -->
                   <display:table
                        name="sources" width="100%"
                        decorator="uk.ac.ebi.intact.application.hierarchView.struts.view.utils.SourceDecorator">
                           <display:column property="label" title="ID" width="25%"/>
                           <display:column property="description" width="75%" />

                           <display:setProperty name="basic.msg.empty_list" value="No source available for that protein" />
                   </display:table>

<%--
                   <br><br>Fake link to try highlight:<br>
                   <a href="http://holbein:8080/hierarchView/source.do?keys=GO:0005829"> GO:0005829 </a>

                   <br><br>Fake link get HTTP content:<br>
                   <a href="http://holbein:8080/hierarchView/displaySourceContent.do?url=http://www.google.com"> Google </a>

                   <br><br>Fake malformed link get HTTP content:<br>
                   <a href="http://holbein:8080/hierarchView/displaySourceContent.do?url=htp:/www.google.com"> Google </a>
  --%>
             </td>
      </tr>

</table>