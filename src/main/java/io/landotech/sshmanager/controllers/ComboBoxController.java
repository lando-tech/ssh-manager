package io.landotech.sshmanager.controllers;

import com.sshtools.common.publickey.SshKeyPairGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ComboBoxController {

    public static final String ssh_rsa = SshKeyPairGenerator.SSH2_RSA;
    public static final String ed25519 = SshKeyPairGenerator.ED25519;

    public static final int minimumKeySize = 2048;
    public static final int mediumKeySize = 3072;
    public static final int defaultKeySize= 4096;

    @FXML
    private ComboBox<String> keyTypeComboBox;

    @FXML
    private ComboBox<Integer> keySizeComboBox;

    @FXML
    public void initialize() {
        initializeKeyTypes();
        initializeKeySizes();
        initializeActionEvent();
    }

    public void initializeKeyTypes() {
        keyTypeComboBox.setItems(FXCollections.observableArrayList(
                SshKeyPairGenerator.SSH2_RSA,
                SshKeyPairGenerator.ED25519
        ));
    }

    public void initializeKeySizes() {
        keySizeComboBox.setItems(FXCollections.observableArrayList(
                minimumKeySize,
                mediumKeySize,
                defaultKeySize
        ));
    }

    public void initializeActionEvent() {
        keyTypeComboBox.setOnAction(actionEvent -> {
            toggleKeySizeEnable();
        });
    }

    public void toggleKeySizeEnable() {
        var selection = keyTypeComboBox.getSelectionModel().getSelectedItem();
        resetKeySize();
        if (selection != null) {
            keySizeComboBox.setDisable(!selection.equals(ssh_rsa));
        } else {
            keySizeComboBox.setDisable(true);
        }
    }

    public void resetKeyType() {
        keyTypeComboBox.getSelectionModel().clearSelection();
    }

    public void resetKeySize() {
        keySizeComboBox.getSelectionModel().clearSelection();
    }
}
