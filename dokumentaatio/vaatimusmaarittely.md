# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on toimia synteettisten DNA-alukkeiden (DNA primer) suunnittelussa, joita käytetään [polymeraasiketjureaktiossa](https://fi.wikipedia.org/wiki/Polymeraasiketjureaktio). 


_Kuva 1: PCR-reaktio pähkinänkuoressa_
<img src="https://qph.ec.quoracdn.net/main-qimg-b332e117400787aac537141a88f8cdb4">

## Käyttäjät

Sovelluksella on yksi käyttäjärooli, eli ohjelman tarjoama funktionaalisuus on kaikille käyttäjille sama.

## Käyttöliittymäluonnos

Sovelluksen käyttöliittymä on alustavasti yksi ikkuna, jossa kaikki funktionaalisuus on tarjolla. Jos yhden ikkunan malli osoittautuu
liian sekavaksi, funktionaalisuutta eriytetään omiin ikkunoihinsa.

## Perusversion tarjoama toiminnallisuus

- Ohjelma keskittyy standardi-PCR -alukkeiden suunnitteluun, eli amplikonin (reaktiossa monistettavan DNA-jakson) pituus on korkeintaan noin 10 000 emäsparia.
- Templaattijuosteen syöttäminen joko suoraan tekstinä tai .fasta -tiedostoformaatissa.
- Luo automaattisesti sekä forward- että reverse -alukkeet sekvenssille. Alukkeiden alku- ja loppupaikkaa voi myös muokata oman maun mukaan.
- Ohjelma laskee perustiedot alukkeista, kuten niiden nukleotidipituuden, kuinka monta nukleotidiä täsmää templaattijuosteen kanssa, GT-pitoisuuden prosenteissa, Tm-lämpötilan Wallacen kaavalla (Tm = 2°C x (A/T) + 4° x (C/G)) sekä Ta -lämpötilan (Ta = Tm - 5°C). Tm-lämpötila tarkoittaa lämpötilaa PCR-reaktiosyklissä, jossa aluke irtoaa templaattijuosteesta, ja Ta tarkoittaa lämpötilaa jossa aluke pystyy kiinnittymään templaattijuosteeseen.
- Ohjelma mahdollistaa alukkeiden muokkaamisen oman halun mukaan.
- Ohjelma ilmoittaa suunnitteluvirheistä alukkeissa, esimerkiksi liiasta GC-pitoisuudesta, liian pitkistä alukkeista, toistojaksoista, yli 4 saman emäksen pituisista toistoista, palindromisista sekvenssijaksoista, alukkeiden välisistä komplementaarisista jaksoista, alukkeiden liian erilaisesta Tm -lämpotilasta jne. Alukkeiden suunnittelusta voi lukea lisää [täältä.](http://www.premierbiosoft.com/tech_notes/PCR_Primer_Design.html)
- Ohjelma mahdollistaa valmiiden alukkeiden tallentamista tietokantaan, josta niitä voi hakea ja halutessaan muokata.


_Kuva 2: Havainnollistava kuva alukkeiden paikoista DNA-juosteessa_
<img src=https://upload.wikimedia.org/wikipedia/commons/9/91/Primers_RevComp.svg>

## Jatkokehitysideoita

- Tm ja Ta -lämpotilojen laskeminen tarkemmalla kaavalla, esimerkiksi Baldinon algoritmi (Tm = 81,5°C + 16,6 log[suolan konsentraatio reaktioliuoksessa millimooleina] + 0,41(GC%) - 675/alukkeen pituus) ja eri polymeraasientsyymien määrittäminen alukkeen suunnittelussa (joka polymeraasilla on omanlaisensa vaatimus suolakonsentraatiosta reaktioliuoksessa).
- Tarkemmat suunnitteluvirheiden ilmoitukset.
- Restriktiokohtien lisääminen alukkeiden 5'-päähän ja restriktiokohtien eli DNA:ta tietyistä paikoista leikkaavien entsyymien leikkauskohtien etsiminen sekvenssistä [samaan malliin kuin NEBCutterilla.](http://nc2.neb.com/NEBcutter2/)
- PCR-koneen ohjelman suunnittelutoiminnallisuus, johon ohjelma määrittelee käytetyn polymeraasientsyymin, amplikonin pituuden, Ta-lämpötilan jne. ja suunnittelee optimaalisen reaktiosyklin.
