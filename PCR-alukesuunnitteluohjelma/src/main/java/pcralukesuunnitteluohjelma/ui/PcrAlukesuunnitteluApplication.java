/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcralukesuunnitteluohjelma.ui;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PcrAlukesuunnitteluApplication extends Application {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage ikkuna) {

        ikkuna.setTitle("PCR-alukesuunnitteluohjelma");

        BorderPane asettelu = new BorderPane();

        final Button openButton = new Button("Avaa tiedosto");
        TextArea tekstikentta = new TextArea();

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {

            public void handle(final ActionEvent e) {
                FileChooser fileChooser = new FileChooser();

                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FASTA files (*.fasta)", "*.fasta");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = fileChooser.showOpenDialog(ikkuna);
                if (file != null) {
                    tekstikentta.setText(readFile(file));
                }
            }

        });

        HBox napit = new HBox();
        napit.setSpacing(10);
        napit.getChildren().add(openButton);

        asettelu.setTop(napit);
        asettelu.setCenter(tekstikentta);

        Scene nakyma = new Scene(asettelu);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }

    private String readFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
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

        return stringBuffer.toString();
    }
}
