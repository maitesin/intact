<%@ page import="org.apache.commons.beanutils.DynaBean,
                 uk.ac.ebi.intact.application.editor.struts.framework.util.EditorConstants"%><!--
  - Author: Sugath Mudali (smudali@ebi.ac.uk)
  - Version: $Id$
  - Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
  - All rights reserved. Please see the file LICENSE in the root directory of
  - this distribution.
  -->

<%--
  - Displays the results data from the search query.
  --%>

<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<jsp:useBean id="user" scope="session"
    class="uk.ac.ebi.intact.application.editor.business.EditUser"/>

    <%
        // Fill with form data for this page to display.
        String formName = EditorConstants.FORM_RESULTS;
        DynaBean dynaBean = user.createForm(formName, request);
        user.populateSearchResult(dynaBean);
        pageContext.setAttribute(formName, dynaBean);
    %>

Search class: <c:out value="${user.lastSearchClass}"/>
&nbsp;Query: <c:out value="${user.lastSearchQuery}"/>

<html:form action="/results">

<table width="100%">
    <tr class="tableRowHeader">
        <th class="tableCellHeader" width="10%">AC</th>
        <th class="tableCellHeader" width="20%">Short Label</th>
        <th class="tableCellHeader" width="70%">Full Name</th>
    </tr>
    <nested:iterate property="items">
    <tr>
        <td class="tableCell">
            <nested:link page="/do/results"
                paramId="shortLabel" paramProperty="shortLabel">
                <nested:write property="ac"/>
            </nested:link>
        </td>
        <td class="tableCell"><nested:write property="shortLabel"/></td>
        <td class="tableCell"><nested:write property="fullName"/></td>
    </tr>
    </nested:iterate>
</table>
</html:form>
