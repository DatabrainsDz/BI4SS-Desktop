package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.type.Subjects;
import com.databrains.bi4ss.java.utils.Constants;
import com.databrains.bi4ss.java.utils.Params;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckTreeView;

import javax.security.auth.Subject;
import java.net.URL;
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
    private BarChart chartBarStudents;
    @FXML
    private PieChart chartPieNonAdmisCity, chartPieNonAdmisGender;

    /* Charts of Subject */
    @FXML
    private StackedBarChart chartStackedBarSubjectByAdmisGender, chartStackedBarSubjectByCity;

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
        /* Radio Of Category view [Admis and Not/Subject] */
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
        /* Start Students Bar Chart */

        XYChart.Series xyAdmis = new XYChart.Series();
        XYChart.Series xyNoAdmis = new XYChart.Series();

        xyAdmis.setName("Admis");
        xyNoAdmis.setName("No Admis");

        xyAdmis.getData().add(new XYChart.Data<>("", 70));
        xyNoAdmis.getData().add(new XYChart.Data<>("", 60));

        chartBarStudents.getData().addAll(xyAdmis, xyNoAdmis);

        /* End Students Admis and Not Bar Chart */


        /* Start Admis and Not by City Pie Chart */

        // Data of Pie Chart
        ObservableList<PieChart.Data> dataAdmisCityChart = FXCollections.observableArrayList();
        dataAdmisCityChart.add(new PieChart.Data("Tiaret", 120));
        dataAdmisCityChart.add(new PieChart.Data("Tissemsilet", 70));
        dataAdmisCityChart.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );

        chartPieNonAdmisCity.setData(dataAdmisCityChart);

        /* End Student Admis and Not by City Chart */

        /* Start Admis and Not by Gender Pie Chart */

        // Data of Pie Chart
        ObservableList<PieChart.Data> dataAdmisGenderChart = FXCollections.observableArrayList();
        dataAdmisGenderChart.add(new PieChart.Data("Male", 120));
        dataAdmisGenderChart.add(new PieChart.Data("Famale", 190));
        dataAdmisGenderChart.forEach(d
                        -> d.nameProperty().bind(
                Bindings.concat((d.pieValueProperty() + "").replaceFirst(".*?(\\d+).*", "$1"), " ", d.getName())
                )
        );

        chartPieNonAdmisGender.setData(dataAdmisGenderChart);

        /* End Student Admis and Not by Gender Chart */
    }

    private void initChartsSubjects() {
        /* Start Subject By Admis Gender Stacked Bar chart */
        XYChart.Series<String, Number> seriesMale = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesFemale = new XYChart.Series<>();

        seriesMale.setName("Male");
        seriesFemale.setName("Female");

        for (String subject : Subjects.LICENCE1) {
            seriesMale.getData().add(new XYChart.Data<String, Number>(subject, 50));
            seriesFemale.getData().add(new XYChart.Data<String, Number>(subject, 45));
        }

        chartStackedBarSubjectByAdmisGender.getData().addAll(seriesMale, seriesFemale);

        /* End Subject By Admis Gender Stacked Bar chart */

        /* Start Subject By City Stacked Bar chart */

        XYChart.Series<String, Number> seriesTiaret = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesVialar = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesForeign = new XYChart.Series<>();

        seriesTiaret.setName("Tiaret");
        seriesVialar.setName("Tissemsilet");
        seriesForeign.setName("Foreign");

        for (String subject : Subjects.LICENCE1) {
            seriesTiaret.getData().add(new XYChart.Data<String, Number>(subject, 40));
            seriesVialar.getData().add(new XYChart.Data<String, Number>(subject, 25));
            seriesForeign.getData().add(new XYChart.Data<String, Number>(subject, 5));
        }

        chartStackedBarSubjectByCity.getData().addAll(seriesTiaret, seriesVialar, seriesForeign);

        /* End Subject By City Stacked Bar chart */
    }
}
