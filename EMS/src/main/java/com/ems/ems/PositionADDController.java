package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PositionADDController implements Initializable{
    @FXML
    private TextField departmentTF;
    @FXML
    private TextField positionName;
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteBtn;


    public VBox root;

    @FXML
    private ComboBox<String> departmentComboBox;
    ObservableList<String> departmentList = FXCollections.observableArrayList();

    int departmentId = 0;
    @FXML
    private VBox vbox;



    @FXML
    void addBtnHandler(ActionEvent event) {
        if (departmentComboBox.getValue().isEmpty()) {
            addBtn.setDisable(true);
        } else {
            Connection connection = com.ems.ems.ConnectionUtil.conDB();
            try {
                PreparedStatement stmAddPosition = connection.prepareStatement("INSERT INTO `position` (`department_id`, `position_name`) VALUES (?,?)");
                PreparedStatement departmentID=connection.prepareStatement("Select id from department where department_name=(?)");
                departmentID.setString(1, departmentComboBox.getValue());
                ResultSet rDst=departmentID.executeQuery();
                while (rDst.next()){
                    departmentId=rDst.getInt("id");
                    System.out.println("Department id is "+ departmentId);
                }

                stmAddPosition.setInt(1, departmentId);
                stmAddPosition.setString(2, positionName.getText());
                stmAddPosition.executeUpdate();

                addBtn.setDisable(true);

                positionName.clear();
            } catch (SQLIntegrityConstraintViolationException e) {
                // Handle the case where the position name already exists
                new RJAlert(Alert.AlertType.ERROR, "Duplicate position, try again",
                        "Failure", "Error", ButtonType.OK).show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void keyReleasedProperty(){
        boolean isDiabled= Boolean.parseBoolean((positionName.getText()));
        addBtn.setDisable(isDiabled);
        deleteBtn.setDisable(isDiabled);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection= com.ems.ems.ConnectionUtil.conDB();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet rs = null;

        try {
            rs = stmt.executeQuery("SELECT DISTINCT department_name FROM department");
            while (rs.next()) {
                departmentList.add(rs.getString("department_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        departmentComboBox.setItems(departmentList);
        deleteBtn.setDisable(true);
        addBtn.setDisable(true);
    }

    public void deleteBtnHandler(ActionEvent event) {
        if (departmentComboBox.getValue().isEmpty()) {
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } else {
            Connection connection = com.ems.ems.ConnectionUtil.conDB();
            try {
                PreparedStatement stmDeletePosition = connection.prepareStatement("DELETE FROM `position` where `department_id`=? and `position_name`=?");
                PreparedStatement departmentID = connection.prepareStatement("Select id from department where department_name=(?)");
                departmentID.setString(1, departmentComboBox.getValue());
                ResultSet rDst = departmentID.executeQuery();
                while (rDst.next()) {
                    departmentId = rDst.getInt("id");
                    System.out.println("Department id is " + departmentId);
                }

                stmDeletePosition.setInt(1, departmentId);
                stmDeletePosition.setString(2, positionName.getText());
                int result=stmDeletePosition.executeUpdate();
                if(result!=1){
                    new RJAlert(Alert.AlertType.ERROR, "No such data", "Oops!", "Error").show();
                }


                addBtn.setDisable(true);

                positionName.clear();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

