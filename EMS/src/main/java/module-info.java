module com.ems.ems {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires json.simple;
    requires fontawesomefx;


    opens com.ems.ems to javafx.fxml;
    exports com.ems.ems;
}