/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.psicquic.ws.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.httpclient.HttpClient;
import uk.ac.ebi.intact.psicquic.ws.IntactPsicquicService;
import uk.ac.ebi.intact.psicquic.ws.jms.StatsConsumer;

/**
 * Psicquic Initializing Bean.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Controller
public class PsicquicInitializingBean implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(IntactPsicquicService.class);

    private static final String STATS_DIR_ENV = "psicquic.stats.dir";

    @Autowired
    private PsicquicConfig config;

    //@Autowired
    //private StatsConsumer statsConsumer;

    public void afterPropertiesSet() throws Exception {
        // proxy set
        if (config.getProxyHost() != null && config.getProxyHost().length() > 0) {
            if (logger.isInfoEnabled()) logger.info("Using proxy host: "+config.getProxyHost());
            System.setProperty("http.proxyHost", config.getProxyHost());
        }
        if (config.getProxyPort() != null && config.getProxyPort().length() > 0) {
            if (logger.isInfoEnabled()) logger.info("Using proxy port: "+config.getProxyPort());
            System.setProperty("http.proxyPort", config.getProxyPort());
        }

        // stats directory
        String statsDir = config.getStatsDirectory();

        if (statsDir == null) {
            statsDir = System.getProperty(STATS_DIR_ENV);

            if (statsDir != null) {
                logger.info("Usage statistics directory (found as system property): "+statsDir);
            } else {
                statsDir = System.getProperty("java.io.tmpdir");
                logger.warn("Usage statistics directory not configured (system property '"+STATS_DIR_ENV+
                            "' not found). Using default: "+statsDir);
            }
        } else {
            logger.info("Usage statistics directory: "+statsDir);
        }

        config.setStatsDirectory(statsDir);

        // stats consumer
        logger.info("Initializing consumer");
        //statsConsumer.start();
    }
}
