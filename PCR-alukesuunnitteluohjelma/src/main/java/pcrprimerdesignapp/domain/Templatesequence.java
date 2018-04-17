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

    public String headerLineFromFile(File file) {

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
            return sb.toString();
        }
        return null;
    }

    public String sequenceFromFile(File file) {

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

            //jaetaan sekvenssi 50 nukleotidin pätkiin tekstikenttään syöttämistä varten.
            String sequence = sb.toString();

            String splitSequence = "";

            int i = 0;
            while (i < sequence.length()) {
                splitSequence = splitSequence.concat(sequence.substring(i, Math.min(sequence.length(), i + 50)) + "\n");
                i += 50;
            }
            return splitSequence;
        }
        return null;
    }
}
