module com.example.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.shop to javafx.fxml;
    exports com.example.shop;
}