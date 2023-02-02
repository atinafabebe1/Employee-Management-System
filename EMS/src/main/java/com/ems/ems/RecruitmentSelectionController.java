package com.ems.ems;


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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URI;

public class RecruitmentSelectionController {
    private String baseUri = "https://recruitment-x6wn.onrender.com/Recruitment?limit=2";
    private int employeeIndex = 0;
    HttpService service;
    ObservableList<String> informations = FXCollections.observableArrayList();

    JSONArray data;

    @FXML
    private TextField ageTF;
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
    private TextField employeeNameTF;

    @FXML
    private HBox searchHB;

    @FXML
    private TextField searchTf;

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

    @FXML
    void searchHanlder(ActionEvent event) {
        if (searchTf.getText().trim().equals("")) {
            resetUI();
        } else if (!searchTf.getText().trim().equals("")) {
            ProgressIndicator progressIndecator = new ProgressIndicator();
            progressIndecator.setPrefSize(20, 20);
            searchHB.getChildren().add(progressIndecator);
            try {
                service = new HttpService(URI.create(baseUri + "/" + searchTf.getText()));
            } catch (Exception e) {
                searchTf.clear();
                searchHB.getChildren().remove(progressIndecator);
                return;
            }
            searchTf.setDisable(true);
            service.setOnSucceeded(e -> {
                if (e.getSource().getValue().toString().trim() != "") {
                    try {
                        Object obj = new JSONParser().parse(e.getSource().getValue().toString());
                        JSONObject recipes = (JSONObject) obj;
                        data = (JSONArray) recipes.get("data");

                        if (data.size() > 0) {
                            System.out.println("Am not empty");
                            System.out.println(data);
                            renderEmployee();
                        }

                    } catch (Exception ex) {

                    }
                }
                searchTf.setDisable(false);
                searchHB.getChildren().remove(progressIndecator);
            });
            service.start();

        }

    }

    public void renderEmployee(){
        ProgressIndicator progressIndecator = new ProgressIndicator();
        progressIndecator.setPrefSize(20, 20);
        searchHB.getChildren().add(progressIndecator);
        try {
            service = new HttpService(URI.create(baseUri));
        } catch (Exception e) {
            searchTf.clear();
            searchHB.getChildren().remove(progressIndecator);
            return;
        }
        service.setOnSucceeded(e -> {

                try {
                    Object obj = new JSONParser().parse(e.getSource().getValue().toString());
                    JSONObject employees = (JSONObject) obj;
                    data = (JSONArray) employees.get("data");


                    if (data.size() > 0) {
                        System.out.println("Am not empty");
                        System.out.println(data);
                    }
                    JSONObject employeeObj,employee;

                    employeeObj=(JSONObject) data.get(employeeIndex);
                        ageTF.setText((String) employeeObj.get("firstName"));
                    String a= String.valueOf(Integer.parseInt(String.valueOf(employeeObj.get("jobExperiment"))));
                    System.out.println(a);
                        experienceTF.setText(a);
                        jobTitleTF.setText((String) employeeObj.get("jobTitle"));

                } catch (Exception ex) {

                }
                searchHB.getChildren().remove(progressIndecator);

        });
        service.start();


    }

    public void renderControls(){
        if((employeeIndex+1)<data.size()){
            nextButton.setDisable(false);
        }else {
            nextButton.setDisable(true);
        }

        if(employeeIndex>0){
            prevButton.setDisable(false);
        }
        else{
            prevButton.setDisable(true);
        }
    }
    public void resetUI(){
        informations.clear();
        ageTF.clear();
        experienceTF.clear();
        jobTitleTF.clear();
        nextButton.setDisable(true);
        prevButton.setDisable(true);
        userPhotoIMV.setImage(new Image(getClass().getResourceAsStream("Unknown.jpg")));
        employeeIndex=0;
    }

    public void initialize(){
       renderEmployee();
//        informationLV.setItems(informations);
//        informationLV.setPlaceholder(new Label("Not content to displa"));
    }
}
