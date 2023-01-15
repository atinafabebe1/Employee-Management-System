module com.ems.ems {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ems.ems to javafx.fxml;
    exports com.ems.ems;
}