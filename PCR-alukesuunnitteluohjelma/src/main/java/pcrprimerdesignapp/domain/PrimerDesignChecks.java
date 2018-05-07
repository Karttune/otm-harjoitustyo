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

    public PrimerDesignChecks() {

    }

    public String checkLowGcPercentage(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        if (fwdprimer.gcPercentage() < 40 && revprimer.gcPercentage() < 40) {
            return "Reverse and forward primer GC% are too low!";
        } else if (fwdprimer.gcPercentage() < 40) {
            return "Forward primer GC% is too low!";
        } else if (revprimer.gcPercentage() < 40) {
            return "Reverse primer GC% is too low!";
        } else {
            return "";
        }
    }

    public String checkHighGcPercentage(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        if (fwdprimer.gcPercentage() > 60 && revprimer.gcPercentage() > 60) {
            return "Forward and reverse primer GC% are too high!";
        } else if (fwdprimer.gcPercentage() > 60) {
            return "Forward primer GC% is too high!";
        } else if (revprimer.gcPercentage() > 60) {
            return "Reverse primer GC% is too high!";
        } else {
            return "";
        }
    }

    public String checkMatchingNucleotides(Forwardprimer fwdprimer, Reverseprimer revprimer, Templatesequence templatesequence) {

        String template = templatesequence.getTemplateSequence();
        Double fwdMatchPercentage = ((double) fwdprimer.matchingNucleotides(template) / (double) fwdprimer.getPrimer().length()) * 100;
        Double revMatchPercentage = ((double) revprimer.matchingNucleotides(template) / (double) revprimer.getPrimer().length()) * 100;

        if (fwdMatchPercentage < 90.0 && revMatchPercentage < 90.0) {
            return "Forward and reverse primer match% is too low!";
        } else if (fwdMatchPercentage < 90.0) {
            return "Forward primer match% is too low!";
        } else if (revMatchPercentage < 90.0) {
            return "Reverse primer match% is too low!";
        } else {
            return "";
        }
    }

    public String checkForRepeats(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        Pattern regex = Pattern.compile("AAAA|TTTT|CCCC|GGGG", Pattern.CASE_INSENSITIVE);

        Matcher fwdMatcher = regex.matcher(fwdprimer.getPrimer());
        Matcher revMatcher = regex.matcher(revprimer.getPrimer());

        if (fwdMatcher.find() && revMatcher.find()) {
            return "Forward and reverse primers contain repeated sequences!";
        } else if (revMatcher.find()) {
            return "Reverse primer contains repeated sequences!";
        } else if (fwdMatcher.find()) {
            return "Forward primer contains repeated sequences!";
        } else {
            return "";
        }
    }

    public String checkLowTm(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        if (fwdprimer.tmTemperature() < 50.0 && revprimer.tmTemperature() < 50.0) {
            return "Forward and reverse primer Tm is too low!";
        } else if (fwdprimer.tmTemperature() < 50.0) {
            return "Forward primer Tm is too low!";
        } else if (revprimer.tmTemperature() < 50.0) {
            return "Reverse primer Tm is too low!";
        } else {
            return "";
        }
    }

    public String checkHighTm(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        if (fwdprimer.tmTemperature() > 65.0 && revprimer.tmTemperature() > 65.0) {
            return "Forward and reverse primer Tm is too high!";
        } else if (fwdprimer.tmTemperature() > 65.0) {
            return "Forward primer Tm is too high!";
        } else if (revprimer.tmTemperature() > 65.0) {
            return "Reverse primer Tm is too high!";
        } else {
            return "";
        }
    }

    public String checkTmMismatch(Forwardprimer fwdprimer, Reverseprimer revprimer) {

        Integer mismatchTemperature = Math.abs(fwdprimer.tmTemperature() - revprimer.tmTemperature());

        if (mismatchTemperature > 5) {
            return "Tm-temperatures are mismatched!";
        } else {
            return "";
        }
    }

    public Double taTemperature(Forwardprimer fwdprimer, Reverseprimer revprimer, Templatesequence templatesequence) {

        String[] template = templatesequence.getTemplateSequence().substring(fwdprimer.getStart(), revprimer.getStart()).split("");

        int templateMeltingTemperature = 0;

        for (int i = 0; i < template.length; i++) {

            if (template[i].matches("[GCgc]")) {
                templateMeltingTemperature += 4;
            } else if (template[i].matches("[ATat]")) {
                templateMeltingTemperature += 2;
            }
        }

        Integer lessStableTm = 0;

        if (fwdprimer.tmTemperature() < revprimer.tmTemperature()) {
            lessStableTm = fwdprimer.tmTemperature();
        } else {
            lessStableTm = revprimer.tmTemperature();
        }

        Double taTemperature = (0.3 * lessStableTm) + (0.7 * templateMeltingTemperature) - 14.9;
        System.out.println(taTemperature);
        return taTemperature;
    }
}
