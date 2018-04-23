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
public class Forwardprimer {

    private String forwardPrimer;

    public Forwardprimer() {
        forwardPrimer = "";
    }

    public String getForwardPrimer(String templateSequence) {

        //Palautetaan oletusarvoinen 20 nukleotidin pituinen aluke.
        if (templateSequence.length() >= 100) {
            String primer = templateSequence.substring(0, 20).toUpperCase();
            forwardPrimer = primer;
            return forwardPrimer;
        } else {
            return "The template sequence is too short!";
        }
    }

    public Integer matchingNucleotides(String templateSequence) {

        String[] template = templateSequence.split("");
        String[] primer = forwardPrimer.split("");

        int matches = 0;

        for (int i = 0; i < primer.length; i++) {

            if (template[i].equalsIgnoreCase(primer[i])) {
                matches++;
            }
        }
        return matches;
    }

    public Double gcPercentage() {

        if (!forwardPrimer.equals("The template sequence is too short!")) {

            String[] nucleotides = forwardPrimer.split("");

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

        if (!forwardPrimer.equals("The template sequence is too short!")) {

            String[] nucleotides = forwardPrimer.split("");

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

        if (!forwardPrimer.equals("The template sequence is too short!")) {
            return forwardPrimer.length();
        } else {
            return 0;
        }
    }

    public void setForwardPrimer(String forwardPrimer) {
        this.forwardPrimer = forwardPrimer;
    }
}
