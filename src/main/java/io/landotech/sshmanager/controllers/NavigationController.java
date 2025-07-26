package io.landotech.sshmanager.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {

    public void navigate(String resourceName, Stage currentStage, String sceneTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationController.class.getResource(resourceName)
            );
            Scene scene = new Scene(loader.load(), 600, 400);
            currentStage.setScene(scene);
            currentStage.setTitle(sceneTitle);
        } catch (IOException io) {
            System.err.println("Unable to load " + resourceName);
        }
    }
}
