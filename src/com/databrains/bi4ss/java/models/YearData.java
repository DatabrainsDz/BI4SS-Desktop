package com.databrains.bi4ss.java.models;

import java.util.List;

public class YearData {
    private List<AdmisInfo> admisCity;
    private List<AdmisInfo> admisNationality;
    private List<AdmisInfo> admisGender;

    public YearData() {
    }

    public YearData(List<AdmisInfo> admisCity, List<AdmisInfo> admisNationality, List<AdmisInfo> admisGender) {
        this.admisCity = admisCity;
        this.admisNationality = admisNationality;
        this.admisGender = admisGender;
    }

    public List<AdmisInfo> getAdmisCity() {
        return admisCity;
    }

    public void setAdmisCity(List<AdmisInfo> admisCity) {
        this.admisCity = admisCity;
    }

    public List<AdmisInfo> getAdmisNationality() {
        return admisNationality;
    }

    public void setAdmisNationality(List<AdmisInfo> admisNationality) {
        this.admisNationality = admisNationality;
    }

    public List<AdmisInfo> getAdmisGender() {
        return admisGender;
    }

    public void setAdmisGender(List<AdmisInfo> admisGender) {
        this.admisGender = admisGender;
    }

    @Override
    public String toString() {
        return "YearData{" +
                "admisCity=" + admisCity +
                ", admisNationality=" + admisNationality +
                ", admisGender=" + admisGender +
                '}';
    }
}
