package com.ems.ems;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignUpController implements Initializable {

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField emailTextField;


    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField homeNumberTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField password1TextFiled;

    @FXML
    private PasswordField password2TextField;

    @FXML
    private TextField phoneNumberTextFIeld;

    @FXML
    private Button registerbutton;

    @FXML
    private TextField roleTextField;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label lblErrors;
    @FXML
    void handleRegisterButton(ActionEvent event) {

            if (event.getSource() == registerbutton) {

                if (signUp().equals("Success")) {
                    try {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }

                }
            }
        
    }


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        }
    }

    public SignUpController() {
        con = com.ems.ems.ConnectionUtil.conDB();
    }

    private String signUp() {


        String status = "Success";
        String username = usernameTextField.getText();
        String password1 = password1TextFiled.getText();
        String password2 = password2TextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String role = roleTextField.getText();
        String email=emailTextField.getText();
        String phoneNumber=phoneNumberTextFIeld.getText();
        String homeNumber=homeNumberTextField.getText();
        String address=addressTextField.getText();



        if(username.isEmpty() || password1.isEmpty()
                || password2.isEmpty()||firstName.isEmpty()
                ||lastName.isEmpty()||role.isEmpty()
                ||email.isEmpty()||phoneNumber.isEmpty()
                ||homeNumber.isEmpty() ||address.isEmpty()
        ) {
            setLblError(Color.TOMATO, "All Filleds Are Required");
            status = "Error";
        } else if (!password1.equals(password2)) {
            setLblError(Color.TOMATO, "Password Mismatch");
            status = "Error";
        } else {
            //query
            String sql = "INSERT INTO users "+
                    "(phoneNumber,firstName,lastName,email,username,address,homeNo,password,role)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, phoneNumber);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, username);
                preparedStatement.setString(6, address);
                preparedStatement.setString(7, homeNumber);
                preparedStatement.setString(8, password1);
                preparedStatement.setString(9, role);
                int result=preparedStatement.executeUpdate();
                if (result!=1) {
                    setLblError(Color.TOMATO, "Something Wrong");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Register Successful");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
    }
}
