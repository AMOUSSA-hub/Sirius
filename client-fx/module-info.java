module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    requires edu.ezip.ing1.pds.client;
    requires client.frontend.v2;

    opens com.example to javafx.fxml;
    exports com.example;
}
