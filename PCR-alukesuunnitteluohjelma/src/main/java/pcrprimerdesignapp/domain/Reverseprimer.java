/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

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
            reversePrimer = primer;
            return reversePrimer;

        } else {
            return "The template sequence is too short!";
        }
    }

    public Integer matchingNucleotides(String templateSequence) {

        String[] template = templateSequence.split("");
        String[] primer = reversePrimer.split("");

        int matches = 0;

        for (int i = 0; i < primer.length; i++) {

            if (template[i].equalsIgnoreCase(primer[i])) {
                matches++;
            }
        }
        return matches;
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
            return reversePrimer.length();
        } else {
            return 0;
        }
    }

    public void setReversePrimer(String reversePrimer) {
        this.reversePrimer = reversePrimer;
    }
}
