package uk.ac.ebi.intact.bridges.ontology_manager.impl;

import psidev.psi.tools.ontology_manager.impl.OntologyTermImpl;
import uk.ac.ebi.intact.bridges.ontology_manager.TermAnnotation;
import uk.ac.ebi.intact.bridges.ontology_manager.TermDbXref;
import uk.ac.ebi.intact.bridges.ontology_manager.interfaces.IntactOntologyTermI;
import uk.ac.ebi.ols.model.interfaces.Annotation;
import uk.ac.ebi.ols.model.interfaces.DbXref;
import uk.ac.ebi.ols.model.interfaces.Term;

import java.util.*;

/**
 * Abstract class fo intact ontology term
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public abstract class AbstractIntactOntologyTerm extends OntologyTermImpl implements IntactOntologyTermI {
    protected static final String PMID = "PMID";

    protected static final String PUBMED = "pubmed";
    protected static final String PUBMED_MI_REF = "MI:0446";

    protected static final String PRIMARY_REFERENCE = "primary-reference";
    protected static final String PRIMARY_REFERENCE_MI_REF = "MI:0358";

    protected static final String SEE_ALSO = "see-also";
    protected static final String SEE_ALSO_MI_REF = "MI:0361";

    protected static final String IDENTITY = "identity";
    protected static final String IDENTITY_MI_REF = "MI:0356";

    protected static final String RESID = "resid";
    protected static final String RESID_MI_REF = "MI:0248";

    protected static final int MAX_SHORT_LABEL_LEN = 256;
    protected static final String LINE_BREAK = "\n";

    protected static final String OBSOLETE = "obsolete";
    protected static final String OBSOLETE_MI_REF = "MI:0431";

    protected static final String URL = "url";
    protected static final String URL_MI_REF = "MI:0614";

    protected static final String METHOD_REFERENCE = "method reference";
    protected static final String METHOD_REFERENCE_MI_REF = "MI:0357";

    protected static final String OBSOLETE_DEF = "OBSOLETE";
    protected static final String DEFINITION_KEY = "definition";
    protected static final String COMMENT_KEY = "comment";
    protected static final String EXACT_KEY = "exact";
    protected static final String EXACT_SYNONYM_KEY = "exact-synonym";
    protected static final String XREF_DEFINITION_KEY = "xref_definition";
    protected static final String META_DATA_SEPARATOR = "_";
    protected static final String META_XREF_SEPARATOR = ":";

    protected static final int XREF_TYPE = 3;
    protected static final int XREF_DEFINITION_TYPE = 2;

    protected String shortLabel;
    protected String fullName;
    protected String definition;
    protected String url;
    protected String obsoleteMessage;
    protected String searchUrl;
    protected String xrefValidationRegexp;
    protected Set<String> comments = new HashSet<String>();
    protected Set<TermDbXref> dbXrefs = new HashSet<TermDbXref>();
    protected Set<TermAnnotation> annotations = new HashSet<TermAnnotation>();
    protected Set<String> aliases = new HashSet<String>();

    public AbstractIntactOntologyTerm(String acc) {
        super(acc);
    }

    public AbstractIntactOntologyTerm(String acc, String name) {
        super(acc, name);
    }

    @Override
    public void loadTermFrom(Term term) {

        // full name
        this.fullName = term.getName();

        // load synonyms (alias and shortlabel)
        processSynonyms(term);

        // set shortlabel in case it was not set with synonyms
        processShortLabel(term);

        // load db xrefs
        processXrefs(term);

        // load definition, url, obsolete message
        processDefinition(term);

        // load annotations
        processAnnotations(term);
    }

    @Override
    public void loadSynonymsFrom(Map metadata, boolean isObsolete) {

        for (Object key : metadata.keySet()){
            String keyName = (String) key;

            // definition
            if (DEFINITION_KEY.equalsIgnoreCase(keyName)){
                String description = (String) metadata.get(keyName);

                boolean hasObsoleteAnnotation = processDefinition(description);

                if (!hasObsoleteAnnotation && isObsolete){
                    TermAnnotation obsolete = new TermAnnotation(OBSOLETE, OBSOLETE_MI_REF, null);
                    this.annotations.add(obsolete);
                }
            }
            // comment
            else if (COMMENT_KEY.equalsIgnoreCase(keyName) || keyName.startsWith(COMMENT_KEY + META_DATA_SEPARATOR)){
                String comment = (String) metadata.get(keyName);
                this.comments.add(comment);
            }
            else {
                String synonym = (String) metadata.get(keyName);
                processSynonym(keyName, synonym);
            }
        }
    }

    @Override
    public void loadXrefsFrom(Map xrefs) {
        Set<String> resIdRefs = new HashSet<String>(xrefs.size());

        for (Object key : xrefs.keySet()){
            String keyName = (String) key;

            // xref definitions
            if (XREF_DEFINITION_KEY.equalsIgnoreCase(keyName) || keyName.startsWith(XREF_DEFINITION_KEY)) {
                String xref = (String) xrefs.get(keyName);

                if (xref.contains(META_XREF_SEPARATOR)){
                    String [] xrefDef = xref.split(META_XREF_SEPARATOR);
                    String database = null;
                    String accession = null;
                    String pubmedPrimary = null;

                    if (xrefDef.length == 2){
                        database = xrefDef[0];
                        accession = xrefDef[1].trim();
                    }
                    else if (xrefDef.length > 2){
                        database = xrefDef[0];
                        accession = xref.substring(database.length()).trim();
                    }

                    if (database != null && accession != null){
                        processXrefDefinition(xref, database, accession, resIdRefs, pubmedPrimary);

                        if (resIdRefs.size() == 1){
                            String residXref = resIdRefs.iterator().next();

                            TermDbXref residIdentity = new TermDbXref(RESID, RESID_MI_REF, residXref, IDENTITY, IDENTITY_MI_REF);
                            this.dbXrefs.add(residIdentity);
                        }
                        else if (resIdRefs.size() > 1){
                            for (String ref : resIdRefs){
                                TermDbXref resXref = new TermDbXref(RESID, RESID_MI_REF, ref, SEE_ALSO, SEE_ALSO_MI_REF);
                                this.dbXrefs.add(resXref);
                            }
                        }
                    }
                }
            }
            else {
                String accession = (String) xrefs.get(keyName);
                processXref(keyName, accession);
            }
        }
    }

    protected abstract void processXref(String db, String accession);

    protected abstract void processXrefDefinition(String xref, String database, String accession, Set<String> resIdRefs, String pubmedPrimary);

    protected abstract void processSynonyms(Term term);

    protected abstract void processSynonym(String synonymName, String synonym);

    protected void processShortLabel(Term term) {
        if (shortLabel == null){
            if ( term.getName() != null && term.getName().length() <= MAX_SHORT_LABEL_LEN ) {
                this.shortLabel = term.getName();
            } else if ( term.getName() != null && term.getName().length() > MAX_SHORT_LABEL_LEN ) {
                this.shortLabel = term.getName().substring( 0, MAX_SHORT_LABEL_LEN );
            }
        }
    }

    protected void processXrefs(Term term) {
        Collection<DbXref> dbXrefs = term.getXrefs();
        Set<String> resIdRefs = new HashSet<String>(dbXrefs.size());
        String pubmedPrimary = null;
        for (DbXref xref : dbXrefs){

            if (xref.getXrefType() == XREF_TYPE){
                processXref(xref.getDbName(), xref.getAccession());
            }
            else {
                processXrefDefinition(xref.toString(), xref.getDbName(), xref.getAccession(), resIdRefs, pubmedPrimary);
            }
        }

        if (resIdRefs.size() == 1){
            String residXref = resIdRefs.iterator().next();

            TermDbXref residIdentity = new TermDbXref(RESID, RESID_MI_REF, residXref, IDENTITY, IDENTITY_MI_REF);
            this.dbXrefs.add(residIdentity);
        }
        else if (resIdRefs.size() > 1){
            for (String ref : resIdRefs){
                TermDbXref resXref = new TermDbXref(RESID, RESID_MI_REF, ref, SEE_ALSO, SEE_ALSO_MI_REF);
                this.dbXrefs.add(resXref);
            }
        }
    }

    protected void processDefinition(Term term) {
        boolean hasObsoleteAnnotation = false;

        String definition = term.getDefinition();

        hasObsoleteAnnotation = processDefinition(definition);

        if (!hasObsoleteAnnotation && term.isObsolete()){
            TermAnnotation obsolete = new TermAnnotation(OBSOLETE, OBSOLETE_MI_REF, null);
            this.annotations.add(obsolete);
        }
    }

    protected boolean processDefinition(String definition) {
        boolean hasObsolete = false;
        if ( definition.contains( LINE_BREAK ) ) {
            String[] defArray = definition.split( LINE_BREAK );

            String otherInfoString = null;

            if ( defArray.length == 2 ) {
                this.definition = defArray[0];
                otherInfoString = defArray[1];
                hasObsolete = processOtherInfoInDescription(definition, otherInfoString);
            } else if ( defArray.length > 2 ) {
                this.definition = defArray[0];

                for (int i = 1; i < defArray.length; i++){
                    otherInfoString = defArray[i];
                    hasObsolete = processOtherInfoInDescription(definition, otherInfoString);
                }
            }
        }
        else {
            this.definition = definition;
        }
        return hasObsolete;
    }

    protected abstract boolean processOtherInfoInDescription(String definition, String otherInfoString);

    protected void processAnnotations(Term term) {
        Collection<Annotation> annotations = term.getAnnotations();

        for (Annotation annot : annotations){
            // only one comment with type null
            if (annot.getAnnotationType() == null){
                this.comments.add(annot.getAnnotationStringValue());
            }
        }
    }

    @Override
    public String getShortLabel() {
        return this.shortLabel;
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public Set<TermDbXref> getDbXrefs() {
        return this.dbXrefs;
    }

    @Override
    public Set<TermAnnotation> getAnnotations() {
        return this.annotations;
    }

    @Override
    public String getDefinition() {
        return this.definition;
    }

    @Override
    public String getObsoleteMessage() {
        return this.obsoleteMessage;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public String getSearchUrl() {
        return this.searchUrl;
    }

    @Override
    public String getXrefValidationRegexp() {
        return this.xrefValidationRegexp;
    }

    @Override
    public Set<String> getComments() {
        return this.comments;
    }

    @Override
    public Set<String> getAliases() {
        return this.aliases;
    }
}
