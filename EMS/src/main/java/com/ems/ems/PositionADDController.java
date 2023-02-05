package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PositionADDController implements Initializable{
    @FXML
    private TextField departmentTF;
    @FXML
    private TextField positionName;
    @FXML
    private Button addBtn;

    @FXML
    private Button clearBtn;

    public VBox root;


    @FXML
    void addBtnHandler(ActionEvent event) {
        if (departmentTF.getText() == "") {
            addBtn.setDisable(true);
        } else {
            Connection connection = com.ems.ems.ConnectionUtil.conDB();

            try {
                PreparedStatement stmAddDepartment = connection.prepareStatement("INSERT INTO `position` (`department_id`, `name`) VALUES (?,?)");
                PreparedStatement searchId=connection.prepareStatement("Select * from department where id=(?)");
                searchId.setString(1,departmentTF.getText().trim());
                ResultSet rst=searchId.executeQuery();

                if(rst.next()){
                    stmAddDepartment.setString(1, departmentTF.getText());
                    stmAddDepartment.setString(2, positionName.getText());
                    stmAddDepartment.executeUpdate();
                    addBtn.setDisable(true);
                    clearBtn.setDisable(true);
                }
                else{
                    new RJAlert(Alert.AlertType.ERROR, "Invalid Department ID, try again",
                            "Oops", "Error").show();
                }

                departmentTF.clear();
                positionName.clear();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }
    @FXML
    void clearBtnHanlder(ActionEvent event) {
        departmentTF.clear();
        positionName.clear();
    }

    @FXML
    void existBtnHandler(ActionEvent event) {

    }

    @FXML
    void keyReleasedProperty(){
        boolean isDiabled= Boolean.parseBoolean((departmentTF.getText()));
        addBtn.setDisable(isDiabled);
        clearBtn.setDisable(isDiabled);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clearBtn.setDisable(true);
        addBtn.setDisable(true);
    }



    private static class Poisition {
        int id;
        String departmentID;
        String name;
        public Poisition(int id, String name,String departmentID) {
            this.id = id;
            this.name = name;
            this.departmentID=departmentID;
        }
    }

}

