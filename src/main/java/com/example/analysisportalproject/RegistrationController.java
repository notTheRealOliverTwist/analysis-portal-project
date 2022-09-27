package com.example.analysisportalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    @FXML
    private TextField rFirstnameTextField;
    @FXML
    private TextField rLastnameTextField;
    @FXML
    private TextField rEmailTextField;
    @FXML
    private TextField rUsernameTextField;
    @FXML
    private PasswordField rPasswordTextField;
    @FXML
    private Button rSignUpButton;
    @FXML
    private Button rLoginButton;
    @FXML
    private Label rRegistrationLabel;

    public void initialize(URL location, ResourceBundle resource){

            rSignUpButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(!rFirstnameTextField.getText().trim().isEmpty() &&
                            !rLastnameTextField.getText().trim().isEmpty() &&
                                !rUsernameTextField.getText().trim().isEmpty() &&
                                    !rPasswordTextField.getText().trim().isEmpty() &&
                                        !rEmailTextField.getText().trim().isEmpty()) {
                        DBUtils.singUpUser(event, rFirstnameTextField.getText(), rLastnameTextField.getText(), rUsernameTextField.getText(), rPasswordTextField.getText(), rEmailTextField.getText());
                    }
                    else{
                        System.out.println("Please fill out all fields");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Please fill in all fields");
                        alert.show();
                    }
                }
            });

            rLoginButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DBUtils.changeScene(event, "login.fxml","Log In!",null,null);
                }
            });
    }
}
