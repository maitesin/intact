<%@ page language="java" %>

<!--
   - Copyright (c) 2002 The European Bioinformatics Institute, and others.
   - All rights reserved. Please see the file LICENSE
   - in the root directory of this distribution.
   -
   - This displays a bunch of radio button which allows the user to select the behaviour
   - of its click on the interaction network.
   -
   - @author Samuel Kerrien (skerrien@ebi.ac.uk)
   - @version $Id$
-->

<%@ page import="uk.ac.ebi.intact.application.hierarchView.business.IntactUserI,
                 uk.ac.ebi.intact.application.hierarchView.business.Constants"%>

<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
    /**
     * Retreive user's data from the session
     */
    IntactUserI user = (IntactUserI) session.getAttribute (Constants.USER_KEY);

    if (user == null) {
        // no user in the session, don't display anything
        return;
    }

   if (user.InteractionNetworkReadyToBeDisplayed()) {

       String centerItem = "";
       String addItem    = "";
       String radio    = "<img src=\"../images/radio.png\" border=\"0\">";
       String radioChk = "<img src=\"../images/radio-chk.png\" border=\"0\">";

        if (user.clickBehaviourIsAdd ()) {
            addItem = radioChk;
            centerItem = "<a href=\"/hierarchView/clickBehaviour.do?action=center\" target=\"sidebarFrame\">" + radio + "</a>";
        } else {
            // default
            addItem = "<a href=\"/hierarchView/clickBehaviour.do?action=add\" target=\"sidebarFrame\">" + radio + "</a>";
            centerItem = radioChk;
        }
%>

<br>
<hr>

<!-- click behaviour section -->

    <table width="100%">
        <tr>
            <td>
                <strong><bean:message key="sidebar.click.section.title"/><strong>
            </td>
        </tr>

        <tr>
            <td>
                <%= centerItem %> <bean:message key="sidebar.click.center.title"/>
            </td>
        </tr>

        <tr>
            <td>
                <%= addItem %> <bean:message key="sidebar.click.add.title"/>
            </td>
        </tr>
    </table>

<%
   } // if InteractionNetworkReady
%>