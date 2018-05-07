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

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
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

        if (!primer.equals("")) {

            String[] nucleotides = primer.split("");

            int meltingTemperature = 0;

            for (int i = 0; i < nucleotides.length; i++) {

                if (nucleotides[i].matches("[GCgc]")) {
                    meltingTemperature += 4;
                } else if (nucleotides[i].matches("[ATat]")) {
                    meltingTemperature += 2;
                }
            }
            return meltingTemperature;
        } else {
            return 0;
        }
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
