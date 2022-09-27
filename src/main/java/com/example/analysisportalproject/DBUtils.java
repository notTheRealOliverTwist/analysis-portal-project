package com.example.analysisportalproject;

// Edits the GUI variables / Handles events
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

// Handles database interactions;
import java.awt.*;
import java.sql.*;

// Used for resizing screen according to users device;

import java.io.IOException;

public class DBUtils {

    // May have to add additonal parameters later, will see
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String fName, String username){
        Parent root = null;

        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                if(fxmlFile.equals("user_dashboard.fxml")){
                    DashboardController dc = loader.getController();
                    dc.setUserInformation(username);
                }
                // Add the logged in screen over here.
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        if(fxmlFile.equals("user_dashboard.fxml")){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double x = screenSize.getWidth();
            double y = screenSize.getHeight();
            stage.setScene(new Scene(root,x,y));
            stage.setX(0);
            stage.setY(0);
            stage.setResizable(true);
            System.out.println("Width: "+x+" Height: "+y);
            //stage.setFullScreen(true);
        }
        else {
            stage.setScene(new Scene(root, 520, 400));
            stage.setResizable(false);
        }
        stage.show();
    }

    // Method used to sign up new users
    public static void singUpUser(ActionEvent event, String fName, String lName, String username, String password, String email){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            // Establishes a connection to the database in order to obtain user information
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_analysis_portal_users", "root", "password");
            // Checks to see if a user with the same username already exists
            psCheckUserExists = connection.prepareStatement("SELECT * FROM UserAccounts WHERE username = ?;");
            psCheckUserExists.setString(1,username);
            // This obtains the information from the database by searching through the tables within the specified database
            resultSet = psCheckUserExists.executeQuery();
            // If the database contains the user already then an alert is created
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please use another username.");
                alert.show();
            }
            // Else the new user is added to the database
            else{
                psInsert = connection.prepareStatement("INSERT INTO UserAccounts (Firstname, Lastname, Username, Password, Email) VALUES (?, ?, ?, ?, ?);");
                psInsert.setString(1, fName);
                psInsert.setString(2,lName);
                psInsert.setString(3,username);
                psInsert.setString(4,password);
                psInsert.setString(5,email);
                psInsert.executeUpdate();

                changeScene(event, "user_dashboard.fxml", "Welcome ", fName, username);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            // Closes unnecessary connections to the database.
            closeDatabaseConnections(connection,psInsert,psCheckUserExists,resultSet);
        }
    }
    public static void closeDatabaseConnections(Connection connection, PreparedStatement psInsert, PreparedStatement psCheckUserExists, ResultSet resultSet){
        if(resultSet != null) {
            try {
                resultSet.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(psCheckUserExists != null) {
            try {
                psCheckUserExists.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(psInsert != null) {
            try {
                psInsert.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(connection != null) {
            try {
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void loginUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx_analysis_portal_users", "root", "password");
            preparedStatement = connection.prepareStatement("SELECT Firstname, Lastname, Username,Password, Email FROM UserAccounts WHERE Username = ?;");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in databasse");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Credentials are incorrect");
                alert.show();
            }
            else {
                while (resultSet.next()) {
                    String resultPassword = resultSet.getString("Password");
                    String resultUsername = resultSet.getString("Username");
                    String resultFName = resultSet.getString("Firstname");
                    //String resultLName = resultSet.getString("Lastname");
                    //String resultEmail = resultSet.getString("Email");

                    if (resultPassword.equals(password)) {
                        changeScene(event, "user_dashboard.fxml", "Admin Dashboard", resultFName, resultUsername);
                    } else {
                        System.out.println("Passwords did not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Credentials are incorrect");
                        alert.show();
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeDatabaseConnections(connection, null, preparedStatement, resultSet);
        }
    }
}
