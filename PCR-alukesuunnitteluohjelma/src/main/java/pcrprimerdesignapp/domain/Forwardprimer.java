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
public class Forwardprimer {

    private String forwardPrimer;
    private Integer id;

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

    public String getForwardPrimer() {

        return forwardPrimer;

    }

    public Integer matchingNucleotides(String templateSequence) {

        if (templateSequence.length() >= 100) {
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
        return 0;
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
            return forwardPrimer.replace(" ", "").length();
        } else {
            return 0;
        }
    }

    public void setForwardPrimer(String forwardPrimer) {
        this.forwardPrimer = forwardPrimer;
    }

    public TextFlow forwardPrimerAlignment(String templateSequence, String forwardPrimer, TextFlow sequenceAlignment) {

        String[] fwdprimer = forwardPrimer.split("");
        String[] fwdsequence = templateSequence.split("");

        for (int i = 0; i < fwdprimer.length; i++) {

            if (fwdprimer[i].equalsIgnoreCase(fwdsequence[i])) {
                Text match = new Text(fwdprimer[i]);
                match.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(match);
            } else {
                Text mismatch = new Text(fwdprimer[i]);
                mismatch.setFill(Color.RED);
                mismatch.setFont(Font.font("Courier New"));
                sequenceAlignment.getChildren().add(mismatch);
            }
        }
        return sequenceAlignment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
