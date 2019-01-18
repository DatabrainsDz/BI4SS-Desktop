package com.databrains.bi4ss.java.models;

public class Profile {
    private boolean isMale;
    private boolean IsAlgerianne;
    private int numCity;
    private double bacAverage;
    private int age;

    public Profile() {
    }

    public Profile(boolean isMale, boolean isAlgerianne, int numCity, double bacAverage, int age) {
        this.isMale = isMale;
        IsAlgerianne = isAlgerianne;
        this.numCity = numCity;
        this.bacAverage = bacAverage;
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isAlgerianne() {
        return IsAlgerianne;
    }

    public void setAlgerianne(boolean algerianne) {
        IsAlgerianne = algerianne;
    }

    public int getNumCity() {
        return numCity;
    }

    public void setNumCity(int numCity) {
        this.numCity = numCity;
    }

    public double getBacAverage() {
        return bacAverage;
    }

    public void setBacAverage(double bacAverage) {
        this.bacAverage = bacAverage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
