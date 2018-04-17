
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;
import pcralukesuunnitteluohjelma.domain.Forwardprimer;

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

    public ForwardprimerTest() {
        fwd = new Forwardprimer();
    }

    @Test
    public void ForwardPrimerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        assertEquals("GCGGCAGGTGGATATGCTGA", fwd.forwardPrimerFromFile(file));
    }

    @Test
    public void ForwardPrimerWorksWhenSequenceIsTooShort() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        assertEquals("The template sequence is too short!", fwd.forwardPrimerFromFile(file));
    }
}
