/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

import java.io.File;
import pcrprimerdesignapp.ui.PcrprimerdesignApplication;

/**
 *
 * @author Konsta
 */
public class Forwardprimer {

    private Templatesequence templateSequence;

    public Forwardprimer() {
        templateSequence = new Templatesequence();
    }

    public String forwardPrimerFromFile(File file) {

        String sequence = templateSequence.sequenceFromFile(file);

        //Palautetaan oletusarvoinen 20 nukleotidin pituinen aluke.
        if (sequence.length() >= 100) {
            String primer = sequence.substring(0, 20).toUpperCase();
            return primer;
        } else {
            return "The template sequence is too short!";
        }
    }

    public String forwardPrimerFromTextField() {

        String sequence = PcrprimerdesignApplication.textField.getText();
        sequence = sequence.replaceAll("\\n", "");

        if (sequence.length() >= 100) {
            String primer = sequence.substring(0, 20).toUpperCase();
            return primer;
        } else {
            return "The template sequence is too short!";
        }
    }

    public String gcPercentage() {

        String primer = forwardPrimerFromTextField();

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

        String primer = forwardPrimerFromTextField();

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
