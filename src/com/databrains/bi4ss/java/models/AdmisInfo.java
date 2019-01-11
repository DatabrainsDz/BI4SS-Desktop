package com.databrains.bi4ss.java.models;

public class AdmisInfo {
    private String name;
    private int admis;
    private int ajourne;

    public AdmisInfo() {
    }

    public AdmisInfo(String name, int admis, int ajourne) {
        this.name = name;
        this.admis = admis;
        this.ajourne = ajourne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdmis() {
        return admis;
    }

    public void setAdmis(int admis) {
        this.admis = admis;
    }

    public int getAjourne() {
        return ajourne;
    }

    public void setAjourne(int ajourne) {
        this.ajourne = ajourne;
    }

    @Override
    public String toString() {
        return "AdmisInfo{" +
                "name='" + name + '\'' +
                ", admis=" + admis +
                ", ajourne=" + ajourne +
                '}';
    }
}
