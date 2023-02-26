package com.ems.ems;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.itextpdf.text.pdf.PdfWriter;

public class payrollController {

    Connection connection = com.ems.ems.ConnectionUtil.conDB();
    ResultSet rs = null;
    PreparedStatement pst = null;
    ResultSet rs2 = null;
    PreparedStatement pst2 = null;
    ResultSet rs3 = null;
    PreparedStatement pst3 = null;

    public payrollController() throws ClassNotFoundException {

    }

    @FXML
    private Button btnSearch;

    @FXML
    private TextField employeeIdTf;

    @FXML
    private TextField epfTf;

    @FXML
    private TextField foodAllTf;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField netpayTf;

    @FXML
    private Button payslipBtn;

    @FXML
    private Button printBtn;

    @FXML
    private TextField salaryTf;

    @FXML
    private TextField searchTf;

    @FXML
    private TextField taxTf;

    @FXML
    private TextField totalTf;

    @FXML
    private TextField travelAllTf;

    @FXML
    private Button clearBtn;


    @FXML
    void payslipHandler(ActionEvent event) {
        String value = employeeIdTf.getText();
        String value1 = nameTf.getText();
        String value2 = salaryTf.getText();
        String value3 = travelAllTf.getText();
        String value4 = foodAllTf.getText();
        String value5 = taxTf.getText();
        String value6 = epfTf.getText();
        String value7 = totalTf.getText();
        String value8 = netpayTf.getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(value + " " + value1 + "-salary Slip" + ".pdf");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            String filePath = file.getPath();

            try {
                Document myDocument = new Document();
                PdfWriter myWriter = PdfWriter.getInstance(myDocument, new FileOutputStream(filePath));

                myDocument.open();

                myDocument.add(new Paragraph("PAY SLIP", FontFactory.getFont(FontFactory.TIMES_BOLD, 20)));
                myDocument.add(new Paragraph(new Date().toString()));
                myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------"));
                myDocument.add((new Paragraph("EMPLOYEE DETAILS", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15))));
                myDocument.add((new Paragraph("Employee Id: " + value, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add((new Paragraph("Name of Employee: " + value1, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------"));
                myDocument.add(new Paragraph("EARNINGS", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15)));
                myDocument.add((new Paragraph("Salary: " + value2, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add((new Paragraph("Travel Allowance: " + value3, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add((new Paragraph("Food Allowance: " + value4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------"));
                myDocument.add(new Paragraph("DEDUCTION", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15)));
                myDocument.add((new Paragraph("Tax : " + value5, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add((new Paragraph("Epf: " + value6, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add((new Paragraph("Total Deductions: " + value7, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------"));
                myDocument.add(new Paragraph("TOTAL PAYMENT", FontFactory.getFont(FontFactory.TIMES_ROMAN, 15)));
                myDocument.add((new Paragraph("Net Pay : " + value8, FontFactory.getFont(FontFactory.TIMES_ROMAN, 10))));
                myDocument.add(new Paragraph("-------------------------------------------------------------------------------------------"));

                myDocument.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Report was successfully generated");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Employee found!");
                alert.setContentText("Slip Generating Error!");
                alert.showAndWait();
            }
        }
    }


    @FXML
    void clearFields(ActionEvent event) {
        searchTf.setText("");
        employeeIdTf.setText("");
        nameTf.setText("");
        salaryTf.setText("");
        travelAllTf.setText("");
        foodAllTf.setText("");
        epfTf.setText("");
        taxTf.setText("");
        totalTf.setText("");
        netpayTf.setText("");
    }

    @FXML
    void searchButtonHandler(ActionEvent event) {
        try{
            String  sql = "SELECT * FROM employee WHERE id=?";
            String sql2 = "SELECT amount\n" +
                    "FROM employee_allowances ea\n" +
                    "INNER JOIN allowances a ON ea.allowance_id = a.id\n" +
                    "WHERE employee_id = ? AND a.allowance = 'Travel allowance';\n";
            String sql3 = "SELECT amount\n" +
                    "FROM employee_allowances ea\n" +
                    "INNER JOIN allowances a ON ea.allowance_id = a.id\n" +
                    "WHERE employee_id = ? AND a.allowance = 'Food allowance';\n";

            pst = connection.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(searchTf.getText()));
            rs= pst.executeQuery();

            if (rs.next()) {

                Integer add1 = rs.getInt("id");
                employeeIdTf.setText(String.valueOf(add1));
                String add2 = rs.getString("firstname");
                String add3 = rs.getString("lastname");
                nameTf.setText(add2 + " " + add3);
                Integer add4 = rs.getInt("salary");
                salaryTf.setText(String.valueOf(add4));

                pst2 = connection.prepareStatement(sql2);
                pst2.setInt(1, Integer.parseInt(searchTf.getText()));
                rs2 = pst2.executeQuery();

                if (!rs2.next()) {
                    travelAllTf.setText(String.valueOf(0));
                } else {
                    Integer add5 = rs2.getInt("amount");
                    travelAllTf.setText(String.valueOf(add5));
                }

                pst3 = connection.prepareStatement(sql3);
                pst3.setInt(1, Integer.parseInt(searchTf.getText()));
                rs3 = pst3.executeQuery();

                if (!rs3.next()) {
                    foodAllTf.setText(String.valueOf(0));
                } else {
                    Integer add6 = rs3.getInt("amount");
                    foodAllTf.setText(String.valueOf(add6));
                }

                taxTf.setText("15%");

                Integer salary = Integer.parseInt(salaryTf.getText());
                Integer add7 = (int) (salary * 0.1);
                epfTf.setText(String.valueOf(add7));

                Integer tax = (int) (salary * 0.15);
                Integer totded = tax + add7;
                totalTf.setText(String.valueOf(totded));

                Integer travall = Integer.parseInt(travelAllTf.getText());
                Integer foodall = Integer.parseInt(foodAllTf.getText());
                Integer totalall = travall + foodall;
                Integer netpay = salary  + totalall - totded;
                netpayTf.setText(String.valueOf(netpay));
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Employee found!");
                alert.setContentText("Employee not found with given ID");
                alert.showAndWait();
            }

        }catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }finally {
            try{
                rs.close();
                pst.close();
                rs2.close();
                pst2.close();
                rs3.close();
                pst3.close();
            }catch (Exception e) {

            }
        }
    }
}
