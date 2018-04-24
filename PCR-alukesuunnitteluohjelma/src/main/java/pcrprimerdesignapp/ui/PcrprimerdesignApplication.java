/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.ui;

import java.io.File;
import java.util.function.UnaryOperator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

        UnaryOperator<Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[ATCGatcg\n]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatterArea = new TextFormatter<>(filter);
        TextFormatter<String> textFormatterFwd = new TextFormatter<>(filter);
        TextFormatter<String> textFormatterRev = new TextFormatter<>(filter);

        UnaryOperator<Change> filterInt = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };

        TextFormatter<String> textFormatterStartFwd = new TextFormatter<>(filterInt);
        TextFormatter<String> textFormatterStartRev = new TextFormatter<>(filterInt);

        textArea.setFont(Font.font("Courier New"));
        textArea.setWrapText(true);
        textArea.setTextFormatter(textFormatterArea);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 5, 5, 5));

        final Button openFile = new Button("Open file");
        Label headerField = new Label("");

        if (templateSequence.getTemplateSequence().length() <= 100) {
            headerField.setText("Enter a nucleotide sequence (<100 nucleotides)");
        } else {
            headerField.setText("");

        }

        TextField forwardPrimerField = new TextField();
        forwardPrimerField.setFont(Font.font("Courier New"));
        forwardPrimerField.setTextFormatter(textFormatterFwd);

        TextField reversePrimerField = new TextField();
        reversePrimerField.setFont(Font.font("Courier New"));
        reversePrimerField.setTextFormatter(textFormatterRev);

        TextField setForwardPrimerStart = new TextField();
        setForwardPrimerStart.setTextFormatter(textFormatterStartFwd);
        setForwardPrimerStart.setText("0");

        TextField setReversePrimerStart = new TextField();
        setReversePrimerStart.setTextFormatter(textFormatterStartRev);

        Label nucleotideLabel = new Label("Nucleotides: 0");

        Label forwardPrimerLength = new Label("Nucleotides: 0");
        Label reversePrimerLength = new Label("Nucleotides: 0");

        Label forwardPrimerMatches = new Label("Matching nucleotides: 0");
        Label reversePrimerMatches = new Label("Matching nucleotides: 0");

        Label forwardPrimerGc = new Label("GC-percentage: 0");
        Label reversePrimerGc = new Label("GC-percentage: 0");

        Label forwardPrimerTm = new Label("Tm: 0°C");
        Label reversePrimerTm = new Label("Tm: 0°C");

        Label alignmentForwardPrimer = new Label("Forward primer: (0-0)");
        Label alignmentReversePrimer = new Label("Reverse primer: (0-0)");

        Text f = new Text();
        f.setFont(Font.font("Courier New"));
        Text r = new Text();
        r.setFont(Font.font("Courier New"));

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

            setForwardPrimerStart.setText("0");
            setReversePrimerStart.setText(Integer.toString(templateSequence.getTemplateSequence().length()));

        });

        textArea.textProperty().addListener((change, oldValue, newValue) -> {

            newValue = newValue.replaceAll("\n", "");
            templateSequence.setTemplateSequence(newValue);

            int nucleotides = newValue.length();
            nucleotideLabel.setText("Nucleotides: " + nucleotides);

            forwardPrimerField.setText(forwardPrimer.getForwardPrimer(templateSequence.getTemplateSequence()));
            reversePrimerField.setText(reversePrimer.getReversePrimer(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

        });

        forwardPrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            forwardPrimer.setForwardPrimer(newValue);

            forwardPrimerLength.setText("Nucleotides: " + forwardPrimer.getPrimerLength());
            forwardPrimerMatches.setText("Matching nucleotides: " + forwardPrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");

            if (templateSequence.getTemplateSequence().length() >= 100) {

                String fwdsequence = templateSequence.getTemplateSequence();
                f.setText(fwdsequence.substring(Integer.parseInt(setForwardPrimerStart.getText()), Integer.parseInt(setForwardPrimerStart.getText()) + 50) + "\n");

                forwardSequenceAlignment.getChildren().clear();
                forwardSequenceAlignment.getChildren().add(f);

                forwardPrimer.forwardPrimerAlignment(templateSequence.getTemplateSequence().substring(Integer.parseInt(setForwardPrimerStart.getText()), templateSequence.getTemplateSequence().length()), forwardPrimer.getForwardPrimer(), forwardSequenceAlignment);

            } else {
                forwardSequenceAlignment.getChildren().clear();
                forwardPrimerField.clear();
            }
        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            reversePrimer.setReversePrimer(newValue);

            reversePrimerLength.setText("Nucleotides: " + reversePrimer.getPrimerLength());
            reversePrimerMatches.setText("Matching nucleotides: " + reversePrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

            if (templateSequence.getTemplateSequence().length() >= 100) {

                String revsequence = new StringBuilder(templateSequence.getTemplateSequence()).reverse().toString();
                r.setText(revsequence.substring(Integer.parseInt(setForwardPrimerStart.getText()), Integer.parseInt(setForwardPrimerStart.getText()) + 50) + "\n");

                reverseSequenceAlignment.getChildren().clear();
                reverseSequenceAlignment.getChildren().add(r);

                reversePrimer.reversePrimerAlignment(templateSequence.getTemplateSequence(), reversePrimer.getReversePrimer(), reverseSequenceAlignment);
            } else {
                reverseSequenceAlignment.getChildren().clear();
                reversePrimerField.clear();
            }
        });

        setForwardPrimerStart.textProperty().addListener((change, oldValue, newValue) -> {

            try {
                if (Integer.parseInt(newValue) > templateSequence.getTemplateSequence().length()) {
                    setForwardPrimerStart.setText(oldValue);
                }

                String fwdsequence = templateSequence.getTemplateSequence();
                forwardPrimerField.setText(forwardPrimer.getForwardPrimer(fwdsequence.substring(Integer.parseInt(setForwardPrimerStart.getText()), fwdsequence.length())));

                if (fwdsequence.length() >= 100) {

                    f.setText(fwdsequence.substring(Integer.parseInt(setForwardPrimerStart.getText()), Integer.parseInt(setForwardPrimerStart.getText()) + 50) + "\n");

                    forwardSequenceAlignment.getChildren().clear();
                    forwardSequenceAlignment.getChildren().add(f);

                    forwardPrimer.forwardPrimerAlignment(fwdsequence.substring(Integer.parseInt(setForwardPrimerStart.getText()), fwdsequence.length()), forwardPrimer.getForwardPrimer(), forwardSequenceAlignment);
                    alignmentForwardPrimer.setText("Forward primer: (" + setForwardPrimerStart.getText() + "-" + (Integer.parseInt(setForwardPrimerStart.getText()) + 50) + ")");

                } else {
                    forwardSequenceAlignment.getChildren().clear();
                    forwardPrimerField.clear();
                }

            } catch (NumberFormatException e) {
            }

        });

        setReversePrimerStart.textProperty().addListener((change, oldValue, newValue) -> {

            try {
                if (Integer.parseInt(newValue) > templateSequence.getTemplateSequence().length()) {
                    setReversePrimerStart.setText(oldValue);
                }

                if (Integer.parseInt(setReversePrimerStart.getText()) > 100) {

                    String revsequence = new StringBuilder(templateSequence.getTemplateSequence()).reverse().toString();
                    Integer sub = revsequence.length() - Integer.parseInt(setReversePrimerStart.getText());
                    String revSequenceSubstring = revsequence.substring(sub, revsequence.length());

                    if (revsequence.length() >= 100) {

                        reversePrimerField.setText(reversePrimer.getReversePrimer(revSequenceSubstring));

                        r.setText(revsequence.substring(sub, sub + 50) + "\n");

                        reverseSequenceAlignment.getChildren().clear();
                        reverseSequenceAlignment.getChildren().add(r);

                        reversePrimer.reversePrimerAlignment(revSequenceSubstring, reversePrimer.getReversePrimer(), reverseSequenceAlignment);
                        alignmentReversePrimer.setText("Reverse primer: (" + setReversePrimerStart.getText() + "-" + (Integer.parseInt(setReversePrimerStart.getText()) - 50) + ")");
                    } else {
                        reverseSequenceAlignment.getChildren().clear();
                        reversePrimerField.clear();
                    }
                }
            } catch (NumberFormatException e) {
            }

        });

        HBox buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(5, 5, 5, 0));
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
        sequenceAlignmentBox.getChildren().add(alignmentForwardPrimer);
        sequenceAlignmentBox.getChildren().add(forwardSequenceAlignment);
        sequenceAlignmentBox.getChildren().add(alignmentReversePrimer);
        sequenceAlignmentBox.getChildren().add(reverseSequenceAlignment);

        grid.add(nucleotideLabel, 1, 0, 5, 1);

        grid.add(new Label("Forward-primer:"), 1, 1);
        grid.add(new Label("Reverse-primer:"), 8, 1);

        grid.add(forwardPrimerField, 1, 2, 4, 1);
        grid.add(new Label("5'"), 0, 2, 1, 1);
        grid.add(new Label("3'"), 5, 2, 1, 1);
        grid.add(new Label("Set starting nucleotide:"), 1, 3);
        grid.add(setForwardPrimerStart, 1, 4, 4, 1);
        grid.add(forwardPrimerLength, 1, 5);
        grid.add(forwardPrimerMatches, 1, 6);
        grid.add(forwardPrimerGc, 1, 7);
        grid.add(forwardPrimerTm, 1, 8);

        grid.add(reversePrimerField, 8, 2, 4, 1);
        grid.add(new Label("5'"), 7, 2, 1, 1);
        grid.add(new Label("3'"), 12, 2, 1, 1);
        grid.add(new Label("Set starting nucleotide:"), 8, 3);
        grid.add(setReversePrimerStart, 8, 4, 4, 1);
        grid.add(reversePrimerLength, 8, 5);
        grid.add(reversePrimerMatches, 8, 6);
        grid.add(reversePrimerGc, 8, 7);
        grid.add(reversePrimerTm, 8, 8);

        layout.setTop(buttonsBox);
        layout.setCenter(textArea);
        layout.setRight(sequenceAlignmentBox);
        layout.setBottom(grid);

        Scene scene = new Scene(layout, 850, 500);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
