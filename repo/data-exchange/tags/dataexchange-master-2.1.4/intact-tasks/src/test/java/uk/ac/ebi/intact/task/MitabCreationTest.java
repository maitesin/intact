/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.task;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.core.persister.CorePersister;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.dataexchange.psimi.solr.CoreNames;
import uk.ac.ebi.intact.dataexchange.psimi.solr.server.SolrJettyRunner;
import uk.ac.ebi.intact.model.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@ContextConfiguration(locations = {"/META-INF/mitab-creation.spring.xml"})
@Transactional(propagation = Propagation.NEVER)
public class MitabCreationTest extends IntactBasicTestCase {

    @Resource(name = "intactBatchJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    private CorePersister corePersister;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SolrJettyRunner solrJettyRunner;

    /*@Before
    public void before() throws Exception {
        solrJettyRunner.start();
    }

    @After
    public void after() throws Exception {

        // uncommenting this will cause the test to hang - which might be used to perform extra solr query.
        // solrJettyRunner.join();

        solrJettyRunner.stop();
    }*/

    @Test
    @DirtiesContext
    @Ignore
    public void writeMitab() throws Exception {
        FileUtils.deleteDirectory(new File("target/lala-lucene"));

        CvTopic hidden = getMockBuilder().createCvObject( CvTopic.class, null, "hidden" );

        CvTopic internalRemark = getMockBuilder().createCvObject( CvTopic.class, null, "internal-remark" );
        internalRemark.addAnnotation( new Annotation(getIntactContext().getInstitution(), hidden, "" ) );

        CvTopic noUniprotUpdate = getMockBuilder().createCvObject( CvTopic.class, null, "no-uniprot-update" );
        noUniprotUpdate.addAnnotation( new Annotation(getIntactContext().getInstitution(), hidden, "" ) );

        Experiment exp = getMockBuilder().createExperimentRandom(3);
        exp.addAnnotation( new Annotation(exp.getOwner(), internalRemark, "some internal information" ) );

        corePersister.saveOrUpdate(exp);

        Protein proteinA = getMockBuilder().createProtein("P12345", "protA");
        Protein proteinB = getMockBuilder().createProtein("Q00001", "protB");
        Protein proteinC = getMockBuilder().createProtein("Q00002", "protC");
        proteinC.getAnnotations().clear();
        proteinC.addAnnotation( new Annotation(exp.getOwner(), noUniprotUpdate, "Could not map sequence" ) );

        Interaction interaction = getMockBuilder().createInteraction(
                getMockBuilder().createComponentBait(proteinA),
                getMockBuilder().createComponentPrey(proteinB),
                getMockBuilder().createComponentPrey(proteinC));

        CvDatabase goDb = getMockBuilder().createCvObject(CvDatabase.class, CvDatabase.GO_MI_REF, CvDatabase.GO);
        proteinA.addXref(getMockBuilder().createXref(proteinA, "GO:0030246", null, goDb));

        proteinA.getBioSource().setTaxId("9606");

        corePersister.saveOrUpdate(interaction);

        Assert.assertEquals(4, getDaoFactory().getInteractionDao().countAll());

        Job job = (Job) applicationContext.getBean("createMitabJob");

        Map<String, JobParameter> params = new HashMap<String, JobParameter>(1);
        params.put("date", new JobParameter(System.currentTimeMillis()));

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        Assert.assertTrue( jobExecution.getAllFailureExceptions().isEmpty() );
        Assert.assertEquals( "COMPLETED", jobExecution.getExitStatus().getExitCode() );

        final SolrServer solrServer = solrJettyRunner.getSolrServer(CoreNames.CORE_PUB);

        Assert.assertEquals(5L, solrServer.query(new SolrQuery("*:*")).getResults().getNumFound());
        Assert.assertEquals(2L, solrServer.query(new SolrQuery("P12345")).getResults().getNumFound());
        Assert.assertEquals(1L, solrServer.query(new SolrQuery("Q00002")).getResults().getNumFound());
        Assert.assertEquals(2L, solrServer.query(new SolrQuery("go:\"GO:0003674\"")).getResults().getNumFound());
        Assert.assertEquals(2L, solrServer.query(new SolrQuery("species:Catarrhini")).getResults().getNumFound());
        Assert.assertEquals(0L, solrServer.query(new SolrQuery("\"Could not map sequence\"")).getResults().getNumFound());

        // checking that the hidden annotation is still there
        final TransactionStatus transactionStatus = getDataContext().beginTransaction();

        proteinC = getDaoFactory().getProteinDao().getByShortLabel("protC");
        Assert.assertEquals(1, proteinC.getAnnotations().size());

        getDataContext().commitTransaction(transactionStatus);
    }

    @Test
    @DirtiesContext
    @Ignore
    public void writeMitabSelf() throws Exception {
        FileUtils.deleteDirectory(new File("target/lala-lucene"));

        Experiment exp = getMockBuilder().createExperimentRandom(3);

        corePersister.saveOrUpdate(exp);

        Protein proteinA = getMockBuilder().createProtein("P12345", "protA");

        Interaction interaction = getMockBuilder().createInteraction(
                getMockBuilder().createComponentNeutral(proteinA));
        Assert.assertEquals(1, interaction.getComponents().size());

        // set stoichiometry
        interaction.getComponents().iterator().next().setStoichiometry(2);

        CvDatabase goDb = getMockBuilder().createCvObject(CvDatabase.class, CvDatabase.GO_MI_REF, CvDatabase.GO);
        proteinA.addXref(getMockBuilder().createXref(proteinA, "GO:0030246", null, goDb));

        proteinA.getBioSource().setTaxId("9606");

        corePersister.saveOrUpdate(interaction);

        Assert.assertEquals(4, getDaoFactory().getInteractionDao().countAll());

        Job job = (Job) applicationContext.getBean("createMitabJob");

        Map<String, JobParameter> params = new HashMap<String, JobParameter>(1);
        params.put("date", new JobParameter(System.currentTimeMillis()));

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        Assert.assertTrue( jobExecution.getAllFailureExceptions().isEmpty() );
        Assert.assertEquals( "COMPLETED", jobExecution.getExitStatus().getExitCode() );

        final SolrServer solrServer = solrJettyRunner.getSolrServer(CoreNames.CORE_PUB);

        Assert.assertEquals(1L, solrServer.query(new SolrQuery("P12345")).getResults().getNumFound());
    }

    @Test
    @DirtiesContext
    @Ignore
    public void writeMitabSelf_stoichioGreaterThan2() throws Exception {
        FileUtils.deleteDirectory(new File("target/lala-lucene"));

        Experiment exp = getMockBuilder().createExperimentRandom(3);

        corePersister.saveOrUpdate(exp);

        Protein proteinA = getMockBuilder().createProtein("P12345", "protA");

        Interaction interaction = getMockBuilder().createInteraction(
                getMockBuilder().createComponentNeutral(proteinA));
        Assert.assertEquals(1, interaction.getComponents().size());

        // set stoichiometry
        interaction.getComponents().iterator().next().setStoichiometry(4);

        CvDatabase goDb = getMockBuilder().createCvObject(CvDatabase.class, CvDatabase.GO_MI_REF, CvDatabase.GO);
        proteinA.addXref(getMockBuilder().createXref(proteinA, "GO:0030246", null, goDb));

        proteinA.getBioSource().setTaxId("9606");

        corePersister.saveOrUpdate(interaction);

        Assert.assertEquals(4, getDaoFactory().getInteractionDao().countAll());

        Job job = (Job) applicationContext.getBean("createMitabJob");

        Map<String, JobParameter> params = new HashMap<String, JobParameter>(1);
        params.put("date", new JobParameter(System.currentTimeMillis()));

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        Assert.assertTrue( jobExecution.getAllFailureExceptions().isEmpty() );
        Assert.assertEquals( "COMPLETED", jobExecution.getExitStatus().getExitCode() );

        final SolrServer solrServer = solrJettyRunner.getSolrServer(CoreNames.CORE_PUB);

        Assert.assertEquals(1L, solrServer.query(new SolrQuery("P12345")).getResults().getNumFound());
    }

    @Test
    @DirtiesContext
    @Ignore
    public void writeMitab_withXrefs() throws Exception {
        FileUtils.deleteDirectory(new File("target/lala-lucene"));

        Protein proteinA = getMockBuilder().createProtein("P12345", "protA");
        Protein proteinB = getMockBuilder().createProtein("Q00001", "protB");
        Protein proteinC = getMockBuilder().createProtein("Q00002", "protC");

        Interaction interaction = getMockBuilder().createInteraction(
                getMockBuilder().createComponentBait(proteinA),
                getMockBuilder().createComponentPrey(proteinB),
                getMockBuilder().createComponentPrey(proteinC));

        CvDatabase imexDb = getMockBuilder().createCvObject(CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        CvXrefQualifier imexPrimary = getMockBuilder().createCvObject(CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        interaction.addXref(getMockBuilder().createXref(interaction, "IM-1234-1", imexPrimary, imexDb));

        corePersister.saveOrUpdate(interaction);

        Assert.assertEquals(1, getDaoFactory().getInteractionDao().countAll());

        Job job = (Job) applicationContext.getBean("createMitabJob");

        Map<String, JobParameter> params = new HashMap<String, JobParameter>(1);
        params.put("date", new JobParameter(System.currentTimeMillis()));

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        Assert.assertTrue( jobExecution.getAllFailureExceptions().isEmpty() );
        Assert.assertEquals( "COMPLETED", jobExecution.getExitStatus().getExitCode() );

        final SolrServer solrServer = solrJettyRunner.getSolrServer(CoreNames.CORE_PUB);
        Assert.assertEquals(2L, solrServer.query(new SolrQuery("IM-1234-1")).getResults().getNumFound());
    }

    @Test
    @DirtiesContext
    @Ignore
    public void writeMitab_negative() throws Exception {
        FileUtils.deleteDirectory(new File("target/lala-lucene"));

        Protein proteinA = getMockBuilder().createProtein("P12345", "protA");
        Protein proteinB = getMockBuilder().createProtein("Q00001", "protB");
        Protein proteinC = getMockBuilder().createProtein("Q00002", "protC");

        Interaction interaction = getMockBuilder().createInteraction(
                getMockBuilder().createComponentBait(proteinA),
                getMockBuilder().createComponentPrey(proteinB),
                getMockBuilder().createComponentPrey(proteinC));

        interaction.addAnnotation(getMockBuilder().createAnnotation("because of this and that", null, CvTopic.NEGATIVE));

        corePersister.saveOrUpdate(interaction);

        Assert.assertEquals(1, getDaoFactory().getInteractionDao().countAll());

        Job job = (Job) applicationContext.getBean("createMitabJob");

        Map<String, JobParameter> params = new HashMap<String, JobParameter>(1);
        params.put("date", new JobParameter(System.currentTimeMillis()));

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(params));
        Assert.assertTrue( jobExecution.getAllFailureExceptions().isEmpty() );
        Assert.assertEquals( "COMPLETED", jobExecution.getExitStatus().getExitCode() );

        final SolrServer solrServer = solrJettyRunner.getSolrServer(CoreNames.CORE_PUB);
        Assert.assertEquals(0L, solrServer.query(new SolrQuery("*:*")).getResults().getNumFound());
    }
}
