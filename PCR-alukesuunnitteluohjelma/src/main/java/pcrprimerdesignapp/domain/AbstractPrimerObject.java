/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcrprimerdesignapp.domain;

/**
 *
 * @author Konsta
 */
public abstract class AbstractPrimerObject {

    String primer;
    private Integer id;
    private Integer start;

    public AbstractPrimerObject() {
        primer = "";
        start = 0;
    }

    /**
     * Metodi palauttaa alukkeen G ja C -nukleotidien määrän prosentuaalisena
     * osuutena.
     *
     * @return palauttaa alukkeen GC% liukulukuna.
     */
    public Double gcPercentage() {

        if (!primer.equals("")) {

            String[] nucleotides = primer.split("");
            double gc = 0;

            for (String nucleotide : nucleotides) {
                if (nucleotide.matches("[GCgc]")) {
                    gc++;
                }
            }

            Double gcPercentage = Math.floor((gc / nucleotides.length) * 100);
            return gcPercentage;
        } else {
            return 0.0;
        }
    }

    /**
     * Metodi palauttaa alukkeen tm-lämpötilan celsiusasteina.
     *
     * @return palauttaa alukkeen tm-lämpötilan kokonaislukuna.
     */
    public Integer tmTemperature() {

        Integer tmTemperature = 0;

        if (!primer.equals("")) {

            String[] nucleotides = primer.split("");

            for (String nucleotide : nucleotides) {
                if (nucleotide.matches("[ATat]")) {
                    tmTemperature = tmTemperature + 2;
                } else if (nucleotide.matches("[GCgc]")) {
                    tmTemperature = tmTemperature + 4;
                }
            }
        }
        return tmTemperature;
    }

    public void setPrimer(String primer) {
        this.primer = primer;
    }

    public String getPrimer() {
        return primer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
