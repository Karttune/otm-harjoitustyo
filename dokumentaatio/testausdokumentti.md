# Testausdokumentti

Ohjelman testaus perustuu JUnit- yksikkö- ja integraatiotesteihin sekä manuaaliseen testaamiseen.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Testit testaavat domain-pakkauksen luokkia, eli Forwardprimer-, PrimerDesignChecks-, Reverseprimer- ja Templatesequence -luokkia. Forwardprimer- ja Reverseprimer-luokkien toteuttamalle abstraktille luokalle AbstractPrimerObject ei ole kirjoitettu omia testejä, vaan se testataan tarkoituksenmukaisemmin Forwardprimer- ja Reverseprimer-toteutuksen kautta. Testin testaavat tilanteita, joiden syöte on mahdollista antaa UI:n kautta.

### DAO-luokat

DAO-luokkien testit toimivat niille määriteltyn test.db -tietokantatiedoston avulla. Myös Dao-luokissa abstraktia AbstractPrimerDao-luokkaa testataan sen toteutuksien kautta.

### Testauskattavuus

<img src="https://raw.githubusercontent.com/Karttune/otm-harjoitustyo/master/dokumentaatio/testikattavuus.png">

Testaamatta jäivät Forwardprimer- ja Reverseprimer-luokkien forwardPrimerAlignment -metodit.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on testattu käyttöohjeen mukaisin ohjein Windows-käyttöjärjestelmällä. Sovellusta on testattu myös ilman sequences.db -tiedostoa samassa kansiossa kuin .jar -tiedostoa, jolloin sovellus luo tiedoston itse.

### Toiminnallisuudet

Vaatimusmäärittelyn mukainen toiminnallisuus on testattu UI:n kautta myös virheellisien syötteiden kanssa. Sovellus estää suurimman osan virheellisistä syötteistä jo UI:n puolella, esimerkiksi sekvessikenttiin voi kirjoittaa vain nukleotidisekvenssiä (ATCG/atcg) ja starting nucleotide -kenttiin vain numeroita (0-9). Tämä varmistaa, että sovelluslogiikan puolelle syötetään vain oikein muotoisia syötteitä.

## Sovellukseen jääneet laatuongelmat

Jos avaa tiedostonavausikkunan mutta sulkee sen avaamatta tiedostoa, sovellus printtaa errorin.
