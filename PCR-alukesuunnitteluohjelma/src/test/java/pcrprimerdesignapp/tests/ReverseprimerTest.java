package pcrprimerdesignapp.tests;


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
        assertEquals("CCTTTGTTTATTTTTATTTT", rev.getReversePrimer(temp.getTemplateSequence()));
    }

    @Test
    public void ReversePrimerWorksWhenSequenceIsTooShort() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        temp.sequenceFromFile(file);
        assertEquals("", rev.getReversePrimer(temp.getTemplateSequence()));
    }

    @Test
    public void matchingNucleotidesWorks() {

        File file = new File("Testisekvenssi.fasta");

        temp.sequenceFromFile(file);
        rev.getReversePrimer(temp.getTemplateSequence());
        rev.setStart(temp.getTemplateSequence().length());
        
        int matches = rev.matchingNucleotides(temp.getTemplateSequence());
        
        assertEquals(20, (int) matches);
    }

    @Test
    public void getPrimerLengthWorks() {
        rev.setPrimer("ATCGATCGATCG");

        assertEquals(12, (int) rev.getPrimer().length());
    }

    @Test
    public void tmTemperatureWorks() {
        rev.setPrimer("ATCGATCGATCG");

        assertEquals(36, (int) rev.tmTemperature());
    }

    @Test
    public void gcPercentageWorks() {
        rev.setPrimer("ATCGATCGATCG");

        assertEquals(50.0, (double) rev.gcPercentage(), 1e-15);
    }

    @Test
    public void idWorks() {
        rev.setId(1);

        assertEquals(1, (int) rev.getId());
    }

    @Test
    public void startWorks() {
        rev.setStart(10);

        assertEquals(10, (int) rev.getStart());
    }
}
