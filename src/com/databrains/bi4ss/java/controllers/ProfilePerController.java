package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.models.Profile;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePerController implements Initializable {

    /* Input attributes */

    @FXML
    private JFXRadioButton radioMale, radioFemale;

    @FXML
    private JFXToggleButton tglIsAlgerienne;

    @FXML
    private JFXTextField fieldNumCity, fieldBacAverage, fieldAge;

    @FXML
    private ImageView imgResult;

    @FXML // For show admitted or adjourned !
    private Label lblResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onCalc() {
        // Missing check input if valid !

        Profile profile = new Profile(
                radioMale.isSelected(),
                tglIsAlgerienne.isSelected(),
                Integer.parseInt(fieldNumCity.getText()),
                Double.parseDouble(fieldBacAverage.getText()),
                Integer.parseInt(fieldAge.getText()));

        boolean isSuccess = true;
        if(isSuccess) {
            //imgResult.setImage(new Image(""));
            imgResult.setVisible(true);

            lblResult.setText("Success !");
            lblResult.setStyle("-fx-text-fill: #0f9d58");
            lblResult.setVisible(true);
        } else {
            //imgResult.setImage(new Image(""));
            imgResult.setVisible(true);

            lblResult.setText("Echec !");
            lblResult.setStyle("-fx-text-fill: #FF3D22");
            lblResult.setVisible(true);
        }
    }

    @FXML
    private void onClose() {
        SystemController.dialogProfilePer.close();
    }

}
