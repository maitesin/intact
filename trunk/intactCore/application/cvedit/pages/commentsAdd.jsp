<%@ page language="java"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%--
    Presents the page to add an annotation to a CV object.
    Author: Sugath Mudali (smudali@ebi.ac.uk)
    Version: $Id$
--%>

<jsp:useBean id="intactuser" scope="session"
    class="uk.ac.ebi.intact.application.cvedit.business.IntactUserImpl"/>

<!-- Set the drop down lists -->
<c:set var="topiclist" value="${intactuser.topicList}"/>

<!-- Adds a new comment. This will invoke addComment action. -->
<html:form action="/cv/comment/add">
    <table class="table" width="70%" border="0" cellspacing="1" cellpadding="2">
    <tr class="tableRowHeader">
        <th width="13%" class="tableCellHeader">Topic</th>
        <th width="70%" class="tableCellHeader">Description</th>
    </tr>
    <tr class="tableRowOdd">
        <td class="tableCell" align="left" valign="top">
            <html:select property="topic">
                <html:options name="topiclist" />
            </html:select>
        </td>
        <td class="tableCell" align="left" valign="top">
            <html:textarea property="description" rows="3" cols="70"/>
        </td>
        <td class="tableCell" align="left" valign="bottom">
            <html:submit>
                <bean:message key="button.add"/>
            </html:submit>
        <td class="tableCell" align="left" valign="bottom">
            <html:reset/>
        </td>
        </td>
    </tr>
    </table>
</html:form>
