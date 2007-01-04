/**
 * Copyright 2006 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package uk.ac.ebi.intact.site.mb;

import uk.ac.ebi.intact.search.wsclient.SearchServiceClient;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * TODO comment this!
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class StatisticsBean implements Serializable
{
    private static final String SEARCH_WS_URL = "uk.ac.ebi.intact.SEARCH_WS_URL";

    private boolean loaded;

    private int experimentCount;
    private int interactionCount;
    private int interactorCount;
    private int cvObjectCount;

    public StatisticsBean()
    {
    }

    public synchronized void prepare(ActionEvent evt)
    {
        String wsdl = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(SEARCH_WS_URL);

        if (!loaded)
        {
            try
            {
                SearchServiceClient client = new SearchServiceClient(wsdl);
                experimentCount = client.getSearchPort().countExperimentsUsingIntactQuery("*");
                interactionCount = client.getSearchPort().countInteractionsUsingIntactQuery("*");
                interactorCount = client.getSearchPort().countInteractorsUsingIntactQuery("*");
                cvObjectCount = client.getSearchPort().countCvObjectsUsingIntactQuery("*");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            loaded = true;
        }
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public void setLoaded(boolean loaded)
    {
        this.loaded = loaded;
    }

    public int getExperimentCount()
    {
        return experimentCount;
    }

    public void setExperimentCount(int experimentCount)
    {
        this.experimentCount = experimentCount;
    }

    public int getInteractionCount()
    {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount)
    {
        this.interactionCount = interactionCount;
    }

    public int getInteractorCount()
    {
        return interactorCount;
    }

    public void setInteractorCount(int interactorCount)
    {
        this.interactorCount = interactorCount;
    }

    public int getCvObjectCount()
    {
        return cvObjectCount;
    }

    public void setCvObjectCount(int cvObjectCount)
    {
        this.cvObjectCount = cvObjectCount;
    }
}
