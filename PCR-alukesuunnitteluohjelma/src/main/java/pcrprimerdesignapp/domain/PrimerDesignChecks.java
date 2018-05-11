/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Konsta
 */
public class PrimerDesignChecks {

    private Forwardprimer fwdPrimer;
    private Reverseprimer revPrimer;
    private Templatesequence tempSequence;

    public PrimerDesignChecks(Forwardprimer fwdPrimer, Reverseprimer revPrimer, Templatesequence tempSequence) {
        this.fwdPrimer = fwdPrimer;
        this.revPrimer = revPrimer;
        this.tempSequence = tempSequence;
    }

    public String checkLowGcPercentage() {

        if (fwdPrimer.gcPercentage() < 40 && revPrimer.gcPercentage() < 40) {
            return "Forward primer GC% is too low! Reverse primer GC% is too low!";
        } else if (fwdPrimer.gcPercentage() < 40) {
            return "Forward primer GC% is too low!";
        } else if (revPrimer.gcPercentage() < 40) {
            return "Reverse primer GC% is too low!";
        } else {
            return "";
        }
    }

    public String checkHighGcPercentage() {

        if (fwdPrimer.gcPercentage() > 60 && revPrimer.gcPercentage() > 60) {
            return "Forward primer GC% is too high! Reverse primer GC% is too high!";
        } else if (fwdPrimer.gcPercentage() > 60) {
            return "Forward primer GC% is too high!";
        } else if (revPrimer.gcPercentage() > 60) {
            return "Reverse primer GC% is too high!";
        } else {
            return "";
        }
    }

    public String checkMatchingNucleotides() {

        String template = tempSequence.getTemplateSequence();
        Double fwdMatchPercentage = ((double) fwdPrimer.matchingNucleotides(template) / (double) fwdPrimer.getPrimer().length()) * 100;
        Double revMatchPercentage = ((double) revPrimer.matchingNucleotides(template) / (double) revPrimer.getPrimer().length()) * 100;

        if (fwdMatchPercentage < 80.0 && revMatchPercentage < 80.0) {
            return "Forward primer match% is too low! Reverse primer match% is too low!";
        } else if (fwdMatchPercentage < 80.0) {
            return "Forward primer match% is too low!";
        } else if (revMatchPercentage < 80.0) {
            return "Reverse primer match% is too low!";
        } else {
            return "";
        }
    }

    public String checkRepeats(AbstractPrimerObject primer, String name) {

        Pattern regex = Pattern.compile("AAAA|TTTT|CCCC|GGGG", Pattern.CASE_INSENSITIVE);

        Matcher matcher = regex.matcher(primer.getPrimer());

        if (matcher.find()) {
            return name + " primer contains repeated sequences!";
        } else {
            return "";
        }
    }

    public String checkLowTm() {

        if (fwdPrimer.tmTemperature() < 50.0 && revPrimer.tmTemperature() < 50.0) {
            return "Forward primer Tm is too low! Reverse primer Tm is too low!";
        } else if (fwdPrimer.tmTemperature() < 50.0) {
            return "Forward primer Tm is too low!";
        } else if (revPrimer.tmTemperature() < 50.0) {
            return "Reverse primer Tm is too low!";
        } else {
            return "";
        }
    }

    public String checkHighTm() {

        if (fwdPrimer.tmTemperature() > 65.0 && revPrimer.tmTemperature() > 65.0) {
            return "Forward primer Tm is too high! Reverse primer Tm is too high!";
        } else if (fwdPrimer.tmTemperature() > 65.0) {
            return "Forward primer Tm is too high!";
        } else if (revPrimer.tmTemperature() > 65.0) {
            return "Reverse primer Tm is too high!";
        } else {
            return "";
        }
    }

    public String checkTmMismatch() {

        Integer mismatchTemperature = Math.abs(fwdPrimer.tmTemperature() - revPrimer.tmTemperature());

        if (mismatchTemperature >= 5) {
            return "Tm-temperatures are mismatched!";
        } else {
            return "";
        }
    }

    public Integer taTemperature() {

        if (fwdPrimer.tmTemperature() < revPrimer.tmTemperature()) {
            return fwdPrimer.tmTemperature() - 5;
        } else {
            return revPrimer.tmTemperature() - 5;
        }
    }

    public String checkGcClamp(AbstractPrimerObject primer, String name) {

        if (primer.getPrimer().length() > 10) {
            String[] primerSequence = primer.getPrimer().substring((primer.getPrimer().length() - 5), primer.getPrimer().length() - 1).split("");
            int gcClamp = 0;

            for (int i = 0; i < primerSequence.length; i++) {
                if (primerSequence[i].matches("[GCgc]")) {
                    gcClamp++;
                }
            }

            if (gcClamp > 3) {
                return name + " primer has too many GC nucleotides in 3' end!";
            } else if (gcClamp == 0) {
                return name + " primer has no GC nucleotides in 3' end!";
            }
        }
        return "";
    }

    public String checkDinucleotideRepeats(AbstractPrimerObject primer, String name) {

        Pattern pattern = Pattern.compile("ATATAT(AT)+|TATATA(TA)+|GCGCGC(GC)+|CGCGCG(CG)+", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(primer.getPrimer());

        if (matcher.find()) {
            return name + " primer contains dinucleotide repeats!";
        } else {
            return "";
        }
    }

    public String checkPalindromicSequences(AbstractPrimerObject primer, String name) {

        if (primer.getPrimer().length() > 10) {

            for (int i = 0; i < primer.getPrimer().length() - 3; i++) {

                String[] nucleotides = new StringBuilder(primer.getPrimer().substring(i, i + 4)).reverse().toString().split("");

                for (int j = 0; j < nucleotides.length; j++) {

                    if (nucleotides[j].equalsIgnoreCase("A")) {
                        nucleotides[j] = "T";
                    } else if (nucleotides[j].equalsIgnoreCase("T")) {
                        nucleotides[j] = "A";
                    } else if (nucleotides[j].equalsIgnoreCase("C")) {
                        nucleotides[j] = "G";
                    } else if (nucleotides[j].equalsIgnoreCase("G")) {
                        nucleotides[j] = "C";
                    }
                }

                Pattern pattern = Pattern.compile(String.join("", nucleotides), Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(primer.getPrimer());

                if (matcher.find(i + 4)) {
                    return name + " primer contains palindromic sequences!";
                }
            }
        }
        return "";
    }

    public String checkPrimerDimer() {

        int matches = 0;

        if (fwdPrimer.getPrimer().length() > 10 && revPrimer.getPrimer().length() > 10) {
            String[] fwdPrimerSequence = fwdPrimer.getPrimer().substring((fwdPrimer.getPrimer().length() - 4), fwdPrimer.getPrimer().length()).split("");
            String[] revPrimerSequence = revPrimer.getPrimer().substring((revPrimer.getPrimer().length() - 4), revPrimer.getPrimer().length()).split("");

            Integer j = 3;
            for (int i = 0; i < 4; i++) {

                if (fwdPrimerSequence[i].equalsIgnoreCase("A") && revPrimerSequence[j].equalsIgnoreCase("T") || fwdPrimerSequence[i].equalsIgnoreCase("T") && revPrimerSequence[j].equalsIgnoreCase("A")) {
                    matches++;
                } else if (fwdPrimerSequence[i].equalsIgnoreCase("C") && revPrimerSequence[j].equalsIgnoreCase("G") || fwdPrimerSequence[i].equalsIgnoreCase("G") && revPrimerSequence[j].equalsIgnoreCase("C")) {
                    matches++;
                }
                j--;
            }
            if (matches >= 3) {
                return "Primer 3' ends are complementary!";
            }
        }
        return "";
    }

    public String checkFwdThreePrimeMatch() {

        if (fwdPrimer.getPrimer().length() > 10) {

            String fwdTemplate = tempSequence.getTemplateSequence().substring(fwdPrimer.getStart() + (fwdPrimer.getPrimer().length() - 2), (fwdPrimer.getStart() + fwdPrimer.getPrimer().length()));
            String fwdPrimerSequence = fwdPrimer.getPrimer().substring((fwdPrimer.getPrimer().length() - 2), fwdPrimer.getPrimer().length());

            if (!fwdTemplate.equalsIgnoreCase(fwdPrimerSequence)) {
                return "Forward primer 3' end is mismatched!";
            }
        }
        return "";
    }

    public String checkRevThreePrimeMatch() {

        int matches = 0;

        if (revPrimer.getPrimer().length() > 10 && tempSequence.getTemplateSequence().length() >= 100) {

            String[] revTemplate = tempSequence.getTemplateSequence().substring((revPrimer.getStart() - revPrimer.getPrimer().length()), (revPrimer.getStart() - revPrimer.getPrimer().length()) + 2).split("");
            String[] revPrimerSequence = revPrimer.getPrimer().substring((revPrimer.getPrimer().length() - 2), revPrimer.getPrimer().length()).split("");

            Integer j = 1;
            for (int i = 0; i < revPrimerSequence.length; i++) {

                if (revTemplate[j].equalsIgnoreCase("A") && revPrimerSequence[i].equalsIgnoreCase("T") || revTemplate[j].equalsIgnoreCase("T") && revPrimerSequence[i].equalsIgnoreCase("A")) {
                    matches++;
                } else if (revTemplate[j].equalsIgnoreCase("C") && revPrimerSequence[i].equalsIgnoreCase("G") || revTemplate[j].equalsIgnoreCase("G") && revPrimerSequence[i].equalsIgnoreCase("C")) {
                    matches++;
                }
                j--;
            }

            if (matches != 2) {
                return "Reverse primer 3' end is mismatched!";
            }
        }
        return "";
    }
}
