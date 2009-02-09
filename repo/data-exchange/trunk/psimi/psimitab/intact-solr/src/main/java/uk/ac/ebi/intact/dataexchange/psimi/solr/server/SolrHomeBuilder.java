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
package uk.ac.ebi.intact.dataexchange.psimi.solr.server;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class SolrHomeBuilder {

    private static Logger log = LoggerFactory.getLogger(SolrHomeBuilder.class);

    private URL solrHomeJar;

    private File solrHomeDir;
    private File solrWar;

    public SolrHomeBuilder() {
       Properties props = new Properties();

        try {
            if (log.isDebugEnabled()) log.debug("Loading properties from classpath");
            props.load(SolrHomeBuilder.class.getResourceAsStream("/IntactSolrConfig.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("Problem loading properties", e);
        }

        String repo = props.getProperty("intact.solr.home.repositoryBase");
        String groupId = props.getProperty("intact.solr.home.groupId");
        String artifactId = "intact-solr-home";
        String version = props.getProperty("intact.solr.home.version");

        StringBuilder url = new StringBuilder();
        url.append(repo);

        if (version.endsWith("-SNAPSHOT")) {
            url.append("_snapshots");
        }
        
        url.append("/");
        url.append(groupId.replaceAll("\\.", "/")).append("/");
        url.append(artifactId).append("/");
        url.append(version).append("/");
        url.append("intact-solr-home-").append(version).append(".jar");

        try {
            solrHomeJar = new URL(url.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Problem creating url: "+url, e);
        }

    }

    public SolrHomeBuilder(URL solrHomeJar) {
        if (solrHomeJar == null) throw new NullPointerException("A Url is needed");

        if (!solrHomeJar.toString().startsWith("jar:")) {
            try {
                solrHomeJar = new URL("jar:" + solrHomeJar.toString() + "!/");
            } catch (MalformedURLException e) {
                throw new RuntimeException("Problem jar creating url: " + solrHomeJar, e);
            }
        }

        this.solrHomeJar = solrHomeJar;
    }

    public void install(File solrWorkingDir) throws IOException {
       install(solrWorkingDir, true);
    }

    public void install(File solrWorkingDir, boolean useLocalJarIfAvailable) throws IOException {
        if (log.isInfoEnabled()) log.info("Installing Intact SOLR Home at: "+solrWorkingDir+", use local jar if available: "+useLocalJarIfAvailable);

        URL jarUrl;

        if (useLocalJarIfAvailable) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String prefix = df.format(new Date());

            File localJar = new File(System.getProperty("java.io.tmpdir"), "intact-solr-home-"+prefix+".jar");
            
            if (!localJar.exists()) {
                if (log.isDebugEnabled()) log.debug("Local jar does not exist. Getting URL: "+solrHomeJar);
                try {
                    writeStreamToFile(localJar, solrHomeJar.openStream());
                } catch (IOException e) {
                    throw new IOException("Problem opening file: "+solrHomeJar, e);
                }
            } else {
                if (log.isDebugEnabled()) log.debug("Using existing local jar: "+localJar);
            }

            jarUrl = new URL("jar:file://"+localJar+"!/");
        } else {
            if (!solrHomeJar.toString().startsWith("jar:")) {
                try {
                    solrHomeJar = new URL("jar:" + solrHomeJar.toString() + "!/");
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Problem jar creating url: " + solrHomeJar, e);
                }
            }

            jarUrl = solrHomeJar;
        }

        // read the jar file
        if (log.isDebugEnabled()) log.debug("Openning connection to: "+jarUrl);

        JarURLConnection connection = (JarURLConnection) jarUrl.openConnection();

        JarFile jarFile = connection.getJarFile();

        Enumeration<JarEntry> jarEntries = jarFile.entries();

        // write
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();

            // exclude META-INF
            if (!entry.toString().startsWith("META-INF")) {
                File fileToCreate = new File(solrWorkingDir, entry.toString());

                if (entry.isDirectory()) {
                    fileToCreate.mkdirs();
                    continue;
                }

                BufferedInputStream is = new BufferedInputStream(jarFile.getInputStream(entry));
                writeStreamToFile(fileToCreate, is);
                is.close();
            }
        }

        solrHomeDir = new File(solrWorkingDir, "home/");
        solrWar = new File(solrWorkingDir, "solr.war");

        if (log.isDebugEnabled()) {
            log.debug("\nSolr Home: {}\nSolr WAR: {}", solrHomeDir.toString(), solrWar.toString());
        }

    }

    private void writeStreamToFile( File fileToCreate, InputStream inputStream) throws IOException {
        int buffer = 2048;
        int count;
        byte data[] = new byte[buffer];

        BufferedInputStream is = new BufferedInputStream(inputStream);

        FileOutputStream fos = new FileOutputStream(fileToCreate);
        BufferedOutputStream dest = new
                BufferedOutputStream(fos, buffer);
        while ((count = is.read(data, 0, buffer))
               != -1) {
            dest.write(data, 0, count);
        }

        dest.flush();
        dest.close();
    }

    public File installTemp() throws IOException {
        File solrTempDir = new File(System.getProperty("java.io.tmpdir"), "solr-home-"+System.currentTimeMillis());
        install(solrTempDir);

        try {
            FileUtils.forceDeleteOnExit(solrTempDir);
        } catch (IOException e) {
            throw new RuntimeException("Problem foring directory to delete on exit: "+solrTempDir, e);
        }

        return solrTempDir;
    }

    public File getSolrHomeDir() {
        return solrHomeDir;
    }

    public File getSolrWar() {
        return solrWar;
    }
}
