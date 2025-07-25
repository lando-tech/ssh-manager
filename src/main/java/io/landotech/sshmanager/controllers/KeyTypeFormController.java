package io.landotech.sshmanager.controllers;


import javafx.fxml.FXML;
import javafx.collections.FXCollections;


public class KeyTypeFormController {

    @FXML
    private ComboBox<String> keyTypeComboBox;

    @FXML
    private ComboBox<Integer> keySizeComboBox;

    @FXML
    protected void initialize() {
        setupKeyTypeComboBox();
        setupKeySizeComboBox();
    }

    private void setupKeyTypeComboBox() {
        keyTypeComboBox.getItems().addAll(FXCollections.obeservableArrayList(
                SshKeyPairGenerator.SSH2_RSA,
                SshKeyPairGenerator.ED25519
        ));
    }

    private void setupKeySizeComboBox() {
        keySizeComboBox.getItems().addAll(FXCollections.obeservableArrayList(
                2048,
                3072,
                4096
        ));
    }
}
