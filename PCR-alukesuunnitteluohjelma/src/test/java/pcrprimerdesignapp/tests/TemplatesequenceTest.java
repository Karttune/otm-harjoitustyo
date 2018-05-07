package pcrprimerdesignapp.tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pcrprimerdesignapp.domain.Templatesequence;

/**
 *
 * @author Konsta
 */
public class TemplatesequenceTest {

    private Templatesequence templateSequence;

    public TemplatesequenceTest() {
        templateSequence = new Templatesequence();
    }

    @Test
    public void sequenceFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        templateSequence.sequenceFromFile(file);
        assertTrue(null != templateSequence.getTemplateSequence());
    }

    @Test
    public void headerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        templateSequence.headerLineFromFile(file);
        assertEquals(">NM_001354656.1 Homo sapiens oxytocin receptor (OXTR)", templateSequence.getSequenceTitle());
    }

    @Test
    public void emptyFileCausesNoErrors() {

        File file = new File("Tyhjatestisekvenssi.fasta");

        templateSequence.headerLineFromFile(file);
        assertEquals("", templateSequence.getSequenceTitle());
    }

    @Test
    public void emptyHeaderCausesNoErrors() {

        File file = new File("Otsikotontestisekvenssi.fasta");

        templateSequence.headerLineFromFile(file);
        assertEquals("", templateSequence.getSequenceTitle());
    }

    @Test(expected = NullPointerException.class)
    public void wrongFileReturnsNull() {

        File file = new File("tamaeioletiedosto.fasta");

        templateSequence.sequenceFromFile(file);
        fail(templateSequence.getSequenceTitle());
    }

    @Test(expected = NullPointerException.class)
    public void wrongFileReturnsNullHeader() {

        File file = new File("tamaeioletiedosto.fasta");

        templateSequence.headerLineFromFile(file);
        fail(templateSequence.getSequenceTitle());
    }

    @Test
    public void idWorks() {
        templateSequence.setId(1);

        assertEquals(1, (int) templateSequence.getId());
    }

    @Test
    public void fwdIdWorks() {
        templateSequence.setForwardPrimerId(1);

        assertEquals(1, (int) templateSequence.getForwardPrimerId());
    }

    @Test
    public void revIdWorks() {
        templateSequence.setReversePrimerId(1);

        assertEquals(1, (int) templateSequence.getReversePrimerId());
    }

    @Test
    public void setTemplateSequenceWorks() {
        templateSequence.setTemplateSequence("GCATCTAGCTGACTAGC");

        assertEquals("GCATCTAGCTGACTAGC", templateSequence.getTemplateSequence());
    }

    @Test
    public void setSequenceTitleWorks() {
        templateSequence.setSequenceTitle(">Testi");

        assertEquals(">Testi", templateSequence.getSequenceTitle());
    }
}
