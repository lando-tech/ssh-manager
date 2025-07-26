package io.landotech.sshmanager.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

    public void handleNextButtonAction(String resourceName, Stage currentStage, String sceneTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            currentStage.setScene(scene);
            currentStage.setTitle(sceneTitle);
        } catch (IOException io) {
            System.err.println("Unable to load " + resourceName);
        }
    }
}
