package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.AdmisInfo;
import com.databrains.bi4ss.java.models.Asociation;
import com.databrains.bi4ss.java.models.YearData;
import com.databrains.bi4ss.java.utils.Params;
import com.databrains.bi4ss.java.webservice.WebService;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainLMDController implements Initializable {

    // Label show selected Year and Level
    @FXML
    private JFXTreeTableView subjectsTable;
    private JFXTreeTableColumn<Table, String> subjectCol, modulesCol;
    @FXML
    private Label lblYear, lblLevel;

    /* Radio Select Type view [Admis and not/Subject] */
    @FXML
    private JFXRadioButton radioAdmisAndNot, radioSubject;

    // For select subject names
    @FXML
    private JFXComboBox<String> comboSubject;

    /* Radio Select Semester [Full/Semester 1/Semester 2] */
    // Container of radio select Semester
    @FXML
    private HBox boxSemester;

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
    private StackedBarChart chartStackedBarSubjectByAdmisGender, chartStackedBarSubjectByCity, chartStackedBarSubjectByNationality;
    List<String> subjects;
    ArrayList<Asociation> asociations=new ArrayList<>();

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
        initTable();
        initComboSubject();
        initRadio();
        initChartsAdmisAndNot();
        initChartsSubjects('?');

    }

    private void initRadio() {
        /* Radio Of Category view [AdmisInfo and Not/Subject] */
        radioAdmisAndNot.setOnAction(e -> {
            gridAdmis.setVisible(true);
            gridSubject.setVisible(false);
            comboSubject.setVisible(false);
            boxSemester.setVisible(false);
            initChartsAdmisAndNot();
        });
        radioSubject.setOnAction(e -> {
            gridAdmis.setVisible(false);
            gridSubject.setVisible(true);
            comboSubject.setVisible(true);
            boxSemester.setVisible(true);
            // load full semester
            initChartsSubjects('?');
            radioFull.setSelected(true);
        });

        /* Radio Of Year Semester view [Full/ by Semester] */
        radioSemesterOne.setOnMouseClicked(e -> initChartsSubjects('1'));
        radioSemesterTwo.setOnMouseClicked(e -> initChartsSubjects('2'));
        radioFull.setOnMouseClicked(e -> initChartsSubjects('?'));
    }

    private void initComboSubject() {

        // Set the data (subjects) to the comboBox
        comboSubject.valueProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
            ArrayList<Asociation> as = new ArrayList<>();
            if (newValue != null) {
                String id = newValue.toString();
                for (Asociation a : asociations) {
                    if (a.getModule().equals(id)) {
                        as.add(a);
                        break;
                    }
                }
                loadTableData(as);
            } else {

            }
        });
       /* subjects = WebService.getSubjects(Params.selectedYear,
                Params.selectedLevel.substring(0, Params.selectedLevel.length() - 1),
                Integer.parseInt(Params.selectedLevel.substring(Params.selectedLevel.length() - 1, Params.selectedLevel.length())));
        if (subjects == null)
            return;

        comboSubject.getItems().addAll(subjects);*/
    }

    private void initComboSubject(ArrayList<Asociation> list){
        comboSubject.getItems().clear();
        for (Asociation asociation : list) {
            comboSubject.getItems().add(asociation.getModule());
        }

    }

    private void initChartsAdmisAndNot() {
        /* Clear charts */
        chartStackedAdmisNationality.getData().clear();
        chartStackedAdmisCity.getData().clear();
        chartStackedAdmisGender.getData().clear();

        // Get data from server
        YearData yearData = WebService.getLevelDataByAdmis(Params.selectedYear, Params.selectedLevel);
        if (yearData == null)
            return;

        /* Start by Nationality Stacked Bar Chart */

        List<AdmisInfo> dataNationality = yearData.getAdmisNationality();

        XYChart.Series<String, Number> seriesAdmisNat = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisNat = new XYChart.Series<>();

        seriesAdmisNat.setName("Admitted");
        seriesNonAdmisNat.setName("Adjourned");

        List<AdmisInfo> admisByNationality = yearData.getAdmisNationality();
        if (admisByNationality != null) {
            // Data of chart
            for (AdmisInfo admisInfo : admisByNationality) {
                seriesAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
                seriesNonAdmisNat.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
            }
        }

        chartStackedAdmisNationality.getData().addAll(seriesAdmisNat, seriesNonAdmisNat);

        /* End by Nationality Stacked Bar Chart */

        /* Start by City Stacked Bar Chart */

        List<AdmisInfo> dataCity = yearData.getAdmisCity();

        XYChart.Series<String, Number> seriesAdmisCity = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesNonAdmisCity = new XYChart.Series<>();

        seriesAdmisCity.setName("Admitted");
        seriesNonAdmisCity.setName("Adjourned");

        List<AdmisInfo> admisByCity = yearData.getAdmisCity();
        if (admisByCity != null) {
            // Data of chart
            for (AdmisInfo admisInfo : admisByCity) {
                seriesAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAdmis()));
                seriesNonAdmisCity.getData().add(new XYChart.Data<String, Number>(admisInfo.getName(), admisInfo.getAjourne()));
            }
        }

        chartStackedAdmisCity.getData().addAll(seriesAdmisCity, seriesNonAdmisCity);

        /* End by City Stacked Bar Chart */

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

    private void initChartsSubjects(char semesterFlag) {
        /* Clear charts */
        chartStackedBarSubjectByNationality.getData().clear();
        chartStackedBarSubjectByCity.getData().clear();
        chartStackedBarSubjectByAdmisGender.getData().clear();

        YearData yearData = WebService.getLevelDataBySubjects(Params.selectedYear, Params.selectedLevel, semesterFlag);
        if (yearData == null) {
            System.out.println("Returned null");
            return;
        }

        /* Start Subject By AdmisInfo Gender Stacked Bar chart */
        List<AdmisInfo> byGenderList = yearData.getAdmisGender();
        XYChart.Series<String, Number> seriesMale = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesFemale = new XYChart.Series<>();

        seriesMale.setName("Male");
        seriesFemale.setName("Female");
        if (byGenderList != null) {
            for (AdmisInfo a : byGenderList) {
                seriesMale.getData().add(new XYChart.Data<String, Number>(a.getName(), a.getAdmis()));
                seriesFemale.getData().add(new XYChart.Data<String, Number>(a.getName(), a.getAjourne()));
            }
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


        updateAssociations(semesterFlag);
        loadTableData(asociations);
    }

    private void updateAssociations(char flag){
        if(flag=='?'){
            //if (Params.selectedLevel.charAt(Params.selectedLevel.length() -1 ))
            asociations=WebService.getAsociations(Params.selectedLevel, '1');
            ArrayList<Asociation> list=WebService.getAsociations(Params.selectedLevel, '2');
            for (Asociation asociation:list) {
                asociations.add(asociation);
            }
        }
        else asociations=WebService.getAsociations(Params.selectedLevel, flag);
        initComboSubject(asociations);
    }

    private void initTable() {
        subjectCol = new JFXTreeTableColumn<>("Subject");
        subjectCol.setPrefWidth(100);
        subjectCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Table, String>
                                                param) -> param.getValue().getValue().sub);

        modulesCol = new JFXTreeTableColumn<>("Subjects Related to");
        modulesCol.setPrefWidth(300);
        modulesCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Table, String>
                                                param) -> param.getValue().getValue().modules);
        subjectsTable.getColumns().addAll(subjectCol, modulesCol);
        subjectsTable.setShowRoot(false);

    }

    private void loadTableData(ArrayList<Asociation> list) {
        ObservableList<Table> listFoundIndex = FXCollections.observableArrayList();

        for (Asociation index : list) {
            listFoundIndex.add(new Table(index.getModule(), index.getModules()));
        }
        System.out.println(listFoundIndex);
        final TreeItem<Table> treeItem = new RecursiveTreeItem<>(listFoundIndex, RecursiveTreeObject::getChildren);
        try {
            subjectsTable.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }

    class Table extends RecursiveTreeObject<Table> {
        StringProperty sub, modules;

        public Table(String _sub, String _modules) {
            sub = new SimpleStringProperty(_sub);
            modules = new SimpleStringProperty(_modules);

        }

        public String getSub() {
            return sub.get();
        }

        public StringProperty subProperty() {
            return sub;
        }

        public void setSub(String sub) {
            this.sub.set(sub);
        }

        public String getModules() {
            return modules.get();
        }

        public StringProperty modulesProperty() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules.set(modules);
        }

        public Table(StringProperty sub, StringProperty modules) {
            this.sub = sub;
            this.modules = modules;
        }
    }

    @FXML
    private void searchSubject() {
        comboSubject.getItems().clear();
        ArrayList<Asociation> as = new ArrayList<>();
        String t = comboSubject.getEditor().getText().isEmpty() ? "" : comboSubject.getEditor().getText().toLowerCase();

        for (Asociation subject : asociations) {
            if (subject.getModule().toLowerCase().contains(t))
                comboSubject.getItems().add(subject.getModule());
            as.add(subject);
        }


        loadTableData(as);
    }

}
