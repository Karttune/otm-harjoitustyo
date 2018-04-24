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

    public Reverseprimer() {
        reversePrimer = "";
    }

    public String getReversePrimer(String templateSequence) {

        templateSequence = templateSequence.replaceAll("\n", "");
        //Reverse-aluke kiinnittyy juosteen loppupäähän ja on komplementaarinen koodaavalle sekvenssille.
        //Tämän takia reverse-aluke täytyy muokata komplementaariseksi.
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
            return "The template sequence is too short!";
        }
    }

    public String getReversePrimer() {

        return reversePrimer;
    }

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

    public Double gcPercentage() {

        if (!reversePrimer.equals("The template sequence is too short!")) {

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

    public Integer tmTemperature() {

        if (!reversePrimer.equals("The template sequence is too short!")) {

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

    public Integer getPrimerLength() {

        if (!reversePrimer.equals("The template sequence is too short!")) {
            return reversePrimer.replace(" ", "").length();
        } else {
            return 0;
        }
    }

    public void setReversePrimer(String reversePrimer) {
        this.reversePrimer = reversePrimer;
    }

    public TextFlow reversePrimerAlignment(String templateSequence, String reversePrimer, TextFlow sequenceAlignment) {

        String[] revprimer = reversePrimer.split("");
        String[] revsequence = new StringBuilder(templateSequence).reverse().toString().split("");

        for (int i = 0; i < revprimer.length; i++) {
            
            if (revprimer[i].equalsIgnoreCase("A") && revsequence[i].equalsIgnoreCase("T") || revprimer[i].equalsIgnoreCase("T") && revsequence[i].equalsIgnoreCase("A")) {
                Text match = new Text(revprimer[i]);
                match.setFill(Color.RED);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else if (revprimer[i].equalsIgnoreCase("C") && revsequence[i].equalsIgnoreCase("G") || revprimer[i].equalsIgnoreCase("G") && revsequence[i].equalsIgnoreCase("C")) {
                Text match = new Text(revprimer[i]);
                match.setFill(Color.RED);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else {
                Text mismatch = new Text(revprimer[i]);
                mismatch.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(mismatch);
            }
        }
        return sequenceAlignment;
    }
}
