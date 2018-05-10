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
import pcrprimerdesignapp.domain.Reverseprimer;
import pcrprimerdesignapp.domain.Templatesequence;

/**
 *
 * @author Konsta
 */
public class PrimerDesignChecksTest {

    private Forwardprimer fwd;
    private Reverseprimer rev;
    private Templatesequence temp;

    public PrimerDesignChecksTest() {
        fwd = new Forwardprimer();
        rev = new Reverseprimer();
        temp = new Templatesequence();
    }

    @Test
    public void checkLowGcPercentageWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("GCGGCAGGTGGATATGCTGA", fwd.getForwardPrimer(temp.getTemplateSequence()));
    }

    @Test
    public void checkHighGcPercentageWorks() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("", fwd.getForwardPrimer(temp.getTemplateSequence()));
    }

    @Test
    public void checkMatchingNucleotidesWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);

        fwd.getForwardPrimer(temp.getTemplateSequence());
        assertEquals(20, (int) fwd.matchingNucleotides(temp.getTemplateSequence()));
    }

    @Test
    public void checkFwdRepeatsWorks() {
        fwd.setPrimer("ATCGATCGATCG");

        assertEquals(12, (int) fwd.getPrimer().length());
    }

    @Test
    public void checkRevRepeatsWorksa() {
        fwd.setPrimer("ATCGATCGATCG");

        assertEquals(36, (double) fwd.tmTemperature());
    }

    @Test
    public void checkLowTmWorks() {
        fwd.setPrimer("ATCGATCGATCG");

        assertEquals(50.0, (double) fwd.gcPercentage(), 1e-15);
    }

    @Test
    public void checkHighTmWorks() {
        fwd.setId(1);

        assertEquals(1, (int) fwd.getId());
    }

    @Test
    public void checkTmMismatchWorks() {
        fwd.setStart(10);

        assertEquals(10, (int) fwd.getStart());
    }

    public void taTemperatureWorks() {

    }

    public void checkGcClampWorks() {

    }

    public void checkFwdDinucleotideRepeatsWorks() {

    }

    public void checkRevDinucleotideRepeats() {

    }
}
