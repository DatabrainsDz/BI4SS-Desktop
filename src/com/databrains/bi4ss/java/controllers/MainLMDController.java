package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.models.YearData;
import com.databrains.bi4ss.java.type.Subjects;
import com.databrains.bi4ss.java.utils.Params;
import com.databrains.bi4ss.java.webservice.WebService;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainLMDController implements Initializable {

    // Label show selected Year and Level
    @FXML
    private Label lblYear, lblLevel;

    /* Radio Select Type view [Admis and not/Subject] */
    @FXML
    private JFXRadioButton radioAdmisAndNot, radioSubject;

    // For select subject names
    @FXML
    private JFXComboBox<String> comboSubject;

    /* Radio Select Semester [Full/Semester 1/Semester 2] */
    @FXML
    private JFXRadioButton radioFull, radioSemesterOne, radioSemesterTwo;

    /* Box for switch between graphe of admis and subject */
    @FXML
    private GridPane gridAdmis, gridSubject;

    /* Charts of admis and Not */
    @FXML
    private StackedBarChart chartStackedAdmisNationality, chartStackedAdmisCity, chartStackedAdmisGender;

    /* Charts of Subject */
    @FXML
    private StackedBarChart chartStackedBarSubjectByAdmisGender, chartStackedBarSubjectByCity,chartStackedBarSubjectByNationality;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblYear.setText(String.valueOf(Params.selectedYear));
        lblYear.setOnMouseClicked(e -> MainSecondController.dialogLevel.close()); // Back to Main Second
        switch (Params.selectedLevel) {
            case "MIAS1":
                lblLevel.setText("Licence 1");
                break;
            case "MIAS2":
                lblLevel.setText("Licence 2");
                break;
            case "MIAS3":
                lblLevel.setText("Licence 3");
                break;
            case "MGI1":
                lblLevel.setText("Master 1");
                break;
            case "MGI2":
                lblLevel.setText("Master 2");
                break;
        }

        // Init the name of radio (: Selected Level)
        switch (Params.selectedLevel) {
            case "MIAS1":
                radioSemesterOne.setText("Semester One");
                radioSemesterTwo.setText("Semester Two");
                break;
            case "MIAS2":
                radioSemesterOne.setText("Semester One");
                radioSemesterTwo.setText("Semester Two");
                break;
            case "MIAS3":
                radioSemesterOne.setText("Semester Three");
                radioSemesterTwo.setText("Semester Four");
                break;
            case "MGI1":
                radioSemesterOne.setText("Semester One");
                radioSemesterTwo.setText("Semester Two");
                break;
            case "MGI2":
                radioSemesterOne.setText("Semester Three");
                radioSemesterTwo.setText("Semester Four");
                break;

        }

        initComboSubject();
        initRadio();
        initChartsAdmisAndNot();
        initChartsSubjects();
    }

    private void initRadio() {
        /* Radio Of Category view [AdmisInfo and Not/Subject] */
        radioAdmisAndNot.setOnAction(e -> {
            gridAdmis.setVisible(true);
            gridSubject.setVisible(false);
            comboSubject.setVisible(false);
        });
        radioSubject.setOnAction(e -> {
            gridAdmis.setVisible(false);
            gridSubject.setVisible(true);
            comboSubject.setVisible(true);
        });

        /* Radio Of Year Semester view [Full/ by Semester] */
        radioFull.setOnAction(e -> {

        });
        radioSemesterOne.setOnAction(e -> {

        });
        radioSemesterTwo.setOnAction(e -> {

        });
    }

    private void initComboSubject() {
        // Set the data (subjects) to the comboBox
        switch (Params.selectedLevel) {
            case "MIAS1":
                comboSubject.getItems().addAll(Subjects.LICENCE1);
                break;
            case "MIAS2":
                comboSubject.getItems().addAll(Subjects.LICENCE2);
                break;
            case "MIAS3":
                comboSubject.getItems().addAll(Subjects.LICENCE3);
                break;
        }
    }

    private void initChartsAdmisAndNot() {
        // Get data from server
        YearData yearData = WebService.getLevelDataByAdmis(Params.selectedYear, Params.selectedLevel);
        if (yearData == null)
            return;

        /* Start by Nationality Stacked Bar Chart */
        List<AdmisInfo> dataNationality = yearData.getAdmisNationality();

        /* End by Nationality Stacked Bar Chart */

        XYChart.Series<String, Number> seriesAdmisNat = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisNat = new XYChart.Series<>();

        seriesAdmisNat.setName("Admitted");
        seriesNonAdmisNat.setName("Adjouned");

        List<AdmisInfo> admisByNationality = yearData.getAdmisNationality();
        if (admisByNationality != null) {
            // Data of chart
            for (AdmisInfo admisInfo : admisByNationality) {
                seriesAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
                seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
            }
        }

        chartStackedAdmisNationality.getData().addAll(seriesAdmisNat, seriesNonAdmisNat);


        /* Start by City Stacked Bar Chart */
        List<AdmisInfo> dataCity = yearData.getAdmisCity();

        /* End by City Stacked Bar Chart */

        XYChart.Series<String, Number> seriesAdmisCity = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisCity = new XYChart.Series<>();

        seriesAdmisCity.setName("AdmisInfo");
        seriesNonAdmisCity.setName("Non AdmisInfo");

        List<AdmisInfo> admisByCity = yearData.getAdmisCity();
        if (admisByCity != null) {
            // Data of chart
            for (AdmisInfo admisInfo : admisByCity) {
                seriesAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
                seriesNonAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
            }
        }

        chartStackedAdmisCity.getData().addAll(seriesAdmisCity, seriesNonAdmisCity);

        /* Start by Gender Stacked Bar Chart */

        List<AdmisInfo> admisByGender = yearData.getAdmisGender();
        if (admisByGender != null) {
            for (AdmisInfo admisInfo : admisByGender) {
                XYChart.Series xyGender = new XYChart.Series();
                xyGender.setName(admisInfo.getName());
                xyGender.getData().add(new XYChart.Data<>("Admitted", admisInfo.getAdmis()));
                xyGender.getData().add(new XYChart.Data<>("Adjourned", admisInfo.getAjourne()));
                chartStackedAdmisGender.getData().add(xyGender);
            }
        }

        /* End by Gender Stacked Bar Chart */

    }

    private void initChartsSubjects() {
        char semesterFlag = '2';
        if (radioFull.isSelected()) semesterFlag = '?';
        else if (radioSemesterOne.isSelected()) semesterFlag = '1';

        YearData yearData = WebService.getLevelDataBySubjects(Params.selectedYear, Params.selectedLevel, semesterFlag);
        if(yearData == null) {
            System.out.println("Returned null");
            return;
        }

        /* Start Subject By AdmisInfo Gender Stacked Bar chart */
        List<AdmisInfo> byGenderList = yearData.getAdmisGender();
        XYChart.Series<String, Number> seriesMale = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesFemale = new XYChart.Series<>();

        seriesMale.setName("Male");
        seriesFemale.setName("Female");
        if (byGenderList != null)
            for (AdmisInfo a : byGenderList) {
                seriesMale.getData().add(new XYChart.Data<String, Number>(a.getName(), a.getAdmis()));
                seriesFemale.getData().add(new XYChart.Data<String, Number>(a.getName(), a.getAjourne()));
            }

        chartStackedBarSubjectByAdmisGender.getData().addAll(seriesMale, seriesFemale);

        /* End Subject By AdmisInfo Gender Stacked Bar chart */

        /* Start Subject By City Stacked Bar chart */
        List<AdmisInfo> byCityList = yearData.getAdmisCity();

        XYChart.Series<String, Number> seriesTiaret = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesVialar = new XYChart.Series<>();
        //XYChart.Series<String, Number> seriesForeign = new XYChart.Series<>();

        seriesTiaret.setName("Tiaret");
        seriesVialar.setName("Tissemsilet");
        //seriesForeign.setName("Foreign");
        if (byCityList != null)
            for (AdmisInfo info : byCityList) {
                seriesTiaret.getData().add(new XYChart.Data<String, Number>(info.getName(), info.getAdmis()));
                seriesVialar.getData().add(new XYChart.Data<String, Number>(info.getName(), info.getAjourne()));
                //seriesForeign.getData().add(new XYChart.Data<String, Number>(subject, 5));
            }

        chartStackedBarSubjectByCity.getData().addAll(seriesTiaret, seriesVialar/*, seriesForeign*/);

        /* End Subject By City Stacked Bar chart */
        /* Start by Nationality Stacked Bar Chart */
        List<AdmisInfo> dataNationality = yearData.getAdmisNationality();

        /* End by Nationality Stacked Bar Chart */

        XYChart.Series<String, Number> seriesAdmisNat = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisNat = new XYChart.Series<>();

        seriesAdmisNat.setName("Algerien");
        seriesNonAdmisNat.setName("Foreign");

        List<AdmisInfo> admisByNationality = yearData.getAdmisNationality();
        if (admisByNationality != null) {
            // Data of chart
            for (AdmisInfo admisInfo : admisByNationality) {
                seriesAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
                seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
            }
        }

        chartStackedBarSubjectByNationality.getData().addAll(seriesAdmisNat, seriesNonAdmisNat);
    }
}
