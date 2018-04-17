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
    public void readSequenceFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        assertTrue(null != templateSequence.sequenceFromFile(file));
    }

    @Test
    public void emptyFileCausesNoErrors() {

        File file = new File("Tyhjatestisekvenssi.fasta");

        assertEquals("", templateSequence.headerLineFromFile(file));
    }

    @Test(expected = NullPointerException.class)
    public void wrongFileReturnsNull() {

        File file = new File("tamaeioletiedosto.fasta");

        fail(templateSequence.sequenceFromFile(file));
    }

    @Test
    public void headerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        assertEquals(">NM_001354656.1 Homo sapiens oxytocin receptor (OXTR)", templateSequence.headerLineFromFile(file));
    }

    @Test
    public void emptyHeaderCausesNoErrors() {

        File file = new File("Otsikotontestisekvenssi.fasta");

        assertEquals("", templateSequence.headerLineFromFile(file));
    }

    @Test(expected = NullPointerException.class)
    public void wrongFileReturnsNullHeader() {

        File file = new File("tamaeioletiedosto.fasta");

        fail(templateSequence.headerLineFromFile(file));
    }

}
