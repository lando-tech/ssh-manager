package io.landotech.sshmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class KeyMetaDataController {

    @FXML
    private TextField comment;

    @FXML
    private  TextField passphrase;

    public String getPassphrase() {
        return passphrase.getText();
    }

    public String getComment() {
        return comment.getText();
    }
}
