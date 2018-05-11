# Käyttöohje

Lataa tiedosto PcrPrimerDesignApplication.jar ja sequences.db.

## Konfiguraatio

Laita PcrPrimerDesignApplication.jar ja sequences.db samaan kansioon.

## Konfiguraatio

Ohjelma voidaan käynnistää komennolla:

...
java -jar PcrPrimerDesignApplication.jar
...

## Ikkuna

Ohjelma avautuu suoraan ikkunaan, jossa on kaikki toiminnallisuus. Alukkeiden muokkaamisen voi aloittaa syöttämällä tekstikenttään yli 100 nukleotidin pituinen koodaava (5'-3' suunnassa kirjoitettu) sekvenssi, avaamalla sekvenssi fasta-tiedostosta tai avaamalla sekvenssi tietokannasta. Tämän jälkeen alukkeille voi syöttää niiden aloituspisteet Starting nucleotide -kenttiin ja muokata alukkeita oman halun mukaan tekstikentistä. Alukekenttien alapuolella näkyvät alukespesifiset tiedot, kuten GC%, tm-lämpötila, ta-lämpötila sekä templaattisekvenssin kanssa täsmäävien nukleotidien määrä.

Sequencealignment-ikkunasta näet 50 nukleotidin pätkän templaattisekvenssiä, ja sen alussa PCR-alukkeen. Mustan väriset kirjaimet sekvenssissä ovat templaattiin täsmääviä, kun taas punaisella värjätyt eivät täsmää. Huom! Reverse-aluke kirjoitetaan 5'-3', jolloin templaattisekvenssi on Reverse-kentässä kirjoitettuna takaperin käännettynä eli 3'-5' -suunnassa.

Sequencealignment-ikkunan yläpuolella ovat tietokantatoiminnot. Drop-down -valikosta valitaan tietokannassa oleva sekvenssi, Load-napista sekvenssi ladataan ohjelmaan, Save-napilla kentissä oleva sekvenssi tallennetaan tietokantaan (vaatii otsikon kirjoittamisen viereiseen tekstikenttään) ja Delete-napista poistetaan valittuna oleva, tietokannassa oleva sekvenssi.

Sovellus tarkistaa alukkeiden suunnittelussa yleisimpiä virheitä. Sovellus varoittaa seuraavista suunnitteluvirheistä:

* Alukkeen GC-nukleotidien prosentuaalinen lukumäärä on alle 40% tai yli 60%.
* Alukkeiden Tm-lämpötila on alle 50°C tai yli 60°C, ja alukkeiden Tm-lämpötiloissa on yli 5°C ero.
* Alukkeissa on yli 4 nukleotidin pituisia yhden emäksen toistojaksoja.
* Alukkeissa on yli 4 nukleotidin pituisia palindromisia sekvenssejä; tämä tarkoittaa sitä, että alukkeessa on esim. ATCG -sekvenssi, jonka jälkeen tulee CGAT -sekvenssi, joka on ATCG-sekvenssille käänteisesti komplementaarinen. Näiden välillä voi tapahtua emäspariutumista, jolloin aluke voi muodostaa itsensä kanssa ns. hiuspinnirakenteen, joka heikentää PCR-reaktion onnistumista.
* Alukkeiden 3' pään kahden viimeisen emäksen täsmääminen templaattisekvenssiin; 3'-pään täytyy olla täysin komplementaarinen templaattiin, sillä muuten polymeraasientsyymi ei voi aloittaa elongaatiota.
* Alukkeiden 3' pään käänteiden komplementaarisuus, eli [primer dimerin](https://en.wikipedia.org/wiki/Primer_dimer) muodostuminen.
