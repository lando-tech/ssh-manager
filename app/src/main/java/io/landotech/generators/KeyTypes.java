package io.landotech.generators;

public enum KeyTypes {
    ED_25519("ed25519"),
    RSA("rsa");

    private String value;
    KeyTypes(String keyType) {
        this.value = keyType;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
