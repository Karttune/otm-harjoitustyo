/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcralukesuunnitteluohjelma.domain;

import java.io.File;
import pcralukesuunnitteluohjelma.ui.PcrprimerdesignApplication;

/**
 *
 * @author Konsta
 */
public class Reverseprimer {

    private Templatesequence templateSequence;

    public Reverseprimer() {
        templateSequence = new Templatesequence();
    }

    public String reversePrimerFromFile(File file) {

        String sequence = templateSequence.sequenceFromFile(file);
        sequence = sequence.replaceAll("\\n", "");

        //Reverse-aluke kiinnittyy juosteen loppupäähän ja on komplementaarinen koodaavalle sekvenssille.
        //Tämän takia reverse-aluke täytyy muokata komplementaariseksi.
        if (sequence.length() >= 100) {

            String primer = sequence.substring(sequence.length() - 20, sequence.length());
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
            return primer;

        } else {
            return "The template sequence is too short!";
        }
    }

    public String reversePrimerFromTextField() {

        String sequence = PcrprimerdesignApplication.textField.getText();
        sequence = sequence.replaceAll("\\n", "");

        if (sequence.length() >= 100) {
            String primer = sequence.substring(sequence.length() - 20, sequence.length());

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
            return primer;

        } else {
            return "The template sequence is too short!";
        }
    }

    public String gcPercentage() {

        String primer = reversePrimerFromTextField();

        if (!primer.equals("The template sequence is too short!")) {

            String[] nucleotides = primer.split("");
            double gc = 0;

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
                    gc++;
                }
            }

            Double gcPercentage = Math.floor((gc / nucleotides.length) * 100);
            return "GC-percentage: " + gcPercentage.toString();
        } else {
            return "GC-percentage: 0";
        }
    }

    public String tmTemperature() {

        String primer = reversePrimerFromTextField();

        if (!primer.equals("The template sequence is too short!")) {

            String[] nucleotides = primer.split("");

            int meltingTemperature = 0;

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
                    meltingTemperature += 4;
                } else if (nucleotides[i].matches("[ATat]")) {
                    meltingTemperature += 2;
                }
            }
            return "Tm: " + meltingTemperature + "°C";
        } else {
            return "Tm: 0";
        }
    }
}
