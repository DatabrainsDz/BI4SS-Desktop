package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.type.Subjects;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
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
        for(String subject: years) {
            seriesAdmis.getData().add(new XYChart.Data<String, Number>(subject, 30));
            seriesNoAdmis.getData().add(new XYChart.Data<String, Number>(subject, 70));
        }

        chartStackedBarPastYears.getData().addAll(seriesAdmis, seriesNoAdmis);

    }
    
    @FXML
    private void on2010() {

    }

    @FXML
    private void on2011() {

    }

    @FXML
    private void on2012() {

    }

    @FXML
    private void on2013() {

    }

    @FXML
    private void on2014() {

    }

    @FXML
    private void on2015() {

    }
}
