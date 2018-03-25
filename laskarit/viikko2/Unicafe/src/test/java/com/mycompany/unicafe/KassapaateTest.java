/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Konsta
 */
public class KassapaateTest {

    Maksukortti kortti;
    Kassapaate paate;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
        paate = new Kassapaate();
    }

    @Test
    public void kassaPaatteenJaKortinMaaratOnOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKateisellaToimii() {
        assertEquals(10, paate.syoEdullisesti(250));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100240, paate.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKateisellaToimii() {
        assertEquals(10, paate.syoMaukkaasti(410));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100400, paate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiKateisellaPalauttaaMaksun() {
        assertEquals(230, paate.syoEdullisesti(230));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKateisellaPalauttaaMaksun() {
        assertEquals(390, paate.syoMaukkaasti(390));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiKortillaToimii() {
        assertEquals(true, paate.syoEdullisesti(kortti));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKortillaToimii() {
        assertEquals(true, paate.syoMaukkaasti(kortti));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiKortillaEiToimiIlmanRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoEdullisesti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiKortillaEiToimiIlmanRahaa() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoMaukkaasti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(2, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kortilleRahaaLadattaessaSaldoMuuttuu() {
        paate.lataaRahaaKortille(kortti, 500);
        assertEquals(100500, paate.kassassaRahaa());
        assertEquals(1500, kortti.saldo());
    }

    @Test
    public void kortilleRahaaLadattaessaSaldoEiMuutuJosNegatiivinen() {
        paate.lataaRahaaKortille(kortti, -500);
        assertEquals(100000, paate.kassassaRahaa());
        assertEquals(1000, kortti.saldo());

    }
}
