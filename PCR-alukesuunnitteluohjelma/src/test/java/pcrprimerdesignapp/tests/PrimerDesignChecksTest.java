/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.tests;

import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.PrimerDesignChecks;
import pcrprimerdesignapp.domain.Reverseprimer;
import pcrprimerdesignapp.domain.Templatesequence;

/**
 *
 * @author Konsta
 */
public class PrimerDesignChecksTest {

    private PrimerDesignChecks checks;
    private Forwardprimer fwd;
    private Reverseprimer rev;
    private Templatesequence temp;

    public PrimerDesignChecksTest() {
        fwd = new Forwardprimer();
        rev = new Reverseprimer();
        temp = new Templatesequence();
        checks = new PrimerDesignChecks(fwd, rev, temp);

    }

    @Test
    public void checkLowGcPercentageWorks() {

        fwd.setPrimer("ATATAATATATATATGATATATCATATA");
        rev.setPrimer("ATATTAATGCTATATATAGCTATATATA");

        assertEquals("Forward primer GC% is too low! Reverse primer GC% is too low!", checks.checkLowGcPercentage());

        fwd.setPrimer("ATATAATATATATATGATATATCATATA");
        rev.setPrimer("GGCCGGCCGTACGCCGCGCAGCGCGCCA");

        assertEquals("Forward primer GC% is too low!", checks.checkLowGcPercentage());

        rev.setPrimer("ATATAATATATATATGATATATCATATA");
        fwd.setPrimer("GGCCGGCCGTACGCCGCGCAGCGCGCCA");

        assertEquals("Reverse primer GC% is too low!", checks.checkLowGcPercentage());

    }

    @Test
    public void checkHighGcPercentageWorks() {

        fwd.setPrimer("GCGCGCGATCGCGCGCGCGGCACTGCGCG");
        rev.setPrimer("GCGCGCATGCGCGATCGCGGCGCGCGCGC");

        assertEquals("Forward primer GC% is too high! Reverse primer GC% is too high!", checks.checkHighGcPercentage());

        fwd.setPrimer("GCGCGCGATCGCGCGCGCGGCACTGCGCG");
        rev.setPrimer("TATATATATATTATTATATATATATTATA");

        assertEquals("Forward primer GC% is too high!", checks.checkHighGcPercentage());

        rev.setPrimer("GCGCGCGATCGCGCGCGCGGCACTGCGCG");
        fwd.setPrimer("TATATATATGCGATTATATATATATTATA");

        assertEquals("Reverse primer GC% is too high!", checks.checkHighGcPercentage());

    }

    @Test
    public void checkMatchingNucleotidesWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);

        fwd.setPrimer("GATCGCATCGTACTACTGCGATCGC");
        rev.setPrimer("GACAGACACCGCGCGACAGCGCGAC");

        fwd.setStart(0);
        rev.setStart(4016);

        assertEquals("Forward primer match% is too low! Reverse primer match% is too low!", checks.checkMatchingNucleotides());

        fwd.getForwardPrimer(temp.getTemplateSequence());
        
        assertEquals("Reverse primer match% is too low!", checks.checkMatchingNucleotides());

    }

    @Test
    public void checkRepeatsWorks() {
        fwd.setPrimer("ATCGAAAAATATGTCGATCG");
        rev.setPrimer("ATCGAAAAATATGTCGATCG");

        assertEquals("Forward primer contains repeated sequences!", checks.checkRepeats(fwd, "Forward"));
        assertEquals("Reverse primer contains repeated sequences!", checks.checkRepeats(rev, "Reverse"));

    }

    @Test
    public void checkLowTmWorks() {
        fwd.setPrimer("ATCGATCGATCG");
        rev.setPrimer("ATCGATCGATCG");

        assertEquals("Forward primer Tm is too low! Reverse primer Tm is too low!", checks.checkLowTm());

        fwd.setPrimer("ATCGATCGATCG");
        rev.setPrimer("GCATCTGACCGCGCATCGTACATCGAT");

        assertEquals("Forward primer Tm is too low!", checks.checkLowTm());

        rev.setPrimer("ATCGATCGATCG");
        fwd.setPrimer("GCATCTGACCGCGCATCGTACATCGAT");

        assertEquals("Reverse primer Tm is too low!", checks.checkLowTm());
    }

    @Test
    public void checkHighTmWorks() {
        fwd.setPrimer("GCGCGCGCGCGCGGCGGGCGCGC");
        rev.setPrimer("GCGCCGGCGCGCGCCGCGCGCGC");

        assertEquals("Forward primer Tm is too high! Reverse primer Tm is too high!", checks.checkHighTm());

        fwd.setPrimer("GCGCGCGCGCGCGGCGGGCGCGC");
        rev.setPrimer("ATATATATGCTATTATATGCTAT");

        assertEquals("Forward primer Tm is too high!", checks.checkHighTm());

        rev.setPrimer("GCGCGCGCGCGCGGCGGGCGCGC");
        fwd.setPrimer("ATATATATGCTATTATATGCTAT");

        assertEquals("Reverse primer Tm is too high!", checks.checkHighTm());

    }

    @Test
    public void checkTmMismatchWorks() {
        fwd.setPrimer("GCGCGCGGCGGGGCGCTATGC");
        rev.setPrimer("TATATATATTAGCTATATATA");

        assertEquals("Tm-temperatures are mismatched!", checks.checkTmMismatch());

    }

    @Test
    public void taTemperatureWorks() {

        fwd.setPrimer("ATCGATCGATCG");
        rev.setPrimer("ATCGTAGCGCATGCGA");

        assertEquals(31, (int) checks.taTemperature());

        rev.setPrimer("ATCGATCGATCG");
        fwd.setPrimer("ATCGTAGCGCATGCGA");

        assertEquals(31, (int) checks.taTemperature());
    }

    @Test
    public void checkGcClampWorks() {

        fwd.setPrimer("TGCTACACTCGAATTATATA");
        rev.setPrimer("ATATGCGACTACATCGCGGCGC");

        assertEquals("Forward primer has no GC nucleotides in 3' end!", checks.checkGcClamp(fwd, "Forward"));
        assertEquals("Reverse primer has too many GC nucleotides in 3' end!", checks.checkGcClamp(rev, "Reverse"));
    }

    @Test
    public void checkDinucleotideRepeatsWorks() {

        fwd.setPrimer("TACGATGTATATATATAGCGCTACGTA");
        rev.setPrimer("ATATGCGACTACATCGCGGCGC");

        assertEquals("Forward primer contains dinucleotide repeats!", checks.checkDinucleotideRepeats(fwd, "Forward"));
        assertEquals("", checks.checkDinucleotideRepeats(rev, "Reverse"));
    }

    @Test
    public void checkPalindromicSequencesWorks() {

        fwd.setPrimer("ATATCGCGTGTCTAGCTCGATAT");
        rev.setPrimer("AAATGCGCGATATATACATCAC");

        assertEquals("Forward primer contains palindromic sequences!", checks.checkPalindromicSequences(fwd, "Forward"));
        assertEquals("", checks.checkPalindromicSequences(rev, "Reverse"));

    }

    @Test
    public void checkPrimerDimerWorks() {

        fwd.setPrimer("ATATCGCGTGTCTAGCTCGATAGCAT");
        rev.setPrimer("ATATGCGACTACATCGCGAGTAATGC");

        assertEquals("Primer 3' ends are complementary!", checks.checkPrimerDimer());
    }

    @Test
    public void checkFwdThreePrimeMatchWorks() {

        temp.setTemplateSequence("ATATCGCGTGTCTAGCTCGGTAGCATTTACGCGATCGCATCGGGCTACGATCGCTAGCATCGACTACGATCGATCGTAGCATTACTCGCGATCTAGTCGCATATGCTAGCTAGCTATATCATGCTACTACTAGTAGCTAC");

        fwd.setPrimer("ATATCGCGTGTCTAGCTCGATAGCCGT");

        assertEquals("Forward primer 3' end is mismatched!", checks.checkFwdThreePrimeMatch());
    }

    @Test
    public void checkRevThreePrimeMatchWorks() {

        temp.setTemplateSequence("ATATCGCGTGTCTAGCTCGGTAGCATTTACGCGATCGCATCGGGCTACGATCGCTAGCATCGACTACGATCGATCGTAGCATTACTCGCGATCTAGTCGCATATGCTAGCTAGCTATATCATGCTACTACTAGTAGCTACGCATTACTCGCGATCTAGTCGCATAT");

        rev.setPrimer("ATATGCGACTACATCGCGAGTAATACA");
        rev.setStart(166);

        assertEquals("Reverse primer 3' end is mismatched!", checks.checkRevThreePrimeMatch());

    }
}
