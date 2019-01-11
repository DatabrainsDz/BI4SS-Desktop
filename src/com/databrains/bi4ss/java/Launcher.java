package com.databrains.bi4ss.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/System.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("BI4SS Application");
        Launcher.stage = stage;
        stage.show();
    }

    public static void centerOnScreen() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Launcher.stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        Launcher.stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
