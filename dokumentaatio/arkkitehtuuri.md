# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on seuraava: ui sisältää käyttöliittymän toteutuksen, domain sisältää sovelluslogiikan ja dao sisältää tietokantatoiminnallisuuden.

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/pakkausrakenne.jpg">

## Sovelluslogiikka

Ohjelman luokkarakenne on seuraava:

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/pakkausjaluokkakaavio.png">

Sovelluksen loogisesta toiminnasta vastaa Templatesequence-, Forwardprimer- ja Reverseprimer-luokat. 

Templatesequence vastaa templaattisekvenssin tallennuksesta, fasta-tiedostojen sekvenssien käsittelystä ja tallentamisesta luokan muuttujiin.

AbstractPrimerObject on abstrakti luokka, joka vastaa sitä toteuttavien Forward- ja Reverseprimer -luokkien yhteisistä metodeista, kuten gettereistä ja settereistä.

Forwardprimer vastaa forward-alukkeen käsittelystä, alukkeen tallentamisesta ja siihen kuuluvien metodien, kuten sekvenssien linjaustoiminnon käsittelystä.

Reverseprimer-luokan toiminnallisuus on pitkälti samanlainen kuin Forwardprimer-luokassa, mutta reverse-alukkeen käsittely vaatii hieman erilaisen toiminnallisuuden tiettyjen metodien osalta.

PrimerDesignChecks-luokka vastaa alukkeisiin liittyvistä tarkistuksista ja palauttaa varoitukset suunnitteluvirheistä.

UI-luokka vastaa tekstikenttien toiminnallisuudesta varsinkin siistimällä käyttäjäsyötettä: DNA-sekvenssi sisältää vain 4 eri emästä, A, T, C ja G, joten syöte täytyy rajoittaa näihin kirjaimiin. Kaikki syöte on merkkikoosta riippumatonta.

### Sekvenssikaavio

Sekvenssikaavio kuvaa tekstikenttään DNA-sekvenssin kirjoittamista.

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/Sekvenssikaavio.png">

## Tietokantatoiminnallisuus

AbstractPrimerDao, ForwardprimerDao, ReverseprimerDao sekä TemplatesequenceDao -luokat vastaavat tietojen talletuksesta SQL-tietokantaan. Luokat noudattavat Data Access Object -suunnittelumallia.

ForwardprimerDao ja ReverseprimerDao -luokat toteuttavat abstraktin AbstractPrimerDao -luokan, jossa on varsinainen DAO-toiminnallisuus, sillä molemmat tietokantataulut ovat rakenteeltaan samanlaisia. ForwardprimerDao ja ReverseprimerDao -luokat luovat siis vain varsinaisen Forwardprimer tai Reverseprimer-luokan tietokantaa käyttäessä.

Sovellus tallettaa sekvenssit kolmeen tietokantatauluun joiden rakenne on kuvattu seuraavassa kuvassa:

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/tietokantakaavio.jpg">
