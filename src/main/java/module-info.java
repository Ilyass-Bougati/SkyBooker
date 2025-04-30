module skybooker.skybooker {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.persistence;

    exports skybooker.skybooker;
    exports skybooker;
    exports skybooker.skybooker.entity;

    opens skybooker.skybooker to javafx.fxml;
    opens skybooker to javafx.fxml;
    opens skybooker.skybooker.entity to javafx.fxml;
}