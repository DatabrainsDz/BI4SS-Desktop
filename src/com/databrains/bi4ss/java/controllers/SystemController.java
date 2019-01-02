package com.databrains.bi4ss.java.controllers;

import com.databrains.bi4ss.java.Launcher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    // Root node (parent of all nodes)
    @FXML
    private StackPane root;

    @FXML // This label show date and time (dynamic clock)
    private Label lblDate;

    @FXML // icon show/hide menu
    private JFXHamburger hamburgerMenu;
    // For make animation to hamburgerMenu
    private HamburgerSlideCloseTransition burgerTask;

    @FXML
    private StackPane holderPane;
    // Drawer (Left Menu)
    @FXML
    private JFXDrawer drawerMenu;
    // content of drawer (view)
    private VBox menuDrawerPane;

    // GUIs (FXML)
    private StackPane mainStartView;
    private AnchorPane homeView;

    // For show Settings/About Dialog
    public static JFXDialog dialogSettings, dialogAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mainStartView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/MainStart.fxml"));
            homeView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/Home.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        // Init Dialog About
        try {
            AnchorPane aboutView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/About.fxml"));
            dialogAbout = new JFXDialog(root, aboutView, JFXDialog.DialogTransition.TOP);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        initMenu();
        initClock();

        // Launch Home view
        setNode(mainStartView);

    }

    private void initMenu() { // initalize menu (show / hide)
        try {
            menuDrawerPane = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/Menu.fxml"));
            drawerMenu.setSidePane(menuDrawerPane);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        burgerTask = new HamburgerSlideCloseTransition(hamburgerMenu);
        //burgerTask.setRate(burgerTask.getRate() * -1);
        hamburgerMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> showHideMenu());

        // Add action to Menu Item
        for(Node node : menuDrawerPane.getChildren()) {
            if(node.getAccessibleText() != null) {
                if(node.getAccessibleText().equalsIgnoreCase("btnMainStart")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(mainStartView);
                        showHideMenu();
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("onLogout")) {
                    ((JFXButton) node).setOnAction(e -> { // switch to home view
                        ((Stage) holderPane.getScene().getWindow()).setScene(new Scene(homeView));
                        Launcher.centerOnScreen(); // make stage in the center
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("onExit")) {
                    // Exit application
                    ((JFXButton) node).setOnAction(e -> Platform.exit());
                }
                // close menu
                burgerTask.setRate(burgerTask.getRate() * -1);
                burgerTask.play();
                drawerMenu.close();
                drawerMenu.setStyle("-fx-pref-width: 0px");
            }
        }
    }

    private void showHideMenu() {
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();

        if(drawerMenu.isShown()) {
            drawerMenu.close();
            drawerMenu.setStyle("-fx-pref-width: 0px");
        } else {
            drawerMenu.setStyle("-fx-pref-width: 270px");
            drawerMenu.open();
        }
    }

    private void initClock() {
        // initialize Clock Showing in home
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss    dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(dateFormat.format(date));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void onShowSettings() {
//        try {
//            VBox settingsView = FXMLLoader.load(getClass().getResource("/com/databrains/bi4ss/resources/views/Settings.fxml"));
//            dialogSettings = new JFXDialog(root, settingsView, JFXDialog.DialogTransition.BOTTOM);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//        dialogSettings.show();
    }

    @FXML
    private void onShowAbout() {
        dialogAbout.show();
    }
}
