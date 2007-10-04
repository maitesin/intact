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
package uk.ac.ebi.intact.dataexchange.imex.imp;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.core.unit.IntactUnit;
import uk.ac.ebi.intact.dataexchange.imex.repository.ImexRepositoryContext;
import uk.ac.ebi.intact.dataexchange.imex.repository.Repository;
import uk.ac.ebi.intact.model.Institution;
import uk.ac.ebi.intact.model.meta.ImexImport;
import uk.ac.ebi.intact.model.meta.ImexImportStatus;

import java.io.File;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ImexImporterTest extends IntactBasicTestCase {

    private Repository repository;

    @Before
    public void before() throws Exception {
        new IntactUnit().createSchema();
        beginTransaction();

        repository = createRepositoryWithMint();
    }

    @After
    public void after() throws Exception {
        commitTransaction();

        repository.close();
    }

    @Test
    public void importNewAndFailed() throws Exception {
        Institution mint = new Institution("mint");
        persistImexImport(mint, "15733859", ImexImportStatus.OK);
        persistImexImport(mint, "15733864", ImexImportStatus.ERROR);

        ImexImporter imexImporter = new ImexImporter(repository);
        ImportReport report = imexImporter.importNewAndFailed();

        Assert.assertEquals(2, report.getSucessfullPmids().size());
        Assert.assertEquals(1, report.getNewPmisImported().size());
        Assert.assertEquals(0, report.getFailedPmids().size());
        Assert.assertEquals(0, report.getPmidsNotFoundInRepo().size());
    }

    @Test
    public void importFailed() throws Exception {
        Institution mint = new Institution("mint");
        persistImexImport(mint, "15733859", ImexImportStatus.OK);
        persistImexImport(mint, "15733864", ImexImportStatus.ERROR);
        persistImexImport(mint, "15757671", ImexImportStatus.ERROR);

        commitTransaction();

        beginTransaction();
        Assert.assertEquals(ImexImportStatus.OK, getDaoFactory().getImexImportDao().getByPmid("15733859").getStatus());
        Assert.assertEquals(ImexImportStatus.ERROR, getDaoFactory().getImexImportDao().getByPmid("15733864").getStatus());
        Assert.assertEquals(ImexImportStatus.ERROR, getDaoFactory().getImexImportDao().getByPmid("15757671").getStatus());
        commitTransaction();

        ImexImporter imexImporter = new ImexImporter(repository);
        ImportReport report = imexImporter.importFailed();

        Assert.assertEquals(2, report.getSucessfullPmids().size());
        Assert.assertEquals(0, report.getNewPmisImported().size());
        Assert.assertEquals(0, report.getFailedPmids().size());
        Assert.assertEquals(0, report.getPmidsNotFoundInRepo().size());

        beginTransaction();

        for (ImexImport imexObject : (List<ImexImport>)getDaoFactory().getImexImportDao().getAll()) {

            Assert.assertEquals(ImexImportStatus.OK,imexObject.getStatus());
        }

        commitTransaction();
    }

    @Test
    public void importFailed_notFound() throws Exception {
        Institution mint = new Institution("mint");
        persistImexImport(mint, "0", ImexImportStatus.ERROR);

        commitTransaction();

        ImexImporter imexImporter = new ImexImporter(repository);
        ImportReport report = imexImporter.importFailed();

        Assert.assertEquals(0, report.getSucessfullPmids().size());
        Assert.assertEquals(0, report.getNewPmisImported().size());
        Assert.assertEquals(0, report.getFailedPmids().size());
        Assert.assertEquals(1, report.getPmidsNotFoundInRepo().size());

        beginTransaction();
        Assert.assertEquals(ImexImportStatus.ERROR, getDaoFactory().getImexImportDao().getByPmid("0").getStatus());
        commitTransaction();
    }

    /**
     * This repository contains three pmids:
     * 15733859 - mint - OK - IMPORTABLE
     * 15733864 - mint - OK - IMPORTABLE
     * 15757671 - mint - OK - IMPORTABLE
     */
    private static Repository createRepositoryWithMint() throws Exception{
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "imex-test-repo-"+System.currentTimeMillis());
        FileUtils.deleteDirectory(tempDir);

        Repository repo = ImexRepositoryContext.openRepository(tempDir.getAbsolutePath());

        File psiMi = new File(ImexImporterTest.class.getResource("/xml/mint_2006-07-18.xml").getFile());
        repo.storeEntrySet(psiMi, "mint");

        return repo;
    }

    private void persistImexImport(Institution institution, String pmid, ImexImportStatus status) {
        getDaoFactory().getInstitutionDao().saveOrUpdate(institution);
        getDaoFactory().getImexImportDao().persist(
                new ImexImport(institution, pmid, status));
    }
}