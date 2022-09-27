package com.example.analysisportalproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;

import java.util.*;

import java.text.SimpleDateFormat;

public class DashboardController implements Initializable {

    @FXML
    private Label tableLabel;
    /*
    @FXML
    private Label mapLabel;
    @FXML
    private Label chartlabel;*/
    @FXML
    private Label dashboardHeading;
    @FXML
    private Label userLabel;
    @FXML
    private Label dashboardClock;
    @FXML
    private Label dashboardDate;

    /*
    @FXML
    private ImageView tableImg;
    @FXML
    private ImageView mapImg;
    @FXML
    private ImageView chartImg; */
    @FXML
    private ImageView settingsImg;

    @FXML
    private BorderPane dashboardBackground;
    @FXML
    private Pane dashboardLine;
    @FXML
    private GridPane adMenuBar;
    @FXML
    private AnchorPane dashboardClockPane;
    @FXML
    private AnchorPane dashboardChartPane;

    // Used to display user usage statistics in hours
    @FXML
    private AreaChart dashboardChart;

    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addHighlightEffectMenuOptions();
        //plotLine();
        updateTime();
        updateDate();
    }

    /*public void update(){
        updateTime();
    }*/

    public void updateDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
        dashboardDate.setText(sdf.format(cal.getTime()));
    }

    public void updateTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            dashboardClock.setText(sdf.format(cal.getTime()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //System.out.println(sdf.format(cal.getTime()));
    }

    private void addHighlightEffectMenuOptions(){
        for (Node node : adMenuBar.getChildren()) {
            node.setOnMouseEntered(e -> adMenuBar.getChildren().forEach(c -> {
                Integer targetIndex = GridPane.getRowIndex(node);
                if (GridPane.getRowIndex(c) == targetIndex) {
                    c.setStyle("-fx-background-color: rgba(211,211,211,0.2);");
                }
            }));
            node.setOnMouseExited(e -> adMenuBar.getChildren().forEach(c -> {
                Integer targetIndex = GridPane.getRowIndex(node);
                if (GridPane.getRowIndex(c) == targetIndex) {
                    c.setStyle("-fx-background-color: rgba(255,0,0,1);");
                }
            }));
        }
    }
    // plotLine plots the relevant data onto the chart


    // createChartUsageData obtains usage data from the database and appends it to the HashMap in order to add it to the chart
    /*private HashMap<String, ArrayList<Double>> createChartUsageData(){
        HashMap<String, ArrayList<Double>> userStats = new HashMap<String, ArrayList<Double>>();
    }*/

    public void setUserInformation(String uName){
        userLabel.setText(uName);
        username = uName;
    }
}
