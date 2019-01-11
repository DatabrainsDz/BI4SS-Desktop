package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.models.YearData;
import com.databrains.bi4ss.java.utils.Constants;
import com.databrains.bi4ss.java.utils.Params;
import com.databrains.bi4ss.java.webservice.WebService;
import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainSecondController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Label lblYear;

    @FXML
    private VBox boxLevel;

    /* Start Chart part */

    @FXML
    private BarChart<String, Number> chartBarGenderPres;

    @FXML
    private StackedBarChart chartStackedBarNatPres, chartStackedBarOriginCityPres;

    /* End Chart part */

    public static JFXDialog dialogLevel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblYear.setText(Params.selectedYear + " Year");
        initActionLevel();
        initCharts();
    }

    private void initActionLevel() {
        for (int i = 0; i < boxLevel.getChildren().size(); i++) {
            final int index = i;
            boxLevel.getChildren().get(i).setOnMouseClicked(e -> {
                Params.selectedLevel = Constants.LEVELS[index];
                // Show Main LMD View
                try {
                    VBox mainLMDView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/MainLMD.fxml"));

                    dialogLevel = new JFXDialog(root, mainLMDView, JFXDialog.DialogTransition.CENTER);
                    dialogLevel.show();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            });
        }
    }

    private void initCharts() {

        YearData yearData = WebService.getYearData(Params.selectedYear);

        /* Start Gender perspective  bar chart */

        List<AdmisInfo> admisByGender = yearData.getAdmisGender();

        for(AdmisInfo admisInfo : admisByGender) {
            XYChart.Series xyGender = new XYChart.Series();
            xyGender.setName(admisInfo.getName());
            xyGender.getData().add(new XYChart.Data<>("Admitted", admisInfo.getAdmis()));
            xyGender.getData().add(new XYChart.Data<>("Adjourned", admisInfo.getAjourne()));
            chartBarGenderPres.getData().add(xyGender);
        }

        /* End Gender perspective bar chart */

        /* Start Nationality perspective stacked bar chart */

        XYChart.Series<String, Number> seriesAdmisNat = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisNat = new XYChart.Series<>();

        seriesAdmisNat.setName("Admitted");
        seriesNonAdmisNat.setName("Adjouned");

        List<AdmisInfo> admisByNationality = yearData.getAdmisNationality();

        // Data of chart
        for(AdmisInfo admisInfo : admisByNationality) {
            seriesAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
            seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
        }

        chartStackedBarNatPres.getData().addAll(seriesAdmisNat, seriesNonAdmisNat);

        /* End Nationality perspective stacked bar chart */

        /* Start Origin City perspective stacked bar chart */

        XYChart.Series<String, Number> seriesAdmisCity = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisCity = new XYChart.Series<>();

        seriesAdmisCity.setName("AdmisInfo");
        seriesNonAdmisCity.setName("Non AdmisInfo");

        List<AdmisInfo> admisByCity = yearData.getAdmisCity();

        // Data of chart
        for(AdmisInfo admisInfo : admisByCity) {
            seriesAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
            seriesNonAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
        }

        chartStackedBarOriginCityPres.getData().addAll(seriesAdmisCity, seriesNonAdmisCity);

        /* End Origin City perspective stacked bar chart */

    }

}
