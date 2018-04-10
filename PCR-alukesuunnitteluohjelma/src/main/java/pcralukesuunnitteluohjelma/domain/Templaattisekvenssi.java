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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pcralukesuunnitteluohjelma.ui.PcrAlukesuunnitteluApplication;

/**
 *
 * @author Konsta
 */
public class Templaattisekvenssi {

    public Templaattisekvenssi() {

    }

    public String otsikkoRivinPalautusTiedostosta(File file) {

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
            return sb.toString();
        }
        return null;
    }

    public String SekvenssiRivienPalautusTiedostosta(File file) {

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

            //jaetaan sekvenssi 50 nukleotidin pätkiin
            String sekvenssi = sb.toString();

            String sekvenssipatkat = "";

            int i = 0;
            while (i < sekvenssi.length()) {
                sekvenssipatkat = sekvenssipatkat.concat(i + 1 + " " + sekvenssi.substring(i, Math.min(i + 50, sekvenssi.length())) + "\n");
                i += 50;
            }
            return sekvenssipatkat;
        }
        return null;
    }
}
