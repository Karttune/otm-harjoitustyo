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
public class Reverseprimer extends AbstractPrimerObject {

    public Reverseprimer() {
        super();
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

        if (templateSequence.length() >= 100) {

            String[] nucleotides = templateSequence.replaceAll("\n", "").substring(templateSequence.length() - 20, templateSequence.length()).split("");

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
            primer = new StringBuilder(String.join("", nucleotides)).reverse().toString();
            return primer;
        }
        return "";
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

            String[] template = new StringBuilder(templateSequence.substring(0, this.getStart())).reverse().toString().split("");
            String[] primerSequence = primer.split("");

            int matches = 0;

            for (int i = 0; i < primerSequence.length; i++) {

                if (primerSequence[i].equalsIgnoreCase("A") && template[i].equalsIgnoreCase("T") || primerSequence[i].equalsIgnoreCase("T") && template[i].equalsIgnoreCase("A") || primerSequence[i].equalsIgnoreCase("C") && template[i].equalsIgnoreCase("G") || primerSequence[i].equalsIgnoreCase("G") && template[i].equalsIgnoreCase("C")) {
                    matches++;
                }
            }
            return matches;
        }
        return 0;
    }

    /**
     * Metodi palauttaa reverse-alukkeen ja templaattisekvenssin täsmäävien
     * nukleotidien määrän ja palauttaa TextFlow-elementin, jossa täsmäävät
     * nukleotidit ovat mustalla ja ei-täsmäävät punaisella.
     *
     * @param templateSequence Käyttäjän antama templaattisekvenssi.
     * @param sequenceAlignment Käyttäjän antama TextFlow-elementti, johon
     * lisätään aluke värjättynä.
     *
     * @return TextFlow-elementti, jossa on aluke.
     */
    public TextFlow reversePrimerAlignment(String templateSequence, TextFlow sequenceAlignment) {

        String[] revPrimer = primer.split("");
        String[] revSequence = new StringBuilder(templateSequence).reverse().toString().split("");

        for (int i = 0; i < revPrimer.length; i++) {

            if (revPrimer[i].equalsIgnoreCase("A") && revSequence[i].equalsIgnoreCase("T") || revPrimer[i].equalsIgnoreCase("T") && revSequence[i].equalsIgnoreCase("A") || revPrimer[i].equalsIgnoreCase("C") && revSequence[i].equalsIgnoreCase("G") || revPrimer[i].equalsIgnoreCase("G") && revSequence[i].equalsIgnoreCase("C")) {
                Text match = new Text(revPrimer[i]);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else {
                Text mismatch = new Text(revPrimer[i]);
                mismatch.setFill(Color.RED);
                mismatch.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(mismatch);
            }
        }
        return sequenceAlignment;
    }
}
