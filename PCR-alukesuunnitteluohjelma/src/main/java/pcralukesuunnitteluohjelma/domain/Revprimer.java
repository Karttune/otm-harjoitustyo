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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import pcralukesuunnitteluohjelma.ui.PcrAlukesuunnitteluApplication;

/**
 *
 * @author Konsta
 */
public class Revprimer {

    public Revprimer() {

    }

    public String revPrimerTiedostosta(File file) {

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

            //Reverse-alukkeesta kiinnittyy juosteen loppupäähän ja on komplementaarinen koodaavalle sekvenssille.
            //Tämän takia reverse-aluke täytyy muokata komplementaariseksi.
            
            String primer = sekvenssi.substring(sekvenssi.length() - 20, sekvenssi.length());

            List<Character> charList = new ArrayList<Character>();

            for (char c : primer.toCharArray()) {
                charList.add(c);
            }

            for (int i = 0; i < charList.size(); i++) {

                if (charList.get(i).equals('A')) {
                    charList.set(i, new Character('T'));

                } else if (charList.get(i).equals('T')) {
                    charList.set(i, new Character('A'));

                } else if (charList.get(i).equals('C')) {
                    charList.set(i, new Character('G'));

                } else if (charList.get(i).equals('G')) {
                    charList.set(i, new Character('C'));
                } else {
                    continue;
                }
            }

            String reversealuke = charList.stream().map(e -> e.toString()).collect(Collectors.joining());

            return reversealuke;
        }

        return null;
    }
}
