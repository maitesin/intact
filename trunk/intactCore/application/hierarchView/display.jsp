<%@ page language="java" %>

<!--
   - Copyright (c) 2002 The European Bioinformatics Institute, and others.
   - All rights reserved. Please see the file LICENSE
   - in the root directory of this distribution.
   -
   - Allows to forward to the main page of the application.
   -
   - @author Samuel Kerrien (skerrien@ebi.ac.uk)
   - @version $Id$
-->

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<html:html>

<head>
     <base target="_top"/>
</head>

<body bgcolor="white">

    <form action="<%=request.getContextPath()%>/display.do" method="post">
          <input type="hidden" name="host" />
          <input type="hidden" name="protocol" />

          <input type="hidden" name="AC"     value="<%= request.getParameter ("AC")     %>" />
          <input type="hidden" name="depth"  value="<%= request.getParameter ("depth")  %>" />
          <input type="hidden" name="method" value="<%= request.getParameter ("method") %>" />
    </form>

    <script language="JavaScript" type="text/javascript">
        <!--
            // gives the current host and protocol from the browser URL.
            document.forms[0].host.value     = window.location.host;
            document.forms[0].protocol.value = window.location.protocol;

            // submit the form automatically toward the entry page
            document.forms[0].submit();
        //-->
    </script>


</body>

</html:html>
