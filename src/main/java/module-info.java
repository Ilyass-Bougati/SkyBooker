module skybooker.skybooker {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires hibernate.jpa;

    exports skybooker to javafx.graphics;
    exports skybooker.entity;

    opens skybooker to javafx.fxml;
//    opens skybooker.entity to javafx.fxml;
    opens skybooker.entity to org.hibernate.orm.core;
}