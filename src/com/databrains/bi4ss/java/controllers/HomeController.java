package com.databrains.bi4ss.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    @FXML
    private AnchorPane root;

    @FXML
    private void onLoginTeacher() {
        Stage mStage = (Stage) root.getScene().getWindow();
        try {
            Parent teacherDashboardView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/TeacherDashboard.fxml"));
            mStage.setScene(new Scene(teacherDashboardView));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    private void onLoginSupportStaff() {

    }
}
