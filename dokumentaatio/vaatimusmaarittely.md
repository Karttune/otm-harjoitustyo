# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on toimia synteettisten DNA-alukkeiden (DNA primer) suunnittelussa, joita käytetään [polymeraasiketjureaktiossa](https://fi.wikipedia.org/wiki/Polymeraasiketjureaktio). 


_Kuva 1: PCR-reaktio pähkinänkuoressa_
<img src="https://qph.ec.quoracdn.net/main-qimg-b332e117400787aac537141a88f8cdb4">

## Käyttäjät

Sovelluksella on yksi käyttäjärooli, eli ohjelman tarjoama funktionaalisuus on kaikille käyttäjille sama.

## Käyttöliittymä

Sovelluksen käyttöliittymä avautuu suoraan yhteen ikkunaan, jossa on kaikki ohjelman tarjoama toiminnallisuus.

## Perusversion tarjoama toiminnallisuus

- Ohjelma keskittyy standardi-PCR -alukkeiden suunnitteluun yleisimmin käytetyllä _Taq_ DNA-polymeraasientsyymillä, joka on ohjelmassa oletusentsyymi.
- Templaattijuosteen syöttäminen joko suoraan tekstinä tai .fasta -tiedostoformaatissa.
- Luo automaattisesti sekä forward- että reverse -alukkeet sekvenssille. Alukkeiden alku- ja loppupaikkaa ja alukkeiden sekvenssiä voi myös muokata oman maun mukaan.
- Ohjelma laskee perustiedot alukkeista, kuten niiden nukleotidipituuden, kuinka monta nukleotidiä täsmää templaattijuosteen kanssa, GC-pitoisuuden prosenteissa, Tm-lämpötilan Wallacen kaavalla (Tm = 2°C x (A/T) + 4° x (C/G)) sekä Ta -lämpötilan (Ta = Tm - 5°C). Tm-lämpötila tarkoittaa lämpötilaa PCR-reaktiosyklissä, jossa aluke irtoaa templaattijuosteesta, ja Ta tarkoittaa lämpötilaa jossa aluke pystyy kiinnittymään templaattijuosteeseen.
- Ohjelma ilmoittaa suunnitteluvirheistä alukkeissa, esimerkiksi liian suuresta tai vähäisestä GC-pitoisuudesta, dinukleotiditoistojaksoista (esimerkiksi ATATATATAT), yli 4 saman emäksen pituisista toistoista, palindromisista sekvenssijaksoista, alukkeiden välisistä 3'-pään komplementaarisista jaksoista, 3'-pään täsmäämisestä templaattisekvenssin kanssa, liian matalasta, korkeasta tai alukkeiden välisestä liian erilaisesta Tm -lämpotilasta. Alukkeiden suunnittelusta voi lukea lisää [täältä.](http://www.premierbiosoft.com/tech_notes/PCR_Primer_Design.html)
- Ohjelma mahdollistaa valmiiden alukkeiden ja templaattisekvenssin tallentamisen tietokantaan, josta niitä voi hakea ja halutessaan muokata.


_Kuva 2: Havainnollistava kuva alukkeiden paikoista DNA-juosteessa_
<img src=https://upload.wikimedia.org/wikipedia/commons/9/91/Primers_RevComp.svg>

## Jatkokehitysideoita

- Tm ja Ta -lämpotilojen laskeminen tarkemmalla kaavalla, esimerkiksi Baldinon algoritmi (Tm = 81,5°C + 16,6 log[suolan konsentraatio reaktioliuoksessa millimooleina] + 0,41(GC%) - 675/alukkeen pituus) ja eri polymeraasientsyymien määrittäminen alukkeen suunnittelussa (joka polymeraasilla on omanlaisensa vaatimus suolakonsentraatiosta reaktioliuoksessa, ja eri polymeraaseilla voidaan amplifikoida pitempiä DNA-jaksoja.).
- Tarkemmat suunnitteluvirheiden ilmoitukset.
- Restriktiokohtien lisääminen alukkeiden 5'-päähän ja restriktiokohtien eli DNA:ta tietyistä paikoista leikkaavien entsyymien leikkauskohtien etsiminen sekvenssistä [samaan malliin kuin NEBCutterilla.](http://nc2.neb.com/NEBcutter2/)
- PCR-koneen ajo-ohjelman suunnittelutoiminnallisuus, johon ohjelma määrittelee käytetyn polymeraasientsyymin, amplikonin pituuden, Ta-lämpötilan jne. ja suunnittelee optimaalisen reaktiosyklin.
- Alukkeiden suunnittelu muille PCR-ohjelmille, esimerkiksi qPCR.
