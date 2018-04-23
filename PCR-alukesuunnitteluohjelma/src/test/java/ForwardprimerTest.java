
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.Templatesequence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konsta
 */
public class ForwardprimerTest {

    private Forwardprimer fwd;
    private Templatesequence temp;

    public ForwardprimerTest() {
        fwd = new Forwardprimer();
        temp = new Templatesequence();
    }

    @Test
    public void ForwardPrimerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("GCGGCAGGTGGATATGCTGA", fwd.getForwardPrimer(temp.getTemplateSequence()));
    }

    @Test
    public void ForwardPrimerWorksWhenSequenceIsTooShort() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("The template sequence is too short!", fwd.getForwardPrimer(temp.getTemplateSequence()));
    }

    @Test
    public void getPrimerLengthWorks() {
        fwd.setForwardPrimer("ATCGATCGATCG");

        assertEquals(12, (int) fwd.getPrimerLength());
    }

    @Test
    public void tmTemperatureWorks() {
        fwd.setForwardPrimer("ATCGATCGATCG");

        assertEquals(36, (int) fwd.tmTemperature());
    }

    @Test
    public void gcPercentageWorks() {
        fwd.setForwardPrimer("ATCGATCGATCG");

        assertEquals(50.0, (double) fwd.gcPercentage(), 1e-15);
    }
}
