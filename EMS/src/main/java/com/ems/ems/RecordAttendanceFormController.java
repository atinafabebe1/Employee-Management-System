package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;
import java.util.Date;

public class RecordAttendanceFormController {
    public TextField txtEmployeeID;
    public ImageView imgProfile;
    public Button btnIn;
    public Button btnOut;
    public Label lblDate;
    public Label lblID;
    public Label lblName;
    public Label lblStatus;
    public Label lblEmployeeName;
    public AnchorPane root;
    private PreparedStatement stmSearchEmployee;
    private Employee employee;

    public void initialize() {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        updateLatestAttendance();

        Connection connection = com.ems.ems.ConnectionUtil.conDB();
        try {
            stmSearchEmployee = connection.prepareStatement("SELECT * FROM users WHERE id=?");
        } catch (Exception e) {
            new RJAlert(Alert.AlertType.WARNING, "Failed to connect with DB", "Connection Error", "Error").show();
            e.printStackTrace();
            Platform.runLater(() -> {
                ((Stage) (btnIn.getScene().getWindow())).close();
            });
        }

        root.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case F10:
                    btnIn.fire();
                    break;
                case ESCAPE:
                    btnOut.fire();
                    break;
            }
        });
    }

    public void btnIn_OnAction(ActionEvent event) {
        recordAttendance(true);
    }

    public void btnOut_OnAction(ActionEvent event) {
        recordAttendance(false);
    }

    private void recordAttendance(boolean in) {
        Connection connection = com.ems.ems.ConnectionUtil.conDB();

        /* Check last record status */
        try {
            String lastStatus = null;
            PreparedStatement stm = connection.
                    prepareStatement("SELECT status, date FROM attendance WHERE id=? ORDER BY date DESC LIMIT 1");
            stm.setString(1, employee.id);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                lastStatus = rst.getString("status");
            }

            if ((lastStatus != null && lastStatus.equals("IN") && in) ||
                    (lastStatus != null && lastStatus.equals("OUT") && !in)) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("AlertForm.fxml"));
                AnchorPane root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                SimpleBooleanProperty record = new SimpleBooleanProperty(false);

                stage.setResizable(false);
                stage.setTitle("Alert!");
                stage.sizeToScene();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(this.root.getScene().getWindow());
                stage.centerOnScreen();
                stage.showAndWait();
                if (!record.getValue()) return;
            }

            PreparedStatement stm2 = connection.
                    prepareStatement("INSERT INTO attendance (date, status,id) VALUES (?,?,?)");
            stm2.setString(1, String.valueOf(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS",new Date())));
            stm2.setString(2, String.valueOf(in));
            stm2.setInt(3, Integer.parseInt(employee.id));
            if (stm2.executeUpdate() != 1) {
                throw new RuntimeException("Failed to add the attendance");
            }
            txtEmployeeID.clear();
            txtEmployeeID_OnAction(null);
            updateLatestAttendance();

        } catch (Throwable e) {
            e.printStackTrace();
            new RJAlert(Alert.AlertType.ERROR, "Failed to save the attendance, try again",
                    "Failure", "Error", ButtonType.OK).show();
        }}

    private void updateLatestAttendance(){
        try {
            Connection connection = com.ems.ems.ConnectionUtil.conDB();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT e.id, e.firstName, a.status, a.date FROM users e INNER JOIN attendance a on e.id = a.id " +
                    "ORDER BY date DESC LIMIT 1");
            if (rst.next()){
                lblID.setText("ID: " + rst.getString("id"));
                lblName.setText("Name: " + rst.getString("firstName"));
                lblStatus.setText("Date: " + rst.getString("date") + " - " + rst.getString("status"));
            }else{
                /* Fresh start */
                lblID.setText("ID: -");
                lblName.setText("Name: -");
                lblStatus.setText("Date: -");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtEmployeeID_OnAction(ActionEvent event) {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        lblEmployeeName.setText("Please enter/scan the Employee ID to proceed");

        if (txtEmployeeID.getText().trim().isEmpty()) {
            return;
        }

        try {
            stmSearchEmployee.setString(1, txtEmployeeID.getText().trim());
            ResultSet rst = stmSearchEmployee.executeQuery();

            if (rst.next()) {
                lblEmployeeName.setText(rst.getString("firstName").toUpperCase());
                btnIn.setDisable(false);
                btnOut.setDisable(false);
                employee = new Employee(txtEmployeeID.getText(), rst.getString("firstName"));
                txtEmployeeID.selectAll();
            } else {
                new RJAlert(Alert.AlertType.ERROR, "Invalid Employee ID, Try again!", "Oops!", "Error").show();
                txtEmployeeID.selectAll();
                employee = null;
                txtEmployeeID.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new RJAlert(Alert.AlertType.WARNING, "Something went wrong. Please try again!", "Connection Failure", "Error").show();
            txtEmployeeID.selectAll();
            txtEmployeeID.requestFocus();
        }
    }

    private static class Employee {
        String id;
        String name;
        public Employee(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
