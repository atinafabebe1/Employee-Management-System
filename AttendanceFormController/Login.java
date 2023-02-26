

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {
public static String username;
public static void setterName(String name){
    username=name;
}
public static String password;
    public static void setterPassword(String name){
        password=name;
    }
    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignin;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String role=null;

    @FXML
    public void handleButtonAction(ActionEvent event) throws SQLException {
        if (event.getSource() == btnSignin) {

            if (logIn().equals("Success")) {
                try {

                    if(role.equals("Admin")){
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePageEmployeer.fxml"))));
                        stage.setScene(scene);
                        stage.show();
                    }
                    else{
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HomePageEmployee.fxml"))));
                        stage.setScene(scene);
                        stage.show();
                    }

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

                System.out.println("logged in successfully");

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        }
    }

    public LoginController() {
        con = com.ems.ems.ConnectionUtil.conDB();
    }

    private String logIn() {
        String status = "Success";
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM users Where username = ? and password = ?";
            try {

                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    setterName(resultSet.getString("username"));
                    setterPassword(resultSet.getString("password"));
                    role=resultSet.getString("role");

                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                } else {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
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