# Arkkitehtuurikuvaus

## Rakenne

Ohjelman alustava pakkausrakenne on seuraava:

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/pakkausrakenne.jpg">

Ohjelman luokkarakenne on seuraava:

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/luokkajapakkauskaavio.jpg">

Pari huomiota luokka/pakkauskaaviosta:

Tällä hetkellä yksittäiset luokat eivät ole varsinaisesti yhden templaattisekvenssin tai alukkeen ilmentymiä, vaan niissä on vain toiminnallisuus UI:n tekstikentässä olevien sekvenssien muokkaamiseen. Tämä todennäköisesti muuttuu, jolloin yksittäiseen luokkaan liittyy aina muokattava sekvenssi. Tämä mahdollistaa esimerkiksi myös useamman alukkeen suunnittelun yhdelle templaattisekvenssille.

Sen lisäksi tietokantatoiminnallisuutta ei ole vielä toteutettu, joten sen luokkarakenne voi muuttua.

### Sekvenssikaavio

Sekvenssikaavio kuvaa tekstikenttään sekvenssin kirjoittamista.

<img src="https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/sekvenssikaavio.png">
