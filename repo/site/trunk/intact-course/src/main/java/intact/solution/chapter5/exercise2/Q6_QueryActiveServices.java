/**
 * Copyright 2011 The European Bioinformatics Institute, and others.
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
 * limitations under the License.
 */

package intact.solution.chapter5.exercise2;

import org.hupo.psi.mi.psicquic.registry.ServiceType;
import org.hupo.psi.mi.psicquic.registry.client.registry.DefaultPsicquicRegistryClient;
import org.hupo.psi.mi.psicquic.registry.client.registry.PsicquicRegistryClient;
import org.hupo.psi.mi.psicquic.wsclient.PsicquicSimpleClient;

import java.util.List;

/**
 * Question 6: Could you query all the active PSICQUIC services and print the partial and
 * total interaction counts for P04637 (TP53)?
 *
 * @see org.hupo.psi.mi.psicquic.registry.client.registry.DefaultPsicquicRegistryClient
 * @see org.hupo.psi.mi.psicquic.registry.client.registry.DefaultPsicquicRegistryClient#listActiveServices()
 * @see org.hupo.psi.mi.psicquic.registry.ServiceType
 * @see org.hupo.psi.mi.psicquic.wsclient.PsicquicSimpleClient
 * @see org.hupo.psi.mi.psicquic.wsclient.PsicquicSimpleClient#countByInteractor(String)
 */
public class Q6_QueryActiveServices {

    public static void main(String[] args) throws Exception {

        final String query = "P04637";  // TP53, P53

        long totalCount = 0;

        PsicquicRegistryClient registryClient = new DefaultPsicquicRegistryClient();

        List<ServiceType> services = registryClient.listActiveServices();

        for (ServiceType service : services) {
            System.out.println("Service: "+service.getName());

            // instantiate the PSICQUIC simple client
            PsicquicSimpleClient client = new PsicquicSimpleClient(service.getRestUrl());

            // count the interactions for our query (which happens to be an interactor)
            long interactionCount = client.countByInteractor(query);

            System.out.println("\tInteractions: "+interactionCount);

            totalCount += interactionCount;
        }

        System.out.println("Total interactions: "+totalCount);

    }
}
