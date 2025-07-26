package io.landotech.sshmanager.controllers;


import com.sshtools.common.publickey.SshKeyPairGenerator;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class KeyTypeFormController {

    @FXML
    private ComboBox<String> keyTypeComboBox;

    @FXML
    private ComboBox<Integer> keySizeComboBox;

    @FXML
    protected void initialize() {
        keySizeComboBox.disableProperty().bind(Bindings.createBooleanBinding(() ->
                SshKeyPairGenerator.ED25519.equals(keyTypeComboBox.getSelectionModel().getSelectedItem()),
                keyTypeComboBox.getSelectionModel().selectedItemProperty()
        ));
        setupKeyTypeComboBox();
        setupKeySizeComboBox();
    }

    private void setupKeyTypeComboBox() {
        keyTypeComboBox.getItems().addAll(FXCollections.observableArrayList(
                SshKeyPairGenerator.SSH2_RSA,
                SshKeyPairGenerator.ED25519
        ));
    }

    private void setupKeySizeComboBox() {
        keySizeComboBox.getItems().addAll(FXCollections.observableArrayList(
                2048,
                3072,
                4096
        ));
    }

    public String getKeyType() {
        return keyTypeComboBox.getSelectionModel().getSelectedItem();
    }

    public Integer getKeySize() {
        return keySizeComboBox.getSelectionModel().getSelectedItem();
    }

}
