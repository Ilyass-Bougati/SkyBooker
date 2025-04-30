module skybooker.skybooker {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.persistence;

    exports skybooker.skybooker to javafx.graphics;
    exports skybooker.skybooker.entity;

    opens skybooker.skybooker to javafx.fxml;
    opens skybooker.skybooker.entity to javafx.fxml;
}