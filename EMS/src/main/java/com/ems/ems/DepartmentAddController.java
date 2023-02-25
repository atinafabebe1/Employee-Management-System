package com.ems.ems;

import com.ems.ems.ConnectionUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    void addBtnHandler(ActionEvent event) throws ClassNotFoundException {
        if(departmentTF.getText()==""){
            addBtn.setDisable(true);
        }
        else{
            Connection connection= ConnectionUtil.conDB();

            try {
                PreparedStatement stmAddDepartment=connection.prepareStatement("INSERT INTO department (name) VALUES (?)");
                stmAddDepartment.setString(1,departmentTF.getText());
                stmAddDepartment.executeUpdate();
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
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clearBtn.setDisable(true);
        addBtn.setDisable(true);
;
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
