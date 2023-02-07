package com.ems.ems;

import com.ems.ems.ConnectionUtil;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private PreparedStatement updateEmployeeInfo;
    final ObservableList<Employee> data= FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> deleteCol;

    @FXML
    private TableColumn<Employee, String> departmentCol;

    @FXML
    private TableColumn<Employee, Integer> eNo;

    @FXML
    private TableColumn<?, ?> editCol;

    @FXML
    private TableColumn<Employee, String> firstnamecol;

    @FXML
    private TableColumn<Employee, String> lastnameCol;

    @FXML
    private TableColumn<Employee, String> phoneNumberCol;

    @FXML
    private TableColumn<Employee, String> positionCol;

    @FXML
    private TableColumn<Employee, Double> salaryCOl;
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
        SimpleBooleanProperty record = new SimpleBooleanProperty(false);

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
       try{
           Connection connection= ConnectionUtil.conDB();
           selectAllEmployee=connection.prepareStatement("select employee.id,`employee_no`, `firstname`, `phoneNumber`, `lastname`, department.name as Dname, position.name as Pname ,`salary` from employee join department on employee.department_id=department.id  join position on  employee.position_id=position.id;");
           updateEmployeeInfo=connection.prepareStatement("UPDATE employee set (?) where `id`=(?) ");

           ResultSet resultSet=selectAllEmployee.executeQuery();

           List<Employee > employeeList=new ArrayList<>();

           while (resultSet.next()){
               employeeList.add(new Employee(
                       resultSet.getInt("id"),
                       resultSet.getString("firstname"),
               resultSet.getString("firstname"),
                       resultSet.getInt("employee_no"),
                       resultSet.getString("phoneNumber"),
               resultSet.getString("Dname"),
               resultSet.getString("Pname"),
                       resultSet.getDouble("salary")
               ));


           }
           data.setAll(employeeList);

           eNo.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("employee_no"));
           firstnamecol.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstname"));
           lastnameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastname"));
           phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("phoneNumber"));
           departmentCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Dname"));
           positionCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("Pname"));
           salaryCOl.setCellValueFactory(new PropertyValueFactory<Employee,Double>("salary"));

           table.setItems(data);
           table.getColumns().add(eNo);
           table.getColumns().add(firstnamecol);
           table.getColumns().add(lastnameCol);
           table.getColumns().add(phoneNumberCol);
           table.getColumns().add(departmentCol);
           table.getColumns().add(positionCol);
           table.getColumns().add(salaryCOl);
       }catch (SQLException e){
           e.printStackTrace();
           new RJAlert(Alert.AlertType.WARNING,"failed to load employee","Connection Error","Error").show();
       }
    }
}
