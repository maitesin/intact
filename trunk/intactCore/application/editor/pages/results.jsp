<!--
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
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/display" prefix="display" %>

<style type="text/css">
    .error {
        color: white;
        background-color: red
    }

    .owner {
        color: white;
        background-color: #ff8c00
    }
</style>

<%-- Javascript code to link to the search page. --%>
<jsp:include page="js.jsp" />

<jsp:useBean id="user" scope="session"
    class="uk.ac.ebi.intact.application.editor.business.EditUser"/>

<%-- Store in the page scope for the display library to access it --%>
<bean:define id="results" name="user" property="searchResult"
    type="java.util.List"/>

<%-- The uri when clicking on tab links --%>
<bean:define id="uri" value="<%=request.getContextPath() + "/do/showResults"%>"/>

<html:form action="/secure/edit">
    <display:table width="100%" name="results" pagesize="50" requestURI="<%=uri%>"
        decorator="uk.ac.ebi.intact.application.editor.struts.view.ResultDisplayWrapper">
        <display:column property="ac" title="AC"/>
        <display:column property="shortLabel" title="Short Label"/>
        <display:column property="fullName" title="Full Name" />
        <display:column property="lock" title="Lock" />
    </display:table>
</html:form>

<%--<html:messages id="message" message="true">--%>
<%--	<span class="warning">--%>
<%--		<bean:write name="message" filter="false"/>--%>
<%--	</span>--%>
<%--</html:messages>--%>
