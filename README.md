# **PCR-alukesuunnitteluohjelma**

Tämä on ohjelmistotekniikan menetelmät -kurssilla tehtävä harjoitustyö, jossa toteutetaan [polymeraasiketjureaktiossa](https://fi.wikipedia.org/wiki/Polymeraasiketjureaktio) käytettävien alukkeiden suunnitteluun tarkoitettu ohjelma.

## Dokumentaatio

[Työaikakirjanpito](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Vaatimusmäärittely](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testausdokumentti](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)

[Viimeinen release](https://github.com/Karttune/otm-harjoitustyo/releases/tag/final)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla:

```
mvn test
```

Testikattavuusraportti luodaan komennolla:

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Suoritetaan komennolla:

```
mvn package
```

### Checkstyle

Ohjelman checkstyle-tarkistukset suoritetaan komennolla:

```
mvn jxr:jxr checkstyle:checkstyle
```

Virheilmoitukset selviävät avaamalla tiedosto _target/site/checkstyle.html_

### Javadoc

Javadoc-dokumentti luodaan komennolla:

```
mvn javadoc:javadoc
```
Dokumentti löytyy kansiosta _target\site\apidocs_

