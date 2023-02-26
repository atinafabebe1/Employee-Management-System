package com.ems.ems;

import com.ems.ems.AttendanceRecord;
import com.ems.ems.ConnectionUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceReportController {

    @FXML
    private TableView<AttendanceRecord> attendanceTable;
    @FXML
    private TextArea reportTextArea;

    @FXML
    private TableColumn<AttendanceRecord, Integer> idColumn;

    @FXML
    private TableColumn<AttendanceRecord, Integer> employeeIdColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> dateColumn;

    @FXML
    private TableColumn<AttendanceRecord, String> statusColumn;

    @FXML
    private Button generateReportButton;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        List<AttendanceRecord> attendanceRecords = loadAttendanceRecords();
        attendanceTable.getItems().addAll(attendanceRecords);
    }

    @FXML
    public void generateReport() {
        // Create a map to store the attendance data for each employee
        Map<Integer, List<Boolean>> attendanceData = new HashMap<>();

        // Loop through the attendance records and add them to the map
        for (AttendanceRecord record : attendanceTable.getItems()) {
            int employeeId = record.getEmployeeId();
            boolean isPresent = record.getStatus().equals("true");
            if (!attendanceData.containsKey(employeeId)) {
                attendanceData.put(employeeId, new ArrayList<>());
            }
            attendanceData.get(employeeId).add(isPresent);
        }

        // Generate the report text
        StringBuilder reportText = new StringBuilder();
        for (Map.Entry<Integer, List<Boolean>> entry : attendanceData.entrySet()) {
            int employeeId = entry.getKey();
            List<Boolean> attendanceList = entry.getValue();
            long trueCount = attendanceList.stream().filter(b -> b).count();
            long falseCount = attendanceList.size() - trueCount;
            reportText.append("Employee ").append(employeeId).append(": \t")
                    .append(trueCount).append(" present, ")
                    .append(falseCount).append(" absent\n");
        }

        // Display the report text in the text area
        reportTextArea.setText(reportText.toString());
    }


    private List<AttendanceRecord> loadAttendanceRecords() {
        List<AttendanceRecord> attendanceRecords = new ArrayList<>();

        try {
            // Establish a connection to the database
            Connection conn = ConnectionUtil.conDB();

            // Prepare a SELECT query to retrieve attendance records
            String query = "SELECT * FROM attendance";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Loop through the result set and create AttendanceRecord objects for each record
            while (rs.next()) {
                int id = rs.getInt("id");
                int employeeId = rs.getInt("employee_id");
                String date = rs.getString("date");
                String status = rs.getString("status");

                AttendanceRecord attendanceRecord = new AttendanceRecord(id, employeeId, date, status);
                attendanceRecords.add(attendanceRecord);
            }

            // Close the database connection and the statement
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attendanceRecords;
    }
}
