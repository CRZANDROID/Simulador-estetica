module com.example.simuladorestetica {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simuladorestetica to javafx.fxml;
    exports com.example.simuladorestetica;
    exports com.example.simuladorestetica.controllers;
    opens com.example.simuladorestetica.controllers to javafx.fxml;
}