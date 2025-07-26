package io.landotech.sshmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathFormController {

    private static final Path defaultPath = Paths.get(System.getProperty("user.home"), ".ssh");
    private static final Path userHome = Paths.get(System.getProperty("user.home"));

    @FXML
    private TextField fileNameField;

    @FXML
    private TextField directoryField;

    @FXML
    private Button directoryChooserButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    private Path filePath;

    // Navigation constants
    private final NavigationController navigationController = new NavigationController();
    private final String nextResource = "/io/landotech/sshmanager/key-type-form.fxml";
    private final String nextResourceTitle = "Choose Key Type";

    @FXML
    protected void initialize() {
        directoryField.setText(defaultPath.toString());
        setDisablePropertyNextButton();
        setNextButtonAction();
    }

    @FXML
    protected void launchDirectoryChooser() {
        var directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Directory");
        directoryChooser.setInitialDirectory(userHome.toFile());
        var userDirectory = directoryChooser.showDialog(directoryChooserButton.getScene().getWindow());
        if (userDirectory != null) {
            directoryField.setText(userDirectory.getAbsolutePath());
        }
    }

    @FXML
    protected void handleNextButton() {
        setFilePath();
    }

    private void setNextButtonAction() {
        setFilePath();
        nextButton.setOnAction(event -> {
            navigationController.navigate(
                    nextResource,
                    (Stage) nextButton.getScene().getWindow(),
                    nextResourceTitle
            );
        });
    }

    private void setDisablePropertyNextButton() {
        nextButton.disableProperty().bind(
            directoryField.textProperty().isEmpty()
            .or(fileNameField.textProperty().isEmpty())
        );
    }

    private void setFilePath() {
        filePath = Paths.get(directoryField.getText() + File.separator + fileNameField.getText()); 
    }

    private Path getFilePath() {
        return this.filePath;
    }
}
