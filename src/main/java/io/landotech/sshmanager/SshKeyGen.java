package io.landotech.sshmanager;

import com.sshtools.common.publickey.SshKeyPairGenerator;
import com.sshtools.common.ssh.SshException;
import com.sshtools.common.ssh.components.SshKeyPair;
import com.sshtools.common.publickey.SshKeyUtils;

import java.io.File;
import java.io.IOException;

public class SshKeyGen {

    private final String passphrase;
    private final String comment;

    public SshKeyGen(String passphrase, String comment) {
        this.passphrase = passphrase;
        this.comment = comment;
    }

    public SshKeyPair generateKeyPair(String keyType) {
        try {
            return SshKeyPairGenerator.generateKeyPair(keyType);
        } catch (IOException | SshException io) {
            System.out.println("Failed to generate key pair: " + io.getMessage());
        }
        return null;
    }

    public SshKeyPair generateKeyPair(String keyType, int keySizeInBytes) {
        try {
            return SshKeyPairGenerator.generateKeyPair(keyType, keySizeInBytes);
        } catch (IOException | SshException io) {
            System.out.println("Failed to generate key pair: " + io.getMessage());
        }
        return null;
    }

    public boolean createSshKeyFiles(SshKeyPair keyPair, File filePath) {
        try {
            SshKeyUtils.createPrivateKeyFile(keyPair, this.passphrase, filePath);
            SshKeyUtils.createPublicKeyFile(keyPair.getPublicKey(), this.comment, new File(filePath + ".pub"));
            return true;
        } catch (IOException e) {
            System.out.println("Unable to create Public/Private Key files: " + e.getMessage());
            return false;
        }
    }
}
