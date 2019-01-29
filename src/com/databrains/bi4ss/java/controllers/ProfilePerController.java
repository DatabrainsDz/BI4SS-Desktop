package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.webservice.WebService;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePerController implements Initializable {

    @FXML
    private StackPane root;

    /* Input attributes */

    @FXML
    private JFXRadioButton radioMale, radioFemale;

    @FXML
    private JFXToggleButton tglIsAlgerienne;

    @FXML
    private JFXTextField fieldNumCity, fieldBacAverage, fieldAge;

    @FXML // Combo select type of algorithm using in calculate
    private JFXComboBox<String> comboAlgo;

    @FXML
    private ImageView imgGender, imgResult;

    @FXML // For show admitted or adjourned !
    private Label lblResult;

    // Like toast in android
    private JFXSnackbar toastMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboAlgo.getItems().addAll("KNN", "SVM");
        comboAlgo.getSelectionModel().selectFirst();

        // for show error msg like Toast in android
        toastMsg = new JFXSnackbar(root);

        /* Listener for change img when select gender */
        String packageProfile = "com/databrains/bi4ss/resources/images/profiles/";
        radioMale.setOnAction(e -> imgGender.setImage(new Image(packageProfile + "studentMale.jpg")));
        radioFemale.setOnAction(e -> imgGender.setImage(new Image(packageProfile + "studentFemale.jpg")));

    }

    @FXML
    private void onCalc() {
        if (fieldNumCity.getText().isEmpty()) {
            toastMsg.show("Please select city !", 2000);
            return;
        }
        if (fieldBacAverage.getText().isEmpty()) {
            toastMsg.show("Please select Bac average !", 2000);
            return;
        }
        if (fieldAge.getText().isEmpty()) {
            toastMsg.show("Please select Age !", 2000);
            return;
        }

        char algo = (comboAlgo.getSelectionModel().getSelectedIndex() == 1) ? '1' : '0';
        char gender = (radioMale.isSelected()) ? 'M' : 'F';
        char nationality = (tglIsAlgerienne.isSelected()) ? '1' : '0';
        String city = fieldNumCity.getText().trim();
        double bac = Double.parseDouble(fieldBacAverage.getText());
        int age = Integer.parseInt(fieldAge.getText());

        boolean isSuccess = WebService.getPredictionProfil(algo, gender, nationality, city, bac, age);
        if (isSuccess) {
            imgResult.setImage(new Image("com/databrains/bi4ss/resources/images/profiles/successImage.jpg"));
            lblResult.setText("Success !");
        } else {
            imgResult.setImage(new Image("com/databrains/bi4ss/resources/images/profiles/failureImage.jpg"));
            lblResult.setText("Failure !");
        }
        imgResult.setVisible(true);

}

    @FXML
    private void onClose() {
        SystemController.dialogProfilePer.close();
    }

}
