package com.databrains.bi4ss.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable {
    @FXML
    private PieChart genderChart;
    // Data of Pie Chart
    private ObservableList<PieChart.Data> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGenderChart();
    }

    private void initGenderChart() {
        data = FXCollections.observableArrayList();
        data.add(new PieChart.Data("60 Male", 60d));
        data.add(new PieChart.Data("70 Famale", 70d));
        genderChart.setData(data);
    }
}
