/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.dataexchange.imex.repository.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.ProviderDao;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.ProviderService;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.Provider;

import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class JpaProviderService implements ProviderService {

    private ProviderDao providerDao;

    public void setProviderDao(ProviderDao providerDao) {
        this.providerDao = providerDao;
    }

    public List<Provider> findAllProviders() {
        return providerDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveProvider(Provider provider) {
        providerDao.save(provider);
    }

    public Provider findByName(String name) {
        try {
            return providerDao.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }
}