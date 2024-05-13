module edu.ezip.ing1.pds.smartcitybyezip {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.ezip.ing1.pds.smartcitybyezip to javafx.fxml;
    exports edu.ezip.ing1.pds.smartcitybyezip;
}