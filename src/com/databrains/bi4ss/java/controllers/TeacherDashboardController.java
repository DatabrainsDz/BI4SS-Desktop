package com.databrains.bi4ss.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.text.Format;
import java.util.Random;
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable {

    /* Start Pie Chart */

    @FXML
    private PieChart genderChart;
    // Data of Pie Chart
    private ObservableList<PieChart.Data> dataGenderChart;
    @FXML
    private JFXComboBox<Integer> comboYearGender, comboYearModule;

    /* End Pie Chart */

    /* Start Line Chart */

    @FXML
    private LineChart<String, Double> lineChart;

    XYChart.Series series2011, series2012, series2013, series2014, series2015, series2016;

    /* End Line Chart */

    /* Start Bar Chart */

    @FXML
    private BarChart barChart;

    /* End Bar Chart */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombo();
        initGenderChart();
        initLineChart();
        initBarChart();
    }

    private void initCombo() {
        for (int i = 2011; i < 2017; i++) {
            comboYearGender.getItems().add(i);
            comboYearModule.getItems().add(i);
        }
        comboYearGender.getSelectionModel().select(0);
        comboYearModule.getSelectionModel().select(0);
    }

    private void initGenderChart() {
        int numOfMale = 60;
        int numOfFamale = 70;

        dataGenderChart = FXCollections.observableArrayList();
        dataGenderChart.add(new PieChart.Data("Male", numOfMale));
        dataGenderChart.add(new PieChart.Data("Famale", numOfFamale));
        dataGenderChart.forEach(d
                -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );

        genderChart.setData(dataGenderChart);
    }
    //
    //

    private void initLineChart() {
        //defining a series
        series2011 = new XYChart.Series();
        series2012 = new XYChart.Series();
        series2013 = new XYChart.Series();
        series2014 = new XYChart.Series();
        series2015 = new XYChart.Series();
        series2016 = new XYChart.Series();

        series2011.setName("2011");
        series2012.setName("2012");
        series2013.setName("2013");
        series2014.setName("2014");
        series2015.setName("2015");
        series2016.setName("2016");

        // Initialize series
        for (int i = 1; i <= 12; i++) {
            series2011.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
            series2012.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
            series2013.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
            series2014.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
            series2015.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
            series2016.getData().add(new XYChart.Data<>(getMonthString(i), 0.0));
        }

        // Populating the series with data
        for (int i = 0; i < 12; i++) {
            series2011.getData().set(i, new XYChart.Data(getMonthString(i + 1), i / 2));
            series2012.getData().set(i, new XYChart.Data(getMonthString(i + 1), i));
            series2013.getData().set(i, new XYChart.Data(getMonthString(i + 1), i * 2));
            series2014.getData().set(i, new XYChart.Data(getMonthString(i + 1), 12 - i));
            series2015.getData().set(i, new XYChart.Data(getMonthString(i + 1), 2));
            series2016.getData().set(i, new XYChart.Data(getMonthString(i + 1), i - 1));
        }

        lineChart.getData().addAll(series2011, series2012, series2013, series2014, series2015, series2016);
    }

    private void initBarChart() {
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();


        XYChart.Series xyAlgo = new XYChart.Series();
        XYChart.Series xyAnalyse = new XYChart.Series();
        XYChart.Series xyAlgebra = new XYChart.Series();
        XYChart.Series xySM = new XYChart.Series();

        xyAlgo.setName("Algorithm");
        xyAnalyse.setName("Analyse");
        xyAlgebra.setName("Algebra");
        xySM.setName("Structure Machine");

        xyAlgo.getData().add(new XYChart.Data<>("September", 20));
        xyAnalyse.getData().add(new XYChart.Data<>("September", 50));
        xyAlgebra.getData().add(new XYChart.Data<>("September", 30));
        xySM.getData().add(new XYChart.Data<>("September", 10));

        xyAlgo.getData().add(new XYChart.Data<>("October", 20));
        xyAnalyse.getData().add(new XYChart.Data<>("October", 10));
        xyAlgebra.getData().add(new XYChart.Data<>("October", 80));
        xySM.getData().add(new XYChart.Data<>("October", 30));

        xyAlgo.getData().add(new XYChart.Data<>("November", 15));
        xyAnalyse.getData().add(new XYChart.Data<>("November", 10));
        xyAlgebra.getData().add(new XYChart.Data<>("November", 20));
        xySM.getData().add(new XYChart.Data<>("November", 13));

        barChart.getData().addAll(xyAlgo, xyAnalyse, xyAlgebra, xySM);
    }

    private String getMonthString(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "";
    }
}
