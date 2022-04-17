package com.musicverse.client.gui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import com.musicverse.client.ServerAPI;
import lombok.val;

public class RegisterScreen {

    private int role;

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
    
    private Alert registerAlert = new Alert(AlertType.NONE);

    @FXML
    void onAvatarClick(MouseEvent event) {

    }

    @FXML
    void onBackBtnClick(MouseEvent event) {
        this.goBack(event);
    }
    
    private void goBack(Event event) {
    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(WelcomeScreen.getHolder(), 1200, 600);
        window.setTitle("Music Verse");
        window.setScene(scene);
        window.show();
    }

    @FXML
    void onSignUpClick(ActionEvent event) {
    	if(!this.correctInputData()) {
             return;
    	}
    	try {
	    	val api = ServerAPI.getInstance();
	    	String nickname = this.nickNameField.getText();
	    	String email = this.emailField.getText();
            String password = this.pswdField.getText();
	    	
	    	if (!api.registerUser(email, nickname, password, role)) {
                registerAlert.setAlertType(AlertType.ERROR);
                registerAlert.setContentText("Username or Email is already in use!");
                registerAlert.show();
                return;
            };
	    	registerAlert.setAlertType(AlertType.INFORMATION);
   		 	registerAlert.setContentText("Používate¾ bol vytvorený!");
   		 	registerAlert.show();
   		 	this.goBack(event);
    	}
    	catch(Exception e){
            e.printStackTrace();
    	}

    }
    
    private boolean correctInputData() {
    	if((this.pswdField.getText().length()<=0) || (this.pswd2Field.getText().length()<=0) ||
    			(this.nickNameField.getText().length()<=0)|| (this.emailField.getText().length()<=0)) {
    		registerAlert.setAlertType(AlertType.ERROR);
   		 	registerAlert.setContentText("Musite zadat vsetky udaje!");
            registerAlert.show();
            return false;
    		
    	}
    	if(!this.pswdField.getText().equals(this.pswd2Field.getText())) {
    		registerAlert.setAlertType(AlertType.ERROR);
   		 	registerAlert.setContentText("Zadane hesla sa nezhoduju!");
            registerAlert.show();
    		return false;
    	}
    	
    	return true;  	
    }
    
    

    public void setLabelText(String text, int role){
        this.role = role;
        signUpBtn.setText(text);
    }

}
