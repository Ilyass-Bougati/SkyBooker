module skybooker.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.sql;

    opens skybooker.client to javafx.fxml;
    exports skybooker.client;
    exports skybooker.client.DTO;
    exports skybooker.client.enums;
    exports skybooker.client.controller;
    opens skybooker.client.controller to javafx.fxml;
    exports skybooker.client.utils;
    opens skybooker.client.utils to javafx.fxml;
}