package com.databrains.bi4ss.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(""));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("BI4SS Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
