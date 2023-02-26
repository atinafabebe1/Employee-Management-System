package com.ems.ems;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class ProfileController implements Initializable {
   String usernamea=LoginController.username;
    String passworda=LoginController.password;
    @FXML
    private TextField addresstxf;

    @FXML
    private TextField emailtxf;

    @FXML
    private TextField passwordtxf;

    @FXML
    private TextField phonetxf;

    @FXML
    private TextField roletxf;

    @FXML
    private Button updatebtn;

    @FXML
    private Label userlabel;

    @FXML
    private TextField usernametxf;

    @FXML
    private TextField password1txf;

    @FXML
    private Label lblErrors;

    public ProfileController() throws ClassNotFoundException {
    }

    @FXML
    void UpdateProfile(ActionEvent event) {

        if (event.getSource() == updatebtn) {

            if (profile().equals("Success")) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("HomePageEmployeer.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    Connection con = com.ems.ems.ConnectionUtil.conDB();
    PreparedStatement preparedStatement = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    ResultSet rs = null;

    @FXML



    public void initialize(URL url, ResourceBundle rb) {
        String usernamea=LoginController.username;
        String passworda=LoginController.password;
        preview();
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        }
    }

    private String profile() {

        String status = "Success";
        String username = usernametxf.getText();
        String password1 = passwordtxf.getText();
        String password2 = password1txf.getText();
        String role = roletxf.getText();
        String email=emailtxf.getText();
        String phoneNumber=phonetxf.getText();
        String address=addresstxf.getText();

        if(username.isEmpty()||role.isEmpty()
                ||email.isEmpty()||phoneNumber.isEmpty()
                ||address.isEmpty()
        ) {
            setLblError(Color.TOMATO, "All Filled Are Required");
            status = "Error";
        } else if (!password1.equals(password2)) {
            setLblError(Color.TOMATO, "Password Mismatch");
            status = "Error";
        } else if(!password1.isEmpty() && !password2.isEmpty() ){

            //query
            String sql = "update  users set phoneNumber=?,email=?,username=?,address=?,password=?,role=? where username=? and password=?";

            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, phoneNumber);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, password1);
                preparedStatement.setString(6, role);
                preparedStatement.setString(7, usernamea);
                preparedStatement.setString(8, passworda);

                int result=preparedStatement.executeUpdate();
                if (result!=1) {
                    setLblError(Color.TOMATO, "Something Wrong");
                    status = "Error";
                } else {
                    LoginController.setterPassword(password1);
                    LoginController.setterName(username);
                    setLblError(Color.GREEN, "Update Successful");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
            }
            else  {
            String sql = "update users set phoneNumber=?,email=?,username=?,address=?,role=? where username=? and password=?";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, phoneNumber);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, username);
                    preparedStatement.setString(4, address);
                    preparedStatement.setString(5, role);
                    preparedStatement.setString(6, usernamea);
                    preparedStatement.setString(7, passworda);




                    int result = preparedStatement.executeUpdate();
                    if (result != 1) {
                        setLblError(Color.TOMATO, "Something Wrong");
                        status = "Error";
                    } else {
                        LoginController.setterName(username);
                        setLblError(Color.GREEN, "Update Successful");
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
    void preview() {
        String status = "Success";
        try {
            String sql = "SELECT * FROM users Where username = ? and password = ?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, usernamea);
            preparedStatement.setString(2, passworda);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                userlabel.setText(resultSet.getString("username"));
                usernametxf.setText(resultSet.getString("username"));
                roletxf.setText(resultSet.getString("role"));
                emailtxf.setText(resultSet.getString("email"));
                phonetxf.setText(resultSet.getString("phoneNumber"));
                addresstxf.setText(resultSet.getString("address"));

            } else {
                setLblError(Color.TOMATO, "Some thing went wrong");
                status = "Error";
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            status = "Exception";
        }
    }

}

