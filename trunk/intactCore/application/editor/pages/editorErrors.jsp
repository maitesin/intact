<%@ page import="org.apache.struts.action.ActionErrors,
                 org.apache.struts.action.ActionMessages"%>
<!--
  - Author: Sugath Mudali (smudali@ebi.ac.uk)
  - Version: $Id$
  - Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
  - All rights reserved. Please see the file LICENSE in the root directory of
  - this distribution.
  -->

<%--
  - The error page to display when the client side validation is turned off.
  --%>

<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/editor.tld" prefix="editor"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<logic:messagesPresent>
    <table width="80%" border="0" cellspacing="1" cellpadding="2">
        <html:messages id="error">
            <tr class="tableRowEven">
                <td class="tableErrorCell"><html:errors/></td>
            </tr>
        </html:messages>
    </table>
</logic:messagesPresent>

<logic:messagesPresent message="true">
    <table width="80%" border="0" cellspacing="1" cellpadding="2">
        <html:messages id="message" message="true">
            <tr class="tableRowEven">
                <td class="tableCell"><bean:write name="message" filter="false"/></td>
            </tr>
        </html:messages>
    </table>
</logic:messagesPresent>


