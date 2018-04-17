# **PCR-alukesuunnitteluohjelma**

Tämä on ohjelmistotekniikan menetelmät -kurssilla tehtävä harjoitustyö, Jossa toteutetaan [polymeraasiketjureaktiossa](https://fi.wikipedia.org/wiki/Polymeraasiketjureaktio) käytettävien alukkeiden suunnitteluun tarkoitettu ohjelma.

## Dokumentaatio

[Työaikakirjanpito](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Vaatimusmäärittely](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Karttune/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

### Checkstyle

Ohjelman checkstyle-tarkistukset suoritetaan komennolla:

```
mvn jxr:jxr checkstyle:checkstyle
```

Virheilmoitukset selviävät avaamalla tiedosto _target/site/checkstyle.html_


