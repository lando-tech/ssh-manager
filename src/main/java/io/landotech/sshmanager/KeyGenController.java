package io.landotech.sshmanager;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.input.InputMethodEvent;
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
    private File defaultPath = new File(System.getProperty("user.home") + "/.ssh");

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
        this.generateButton.setOnAction(actionEvent -> {
            createSshKeys();
        });
        this.directoryToggleGroup.selectedToggleProperty().addListener(changeListener -> {
            enableDirectoryChooserButton();
        });
        this.directoryBrowseButton.setOnAction(actionEvent -> {
            launchDirectoryChooser();
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
        if (this.directoryToggleGroup.getSelectedToggle().equals(this.customPathButton)) {
            this.directoryBrowseButton.setDisable(false);
            this.filePathField.setDisable(false);
        } else if (this.directoryToggleGroup.getSelectedToggle().equals(this.defaultDirectoryButton)) {
            this.directoryBrowseButton.setDisable(true);
            this.filePathField.setDisable(true);
            this.filePathField.setText(defaultPath.getAbsolutePath());
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
        this.commentField.clear();
        this.passphraseField.clear();
    }
}
