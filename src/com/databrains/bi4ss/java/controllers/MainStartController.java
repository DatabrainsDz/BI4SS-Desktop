package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.GeneralYearAdmis;
import com.databrains.bi4ss.java.type.Subjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainStartController implements Initializable {

    @FXML
    private StackedBarChart chartStackedBarPastYears;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPastYearsChart();
    }

    private void initPastYearsChart() { // Past Years Stacked Bar chart
        XYChart.Series<String, Number> seriesAdmis = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNoAdmis = new XYChart.Series<>();

        seriesAdmis.setName("Admis");
        seriesNoAdmis.setName("No Admis");

        String[] years = {"2010", "2011", "2012", "2013", "2014", "2015"};

        for(GeneralYearAdmis generalYearAdmis: getDataFromServer()) {
            seriesAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getYear()), generalYearAdmis.getAdmis()));
            seriesNoAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getYear()), generalYearAdmis.getAjourni()));
        }

        chartStackedBarPastYears.getData().addAll(seriesAdmis, seriesNoAdmis);
    }

    private List<GeneralYearAdmis> getDataFromServer() {
        List<GeneralYearAdmis> generalYearAdmis = new LinkedList<>();
        generalYearAdmis.add(new GeneralYearAdmis(2010, 312, 367));
        generalYearAdmis.add(new GeneralYearAdmis(2011, 508, 568));
        generalYearAdmis.add(new GeneralYearAdmis(2012, 509, 804));
        generalYearAdmis.add(new GeneralYearAdmis(2013, 326, 939));
        generalYearAdmis.add(new GeneralYearAdmis(2014, 265, 899));
        generalYearAdmis.add(new GeneralYearAdmis(2015, 178, 886));

        return generalYearAdmis;
    }
}
