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
package uk.ac.ebi.intact.dataexchange.imex.repository;

import org.apache.commons.io.FileUtils;
import uk.ac.ebi.intact.dataexchange.imex.repository.dao.RepoEntryService;
import uk.ac.ebi.intact.dataexchange.imex.repository.ftp.ImexFTPClient;
import uk.ac.ebi.intact.dataexchange.imex.repository.ftp.ImexFTPClientFactory;
import uk.ac.ebi.intact.dataexchange.imex.repository.ftp.ImexFTPFile;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.RepoEntry;
import uk.ac.ebi.intact.dataexchange.imex.repository.model.Message;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Playground {

    public static void main(String[] args) throws Exception {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "myRepo-mintdip2/");
        FileUtils.deleteDirectory(tempDir);

        Repository repo = ImexRepositoryContext.openRepository(tempDir.getAbsolutePath());

        final ImexFTPClient mintClient = ImexFTPClientFactory.createMintClient();
        mintClient.connect();
        for (ImexFTPFile ftpFile : mintClient.listFiles()) {
            try {
                repo.storeEntrySet(ftpFile, "mint");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        mintClient.disconnect();

        final ImexFTPClient dipClient = ImexFTPClientFactory.createDipClient();
        dipClient.connect();
        for (ImexFTPFile ftpFile : dipClient.listFiles()) {
            try {
                repo.storeEntrySet(ftpFile, "dip");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dipClient.disconnect();

//        final ImexFTPClient intactClient = ImexFTPClientFactory.createIntactClient();
//        intactClient.connect();
//        for (ImexFTPFile ftpFile : intactClient.listFiles()) {
//            repo.storeEntrySet(ftpFile, "intact");
//        }
//        intactClient.disconnect();


        RepoEntryService repoEntryService = ImexRepositoryContext.getInstance().getImexServiceProvider().getRepoEntryService();
        for (RepoEntry repoEntry : repoEntryService.findAllRepoEntries()) {
            System.out.println(repoEntry.getPmid()+" - "+repoEntry.getRepoEntrySet().getProvider().getName()+" - "+(repoEntry.isValid()? "OK" : "ERROR")+
            (repoEntry.isImportable()? " - IMPORTABLE" : ""));

            if (!repoEntry.isValid()) {
                for (Message error : repoEntry.getMessages()) {
                    System.out.println("\tError: "+error.getText());
                    System.out.println(error.getStackTrace());
                }
            }
        }

        System.out.println("\nStats:");
        System.out.println("\tImportable: "+repoEntryService.findImportableExcluding(Collections.EMPTY_LIST).size());

    }


}