module io.landotech.sshmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.sshtools.maverick.base;

    opens io.landotech.sshmanager to javafx.fxml;
    exports io.landotech.sshmanager;
}