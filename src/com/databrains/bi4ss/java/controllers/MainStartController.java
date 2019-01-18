package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.webservice.WebService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

        seriesAdmis.setName("Admitted");
        seriesNoAdmis.setName("Adjourned");
        List<AdmisInfo> pastYearsData = WebService.getPastYearsData();
        if(pastYearsData != null) {
            for(AdmisInfo generalYearAdmis: pastYearsData) {
                seriesAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getName()), generalYearAdmis.getAdmis()));
                seriesNoAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getName()), generalYearAdmis.getAjourne()));
            }
        }

        chartStackedBarPastYears.getData().addAll(seriesAdmis, seriesNoAdmis);
    }
}
