
/**
 * ParseSPTR.java
 *
 * This is the main work horse to parse the SPTR data used by
 * UpdateProteins.java. 
 *
 * All parsed data are output as a hashmap by a method 
 * parseSPTR() These data can be used by any user for any
 * purpose, using ID, AC, GN, DE, SEQ, CRC64, ... as hash keys. 
 * 
 *
 * Created: Wed Oct 23 15:11:09 2002
 *
 * @author <a href="mailto: danwu@ebi.ac.uk">Dan Wu</a>
 * @version
 */

package uk.ac.ebi.intact.util ; 


import java.util.*;
import java.io.*;

//re: SPTR parser
import uk.ac.ebi.yasp.*;
import uk.ac.ebi.yasp.util.*;
import uk.ac.ebi.aristotle.controller.*;
import uk.ac.ebi.aristotle.model.*;
import uk.ac.ebi.yasp.view.flatfile.FlatFile;
import uk.ac.ebi.yasp.view.flatfile.ViewHelper;
import java.util.Vector;


public class ParseSPTR {

    public static HashMap parseSPTR (String entry) {
        HashMap sptrHash = null ;
        
            try {
                YASPResult res = YASP.parse(entry, new YASPOptions());
                Aristotle a = res.getAristotle();
                Vector tremblRefs = TremblController.getAllTremblReferences(a);
                ReferenceID refid = (ReferenceID)tremblRefs.get(0);
                TremblController c = new TremblController();
                c.setAristotle(a);
                c.selectID(refid);

                
                Vector organismIDs = c.getOrganisms();

                for (int iii = 0; iii < organismIDs.size(); iii++) {
                    BioobjectID organismID =
                        (BioobjectID)organismIDs.get(iii);
                    String id =
                        Integer.toString(c.getNCBITaxonomy(organismID).NCBI_TaxID);
                    if (id.equals("4932")) { //eventually, '4932' will be a user param input
                        sptrHash = parseIntoHash(c, res.getHelper(), id);
                    }
                    else sptrHash = null ;
                }

            } catch (Exception excp1) {
                System.out.println(excp1.getMessage());
            }

            return sptrHash;
    }


    private static HashMap parseIntoHash(TremblController c, 
                                         ViewHelper helper, 
                                         String taxid 
                                         ) throws ControllerException {

        String ID = c.getTremblIDName();  
        Vector v = c.getTremblAccessionNumbers();
        String ac = (String)v.elementAt(0) ;
        String crc = c.getTremblSequenceCRC64() ;
        String seq = c.getTremblSequence() ;
        String fullname = appendDELine(c, helper) ;
        String sLabel = appendGNLine(c, helper) ;
        //value converted to "" by the parser if it was null in SPTR db.
        // ac could be in format 'P12345 or Q54321 or AH001'

        UpdateProteins upp = null ;
        HashMap hm = new HashMap() ;

        try {
            upp = new UpdateProteins() ;
        } catch (Exception up) {
            up.printStackTrace() ;
        }

        try {
            fullname = upp.replace("\\.$", fullname, "" ) ;
            fullname = upp.replace("'", fullname, "''" ); //Oracle needs EBI''s not EBI's for input.
            sLabel = upp.replace("\\.$", sLabel, "" ) ;
            sLabel = upp.replace("'", sLabel, "''" );
        } catch (Exception rep) 
            { System.out.println(rep.getMessage()) ; } 

        String loc = "" ;

        v = c.getTremblComments();
        for (int iii = 0; iii < v.size(); iii++) {
            ReferenceID id = (ReferenceID) v.elementAt(iii);
            SPTRComment comm = c.getTremblComment(id);
            if (comm instanceof SubcellularLocation) {
                loc = comm.description ;
                //System.out.println("Location: "+comm.description);
            }
        }

        /*
        System.out.println("ID: "+ ID) ;
        System.out.println("AC: "+ ac) ;
        System.out.println("TaxID: "+ taxid) ;
        System.out.println("SEQ: "+ seq) ;
        System.out.println("CRC64: "+ crc) ;
        System.out.println("ShortLabel: "+ sLabel) ;
        System.out.println("FullName: "+ fullname) ;
        System.out.println("Place: "+ loc) ;
        System.out.println() ;
        */

        //upp.toIntactDB(ID, ac, taxid, seq, crc, sLabel, fullname, loc) ;

       
        hm.put("ID", ID) ;
        hm.put("AC", ac) ;
        hm.put("TAXID", taxid) ;
        hm.put("SEQ", seq) ;
        hm.put("CRC64", crc) ;
        hm.put("GN", sLabel) ;
        hm.put("DE", fullname) ;
        hm.put("LOCATION", loc) ;

       return hm ;


    }

    /**
     * Appends the TrEMBL KW line to a given FlatFile using a ViewHelper
     * @param controller The TremblController that holds the data
     * @param ff The FlatFile that holds the preliminary flat file output
     * @param helper The ViewHelper that holds further layout information
     * @return A Vector contatining errors that occured during line geration
     * @throws ControllerException in case there are illegal access
     operations
    */

    //part of the parser
    private static String appendDELine(TremblController controller,
                                       ViewHelper helper)
        throws ControllerException {
        
        StringBuffer sb = new StringBuffer();

        //sb.append("DE:   ");
        if (controller.isHypothetical()) {
            sb.append("Hypothetical ");
        }
        
        if (controller.isPossible()) {
            sb.append("Possible ");
        }

        if (controller.isProbable()) {
            sb.append("Probable ");
        }

        if (controller.isPutative()) {
            sb.append("Putative ");
        }
        
        ReferenceID refid = null;
        String primaryProteinName = "";
        try {
            refid = controller.getTremblProteinNameReferenceID();
            SPTRProteinName nameref =
                controller.getTremblProteinName(refid);
            primaryProteinName = nameref.name;
            sb.append(primaryProteinName);
            try {
                ECReference ecReference =
                    controller.getECNumber(primaryProteinName);
                addECNumber(controller, primaryProteinName, sb);
            } catch (Exception excp2) {
                System.out.println(excp2.getMessage()) ;
            }
        } catch (Exception excp3) {
            System.out.println(excp3.getMessage()) ;
        }
        
        try {
            Vector syn = controller.getTremblSynonymOrder();
            
            if (syn != null) {
                HashSet osyn = new
                HashSet(controller.getTremblProteinSynonymsAsReferences());
                
                for (int iii = 0; iii < syn.size(); iii++) {
                    ReferenceID srefid = (ReferenceID) syn.elementAt(iii);
                    //if (osyn.contains(srefid)) {
                    sb.append(" (");
                    sb.append((controller.getTremblProteinSynonym((ReferenceID)syn.get(iii))).name);
                    sb.append(")");
                    //   osyn.remove(srefid);
                    //}
                }
            }

        } catch (Exception excp4) {
            System.out.println(excp4.getMessage());
        }

        try {
            Vector contains = helper.getContainsElements();
            
            if (contains != null && contains.size() != 0) {
                sb.append(" [Contains: ");
                
                int containsSize = contains.size();
                for (int iii = 0; iii < containsSize; iii++) {
                    
                    ReferenceID ref = (ReferenceID) contains.get(iii);
                    SPTRProteinName pname =
                        controller.getTremblContainsNestedName(ref);

                    String primaryContainsName = pname.getName();
                    sb.append(primaryContainsName);

                    addECNumber(controller, primaryContainsName, sb);

                    controller.getTremblContainsSynonyms(primaryContainsName);
                    Vector containsSynonyms =
                        helper.getContainsSynonyms(ref);
                    for (int jjj = 0; jjj < containsSynonyms.size(); jjj++)
                        {
                            sb.append(" (");
                            sb.append(containsSynonyms.get(jjj));
                            sb.append(")");
                        }
                    if (containsSize > 1 && iii < containsSize-1) {
                        sb.append("; ");
                    }
                }
                sb.append("]");
            }
        } catch (Exception excp5) {
                System.out.println(excp5.getMessage());         
        }
        try {
            Vector includes = helper.getIncludesElements();
            if (includes != null && includes.size() != 0) {
                sb.append(" [Includes: ");
                int includesSize = includes.size();
                for (int iii = 0; iii < includesSize; iii++) {
                    
                    ReferenceID ref = (ReferenceID) includes.get(iii);
                    SPTRProteinName pname =
                        controller.getTremblIncludesNestedName(ref);
                    String primaryIncludesName = pname.getName();

                    sb.append(primaryIncludesName);

                    addECNumber(controller, primaryIncludesName, sb);

                    Vector includesSynonyms =
                        controller.getTremblIncludesSynonyms(primaryIncludesName);
                    for (int jjj = 0; jjj < includesSynonyms.size(); jjj++)
                        {
                            sb.append(" (");
                            sb.append(includesSynonyms.get(jjj));
                            sb.append(")");
                        }
                    if (includesSize > 1 && iii < includesSize-1) {
                        sb.append("; ");
                    }
                }
                sb.append("]");
            }
        } catch (Exception excp6) {
            System.out.println(excp6);
        }
        

        if (controller.isFragment()) {
            sb.append(" (Fragment)");
        }

        if (controller.isFragments()) {
            sb.append(" (Fragments)");
        }

        if (controller.isVersion1()) {
            sb.append(" (Version 1)");
        }

        if (controller.isVersion2()) {
            sb.append(" (Version 2)");
        }
        sb.append(".");
        return sb.toString();
    }

    //part of the parser
    private static void addECNumber(TremblController controller, 
                                    String primaryProteinName, 
                                    StringBuffer sb) 
        throws ControllerException{
        ECReference ecReference =
            controller.getECNumber(primaryProteinName);

        
        if (ecReference != null) {
            sb.append(" (EC ");
            if (ecReference.firstLevel != ecReference.DASH) {
                sb.append(ecReference.firstLevel);
            }
            else {
                sb.append('-');
            }
            sb.append('.');
            if (ecReference.secondLevel != ecReference.DASH) {
                sb.append(ecReference.secondLevel);
            }
            else {
                sb.append('-');
            }
            sb.append('.');
            if (ecReference.thirdLevel != ecReference.DASH) {
                sb.append(ecReference.thirdLevel);
            }
            else {
                sb.append('-');
            }
            sb.append('.');
            if (ecReference.fourthLevel != ecReference.DASH) {
                sb.append(ecReference.fourthLevel);
            }
            else {
                sb.append('-');
            }
            sb.append(')');
        }
    }

    /**
     * Appends the TrEMBL GN line to a given FlatFile using a ViewHelper
     * @param controller The TremblController that holds the data
     * @param ff The FlatFile that holds the preliminary flat file output
     * @param helper The ViewHelper that holds further layout information
     * @return A Vector contatining errors that occured during line geration
     * @throws ControllerException in case there are illegal access
     operations
    */

    //part of the parser
    private static String appendGNLine(TremblController controller,
                                       ViewHelper helper)
        throws ControllerException {

        Vector errors = new Vector();
        Vector v = helper.getGNOrder();
        if (v.size() == 0) {
            return "";
        }
        
        StringBuffer sb = new StringBuffer();
        //sb.append("GN:   ");
       
        
        for (int iii = 0; iii < v.size(); iii++) {
            BioobjectID id = (BioobjectID) v.elementAt(iii);
            Vector w = helper.getGeneNames(id);
            if (v.size() > 1 && w.size() > 1) {
                sb.append('(');
            }
            
            for (int jjj = 0; jjj < w.size(); jjj++) {
                ReferenceID refid = (ReferenceID) w.elementAt(jjj);
                try {
                    GeneNameReference ref =
                        controller.getGeneNameReference(refid);
                    
                    sb.append(ref.getName());
                    
                } catch (Exception excp7) {
                    System.out.println(excp7);
                }
                
                if (jjj < w.size()-1) {
                    sb.append(' ');
                    sb.append("or");
                    sb.append(' ');
                }
            }
            if (v.size() > 1 && w.size() > 1) {
                sb.append(')');
                
            }
            if (iii < v.size()-1) {
                sb.append(' ');
                sb.append("and");
                sb.append(' ');
                
            }
        }

        sb.append(".");
        return sb.toString();
    } //appendGNLine()


}// ParseSPTR
