package com.ems.ems;

import com.ems.ems.ConnectionUtil;
import com.ems.ems.Utils.RJAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DepartmentAddController implements Initializable {

    @FXML
    private TextField departmentTF;
    @FXML
    private Button addBtn;

    @FXML
    private Button clearBtn;
    @FXML
    private Button deleteBtn;


    @FXML
    void addBtnHandler(ActionEvent event) {
        if(departmentTF.getText()==""){
            addBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }
        else{
            Connection connection= ConnectionUtil.conDB();

            try {
                PreparedStatement stmAddDepartment=connection.prepareStatement("INSERT INTO department (department_name) VALUES (?)");
                stmAddDepartment.setString(1,departmentTF.getText());
                int res=stmAddDepartment.executeUpdate();
                if(res!=1){
                    new RJAlert(Alert.AlertType.ERROR, "Deprtment no found with this name", "Oops!", "Error").show();
                }
                departmentTF.clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void clearBtnHanlder(ActionEvent event) {
         departmentTF.clear();
    }

    @FXML
    void existBtnHandler(ActionEvent event) {

    }

    @FXML
    void keyReleasedProperty(){
        boolean isDiabled= Boolean.parseBoolean((departmentTF.getText()));
        addBtn.setDisable(isDiabled);
        clearBtn.setDisable(isDiabled);
        deleteBtn.setDisable(true);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clearBtn.setDisable(true);
        addBtn.setDisable(true);
        deleteBtn.setDisable(true);
;
    }

    public void deleteBtnHandler(ActionEvent event) {
        Connection connection = com.ems.ems.ConnectionUtil.conDB();
        try {
            PreparedStatement stmdeleteDepartment = connection.prepareStatement("DELETE FROM `department` where `department_name`=?");
            stmdeleteDepartment.setString(1, departmentTF.getText().trim());

            int result=stmdeleteDepartment.executeUpdate();
            if(result!=1){
                new RJAlert(Alert.AlertType.ERROR, "Deprtment no found with this name", "Oops!", "Error").show();
            }
            addBtn.setDisable(true);
            clearBtn.setDisable(true);
            deleteBtn.setDisable(true);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static class Department {
        int id;
        String name;
        public Department(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
