module com.ems.ems {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ems.ems to javafx.fxml;
    exports com.ems.ems;
}