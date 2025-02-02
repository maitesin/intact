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
package uk.ac.ebi.intact.sanity.commons.rules.report;

import uk.ac.ebi.intact.sanity.commons.report.SanityReport;
import uk.ac.ebi.intact.sanity.commons.SanityRuleException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class XmlReportReader extends ReportReader {

    public SanityReport readReport(InputStream is) throws IOException {
        try {
            JAXBContext jc = JAXBContext.newInstance(SanityReport.class.getPackage().getName());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (SanityReport) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            throw new SanityRuleException("Problem reading report", e);
        }
    }
}