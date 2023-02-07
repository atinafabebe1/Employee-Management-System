package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeRecordController implements Initializable {

    @FXML
    private Button clearBTN;
    @FXML
    private Button registerBTN;
    @FXML
    private TextField DepartmetnIDTF;

    @FXML
    private TextField EmployeeNOTF;

    @FXML
    private TextField PhoneNumberTF;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField positionIDTF;

    @FXML
    private TextField salaryTF;
    @FXML
    private Label errorLBL;

    @FXML
    void clearBtnHanlder(ActionEvent event) {
        EmployeeNOTF.clear();
        firstNameTF.clear();
        lastNameTF.clear();
        PhoneNumberTF.clear();
        DepartmetnIDTF.clear();
        salaryTF.clear();
        positionIDTF.clear();
    }

    @FXML
    void registerBtnHandler(ActionEvent event) {

        if (firstNameTF.getText().isEmpty()  || lastNameTF.getText().isEmpty() || EmployeeNOTF.getText().isEmpty() || DepartmetnIDTF.getText().isEmpty()
           || PhoneNumberTF.getText().isEmpty() || positionIDTF.getText().isEmpty() || salaryTF.getText().isEmpty()
        ) {
            registerBTN.setDisable(true);
            errorLBL.setText("All fields are required!!!");
            errorLBL.setTextFill(Color.RED);
        } else {
            Connection connection = com.ems.ems.ConnectionUtil.conDB();

            try {
                PreparedStatement stmAddDepartment = connection.prepareStatement("INSERT INTO employee (employee_no, firstname, phoneNumber, lastname, department_id, position_id, salary) VALUES"
                        +
                        "(?, ?, ?, ?, ?, ?, ?);");
                PreparedStatement searchDId=connection.prepareStatement("Select * from department where id=(?)");
                PreparedStatement searchPID=connection.prepareStatement("Select * from position where id=(?)");
                searchDId.setInt(1, Integer.parseInt(DepartmetnIDTF.getText().trim()));
                searchPID.setInt(1, Integer.parseInt(positionIDTF.getText()));
                ResultSet rDst=searchDId.executeQuery();
                ResultSet rPst=searchPID.executeQuery();

                if(rDst.next() && rPst.next()){
                    stmAddDepartment.setString(1, EmployeeNOTF.getText());
                    stmAddDepartment.setString(2, firstNameTF.getText());
                    stmAddDepartment.setString(3, PhoneNumberTF.getText());
                    stmAddDepartment.setString(4, lastNameTF.getText());
                    stmAddDepartment.setInt(5, Integer.parseInt(DepartmetnIDTF.getText()));
                    stmAddDepartment.setInt(6, Integer.parseInt(positionIDTF.getText()));
                    stmAddDepartment.setFloat(7, Float.parseFloat((salaryTF.getText())));

                    stmAddDepartment.executeUpdate();
                    registerBTN.setDisable(true);
                    clearBTN.setDisable(true);
                }
                else{
                    new RJAlert(Alert.AlertType.ERROR, "Invalid Department or Position ID, try again",
                            "Oops", "Error").show();
                }

                EmployeeNOTF.clear();
                firstNameTF.clear();
                lastNameTF.clear();
                PhoneNumberTF.clear();
                DepartmetnIDTF.clear();
                salaryTF.clear();
                positionIDTF.clear();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    @FXML
    void keyReleasedProperty(){
        boolean isDiabled= Boolean.parseBoolean(String.valueOf((firstNameTF.getText().isEmpty())));
        registerBTN.setDisable(isDiabled);
        clearBTN.setDisable(isDiabled);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            registerBTN.setDisable(true);
            clearBTN.setDisable(true);
    }
}
