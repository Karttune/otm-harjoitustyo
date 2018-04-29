/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pcrprimerdesignapp.ui.PcrprimerdesignApplication;

/**
 *
 * @author Konsta
 */
public class Templatesequence {

    private String sequenceTitle;
    private String templateSequence;
    private Integer id;
    private Integer forwardPrimerId;
    private Integer reversePrimerId;

    public Templatesequence() {
        sequenceTitle = "";
        templateSequence = "";
    }

    public void headerLineFromFile(File file) {

        //Luetaan tiedostosta pelkkä ">" -merkillä alkava otsikkorivi.
        if (file != null) {

            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String text;
                while ((text = bufferedReader.readLine()) != null) {

                    if (text.startsWith(">")) {
                        sb.append(text);
                    } else {
                        break;
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sequenceTitle = sb.toString();
        }
    }

    public void sequenceFromFile(File file) {

        //Luetaan sekvenssi tiedostosta ilman ">" -merkkiä alkavaa otsikkoriviä.
        if (file != null) {

            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String text;
                while ((text = bufferedReader.readLine()) != null) {

                    if (!text.startsWith(">")) {
                        sb.append(text);
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String sequence = sb.toString();
            sequence = sequence.replaceAll("\n", "");
            templateSequence = sequence;
        }
    }

    public String getTemplateSequence() {
        return templateSequence;
    }

    public void setTemplateSequence(String templateSequence) {
        this.templateSequence = templateSequence;
    }

    public String getSequenceTitle() {
        return sequenceTitle;
    }

    public void setSequenceTitle(String sequenceTitle) {
        this.sequenceTitle = sequenceTitle;
    }

    public String splitSequence() {

        //jaetaan sekvenssi 50 nukleotidin pätkiin tekstikenttään syöttämistä varten.
        String splitSequence = "";

        int i = 0;
        while (i < templateSequence.length()) {
            splitSequence = splitSequence.concat(templateSequence.substring(i, Math.min(templateSequence.length(), i + 50)) + "\n");
            i += 50;
        }
        return splitSequence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForwardPrimerId() {
        return forwardPrimerId;
    }

    public void setForwardPrimerId(Integer forwardPrimerId) {
        this.forwardPrimerId = forwardPrimerId;
    }

    public Integer getReversePrimerId() {
        return reversePrimerId;
    }

    public void setReversePrimerId(Integer reversePrimerId) {
        this.reversePrimerId = reversePrimerId;
    }
    
}
