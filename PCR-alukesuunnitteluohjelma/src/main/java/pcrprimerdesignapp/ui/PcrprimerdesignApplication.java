/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
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
import pcrprimerdesignapp.dao.ForwardprimerDao;
import pcrprimerdesignapp.dao.ReverseprimerDao;
import pcrprimerdesignapp.dao.TemplatesequenceDao;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.Reverseprimer;
import pcrprimerdesignapp.domain.Templatesequence;

public class PcrprimerdesignApplication extends Application {

    private Templatesequence templateSequence;
    private Forwardprimer forwardPrimer;
    private Reverseprimer reversePrimer;
    public static TextArea textArea;

    public PcrprimerdesignApplication() {
        this.textArea = new TextArea();
        templateSequence = new Templatesequence();
        forwardPrimer = new Forwardprimer();
        reversePrimer = new Reverseprimer();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("PCR-primer design");

        Database database = new Database("jdbc:sqlite:sequences.db");
        ForwardprimerDao forwardDao = new ForwardprimerDao(database);
        ReverseprimerDao reverseDao = new ReverseprimerDao(database);
        TemplatesequenceDao templateDao = new TemplatesequenceDao(database);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FASTA files (*.fasta)", "*.fasta");
        fileChooser.getExtensionFilters().add(extFilter);

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

        UnaryOperator<Change> filterStart = change -> {
            String text = change.getText();

            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatterStartFwd = new TextFormatter<>(filterStart);
        TextFormatter<String> textFormatterStartRev = new TextFormatter<>(filterStart);

        textArea.setFont(Font.font("Courier New"));
        textArea.setWrapText(true);
        textArea.setTextFormatter(textFormatterArea);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(5, 5, 5, 5));

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(5, 5, 5, 5));

        final Button openFile = new Button("Open file");
        Label headerField = new Label("");
        headerField.setText("Enter a nucleotide sequence (<100 nucleotides)");

        TextField forwardPrimerField = new TextField();
        forwardPrimerField.setFont(Font.font("Courier New"));
        forwardPrimerField.setTextFormatter(textFormatterFwd);

        TextField reversePrimerField = new TextField();
        reversePrimerField.setFont(Font.font("Courier New"));
        reversePrimerField.setTextFormatter(textFormatterRev);

        TextField setForwardPrimerStart = new TextField();
        setForwardPrimerStart.setTextFormatter(textFormatterStartFwd);

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

        ChoiceBox databaseSequences = new ChoiceBox();
        databaseSequences.getItems().setAll(templateDao.findAllTitles());
        TextField nameForDatabase = new TextField();
        Button loadFromDatabase = new Button("Load");
        Button saveToDatabase = new Button("Save");
        Button deleteFromDatabase = new Button("Delete");

        forwardPrimer.setStart(0);

        openFile.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showOpenDialog(stage);

            templateSequence.headerLineFromFile(file);
            headerField.setText(templateSequence.getSequenceTitle());
            nameForDatabase.setText(templateSequence.getSequenceTitle());

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

            if (newValue.length() < 100) {
                headerField.setText("Enter a nucleotide sequence (<100 nucleotides)");
                forwardPrimerField.setText("");
                reversePrimerField.setText("");
                setForwardPrimerStart.setText("0");
                setReversePrimerStart.setText("0");
                forwardPrimer.setStart(0);
                reversePrimer.setStart(0);
            } else {
                headerField.setText("");
            }

            int nucleotides = newValue.length();
            nucleotideLabel.setText("Nucleotides: " + nucleotides);

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

        });

        forwardPrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            if (newValue.length() >= 30) {
                forwardPrimerField.setText(oldValue);
            }

            forwardPrimer.setForwardPrimer(newValue);

            forwardPrimerLength.setText("Nucleotides: " + forwardPrimer.getForwardPrimer().length());
            forwardPrimerMatches.setText("Matching nucleotides: " + forwardPrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");

            if (templateSequence.getTemplateSequence().length() >= 100) {

                String fwdsequence = templateSequence.getTemplateSequence();
                f.setText(fwdsequence.substring(forwardPrimer.getStart(), (forwardPrimer.getStart() + 50)) + "\n");

                forwardSequenceAlignment.getChildren().clear();
                forwardSequenceAlignment.getChildren().add(f);

                forwardPrimer.forwardPrimerAlignment(templateSequence.getTemplateSequence().substring(forwardPrimer.getStart(), templateSequence.getTemplateSequence().length()), forwardPrimer.getForwardPrimer(), forwardSequenceAlignment);
            } else {
                forwardSequenceAlignment.getChildren().clear();
                forwardPrimerField.clear();
            }
        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            if (newValue.length() >= 30) {
                reversePrimerField.setText(oldValue);
            }

            reversePrimer.setReversePrimer(newValue);

            reversePrimerLength.setText("Nucleotides: " + reversePrimer.getReversePrimer().length());
            reversePrimerMatches.setText("Matching nucleotides: " + reversePrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());

            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

            try {
                if (templateSequence.getTemplateSequence().length() >= 100 && reversePrimer.getStart() >= 100) {

                    String revsequence = templateSequence.getTemplateSequence().substring(0, reversePrimer.getStart());
                    r.setText(new StringBuilder(revsequence).reverse().toString().substring(0, 50) + "\n");

                    reverseSequenceAlignment.getChildren().clear();
                    reverseSequenceAlignment.getChildren().add(r);

                    reversePrimer.reversePrimerAlignment(revsequence, reversePrimer.getReversePrimer(), reverseSequenceAlignment);
                } else {
                    reverseSequenceAlignment.getChildren().clear();
                    reversePrimerField.clear();
                }
            } catch (NumberFormatException e) {
            }
        });

        setForwardPrimerStart.textProperty().addListener((change, oldValue, newValue) -> {

            Integer a = templateSequence.getTemplateSequence().length() - 50;

            if (a < 0) {
                a = 0;
            }

            try {
                if (Integer.parseInt(newValue) > a) {
                    setForwardPrimerStart.setText(oldValue);
                }

                forwardPrimer.setStart(Integer.parseInt(setForwardPrimerStart.getText()));
                String fwdsequence = templateSequence.getTemplateSequence();

                if (fwdsequence.length() >= 100) {

                    forwardPrimerField.setText(forwardPrimer.getForwardPrimer(fwdsequence.substring(forwardPrimer.getStart(), fwdsequence.length())));
                    f.setText(fwdsequence.substring(forwardPrimer.getStart(), forwardPrimer.getStart() + 50) + "\n");

                    forwardSequenceAlignment.getChildren().clear();
                    forwardSequenceAlignment.getChildren().add(f);

                    forwardPrimer.forwardPrimerAlignment(fwdsequence.substring(forwardPrimer.getStart(), fwdsequence.length()), forwardPrimer.getForwardPrimer(), forwardSequenceAlignment);
                    alignmentForwardPrimer.setText("Forward primer: (" + forwardPrimer.getStart() + "-" + (forwardPrimer.getStart() + 50) + ")");

                } else {
                    alignmentForwardPrimer.setText("Forward primer: (0-0)");
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

                reversePrimer.setStart(Integer.parseInt(setReversePrimerStart.getText()));
                String revsequence = templateSequence.getTemplateSequence().substring(0, reversePrimer.getStart());

                if (revsequence.length() >= 100 && reversePrimer.getStart() >= 100) {

                    reversePrimerField.setText(reversePrimer.getReversePrimer(revsequence));

                    r.setText(new StringBuilder(revsequence).reverse().toString().substring(0, 50) + "\n");

                    reverseSequenceAlignment.getChildren().clear();
                    reverseSequenceAlignment.getChildren().add(r);

                    reversePrimer.reversePrimerAlignment(revsequence, reversePrimer.getReversePrimer(), reverseSequenceAlignment);
                    alignmentReversePrimer.setText("Reverse primer: (" + revsequence.length() + "-" + (revsequence.length() - 50) + ")");
                } else {
                    alignmentReversePrimer.setText("Reverse primer: (0-0)");
                    reverseSequenceAlignment.getChildren().clear();
                    reversePrimerField.clear();
                }
            } catch (NumberFormatException e) {
            }
        });

        loadFromDatabase.setOnAction((ActionEvent event) -> {

            String value = (String) databaseSequences.getValue();

            try {
                templateSequence = templateDao.findOne(value);
                forwardPrimer = forwardDao.findOne(templateSequence.getForwardPrimerId());
                reversePrimer = reverseDao.findOne(templateSequence.getForwardPrimerId());
            } catch (Exception ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            }

            headerField.setText(templateSequence.getSequenceTitle());
            textArea.setText(templateSequence.getTemplateSequence());
            setForwardPrimerStart.setText(Integer.toString(forwardPrimer.getStart()));
            setReversePrimerStart.setText(Integer.toString(reversePrimer.getStart()));
            forwardPrimerField.setText(forwardPrimer.getForwardPrimer());
            reversePrimerField.setText(reversePrimer.getReversePrimer());

        });

        saveToDatabase.setOnAction((ActionEvent event) -> {

            if (!nameForDatabase.getText().equals("")) {
                String title = nameForDatabase.getText();
                templateSequence.setSequenceTitle(title);

                templateSequence.setId(-1);
                forwardPrimer.setId(-1);
                reversePrimer.setId(-1);
                forwardPrimer.setStart(Integer.parseInt(setForwardPrimerStart.getText()));
                reversePrimer.setStart(Integer.parseInt(setReversePrimerStart.getText()));

                try {

                    Integer fwdId = forwardDao.findAll().size() + 1;
                    Integer revId = reverseDao.findAll().size() + 1;
                    templateSequence.setForwardPrimerId(fwdId);
                    templateSequence.setReversePrimerId(revId);

                    templateDao.saveOrUpdate(templateSequence);
                    forwardDao.saveOrUpdate(forwardPrimer);
                    reverseDao.saveOrUpdate(reversePrimer);
                    databaseSequences.getItems().setAll(templateDao.findAllTitles());
                } catch (Exception ex) {
                    Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                nameForDatabase.setText("Enter a title!");
            }
        });

        nameForDatabase.textProperty().addListener((change, oldValue, newValue) -> {
            headerField.setText(newValue);
        });

        HBox buttonsBox = new HBox();
        buttonsBox.setPadding(new Insets(5, 5, 5, 0));
        buttonsBox.setSpacing(10);
        buttonsBox.getChildren().add(openFile);
        buttonsBox.getChildren().add(headerField);

        HBox databaseFunctionBox = new HBox();
        databaseFunctionBox.setSpacing(10);
        databaseFunctionBox.getChildren().add(databaseSequences);
        databaseFunctionBox.getChildren().add(loadFromDatabase);
        databaseFunctionBox.getChildren().add(saveToDatabase);
        databaseFunctionBox.getChildren().add(nameForDatabase);
        databaseFunctionBox.getChildren().add(deleteFromDatabase);

        VBox sequenceAlignmentBox = new VBox();
        sequenceAlignmentBox.setPadding(new Insets(5, 5, 5, 5));
        sequenceAlignmentBox.getChildren().add(databaseFunctionBox);
        sequenceAlignmentBox.getChildren().add(new Label(""));
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
