package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.webservice.WebService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
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
        pastYearsData = new LinkedList<>();
        pastYearsData.add(new AdmisInfo("2011", 200, 400));
        pastYearsData.add(new AdmisInfo("2012", 200, 400));
        pastYearsData.add(new AdmisInfo("2013", 600, 400));
        pastYearsData.add(new AdmisInfo("2014", 800, 200));

        if(pastYearsData != null) {
            for(AdmisInfo generalYearAdmis: pastYearsData) {
                seriesAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getName()), generalYearAdmis.getAdmis()));
                seriesNoAdmis.getData().add(new XYChart.Data<String, Number>(String.valueOf(generalYearAdmis.getName()), generalYearAdmis.getAjourne()));
            }
        }

        chartStackedBarPastYears.getData().addAll(seriesAdmis, seriesNoAdmis);

        for(XYChart.Data<String, Number> data : seriesAdmis.getData())
            displayLabelForData(data);
        for(XYChart.Data<String, Number> data : seriesNoAdmis.getData())
            displayLabelForData(data);
    }

    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                Group parentGroup = (Group) parent;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }
}
