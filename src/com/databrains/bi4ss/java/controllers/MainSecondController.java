package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.utils.Constants;
import com.databrains.bi4ss.java.utils.Params;
import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSecondController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private Label lblYear;

    @FXML
    private VBox boxLevel;

    public static JFXDialog dialogLevel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblYear.setText(Params.selectedYear + " Year");
        initActionLevel();
    }

    private void initActionLevel() {
        for(int i = 0; i < boxLevel.getChildren().size(); i++) {
            final int index = i;
            boxLevel.getChildren().get(i).setOnMouseClicked(e -> {
                Params.selectedLevel = Constants.LEVELS[index];
                showMainLMD();
            });
        }
    }

    private void showMainLMD() {
        try {
            VBox mainLMDView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/MainLMD.fxml"));

            dialogLevel = new JFXDialog(root, mainLMDView, JFXDialog.DialogTransition.CENTER);
            dialogLevel.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
