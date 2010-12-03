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
import org.joda.time.DateTime;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.RepoEntryDao;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.RepoEntryService;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.RepoEntry;

import java.util.Iterator;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: JpaEntrySetService.java 9304 2007-08-06 11:10:23Z baranda $
 */
public class JpaRepoEntryService implements RepoEntryService
{
    private RepoEntryDao entryDao;

    public void setEntryDao(RepoEntryDao entryDao) {
        this.entryDao = entryDao;
    }

    public List<RepoEntry> findAllRepoEntries() {
        return entryDao.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRepoEntry(RepoEntry repoEntry) {
        entryDao.save(repoEntry);
    }

    public RepoEntry findByPmid(String pmid) {
        return entryDao.findByPmid(pmid);
    }

    /**
     * Retrieves the importable RepoEntries that are not in the passed list
     * @param pmids the PMIDs to exclude
     * @return
     */
    public List<RepoEntry> findImportableExcluding(List<String> pmids) {
        List<RepoEntry> importables = entryDao.findImportable();

        for (Iterator<RepoEntry> repoEntryIterator = importables.iterator(); repoEntryIterator.hasNext();) {
            RepoEntry repoEntry = repoEntryIterator.next();

            if (pmids.contains(repoEntry.getPmid())) {
                 repoEntryIterator.remove();
             }
        }

        return importables;
    }

    public List<RepoEntry> findImportableModifiedAfter(DateTime dateTime) {
        return entryDao.findImportableModifierAfter(dateTime);
    }

    public Long countAll() {
        return entryDao.countAll();
    }
}