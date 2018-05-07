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
public class Forwardprimer extends AbstractPrimerObject {
    
    public Forwardprimer() {
        super();
    }

    /**
     * Metodi palauttaa forward-alukkeen muodostamalla se sille annetulla
     * templaattisekvenssillä.
     *
     * @param templateSequence käyttäjän antama nukleotidisekvenssi
     *
     * @return oletusarvoinen 20 nukleotidin pituinen aluke, jos templaatti on
     * yli 100 nukleotidiä pitkä.
     */
    public String getForwardPrimer(String templateSequence) {
        
        if (templateSequence.length() >= 100) {
            String primerSequence = templateSequence.substring(0, 20).toUpperCase();
            primer = primerSequence;
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
            String[] template = templateSequence.substring(this.getStart(), this.getStart() + 50).split("");
            String[] primerSequence = primer.split("");
            
            int matches = 0;
            
            for (int i = 0; i < primerSequence.length; i++) {
                
                if (template[i].equalsIgnoreCase(primerSequence[i])) {
                    matches++;
                }
            }
            return matches;
        }
        return 0;
    }

    /**
     * Metodi palauttaa forward-alukkeen ja templaattisekvenssin täsmäävien
     * nukleotidien määrän ja palauttaa TextFlow-elementin, jossa täsmäävät
     * nukleotidit ovat mustalla ja ei-täsmäävät punaisella.
     *
     * @param templateSequence Käyttäjän antama templaattisekvenssi.
     * @param sequenceAlignment Käyttäjän antama TextFlow-elementti, johon
     * lisätään aluke värjättynä.
     *
     * @return TextFlow-elementti, jossa on aluke.
     */
    public TextFlow forwardPrimerAlignment(String templateSequence, TextFlow sequenceAlignment) {
        
        String[] fwdprimer = primer.split("");
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
}
