/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.ui;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.Reverseprimer;
import pcrprimerdesignapp.domain.Templatesequence;

public class PcrprimerdesignApplication extends Application {

    private Templatesequence templateSequence = new Templatesequence();
    private Forwardprimer forwardPrimer = new Forwardprimer();
    private Reverseprimer reversePrimer = new Reverseprimer();
    public static TextArea textArea;

    public PcrprimerdesignApplication() {
        this.textArea = new TextArea();
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

        Label forwardPrimerLength = new Label("Nucleotides: 0");
        Label reversePrimerLength = new Label("Nucleotides: 0");

        Label forwardPrimerMatches = new Label("Matching nucleotides: 0");
        Label reversePrimerMatches = new Label("Matching nucleotides: 0");

        Label forwardPrimerGc = new Label("GC-percentage: 0");
        Label reversePrimerGc = new Label("GC-percentage: 0");

        Label forwardPrimerTm = new Label("Tm: 0°C");
        Label reversePrimerTm = new Label("Tm: 0°C");

        ChoiceBox databaseFunctions = new ChoiceBox();

        openFile.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showOpenDialog(stage);

            templateSequence.headerLineFromFile(file);
            headerField.setText(templateSequence.getSequenceTitle());

            templateSequence.sequenceFromFile(file);
            textArea.setText(templateSequence.splitSequence());

            forwardPrimerField.setText(forwardPrimer.getForwardPrimer(templateSequence.getTemplateSequence()));
            reversePrimerField.setText(reversePrimer.getReversePrimer(templateSequence.getTemplateSequence()));
        });

        textArea.textProperty().addListener((change, oldValue, newValue) -> {

            templateSequence.setTemplateSequence(newValue);

            newValue = newValue.replaceAll("\n", "");

            if (newValue.matches("[ATCGatcg]*")) {
                int nucleotides = newValue.length();
                nucleotideLabel.setText("Nucleotides: " + nucleotides);
            } else {
                nucleotideLabel.setText("Invalid input!");
            }

            forwardPrimerField.setText(forwardPrimer.getForwardPrimer(templateSequence.getTemplateSequence()));
            reversePrimerField.setText(reversePrimer.getReversePrimer(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

            if (!forwardPrimerField.getText().equals("The template sequence is too short!")) {
                String[] ayy = forwardPrimerField.getText().split("");
                String[] lmao = newValue.substring(0, ayy.length).split("");

                for (int i = 0; i < ayy.length; i++) {

                    if (ayy[i].equalsIgnoreCase(lmao[i])) {
                        Text t = new Text();
                        t.setFill(Color.GREEN);
                    }
                }
            }
        });

        forwardPrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            forwardPrimer.setForwardPrimer(newValue);

            forwardPrimerLength.setText("Nucleotides: " + forwardPrimer.getPrimerLength());

            forwardPrimerMatches.setText("Matching nucleotides: " + forwardPrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            reversePrimer.setReversePrimer(newValue);

            reversePrimerLength.setText("Nucleotides: " + reversePrimer.getPrimerLength());

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

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
        grid.add(forwardPrimerLength, 1, 3);
        grid.add(forwardPrimerMatches, 1, 4);
        grid.add(forwardPrimerGc, 1, 5);
        grid.add(forwardPrimerTm, 1, 6);

        grid.add(reversePrimerField, 8, 2, 4, 1);
        grid.add(new Label("3'"), 7, 2, 1, 1);
        grid.add(new Label("5'"), 12, 2, 1, 1);
        grid.add(reversePrimerLength, 8, 3);
        grid.add(reversePrimerGc, 8, 4);
        grid.add(reversePrimerTm, 8, 5);

        layout.setTop(buttons);
        layout.setCenter(textArea);
        layout.setBottom(grid);

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
