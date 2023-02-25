package com.ems.ems;

import com.ems.ems.Utils.RJAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {
    private PreparedStatement selectAllEmployee;
    private PreparedStatement updateEmployeeid;
    private PreparedStatement updateEmployeefn;
    private PreparedStatement updateEmployeeln;
    private PreparedStatement updateEmployeeph;
    private PreparedStatement updateEmployeedn;
    private PreparedStatement updateEmployeepn;
    private PreparedStatement updateEmployees;
    private PreparedStatement deleteEmployee;

    final ObservableList<Employee> data= FXCollections.observableArrayList();
    @FXML
    private TableColumn<Employee, Void> deleteCol;

    @FXML
    private TableColumn<Employee, String> departmentCol;

    @FXML
    private TableColumn<Employee, Integer> eNo;

    @FXML
    private TableColumn<Employee, String> firstnamecol;
    @FXML
    private TableColumn<Employee, String> lastnameCol;

    @FXML
    private TableColumn<Employee, String> phoneNumberCol;

    @FXML
    private TableColumn<Employee, String> positionCol;

    @FXML
    private TableColumn<Employee, Double> salaryCol;
    @FXML
    private TableView<Employee> table;
    @FXML
    private GridPane root;

    @FXML
    void addEmployeeHandler(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("EmployeeRecord.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle("Alert!");
        stage.sizeToScene();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.root.getScene().getWindow());
        stage.centerOnScreen();
        stage.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTable();
    }

    private void showTable() {
        try{
            table.setEditable(true);
            Connection connection= com.ems.ems.ConnectionUtil.conDB();
            selectAllEmployee=connection.prepareStatement("select employee.id,`employee_no`, `firstname`, `phoneNumber`, `lastname`, department.department_name , position.position_name ,`salary` from employee join department on employee.department_id=department.id  join position on  employee.position_id=position.id;");
            updateEmployeeid = connection.prepareStatement("UPDATE employee SET employee_no = ? WHERE id = ?");
            updateEmployeefn = connection.prepareStatement("UPDATE employee SET firstname = ? WHERE id = ?");
            updateEmployeeln = connection.prepareStatement("UPDATE employee SET lastname = ? WHERE id = ?");
            updateEmployeeph = connection.prepareStatement("UPDATE employee SET phoneNumber = ? WHERE id = ?");
            updateEmployeedn = connection.prepareStatement("UPDATE employee SET Dname = ? WHERE id = ?");
            updateEmployeepn = connection.prepareStatement("UPDATE employee SET Pname = ? WHERE id = ?");
            updateEmployees = connection.prepareStatement("UPDATE employee SET salary = ? WHERE id = ?");
            deleteEmployee = connection.prepareStatement("DELETE FROM employee WHERE id = ?");

            ResultSet resultSet=selectAllEmployee.executeQuery();

            List<Employee > employeeList=new ArrayList<>();

            while (resultSet.next()){
                employeeList.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("employee_no"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("department_name"),
                        resultSet.getString("position_name"),
                        resultSet.getDouble("salary")
                ));


            }
            data.setAll(employeeList);

            eNo.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("employee_no"));
            eNo.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            eNo.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeeid.setInt(1,event.getNewValue());
                    updateEmployeeid.setInt(2,employee.getId());
                    updateEmployeeid.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setId(event.getNewValue());

            });

            firstnamecol.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstname"));
            firstnamecol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstnamecol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeefn.setString(1,event.getNewValue());
                    updateEmployeefn.setInt(2,employee.getId());
                    updateEmployeefn.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setFirstname(event.getNewValue());

            });

            lastnameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastname"));
            lastnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            lastnameCol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeeln.setString(1,event.getNewValue());
                    updateEmployeeln.setInt(2,employee.getId());
                    updateEmployeeln.executeUpdate();

                    if( updateEmployeeln.executeUpdate()!=1){
                        System.out.println("error");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setLastname(event.getNewValue());

            });
            phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("phoneNumber"));
            phoneNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
            phoneNumberCol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeeph.setString(1,event.getNewValue());
                    updateEmployeeph.setInt(2,employee.getId());
                    updateEmployeeph.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setPhoneNumber(event.getNewValue());

            });
            departmentCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("department_name"));
            departmentCol.setCellFactory(TextFieldTableCell.forTableColumn());
            departmentCol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeedn.setString(1,event.getNewValue());
                    updateEmployeedn.setInt(2,employee.getId());
                    updateEmployeedn.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setDepartment_name(event.getNewValue());

            });
            positionCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("position_name"));
            positionCol.setCellFactory(TextFieldTableCell.forTableColumn());
            positionCol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployeepn.setString(1,event.getNewValue());
                    updateEmployeepn.setInt(2,employee.getId());
                    updateEmployeepn.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setPosition_name(event.getNewValue());
            });
            salaryCol.setCellValueFactory(new PropertyValueFactory<Employee,Double>("salary"));
            salaryCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            salaryCol.setOnEditCommit(event -> {
                Employee employee=event.getRowValue();
                try {
                    updateEmployees.setDouble(1,event.getNewValue());
                    updateEmployees.setInt(2,employee.getId());
                    updateEmployees.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                employee.setSalary(event.getNewValue());

            });

            deleteCol.setCellFactory(param -> new TableCell<>() {
                private final Button deleteButton = new Button("Delete");


                {
                    deleteButton.setTextFill(Color.RED);
                    deleteButton.setOnAction(event -> {
                        Employee employee = getTableRow().getItem();
                        if (employee != null) {
                            try {
                                deleteEmployee.setInt(1,employee.getId());
                                deleteEmployee.executeUpdate();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            data.remove(employee);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : deleteButton);
                }
            });

            table.setItems(data);
            table.getColumns().add(eNo);
            table.getColumns().add(firstnamecol);
            table.getColumns().add(lastnameCol);
            table.getColumns().add(phoneNumberCol);
            table.getColumns().add(departmentCol);
            table.getColumns().add(positionCol);
            table.getColumns().add(salaryCol);
            table.getColumns().add(deleteCol);
        }catch (SQLException e){
            e.printStackTrace();
            new RJAlert(Alert.AlertType.WARNING,"failed to load employee","Connection Error","Error").show();
        }
    }
}