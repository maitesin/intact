<%@ page import="org.apache.commons.beanutils.DynaBean"%>
<!--
  - Author: Sugath Mudali (smudali@ebi.ac.uk)
  - Version: $Id$
  - Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
  - All rights reserved. Please see the file LICENSE in the root directory of
  - this distribution.
  -->

<%--
  - The page to searh for Proteins.
  --%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<html:form action="/interaction/protein/search">
    <table width="100%" border="0" cellspacing="1" cellpadding="2">
        <tr class="tableRowHeader">
            <th class="tableCellHeader" width="10%">Action</th>
            <th class="tableCellHeader" width="10%">Short Label</th>
            <th class="tableCellHeader" width="10%">SP AC</th>
            <th class="tableCellHeader" width="10%">IntAct AC</th>
            <th class="tableCellHeader" width="60%">Full Text</th>
        </tr>
        <tr class="tableRowEven">
            <td class="tableCell">
                <html:submit titleKey="biosource.taxid.button.titleKey">
                    <bean:message key="button.search"/>
                </html:submit>
            </td>
            <td class="tableCell">
                <html:text property="shortLabel" size="10" maxlength="16"/>
            </td>
            <td class="tableCell">
                <html:text property="spAc" size="10" maxlength="16"/>
            </td>
            <td class="tableCell">
                <html:text property="ac" size="10" maxlength="16"/>
            </td>
            <td class="tableCell">
                <html:text property="fullName" size="20" maxlength="30"/>
            </td>
        </tr>

        <%-- Prints all the error messages relevant to this page only. --%>
        <logic:messagesPresent>
            <tr class="tableRowOdd">
                <td class="tableErrorCell" colspan="5">
                    <%-- Filter out other error messages. --%>
                    <html:errors property="protein.search"/>
                </td>
            </tr>
        </logic:messagesPresent>

    </table>
</html:form>
