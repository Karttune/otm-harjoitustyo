
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcrprimerdesignapp.domain.Reverseprimer;
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
public class ReverseprimerTest {

    private Reverseprimer rev;
    private Templatesequence temp;

    public ReverseprimerTest() {
        rev = new Reverseprimer();
        temp = new Templatesequence();
    }

    @Test
    public void ReversePrimerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("TTTTATTTTTATTTGTTTCC", rev.getReversePrimer(temp.getTemplateSequence()));
    }

    @Test
    public void ReversePrimerWorksWhenSequenceIsTooShort() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("The template sequence is too short!", rev.getReversePrimer(temp.getTemplateSequence()));
    }

    @Test
    public void getPrimerLengthWorks() {
        rev.setReversePrimer("ATCGATCGATCG");

        assertEquals(12, (int) rev.getPrimerLength());
    }

    @Test
    public void tmTemperatureWorks() {
        rev.setReversePrimer("ATCGATCGATCG");

        assertEquals(36, (int) rev.tmTemperature());
    }

    @Test
    public void gcPercentageWorks() {
        rev.setReversePrimer("ATCGATCGATCG");

        assertEquals(50.0, (double) rev.gcPercentage(), 1e-15);
    }
}
