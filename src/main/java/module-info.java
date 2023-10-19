module com.mycompany.javafx_db_example {
    requires org.mariadb.jdbc;
    requires slf4j.api;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.javafx_db_example to javafx.fxml;
    exports com.mycompany.javafx_db_example;
}
