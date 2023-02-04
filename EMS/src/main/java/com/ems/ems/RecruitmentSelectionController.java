package com.ems.ems;

import com.ems.ems.HttpService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import java.net.URI;

public class RecruitmentSelectionController {
    private String baseUri = "https://recruitment-x6wn.onrender.com/Recruitment";
    private int employeeIndex = 0;
    HttpService service;
    ObservableList<String> informations = FXCollections.observableArrayList();

    JSONArray data;

    @FXML
    private TextField qualificationTF;
    @FXML
    private TextField salaryExpectation;

    @FXML
    private TextField email;
    @FXML
    private TextField experienceTF;
    @FXML
    private TextField jobTitleTF;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private ImageView userPhotoIMV;

    @FXML
    private ListView<String> informationLV;

    @FXML
    private HBox searchHB;

    @FXML
    private TextField employeeNameTF;

    @FXML
    void nextEmployeeHandler(ActionEvent event) {
        ++employeeIndex;
        renderEmployee();
    }

    @FXML
    void prevEmployeeHanlder(ActionEvent event) {
        --employeeIndex;
        renderEmployee();
    }



    public void renderEmployee(){
        ProgressIndicator progressIndecator = new ProgressIndicator();
        progressIndecator.setPrefSize(40, 40);
        searchHB.getChildren().add(progressIndecator);
        try {
            service = new HttpService(URI.create(baseUri));
        } catch (Exception e) {
            searchHB.getChildren().remove(progressIndecator);
            return;
        }
        service.setOnSucceeded(e -> {

                try {
                    Object obj = new JSONParser().parse(e.getSource().getValue().toString());
                    JSONObject employees = (JSONObject) obj;
                    data = (JSONArray) employees.get("data");
                    if((employeeIndex+1)<data.size()){
                        nextButton.setDisable(false);
                    }
                    else{
                        nextButton.setDisable(true);
                    }
                    if(employeeIndex>0){
                        prevButton.setDisable(false);
                    }
                    else {
                        prevButton.setDisable(true);
                    }

                    JSONObject employeeObj,employee;

                    employeeObj=(JSONObject) data.get(employeeIndex);
                    String experiments= String.valueOf(Integer.parseInt(String.valueOf(employeeObj.get("jobExperiment"))));
                    String salary= String.valueOf(Integer.parseInt(String.valueOf(employeeObj.get("salaryExpectations"))));
                    String firstName= (String) employeeObj.get("firstName");
                    String lastName= (String) employeeObj.get("lastName");
                    employeeNameTF.setText(firstName+" "+lastName);
                    salaryExpectation.setText(salary);
                    email.setText((String) employeeObj.get("email"));
                    qualificationTF.setText((String) employeeObj.get("qualifications"));
                    experienceTF.setText(experiments);
                    jobTitleTF.setText((String) employeeObj.get("jobTitle"));
                    userPhotoIMV.setImage(new Image((String) employeeObj.get("photo")));

                } catch (Exception ex) {

                }
                searchHB.getChildren().remove(progressIndecator);

        });
        service.start();
    }
    public void initialize(){
       renderEmployee();
    }
}
