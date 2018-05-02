/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Konsta
 */
public class Reverseprimer {

    private String reversePrimer;
    private Integer id;
    private Integer start;

    public Reverseprimer() {
        reversePrimer = "";
        start = 0;
    }

    /**
     * Metodi palauttaa reverse-alukkeen muodostamalla se sille annetulla
     * templaattisekvenssillä ja kääntämällä sen komplementaariseksi.
     *
     * @param templateSequence käyttäjän antama nukleotidisekvenssi
     *
     * @return oletusarvoinen 20 nukleotidin pituinen aluke, jos templaatti on
     * yli 100 nukleotidiä pitkä.
     */
    public String getReversePrimer(String templateSequence) {

        templateSequence = templateSequence.replaceAll("\n", "");
        if (templateSequence.length() >= 100) {

            String primer = templateSequence.substring(templateSequence.length() - 20, templateSequence.length());
            String[] nucleotides = primer.split("");

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].equalsIgnoreCase("A")) {
                    nucleotides[i] = "T";
                } else if (nucleotides[i].equalsIgnoreCase("T")) {
                    nucleotides[i] = "A";
                } else if (nucleotides[i].equalsIgnoreCase("C")) {
                    nucleotides[i] = "G";
                } else if (nucleotides[i].equalsIgnoreCase("G")) {
                    nucleotides[i] = "C";
                }
            }
            primer = String.join("", nucleotides);
            primer = new StringBuilder(primer).reverse().toString();
            reversePrimer = primer;
            return reversePrimer;
        } else {
            return "";
        }
    }

    /**
     * Metodi palauttaa templaattisekvenssin ja alukkeen toisiinsa täsmäävien
     * nukleotidien määrän.
     *
     * @param templateSequence käyttäjän antama nukleotidisekvenssi
     *
     * @return palauttaa täsmäävien nukleotidien määrän
     */
    public Integer matchingNucleotides(String templateSequence) {

        if (templateSequence.length() >= 100) {

            String[] template = templateSequence.substring(templateSequence.length() - 50, templateSequence.length()).split("");
            String[] primer = reversePrimer.split("");

            int matches = 0;
            int y = 49;

            for (int i = 0; i < primer.length; i++) {

                if (primer[i].equalsIgnoreCase("A") && template[y].equalsIgnoreCase("T") || primer[i].equalsIgnoreCase("T") && template[y].equalsIgnoreCase("A")) {
                    matches++;
                } else if (primer[i].equalsIgnoreCase("C") && template[y].equalsIgnoreCase("G") || primer[i].equalsIgnoreCase("G") && template[y].equalsIgnoreCase("C")) {
                    matches++;
                }
                y--;
            }
            return matches;
        }
        return 0;
    }

    /**
     * Metodi palauttaa alukkeen G ja C -nukleotidien määrän prosentuaalisena
     * osuutena.
     *
     * @return palauttaa alukkeen GC% liukulukuna.
     */
    public Double gcPercentage() {

        if (!reversePrimer.equals("")) {

            String[] nucleotides = reversePrimer.split("");
            double gc = 0;

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
                    gc++;
                }
            }

            Double gcPercentage = Math.floor((gc / nucleotides.length) * 100);
            return gcPercentage;
        } else {
            return 0.0;
        }
    }

    /**
     * Metodi palauttaa alukkeen tm-lämpötilan celsiusasteina.
     *
     * @return palauttaa alukkeen tm-lämpötilan kokonaislukuna.
     */
    public Integer tmTemperature() {

        if (!reversePrimer.equals("")) {

            String[] nucleotides = reversePrimer.split("");

            int meltingTemperature = 0;

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
                    meltingTemperature += 4;
                } else if (nucleotides[i].matches("[ATat]")) {
                    meltingTemperature += 2;
                }
            }
            return meltingTemperature;
        } else {
            return 0;
        }
    }

    /**
     * Metodi palauttaa reverse-alukkeen ja templaattisekvenssin täsmäävien
     * nukleotidien määrän ja palauttaa TextFlow-elementin, jossa täsmäävät
     * nukleotidit ovat mustalla ja ei-täsmäävät punaisella.
     *
     * @param templateSequence Käyttäjän antama templaattisekvenssi.
     * @param reversePrimer Käyttäjän antama aluke.
     * @param sequenceAlignment Käyttäjän antama TextFlow-elementti, johon
     * lisätään aluke värjättynä.
     *
     * @return TextFlow-elementti, jossa on aluke.
     */
    public TextFlow reversePrimerAlignment(String templateSequence, String reversePrimer, TextFlow sequenceAlignment) {

        String[] revprimer = reversePrimer.split("");
        String[] revsequence = new StringBuilder(templateSequence).reverse().toString().split("");

        for (int i = 0; i < revprimer.length; i++) {

            if (revprimer[i].equalsIgnoreCase("A") && revsequence[i].equalsIgnoreCase("T") || revprimer[i].equalsIgnoreCase("T") && revsequence[i].equalsIgnoreCase("A")) {
                Text match = new Text(revprimer[i]);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else if (revprimer[i].equalsIgnoreCase("C") && revsequence[i].equalsIgnoreCase("G") || revprimer[i].equalsIgnoreCase("G") && revsequence[i].equalsIgnoreCase("C")) {
                Text match = new Text(revprimer[i]);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else {
                Text mismatch = new Text(revprimer[i]);
                mismatch.setFill(Color.RED);
                mismatch.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(mismatch);
            }
        }
        return sequenceAlignment;
    }

    public String getReversePrimer() {
        return reversePrimer;
    }

    public void setReversePrimer(String reversePrimer) {
        this.reversePrimer = reversePrimer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
