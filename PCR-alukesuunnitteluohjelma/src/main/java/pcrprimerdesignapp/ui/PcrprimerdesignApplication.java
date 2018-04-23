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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

        Text f = new Text();
        Text r = new Text();

        TextFlow forwardSequenceAlignment = new TextFlow();
        forwardSequenceAlignment.setPrefSize(400, 50);

        TextFlow reverseSequenceAlignment = new TextFlow();
        reverseSequenceAlignment.setPrefSize(400, 50);

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

            newValue = newValue.replaceAll("\n", "");
            templateSequence.setTemplateSequence(newValue);

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

            if (templateSequence.getTemplateSequence().length() >= 100) {
                String fwdsequence = templateSequence.getTemplateSequence().substring(0, 50);
                f.setText(fwdsequence + "\n");

                String revsequence = templateSequence.getTemplateSequence().substring(templateSequence.getTemplateSequence().length() - 50, templateSequence.getTemplateSequence().length());
                revsequence = new StringBuilder(revsequence).reverse().toString();
                r.setText(revsequence + "\n");
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

            if (templateSequence.getTemplateSequence().length() >= 100) {
                String[] fwdsequence = templateSequence.getTemplateSequence().substring(0, 50).split("");
                String[] fwdprimer = forwardPrimer.getForwardPrimer().split("");

                String[] revprimer = reversePrimer.getReversePrimer().split("");
                String[] revsequence = templateSequence.getTemplateSequence().substring(templateSequence.getTemplateSequence().length() - 50, templateSequence.getTemplateSequence().length()).split("");

                forwardSequenceAlignment.getChildren().clear();
                forwardSequenceAlignment.getChildren().add(f);

                for (int i = 0; i < fwdprimer.length; i++) {

                    if (fwdprimer[i].equalsIgnoreCase(fwdsequence[i])) {
                        Text match = new Text(fwdprimer[i]);
                        match.setFill(Color.RED);
                        forwardSequenceAlignment.getChildren().add(match);
                    } else {
                        Text mismatch = new Text(fwdprimer[i]);
                        forwardSequenceAlignment.getChildren().add(mismatch);
                    }
                }

                reverseSequenceAlignment.getChildren().clear();
                reverseSequenceAlignment.getChildren().add(r);

                int y = 49;

                for (int i = 0; i < revprimer.length; i++) {
                    if (revprimer[i].equalsIgnoreCase("A") && revsequence[y].equalsIgnoreCase("T") || revprimer[i].equalsIgnoreCase("T") && revsequence[y].equalsIgnoreCase("A")) {
                        Text match = new Text(revprimer[i]);
                        match.setFill(Color.RED);
                        reverseSequenceAlignment.getChildren().add(match);
                    } else if (revprimer[i].equalsIgnoreCase("C") && revsequence[y].equalsIgnoreCase("G") || revprimer[i].equalsIgnoreCase("G") && revsequence[y].equalsIgnoreCase("C")) {
                        Text match = new Text(revprimer[i]);
                        match.setFill(Color.RED);
                        reverseSequenceAlignment.getChildren().add(match);
                    } else {
                        Text mismatch = new Text(revprimer[i]);
                        reverseSequenceAlignment.getChildren().add(mismatch);
                    }
                    y--;
                }
            }
        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            reversePrimer.setReversePrimer(newValue);

            reversePrimerLength.setText("Nucleotides: " + reversePrimer.getPrimerLength());
            reversePrimerMatches.setText("Matching nucleotides: " + reversePrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

            if (templateSequence.getTemplateSequence().length() >= 100) {

                String[] fwdsequence = templateSequence.getTemplateSequence().substring(0, 50).split("");
                String[] fwdprimer = forwardPrimer.getForwardPrimer().split("");

                String[] revprimer = reversePrimer.getReversePrimer().split("");
                String[] revsequence = templateSequence.getTemplateSequence().substring(templateSequence.getTemplateSequence().length() - 50, templateSequence.getTemplateSequence().length()).split("");

                forwardSequenceAlignment.getChildren().clear();
                forwardSequenceAlignment.getChildren().add(f);

                for (int i = 0; i < fwdprimer.length; i++) {

                    if (fwdprimer[i].equalsIgnoreCase(fwdsequence[i])) {
                        Text match = new Text(fwdprimer[i]);
                        match.setFill(Color.RED);
                        forwardSequenceAlignment.getChildren().add(match);
                    } else {
                        Text mismatch = new Text(fwdprimer[i]);
                        forwardSequenceAlignment.getChildren().add(mismatch);
                    }
                }

                reverseSequenceAlignment.getChildren().clear();
                reverseSequenceAlignment.getChildren().add(r);

                int y = 49;

                for (int i = 0; i < revprimer.length; i++) {

                    if (revprimer[i].equalsIgnoreCase("A") && revsequence[y].equalsIgnoreCase("T") || revprimer[i].equalsIgnoreCase("T") && revsequence[y].equalsIgnoreCase("A")) {
                        Text match = new Text(revprimer[i]);
                        match.setFill(Color.RED);
                        reverseSequenceAlignment.getChildren().add(match);
                    } else if (revprimer[i].equalsIgnoreCase("C") && revsequence[y].equalsIgnoreCase("G") || revprimer[i].equalsIgnoreCase("G") && revsequence[y].equalsIgnoreCase("C")) {
                        Text match = new Text(revprimer[i]);
                        match.setFill(Color.RED);
                        reverseSequenceAlignment.getChildren().add(match);
                    } else {
                        Text mismatch = new Text(revprimer[i]);
                        reverseSequenceAlignment.getChildren().add(mismatch);
                    }
                    y--;
                }
            }
        });

        HBox buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(5, 5, 5, 5));

        buttonsBox.setSpacing(10);
        buttonsBox.getChildren().add(openFile);
        buttonsBox.getChildren().add(headerField);

        HBox databaseFunctionBox = new HBox();

        databaseFunctionBox.setSpacing(10);
        databaseFunctionBox.getChildren().add(databaseFunctions);

        VBox sequenceAlignmentBox = new VBox();
        sequenceAlignmentBox.setPadding(new Insets(5, 5, 5, 5));
        sequenceAlignmentBox.getChildren().add(new Label("Sequence alignment:"));
        sequenceAlignmentBox.getChildren().add(new Label(""));
        sequenceAlignmentBox.getChildren().add(new Label("Forward-primer (5'->3'):"));
        sequenceAlignmentBox.getChildren().add(forwardSequenceAlignment);
        sequenceAlignmentBox.getChildren().add(new Label("Reverse-primer (5'->3'):"));
        sequenceAlignmentBox.getChildren().add(reverseSequenceAlignment);

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
        grid.add(new Label("5'"), 7, 2, 1, 1);
        grid.add(new Label("3'"), 12, 2, 1, 1);
        grid.add(reversePrimerLength, 8, 3);
        grid.add(reversePrimerMatches, 8, 4);
        grid.add(reversePrimerGc, 8, 5);
        grid.add(reversePrimerTm, 8, 6);

        layout.setTop(buttonsBox);
        layout.setCenter(textArea);
        layout.setRight(sequenceAlignmentBox);
        layout.setBottom(grid);

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
