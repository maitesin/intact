<!--
  - Author: Sugath Mudali (smudali@ebi.ac.uk)
  - Version: $Id$
  - Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
  - All rights reserved. Please see the file LICENSE in the root directory of
  - this distribution.
  -->

<%--
  - This page consists of action buttons at the bottom of the editing page.
  --%>

<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<script language="JavaScript" type="text/javascript">

    function confirmDelete() {
        skipValidation();
        return window.confirm("Do you want to delete this CV? Press OK to confirm");
    }

    // Skip client-side javascript validation
    function skipValidation() {
        bCancel = true;
    }

    // Contains general sanity checking rules. Not serious errors but just warning
    // messages.
    function sanityCheck() {
        var label = document.forms[0].shortLabel.value;
        var size = label.length;
        var suffix = label.substring(size -2, size);
        if (suffix == '-x') {
            return window.confirm('Short label contains the suffix -x, do you want to save as it is?');
        }
    }

</script>

<jsp:useBean id="user" scope="session"
    class="uk.ac.ebi.intact.application.editor.business.EditUser"/>

<bean:define id="view" name="user" property="view"
    type="uk.ac.ebi.intact.application.editor.struts.framework.util.AbstractEditViewBean"/>

<%-- Read only if the view says so --%>
<bean:define id="disable" name="view" property="readOnly" type="java.lang.Boolean"/>

<table class="table" width="100%" cellspacing="1" cellpadding="2">
    <tr class="tableRowOdd">
        <td align="center" bgcolor="green">
            <html:submit property="dispatch"
                disabled="<%=disable.equals(Boolean.TRUE)%>"
                onclick="return sanityCheck()">
                <bean:message key="button.submit"/>
            </html:submit>
            <br/><bean:message key="button.submit.titleKey"/>
        </td>

        <td align="center" bgcolor="palegreen">
            <html:submit property="dispatch"
                disabled="<%=disable.equals(Boolean.TRUE)%>"
                onclick="return sanityCheck()">
                <bean:message key="button.save.continue"/>
            </html:submit>
            <br/><bean:message key="button.save.continue.titleKey"/>
        </td>

        <%-- Clone button if the view says so --%>
        <c:if test="${view.cloneState}">
            <td align="center" bgcolor="limegreen">
            <html:submit property="dispatch">
                <bean:message key="button.clone"/>
            </html:submit>
                <br/><bean:message key="button.clone.titleKey"/>
            </td>
        </c:if>

        <td align="center" bgcolor="yellow">
            <html:submit property="dispatch" onclick="skipValidation();">
                <bean:message key="button.cancel"/>
            </html:submit>
            <br/><bean:message key="button.cancel.titleKey"/>
        </td>

        <%-- Delete button if the view says so --%>
        <c:if test="${view.deleteState}">
            <td align="center" bgcolor="red">
                <html:submit property="dispatch" onclick="return confirmDelete()"
                    disabled="<%=disable.equals(Boolean.TRUE)%>">
                    <bean:message key="button.delete"/>
                </html:submit>
                <br/><bean:message key="button.delete.titleKey"/>
            </td>
        </c:if>
    </tr>
</table>
