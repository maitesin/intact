/**
 * Copyright 2007 The European Bioinformatics Institute, and others.
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
package uk.ac.ebi.intact.view.webapp.controller.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import uk.ac.ebi.intact.view.webapp.controller.config.IntactViewConfiguration;

import javax.faces.bean.ApplicationScoped;
import java.io.File;
import java.io.Serializable;

/**
 * Application scope bean, with configuration stuff
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
@Controller
@ApplicationScoped
public class AppConfigBean implements Serializable, InitializingBean {

    private Log log = LogFactory.getLog(AppConfigBean.class);

    @Autowired
    private OntologyBean ontologyBean;

    @Autowired
    private IntactViewConfiguration viewConfig;

    private String configFileLocation;

    private XrefLinkContext linkContext;

    public AppConfigBean() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        configFileLocation = viewConfig.getConfigFile();

        if ( ! new File(configFileLocation).exists() ) {
            if (log.isInfoEnabled()) log.info("No configuration File found. First time setup");
        }

        if (log.isInfoEnabled()) log.info("Initializing xref link context...");
        this.linkContext = XrefLinkContextFactory.createDefaultXrefLinkContext();
    }

    public XrefLinkContext getLinkContext() {
        return linkContext;
    }

    public void setLinkContext(XrefLinkContext linkContext) {
        this.linkContext = linkContext;
    }

    public String getConfigFileLocation() {
        return configFileLocation;
    }

    public void setConfigFileLocation(String configFileLocation) {
        this.configFileLocation = configFileLocation;
    }

    public boolean isConfigFileExists() {
        return new File(configFileLocation).exists();
    }


}
