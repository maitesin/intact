package uk.ac.ebi.intact.protein.mapping.strategies;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.intact.model.BioSource;
import uk.ac.ebi.intact.protein.mapping.actions.status.StatusLabel;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.MappingReport;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.impl.DefaultBlastReport;
import uk.ac.ebi.intact.protein.mapping.model.actionReport.impl.DefaultMappingReport;
import uk.ac.ebi.intact.protein.mapping.model.contexts.UpdateContext;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;
import uk.ac.ebi.intact.protein.mapping.strategies.exceptions.StrategyException;

/**
 * Unit test for StrategyForProteinUpdate
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30-Apr-2010</pre>
 */
public class StrategyForProteinUpdateTest {

    private StrategyForProteinUpdate strategy;

    public StrategyForProteinUpdateTest(){
        this.strategy = new StrategyForProteinUpdate();
        this.strategy.enableIsoforms(false);
    }

    private BioSource createBiosource(String shortLabel, String fullName, String taxId){
        BioSource bioSource = new BioSource();
        bioSource.setFullName(fullName);
        bioSource.setShortLabel(shortLabel);
        bioSource.setTaxId(taxId);

        return bioSource;
    }

    @Test
    public void test_Sequence_Mouse_BasicBlastProcessSuccessful(){
        String sequence = "METSVSEIQVETKDEKGPVAASPQKERQERKTATLCFKRRKKANKTKPKAGSRTAEETKKHPPEAGGSGQRQPAGAWASIKGLVTHRKRSEPAKKQKPPEAEVQPEDGALPKKKAKSRLKFPCLRFSRGAKRSRHSKLTEDSGYVRVQGEADDLEIKAQTQPDDQAIQAGSTQGLQEGVLVRDGKKSQESHISNSVTSGENVIAIELELENKSSAIQMGTPELEKETKVITEKPSVQTQRASLLESSAAGSPRSVTSAAPPSPATTHQHSLEEPSNGIRESAPSGKDDRRKTAAEEKKSGETALGQAEEAAVGQADKRALSQAGEATAGHPEEATVIQAESQAKEGKLSQAEETTVAQAKETVLSQAKEGELSQAKKATVGQAEEATIDHTEKVTVDQAEETTVGQAEEATVGQAGEAILSQAKEATVVGQAEEATVDRAEEATVGQAEEATVGHTEKVTVDQAEEATVGQAEEATVGQAEEATVDWAEKPTVGQAEEATVGQAEEATVGHTEKVTVDQAEEATVGQAEEATVGHTEKVTVDHAEEATVGQAEEATVGQAEKVTVDHAEEATVGQAEEATVGQAEKVTVDHAEEATVGQAEEATVGQAEKVTVDQAEEPTVDQAEEAISSHAPDLKENGIDTEKPRSEESKRMEPIAIIITDTEISEFDVKKSKNVPKQFLISMENEQVGVFANDSDFEGRTSEQYETLLIETASSLVKNAIELSVEQLVNEMVSEDNQINTLFQ";
        BioSource organism = createBiosource("mouse", "Mus musculus", "10090");

        UpdateContext context = new UpdateContext();
        context.setSequence(sequence);
        context.setOrganism(organism);

        this.strategy.setBasicBlastProcessRequired(true);

        IdentificationResults<DefaultMappingReport> result = null;
        try {
            result = this.strategy.identifyProtein(context);

            Assert.assertNotNull(result);

            for (MappingReport r : result.getListOfActions()){
                System.out.println("Label : " + r.getStatus().getLabel().toString() + ": Description : " + r.getStatus().getDescription());
            }

            Assert.assertNull(result.getFinalUniprotId());
            Assert.assertEquals(true, result.getLastAction() instanceof DefaultBlastReport);
            Assert.assertEquals(StatusLabel.TO_BE_REVIEWED, result.getLastAction().getStatus().getLabel());
        } catch (StrategyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void test_Sequence_Human_WithIdentifier(){
        String sequence = "MKACASFWKKPTLPEKGSSDVQETEDSCPKSRKLAPENWSVINSSSGERVGTFLEKRITSSLTSEEKECNFEDRILFSREILWSGTSESEDQVSPSSESHVPSSNGISSSLPLFYSEVEETCLSHTEHPDREYETIQFSSKKLFSMMKTNKNKNSGFSSDLSFSASRFTVENEDLDVAPCPLAHLFLSRDQVRLLEENVRNQIPSKPKTKLGSRTTYQCSRSQESLNQNQPSVGMVISVQAQDSFPGQNAFQNQGLYEVQFTSQAQYINHNQESIKSQPESKASNFAQPEDVMKKPFSSSTQDSFQSQDLDRNQHFVEVPSIVEAKYSVKGLESDEHLGEDQHCVWFIDSNKVKYSIKGQDTIFKNAEFLVLTLNPNLVTEDMPQLRSVKAQGQQQIVSSELNQDSVYSSVPLLSTIKGQKNRRKTPDSKSKLSLNVPSLKAKKTPTSQVFQITVCHTLKNRNELGCKNNTEKKELHERKDISDIALHLISVSKLILPYVKNYSRKQLVKVMPGLIKCGHFLQKQNKSPDTEKINYAGPLEETGISDITKKEKEYDKENKRLKNISPKMLPQLEQSFMVNTVQLKAPCLLVETNGKSKESLKDSITQAKGIGITEFHVLNSKKPFDLHIPKHKTSLEEAISKPMQKLVSSPEMESNNRMKIQEDLQSSENSHLQLSNGEELPTSTPKTQRCFPRENTQKQKDFLELVLELSNVGLLISPGSKMHKSSEELEAIKIQVNTESVNLKESKPLILNVTEDSDLRESEELECNTGSNITNMHQDKETSDAFHSATYTTISQLPDTETHSISKAKADTLRIIRLSHSASKQEKLPDEKETQNAEYIDKSCTFKKPQQCDRKEQEKEANSELTQGFRFSIHLKQKPKYVKFQMEQISSGSSKAPNKEQEVQPQTLSTQTILENSPCPMMDPFQVEKVKQSTDRPTDRESAGDPKNPLTMPENLPVGELLIETTEYSVPFGGNLQKTTDSHIAEEKEDVKRYLPAVALGSFNNHLLTLPYFKRQEIKKKLSETKSVLSVKYVIMKVKKPAISLMPYINICGTSNHRKKMGGNFEIIIKQILQDKIAAGMLLNVIYPPMSILPNTRMYSRLNAENHSHIKLVQEESQIEREEKYPYFINEGNESQNTLDAKLQDEVKGVKETLPKAVLHDSCNLGLDAHLEKEIKTEKEMHQPIPFTETIIESVVSPIMELSHAENVKSTQKTQTDCKCTADSETPSPISGKSLIGDPLNQTRESYIPSNGSDTREMGYCFAEEKTEIPKDLPATSPETFNYCTPVLSCSKVMKKRVTFALTTSTAKPKCVNTKAVKPSISETVSVTSHRKKSELDFKTKFKKINQTKGLVPECLNTLCSPMHSRLQREFCLPASQLKQGETADKTYTDVFAKNSISHDREEKLQDGKEEEHKVLLEAAPQLSQHLGSEAGQMKEIHLESDPVLNCLTLELHINGQRLQHQTGFEQTTLETSLQMGPLEAEELQKANETENDIKVLGGPKIPPPKALQALENSDGLILNAYQKDNELVKSDEELNQPGSTNIQVQPQTHFTQTILKSTSCPTLDQFPFEKVESHVRFSPLKSGEAKVDEIIFYAREGGISSDSSHQKEQAGGTEKKETAIFGSCMPALSTPKTTRNLKQFSDMKTLVNPKCGIIKAKKPSISYMLNIRAGAGPKRRKELSCNLTTKMKELHQGKKGVDETYAFLTMTPDINKYSKVETEKDTLREKRLSSTQVKQDTSPHEDSITSRDIKETLLQDEEQEERKQEALLKVIPQHLQHFMFRSGQGKDLDFHKLENQGSRKILFVTKQDVPQQLQPAEPIQREETKKCLQTQNGTICTVNSKLLPLKSEDSVNGEVLTGAIKRGVPTDRKCMGEQHNSGKGEKAEFNKDLQATVLELQKSPHGGEAQKANLTDMESGSSNAMNMNVQHEREDKNIQKMLTESVPCYSQHLRFSTHQMKDPDPCKSGSEPKSPEGRSWNLSHIVQKTKQETHFRETVLEPISGYMMKQSPHMQEGIKCMEGLKTSFPKTGKSKIGSIPRDTPWDENPRRKWDSSISEKTAWNQKNLQTVLKPLDFSSLMSSEYESRSYTLEFIGKKSMSPKCVTLKAKQLRILQLFNIIRYSTENHRKKKQHRFKYKMKGKQWYTSIGEALLSATEYAKSTTSKSMIDKLLYNTAARCILSNRTRRQNLDGHITEEKEEVQENVAAIFLGLLDFFMPVLSDSKNQRNTAQLSEKEIIFNAKCLTMKEKKSSISQIHKINRESTRKHRKKCKSYLKTVSNRKCQENHGHITEEEEEVQENSPATFLGPLDFFMPVLSDSKNQINTIQLSERKIILNPKCLTMKEKKPPISQIHKISGQFTTKHRKKLESNLKTKLKAMWQGENVADTFPNTTSFTPDSSDIKRQSGFQTEIDMRISGLSHTQPTQIESLAEGIARYSDPIDKRRTSNLVKGAKLHDRESGEEKQEHLTEMDPFYAENFMANTYLRKDRHLGKSEDVLLGETFFSKSQIYKGNSEKNVKIEKNKNGKESLKVGLARMEKSDNCAELSEATDDAISNKYDKQNIGHSVLKENAFCNLAAIVPDSVGRHSPASEEMKRQNGRLKMADRSSPQGRPLQAKQSAVSQSPDTAGYAVVSNNKEQKQNFKAQKTEAEVDLIDQEAKINVAEEFNPESVFFSKIHPLQIENKKEFKTADWKTRADPKTFALPKKQQELCVSGTIWSYPNPYTSISPKIIRHKDKAKTADVESTMHTKQIKLKAKRITVSQLLEYGTASNKKELRGNIQQQKSFQLSKNAVHRVLKAVYDSGYCVSSIKKLTEVKMEKDKPKDRTCILPQPKLEKPLKEMQRSLSGCTDMSSILRKQEQDIREKEQKHQSISEDISQYYIGPLRISSQQINYSSFDAPRIRTDEELEFLIAQRAKEKDVGIAKHSVSIPWEREGSKRLDIPLNSKGQNIFFTELDTSQQKTCQEQELLKQEDISMTNLGSMACPIMEPLHLENTGKVTEEEDVYINRKISSHVLGKEGLKETDIFVGSKGQKFLCTNSEVQHKVPAEQKEQVNPDHVPESILDSESFLSKDPLHLKQAVNTARKENVTISESFNENLWGKEQSKLDITLKSNRQKMDFSKKLRMKHLSNYYQNKENILESVLPCILHQLYIENPKKEGSAEEIMSSKVLSPMVEKASHEVGIPVDQPPCSEGIHLNIKGRKEHPQESTHEAFPASVSHSLMDVLQIKSPKVKKALKAINSLGYLTSNTKGIGLLFPRQAEKEEKYTYKALPKPASHSKTDLFQFNASMQQEKLDAMDIPHYDYLTSQTREAVKQMDVIVGYTQNSKKRQDLLKTGQKWQYLPISYENFWEHISCPQKYPCLLQHLMPQEKEALSEGGNLSSRTPGLDLFSADQLSTITKNRLEWIVPLISPRQMKKQDSMLPLGSYHKTIKYASLLFPKGMKSSDGVQVFDLISNNSSPKLRLGKKIETQKANEKVQKEVCLPITLHSLSASMPILQESKGQKDSVEQVIRKGVICHKRRTSKWKKSVFSHILNTSDCGASSNRLEMQWNMTDKMVNVKHRMSEIDLVAAKIYIIKQEGKMQEGKGKSSMKLTNLCTSLPSLSHSNSNSRTKAGKDKSGTLKGCLPPLKLQASSNARRVSSAESINRDSLSNVIESKCFPQKKKEDRENIVDVKDVMGLKCITLKGKKSLFRHLLHGKEPQRSNKKLEKMTQEDESNLNVVQNKLCASILSPPHLEWNPRIKEVYMRGITRFCLSSSTQQELSDTMEKCEQPIDDSLSSIEKAKHMPQKDKDRVEKALEKIMHSKRIALEVKQPSIFQELELNIKEKGGKIQEDKEVEIWSKPFASISFLPYSKVGTIEGEEAMRIKMRSSFSQPNLQESSDTEKTAYEKCISDNISNSVKKALESILQKEQRQKMEKIRALKKMKSSISQGIQLDIKEQEKRIEHIKGEPSVLLTNACASIPSPSHLQLDTRREKAEYVTEITRYYLPELSHQKSSEAGEKADGVASKGDITIKVQKAKDYMQQKEDDEVKISAKKDIMHPEDKDKRTAGKKEEQGVTRSFLPPSWHMESSDTGKLKYTLSYLNDITGDSNRTKYMAQIQKDKANISEKSVMHPEYIAVKAEKSPLSHILKTKELQVNISQQGEKAQEGEVEIVVLLSKTCPFVTSSAFLELDSIKEEEGEPRITRSFMPHLEIQESLPSRQTAPTKPTESLVKKEKQLLPQKEDRVQTVSMHGLMHPNGAVFKAKTSAPPQVFSITEHSPLSKRKEPQWGMKERAGQKQDRTGRPHVILTKTHPFMPSLSHHRFSPSQPKLPISSGAGKSRLANSNEGISSHKVILKANQQMPYKEAKDRVKIEGREGRILPKRIHLRAEALPLALLCNGKNYSLHIEEQGEGVQESKKEPGVVPRKSASFPPPPFYLNCDTRRNEKEGTLGKTQFSFPPLKIQDSSDSGKKAYTESLHGYTLSNSKGPVQPTAQGEEKGGLRIDMEDKMLPKCTDLKAKQLLLSDILNTKKLQWKSKEQKRKIQEDKNKQVKGLPSINTSLLTPPYLKFDTTEGQENVIRIAKVSLPQSRSKESSDAGRIACPEATHGELSSDVKQLKAHLLQKEEKDREKVADMTSVLDPNKMYLKAKKSPVLHTHSFSDLQWKTREQEEEKVQKVKSGPGVMLSKSPSRSSPLHLNVNTGFQEESIPILTRPSFPLVKLQVSPDTEGGTCIRPIAGDILIYLQKGKHVSQNKEEDDVQIVSILIFPKHQEEKVQECEGEPGVVLTKSTSLPSLSQLELDKETHLGNEMLRLKRPILRRISHIGETVHRESVVGDIPKDVKNEKQHIPQKEERNQKKIIDMRGTDITLKSKKSPRSCMLHRTELHVNIGGQGRKEHEGQDKPPGMIQRKMCILFSKPLPSNLKLERATHADEERLGGKTSFVLPLMPSALPDTEKTADAEARSGDVRKGKPHRSQKENRHEVKTIDMRFRIHCQEARISPMSHILNAKELVLNINKLEKKVHKDKDEACVVLSRTFLSIPSAPPLYLDSGNKTDKDTPGITGSSCPQRTLHVPSNTQKITNRDSVEGVDKNVVKQAEQYVPRPEAEQQLTSNFMISVQQRNQPSRVRSEEDLNQLVLNSRDEDIYFTGFGTIRSGKRPEWLFTGKKAQPVKYKTETLTAFLSYPTMDATKMGGLEEDTEIMDNLNHKISPKASVSLIRKISKELYVTLGTPANSKGFSVSERYAHQQETSSKVSPELAGSCKFDKPKEDGQSNDRISKMFSPKVLAPQTKGSLKKISIVTNWNAPQNIEEQDIVMKKQVIRRCEHGHKTRTNTILSKFPLQSGKQKTPSETDVDKKTTAHLSLQMLPGIHMDMTEIDPAKGGRKQALLISEQEEGVLEFLPKSLFPPWTFQFQSGDLEEKHQTDANTNINLEQKKLEMDNDSTVNQKEGKLKIGTNRALHLQEEKTEMHKARTANLEKERGRMDTSSSAHPHLLSLKAEESQMKTQVITHRENSRLIMQKQKKELEASNAKQSIQLQKLFQRNVLDSFYSYVPLSPKRKDQKGRLTIRDLKRELSTKYLTMKIQNHPIPQMLNITGRGTPSNRKKLEYDVKLKNIASWSKDVSGIFIRSLSISIMRSPHTDPKTNLEREKRICLPKFQEKSPNTSEMSKRDTLTIVKGEQNFTNTVPQDPQPFAVDKQQMQKLPNVKSEANLRSEMNKKYLKAQTKERIVPEHDVSRIIKKPDLRIIEQEEKILKRILTPTECPSMLEDPKLPKQRDQSEPVWDMTTQKVQQQKAFPGTVPIPPQVKSSEVKIVADSTNAEHLLPICEATKAISESQVKNMIQDKVSSDKLDNIQAYKPDDLKSPPFPEGPDTISTAIYPKTQHKSLLEQFTPKEKNKLTSHLESKALEIQLNLIPEMARKSLQMFNFYPKGTISKDNSWRFYSRHKTMNFMSLEGTDTIEPNSKHKHQKDSPLASNMKTLIVDVSSDSEETITKLQSINKLENGTSAVTSASEMLLPHTLQNHSVKEKGKLLMHFSVKTLEIQMKAFPRIVRESYAMTSAHERKKPLSNCIHPGFTGPKRQNRILLLSEEKSLHQIDLDLQYKYLRFLLGLPVGSTFPKPNVLPKHSKLNTIAVCKNVNAGGQSGSLSIDTELLEQHISFKKQSPHENSSLIRKFPQPTLVCASDRDLHSPRKKDTQVLSESEFHVTPEKNKQYHVWFQERNTCESVDLRTQRNATGSAVSCETQISEDFVDIQTDIESPADLDECSCLEVSESEECVFLEANSYLSQESENILFELQTGIPLENVYKITTDLKSFYSEDSGSHCTRECRKETLIITPPSCKSHKSRKYRSSSKMKSPDWLCHSSSNTAEIQSRSSSVSFSEEKISWTTKSRTSYSSAPLTESNIKSHLAKNQGKSHRHPESQERKKARSDLFRKNSSHWDHDYSCTHSKGKRDRKKRVYDYESERLDCFQSKHKSASKPHHDDINFYSERKQNRPFFFACVPADSLEVIPKTIRWTIPPETLRKRNFRIPLVAKISSSWNIWSSSKKLLGSLSGSLTTVFHS";
        BioSource organism = createBiosource("human", "Momo sapiens", "9606");
        String identifier = "41149911";
        
        UpdateContext context = new UpdateContext();
        context.setSequence(sequence);
        context.setOrganism(organism);
        context.addIdentifier("MI:0860", identifier);

        this.strategy.setBasicBlastProcessRequired(false);

        IdentificationResults<DefaultMappingReport> result = null;
        try {
            result = this.strategy.identifyProtein(context);

            Assert.assertNotNull(result);

            for (MappingReport r : result.getListOfActions()){
                System.out.println("name" + r.getName().toString() + "Label : " + r.getStatus().getLabel().toString() + ": Description : " + r.getStatus().getDescription());
            }

            Assert.assertNull(result.getFinalUniprotId());
        } catch (StrategyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void test_Sequence_Drome_WithoutIdentifier(){
        String sequence = "MSGERRRTTTHSIEVPSQLTAQHNSRKRPPSDHQDYGNYLETCRAAEILSSMKLQSPHGSMADKCSSPGSSSSASWSSGSPSPPLSDDGHAHHSPHNIMSPHDADNARTRTASVSTSDEGIVIDYKEERKKKSKKFRCVYRGCVGVVDDLNGVVRHIRKTHLGKDSHRSADDDGNEEDFYLEDADDDVEQVKPTLASEPTLSHRDMARPPHEDPEYQKQIVGNFKQGRGGSHYNHLAQSHGRTISGSNIPSTHQQQLQNNNTSCIPTSHLAHHNYTCPAATATVGSYSSTGTGSVAASSSASPIGKHARSSSSRPTHSVAPYPSPTYVQQQQHHQHTHHHNYAGSSGSSNSSSSSSPVIHSNSSANNMLQQLSQQNVTVTAHHSQQQQQLQQQQHHQQQQQHSHQQQQQHLLSSVTITPNFHPAQQQHHHQPMRGHQQQHPQTTAGNMVAQNNSNNHSNGSNPLQQQQHMAQQVAVKHTPHSPGKRTRGENKKCRKVYGMEKRDQWCTQCRWKKACSRFGD";
        BioSource organism = createBiosource("drome", "Drosophila melanogaster", "7227");

        UpdateContext context = new UpdateContext();
        context.setSequence(sequence);
        context.setOrganism(organism);

        this.strategy.setBasicBlastProcessRequired(false);

        IdentificationResults<MappingReport> result = null;
        try {
            result = this.strategy.identifyProtein(context);

            Assert.assertNotNull(result);

            for (MappingReport r : result.getListOfActions()){
                System.out.println("name " + r.getName().toString() + "Label : " + r.getStatus().getLabel().toString() + ": Description : " + r.getStatus().getDescription());
            }

            Assert.assertNull( result.getFinalUniprotId());
        } catch (StrategyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
