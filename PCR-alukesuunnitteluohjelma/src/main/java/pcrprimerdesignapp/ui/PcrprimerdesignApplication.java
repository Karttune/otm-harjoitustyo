/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.ui;

import java.io.File;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pcrprimerdesignapp.dao.ForwardprimerDao;
import pcrprimerdesignapp.dao.ReverseprimerDao;
import pcrprimerdesignapp.dao.TemplatesequenceDao;
import pcrprimerdesignapp.database.Database;
import pcrprimerdesignapp.domain.Forwardprimer;
import pcrprimerdesignapp.domain.PrimerDesignChecks;
import pcrprimerdesignapp.domain.Reverseprimer;
import pcrprimerdesignapp.domain.Templatesequence;

public class PcrprimerdesignApplication extends Application {

    private Templatesequence templateSequence;
    private Forwardprimer forwardPrimer;
    private Reverseprimer reversePrimer;
    private PrimerDesignChecks primerChecks;

    public PcrprimerdesignApplication() {
        templateSequence = new Templatesequence();
        forwardPrimer = new Forwardprimer();
        reversePrimer = new Reverseprimer();
        primerChecks = new PrimerDesignChecks(forwardPrimer, reversePrimer, templateSequence);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("PCR-primer design");

        Database database = new Database("jdbc:sqlite:sequences.db");
        ForwardprimerDao forwardDao = new ForwardprimerDao(database, "Forwardprimer");
        ReverseprimerDao reverseDao = new ReverseprimerDao(database, "Reverseprimer");
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

        TextArea textArea = new TextArea();
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
        headerField.setPadding(new Insets(4, 5, 0, 0));
        headerField.setText("Enter a nucleotide sequence (<100 nucleotides)");

        TextField forwardPrimerField = new TextField();
        forwardPrimerField.setFont(Font.font("Courier New"));
        forwardPrimerField.setTextFormatter(textFormatterFwd);

        TextField reversePrimerField = new TextField();
        reversePrimerField.setFont(Font.font("Courier New"));
        reversePrimerField.setTextFormatter(textFormatterRev);

        TextField setForwardPrimerStart = new TextField("0");
        setForwardPrimerStart.setTextFormatter(textFormatterStartFwd);

        TextField setReversePrimerStart = new TextField("0");
        setReversePrimerStart.setTextFormatter(textFormatterStartRev);

        Label nucleotideLabel = new Label("Nucleotides: 0");
        Label productSizeLabel = new Label("PCR product size: 0");

        Label forwardPrimerLength = new Label("Nucleotides: 0");
        Label reversePrimerLength = new Label("Nucleotides: 0");

        Label forwardPrimerMatches = new Label("Matching nucleotides: 0");
        Label reversePrimerMatches = new Label("Matching nucleotides: 0");

        Label forwardPrimerGc = new Label("GC-percentage: 0");
        Label reversePrimerGc = new Label("GC-percentage: 0");

        Label forwardPrimerTm = new Label("Tm: 0°C");
        Label reversePrimerTm = new Label("Tm: 0°C");
        Label taTemperature = new Label("Ta: 0°C");

        Label alignmentForwardPrimer = new Label("Forward primer: (0-0)");
        Label alignmentReversePrimer = new Label("Reverse primer: (0-0)");

        Label matchingNucleotidesCheck = new Label("");
        Label gcCheck = new Label("");
        Label tmCheck = new Label("");
        Label repeatCheck = new Label("");
        Label gcClampCheck = new Label("");
        Label dinucleotideCheck = new Label("");
        Label palindromicCheck = new Label("");
        Label primerDimerCheck = new Label("");
        Label fwdThreePrimeMatchCheck = new Label("");

        Text f = new Text();
        f.setFont(Font.font("Courier New"));
        Text r = new Text();
        r.setFont(Font.font("Courier New"));

        TextFlow forwardSequenceAlignment = new TextFlow();
        forwardSequenceAlignment.setPrefSize(400, 50);

        TextFlow reverseSequenceAlignment = new TextFlow();
        reverseSequenceAlignment.setPrefSize(400, 50);

        ChoiceBox databaseSequences = new ChoiceBox();
        databaseSequences.setMaxWidth(100);
        databaseSequences.getItems().setAll(templateDao.findAllTitles());
        TextField nameForDatabase = new TextField();
        Button loadFromDatabase = new Button("Load");
        Button saveToDatabase = new Button("Save");
        Button deleteFromDatabase = new Button("Delete");

        forwardPrimer.setStart(0);
        String measure = "0        10        20        30        40        50";

        openFile.setOnAction((ActionEvent event) -> {

            File file = fileChooser.showOpenDialog(stage);

            templateSequence.headerLineFromFile(file);
            headerField.setText(templateSequence.getSequenceTitle());
            nameForDatabase.setText(templateSequence.getSequenceTitle());

            templateSequence.sequenceFromFile(file);
            textArea.setText(templateSequence.getTemplateSequence());

            setForwardPrimerStart.setText("0");
            forwardPrimer.setStart(0);
            setReversePrimerStart.setText(Integer.toString(templateSequence.getTemplateSequence().length()));
            reversePrimer.setStart(templateSequence.getTemplateSequence().length());

            forwardPrimerField.setText(forwardPrimer.getForwardPrimer(templateSequence.getTemplateSequence()));
            reversePrimerField.setText(reversePrimer.getReversePrimer(templateSequence.getTemplateSequence()));
        });

        textArea.textProperty().addListener((change, oldValue, newValue) -> {

            newValue = newValue.replaceAll("\n", "");
            templateSequence.setTemplateSequence(newValue);

            int nucleotides = newValue.length();
            nucleotideLabel.setText("Nucleotides: " + nucleotides);

            if (newValue.length() < 100) {
                headerField.setText("Enter a nucleotide sequence (<100 nucleotides)");
                forwardPrimerField.setText("");
                reversePrimerField.setText("");
                setForwardPrimerStart.setText("0");
                setReversePrimerStart.setText("0");
                forwardPrimer.setStart(0);
                reversePrimer.setStart(0);
                nameForDatabase.setText("");
            } else {
                headerField.setText("");
                setForwardPrimerStart.setText(Integer.toString(forwardPrimer.getStart()));
                setReversePrimerStart.setText(Integer.toString(reversePrimer.getStart()));
                forwardPrimer.setStart(forwardPrimer.getStart());
                reversePrimer.setStart(reversePrimer.getStart());
                productSizeLabel.setText("PCR product size: " + Integer.toString(reversePrimer.getStart() - forwardPrimer.getStart()));
            }
        });

        forwardPrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            if (newValue.length() >= 30) {
                forwardPrimerField.setText(oldValue);
            }

            forwardPrimer.setPrimer(newValue);

            if (templateSequence.getTemplateSequence().length() >= 100 && !forwardPrimer.getPrimer().equals("")) {

                String fwdsequence = templateSequence.getTemplateSequence();
                f.setText(measure + "\n" + fwdsequence.substring(forwardPrimer.getStart(), (forwardPrimer.getStart() + 50)) + "\n");

                forwardSequenceAlignment.getChildren().clear();
                forwardSequenceAlignment.getChildren().add(f);

                forwardPrimer.forwardPrimerAlignment(templateSequence.getTemplateSequence().substring(forwardPrimer.getStart(), templateSequence.getTemplateSequence().length()), forwardSequenceAlignment);
            } else {
                forwardSequenceAlignment.getChildren().clear();
                forwardPrimerField.clear();
            }

            forwardPrimerLength.setText("Nucleotides: " + forwardPrimer.getPrimer().length());
            forwardPrimerMatches.setText("Matching nucleotides: " + forwardPrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            forwardPrimerGc.setText("GC-percentage: " + forwardPrimer.gcPercentage());
            forwardPrimerTm.setText("Tm: " + forwardPrimer.tmTemperature() + " °C");

            matchingNucleotidesCheck.setText(primerChecks.checkLowGcPercentage() + " " + primerChecks.checkHighGcPercentage());
            gcCheck.setText(primerChecks.checkMatchingNucleotides());
            tmCheck.setText(primerChecks.checkLowTm() + " " + primerChecks.checkHighTm() + " " + primerChecks.checkTmMismatch());
            repeatCheck.setText(primerChecks.checkRepeats(forwardPrimer, "Forward") + " " + primerChecks.checkRepeats(reversePrimer, "Reverse"));
            gcClampCheck.setText(primerChecks.checkGcClamp(forwardPrimer, "Forward") + " " + primerChecks.checkGcClamp(reversePrimer, "Reverse"));
            taTemperature.setText("Ta: " + primerChecks.taTemperature() + " °C");
            dinucleotideCheck.setText(primerChecks.checkDinucleotideRepeats(forwardPrimer, "Forward") + " " + primerChecks.checkDinucleotideRepeats(reversePrimer, "Reverse"));
            palindromicCheck.setText(primerChecks.checkPalindromicSequences(forwardPrimer, "Forward") + " " + primerChecks.checkPalindromicSequences(reversePrimer, "Reverse"));
            primerDimerCheck.setText(primerChecks.checkPrimerDimer());
            fwdThreePrimeMatchCheck.setText(primerChecks.checkFwdThreePrimeMatch() + " " + primerChecks.checkRevThreePrimeMatch());
        });

        reversePrimerField.textProperty().addListener((change, oldValue, newValue) -> {

            if (newValue.length() >= 30) {
                reversePrimerField.setText(oldValue);
            }

            reversePrimer.setPrimer(newValue);

            if (templateSequence.getTemplateSequence().length() >= 100 && reversePrimer.getStart() >= 100 && !reversePrimer.getPrimer().equals("")) {

                String revsequence = templateSequence.getTemplateSequence().substring(0, reversePrimer.getStart());
                r.setText(measure + "\n" + new StringBuilder(revsequence).reverse().toString().substring(0, 50) + "\n");

                reverseSequenceAlignment.getChildren().clear();
                reverseSequenceAlignment.getChildren().add(r);

                reversePrimer.reversePrimerAlignment(revsequence, reverseSequenceAlignment);
            } else {
                reverseSequenceAlignment.getChildren().clear();
                reversePrimerField.clear();
            }

            reversePrimerLength.setText("Nucleotides: " + reversePrimer.getPrimer().length());
            reversePrimerMatches.setText("Matching nucleotides: " + reversePrimer.matchingNucleotides(templateSequence.getTemplateSequence()));

            reversePrimerGc.setText("GC-percentage: " + reversePrimer.gcPercentage());
            reversePrimerTm.setText("Tm: " + reversePrimer.tmTemperature() + " °C");

            matchingNucleotidesCheck.setText(primerChecks.checkLowGcPercentage() + " " + primerChecks.checkHighGcPercentage());
            gcCheck.setText(primerChecks.checkMatchingNucleotides());
            tmCheck.setText(primerChecks.checkLowTm() + " " + primerChecks.checkHighTm() + " " + primerChecks.checkTmMismatch());
            repeatCheck.setText(primerChecks.checkRepeats(forwardPrimer, "Forward") + " " + primerChecks.checkRepeats(reversePrimer, "Reverse"));
            gcClampCheck.setText(primerChecks.checkGcClamp(forwardPrimer, "Forward") + " " + primerChecks.checkGcClamp(reversePrimer, "Reverse"));
            taTemperature.setText("Ta: " + primerChecks.taTemperature() + " °C");
            dinucleotideCheck.setText(primerChecks.checkDinucleotideRepeats(forwardPrimer, "Forward") + " " + primerChecks.checkDinucleotideRepeats(reversePrimer, "Reverse"));
            palindromicCheck.setText(primerChecks.checkPalindromicSequences(forwardPrimer, "Forward") + " " + primerChecks.checkPalindromicSequences(reversePrimer, "Reverse"));
            primerDimerCheck.setText(primerChecks.checkPrimerDimer());
            fwdThreePrimeMatchCheck.setText(primerChecks.checkFwdThreePrimeMatch() + " " + primerChecks.checkRevThreePrimeMatch());
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
                productSizeLabel.setText("PCR product size: " + Integer.toString(reversePrimer.getStart() - forwardPrimer.getStart()));
                String fwdSequence = templateSequence.getTemplateSequence();

                String fwdMeasure = forwardPrimer.getStart().toString();

                for (int i = forwardPrimer.getStart().toString().length(); i <= 50; i++) {

                    if (i % 10 == 0 && i != 0) {
                        fwdMeasure = fwdMeasure + (forwardPrimer.getStart() + i);
                        i = i + forwardPrimer.getStart().toString().length();

                        System.out.println(i);

                    } else {
                        fwdMeasure = fwdMeasure + " ";
                    }
                }

                if (fwdSequence.length() >= 100) {

                    forwardPrimerField.setText(forwardPrimer.getForwardPrimer(fwdSequence.substring(forwardPrimer.getStart(), fwdSequence.length())));
                    f.setText(fwdMeasure + "\n" + fwdSequence.substring(forwardPrimer.getStart(), forwardPrimer.getStart() + 50) + "\n");

                    forwardSequenceAlignment.getChildren().clear();
                    forwardSequenceAlignment.getChildren().add(f);

                    forwardPrimer.forwardPrimerAlignment(fwdSequence.substring(forwardPrimer.getStart(), fwdSequence.length()), forwardSequenceAlignment);
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
                productSizeLabel.setText("PCR product size: " + Integer.toString(reversePrimer.getStart() - forwardPrimer.getStart()));
                String revSequence = templateSequence.getTemplateSequence().substring(0, reversePrimer.getStart());

                if (revSequence.length() >= 100 && reversePrimer.getStart() >= 100) {

                    reversePrimerField.setText(reversePrimer.getReversePrimer(revSequence));
                    r.setText(measure + "\n" + new StringBuilder(revSequence).reverse().toString().substring(0, 50) + "\n");

                    reverseSequenceAlignment.getChildren().clear();
                    reverseSequenceAlignment.getChildren().add(r);

                    reversePrimer.reversePrimerAlignment(revSequence, reverseSequenceAlignment);
                    alignmentReversePrimer.setText("Reverse primer: (" + revSequence.length() + "-" + (revSequence.length() - 50) + ")");
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
            nameForDatabase.setText((String) databaseSequences.getValue());

            if (value != null) {
                try {
                    templateSequence = templateDao.findOne(value);
                    forwardPrimer = forwardDao.findOne(templateSequence.getForwardPrimerId());
                    reversePrimer = reverseDao.findOne(templateSequence.getForwardPrimerId());
                } catch (Exception ex) {
                    Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
                }

                textArea.setText(templateSequence.getTemplateSequence());
                headerField.setText(templateSequence.getSequenceTitle());
                setForwardPrimerStart.setText(Integer.toString(forwardPrimer.getStart()));
                setReversePrimerStart.setText(Integer.toString(reversePrimer.getStart()));
                forwardPrimerField.setText(forwardPrimer.getPrimer());
                reversePrimerField.setText(reversePrimer.getPrimer());
            }
        });

        saveToDatabase.setOnAction((ActionEvent event) -> {

            if (templateSequence.getTemplateSequence().length() >= 100 && !nameForDatabase.getText().equals("")) {

                String title = nameForDatabase.getText();
                templateSequence.setSequenceTitle(title);

                templateSequence.setId(-1);
                forwardPrimer.setId(-1);
                reversePrimer.setId(-1);
                forwardPrimer.setStart(Integer.parseInt(setForwardPrimerStart.getText()));
                reversePrimer.setStart(Integer.parseInt(setReversePrimerStart.getText()));

                try {
                    templateSequence.setForwardPrimerId(forwardDao.returnNextIndex());
                    templateSequence.setReversePrimerId(reverseDao.returnNextIndex());

                    templateDao.saveOrUpdate(templateSequence);
                    forwardDao.saveOrUpdate(forwardPrimer);
                    reverseDao.saveOrUpdate(reversePrimer);
                    databaseSequences.getItems().setAll(templateDao.findAllTitles());
                    nameForDatabase.setText(title + " saved!");
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

        deleteFromDatabase.setOnAction((ActionEvent event) -> {

            String value = (String) databaseSequences.getValue();

            try {
                templateSequence = templateDao.findOne(value);
                templateDao.delete(templateSequence.getId());
                forwardDao.delete(templateSequence.getForwardPrimerId());
                reverseDao.delete(templateSequence.getForwardPrimerId());
                databaseSequences.getItems().setAll(templateDao.findAllTitles());
                nameForDatabase.setText(value + " deleted!");
            } catch (Exception ex) {
                Logger.getLogger(PcrprimerdesignApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        sequenceAlignmentBox.setPadding(new Insets(0, 0, 5, 5));
        sequenceAlignmentBox.getChildren().add(databaseFunctionBox);
        sequenceAlignmentBox.getChildren().add(new Label(""));
        sequenceAlignmentBox.getChildren().add(new Label("Sequence alignment:"));
        sequenceAlignmentBox.getChildren().add(new Label(""));
        sequenceAlignmentBox.getChildren().add(alignmentForwardPrimer);
        sequenceAlignmentBox.getChildren().add(forwardSequenceAlignment);
        sequenceAlignmentBox.getChildren().add(alignmentReversePrimer);
        sequenceAlignmentBox.getChildren().add(reverseSequenceAlignment);

        grid.add(nucleotideLabel, 0, 0, 4, 1);
        grid.add(productSizeLabel, 6, 0, 5, 1);

        grid.add(new Label("Forward-primer:"), 0, 1);
        grid.add(new Label("Reverse-primer:"), 6, 1);

        grid.add(forwardPrimerField, 0, 2, 4, 1);
        grid.add(new Label("Set starting nucleotide:"), 0, 3);
        grid.add(setForwardPrimerStart, 0, 4, 4, 1);
        grid.add(forwardPrimerLength, 0, 5);
        grid.add(forwardPrimerMatches, 0, 6);
        grid.add(forwardPrimerGc, 0, 7);
        grid.add(forwardPrimerTm, 0, 8);
        grid.add(taTemperature, 0, 9);

        grid.add(reversePrimerField, 6, 2, 4, 1);
        grid.add(new Label("Set starting nucleotide:"), 6, 3);
        grid.add(setReversePrimerStart, 6, 4, 4, 1);
        grid.add(reversePrimerLength, 6, 5);
        grid.add(reversePrimerMatches, 6, 6);
        grid.add(reversePrimerGc, 6, 7);
        grid.add(reversePrimerTm, 6, 8);

        grid.add(new Label("Warnings:"), 12, 1);
        grid.add(matchingNucleotidesCheck, 12, 2);
        grid.add(gcCheck, 12, 3);
        grid.add(tmCheck, 12, 4);
        grid.add(repeatCheck, 12, 5);
        grid.add(gcClampCheck, 12, 6);
        grid.add(dinucleotideCheck, 12, 7);
        grid.add(palindromicCheck, 12, 8);
        grid.add(primerDimerCheck, 12, 9);
        grid.add(fwdThreePrimeMatchCheck, 12, 10);

        layout.setTop(buttonsBox);
        layout.setCenter(textArea);
        layout.setRight(sequenceAlignmentBox);
        layout.setBottom(grid);

        Scene scene = new Scene(layout, 1000, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
