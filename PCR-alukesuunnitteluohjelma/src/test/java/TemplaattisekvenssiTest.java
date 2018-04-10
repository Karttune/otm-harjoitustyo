/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pcralukesuunnitteluohjelma.domain.Templaattisekvenssi;

/**
 *
 * @author Konsta
 */
public class TemplaattisekvenssiTest {

    private Templaattisekvenssi templaattisekvenssi;

    public TemplaattisekvenssiTest() {
        templaattisekvenssi = new Templaattisekvenssi();
    }

    @Test
    public void tiedostonLukeminenToimii() {

        File file = new File("Testisekvenssi.fasta");

        assertTrue(null != templaattisekvenssi.sekvenssiRivienPalautusTiedostosta(file));

    }

    @Test
    public void otsikkoRiviToimii() {

        File file = new File("Testisekvenssi.fasta");

        assertEquals(">NM_001354656.1 Homo sapiens oxytocin receptor (OXTR)", templaattisekvenssi.otsikkoRivinPalautusTiedostosta(file));

    }
}
