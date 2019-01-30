package com.databrains.bi4ss.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane root;

    private Parent mainStartView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mainStartView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/System.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    private void onLoginTeacher() {

    }

    @FXML
    private void onLoginSupportStaff() {
        Stage mStage = (Stage) root.getScene().getWindow();
        mStage.setScene(new Scene(mainStartView));
    }
}
