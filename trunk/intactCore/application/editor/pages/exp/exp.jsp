<!--
  - Author: Sugath Mudali (smudali@ebi.ac.uk)
  - Version: $Id$
  - Copyright (c) 2002-2003 The European Bioinformatics Institute, and others.
  - All rights reserved. Please see the file LICENSE in the root directory of
  - this distribution.
  -->

<%--
  - This page accepts changes to an Annotated object's short label and full name.
  --%>

<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<script language="JavaScript" type="text/javascript">
    // Set the hidden interaction field when the user clicks on any Interaction
    // button.
    function setIntAc(ac) {
        document.forms["expForm"].intac.value=ac;
    }
</script>

<jsp:include page="../js.jsp" />

<%-- Include javascript for show user functionality --%>
<jsp:include page="../misc/user_js.jsp"/>

<html:form action="/expDispatch" onsubmit="return validateExpForm(this)">
    <html:hidden property="intac" />
    <html:hidden property="anchor" />

    <jsp:include page="info.jsp" />
    <p></p>
    <jsp:include page="ints.jsp" />
    <p></p>
    <jsp:include page="intsHold.jsp" />
    <jsp:include page="intSearch.jsp" />
    <p></p>
    <jsp:include page="../annots.jsp" />
    <jsp:include page="../addAnnots.jsp" />
    <jsp:include page="../xrefs.jsp" />
    <jsp:include page="../addXrefs.jsp" />

    <p></p>
    <jsp:include page="../action.jsp" />

</html:form>
<html:javascript formName="expForm"/>