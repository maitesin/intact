<%@ page language="java" %>

<%@ page import="uk.ac.ebi.intact.application.hierarchView.business.IntactUserI,
                 uk.ac.ebi.intact.application.hierarchView.business.Constants"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%--
    This layout displays graph management components for hierarchView.
    According to the current state of the displayed graph : its depth,
    we display button in order to get expanded or contracted.
    We show only available options (e.g. if the depth can be desacrease
    we don't show the contract button).

    Author: Samuel Kerrien (skerrien@ebi.ac.uk)
--%>

<%
    /**
     * Retreive user's data from the session
     */
    IntactUserI user = (IntactUserI) session.getAttribute (Constants.USER_KEY);

    if (user == null) {
        // no user in the session, don't display anything
        return;
    }
%>

<%
   if (user.InteractionNetworkReadyToBeDisplayed()) {
%>

<br>
<hr>

<!-- Graph section -->
<html:form action="/interactionNetwork">

    <table width="100%">
        <tr>
          <th colspan="2">
             <div align="left">
                <strong><bean:message key="sidebar.graph.section.title"/></strong>
             </div>
          </th>
        </tr>

        <tr>
            <td width="50%">
                <%
                    if (user.maximalDepthReached() == false) {
                %>
                    <html:submit property="action" titleKey="sidebar.graph.button.expand.title">
                        <bean:message key="sidebar.graph.button.expand"/>
                    </html:submit>
                <%
                    }
                %>
            </td>

            <td width="50%">
                <%
                    if (user.minimalDepthReached() == false) {
                %>
                    <html:submit property="action" titleKey="sidebar.graph.button.contract.title">
                        <bean:message key="sidebar.graph.button.contract"/>
                    </html:submit>
                <%
                    }
                %>
            </td>
        </tr>
    </table>

</html:form>

<%
   } // if InteractionNetworkReady
%>