/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.statisticView.business.data;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import uk.ac.ebi.intact.application.statisticView.business.model.BioSourceStatistics;
import uk.ac.ebi.intact.application.statisticView.business.model.ExperimentStatistics;
import uk.ac.ebi.intact.application.statisticView.business.model.IdentificationMethodStatistics;
import uk.ac.ebi.intact.application.statisticView.business.model.IntactStatistics;
import uk.ac.ebi.intact.application.statisticView.business.util.Constants;
import uk.ac.ebi.intact.application.statisticView.business.util.IntactStatisticComparator;
import uk.ac.ebi.intact.application.statisticView.graphic.ChartBuilder;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Michael Kleen mkleen@ebi.ac.uk
 * Date: Mar 14, 2005
 * Time: 11:24:07 PM
 */
public class StatisticHelper {

    private static final Logger logger = Logger.getLogger(Constants.LOGGER_NAME);
    private List intactStatistics = null;
    private List filteredStatistics = null;
    public final SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");

    public JFreeChart getExperimentChart(String start, String stop) throws IntactException {
        // set up on the first call
        if (filteredStatistics == null) {
            filteredStatistics = this.getIntactStatistics(start, stop);
        }
        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart result = chartBuilder.numberOfExperiments(filteredStatistics);
        return result;
    }

    public JFreeChart getInteractionChart(String start, String stop) throws IntactException {
        // set up on the first call
        if (filteredStatistics == null) {
            filteredStatistics = this.getIntactStatistics(start, stop);
        }
        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart result = chartBuilder.numberOfInteractions(filteredStatistics);
        return result;
    }

    public JFreeChart getBinaryInteractionChart(String start, String stop) throws IntactException {
        // set up on the first call
        if (filteredStatistics == null) {
            filteredStatistics = this.getIntactStatistics(start, stop);
        }
        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart result = chartBuilder.numberOfBinaryInteractions(filteredStatistics);
        return result;
    }

    public JFreeChart getProteinChart(String start, String stop) throws IntactException {
        // set up on the first call
        if (filteredStatistics == null) {
            filteredStatistics = this.getIntactStatistics(start, stop);
        }
        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart result = chartBuilder.numberOfProteins(filteredStatistics);
        return result;
    }

    public JFreeChart getCvChart(String start, String stop) throws IntactException {
        // set up on the first call
        if (filteredStatistics == null) {
            filteredStatistics = this.getIntactStatistics(start, stop);
        }
        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart result = chartBuilder.numberOfCvTerms(filteredStatistics);
        return result;
    }

    public JFreeChart getIdentificationChart() throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        ChartBuilder chartBuilder = new ChartBuilder();
        Collection intactStatistics = intactHelper.search(IdentificationMethodStatistics.class, "ac", null);
        JFreeChart result = chartBuilder.identificationMethods(intactStatistics);
        intactHelper.closeStore();
        return result;
    }

    public JFreeChart getBioSourceChart() throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        ChartBuilder chartBuilder = new ChartBuilder();
        Collection intactStatistics = intactHelper.search(BioSourceStatistics.class, "ac", null);
        JFreeChart result = chartBuilder.binaryInteractionsPerOrganism(intactStatistics);
        intactHelper.closeStore();
        return result;
    }

    public JFreeChart getEvidenceChart() throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        ChartBuilder chartBuilder = new ChartBuilder();
        Collection intactStatistics = intactHelper.search(ExperimentStatistics.class, "ac", null);
        JFreeChart result = chartBuilder.evidencePerExperiment(intactStatistics);
        intactHelper.closeStore();
        return result;
    }

    public JFreeChart getIdentificationChart(String date) throws IntactException {
        final SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
        IntactHelper intactHelper = new IntactHelper();
        Collection result = intactHelper.search(IdentificationMethodStatistics.class, "ac", null);
        intactHelper.closeStore();
        long dateNumber = 0;

        if (date == null || date == "") {
            dateNumber = (this.getLastBioSourceTimestamp()).getTime();
        }
        else {
            try {
                dateNumber = dateFormater.parse(date).getTime();
            }
            catch (ParseException e) {
                dateNumber = (this.getLastBioSourceTimestamp()).getTime();
                //   throw new IntactException("unable to parse date", e);
            }
        }

        final List filteredIntactStatistics = new ArrayList();

        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            final IdentificationMethodStatistics intactStatistics = (IdentificationMethodStatistics) iterator.next();
            final long timestamp = intactStatistics.getTimestamp().getTime();

            if (timestamp != dateNumber) {
                filteredIntactStatistics.add(intactStatistics);
            }

        } // for

        List toSort = new ArrayList(filteredIntactStatistics);
        Collections.sort(toSort);

        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart chart = chartBuilder.identificationMethods(toSort);
        return chart;

    }

    public JFreeChart getBioSourceChart(String date) throws IntactException {
        // we can use criteria here
        final SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");
        IntactHelper intactHelper = new IntactHelper();
        Collection result = intactHelper.search(BioSourceStatistics.class, "ac", null);
        intactHelper.closeStore();
        long dateNumber = 0;

        if (date == null || date == "") {
            dateNumber = (this.getLastBioSourceTimestamp()).getTime();
        }
        else {
            try {
                dateNumber = dateFormater.parse(date).getTime();
            }
            catch (ParseException e) {
                throw new IntactException("unable to parse date", e);
            }
        }

        final List filteredIntactStatistics = new ArrayList();

        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            final BioSourceStatistics intactStatistics = (BioSourceStatistics) iterator.next();
            final long timestamp = intactStatistics.getTimestamp().getTime();

            if (timestamp != dateNumber) {
                filteredIntactStatistics.add(intactStatistics);
            }

        } // for

        List toSort = new ArrayList(filteredIntactStatistics);
        Collections.sort(toSort);

        ChartBuilder chartBuilder = new ChartBuilder();
        JFreeChart chart = chartBuilder.binaryInteractionsPerOrganism(toSort);
        return chart;


    }

    public JFreeChart getEvidenceChart(String start) throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        ChartBuilder chartBuilder = new ChartBuilder();
        Collection intactStatistics = intactHelper.search(ExperimentStatistics.class, "ac", null);
        JFreeChart result = chartBuilder.evidencePerExperiment(intactStatistics);
        intactHelper.closeStore();
        return result;
    }

    private List getIntactStatistics() throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        Collection result = intactHelper.search(IntactStatistics.class.getName(), "ac", null);
        intactHelper.closeStore();
        ArrayList toSort = new ArrayList(result);
        Collections.sort(toSort, new IntactStatisticComparator());
        return toSort;
    }

    public List getIntactStatistics(final String startDate, final String stopDate) throws IntactException {
        final SimpleDateFormat dateFormater = new SimpleDateFormat("dd-MMM-yyyy");

        long start = 0;
        long stop = 0;

        final IntactHelper intactHelper = new IntactHelper();
        final Collection result = intactHelper.search(IntactStatistics.class.getName(), "ac", null);
        intactHelper.closeStore();

        if (startDate == null || startDate == "") {
            start = (this.getFirstTimestamp()).getTime();
        }

        else {
            try {
                start = dateFormater.parse(startDate).getTime();
            }
            catch (ParseException e) {
                start = (this.getFirstTimestamp()).getTime();
//                throw new IntactException("unable to parse date", e);
            }
        }

        if (stopDate == null || stopDate == "") {
            stop = (this.getLastTimestamp()).getTime();
        }

        else {
            try {
                stop = dateFormater.parse(stopDate).getTime();
            }
            catch (ParseException e) {
                stop = (this.getLastTimestamp()).getTime();
            }
        }


        final List filteredIntactStatistics = new ArrayList();

        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            final IntactStatistics intactStatistics = (IntactStatistics) iterator.next();
            final long timestamp = intactStatistics.getTimestamp().getTime();
            boolean keepIt = true;

            if (timestamp < start) {
                keepIt = false;
            }

            if (timestamp > stop) {
                keepIt = false;
            }

            if (keepIt) {
                filteredIntactStatistics.add(intactStatistics);
            }
        } // for

        List toSort = new ArrayList(filteredIntactStatistics);
        Collections.sort(toSort, new IntactStatisticComparator());
        return toSort;


    }

    public int getProteinCount() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }
        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getNumberOfProteins();
    }

    public int getExperimentCount() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }
        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getNumberOfExperiments();
    }

    public int getInteractionCount() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }
        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getNumberOfInteractions();
    }

    public int getBinaryInteractionCount() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }
        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getNumberOfBinaryInteractions();
    }

    public int getCvCount() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }
        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getNumberOfCvTerms();
    }

    public Timestamp getFirstTimestamp() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }

        IntactStatistics first = (IntactStatistics) intactStatistics.get(0);
        return first.getTimestamp();
    }

    public Timestamp getLastTimestamp() throws IntactException {
        if (intactStatistics == null) {
            intactStatistics = this.getIntactStatistics();
        }

        IntactStatistics last = (IntactStatistics) intactStatistics.get(intactStatistics.size() - 1);
        return last.getTimestamp();

    }

    private Timestamp getLastBioSourceTimestamp() throws IntactException {
        IntactHelper intactHelper = new IntactHelper();
        Collection result = intactHelper.search(BioSourceStatistics.class, "ac", null);
        intactHelper.closeStore();
        List list = new ArrayList(result);
        BioSourceStatistics last = (BioSourceStatistics) list.get(list.size() - 1);
        return last.getTimestamp();

    }
}
