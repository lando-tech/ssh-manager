package io.landotech.sshmanager.controllers;


import com.sshtools.common.publickey.SshKeyPairGenerator;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class KeyTypeFormController {

    @FXML
    private ComboBox<String> keyTypeComboBox;

    @FXML
    private ComboBox<Integer> keySizeComboBox;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    private final NavigationController navigationController = new NavigationController();
    private final String nextResource = "/io/landotech/sshmanager/next.fxml";
    private final String prevResource = "/io/landotech/sshmanager/file-path-form.fxml";

    @FXML
    protected void initialize() {
        keySizeComboBox.disableProperty().bind(Bindings.createBooleanBinding(() ->
                SshKeyPairGenerator.ED25519.equals(keyTypeComboBox.getSelectionModel().getSelectedItem()),
                keyTypeComboBox.getSelectionModel().selectedItemProperty()
        ));
        setupKeyTypeComboBox();
        setupKeySizeComboBox();
        handleBackButton();
    }

    private void handleNextButton() {

    }

    private void handleBackButton() {
        backButton.setOnAction(event -> {
            navigationController.navigate(
                    prevResource,
                    (Stage) backButton.getScene().getWindow(),
                    "Select File Path"
            );
        });
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
