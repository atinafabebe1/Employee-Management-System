package com.ems.ems;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageEmployeerController {
    public void btnSignOut_OnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("LoginForm.fxml"));
        Scene loginScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(loginScene);
        stage.setTitle("Employee Attendance System: Log In");
        stage.setResizable(false);
        stage.show();

        Platform.runLater(()->{
            stage.sizeToScene();
            stage.centerOnScreen();
        });

    }
}
