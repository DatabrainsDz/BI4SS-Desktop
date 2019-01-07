package com.databrains.bi4ss.java.models;

public class GeneralYearAdmis {
    private int year;
    private int admis;
    private int ajourni;

    public GeneralYearAdmis() {
    }

    public GeneralYearAdmis(int year, int admis, int ajourni) {
        this.year = year;
        this.admis = admis;
        this.ajourni = ajourni;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAdmis() {
        return admis;
    }

    public void setAdmis(int admis) {
        this.admis = admis;
    }

    public int getAjourni() {
        return ajourni;
    }

    public void setAjourni(int ajourni) {
        this.ajourni = ajourni;
    }
}
