/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcralukesuunnitteluohjelma.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pcralukesuunnitteluohjelma.ui.PcrAlukesuunnitteluApplication;

/**
 *
 * @author Konsta
 */
public class Fwdprimer {

    private String sekvenssi;

    public Fwdprimer() {

    }

    public String fwdPrimerTiedostosta(File file) {

        if (file != null) {

            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
                String text;
                while ((text = bufferedReader.readLine()) != null) {

                    if (text.startsWith(">")) {
                    } else {
                        sb.append(text);
                    }
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(PcrAlukesuunnitteluApplication.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PcrAlukesuunnitteluApplication.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(PcrAlukesuunnitteluApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Palautetaan 20 nukleotidin pituinen aluke.
            String sekvenssi = sb.toString();

            String primer = sekvenssi.substring(0, 20);

            return primer;
        }
        return null;
    }
}
