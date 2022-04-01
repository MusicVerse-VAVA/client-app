package com.musicverse.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class RegisterScreen {

    @FXML
    private Circle avatarCircle;

    @FXML
    private Label backBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nickNameField;

    @FXML
    private PasswordField pswd2Field;

    @FXML
    private PasswordField pswdField;

    @FXML
    private Button signUpBtn;

    @FXML
    void onAvatarClick(MouseEvent event) {

    }

    @FXML
    void onBackBtnClick(MouseEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(WelcomeScreen.getHolder(), 1200, 600);
        window.setTitle("Music Verse");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void onSignUpClick(ActionEvent event) {

    }

    void setLabelText(String text){
        signUpBtn.setText(text);
    }

}
