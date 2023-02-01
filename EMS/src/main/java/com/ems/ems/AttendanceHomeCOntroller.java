package com.ems.ems;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class AttendanceHomeCOntroller {
    public Button btnRecordAttendance;
    public Button btnViewReports;


    public AnchorPane root;

    public void initialize(){

    }



    public void btnRecordAttendance_OnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("RecordAttendanceForm.fxml"));
        Scene attendanceScene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Employee Attendance System: RecordAttendanceFormController Attendance");
        stage.setScene(attendanceScene);
        stage.setResizable(false);
        stage.initOwner(btnRecordAttendance.getScene().getWindow());
        stage.show();

        Platform.runLater(()->{
            stage.sizeToScene();
            stage.centerOnScreen();
        });

    }

    public void btnViewReports_OnAction(ActionEvent event) {

    }
}
