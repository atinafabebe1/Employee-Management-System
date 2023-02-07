package com.ems.ems;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageEmployeerController implements Initializable{

    @FXML
    private StackPane containerSP;

    @FXML
    public void signOutBtnHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("Login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene loginScene=new Scene(root);
        stage.setScene(loginScene);
        stage.setTitle("Employee Attendance System: Log In");
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Parent fxml=FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage=new Stage();
            Scene scene=new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void home(javafx.event.ActionEvent actionEvent ) throws IOException{
        Parent fxml=FXMLLoader.load(getClass().getResource("HomePageEmployeer.fxml"));
        containerSP.getChildren().removeAll();
        containerSP.getChildren().setAll(fxml);
    }
    @FXML
    public void recruitment(javafx.event.ActionEvent actionEvent ) throws IOException{
        Parent fxml=FXMLLoader.load(getClass().getResource("Recruitment.fxml"));
        containerSP.getChildren().removeAll();
        containerSP.getChildren().setAll(fxml);
    }
    @FXML
    public void payroll(javafx.event.ActionEvent actionEvent ) throws IOException{
        Parent fxml=FXMLLoader.load(getClass().getResource("Payroll.fxml"));
        containerSP.getChildren().removeAll();
        containerSP.getChildren().setAll(fxml);
    }
    @FXML
    public void attendanceHome(javafx.event.ActionEvent actionEvent ) throws IOException{
        Parent fxml=FXMLLoader.load(getClass().getResource("AttendanceHome.fxml"));
        containerSP.getChildren().removeAll();
        containerSP.getChildren().setAll(fxml);
    }
    @FXML
    public void employee(javafx.event.ActionEvent actionEvent ) throws IOException{
        Parent fxml=FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));
        containerSP.getChildren().removeAll();
        containerSP.getChildren().setAll(fxml);
    }
}
