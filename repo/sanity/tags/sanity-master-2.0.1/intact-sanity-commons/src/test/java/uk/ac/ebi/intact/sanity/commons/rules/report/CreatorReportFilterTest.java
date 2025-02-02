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

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.intact.core.unit.IntactBasicTestCase;
import uk.ac.ebi.intact.model.Protein;
import uk.ac.ebi.intact.sanity.commons.rules.GeneralMessage;
import uk.ac.ebi.intact.sanity.commons.rules.MessageLevel;
import uk.ac.ebi.intact.sanity.commons.rules.MessageDefinition;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class CreatorReportFilterTest extends IntactBasicTestCase {

    @Test
    public void accept_true() throws Exception {
        Protein prot = getMockBuilder().createProteinRandom();
        prot.setCreator("peter");
        prot.setUpdator("anne");

        GeneralMessage message = new GeneralMessage(MessageDefinition.EXPERIMENT_NOT_SUPER_CURATED, prot);

        CreatorReportFilter creatorReportFilter = new CreatorReportFilter("Peter");

        Assert.assertTrue(creatorReportFilter.accept(message.getInsaneObject()));
    }
    
    @Test
    public void accept_false() throws Exception {
        Protein prot = getMockBuilder().createProteinRandom();
        prot.setCreator("peter");
        prot.setUpdator("anne");

        GeneralMessage message = new GeneralMessage(MessageDefinition.BROKEN_URL, prot);

        CreatorReportFilter creatorReportFilter = new CreatorReportFilter("anne");

        Assert.assertFalse(creatorReportFilter.accept(message.getInsaneObject()));
    }
}