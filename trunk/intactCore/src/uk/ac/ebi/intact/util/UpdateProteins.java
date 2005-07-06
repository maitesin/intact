/*
Copyright (c) 2002 The European Bioinformatics Institute, and others.
 All rights reserved. Please see the file LICENSE
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.util;

import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.aristotle.model.sptr.AristotleSPTRException;
import uk.ac.ebi.aristotle.util.interfaces.AlternativeSplicingAdapter;
import uk.ac.ebi.intact.business.BusinessConstants;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.business.IntactHelper;
import uk.ac.ebi.intact.model.*;
import uk.ac.ebi.interfaces.Factory;
import uk.ac.ebi.interfaces.feature.FeatureException;
import uk.ac.ebi.interfaces.sptr.*;
import uk.ac.ebi.sptr.flatfile.yasp.EntryIterator;
import uk.ac.ebi.sptr.flatfile.yasp.YASP;
import uk.ac.ebi.sptr.flatfile.yasp.YASPException;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Parse an URL and update the IntAct database.
 * <p/>
 * Here is the detail implemented algorithm <br>
 * <pre>
 * <p/>
 * (1) From the URL given by the user, get an <i>EntryIterator</i> to process them one by one.
 * <p/>
 * (2) for each <i>SPTREntry</i>
 * <p/>
 *    (2.1)
 *          a)
 *          From the Accession number, retreive from IntAct all <i>Protein</i> with that AC as
 *          a SPTR <i>Xref</i>. We can find several instance of Protein in case they are link
 *          to different <i>BioSource</i>. Lets call that set of Protein: PROTEINS.
 *          Note: an SPTREntry can contains several AC so we check in IntAct for all of them.
 * <p/>
 *          b)
 *          From PROTEINS, we retreive from IntAct all <i>Splice Variant</i> (ie. <code>Protein</code>)
 *          with the AC of a retreived proteins (in PROTEINS) and having a CvXrefQualifier equals to
 *          <i>isoform-parent</i>. We can find several instance of Splice Variant per master Protein
 *          in case in case we have multiple <i>BioSource</i>. Lets call that set of Protein:
 *          SPLICE-VARIANTS.
 * <p/>
 *    (2.2) The user can give a taxid 'filter' (lets call it t) in order to retrieve only
 *          protein related to that taxid (beware that behind the scene, all protein are
 *          update/create). In an SPTREntry, there is 1..n specified organism (i.e. taxid).
 *          So if the taxid parameter t is null, we give back to the user all proteins created
 *          or updated, if a valid taxid is given by the user, we filter the set of proteins.
 *          If it is not found, the procedure fails.
 * <p/>
 *    (2.3) For each taxid of the SPTREntry (lets call it TAXID)
 * <p/>
 *       (2.3.1) Get up-to-date information about the organism from Newt.
 *               If that organism is already existing inIntAct as a BioSource, we check if an
 *               update is needed. We take also into account that a taxid can be obsolete and
 *               in such a case we update IntAct data accordingly.
 * <p/>
 *       (2.3.2)
 *                 a) If a Protein from PROTEINS (cf. 2.1) has TAXID as BioSource,
 *                    we update its data from the SPTREntry.
 * <p/>
 *                 b) If no Protein from PROTEINS has TAXID as BioSource, we create a new Protein.
 * <p/>
 *                 c) If a Protein from PROTEINS has a taxid not found in the SPTREntry, we display
 *                    a warning message.
 * <p/>
 *                 d) If a Protein from SPLICE-VARIANTS (cf. 2.1) has TAXID as BioSource,
 *                    we update its data from the SPTREntry.
 * <p/>
 *                 e) If no Protein from SPLICE-VARIANTS has TAXID as BioSource, we create a new Protein.
 * <p/>
 *                 f) If a Protein from SPLICE-VARIANTS has a taxid not found in the SPTREntry,
 *                    we display a warning message.
 * <p/>
 * <p/>
 * Cross references created on step 2.3.2:
 * <p/>
 * For Proteins :
 * <p/>
 *     (1) a link to uniprot
 * <p/>
 *          Xref( CvDatabase(uniprot)
 *                primaryId(uniprotAc-spliceVarNumber)
 *                secondaryId(uniprotId)
 *                CvXrefQualifier(identity)
 *              );
 * <p/>
 *     (2) Link to GO, SGD, INTERPRO, FLYBASE, REACTOME.
 *         Those Xrefs comply to the following schema:
 *         TODO: when updating Xrefs, remove those that no longer exists
 * <p/>
 *          Xref( CvDatabase(DB),
 *                primaryId(AC),
 *                secondaryId(ID),
 *                CvXrefQualifier(-)
 *              );
 * <p/>
 * <p/>
 * For Splice Variants:
 * <p/>
 *     (1) a link to the master protein
 * <p/>
 *           Xref( CvDatabase(intact)
 *                 primaryId(intactAc)
 *                 secondaryId(intactShortlabel)
 *                 CvXrefQualifier(isoform-parent)
 *               );
 * <p/>
 *     (2) a link to uniprot
 * <p/>
 *            Xref( CvDatabase(uniprot)
 *                  primaryId(uniprotAc-spliceVarNumber)
 *                  secondaryId(uniprotId)
 *                  CvXrefQualifier(identity)
 *                );
 * <p/>
 * BEWARE that no checks have been done about the ownership of updated objects.
 * <p/>
 * </pre>
 * </p>
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 */
public class UpdateProteins extends UpdateProteinsI {

    // TODO if the SRS server doesn't reply, handle it nicely.

    private static final String NEW_LINE = System.getProperty( "line.separator" );

    private static final String TIME;


    static {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd@HH_mm" );
        TIME = formatter.format( new Date() );
        formatter = null;
    }

    // Store that error in the temp directory (OS independant)
    private static final String ENTRY_OUTPUT_FILE = System.getProperty( "java.io.tmpdir" ) + File.separator + "Entries.error";

    // to record entry error
    private String entryErrorFilename = null;
    private FileOutputStream file = null;
    private BufferedOutputStream buffer = null;

    // to record local entry cache
    private String localEntryCacheFilename = System.getProperty( "java.io.tmpdir" ) + File.separator + "localEntryCache." + TIME + ".txl";
    private boolean displayLocalEntryCacheMessage = true;

    private boolean localEntryCacheEnabled = false;
    private BufferedWriter localEntryCache = null;

    // flag for output on STDOUT
    private boolean debugOnScreen = false;

    // iterator on all parsed Entries.
    private EntryIterator entryIterator = null;

    // count of all potential protein
    // (i.e. for a SPTREntry, we can create/update several IntAct protein. One by BioSource)
    private int proteinTotal;

    // Successfully created/updated protein in IntAct
    private int entryCount;
    private int entrySkipped;

    private int proteinCreated;
    private int proteinUpdated;
    private int proteinUpToDate;

    private int spliceVariantTotal;
    private int spliceVariantCreated;
    private int spliceVariantUpdated;
    private int spliceVariantUpToDate;

    /**
     * Set of updated/created proteins during the process.
     */
    private Collection proteins;


    public UpdateProteins( IntactHelper helper ) throws UpdateException {
        super( helper );
    }

    public UpdateProteins( IntactHelper helper, boolean setOutput ) throws UpdateException {
        super( helper, setOutput );
    }

    public int getProteinCreatedCount() {
        return proteinCreated;
    }

    public int getProteinUpdatedCount() {
        return proteinUpdated;
    }

    public int getProteinUpToDateCount() {
        return proteinUpToDate;
    }

    public int getProteinCount() {
        return proteinTotal;
    }

    public int getProteinSkippedCount() {
        return ( proteinTotal - ( proteinCreated + proteinUpdated + proteinUpToDate ) );
    }

    public int getSpliceVariantCreatedCount() {
        return spliceVariantCreated;
    }

    public int getSpliceVariantUpdatedCount() {
        return spliceVariantUpdated;
    }

    public int getSpliceVariantUpToDateCount() {
        return spliceVariantUpToDate;
    }

    public int getSpliceVariantCount() {
        return spliceVariantTotal;
    }

    public int getSpliceVariantProteinCount() {
        return spliceVariantTotal;
    }

    public int getSpliceVariantSkippedCount() {
        return ( spliceVariantTotal - ( spliceVariantCreated + spliceVariantUpdated + spliceVariantUpToDate ) );
    }

    public int getEntryCount() {
        return entryCount;
    }

    public int getEntryProcessededCount() {
        return entryCount - entrySkipped;
    }

    public int getEntrySkippedCount() {
        return entrySkipped;
    }

    public void setDebugOnScreen( boolean debug ) {
        this.debugOnScreen = debug;
    }

    private void logInfo( String msg ) {
        if ( logger != null ) {
            logger.info( msg );
        }
    }

    private void reset() {

        if ( proteins == null ) {
            proteins = new ArrayList();
        } else {
            proteins.clear();
        }

        parsingExceptions.clear();

        proteinTotal = 0;
        proteinCreated = 0;
        proteinUpdated = 0;

        spliceVariantTotal = 0;
        spliceVariantCreated = 0;
        spliceVariantUpdated = 0;
        spliceVariantUpToDate = 0;

        proteinUpToDate = 0;
        entryCount = 0;
        entrySkipped = 0;
    }

    public final String getUrl( String uniprotAC ) {

        // the SRS url has been collected from the CvDatabase( uniprot ) during the initialisation process
        // and saved under srsUrl.
        return SearchReplace.replace( srsUrl, "${ac}", uniprotAC );
    }

    private boolean isSpliceVariant( Protein protein ) {

        if ( protein == null ) {
            return false; // throw IllegalArgumentException ??
        }

        Collection xrefs = protein.getXrefs();
        if ( xrefs == null ) {
            return false;
        }

        for ( Iterator iterator = xrefs.iterator(); iterator.hasNext(); ) {
            Xref xref = (Xref) iterator.next();
            CvXrefQualifier qualifier = xref.getCvXrefQualifier();
            if ( qualifier != null && qualifier.equals( isoFormParentXrefQualifier ) ) {
                return true;
            }
        }

        return false;
    }

    public static final int PRIMARY_AC = 0;
    public static final int SECONDARY_AC = 1;
    public static final int ALL_AC = 2;

    /**
     * From a SPTR entry we try to get a set of IntAct proteins.<br> As a SPTR entry can contains several ACs, there is
     * a probability that it gives us several IntAct protein.<br> We exclude splice variants.
     *
     * @param sptrEntry a SPTR entry
     * @param helper    the intact data source
     * @param taxid     the taxid filter (can be null)
     *
     * @return An collection of Intact protein or null if an error occur.
     */
    private Collection getProteinsFromSPTrAC( SPTREntry sptrEntry,
                                              CvXrefQualifier qualifier,
                                              String taxid,
                                              int acType,
                                              IntactHelper helper )
            throws SPTRException, IntactException {

        if ( PRIMARY_AC != acType &&
             SECONDARY_AC != acType &&
             ALL_AC != acType ) {
            throw new IllegalArgumentException( "the acType has to be either: PRIMARY_AC or SECONDARY_AC or ALL_AC" );
        }

        String spAC[] = sptrEntry.getAccessionNumbers();

        logInfo( spAC.length + " AC in the SPTR entry." );

        int start, stop = 0;
        if ( PRIMARY_AC == acType ) {
            // only the first one
            start = 0;
            stop = 1;
        } else if ( SECONDARY_AC == acType ) {
            // all but the first one
            start = 1;
            stop = spAC.length;
        } else {
            start = 0;
            stop = spAC.length;
        }

        logInfo( "We use: " + start + ".." + stop );

        Collection proteins = new HashSet( 2 ); // 2 will be enough in most cases
        int i = 0;
        for ( i = start; i < stop; i++ ) {
            String ac = spAC[ i ];
            Collection tmp = helper.getObjectsByXref( Protein.class, uniprotDatabase, qualifier, ac );

            logInfo( "look for " + ac );
            logInfo( tmp.size() + " proteins found" );

            for ( Iterator iterator = tmp.iterator(); iterator.hasNext(); ) {
                Protein p = (Protein) iterator.next();
                // keep the protein only if no taxid is specified OR the taxid is the same.
                if ( taxid == null || p.getBioSource().getTaxId().equals( taxid ) ) {
                    if ( !isSpliceVariant( p ) ) {
                        // insert only non splice variant
                        proteins.add( p );
                    }
                }
            }
        }

        logInfo( proteins.size() + " proteins selected" + ( taxid == null ? "" : " using filter taxid: " + taxid ) );

        return proteins;
    }

    /**
     * Check if the given protein has been demerged. <br>
     * <p/>
     * Algorithm sketch:
     * <pre>
     * (1) gets its uniprot id as Xref( uniprot, identity )
     * (2) query SRS and retreive a set of SPTR entries
     * (3) count the occurence of that ID being a secondary AC
     *     if( > 1 ) : return true
     *          else : return false.
     * </pre>
     *
     * @param protein the protein for which we want to know if it has been demerged
     *
     * @return true if the protein has been demerged, otherwise false.
     *
     * @throws IntactException if any exception is raised during the process.
     */
    private boolean isDemerged( Protein protein ) throws IntactException {

        Exception exceptionRaised = null;

        // get the Xref( uniprot, identity ) of that protein
        Collection xrefs = protein.getXrefs();
        String primaryAC = null;
        for ( Iterator iterator = xrefs.iterator(); iterator.hasNext() && null == primaryAC; ) {
            Xref xref = (Xref) iterator.next();

            if ( identityXrefQualifier.equals( xref.getCvXrefQualifier() ) &&
                 uniprotDatabase.equals( xref.getCvDatabase() ) ) {

                // found it
                primaryAC = xref.getPrimaryId();
            }
        } // xrefs

        String sourceUrl = null;

        // count of reference to that ac in the secondary ACs of the retreived Entry from SRS.
        int countSecondaryAC = 0;

        if ( null == primaryAC ) {
            // no Xref( uniprot, identity ) found
            throw new IntactException( "No Xref( uniprot, identity ) found for the protein( " + protein.getAc() + " )" );

        } else {
            // query SRS
            sourceUrl = getUrl( primaryAC );

            logInfo( "Parsing: " + sourceUrl );

            EntryIterator localEntryIterator = null;
            try {
                URL url = new URL( sourceUrl );
                InputStream is = url.openStream();
                localEntryIterator = YASP.parseAll( is );
            } catch ( IOException e ) {
                exceptionRaised = e;
            } catch ( YASPException e ) {
                exceptionRaised = e;
            }

            if ( exceptionRaised != null ) {
                // not sure that the protein was not demerged, then throw the exception
                exceptionRaised.printStackTrace();
                throw new IntactException( "An Exception has been raise while trying to parse: " + sourceUrl +
                                           " ) in order to check if protein " + protein.getAc() + " was demerged.",
                                           exceptionRaised );
            }

            while ( localEntryIterator.hasNext() ) {

                // Check if there is any exception remaining in the Entry before to use it
                if ( localEntryIterator.hadException() ) {

                    Exception originalException = localEntryIterator.getException().getOriginalException();
                    parsingExceptions.put( new Integer( entryCount ), originalException );

                    if ( originalException != null ) {
                        if ( debugOnScreen ) {
                            originalException.printStackTrace();
                            localEntryIterator.getException().printStackTrace();
                        }
                    } else {
                        if ( logger != null ) {
                            logger.error( "Parsing error while processing the entry " + entryCount,
                                          localEntryIterator.getException() );
                        }
                        if ( debugOnScreen ) {
                            localEntryIterator.getException().printStackTrace();
                        }
                    }

                    // wrong entries are NOT processed any further
                    writeEntry2file( localEntryIterator );
                    continue;
                }

                // get the SPTREntry
                SPTREntry sptrEntry = (SPTREntry) localEntryIterator.next();
                String spAC[] = null;
                try {
                    spAC = sptrEntry.getAccessionNumbers();
                } catch ( SPTRException e ) {

                    throw new IntactException( "An Exception has been raise while checking if the protein( " +
                                               protein.getAc() + " ) was demerged", exceptionRaised );
                }

                // Count the reference of the current primary AC in the set of retreived secondary ACs.
                for ( int i = 1; i < spAC.length; i++ ) {
                    String secondaryAC = spAC[ i ];
                    if ( secondaryAC.equals( primaryAC ) ) {
                        countSecondaryAC++;
                    }
                } // for
            } // while
        }

        boolean isDemerged = false;

        logInfo( primaryAC + " was found " + countSecondaryAC + " time(s) as secondary AC in " + sourceUrl );

        if ( countSecondaryAC > 1 ) {
            logInfo( "Hence we have here a protein eligible for demerge." );
            isDemerged = true;
        }

        return isDemerged;
    }

    /**
     * Get existing splice variant generated from that SPTREntry. <br>
     * <pre>
     * Algorithm sketch:
     *          1 for each Protein p in PROTEINS
     *            1.1 get it's IntAct AC
     *            1.2 search for all Xrefs which have are qualified by isoform-parent with that AC as a primary key.
     *            1.3 Get the Proteins (should be one since Xref are not shared) who own that particular Xref.
     * </pre>
     *
     * @param master The master protein of the splice variant
     * @param helper The database access
     *
     * @return the created splice variants
     */
    private Collection getSpliceVariantFromSPTrAC( Protein master, IntactHelper helper ) throws IntactException {

        if ( master == null ) {
            return Collections.EMPTY_SET;
        }

        // TODO to get the splice variant, we could use the SPTREntry and get the SV by uniprot ID.

        Collection spliceVariants = null; // we want a distinct set of Protein, 2 will be enough in most cases

        // need that reference to ac out of the loop in order to have it available if an exception is raised.
        String ac = null;

        ac = master.getAc();
        logInfo( "Look for splice variant for the master: " + master.getShortLabel() + "(" + ac + ")" );

        // All splice proteins have 'this' protein as the primary id.
        Collection proteins = helper.getObjectsByXref( Protein.class, intactDatabase, isoFormParentXrefQualifier, ac );

        if ( proteins != null || !proteins.isEmpty() ) {
            logInfo( proteins.size() + " splice variant(s) found." );

            if ( spliceVariants == null ) {
                spliceVariants = new HashSet( 2 );
            }

            spliceVariants.addAll( proteins );
        } else {
            logInfo( "no splice variant found." );
        }

        logInfo( spliceVariants.size() + " splice variant(s) selected." );

        if ( spliceVariants == null ) {
            spliceVariants = Collections.EMPTY_SET;
        }

        return spliceVariants;
    }

    private Collection getTaxids( final SPTREntry sptrEntry ) throws SPTRException {

        int organismCount = sptrEntry.getOrganismNames().length;
        ArrayList taxids = new ArrayList( organismCount );

        for ( int i = 0; i < organismCount; i++ ) {
            String organism = sptrEntry.getOrganismNames()[ i ];
            String entryTaxid = sptrEntry.getNCBITaxonomyID( organism );
            taxids.add( entryTaxid );
        }

        return taxids;
    }

    /**
     * Checks if the protein has been annotated with the no-uniprot-update CvTopic, if so, return false,
     * otherwise true. That flag is added to a protein when created via the editor. As some protein may
     * have a UniProt ID as identity we don't want those to be overwitten.
     *
     * @param protein the protein to check
     *
     * @return false if no Annotation having CvTopic( no-uniprot-update ), otherwise true.
     */
    private boolean needsUniprotUpdate( final Protein protein ) {

        boolean needsUpdate = true;

        if( null == noUniprotUpdate ) {
            // in case the term hasn't been created, assume there are no proteins created via editor.
            return true;
        }

        for ( Iterator iterator = protein.getAnnotations().iterator(); iterator.hasNext() && true == needsUpdate; ) {
            Annotation annotation = (Annotation) iterator.next();

            if( noUniprotUpdate.equals( annotation.getCvTopic() ) ) {
                needsUpdate = false;
            }
        }

        return needsUpdate;
    }

    /**
     * From a SPTREntry, that method will look for the correxponding proteins in IntAct in order to update its data or
     * create brand new if it doesn't exists.
     *
     * @param sptrEntry the SPTR entry
     * @param update    If true, update existing Protein objects according to the retrieved data. else, skip existing
     *                  Protein objects.
     */
    private void createProteinFromSPTrEntry( final SPTREntry sptrEntry,
                                             final boolean update ) throws SPTRException {

        logInfo( "=================================================================================" );
        logInfo( "update requested: " + update );

        Protein selectedProtein = null;

        try {
            // according to the SPTR entry, get the corresponding proteins in IntAct
            // we don't activate the taxid filter here. Can throw IntactException
            Collection proteins = getProteinsFromSPTrAC( sptrEntry, identityXrefQualifier, null, PRIMARY_AC, helper );

            // TODO bug to fix !!
            // use case
            //
            // we have a protein that has AC 4 3 2 1
            // on the web site the same entry 4 gives multiple flat files
            // what happens is that  is a share secondary AC to many proteins
            // eg. 4 as P02248 -> 11 proteins


            // Select which taxid to consider in the process.
            Collection taxids = getTaxids( sptrEntry );

            // TODO get SV using the SPTRentry here !!

            boolean generateProteinShortlabelUsingBiosource = false;
            if ( taxids.size() > 1 ) {
                // when we have more than one biosource, the shortlabel has to get the biosource name in it.
                generateProteinShortlabelUsingBiosource = true;
            }

            /**
             * Process all collected taxids
             */
            for ( Iterator iteratorTaxid = taxids.iterator(); iteratorTaxid.hasNext(); ) {
                String sptrTaxid = (String) iteratorTaxid.next();
                logInfo( "" );
                logInfo( "Prossessing: sptrTaxid=" + sptrTaxid );

                proteinTotal++; // wrong since the demerge

                // get a valid BioSource from either Intact or Newt
                BioSource sptrBioSource = bioSourceFactory.getValidBioSource( sptrTaxid );
                logInfo( "selected biosource: " + sptrBioSource );

                selectedProtein = null;
                // look for a protein in the set (retreived from primary AC) which has that taxid
                // there should be only one.
                for ( Iterator iterator = proteins.iterator(); iterator.hasNext() && selectedProtein == null; ) {
                    Protein p = (Protein) iterator.next();
                    BioSource bs = p.getBioSource();

                    if ( bs.equals( sptrBioSource ) ) {
                        // found it.
                        if( needsUniprotUpdate( p ) ) {

                            // that protein should be updated.
                            selectedProtein = p;

                            // remove it from the collection
                            iterator.remove();

                        } else {

                            // that protein should not be updated but it is returned to the user.
                            logInfo( "A protein was found but 'no-uniprot-update' was requested: " + p );
                            this.proteins.add( p );
                        }
                    }
                }

                // remove it from the collection
//                proteins.remove( selectedProtein );

                // allow to know, while we are processing the splice variant, if the master was demerged.
                boolean masterWasDemerged = false;

                if ( selectedProtein == null ) {

                    logInfo( "No existing protein (with no 'no-uniprot-update' Annotation) found in IntAct for taxid " + sptrTaxid );

                    /**
                     * We could NOT find an existing protein so now two cases have to be taken into account:
                     * if an old protein already exists (ie. created before demerge) it would not have been
                     * picked up searching by Xref( uniprot, identity ) for the primary AC so now we search
                     * by secondary AC of the SPTR Entry.
                     *
                     *     1) If we find one or more (it could confirm that it was a demerge)
                     *        For each of those proteins:
                     *             get that protein Xref( uniprot, identity ) and query SRS
                     *                 a) if MORE THAN ONE Entry have that AC as secondary, it means that this
                     *                    is a demerge. => we update that protein to be the demerged one
                     *                    (!) Related splice variant (if any) have to be updated too.
                     *
                     *                 b) if not, (hence, it was not a demerge) create a new protein.
                     *
                     *     2) If we didn't find it, we create a new protein.
                     */

                    // (1) Search for IntAct proteins by Xref( uniprot, identity ) based on the secondary Ac of the entry

                    logInfo( "Looking for protein having secondaryId with filter on " + sptrTaxid );
                    Collection secondaryProteins = getProteinsFromSPTrAC( sptrEntry, identityXrefQualifier,
                                                                          sptrTaxid,
                                                                          SECONDARY_AC, helper );

                    // take out the no-uniprot-update
                    for ( Iterator iterator = secondaryProteins.iterator(); iterator.hasNext(); ) {
                        Protein protein = (Protein) iterator.next();

                        if( false == needsUniprotUpdate( protein ) ) {

                            logInfo( "A protein was found (secondaryId) but 'no-uniprot-update' was requested: " + protein );
                            // add that protein to the list that will be returned to the user.
                            this.proteins.add( protein );

                            // remove it t the collection
                            iterator.remove();
                        }
                    }

                    boolean doUpdate = false;
                    boolean doCreate = false;

                    if ( secondaryProteins.isEmpty() ) {
                        // no protein found, then create it
                        logInfo( "No protein found by secondary ID." );
                        doCreate = true;

                    } else {

                        logInfo( "found " + secondaryProteins.size() + " protein(s) by secondary AC." );

                        // found proteins
                        boolean isDemerged = false;

                        // There should be only one protein as we applied a filter on taxid.

                        logInfo( "Check if one of those proteins is to be demerged." );
                        for ( Iterator iterator = secondaryProteins.iterator(); iterator.hasNext() && !isDemerged; ) {
                            Protein secondaryProtein = (Protein) iterator.next();

                            isDemerged = isDemerged( secondaryProtein ); // leave the procedure if an IntactException is thrown

                            // select the protein to be updated.
                            selectedProtein = secondaryProtein;
                        }

                        if ( isDemerged ) {
                            // update the protein
                            logInfo( "A protein was found to be demerged: " + selectedProtein.getAc() );

                            doUpdate = true;
                            masterWasDemerged = true;
                        } else {
                            // create the protein
                            doCreate = true;
                        }

                    } // else

                    if ( localTransactionControl ) {
                        helper.startTransaction( BusinessConstants.JDBC_TX );
                    }

                    if ( doCreate ) {
                        logInfo( "Call createProtein with parameter BioSource.taxId=" + sptrBioSource.getTaxId() );

                        if ( ( selectedProtein = createNewProtein( sptrEntry,
                                                                   sptrBioSource,
                                                                   generateProteinShortlabelUsingBiosource ) ) != null ) {
                            logInfo( "creation sucessfully done: " + selectedProtein.getShortLabel() );
                        }
                    }

                    if ( doUpdate ) {
                        logInfo( "Call updateProtein with parameter BioSource.taxId=" + sptrBioSource.getTaxId() );

                        if ( updateExistingProtein( selectedProtein,
                                                    sptrEntry,
                                                    sptrBioSource,
                                                    generateProteinShortlabelUsingBiosource ) ) {
                            logInfo( "update sucessfully done" );
                        }
                    }

                    if ( localTransactionControl ) {
                        helper.finishTransaction();
                    }
                    logInfo( "Transaction complete" );

                } else {

                    logInfo( "selected protein: " + selectedProtein );

                    if ( update ) {
                        /*
                        * We are doing the update of the existing protein only if the
                        * user request it we only update its content if needed
                        */
                        logInfo( "A protein exists for that taxid (" + sptrTaxid + "), try to update" );

                        if ( localTransactionControl ) {
                            helper.startTransaction( BusinessConstants.JDBC_TX );
                        }

                        if ( updateExistingProtein( selectedProtein,
                                                    sptrEntry,
                                                    sptrBioSource,
                                                    generateProteinShortlabelUsingBiosource ) ) {
                            logInfo( "update sucessfully done" );
                        }

                        if ( localTransactionControl ) {
                            helper.finishTransaction();
                            logInfo( "Transaction complete" );
                        }

                    } else {
                        // Store the protein in the list we'll return
                        this.proteins.add( selectedProtein );
                    }

                } // selectedProtein != null


                ///////////////////////////////////////
                // Management of the splice variant

                // pull up all splice variants attached to the selected protein.
                // that selected protein has been either
                //     - freshly created, there will be no splice variant attached.
                //     - updated in which case we MIGHT find some
                //       Note: the AC of an updated protein stays the same.

                Protein master = selectedProtein;
                Collection spliceVariants = getSpliceVariantFromSPTrAC( master, helper );

                // take out the no-uniprot-update
                for ( Iterator iterator = spliceVariants.iterator(); iterator.hasNext(); ) {
                    Protein protein = (Protein) iterator.next();

                    if( false == needsUniprotUpdate( protein ) ) {

                        logInfo( "A splice variant was found but 'no-uniprot-update' was requested: " + protein );
                        // add that protein to the list that will be returned to the user.
                        this.proteins.add( protein );

                        // remove it t the collection
                        iterator.remove();
                    }
                }


                // retrieve the comments of that entry
                SPTRComment[] comments = sptrEntry.getComments( Factory.COMMENT_ALTERNATIVE_SPLICING );

                for ( int j = 0; j < comments.length; j++ ) {
                    SPTRComment comment = comments[ j ];
                    if ( !( comment instanceof AlternativeSplicingAdapter ) ) {
                        if ( logger != null ) {
                            logger.error( "Looking for Comment type: " + AlternativeSplicingAdapter.class.getName() );
                            logger.error( "Could not handle comment type: " + comment.getClass().getName() );
                            logger.error( "SKIP IT." );
                        }
                        continue; // skip it, go to next iteration.
                    }

                    AlternativeSplicingAdapter asa = (AlternativeSplicingAdapter) comment;
                    Isoform[] isoForms = asa.getIsoforms();

                    // for each comment, browse its isoforms ...
                    for ( int ii = 0; ii < isoForms.length; ii++ ) {
                        Isoform isoForm = isoForms[ ii ];

                        spliceVariantTotal++;

                        /**
                         * browse isoform's IDs which, in case they have been store in the database,
                         * are used as shortlabel of the related Protein.
                         */
                        String[] ids = isoForm.getIDs();

                        if ( ids.length > 0 ) {

                            // only the first ID should be taken into account, the following ones are secondary IDs.
                            String spliceVariantID = ids[ 0 ];

                            logInfo( "Splice variant ID: " + spliceVariantID );

                            // Search for an existing splice variant from IntAct corresponding to the ID found in the entry.
                            Protein spliceVariant = null;
                            boolean spliceVariantFound = false;
                            for ( Iterator iterator = spliceVariants.iterator(); iterator.hasNext() && !spliceVariantFound; ) {
                                Protein sv = (Protein) iterator.next();

                                /* How to spot and select the right splice variant ?
                                 * -----------------------------------------------
                                 * => Just filter using the Xref( uniprot, identity ).
                                 */

                                if ( sv.getBioSource().equals( sptrBioSource ) ) {

                                    // check for Xref( uniprot, identity )
                                    for ( Iterator iterator1 = sv.getXrefs().iterator(); iterator1.hasNext() && !spliceVariantFound; ) {
                                        Xref xref = (Xref) iterator1.next();
                                        if ( identityXrefQualifier.equals( xref.getCvXrefQualifier() )
                                             &&
                                             uniprotDatabase.equals( xref.getCvDatabase() ) ) {

                                            // check if the primary id of that Xref is one the the splice variant ID
                                            int idx = 0;
                                            for ( idx = 0; idx < ids.length && !spliceVariantFound; idx++ ) {
                                                if ( xref.getPrimaryId().equals( ids[ idx ] ) ) {
                                                    // found it.
                                                    spliceVariant = sv;
                                                    spliceVariantFound = true;

                                                    logInfo( "Splice variant found using " +
                                                             ( idx == 0 ? "primary" : "secondary" ) +
                                                             " ID: " + ids[ idx ] );
                                                }
                                            }
                                        }
                                    } // for xrefs
                                } else {
                                    throw new IntactException( "A splice variant:" + sv + NEW_LINE +
                                                               "associated to the protein: " + selectedProtein + NEW_LINE +
                                                               "doesn't have the same BioSource. abort." );
                                }
                            } // for spliceVariants

                            if ( spliceVariantFound ) {
                                // ... update it.

                                if ( update || masterWasDemerged ) {

                                    // We only perform an update of the existing splice variant if the user request it
                                    // or if the master protein was a demerged protein.

                                    /**
                                     * We are doing the update of the existing protein only if the
                                     * user request it we only update its content if needed
                                     */
                                    logInfo( "A splice variant exists for that taxid (" + sptrTaxid + "), try to update" );

                                    if ( localTransactionControl ) {
                                        /**
                                         * We want here to use a database transaction (NOT OBJECT) because
                                         * the creation of a splice variant can involves the creation of
                                         * Annotation. This is a problem with an Object transaction because
                                         * everything is written in the database when the transaction is
                                         * commited ... in the case of an Annotation, it needs to be written
                                         * in the DB before the Annotated object in order not to violate
                                         * any integrity rule.
                                         * By using a Database transaction (JDBC_TX) it is written when it's
                                         * asked for and everything is deleted if something goes wrong.
                                         */
                                        helper.startTransaction( BusinessConstants.JDBC_TX );
                                    }

                                    if ( updateExistingSpliceVariant( isoForm,
                                                                      spliceVariantID,
                                                                      spliceVariant,
                                                                      master,
                                                                      sptrEntry,
                                                                      sptrBioSource,
                                                                      generateProteinShortlabelUsingBiosource ) ) {
                                        logInfo( "update sucessfully done" );
                                    }

                                    if ( localTransactionControl ) {
                                        helper.finishTransaction();
                                        logInfo( "Transaction complete" );
                                    }
                                } else {
                                    // Store the splice variant in the list we'll return
                                    this.proteins.add( spliceVariant );
                                }

                                // remove that splice variant from the collection.
                                spliceVariants.remove( spliceVariant );

                            } else {

                                // could not find the splice variant in IntAct, then we create it.
                                logInfo( "No existing splice variant for that taxid (" + sptrTaxid + "), create a new one" );

                                if ( localTransactionControl ) {
                                    // See remarks about database transaction above.
                                    helper.startTransaction( BusinessConstants.JDBC_TX );
                                }
                                if ( ( spliceVariant = createNewSpliceVariant( isoForm, spliceVariantID, master,
                                                                               sptrEntry, sptrBioSource,
                                                                               generateProteinShortlabelUsingBiosource ) ) != null ) {
                                    logInfo( "creation sucessfully done" );
                                }
                                if ( localTransactionControl ) {
                                    helper.finishTransaction();
                                }
                                logInfo( "Transaction complete" );
                            }
                        } // if (ids.length > 0)
                    } //for isoforms
                } //for comments
            } // for each taxid

            /*
            * Check if the protein list is empty, if not, that means we have in IntAct some
            * proteins linked with a BioSource which is not recorded in the current SPTR Entry
            */
            if ( false == proteins.isEmpty() ) {

                if ( logger != null ) {
                    logger.error( "The following association's <protein,taxid> list has been found in IntAct but not in SPTR:" );
                }
                for ( Iterator iterator = proteins.iterator(); iterator.hasNext(); ) {
                    Protein p = (Protein) iterator.next();
                    BioSource bs = p.getBioSource();

                    if ( logger != null ) {
                        logger.error( "\t intactAC=" + p.getAc() +
                                      " shortlabel:" + p.getShortLabel() +
                                      " taxid=" + ( bs == null ? "none" : bs.getTaxId() ) );
                    }
                }
            }
        } catch ( IntactException ie ) {
            if ( logger != null ) {
                logger.error( ie.getRootCause(), ie );
            }

            writeEntry2file( entryIterator );

            // Try to rollback
            if ( helper.isInTransaction() ) {
                if ( logger != null ) {
                    logger.error( "Try to undo transaction." );
                }
                try {
                    // try to undo the transaction
                    helper.undoTransaction();
                } catch ( IntactException ie2 ) {
                    if ( logger != null ) {
                        logger.error( "Could not undo the current transaction" );
                    }
                }
            }
        }
    }

    public boolean addNewXref( AnnotatedObject current, final Xref xref ) {
        // Make sure the xref does not yet exist in the object
        if ( current.getXrefs().contains( xref ) ) {
            logInfo( "SKIPPED: [" + xref + "] already exists" );
            return false; // quit
        }

        // add the xref to the AnnotatedObject
        current.addXref( xref );

        // That test is done to avoid to record in the database an Xref
        // which is already linked to that AnnotatedObject.
        if ( xref.getParentAc() == current.getAc() ) {
            try {
                helper.create( xref );
                if ( logger != null ) {
                    logInfo( "CREATED: [" + xref + "]" );
                }
            } catch ( Exception e_xref ) {
                if ( logger != null ) {
                    logger.error( "Error while creating an Xref for protein " + current, e_xref );
                }
                return false;
            }
        }

        return true;
    }

    public boolean addNewAlias( AnnotatedObject current, Alias alias ) {
        // Make sure the alias does not yet exist in the object
        Collection aliases = current.getAliases();
        for ( Iterator iterator = aliases.iterator(); iterator.hasNext(); ) {
            Alias anAlias = (Alias) iterator.next();
            if ( anAlias.equals( alias ) ) {
                logInfo( "SKIPPED: [" + alias + "] already exists" );
                return false; // already in, exit
            }
        }

        // add the alias to the AnnotatedObject
        current.addAlias( alias );

        // That test is done to avoid to record in the database an Alias
        // which is already linked to that AnnotatedObject.
        if ( alias.getParentAc() == current.getAc() ) {
            try {
                helper.create( alias );
                logInfo( "CREATED: [" + alias + "]" );
            } catch ( Exception e_alias ) {
                if ( logger != null ) {
                    logger.error( "Error when creating an Alias for protein " + current, e_alias );
                }
                return false;
            }
        }

        return true;
    }

    /**
     * Add an annotation to an annotated object. <br> We check if that annotation is not already existing, if so, we
     * don't record it.
     *
     * @param current    the annotated object to which we want to add an Annotation.
     * @param annotation the annotation to add the Annotated object
     */
    public void addNewAnnotation( AnnotatedObject current, final Annotation annotation ) {

        // TODO: what if an annotation is already existing ... should we use a single one ?
        // YES ! we should search dor it first and reuse existing Annotation

        // Make sure the alias does not yet exist in the object
        Collection annotations = current.getAnnotations();
        for ( Iterator iterator = annotations.iterator(); iterator.hasNext(); ) {
            Annotation anAnnotation = (Annotation) iterator.next();
            if ( anAnnotation.equals( annotation ) ) {
                return; // already in, exit
            }
        }

        // add the alias to the AnnotatedObject
        current.addAnnotation( annotation );

        try {
            helper.create( annotation );
            logInfo( "ADD " + annotation + " to: " + current.getShortLabel() );
        } catch ( Exception e_alias ) {
            if ( logger != null ) {
                logger.error( "Error when creating an Annotation for protein " + current, e_alias );
            }
        }
    }

    /**
     * update all Xref specific to a database. That procedure is used when creating and updating a Protein Xref.
     *
     * @param sptrEntry  Entry from which we get the Xrefs
     * @param protein    The protein to update
     * @param database   The database filter
     * @param cvDatabase The CvDatabase to link in the Protein's Xref
     *
     * @return true if the protein has been updated, else false.
     *
     * @throws SPTRException
     */
    private boolean updateXref( final SPTREntry sptrEntry,
                                Protein protein,
                                final String database,
                                final CvDatabase cvDatabase ) throws SPTRException, IntactException {

        boolean needUpdate = false;

        // create existing GO Xrefs
        SPTRCrossReference cr[] = sptrEntry.getCrossReferences( database );
        logInfo( "Look in the entry for Xref of type: " + database + " (" + cr.length + " found)" );

        // create a list of new xrefs
        Collection xrefs = new ArrayList( cr.length );

        for ( int i = 0; i < cr.length; i++ ) {
            SPTRCrossReference sptrXref = cr[ i ];

            String ac = sptrXref.getAccessionNumber();
            String id = null;

            try {

                id = sptrXref.getPropertyValue( SPTRCrossReference.SECONDARY_PROPERTY );

            } catch ( AristotleSPTRException e ) {
                // there was no description, we don't fail for that.
                if ( logger != null ) {
                    logger.warn( "Entry(sptrId=" + sptrEntry.getAccessionNumbers()[ 0 ] + "), Xref(id=" + ac +
                                 ") has no description and a AristotleSPTRException was thrown", e );
                }
            }

            xrefs.add( new Xref( myInstitution,
                                 cvDatabase,
                                 ac,
                                 id,
                                 null,
                                 null ) );
        }

        // update the protein
        needUpdate = updateXrefCollection( protein, cvDatabase, xrefs );

        logInfo( "Update requested for " + database + ": " + needUpdate );

        return needUpdate;
    }

    /**
     * Those Xref are not stored explicitly in the entry but are present as gene name or gene synonyms. Hence we are
     * looking for geneNames starting with KIAA and create Xrefs out of them.
     *
     * @param sptrEntry the entry from which we'll read the gene name.
     * @param protein   the protein for which we want to update the xrefs.
     *
     * @return true if anything has been updated, otherwise false.
     */
    private boolean updateHugeXref( final SPTREntry sptrEntry,
                                    Protein protein ) throws SPTRException, IntactException {

        Gene[] genes = sptrEntry.getGenes();
        Collection kiaas = null;
        for ( int i = 0; i < genes.length; i++ ) {

            String geneName = genes[ i ].getName();
            if ( geneName.startsWith( "KIAA" ) ) {

                if ( kiaas == null ) {
                    kiaas = new ArrayList( 1 );
                }

                kiaas.add( new Xref( myInstitution, hugeDatabase, geneName, null, null, null ) );
            }

            // get KIAAs from synonyms (if any)
            String[] synonyms = genes[ i ].getSynonyms();
            for ( int ii = 0; ii < synonyms.length; ii++ ) {

                String syn = synonyms[ ii ];

                if ( syn != null && syn.startsWith( "KIAA" ) ) {

                    if ( kiaas == null ) {
                        kiaas = new ArrayList( 1 );
                    }

                    kiaas.add( new Xref( myInstitution, hugeDatabase, syn, null, null, null ) );
                }
            } // Gene name synonyms
        } // genes

        if ( kiaas == null ) {
            kiaas = Collections.EMPTY_LIST;
        }

        // update Xrefs
        return updateXrefCollection( protein, hugeDatabase, kiaas );
    }

    /**
     * Generate the shortlabel of a protein. <br> Selects the swissProt name if available, otherwise the TrEMBL's. <br>
     * Takes care of replacing illegal characters like, hyphen, spaces and dots. <br> the generated shortlabel is always
     * lowercase. <br> We use the organism name inthe shortlabel if requested.
     *
     * @param sptrEntry the SPTR entry from which we create/update the protein.
     * @param bioSource the BioSource from which we get the shortlabel in case the user requests it.
     * @param generateProteinShortlabelUsingBiosource
     *                  if true, the bioSource shortlabel is integrated in the protein's shortlabel.
     *
     * @return a protein shortlabel.
     *
     * @throws SPTRException if something goes wrong while reading data from the SPTREntry.
     */
    private String generateProteinShortLabel( SPTREntry sptrEntry,
                                              BioSource bioSource,
                                              boolean generateProteinShortlabelUsingBiosource ) throws SPTRException {
        String shortlabel = null;

        if ( generateProteinShortlabelUsingBiosource ) {

            Gene[] genes = sptrEntry.getGenes();

            if ( genes.length > 0 ) {
                shortlabel = genes[ 0 ].getName();

                // replace any weird character by '_'
                if ( shortlabel != null ) {
                    shortlabel = SearchReplace.replace( shortlabel, "-", "_" );
                    shortlabel = SearchReplace.replace( shortlabel, " ", "_" );
                    shortlabel = SearchReplace.replace( shortlabel, ".", "_" );
                }
            }

            if ( shortlabel == null ) {
                final String msg = "WARNING: could not generate the Shortlabel, no gene name available. Using the AC.";
                if ( debugOnScreen ) {
                    System.err.println( msg );
                }
                if ( logger != null ) {
                    logger.warn( msg );
                }
                shortlabel = sptrEntry.getID();
            } else {
                // check if the ID contains already _specie (TREMBL)
                int index = shortlabel.indexOf( '_' );
                if ( index != -1 ) {
                    if ( logger != null ) {
                        logInfo( "Remove existing _${specie} from " + shortlabel );
                    }
                    shortlabel = shortlabel.substring( 0, index );
                    if ( logger != null ) {
                        logInfo( "Result: " + shortlabel );
                    }
                }

                // Concatenate Biosource to the gene name !
                if ( bioSource.getShortLabel() != null && !bioSource.getShortLabel().equals( "" ) ) {
                    shortlabel = shortlabel + "_" + bioSource.getShortLabel();
                } else {
                    final String msg = "WARNING: generate the shortlabel using taxid since the shortlabel doesn't exists.";
                    if ( debugOnScreen ) {
                        System.err.println( msg );
                    }
                    if ( logger != null ) {
                        logger.warn( msg );
                    }
                    shortlabel = shortlabel + "_" + bioSource.getTaxId();
                }
            }
        } else {
            shortlabel = sptrEntry.getID();
        }

        return shortlabel.toLowerCase();
    }


    /**
     * Update of the Xref of a protein.
     * <p/>
     * <pre>
     * Algo sketch:
     * 1) select a subset of the xref of the given protein based on the given CvDatabase
     * 2) select the outdated Xref
     * 3) reused them to create new Xref and delete the remaining one. By doing so we don't waste ACs
     * </pre>
     *
     * @param protein  the protein what we want to update the Xrefs
     * @param database the target database
     * @param newXrefs the new set of xrefs
     *
     * @return true if the protein has been updated, otherwise false
     *
     * @throws IntactException
     */
    private boolean updateXrefCollection( Protein protein, CvDatabase database, Collection newXrefs )
            throws IntactException {

        if ( protein == null ) {
            throw new IllegalArgumentException( "You must give a non null protein." );
        }

        if ( database == null ) {
            throw new IllegalArgumentException( "You must give a non null database." );
        }

        if ( newXrefs == null ) {
            throw new IllegalArgumentException( "You must give a non null collection of xref." );
        }

        boolean updated = false;
        Collection currentXrefs = null;

        // select only the xref of the given database
        for ( Iterator iterator = protein.getXrefs().iterator(); iterator.hasNext(); ) {
            Xref xref = (Xref) iterator.next();
            if ( database.equals( xref.getCvDatabase() ) ) {
                if ( currentXrefs == null ) {
                    currentXrefs = new ArrayList();
                }
                currentXrefs.add( xref );
            }
        }

        if ( currentXrefs == null ) {
            currentXrefs = Collections.EMPTY_LIST;
        }

        Collection toDelete = CollectionUtils.subtract( currentXrefs, newXrefs ); // current minus new
        Collection toCreate = CollectionUtils.subtract( newXrefs, currentXrefs );

        Iterator toDeleteIterator = toDelete.iterator();
        for ( Iterator iterator = toCreate.iterator(); iterator.hasNext(); ) {
            Xref xref = (Xref) iterator.next();

            if ( toDeleteIterator.hasNext() ) {
                // in order to avoid wasting ACs, we overwrite attributes of an outdated xref.
                Xref recycledXref = (Xref) toDeleteIterator.next();

                // note: parent_ac was already set before as the object was persistent
                recycledXref.setPrimaryId( xref.getPrimaryId() );
                recycledXref.setSecondaryId( xref.getSecondaryId() );
                recycledXref.setCvDatabase( xref.getCvDatabase() );
                recycledXref.setCvXrefQualifier( xref.getCvXrefQualifier() );
                recycledXref.setDbRelease( xref.getDbRelease() );

                helper.update( recycledXref );
                updated = true;

            } else {

                updated = updated | addNewXref( protein, xref );
            }
        }

        for ( ; toDeleteIterator.hasNext(); ) {
            // delete remaining outdated/unrecycled xrefs
            Xref xref = (Xref) toDeleteIterator.next();
            helper.delete( xref );

            updated = true;
        }

        return updated;
    }

    /**
     * Update of the Aliases of a protein.
     * <p/>
     * <pre>
     * Algo sketch:
     * 1) select all aliases of the given protein
     * 2) select the outdated aliases
     * 3) reused them to create new Alias and delete the remaining one. By doing so we don't waste ACs
     * </pre>
     *
     * @param protein    the protein what we want to update the Aliases
     * @param newAliases the new set of Aliases
     *
     * @return true if the protein has been updated, otherwise false
     *
     * @throws IntactException
     */
    private boolean updateAliasCollection( Protein protein, Collection newAliases ) throws IntactException {

        if ( protein == null ) {
            throw new IllegalArgumentException( "You must give a non null protein." );
        }

        if ( newAliases == null ) {
            throw new IllegalArgumentException( "You must give a non null collection of xref." );
        }

        boolean updated = false;
        Collection currentAliases = protein.getAliases();

        Collection toDelete = CollectionUtils.subtract( currentAliases, newAliases ); // current minus new
        Collection toCreate = CollectionUtils.subtract( newAliases, currentAliases );

        Iterator toDeleteIterator = toDelete.iterator();
        for ( Iterator iterator = toCreate.iterator(); iterator.hasNext(); ) {
            Alias alias = (Alias) iterator.next();

            if ( toDeleteIterator.hasNext() ) {
                // in order to avoid wasting ACs, we overwrite attributes of an outdated xref.
                Alias recycledAlias = (Alias) toDeleteIterator.next();

                // note: parent_ac was already set before as the object was persistent
                recycledAlias.setName( alias.getName() );
                recycledAlias.setCvAliasType( alias.getCvAliasType() );

                helper.update( recycledAlias );
                updated = true;

            } else {

                updated = updated | addNewAlias( protein, alias );
            }
        }

        for ( ; toDeleteIterator.hasNext(); ) {
            // delete remaining outdated/unrecycled aliases
            Alias alias = (Alias) toDeleteIterator.next();
            helper.delete( alias );

            updated = true;
        }

        return updated;
    }

    /**
     * Read the SPTR entry and create a collection of Alias we want to update on the given protein.
     *
     * @param sptrEntry the SPTR Entry from which we will read the gene/locus/synonym/orf information.
     * @param protein   the protein we want to update
     *
     * @return a collection (never null) of Alias. The collection may be empty.
     *
     * @throws SPTRException if an error occurs when reading the gene/locus/synonym/orf information.
     */
    private Collection getProteinAliasesFromSPTR( SPTREntry sptrEntry, Protein protein ) throws SPTRException {

        Collection aliases = null;
        Gene[] genes = sptrEntry.getGenes();
        Alias alias = null;

        for ( int i = 0; i < genes.length; i++ ) {

            String geneName = genes[ i ].getName();

            if ( geneName != null && ( false == "".equals( geneName.trim() ) ) ) {

                alias = new Alias( myInstitution, protein, geneNameAliasType, geneName );

                if ( aliases == null ) {
                    aliases = new ArrayList( 8 );
                }
                aliases.add( alias );
            }


            // create synonyms
            String[] synonyms = genes[ i ].getSynonyms();
            for ( int ii = 0; ii < synonyms.length; ii++ ) {

                String syn = synonyms[ ii ];

                if ( syn != null && ( false == "".equals( syn.trim() ) ) ) {

                    alias = new Alias( myInstitution, protein, geneNameSynonymAliasType, syn );

                    if ( aliases == null ) {
                        aliases = new ArrayList( 8 );
                    }
                    aliases.add( alias );
                }
            } // Gene name synonyms


            // create locus names
            String[] locus = genes[ i ].getLocusNames();
            for ( int ii = 0; ii < locus.length; ii++ ) {

                String locusName = locus[ ii ];

                if ( locusName != null && ( false == "".equals( locusName.trim() ) ) ) {

                    alias = new Alias( myInstitution, protein, locusNameAliasType, locusName );

                    if ( aliases == null ) {
                        aliases = new ArrayList( 8 );
                    }
                    aliases.add( alias );
                }
            } // Locus names


            // create ORF names
            String[] ORFs = genes[ i ].getORFNames();
            for ( int ii = 0; ii < ORFs.length; ii++ ) {

                String orfName = ORFs[ ii ];

                if ( orfName != null && ( false == "".equals( orfName.trim() ) ) ) {

                    alias = new Alias( myInstitution, protein, orfNameAliasType, orfName );

                    if ( aliases == null ) {
                        aliases = new ArrayList( 8 );
                    }
                    aliases.add( alias );
                }
            } // ORFs
        } // Genes

        if ( aliases == null ) {
            aliases = Collections.EMPTY_LIST;
        }

        return aliases;
    }

    private Collection getSpliceVariantAliasesFromSPTR( Isoform isoform, Protein spliceVariant ) throws SPTRException {

        Collection aliases = null;
        String[] isoSynonyms = isoform.getSynonyms();

        if ( isoSynonyms.length > 0 ) {

            aliases = new ArrayList( isoSynonyms.length );

            for ( int i = 0; i < isoSynonyms.length; i++ ) {
                String isoSynonym = isoSynonyms[ i ];

                // create a new alias
                Alias alias = new Alias( myInstitution, spliceVariant, isoformSynonym, isoSynonym );
                aliases.add( alias );
            }

        } else {

            // never send a null collection back.
            aliases = Collections.EMPTY_LIST;
        }

        return aliases;
    }


    /**
     * Extract from the SPTREntry the annotation release and the entry type, then combine them to get a version we will
     * use in the Xref. uniprot, identity )
     *
     * @param sptrEntry the entry from which we extract the information.
     *
     * @return a version as a String.
     *
     * @throws SPTRException
     */
    private String getSPTREntryReleaseVersion( SPTREntry sptrEntry ) throws SPTRException {
        String version = null;
        String uniprotRelease = sptrEntry.getLastAnnotationUpdateRelease();

        if ( sptrEntry.getEntryType() == SPTREntry.SWISSPROT ) {
            version = "SP_" + uniprotRelease;
        } else if ( sptrEntry.getEntryType() == SPTREntry.TREMBL ) {
            // will allow Version up to 999 ... then it will be truncated as Xref.dbRelease is VARCHAR2(10)
            version = "TrEMBL_" + uniprotRelease;
        } else {
            // though should not happen.
            version = uniprotRelease;
        }

        return version;
    }

    /**
     * Update (create them if not exist) SPTR Cross references to the given protein It also deletes all irrelevant Xref(
     * uniprot, identity )
     *
     * @param sptrEntry the entry in which we'll find the primary ID of the Xrefs
     * @param protein   the protein to update
     *
     * @return true if at least one Xref as been added, else false.
     *
     * @throws SPTRException
     */
    private boolean updateUniprotXref4Protein( SPTREntry sptrEntry, Protein protein ) throws SPTRException,
                                                                                             IntactException {

        boolean updated = false;
        String proteinAC[] = sptrEntry.getAccessionNumbers();
        String shortLabel = protein.getShortLabel();

        // TODO use that annotation to quick check if a protein need update
        String version = getSPTREntryReleaseVersion( sptrEntry );

        // TODO we need also to keep track of the last sequence update somehow.
        // TODO a similar realease data is available for the last sequence update
        // we would need to store that information somewhere in order to know what need to be updated.
        // could be annotRelease/seqRelease.
//        String seqRelease = sptrEntry.getLastSequenceUpdateRelease();

        Collection xrefs = new ArrayList( proteinAC.length );

        // TODO looks bizarre here as we do the update twice !!

        // create a list of all new Xrefs
        xrefs.add( new Xref( myInstitution,
                             uniprotDatabase,
                             proteinAC[ 0 ],
                             shortLabel,
                             version,
                             identityXrefQualifier ) );

        String ac = null;
        for ( int i = 1; i < proteinAC.length; i++ ) {
            ac = proteinAC[ i ];

            xrefs.add( new Xref( myInstitution,
                                 uniprotDatabase,
                                 ac,
                                 shortLabel,
                                 version,
                                 secondaryXrefQualifier ) );
        }

        updated = updateXrefCollection( protein, uniprotDatabase, xrefs );

        logInfo( "Update requested for " + uniprotDatabase.getShortLabel() + ": " + updated );

        return updated;
    }

    /**
     * Update (create them if not exist) UNIPROT Cross reference to the given splice variant. It also deletes all
     * irrelevant Xref( uniprot, identity )
     *
     * @param sptrEntry     the entry in which we'll find the primary ID of the Xrefs
     * @param spliceVariant the splice variant to update
     *
     * @return true if at least one Xref as been added, else false.
     *
     * @throws SPTRException
     */
    private boolean updateUniprotXref4SpliceVariant( SPTREntry sptrEntry,
                                                     Protein spliceVariant,
                                                     Isoform isoform ) throws SPTRException, IntactException {
        String masterAc = sptrEntry.getID();
        if ( masterAc != null ) {
            masterAc = masterAc.toLowerCase();
        }
        String[] isoIds = isoform.getIDs();

        String version = getSPTREntryReleaseVersion( sptrEntry );

        // create a list of all new Xrefs
        Collection xrefs = new ArrayList( isoIds.length );
        xrefs.add( new Xref( myInstitution,
                             uniprotDatabase,
                             isoIds[ 0 ],
                             masterAc,
                             version,
                             identityXrefQualifier ) );

        for ( int i = 1; i < isoIds.length; i++ ) {

            String isoId = isoIds[ i ];
            xrefs.add( new Xref( myInstitution,
                                 uniprotDatabase,
                                 isoId,
                                 masterAc,
                                 version,
                                 secondaryXrefQualifier ) );
        }

        return updateXrefCollection( spliceVariant, uniprotDatabase, xrefs );
    }

    /**
     * From a SPTR Entry, create in IntAct a new protein.
     *
     * @param sptrEntry the source entry
     * @param bioSource the BioSource to link to the Protein
     *
     * @return true is the protein is created
     *
     * @throws SPTRException
     * @throws IntactException
     */
    private Protein createNewProtein( SPTREntry sptrEntry,
                                      BioSource bioSource,
                                      boolean generateProteinShortlabelUsingBiosource )
            throws SPTRException,
                   IntactException {

        /**
         * To avoid to have multiple species having the same short label
         * eg. cdc42 are known in human, mouse, bovine and dog
         * we shoold have the labels: cdc42_human, cdc42_mouse ...
         */
        String shortLabel = generateProteinShortLabel( sptrEntry, bioSource, generateProteinShortlabelUsingBiosource );

        Protein protein = new ProteinImpl( myInstitution, bioSource, shortLabel );

        // get the protein info we need
        helper.create( protein );

        String fullName = sptrEntry.getProteinName();
        if ( fullName.length() > 250 ) {
            fullName = fullName.substring( 0, 250 );
        }

        String sequence = sptrEntry.getSequence();
        String crc64 = sptrEntry.getCRC64();

        protein.setFullName( fullName );
        protein.setSequence( helper, sequence );
        protein.setCrc64( crc64 );

        updateXref( sptrEntry, protein, Factory.XREF_SGD, sgdDatabase );
        updateXref( sptrEntry, protein, Factory.XREF_GO, goDatabase );
        updateXref( sptrEntry, protein, Factory.XREF_INTERPRO, interproDatabase );
        updateXref( sptrEntry, protein, Factory.XREF_FLYBASE, flybaseDatabase );
        updateXref( sptrEntry, protein, Factory.XREF_REACTOME, reactomeDatabase );
        updateHugeXref( sptrEntry, protein );

        updateUniprotXref4Protein( sptrEntry, protein );

        // create Aliases
        Collection aliases = getProteinAliasesFromSPTR( sptrEntry, protein );
        updateAliasCollection( protein, aliases );

        // update database
        try {
            // Only update if the protein exists in the DB.
            if ( helper.isPersistent( protein ) ) {
                helper.update( protein );
            }
            // keep that protein
            proteins.add( protein );

            if ( logger != null ) {
                logInfo( "protein updated: " + protein );
            }
            if ( debugOnScreen ) {
                System.out.print( " pC" );
            }

            proteinCreated++;
            return protein;
        } catch ( IntactException e ) {
            if ( logger != null ) {
                logger.error( protein, e );
            }
            throw e;
        }

    }

    /**
     * Update an existing protein with data from a SPTR Entry.
     *
     * @param protein   the protein to update
     * @param sptrEntry the source entry
     * @param bioSource the BioSource to link to the Protein
     *
     * @return true is the protein is created
     *
     * @throws SPTRException
     * @throws IntactException
     */
    private boolean updateExistingProtein( Protein protein,
                                           SPTREntry sptrEntry,
                                           BioSource bioSource,
                                           boolean generateProteinShortlabelUsingBiosource )
            throws SPTRException,
                   IntactException {

        if ( !protein.getBioSource().getTaxId().equals( bioSource.getTaxId() ) ) {

            String msg = "UpdateProteins is trying to modify the BioSource of the following protein:" +
                         protein + " by " + bioSource +
                         "\nChanging the taxid of an existing protein is a forbidden operation.";

            throw new IntactException( msg );
        }


        boolean needUpdate = false;

        // get the current version of annotationUpdate and compare it to the one stored in the Uniprot Xref
        if ( needAnnotationUpdate( sptrEntry, protein ) ) {

            // get the protein info we need
            String fullName = sptrEntry.getProteinName();
            if ( fullName.length() > 250 ) {
                fullName = fullName.substring( 0, 250 );
            }

            String shortLabel = generateProteinShortLabel( sptrEntry, bioSource,
                                                           generateProteinShortlabelUsingBiosource );

            if ( !protein.getFullName().equals( fullName ) ) {
                protein.setFullName( fullName );
                logInfo( "fullname is different" );
                needUpdate = true;
            }

            if ( !protein.getShortLabel().equals( shortLabel ) ) {
                protein.setShortLabel( shortLabel );
                logInfo( "shorltabel is different" );
                needUpdate = true;
            }

            /**
             * false || false -> false
             * false || true -> true
             * true || false -> true
             * true || true -> true
             */
            needUpdate = needUpdate | updateXref( sptrEntry, protein, Factory.XREF_SGD, sgdDatabase );
            needUpdate = needUpdate | updateXref( sptrEntry, protein, Factory.XREF_GO, goDatabase );
            needUpdate = needUpdate | updateXref( sptrEntry, protein, Factory.XREF_INTERPRO, interproDatabase );
            needUpdate = needUpdate | updateXref( sptrEntry, protein, Factory.XREF_FLYBASE, flybaseDatabase );
            needUpdate = needUpdate | updateXref( sptrEntry, protein, Factory.XREF_REACTOME, reactomeDatabase );
            needUpdate = needUpdate | updateHugeXref( sptrEntry, protein );

            // update SPTR Xrefs
            needUpdate = needUpdate | updateUniprotXref4Protein( sptrEntry, protein );

            // check on aliases
            Collection aliases = getProteinAliasesFromSPTR( sptrEntry, protein );
            needUpdate = needUpdate | updateAliasCollection( protein, aliases );
        }


        // the sequence update is not optimized yet. We could use the version of the sequence in the SPTREntry to do that.

        String sequence = sptrEntry.getSequence();
        String crc64 = sptrEntry.getCRC64();

        if ( !protein.getCrc64().equals( crc64 ) ) {
            protein.setCrc64( crc64 );
            logInfo( "CRC64 is different" );
            needUpdate = true;
        }

        if ( !protein.getSequence().equals( sequence ) ) {
            protein.setSequence( helper, sequence );
            logInfo( "sequence is different" );
            needUpdate = true;
        }

        // keep that protein
        proteins.add( protein );

        if ( needUpdate == true ) {
            logInfo( "That protein needs an update" );

            // update databse
            try {
                /**
                 * If the object is proxied, the update will throw an Exception: ClassNotPersistenceCapableException
                 * So we use the IntactHelper to get the realObject.
                 */
                helper.update( IntactHelper.getRealIntactObject( protein ) );

                if ( debugOnScreen ) {
                    System.out.print( " pU" );
                }
                proteinUpdated++;
                return true;
            } catch ( IntactException ie ) {
                if ( logger != null ) {
                    logger.error( protein, ie );
                }
                throw ie;
            }
        } else {
            logInfo( "That protein was up-to-date" );
            if ( debugOnScreen ) {
                System.out.print( " p-" );
            }
            proteinUpToDate++;
        }

        return false;
    }


    /**
     * From a SPTR Entry, create in IntAct a new splice variant. <br> Note that few information are store in that
     * object, the main of it is the sequence/crc64, eventual alias and node. The rest of the data is store in the
     * master protein that we can reach by following the Xref (isoform-parent).
     *
     * @param isoform   the isoform from which is originated that splice variant
     * @param sptrEntry the source entry
     * @param bioSource the BioSource to link to the Protein
     *
     * @return true is the protein is created
     *
     * @throws SPTRException
     * @throws IntactException
     */
    private Protein createNewSpliceVariant( Isoform isoform,
                                            String isoId,
                                            Protein master,
                                            SPTREntry sptrEntry,
                                            BioSource bioSource,
                                            boolean generateProteinShortlabelUsingBiosource )
            throws SPTRException, IntactException {

        if ( master == null ) {
            if ( logger != null ) {
                logger.error( " The given master is null, EXIT " );
            }
            throw new IntactException( "The given master protein for " + isoId + " is null, abort." );
        }

        String shortLabel = isoId;

        if ( generateProteinShortlabelUsingBiosource ) {
            if ( bioSource.getShortLabel() != null && !bioSource.getShortLabel().equals( "" ) ) {
                shortLabel = shortLabel + "_" + bioSource.getShortLabel();
            } else {
                System.err.println( "WARNING: generate the shortlabel using taxid since the shortlabel doesn't exists." );
                shortLabel = shortLabel + "_" + bioSource.getTaxId();
            }
        }

        Protein spliceVariant = new ProteinImpl( myInstitution, bioSource, shortLabel.toLowerCase() );

        // get the spliceVariant info we need
        helper.create( spliceVariant );

        String fullName = sptrEntry.getProteinName();

        String sequence = null;
        String crc64 = null;
        try {
            sequence = sptrEntry.getAlternativeSequence( isoform );
            if ( sequence != null ) {
                crc64 = Crc64.getCrc64( sequence );
            }
        } catch ( FeatureException e ) {
            if ( logger != null ) {
                logger.error( "Could not get the Alternative splice variant sequence from SPTREntry.", e );
            }
            return null;
        }

        spliceVariant.setFullName( fullName );
        spliceVariant.setSequence( helper, sequence );
        spliceVariant.setCrc64( crc64 );

        // add Xref (isoform-parent), which links to the splice variant's master protein ...
        Xref isoformXref = new Xref( myInstitution,
                                     intactDatabase,
                                     master.getAc(),
                                     master.getShortLabel(),
                                     null, isoFormParentXrefQualifier );
        addNewXref( spliceVariant, isoformXref );

        // Add UNIPROT xref.
        updateUniprotXref4SpliceVariant( sptrEntry, spliceVariant, isoform );

        // create Aliases
        Collection aliases = getSpliceVariantAliasesFromSPTR( isoform, spliceVariant );
        updateAliasCollection( spliceVariant, aliases );

        // Add existing synonyms ... as Alias.
        String[] isoSynonyms = isoform.getSynonyms();
        for ( int i = 0; i < isoSynonyms.length; i++ ) {
            String isoSynonym = isoSynonyms[ i ];
            Alias alias = new Alias( myInstitution, spliceVariant, isoformSynonym, isoSynonym );
            addNewAlias( spliceVariant, alias );
        }

        // Add existing note ... as an Annotation.
        String note = isoform.getNote();
        if ( ( note != null ) && ( !note.trim().equals( "" ) ) ) {
            Annotation annotation = new Annotation( myInstitution, isoformComment );
            annotation.setAnnotationText( isoform.getNote() );
            addNewAnnotation( spliceVariant, annotation );
        }

        // update database
        try {
            // TODO bug here when running the InsertComplex (Q9NRI5, Q9NV70, Q9UKE5). could be nested transaction ...
            helper.update( spliceVariant );

            // keep that spliceVariant
            proteins.add( spliceVariant );

            logInfo( "spliceVariant updated: " + spliceVariant );
            if ( debugOnScreen ) {
                System.out.print( " svC" );
            }

            spliceVariantCreated++;
            return spliceVariant;
        } catch ( IntactException e ) {
            if ( logger != null ) {
                logger.error( spliceVariant, e );
            }
            throw e;
        }
    }

    /**
     * Update an existing splice variant with data from a SPTR Entry.
     *
     * @param isoform       the isoform from which is originated that splice variant
     * @param spliceVariant the spliceVariant to update
     * @param master        the master protein to which link that splice variant
     * @param sptrEntry     the source entry
     * @param bioSource     the BioSource to link to the Protein
     *
     * @return true is the spliceVariant is updated or up-to-date
     *
     * @throws SPTRException
     * @throws IntactException
     */
    private boolean updateExistingSpliceVariant( Isoform isoform,
                                                 String isoId,
                                                 Protein spliceVariant,
                                                 Protein master,
                                                 SPTREntry sptrEntry,
                                                 BioSource bioSource,
                                                 boolean generateProteinShortlabelUsingBiosource )
            throws SPTRException,
                   IntactException {

        if ( master == null ) {
            if ( logger != null ) {
                logger.error( " The given master is null, EXIT " );
            }
            throw new IntactException( "The given master protein for " + isoId + " is null, abort." );
        }

        if ( !spliceVariant.getBioSource().getTaxId().equals( bioSource.getTaxId() ) ) {

            String msg = "UpdateProteins is trying to modify the BioSource of the following splice variant:" +
                         spliceVariant + " by " + bioSource +
                         "\nChanging the taxid of an existing protein is a forbidden operation.";

            throw new IntactException( msg );
        }

        boolean needUpdate = false;

        if ( needAnnotationUpdate( sptrEntry, spliceVariant ) ) {

            // get the spliceVariant info we need
            String fullName = sptrEntry.getProteinName();
            String shortLabel = isoId;

            if ( generateProteinShortlabelUsingBiosource ) {
                if ( bioSource.getShortLabel() != null && !bioSource.getShortLabel().equals( "" ) ) {
                    shortLabel = shortLabel + "_" + bioSource.getShortLabel();
                } else {
                    final String msg = "WARNING: generate the shortlabel using taxid since the shortlabel doesn't exists.";
                    if ( debugOnScreen ) {
                        System.err.println( msg );
                    }
                    if ( logger != null ) {
                        logger.warn( msg );
                    }
                    shortLabel = shortLabel + "_" + bioSource.getTaxId();
                }
            }
            shortLabel = shortLabel.toLowerCase();

            if ( !spliceVariant.getFullName().equals( fullName ) ) {
                spliceVariant.setFullName( fullName );
                logInfo( "fullname is different" );
                needUpdate = true;
            }

            if ( !spliceVariant.getShortLabel().equals( shortLabel ) ) {
                spliceVariant.setShortLabel( shortLabel );
                logInfo( "shortlabel is different" );
                needUpdate = true;
            }

            // check the only single Xref (isoform-parent), which references the master spliceVariant ...
            // TODO count only the new reference of XREF (isoform-parent).
            Collection xrefs = spliceVariant.getXrefs();
            Xref isoformXref = null;
            boolean found = false;
            for ( Iterator iterator = xrefs.iterator(); iterator.hasNext() && !found; ) {
                isoformXref = (Xref) iterator.next();
                if ( isoformXref.getCvXrefQualifier().equals( isoFormParentXrefQualifier ) ) {
                    found = true; // exit, isoformXref is still refering to it.
                }
            }

            if ( !found ) {
                // error ... but create it.
                isoformXref = new Xref( myInstitution,
                                        intactDatabase,
                                        master.getAc(),
                                        master.getShortLabel().toLowerCase(),
                                        null,
                                        isoFormParentXrefQualifier );
                needUpdate = needUpdate | addNewXref( spliceVariant, isoformXref );

            } else {

                // check for update
                if ( !isoformXref.getPrimaryId().equals( master.getAc() ) ) {
                    if ( logger != null ) {
                        logger.error( "The master protein has changed, was " + isoformXref.getPrimaryId() + " and now is" + master.getAc() );
                    }
                    isoformXref.setPrimaryId( master.getAc() );
                    logInfo( "AC of Xref[isoFormParentXrefQualifier] was different" );

                    needUpdate = true;
                }

                String shortlabel = master.getShortLabel().toLowerCase();
                if ( !isoformXref.getSecondaryId().equals( shortlabel ) ) {
                    isoformXref.setSecondaryId( shortlabel );
                    logInfo( "secondaryId of Xref[isoFormParentXrefQualifier] was different" );
                    helper.update( isoformXref );
                    needUpdate = true;
                }
            }

            // update SPTR Xrefs.
            needUpdate = needUpdate | updateUniprotXref4SpliceVariant( sptrEntry, spliceVariant, isoform );

            // check for aliases ... that we could update as Alias.
            Collection aliases = getSpliceVariantAliasesFromSPTR( isoform, spliceVariant );
            needUpdate = needUpdate | updateAliasCollection( spliceVariant, aliases );

            // check for the note ... that we could update as an Annotation.
            String note = isoform.getNote();
            if ( ( note != null ) && ( !note.trim().equals( "" ) ) ) {
                Collection annotations = spliceVariant.getAnnotations();
                Annotation annotation = null;
                for ( Iterator iterator = annotations.iterator(); iterator.hasNext() && annotation == null; ) {
                    Annotation _annotation = (Annotation) iterator.next();
                    if ( isoformComment.equals( _annotation.getCvTopic() ) ) {
                        annotation = _annotation;
                    }
                }

                if ( annotation == null ) {
                    // create it
                    annotation = new Annotation( myInstitution, isoformComment );
                    annotation.setAnnotationText( note );
                    helper.create( annotation );
                    spliceVariant.addAnnotation( annotation );
                    logInfo( "CREATE: " + annotation );
                    needUpdate = true;
                } else {
                    // try to update it.
                    if ( !annotation.getAnnotationText().equals( note ) ) {
                        annotation.setAnnotationText( note );
                        helper.update( annotation );
                        logInfo( "UPDATE" + annotation );
                        needUpdate = true;
                    }
                }
            }
        } // annotation update.


        // sequence update.
        String sequence = null;
        String crc64 = null;
        try {
            sequence = sptrEntry.getAlternativeSequence( isoform );
            if ( sequence != null ) {
                crc64 = Crc64.getCrc64( sequence );
            }
        } catch ( FeatureException e ) {
            if ( logger != null ) {
                logger.error( "Could not get the Alternative splice variant sequence from SPTREntry.", e );
            }
            return false;
        }

        /**
         * The CRC64 is updated only if the sequence is so.
         */
        String _sequence = spliceVariant.getSequence();
        if ( _sequence == null ) {
            if ( sequence != null && ( false == "".equals( sequence ) ) ) {
                spliceVariant.setSequence( helper, sequence );
                spliceVariant.setCrc64( crc64 );
                needUpdate = true;
            }
        } else if ( !spliceVariant.getSequence().equals( sequence ) ) {
            spliceVariant.setSequence( helper, sequence );
            spliceVariant.setCrc64( crc64 );
            needUpdate = true;
        }

        // keep that spliceVariant
        proteins.add( spliceVariant );

        if ( needUpdate == true ) {
            // update databse
            try {
                /**
                 * If the object is proxied, the update will throw an Exception: ClassNotPersistenceCapableException
                 * So we use the IntactHelper to get the realObject.
                 */
                helper.update( IntactHelper.getRealIntactObject( spliceVariant ) );

                if ( debugOnScreen ) {
                    System.out.print( " svU" );
                }
                spliceVariantUpdated++;
                return true;
            } catch ( IntactException ie ) {
                if ( logger != null ) {
                    logger.error( spliceVariant, ie );
                }
                throw ie;
            }
        } else {
            logInfo( "That splice variant was up-to-date" );
            if ( debugOnScreen ) {
                System.out.print( " sv-" );
            }
            spliceVariantUpToDate++;
        }

        return false;
    }

    /**
     * Read the Annotation release version from the SPTREntry and compare it to the one store in the Protein UniProt
     * Xref. If they are different or absent, we answer that an update is needed.
     *
     * @param sptrEntry the SPTREntry from which we read the annotation version.
     * @param protein   the protein from which we get the IntAct annotation version.
     *
     * @return true if the intact protein is outdated, false otherwise.
     *
     * @throws SPTRException if something goes wrong when reading the LastAnnotationUpdateRelease.
     */
    private boolean needAnnotationUpdate( SPTREntry sptrEntry, Protein protein ) throws SPTRException {

        String sptrRelease = getSPTREntryReleaseVersion( sptrEntry );

        String intactRelease = null;
        // exit the loop as soon as we find a release in IntAct.
        for ( Iterator iterator = protein.getXrefs().iterator(); iterator.hasNext() && intactRelease == null; ) {
            Xref xref = (Xref) iterator.next();

            if ( uniprotDatabase.equals( xref.getCvDatabase() ) ) {

                // read the version in the first uniprot Xref and use it to compare to SPTR's one.
                intactRelease = xref.getDbRelease();
            }
        }

        boolean needupdate = true;
        if ( intactRelease != null ) {

            if ( intactRelease.equals( sptrRelease ) ) {
                needupdate = false;
            }
        }

        logInfo( "After comparison of the LastAnnotationUpdateRelease in the Entry and in IntAct, update needed = " + needupdate );

        return needupdate;
    }

    public Collection insertSPTrProteins( String proteinAc ) {

        if ( proteinAc == null || proteinAc.trim().equals( "" ) ) {
            throw new IllegalArgumentException( "The protein AC MUST not be null or empty." );
        }

        String url = getUrl( proteinAc );
        int i = insertSPTrProteinsFromURL( url, null, true );
        if ( debugOnScreen ) {
            System.out.println( i + " proteins created/updated." );
        }

        return proteins;
    }

    /**
     * Filter a set of Proteins based on their biosource's taxid.
     *
     * @param c     the collection to filter out.
     * @param taxid the taxid on which we base the filter. ie. we'll keep all proteins having that taxid
     *
     * @return a set of proteins where all have the given taxid
     */
    private Collection filterOnTaxid( Collection c, String taxid ) {

        Collection filteredProtein = null;

        for ( Iterator iterator = c.iterator(); iterator.hasNext(); ) {
            Protein protein = (Protein) iterator.next();

            if ( protein.getBioSource().getTaxId().equals( taxid ) ) { // NullPointerException

                if ( filteredProtein == null ) {
                    filteredProtein = new ArrayList( c.size() ); // set to its maximal size
                }

                filteredProtein.add( protein );
            }
        }

        if ( filteredProtein == null ) {
            filteredProtein = Collections.EMPTY_LIST;
        }

        return filteredProtein;
    }

    public Collection insertSPTrProteins( String proteinAc, String taxId, boolean update ) {

        if ( proteinAc == null || proteinAc.trim().equals( "" ) ) {
            throw new IllegalArgumentException( "The protein AC MUST not be null or empty." );
        }

        String url = getUrl( proteinAc );
        int i = insertSPTrProteinsFromURL( url, taxId, update );
        if ( debugOnScreen ) {
            System.out.println( i + " proteins created/updated." );
        }

        // TODO: could be nice to have a method like getProteins() and getProtein( String taxid )
        // to allow multiple post call to insert.
        if ( taxId != null ) {
            // filter out using the given taxid before to return the collection.
            Collection c = filterOnTaxid( proteins, taxId );
            if ( logger != null && logger.isInfoEnabled() ) {
                logger.info( "Protein selected after filtering (" + c.size() + "):" );
                for ( Iterator iterator = c.iterator(); iterator.hasNext(); ) {
                    Protein protein = (Protein) iterator.next();
                    logger.info( "\t" + protein.getShortLabel() );
                }
            }
            return c;
        }

        if ( logger != null && logger.isInfoEnabled() ) {
            logger.info( "Protein created/updated (" + proteins.size() + "):" );
            for ( Iterator iterator = proteins.iterator(); iterator.hasNext(); ) {
                Protein protein = (Protein) iterator.next();
                logger.info( "\t" + protein.getShortLabel() );
            }
        }

        return proteins;
    }

    /**
     * Creates a simple Protein object for entries which are not in SPTR. The Protein will more or less only contain the
     * crossreference to the source database.
     *
     * @param anAc      The primary identifier of the protein in the external database.
     * @param aDatabase The database in which the protein is listed.
     * @param aTaxId    The tax id the protein should have
     *
     * @return the protein created or retrieved from the IntAct database
     */
    public Protein insertSimpleProtein( String anAc, CvDatabase aDatabase, String aTaxId ) throws IntactException {

        // Search for the protein or create it
        Collection newProteins = helper.getObjectsByXref( Protein.class, anAc );

        if ( localTransactionControl ) {
            helper.startTransaction( BusinessConstants.OBJECT_TX );
        }

        // Get or create valid biosource from taxid
        BioSource validBioSource = bioSourceFactory.getValidBioSource( aTaxId );

        /* If there were obsolete taxids in the db, they should now be updated.
         * So we will only compare valid biosources.
         */

        // Filter for exactly one entry with appropriate taxId
        Protein targetProtein = null;
        for ( Iterator i = newProteins.iterator(); i.hasNext(); ) {
            Protein tmpProtein = (Protein) i.next();
            if ( tmpProtein.getBioSource().getTaxId().equals( validBioSource.getTaxId() ) ) {
                if ( null == targetProtein ) {
                    targetProtein = tmpProtein;
                } else {
                    throw new IntactException( "More than one Protein with AC "
                                               + anAc
                                               + " and taxid "
                                               + aTaxId
                                               + " found." );
                }
            }
        }

        if ( null == targetProtein ) {
            // No appropriate protein found, create it.

            // Create new Protein
            targetProtein = new ProteinImpl( myInstitution, validBioSource, anAc );
            helper.create( targetProtein );

            // Create new Xref if a DB has been given
            if ( null != aDatabase ) {
                Xref newXref = new Xref( myInstitution, aDatabase, anAc, null, null, null );
                newXref.setOwner( myInstitution );
                newXref.setCvDatabase( aDatabase );
                newXref.setPrimaryId( anAc );
                targetProtein.addXref( newXref );
                helper.create( newXref );
            }
        }

        if ( localTransactionControl ) {
            helper.finishTransaction();
        }

        return targetProtein;
    }

    public int insertSPTrProteinsFromURL( String sourceUrl, String taxid, boolean update ) {

        Collection result = null;

        try {
            if ( logger != null ) {
                logInfo( "update from URL: " + sourceUrl );
            }
            if ( debugOnScreen ) {
                System.out.println( "update from URL: " + sourceUrl );
            }

            if ( sourceUrl == null ) {
                return 0;
            }

            URL url = new URL( sourceUrl );
            InputStream is = url.openStream();

            result = insertSPTrProteins( is, taxid, update );

            is.close();

        } catch ( IOException e ) {
            if ( debugOnScreen ) {
                e.printStackTrace();
            }
            if ( logger != null ) {
                logger.error( "URL error: " + sourceUrl, e );
                logger.error( "Please provide a valid URL" );
            }
        }

        return ( result == null ? 0 : result.size() );
    }

    /**
     * Process one to many entries and insert/update the database.
     *
     * @param inputStream where we read the entries from.
     * @param update      true to allow update, otherwise false.
     *
     * @return a collection of updated created proteins and Splice variant (Protein object).
     */
    private Collection insertSPTrProteins( InputStream inputStream, boolean update ) {

        if ( inputStream == null ) {
            if ( logger != null ) {
                logger.error( "You are trying to update using a null InputStream" );
            }
            return null;
        }

        /**
         * Has to be called in order to have the statistics properly initialized
         * as well as to keep track of all updated/created proteins.
         */
        reset();

        try {
            // parse it with YASP
            if ( debugOnScreen ) {
                System.out.print( "Parsing..." );
                System.out.flush();
            }

            entryIterator = YASP.parseAll( inputStream );

            if ( debugOnScreen ) {
                System.out.println( "done" );
            }

            if ( localEntryCacheEnabled ) {
                try {
                    if ( localEntryCache == null ) {
                        localEntryCache = new BufferedWriter( new FileWriter( localEntryCacheFilename, true ) );
                        if ( displayLocalEntryCacheMessage ) {
                            System.out.println( "Local Entry cache created: " + localEntryCacheFilename );
                            displayLocalEntryCacheMessage = false; // display it only once
                        }
                    }
                } catch ( IOException e ) {
                    System.err.println( "Coud not create the local entry cache file (" + entryErrorFilename + ")." );
                    e.printStackTrace();
                }
            }

            /**
             * C A U T I O N
             * -------------
             *  The YASP Iterator has to be used carrefully. It doesn't behave like an java.util.Iterator.
             * .next() method gives you the current element
             * .hasNext() loads the next elements and says you if there was one.
             * So, calling twice .hasNext() without processing in between would
             * make you miss an element.
             */
            while ( entryIterator.hasNext() ) {

                entryCount++;

                // Check if there is any exception remaining in the Entry before to use it
                if ( entryIterator.hadException() ) {

                    Exception originalException = entryIterator.getException().getOriginalException();
                    parsingExceptions.put( new Integer( entryCount ), originalException );

                    if ( originalException != null ) {
                        if ( debugOnScreen ) {
                            originalException.printStackTrace();
                            entryIterator.getException().printStackTrace();
                        }
                    } else {
                        if ( logger != null ) {
                            logger.error( "Parsing error while processing the entry " + entryCount,
                                          entryIterator.getException() );
                        }
                        if ( debugOnScreen ) {
                            entryIterator.getException().printStackTrace();
                        }
                    }

                    // wrong entries are NOT processed any further
                    writeEntry2file( entryIterator );
                    entrySkipped++;
                    continue;
                }

                // get the SPTREntry
                SPTREntry sptrEntry = (SPTREntry) entryIterator.next();

                // give the user the option to cache all entries in a text file.
                if ( localEntryCacheEnabled ) {
                    try {
                        if ( localEntryCache != null ) {
                            final String entry = entryIterator.getOriginal();
                            localEntryCache.write( entry );
                        }
                    } catch ( IOException e ) {
                        System.err.println( "Could not cache localy the current Entry." );
                        e.printStackTrace();
                    }
                }

                if ( sptrEntry == null ) {
                    if ( logger != null ) {
                        logger.error( "\n\nSPTR entry is NULL ... skip it" );
                    }

                    entrySkipped++;
                    continue;
                }

                if ( debugOnScreen ) {
                    System.out.print( "(" + sptrEntry.getID() + ":" );
                }

                createProteinFromSPTrEntry( sptrEntry, update );

                if ( debugOnScreen ) {
                    System.out.println( ")" );
                }

                // Display some statistics every 500 entries processed.
                if ( entryCount % 500 == 0 ) {
                    printStats();
                }
            }

        } catch ( YASPException e ) {
            e.printStackTrace();
            if ( logger != null ) {
                logger.error( e.getOriginalException() );
            }
        } catch ( SPTRException e ) {
            e.printStackTrace();
            if ( logger != null ) {
                logger.error( "Error while processing an SPTREntry", e );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            if ( logger != null ) {
                logger.error( "Error while parsing an SPTREntry", e );
            }
        }

        if ( localEntryCacheEnabled ) {
            try {
                if ( localEntryCache != null ) {
                    localEntryCache.close();
                    localEntryCache = null;
                }
            } catch ( IOException e ) {
                System.err.println( "Could not close the local cache Entry file." );
                e.printStackTrace();
            }
        }

        closeFile(); // try to close the bad entries repository if it exists

//        printStats();

        return proteins;
    }

    /**
     * Create all the proteins related to that set of entry. It apply the taxif filter and return the remaining
     * proteins.
     *
     * @param inputStream the set of entries.
     * @param taxid       the taxid of the protein we will return
     * @param update      true if we update existing proteins
     *
     * @return a set of proteins and splice variants (as Protein object)
     */
    public Collection insertSPTrProteins( InputStream inputStream, String taxid, boolean update ) {

        // check the taxid parameter validity
        try {
            if ( taxid != null ) {
                String newTaxid = bioSourceFactory.getUpToDateTaxid( taxid );
                if ( newTaxid == null ) {
                    if ( logger != null ) {
                        logger.error( "Could not find an up-to-date taxid for " + taxid + " abort update procedure." );
                    }
                    return null;
                }
            }
        } catch ( IntactException ie ) {
            String msg = "Could not find an up-to-date taxid for " + taxid + " abort update procedure.";
            if ( logger != null ) {
                logger.error( msg, ie );
            }
            return null;
        }

        insertSPTrProteins( inputStream, update ); // updates the collection: proteins

        if ( taxid != null ) {
            // filter out using the given taxid before to return the collection.
            Collection c = filterOnTaxid( proteins, taxid );
            if ( logger != null && logger.isInfoEnabled() ) {
                logger.info( "Protein selected after filtering (" + c.size() + "):" );
                for ( Iterator iterator = c.iterator(); iterator.hasNext(); ) {
                    Protein protein = (Protein) iterator.next();
                    logger.info( "\t" + protein.getShortLabel() );
                }
            }
            return c;
        }

        return proteins;
    }

    private void printStats() {
        // in log file
        if ( logger != null ) {
            logger.info( "Protein created:    " + getProteinCreatedCount() );
            logger.info( "Protein updated:    " + getProteinUpdatedCount() );
            logger.info( "Protein up-to-date: " + getProteinUpToDateCount() );
            logger.info( "Protein skipped:    " + getProteinSkippedCount() );
            logger.info( "Splice variant created:    " + getSpliceVariantCreatedCount() );
            logger.info( "Splice variant updated:    " + getSpliceVariantUpdatedCount() );
            logger.info( "Splice variant up-to-date: " + getSpliceVariantUpToDateCount() );
            logger.info( "Splice variant skipped:    " + getSpliceVariantSkippedCount() );
            logger.info( "Entry processed:    " + getEntryProcessededCount() );
            logger.info( "Entry skipped:      " + getEntrySkippedCount() );
        }

        // on STDOUT
        if ( debugOnScreen ) {
            System.out.println( "Protein created:    " + getProteinCreatedCount() );
            System.out.println( "Protein updated:    " + getProteinUpdatedCount() );
            System.out.println( "Protein up-to-date: " + getProteinUpToDateCount() );
            System.out.println( "Protein skipped:    " + getProteinSkippedCount() );
            System.out.println( "Splice variant created:    " + getSpliceVariantCreatedCount() );
            System.out.println( "Splice variant updated:    " + getSpliceVariantUpdatedCount() );
            System.out.println( "Splice variant up-to-date: " + getSpliceVariantUpToDateCount() );
            System.out.println( "Splice variant skipped:    " + getSpliceVariantSkippedCount() );
            System.out.println( "Entry processed:    " + getEntryProcessededCount() );
            System.out.println( "Entry skipped:      " + getEntrySkippedCount() );
        }
    }

    /**
     * Write the content of an Entry in a file for later on processing
     */
    private void writeEntry2file( EntryIterator entryIterator ) {

        if ( file == null ) {
            // make a generic output byte stream
            try {
                entryErrorFilename = ENTRY_OUTPUT_FILE + "-" + TIME;
                file = new FileOutputStream( entryErrorFilename );
                // attach BufferedOutputStream to buffer it
                buffer = new BufferedOutputStream( file, 4096 );

            } catch ( FileNotFoundException e ) {
                if ( logger != null ) {
                    logger.error( "Could not write the current entry to the temp file: " + entryErrorFilename, e );
                }
                return;
            }
        }

        // write the entry in the file
        try {
            String entry = entryIterator.getOriginal();
            if ( entry != null ) {
                buffer.write( entry.getBytes() );
                if ( logger != null ) {
                    logger.error( "\nEntry written in the file" );
                }
            } else {
                if ( logger != null ) {
                    logger.error( "Couldn't write the entry in the file" );
                }
            }
        } catch ( IOException e ) {
            if ( logger != null ) {
                logger.error( "An error occur when trying to save an entry which cause a processing problem", e );
            }
        }
    }

    private void closeFile() {

        if ( buffer != null ) {
            try {
                buffer.close();
            } catch ( IOException e ) {
                if ( logger != null ) {
                    logger.error( "Error when trying to close faulty entry file", e );
                }
            }
        }

        if ( file != null ) {
            try {
                file.close();
            } catch ( IOException e ) {
                if ( logger != null ) {
                    logger.error( "Error when trying to close faulty entry file", e );
                }
            }
        }

    }

    public String getErrorFileName() {
        return entryErrorFilename;
    }

    public void enableLocalEntryCache() {
        localEntryCacheEnabled = true;
    }

    public void disableLocalEntryCache() {
        localEntryCacheEnabled = false;
    }

    public boolean isLocalEntryCacheEnabled() {
        return localEntryCacheEnabled;
    }

    public String getLocalEntryCacheFileName() {
        return localEntryCacheFilename;
    }

    /**
     * If true, each protein is updated in a distinct transaction. If localTransactionControl is false, no local
     * transactions are initiated, control is left with the calling class. This can be used e.g. to have transctions
     * span the insertion of all proteins of an entire complex.
     *
     * @return current value of localTransactionControl
     */
    public boolean isLocalTransactionControl() {
        return localTransactionControl;
    }

    /**
     * If true, each protein is updated in a distinct transaction. If localTransactionControl is false, no local
     * transactions are initiated, control is left with the calling class. This can be used e.g. to have transctions
     * span the insertion of all proteins of an entire complex.
     *
     * @param aLocalTransactionControl New value for localTransactionControl
     */
    public void setLocalTransactionControl( boolean aLocalTransactionControl ) {
        localTransactionControl = aLocalTransactionControl;
    }

    /**
     * Get the uniprot primary ID from Protein and Splice variant.
     *
     * @param protein the Protein for which we want the uniprot ID.
     *
     * @return the uniprot ID as a String or null if none is found (should not occur)
     */
    public final String getUniprotID( final Protein protein ) {

        String uniprot = null;

        Collection xrefs = protein.getXrefs();
        boolean found = false;
        for ( Iterator iterator = xrefs.iterator(); iterator.hasNext() && !found; ) {
            Xref xref = (Xref) iterator.next();

            if ( uniprotDatabase.equals( xref.getCvDatabase() ) &&
                 identityXrefQualifier.equals( xref.getCvXrefQualifier() ) ) {
                uniprot = xref.getPrimaryId();
                found = true;
            }
        }

        return uniprot;
    }

    public int updateAllProteins( Collection proteins ) throws IntactException {

        int count = 0;

        if ( proteins == null ) {
            // retreive all proteins.
            proteins = helper.search( Protein.class.getName(), "ac", null );
        }


        HashSet uniprotIds = new HashSet( 4096 );

        int proteinCount = proteins.size();
        NumberFormat formatter = new DecimalFormat( ".00" );

        System.out.println( proteinCount + " protein(s) to be updated." );
        int proteinProcessed = 0;
        for ( Iterator iterator = proteins.iterator(); iterator.hasNext(); ) {
            Protein protein = (Protein) iterator.next();
            iterator.remove(); // don't keep it in the collection, free memory.

            // keep track of all ID we already updated.
            String uniprotID = getUniprotID( protein );

            if ( uniprotID != null ) {
                if ( uniprotIds.contains( uniprotID ) ) {

                    System.out.println( "Skip " + uniprotID + " was already processed." );

                } else {

                    uniprotIds.add( uniprotID );
                    Collection updatedProteins = insertSPTrProteins( uniprotID );
                    count += updatedProteins.size();

                    // update uniprot IDs list (Protein + eventual splice variants)
                    for ( Iterator iterator1 = updatedProteins.iterator(); iterator1.hasNext(); ) {
                        protein = (Protein) iterator1.next();
                        uniprotID = getUniprotID( protein );
                        if ( uniprotID != null ) {
                            uniprotIds.contains( uniprotID );
                        }
                    }
                }
            } else {
                System.out.println( "Protein " + protein.getAc() + " doesn't have a Uniprot ID, skip it." );
            }

            proteinProcessed++;

            String percent = formatter.format( ( (float) proteinProcessed / (float) proteinCount ) * 100 );
            System.out.println( proteinProcessed + " protein processed out of " + proteinCount + "( " + percent + "%)" );
        }

        return count;
    }

    /**
     * D E M O
     * <p/>
     * Could be use for loading from a .txl file ./scripts/javaRun.sh UpdateProteins file:///homes/user/mySPTRfile.txl
     */
    public static void main( String[] args ) throws Exception {

        IntactHelper helper = null;

        try {
            String url = null;

            if ( args.length >= 1 ) {
                url = args[ 0 ];
            } else {
                System.out.println( "Usage: javaRun.sh UpdateProteins <URL>|<-all>" );
                System.exit( 1 );
            }

            try {
                helper = new IntactHelper();
                System.out.println( "Helper created (User: " + helper.getDbUserName() + " " +
                                    "Database: " + helper.getDbName() + ")" );

            } catch ( IntactException e ) {
                System.out.println( "Root cause: " + e.getRootCause() );
                e.printStackTrace();
                System.exit( 1 );
            }


            UpdateProteinsI update = new UpdateProteins( helper );
            Chrono chrono = new Chrono();
            chrono.start();

            update.setDebugOnScreen( true );

            int nb = 0;
            if ( url.equals( "-all" ) ) {
                System.out.println( "Updating all proteins..." );
                nb = update.updateAllProteins( null );
            } else {
                nb = update.insertSPTrProteinsFromURL( url, null, true );
            }

            chrono.stop();
            System.out.println( "Time elapsed: " + chrono );
            System.out.println( "Entries error in : " + update.getErrorFileName() );

            System.out.println( nb + " protein updated/created" );

        } catch ( OutOfMemoryError aome ) {

            aome.printStackTrace();

            System.err.println( "" );
            System.err.println( "UpdateProteins ran out of memory." );
            System.err.println( "Please run it again and change the JVM configuration." );
            System.err.println( "Here are some the options: http://java.sun.com/docs/hotspot/VMOptions.html" );
            System.err.println( "Hint: You can use -Xms -Xmx to specify respectively the minimum and maximum" );
            System.err.println( "      amount of memory that the JVM is allowed to allocate." );
            System.err.println( "      eg. java -Xms128m -Xmx512m <className>" );
            System.err.println( "      you can set it up in scripts/javaRun.sh" );

            System.exit( 1 );

        } catch ( Exception e ) {

            e.printStackTrace();

            Throwable t = e;
            while ( t.getCause() != null ) {

                t = e.getCause();

                System.err.println( "" );
                System.err.println( "================== ROOT CAUSE ==========================" );
                System.err.println( "" );

                t.printStackTrace( System.err );
            }

            System.exit( 1 );
        } finally {
            if ( helper != null ) {
                helper.closeStore();
            }
        }

        System.exit( 0 );
    }
}