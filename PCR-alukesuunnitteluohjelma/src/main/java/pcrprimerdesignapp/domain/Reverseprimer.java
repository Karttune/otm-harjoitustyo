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
        
        templateSequence = templateSequence.replaceAll("\n", "");
        if (templateSequence.length() >= 100) {
            
            String[] nucleotides = templateSequence.substring(templateSequence.length() - 20, templateSequence.length()).split("");
            
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
            String revprimer = String.join("", nucleotides);
            primer = new StringBuilder(revprimer).reverse().toString();
            return primer;
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
            
            String[] template = new StringBuilder(templateSequence.substring(0, this.getStart())).reverse().toString().split("");
            String[] primerSequence = primer.split("");
            
            int matches = 0;
            
            for (int i = 0; i < primerSequence.length; i++) {
                
                if (primerSequence[i].equalsIgnoreCase("A") && template[i].equalsIgnoreCase("T") || primerSequence[i].equalsIgnoreCase("T") && template[i].equalsIgnoreCase("A")) {
                    matches++;
                } else if (primerSequence[i].equalsIgnoreCase("C") && template[i].equalsIgnoreCase("G") || primerSequence[i].equalsIgnoreCase("G") && template[i].equalsIgnoreCase("C")) {
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
        
        String[] revprimer = primer.split("");
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
}
