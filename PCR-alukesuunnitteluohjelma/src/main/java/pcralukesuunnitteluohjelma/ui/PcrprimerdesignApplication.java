/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcralukesuunnitteluohjelma.ui;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import pcralukesuunnitteluohjelma.domain.Forwardprimer;
import pcralukesuunnitteluohjelma.domain.Reverseprimer;
import pcralukesuunnitteluohjelma.domain.Templatesequence;

public class PcrprimerdesignApplication extends Application {

    private Templatesequence templateSequence = new Templatesequence();
    private Forwardprimer forwardPrimer = new Forwardprimer();
    private Reverseprimer reversePrimer = new Reverseprimer();
    public static TextArea textField;

    public PcrprimerdesignApplication() {
        this.textField = new TextArea();
    }

    @Override
    public void start(Stage stage) {

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FASTA files (*.fasta)", "*.fasta");
        fileChooser.getExtensionFilters().add(extFilter);

        stage.setTitle("PCR-primer design");

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 5, 5, 5));

        final Button openFile = new Button("Open file");
        Label headerField = new Label();
        TextField forwardPrimerField = new TextField();
        TextField reversePrimerField = new TextField();
        Label nucleotideLabel = new Label("Nucleotides: 0");

        Label forwardPrimerGc = new Label("GC-percentage: 0");
        Label reversePrimerGc = new Label("GC-percentage: 0");
        Label forwardPrimerTm = new Label("Tm: 0°C");
        Label reversePrimerTm = new Label("Tm: 0°C");

        ChoiceBox databaseFunctions = new ChoiceBox();

        openFile.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showOpenDialog(stage);

            headerField.setText(templateSequence.headerLineFromFile(file));
            textField.setText(templateSequence.sequenceFromFile(file));

            forwardPrimerField.setText(forwardPrimer.forwardPrimerFromFile(file));
            reversePrimerField.setText(reversePrimer.reversePrimerFromFile(file));
        });

        textField.textProperty().addListener((change, oldValue, newValue) -> {

            newValue = newValue.replaceAll("\n", "");

            if (newValue.matches("[ATCGatcg]*")) {
                int nucleotides = newValue.length();
                nucleotideLabel.setText("Nucleotides: " + nucleotides);
            } else {
                nucleotideLabel.setText("Invalid input!");
            }

            forwardPrimerField.setText(forwardPrimer.forwardPrimerFromTextField());
            reversePrimerField.setText(reversePrimer.reversePrimerFromTextField());
            forwardPrimerGc.setText(forwardPrimer.gcPercentage());
            reversePrimerGc.setText(reversePrimer.gcPercentage());

            forwardPrimerTm.setText(forwardPrimer.tmTemperature());
            reversePrimerTm.setText(reversePrimer.tmTemperature());

        });

        forwardPrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            forwardPrimerGc.setText(forwardPrimer.gcPercentage());
            reversePrimerGc.setText(reversePrimer.gcPercentage());

            forwardPrimerTm.setText(forwardPrimer.tmTemperature());
            reversePrimerTm.setText(reversePrimer.tmTemperature());

        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            forwardPrimerGc.setText(forwardPrimer.gcPercentage());
            reversePrimerGc.setText(reversePrimer.gcPercentage());

            forwardPrimerTm.setText(forwardPrimer.tmTemperature());
            reversePrimerTm.setText(reversePrimer.tmTemperature());

        });

        HBox buttons = new HBox();
        buttons.setPadding(new Insets(5, 5, 5, 5));

        HBox databaseFunctionBox = new HBox();

        buttons.setSpacing(10);
        buttons.getChildren().add(openFile);
        buttons.getChildren().add(headerField);

        databaseFunctionBox.setSpacing(10);
        databaseFunctionBox.getChildren().add(databaseFunctions);

        grid.add(nucleotideLabel, 1, 0, 5, 1);
        grid.add(new Label("Forward-primer:"), 1, 1);
        grid.add(new Label("Reverse-primer:"), 8, 1);

        grid.add(forwardPrimerField, 1, 2, 4, 1);
        grid.add(new Label("5'"), 0, 2, 1, 1);
        grid.add(new Label("3'"), 5, 2, 1, 1);
        grid.add(forwardPrimerGc, 1, 3);
        grid.add(forwardPrimerTm, 1, 4);

        grid.add(reversePrimerField, 8, 2, 4, 1);
        grid.add(new Label("3'"), 7, 2, 1, 1);
        grid.add(new Label("5'"), 12, 2, 1, 1);
        grid.add(reversePrimerGc, 8, 3);
        grid.add(reversePrimerTm, 8, 4);

        layout.setTop(buttons);
        layout.setCenter(textField);
        layout.setBottom(grid);

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
