package io.landotech.sshmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTextFieldsController {

    private static final Path defaultPath = Paths.get(System.getProperty("user.home"), ".ssh");

    @FXML
    private TextField fileNameField;

    @FXML
    private ToggleGroup directoryToggleGroup;

    @FXML
    private RadioButton defaultDirectoryRadioButton;

    @FXML
    private TextField defaultDirectoryField;

    @FXML
    private RadioButton customDirectoryRadioButton;

    @FXML
    private Button directoryChooserButton;

    @FXML
    public void initialize() {
        enableDefaultField();
        mirrorTextFields();
        defaultDirectoryRadioButton.setOnAction(actionEvent -> {
            enableDefaultField();
        });
        customDirectoryRadioButton.setOnAction(actionEvent -> {
            disableDefaultField();
        });
        directoryChooserButton.setOnAction(actionEvent -> {
            launchDirectoryChooser();
        });
    }

    public void enableDefaultField() {
        setDefaultPath();
        defaultDirectoryField.setDisable(true);
        directoryChooserButton.setDisable(true);
    }

    public void disableDefaultField() {
        defaultDirectoryField.setDisable(false);
        directoryChooserButton.setDisable(false);
    }

    public void setDefaultPath() {
        if (!fileNameField.getText().isEmpty()) {
            defaultDirectoryField.setText(defaultPath + File.separator + fileNameField.getText());
        } else {
            defaultDirectoryField.setText(defaultPath.toString());
        }
    }

    public void setCustomPath(String path) {
        defaultDirectoryField.setText(path + File.separator + fileNameField.getText());
    }

    public Path getPath() {
        return Paths.get(defaultDirectoryField.getText(), fileNameField.getText());
    }

    public void mirrorTextFields() {
        var baseText = defaultDirectoryField.getText();
        fileNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            defaultDirectoryField.setText(baseText + File.separator + fileNameField.getText());
        });
    }

    public void launchDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File customFilePath = directoryChooser.showDialog(new Stage());
        if (customFilePath != null) {
            setCustomPath(customFilePath.getPath());
        }
    }
}
