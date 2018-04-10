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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.substring;
import pcralukesuunnitteluohjelma.domain.Templaattisekvenssi;

public class PcrAlukesuunnitteluApplication extends Application {

    private Desktop desktop = Desktop.getDesktop();
    private Templaattisekvenssi templaattisekvenssi;

    @Override
    public void start(Stage ikkuna) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FASTA files (*.fasta)", "*.fasta");
        fileChooser.getExtensionFilters().add(extFilter);

        ikkuna.setTitle("PCR-alukesuunnitteluohjelma");

        BorderPane asettelu = new BorderPane();

        final Button avaatiedosto = new Button("Avaa tiedosto");
        TextArea tekstikentta = new TextArea();
        Label otsikkokentta = new Label();

        ChoiceBox tietokantavalikko = new ChoiceBox();

        Label fwdprimer = new Label("Forward primer");
        Label revprimer = new Label("Reverse primer");

        avaatiedosto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = fileChooser.showOpenDialog(ikkuna);

                otsikkokentta.setText(templaattisekvenssi.otsikkoRivinPalautusTiedostosta(file));
                tekstikentta.setText(templaattisekvenssi.SekvenssiRivienPalautusTiedostosta(file));
            }
        });

        HBox napit = new HBox();
        HBox tietokantatoiminnot = new HBox();
        HBox alukeotsikot = new HBox();
        HBox alukkeet = new HBox();

        napit.setSpacing(10);
        napit.getChildren().add(avaatiedosto);
        napit.getChildren().add(otsikkokentta);

        tietokantatoiminnot.setSpacing(10);
        tietokantatoiminnot.getChildren().add(tietokantavalikko);

        alukeotsikot.setSpacing(50);
        alukeotsikot.getChildren().add(fwdprimer);
        alukeotsikot.getChildren().add(revprimer);

        asettelu.setTop(napit);
        asettelu.setCenter(tekstikentta);
        asettelu.setBottom(tietokantatoiminnot);
        asettelu.setBottom(alukeotsikot);
        asettelu.setBottom(alukkeet);

        Scene nakyma = new Scene(asettelu);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
}
