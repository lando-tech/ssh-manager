package io.landotech.sshmanager;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeyGenController {

    @FXML
    private ComboBox<String> keyTypeCombo;

    @FXML
    public ComboBox<Integer> keySizeCombo;

    @FXML
    private TextField commentField;

    @FXML
    private PasswordField passphraseField;

    @FXML
    private Button directoryBrowseButton;

    @FXML
    private TextField fileNameField;

    @FXML
    private TextField filePathField;
    private Path customPath;
    private final Path defaultPath = Paths.get(System.getProperty("user.home"), ".ssh");

    @FXML
    private ToggleGroup directoryToggleGroup;

    @FXML
    private RadioButton defaultDirectoryButton;

    @FXML
    private RadioButton customPathButton;

    @FXML
    private Button generateButton;

    @FXML
    private Label infoMessage;

    @FXML
    public void initialize() {

        filePathField.setText(defaultPath.toString());
        copyTextFieldInput();
        directoryToggleGroup.selectedToggleProperty().addListener((Observable changeListener) -> enableDirectoryChooserButton());
        directoryBrowseButton.setOnAction(actionEvent -> launchDirectoryChooser());
        keyTypeCombo.setOnAction(actionEvent -> {
            if (keyTypeCombo.getSelectionModel().getSelectedItem().equals("ssh-rsa")) {
                keySizeCombo.setDisable(false);
            }
        });
        generateButton.setOnAction(actionEvent -> createSshKeys());
    }

    public void createSshKeys() {
        var keyType = keyTypeCombo.getSelectionModel().getSelectedItem();
        var commentText = commentField.getText();
        var passphraseText = passphraseField.getText();

        var keyGen = new SshKeyGen(passphraseText, commentText);
        var keyPair = keyGen.generateKeyPair(keyType);

        if (customPath != null) {
            Path finalPath = Paths.get(customPath.toString(), fileNameField.getText());
            if (keyGen.createSshKeyFiles(keyPair, finalPath)) {
                infoMessage.setText("SSH keys generated @ " + finalPath);
                clearTextFields();
            } else {
                infoMessage.setText("Failed to create SSH keys!");
            }
        } else {
            Path finalPath = Paths.get(defaultPath.toString(), fileNameField.getText());
            if (keyGen.createSshKeyFiles(keyPair, finalPath)) {
                infoMessage.setText("SSH keys generated @ " + finalPath);
            } else {
                infoMessage.setText("Failed to create SSH keys!");
            }
        }
    }

    public void copyTextFieldInput() {
        String baseText = filePathField.getText();
        fileNameField.textProperty().addListener((obs, oldValue, newValue) -> {
            filePathField.setText(baseText + File.separator + fileNameField.getText());
        });
    }

    public void enableDirectoryChooserButton() {
        if (directoryToggleGroup.getSelectedToggle().equals(customPathButton)) {
            directoryBrowseButton.setDisable(false);
            filePathField.setDisable(false);
        } else if (directoryToggleGroup.getSelectedToggle().equals(defaultDirectoryButton)) {
            directoryBrowseButton.setDisable(true);
            filePathField.setDisable(true);
            if (!filePathField.getText().isEmpty()) {
                filePathField.setText(defaultPath + File.separator + fileNameField.getText());
            } else {
                filePathField.setText(defaultPath.toString());
            }
        }
    }

    public void launchDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File customFilePath = directoryChooser.showDialog(new Stage());
        if (customFilePath != null) {
            customPath = customFilePath.toPath();
            filePathField.setText(customPath.toString() + File.separator + fileNameField.getText());
        }
    }

    public void clearTextFields() {
        commentField.clear();
        passphraseField.clear();
    }

}
