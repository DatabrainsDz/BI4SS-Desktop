package com.databrains.bi4ss.java.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AboutController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnClose() {
        SystemController.dialogAbout.close();
    }
    
    @FXML
    void goGithub() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.github.com/DatabrainsDz"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goFacebook() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/DatabrainsDz"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goLinkedIn() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/DatabrainsDz"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void goTwitter() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.twitter.com/DatabrainsDz"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
