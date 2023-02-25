package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
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
    private ComboBox<String> departmentComboBox;
    @FXML
    private ComboBox<String> positionComboBox;
    ObservableList<String> departmentList = FXCollections.observableArrayList();
    ObservableList<String> positionList = FXCollections.observableArrayList();


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
    void registerBtnHandler(ActionEvent event) throws ClassNotFoundException {

        if (firstNameTF.getText().isEmpty()  || lastNameTF.getText().isEmpty() || EmployeeNOTF.getText().isEmpty() || departmentComboBox.getValue().isEmpty()
           || PhoneNumberTF.getText().isEmpty() || positionComboBox.getValue().isEmpty() || salaryTF.getText().isEmpty()
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
                PreparedStatement departmentID=connection.prepareStatement("Select id from department where department_name=(?)");
                PreparedStatement positionID=connection.prepareStatement("Select id from position where position_name=(?)");
                departmentID.setString(1, departmentComboBox.getValue());
                positionID.setString(1, positionComboBox.getValue());
                ResultSet rDst=departmentID.executeQuery();
                ResultSet rPst=positionID.executeQuery();
                int departmentId = 0;
                int positionId = 0;
                
                while (rDst.next()){
                    departmentId=rDst.getInt("id");
                    System.out.println("Department id is "+ departmentId);
                }
                while (rPst.next()){
                    positionId=rPst.getInt("id");
                }

                    stmAddDepartment.setString(1, EmployeeNOTF.getText());
                    stmAddDepartment.setString(2, firstNameTF.getText());
                    stmAddDepartment.setString(3, PhoneNumberTF.getText());
                    stmAddDepartment.setString(4, lastNameTF.getText());
                    stmAddDepartment.setInt(5, departmentId);
                    stmAddDepartment.setInt(6, positionId);
                    stmAddDepartment.setFloat(7, Float.parseFloat((salaryTF.getText())));

                    stmAddDepartment.executeUpdate();
                    registerBTN.setDisable(true);
                    clearBTN.setDisable(true);

                EmployeeNOTF.clear();
                firstNameTF.clear();
                lastNameTF.clear();
                PhoneNumberTF.clear();
                salaryTF.clear();
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

        try {
            rs = stmt.executeQuery("SELECT DISTINCT position_name FROM position");
                while (rs.next()){
                    positionList.add(rs.getString("position_name"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
        }
        departmentComboBox.setItems(departmentList);
        positionComboBox.setItems(positionList);
             registerBTN.setDisable(true);
             clearBTN.setDisable(true);
    }
}
