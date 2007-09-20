/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others. All
 * rights reserved. Please see the file LICENSE in the root directory of this
 * distribution.
 */
package uk.ac.ebi.intact.bridges.blast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.ac.ebi.intact.bridges.blast.client.BlastClientException;
import uk.ac.ebi.intact.bridges.blast.client.BlastClient;
import uk.ac.ebi.intact.bridges.blast.model.BlastInput;
import uk.ac.ebi.intact.bridges.blast.model.BlastJobStatus;
import uk.ac.ebi.intact.bridges.blast.model.BlastOutput;
import uk.ac.ebi.intact.bridges.blast.model.Job;
import uk.ac.ebi.intact.bridges.blast.model.UniprotAc;
import uk.ac.ebi.intact.confidence.blastmapping.BlastMappingException;
import uk.ac.ebi.intact.confidence.blastmapping.BlastMappingReader;
import uk.ac.ebi.intact.confidence.blastmapping.jaxb.EBIApplicationResult;
import uk.ac.ebi.intact.confidence.blastmapping.jaxb.TAlignment;
import uk.ac.ebi.intact.confidence.blastmapping.jaxb.THit;

/**
 * TODO comment this ... someday
 * 
 * @author Irina Armean (iarmean@ebi.ac.uk)
 * @version
 * @since
 * 
 * <pre>
 * 12 Sep 2007
 * </pre>
 */
public class EbiWsWUBlast extends AbstractBlastService {
	/**
	 * Sets up a logger for that class.
	 */
	public static final Log	log	= LogFactory.getLog(EbiWsWUBlast.class);
	private BlastClient		bc;

	// ///////////////
	// Constructor
	public EbiWsWUBlast(File workDir, String email) throws BlastServiceException {
		this.setWorkDir(workDir);
		try {
			bc = new BlastClient(email);
		} catch (BlastClientException e) {
			throw new BlastServiceException(e);
		}
	}

	// /////////////////
	// public Methods
	public Job runBlast(UniprotAc uniprotAc) throws BlastClientException {
		return bc.blast(new BlastInput(uniprotAc));
	}

	public List<Job> runBlast(Set<UniprotAc> uniprotAcs) throws BlastClientException {
		Set<BlastInput> toBlast = convertToBlastInput(uniprotAcs);
		return bc.blast(toBlast);
	}

	public BlastJobStatus checkStatus(Job job) throws BlastClientException {
		return bc.checkStatus(job);
	}

	public BlastOutput getResult(Job job) throws BlastClientException {
		return bc.getResult(job);
	}

	public BlastResult processOutput(File blastFile) {
		return parseXmlOutput(blastFile);
	}

	// /////////////////
	// private Methods
	private Set<BlastInput> convertToBlastInput(Set<UniprotAc> uniprotAcs) {
		Set<BlastInput> toBlast = new HashSet<BlastInput>(uniprotAcs.size());
		for (UniprotAc uniprotAc : uniprotAcs) {
			toBlast.add(new BlastInput(uniprotAc));
		}
		return toBlast;
	}

	private BlastResult parseXmlOutput(File xmlFile) {
		// BlastResult result = new BlastResult();
		String uniprotAc = "";
		List<Hit> blastHits = new ArrayList<Hit>();
		BlastMappingReader bmr = new BlastMappingReader();

		try {
			EBIApplicationResult appResult = bmr.read(xmlFile);

			List<THit> xmlHits = appResult.getSequenceSimilaritySearchResult().getHits().getHit();
			for (THit hit : xmlHits) {
				if (uniprotAc.equals("")) {
					uniprotAc = hit.getAc();
				} else {
					String accession = hit.getAc();
					// a value that will never be < threshold
					Float evalue = new Float(1000);
					List<TAlignment> alignments = hit.getAlignments().getAlignment();
					// FIXME: change :takes the last alignment
					// TODO: throw exception if alignments.size() >1
					for (TAlignment align : alignments) {
						evalue = align.getExpectation();
					}
					blastHits.add(new Hit(accession, evalue));
				}
			}
		} catch (BlastMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (uniprotAc.equals("") && blastHits.size() == 0) {
			log.debug("NO BlastResult was found, see file " + xmlFile.getPath());
		}
		return new BlastResult(uniprotAc, blastHits);
	}
}
