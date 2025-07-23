package io.landotech.sshmanager;

import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;

public class KeyGenController {

    @FXML
    private ComboBox<String> keyTypeCombo;

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
    private File customPath;
    private final File defaultPath = getSshDirectory();

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

        filePathField.setText(defaultPath.getAbsolutePath());
        copyTextFieldInput();
        generateButton.setOnAction(actionEvent -> createSshKeys());
        directoryToggleGroup.selectedToggleProperty().addListener((Observable changeListener) -> enableDirectoryChooserButton());
        directoryBrowseButton.setOnAction(actionEvent -> launchDirectoryChooser());
    }

    public void copyTextFieldInput() {
        String baseText = addCorrectSlash(filePathField.getText());
        fileNameField.textProperty().addListener((obs, oldValue, newValue) -> {
            filePathField.setText(baseText + fileNameField.getText());
        });
    }

    public void createSshKeys() {
        var keyType = keyTypeCombo.getSelectionModel().getSelectedItem();
        var commentText = commentField.getText();
        var passphraseText = passphraseField.getText();

        var keyGen = new SshKeyGen(passphraseText, commentText);
        var keyPair = keyGen.generateKeyPair(keyType);

        if (customPath != null) {
            if (keyGen.createSshKeyFiles(keyPair, customPath)) {
                infoMessage.setText("SSH keys generated @ " + customPath.getName());
                clearTextFields();
            } else {
                infoMessage.setText("Failed to create SSH keys!");
            }
        }
    }

    public void enableDirectoryChooserButton() {
        if (directoryToggleGroup.getSelectedToggle().equals(customPathButton)) {
            directoryBrowseButton.setDisable(false);
            filePathField.setDisable(false);
        } else if (directoryToggleGroup.getSelectedToggle().equals(defaultDirectoryButton)) {
            directoryBrowseButton.setDisable(true);
            filePathField.setDisable(true);
            filePathField.setText(defaultPath.getAbsolutePath());
        }
    }

    public void launchDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        customPath = directoryChooser.showDialog(new Stage());
        if (customPath != null) {
            filePathField.setText(customPath.getAbsolutePath());
        }
    }

    public void clearTextFields() {
        commentField.clear();
        passphraseField.clear();
    }

    public String addCorrectSlash(String filePath) {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("windows")) {
            return filePath + "\\";
        } else if (osName.toLowerCase().contains("linux") || osName.toLowerCase().contains("mac")) {
            return filePath + "/";
        }
        throw new RuntimeException("Could not determine Operating System on Host Machine");
    }

    public File getSshDirectory() {
        String osName = System.getProperty("os.name");
        String homeDirectory = System.getProperty("user.home");
        if (osName.toLowerCase().contains("windows")) {
            return new File(homeDirectory + "\\.ssh");
        } else if (osName.toLowerCase().contains("linux") || osName.toLowerCase().contains("mac")) {
            return new File(homeDirectory + "/.ssh");
        }
        throw new RuntimeException("Unable to determine Operating System on Host Machine");
    }
}
