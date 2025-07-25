package io.landotech.sshmanager.controllers;


import com.sshtools.common.publickey.SshKeyPairGenerator;
import com.sshtools.common.ssh.components.SshKeyPair;
import io.landotech.sshmanager.sshutils.SshKeyGen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class KeyGenController {

    @FXML
    ComboBoxController comboBoxController = new ComboBoxController();
    @FXML
    FileTextFieldsController fileTextFieldsController;
    @FXML
    KeyMetaDataController keyMetaDataController;
    @FXML
    private Button generateSshKeysButton;

    private String keyType;
    private Integer keySize;

    private SshKeyGen sshKeyGen;

    @FXML
    public void initialize() {
        generateSshKeysButton.setOnAction(event -> {
           createSshKeys();
        });
    }

    public void createSshKeys() {
        if (!verifyKeyType() || !verifyKeySize()) {
            return;
        }
        SshKeyPair keyPair = getSshKeyPair();
        var filePath = fileTextFieldsController.getPath();
        this.sshKeyGen.createSshKeyFiles(keyPair, filePath);
    }

    public SshKeyPair getSshKeyPair() {
        var commentText = keyMetaDataController.getComment();
        var passphraseText = keyMetaDataController.getPassphrase();
        this.sshKeyGen = new SshKeyGen(passphraseText, commentText);
        SshKeyPair keyPair;
        if (keyType.equals(SshKeyPairGenerator.SSH2_RSA)) {
            keyPair = this.sshKeyGen.generateKeyPair(keyType, keySize);
        } else {
            keyPair = this.sshKeyGen.generateKeyPair(keyType);
        }
        return keyPair;
    }

    public boolean verifyKeyType() {
        this.keyType = comboBoxController.getKeyType();
        if (this.keyType == null) {
            comboBoxController.unhideTypeLabel();
            comboBoxController.setTypeWarning("Key type is required.");
            return false;
        } else {
            comboBoxController.hideTypeLabel();
            return true;
        }
    }

    public boolean verifyKeySize() {
        this.keySize = comboBoxController.getKeySize();
        if (this.keyType.equals(SshKeyPairGenerator.SSH2_RSA) && this.keySize == null) {
            comboBoxController.unhideSizeLabel();
            comboBoxController.setSizeWarning("Key size is required when generating ssh-rsa keys.");
            return false;
        } else {
            comboBoxController.hideSizeLabel();
            return true;
        }
    }
}

//public class KeyGenController {
//
//    // Combo box to specify key-type: ssh-rsa, ed25519
//    @FXML
//    private ComboBox<String> keyTypeCombo;
//    // Combo box to specify key-size, only if ssh-rsa is chosen
//    @FXML
//    public ComboBox<Integer> keySizeCombo;
//    // The actual name of the ssh-key pair file(s), ex: myKey/myKey.pub
//    @FXML
//    private TextField fileNameField;
//    // The comment to go at the end of the public key, ex: myuser@mypc GitHub
//    @FXML
//    private TextField commentField;
//    // Secure ssh key with a passphrase
//    @FXML
//    private PasswordField passphraseField;
//    // Toggle group to switch between custom and default directory entries
//    @FXML
//    private ToggleGroup directoryToggleGroup;
//    // Default radio selection will result in the /home/user/.ssh directory to be used for key storage
//    @FXML
//    private RadioButton defaultDirectoryRadioButton;
//    // Will disable default radio button and allow manual path specification
//    @FXML
//    private RadioButton customPathRadioButton;
//    // The actual text field to hold the path to save the key pair, ex: ~/.ssh
//    @FXML
//    private TextField filePathField;
//    // Simple button to launch the native file explorer to specify custom directory
//    @FXML
//    private Button directoryChooserButton;
//    // Variable to hold the user's custom path
//    private Path customPath;
//    // Default .ssh directory path
//    private final Path defaultPath = Paths.get(System.getProperty("user.home"), ".ssh");
//    // Simple button to call generation of the ssh keys
//    @FXML
//    private Button generateSshKeysButton;
//    // Displays info message to user after generation
//    @FXML
//    private Label infoMessage;
//
//    @FXML
//    public void initialize() {
//        // Initialize default ActionEvents
//        filePathField.setText(defaultPath.toString());
//        mirrorTextFields();
//        directoryToggleGroup.selectedToggleProperty().addListener((Observable changeListener) -> getSelectedDirectoryToggle());
//        directoryChooserButton.setOnAction(actionEvent -> launchDirectoryChooser());
//        keyTypeCombo.setOnAction(actionEvent -> toggleEnableKeySizeComboBox());
//        generateSshKeysButton.setOnAction(actionEvent -> createSshKeys());
//    }
//
//    // Begin ActionEvents ->
//    public void mirrorTextFields() {
//        String baseText = filePathField.getText();
//        fileNameField.textProperty().addListener(
//                (obs, oldValue, newValue) ->
//                        filePathField.setText(baseText + File.separator + fileNameField.getText()));
//    }
//
//    public void getSelectedDirectoryToggle() {
//        if (directoryToggleGroup.getSelectedToggle().equals(customPathRadioButton)) {
//            enableCustomPath();
//        } else if (directoryToggleGroup.getSelectedToggle().equals(defaultDirectoryRadioButton)) {
//            disableCustomPath();
//            setDefaultPathText();
//        }
//    }
//
//    public void launchDirectoryChooser() {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//        File customFilePath = directoryChooser.showDialog(new Stage());
//        setCustomPathText(customFilePath);
//    }
//
//    public void toggleEnableKeySizeComboBox() {
//        var selection = keyTypeCombo.getSelectionModel().getSelectedItem();
//        if (selection != null) {
//            keySizeCombo.setDisable(!selection.equals("ssh-rsa"));
//        } else {
//            keySizeCombo.setDisable(true);
//        }
//    }
//

//
//    // <- End ActionEvents
//
//    public void enableCustomPath() {
//        directoryChooserButton.setDisable(false);
//        filePathField.setDisable(false);
//    }
//
//    public void disableCustomPath() {
//        directoryChooserButton.setDisable(true);
//        filePathField.setDisable(true);
//    }
//
//    public void setDefaultPathText() {
//        if (!filePathField.getText().isEmpty()) {
//            filePathField.setText(defaultPath + File.separator + fileNameField.getText());
//        } else {
//            filePathField.setText(defaultPath.toString());
//        }
//    }
//
//    public void setCustomPathText(File customFilePath) {
//        customPath = Objects.requireNonNull(customFilePath.toPath(), defaultPath.toString());
//        filePathField.setText(customPath + File.separator + fileNameField.getText());
//    }
//
//    public void saveSshKeyFiles(Path finalPath, SshKeyPair keyPair, SshKeyGen keyGen) {
//        if (keyGen.createSshKeyFiles(keyPair, finalPath)) {
//            infoMessage.setText("SSH Keys generated @ " + finalPath);
//            clearTextFields();
//        } else {
//            infoMessage.setText("Failed to create SSH Keys!");
//        }
//    }
//
//    public String getKeyType() {
//        return keyTypeCombo.getSelectionModel().getSelectedItem();
//    }
//
//    public int getKeySize() {
//        return keySizeCombo.getSelectionModel().getSelectedItem();
//    }
//
//    public String getComment() {
//        return commentField.getText();
//    }
//
//    public String getPassphrase() {
//        return passphraseField.getText();
//    }
//
//    public boolean verifyKeyType() {
//        return keyTypeCombo.getSelectionModel().getSelectedItem() != null;
//    }
//
//    public boolean verifyKeySize() {
//        return keySizeCombo.getSelectionModel().getSelectedItem() != null;
//    }
//
//    public boolean verifyFileNameField() {
//        return !fileNameField.getText().isEmpty();
//    }
//
//    public boolean verifyFilePathField() {
//        File tempFile = new File(filePathField.getText());
//        return !filePathField.getText().isEmpty() && tempFile.isDirectory() && tempFile.exists();
//    }
//
//    public void clearTextFields() {
//        resetComboBoxes();
//        fileNameField.clear();
//        commentField.clear();
//        passphraseField.clear();
//        filePathField.setText(defaultPath.toString());
//    }
//
//    public void resetComboBoxes() {
//        resetKeyTypeComboBox();
//        resetKeySizeComboBox();
//    }
//
//    public void resetKeyTypeComboBox() {
//        keyTypeCombo.setValue(null);
//        keyTypeCombo.getSelectionModel().clearSelection();
//    }
//
//    public void resetKeySizeComboBox() {
//        if (keySizeCombo.getSelectionModel().getSelectedItem() != null) {
//            keySizeCombo.setValue(null);
//            keySizeCombo.getSelectionModel().clearSelection();
//        }
//    }
//}
