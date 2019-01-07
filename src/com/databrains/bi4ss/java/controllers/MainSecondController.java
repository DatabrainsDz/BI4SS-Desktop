package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.utils.Constants;
import com.databrains.bi4ss.java.utils.Params;
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
        /* Start Gender perspective  bar chart */

        XYChart.Series xyMale = new XYChart.Series();
        XYChart.Series xyFamale = new XYChart.Series();

        xyMale.setName("Admis");
        xyFamale.setName("No Admis");

        xyMale.getData().add(new XYChart.Data<>("Admis", 25));
        xyFamale.getData().add(new XYChart.Data<>("Admis", 30));

        xyMale.getData().add(new XYChart.Data<>("Non Admis", 70));
        xyFamale.getData().add(new XYChart.Data<>("Non Admis", 60));

        chartBarGenderPres.getData().addAll(xyMale, xyFamale);

        /* End Gender perspective bar chart */

        /* Start Nationality perspective stacked bar chart */

        XYChart.Series<String, Number> seriesAdmisNat = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisNat = new XYChart.Series<>();

        seriesAdmisNat.setName("Admis");
        seriesNonAdmisNat.setName("Non Admis");

        // Data of chart
        seriesAdmisNat.getData().add(new XYChart.Data<String, Number>("Algerian", 60));
        seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>("Algerian", 40));

        seriesAdmisNat.getData().add(new XYChart.Data<String, Number>("Foreign", 40));
        seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>("Foreign", 60));

        chartStackedBarNatPres.getData().addAll(seriesAdmisNat, seriesNonAdmisNat);

        /* End Nationality perspective stacked bar chart */

        /* Start Origin City perspective stacked bar chart */

        XYChart.Series<String, Number> seriesAdmis = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmis = new XYChart.Series<>();

        seriesAdmis.setName("Admis");
        seriesNonAdmis.setName("Non Admis");

        // Data of chart
        seriesAdmis.getData().add(new XYChart.Data<String, Number>("Tiaret", 200));
        seriesNonAdmis.getData().add(new XYChart.Data<String, Number>("Tiaret", 400));

        seriesAdmis.getData().add(new XYChart.Data<String, Number>("Tissemsilet", 150));
        seriesNonAdmis.getData().add(new XYChart.Data<String, Number>("Tissemsilet", 100));

        seriesAdmis.getData().add(new XYChart.Data<String, Number>("Other wilaya", 30));
        seriesNonAdmis.getData().add(new XYChart.Data<String, Number>("Other wilaya", 20));

        seriesAdmis.getData().add(new XYChart.Data<String, Number>("Foreign", 60));
        seriesNonAdmis.getData().add(new XYChart.Data<String, Number>("Foreign", 40));

        chartStackedBarOriginCityPres.getData().addAll(seriesAdmis, seriesNonAdmis);

        /* End Origin City perspective stacked bar chart */

    }

}
