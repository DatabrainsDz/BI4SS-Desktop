package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.type.Subjects;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckTreeView;

import javax.security.auth.Subject;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLMDController implements Initializable {

    /* Radio Select Type view [Admis and not/Subject] */
    @FXML
    private JFXRadioButton radioAdmisAndNot, radioSubject;

    // For select subject names
    @FXML
    private CheckTreeView<String> treeSubject;

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

    // Selected study level for changing data of combo subject
    public static int selectedLevel = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            treeSubject.setVisible(false);
        });
        radioSubject.setOnAction(e -> {
            gridAdmis.setVisible(false);
            gridSubject.setVisible(true);
            treeSubject.setVisible(true);
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
        // Create the data to show in the CheckTreeView
        CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Subject");
        root.setExpanded(true);

        // Set the data to the comboBox
        switch (selectedLevel) {
            case 1:
                for (String subject : Subjects.LMD1)
                    root.getChildren().add(new CheckBoxTreeItem<String>(subject));
                break;
            case 2:
                for (String subject : Subjects.LMD2)
                    root.getChildren().add(new CheckBoxTreeItem<String>(subject));
                break;
            case 3:
                for (String subject : Subjects.LMD3)
                    root.getChildren().add(new CheckBoxTreeItem<String>(subject));
                break;
        }

        treeSubject.setRoot(root);

        // and listen to the relevant events (e.g. when the checked items change).
        treeSubject.getCheckModel().getCheckedItems().addListener(new ListChangeListener<TreeItem<String>>() {
            public void onChanged(ListChangeListener.Change<? extends TreeItem<String>> c) {
                System.out.println(treeSubject.getCheckModel().getCheckedItems());
            }
        });
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

        for(String subject: Subjects.LMD1) {
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

        for(String subject: Subjects.LMD1) {
            seriesTiaret.getData().add(new XYChart.Data<String, Number>(subject, 40));
            seriesVialar.getData().add(new XYChart.Data<String, Number>(subject, 25));
            seriesForeign.getData().add(new XYChart.Data<String, Number>(subject, 5));
        }

        chartStackedBarSubjectByCity.getData().addAll(seriesTiaret, seriesVialar, seriesForeign);

        /* End Subject By City Stacked Bar chart */
    }
}
