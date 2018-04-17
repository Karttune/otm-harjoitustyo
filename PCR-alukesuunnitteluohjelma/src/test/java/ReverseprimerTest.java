
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pcralukesuunnitteluohjelma.domain.Forwardprimer;
import pcralukesuunnitteluohjelma.domain.Reverseprimer;

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

    public ReverseprimerTest() {
        rev = new Reverseprimer();
    }

    @Test
    public void ReversePrimerFromFileWorks() {

        File file = new File("Testisekvenssi.fasta");

        assertEquals("TTTTATTTTTATTTGTTTCC", rev.reversePrimerFromFile(file));
    }

    @Test
    public void ReversePrimerWorksWhenSequenceIsTooShort() {

        File file = new File("Lyhyttestisekvenssi.fasta");

        assertEquals("The template sequence is too short!", rev.reversePrimerFromFile(file));
    }
}
