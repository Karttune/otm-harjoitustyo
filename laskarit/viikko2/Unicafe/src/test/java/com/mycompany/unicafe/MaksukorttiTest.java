package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(2500);
        assertEquals("saldo: 35.0", kortti.toString());
    }

    @Test
    public void saldoVaheneeKunRahaaOnTarpeeksi() {
        kortti.otaRahaa(500);
        assertEquals("saldo: 5.0", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(2500);
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittavat() {
        assertEquals(true, kortti.otaRahaa(500));
    }

    @Test
    public void otaRahaaPalauttaaFalse() {
        assertEquals(false, kortti.otaRahaa(2500));
    }
}
