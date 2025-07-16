/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.landotech.generators;

import java.security.*;

/**
 *
 * @author aaron
 */

public class KeyGenerator {

    private KeyPairGenerator keyPairGenerator;

    public KeyGenerator(String keyType) {
        setKeyPairGenerator(keyType);
    }

    public KeyGenerator(String keyType, int keySize) {
        if (keyType.equalsIgnoreCase("RSA")) {
            setKeyPairGenerator(keyType);
            this.keyPairGenerator.initialize(keySize);
        } else if (keyType.equalsIgnoreCase("Ed25519")) {
            throw new IllegalArgumentException("Size cannot be specified for key type 'ed25519'");
        } else {
            throw new IllegalArgumentException("Invalid key type '" + keyType + "'");
        }
    }

    public void setKeyPairGenerator(String keyType) {
        try {
            this.keyPairGenerator = KeyPairGenerator.getInstance(keyType);
        } catch (NoSuchAlgorithmException nsa) {
            System.err.println("NoSuchAlgorithmException");
        }
    }

    public KeyPairGenerator getKeyPairGenerator() {
        return this.keyPairGenerator;
    }

    public KeyPair getKeyPair() {
        return this.keyPairGenerator.generateKeyPair();
    }
}
