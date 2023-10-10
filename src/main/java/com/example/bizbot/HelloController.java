package com.example.bizbot;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {
    @FXML
    private Hyperlink id_forgot;

    @FXML
    private Button id_loginBtn;

    @FXML
    private AnchorPane id_loginform;

    @FXML
    private PasswordField id_password;

    @FXML
    private Button id_sidebtn;
    @FXML
    private Button id_ready;

    @FXML
    private AnchorPane id_sideform;

    @FXML
    private TextField id_username;

    @FXML
    private TextField su_answer;

    @FXML
    private ComboBox<?> su_combo;

    @FXML
    private PasswordField su_password;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;
    //The function below is just for testing purpose. this should be removed and replaced with db connection and user authorization
    public void Login(ActionEvent event) throws IOException {
        if(id_username.getText().equals("admin") && id_password.getText().equals("pass")){
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("maindesign.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
            stage.setTitle("Bizbot Dashboard");
            stage.setScene(scene);
            stage.show();
        }
    }
    public void switchForm(ActionEvent event){
        // this is for sliding animation. TranslateTransition is an inbuilt library
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == id_sidebtn){
            slider.setNode(id_sideform);
            //ToX is a translation function that translates towards the x-axis
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(
                    (ActionEvent e)->{
                id_ready.setVisible(true);
                id_sidebtn.setVisible(false);
            }
            );
            slider.play();
        } else if (event.getSource()== id_ready) {
            slider.setNode(id_sideform);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(
                    (ActionEvent e)->{
                        id_ready.setVisible(false);
                        id_sidebtn.setVisible(true);
                    }
            );
            slider.play();
        }
    }

}