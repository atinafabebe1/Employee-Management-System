package com.ems.ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Payroll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setMinHeight(900);
        stage.setMinWidth(900);
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AttendanceHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 560);
>>>>>>> 5f4e989439f34d145cfaff5a4dd4838f5e4c6114
        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}