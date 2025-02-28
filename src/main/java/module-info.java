module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo1 to javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    exports com.example.demo1;
    requires mysql.connector.j;
    requires java.sql;
    opens com.example.demo1.modelos;
}