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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeString.substring;
import pcralukesuunnitteluohjelma.domain.Fwdprimer;
import pcralukesuunnitteluohjelma.domain.Revprimer;
import pcralukesuunnitteluohjelma.domain.Templaattisekvenssi;

public class PcrAlukesuunnitteluApplication extends Application {

    private Templaattisekvenssi templaattisekvenssi = new Templaattisekvenssi();
    private Fwdprimer forwardaluke = new Fwdprimer();
    private Revprimer reversealuke = new Revprimer();

    @Override
    public void start(Stage ikkuna) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FASTA files (*.fasta)", "*.fasta");
        fileChooser.getExtensionFilters().add(extFilter);

        ikkuna.setTitle("PCR-alukesuunnitteluohjelma");

        BorderPane asettelu = new BorderPane();
        asettelu.setPadding(new Insets(10, 10, 10, 10));

        GridPane ruudukko = new GridPane();
        ruudukko.setHgap(10);
        ruudukko.setVgap(10);
        ruudukko.setPadding(new Insets(10, 10, 10, 10));

        final Button avaatiedosto = new Button("Avaa tiedosto");
        TextArea tekstikentta = new TextArea();
        Label otsikkokentta = new Label();
        TextField fwdaluke = new TextField();
        TextField revaluke = new TextField();

        ChoiceBox tietokantavalikko = new ChoiceBox();

        avaatiedosto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                File file = fileChooser.showOpenDialog(ikkuna);

                otsikkokentta.setText(templaattisekvenssi.otsikkoRivinPalautusTiedostosta(file));
                tekstikentta.setText(templaattisekvenssi.sekvenssiRivienPalautusTiedostosta(file));

                fwdaluke.setText(forwardaluke.fwdPrimerTiedostosta(file));
                revaluke.setText(reversealuke.revPrimerTiedostosta(file));

            }
        });

        HBox napit = new HBox();
        napit.setPadding(new Insets(5, 5, 5, 5));

        HBox tietokantatoiminnot = new HBox();

        napit.setSpacing(10);
        napit.getChildren().add(avaatiedosto);
        napit.getChildren().add(otsikkokentta);

        tietokantatoiminnot.setSpacing(10);
        tietokantatoiminnot.getChildren().add(tietokantavalikko);

        ruudukko.add(new Label("Forward-aluke:"), 0, 0);
        ruudukko.add(new Label("Reverse-aluke:"), 10, 0);
        ruudukko.add(fwdaluke, 0, 1);
        ruudukko.add(revaluke, 10, 1);

        asettelu.setTop(napit);
        asettelu.setCenter(tekstikentta);
        asettelu.setBottom(ruudukko);

        Scene nakyma = new Scene(asettelu);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
}
