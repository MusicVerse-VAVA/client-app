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

import com.musicverse.client.Database;

public class RegisterScreen {
	
	private Database db;

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
	    	db = new Database();
	    	String nickname = this.nickNameField.getText();
	    	String email = this.emailField.getText();
	    	
	    	if(db.emailExists(email)) {
	    		registerAlert.setAlertType(AlertType.ERROR);
	   		 	registerAlert.setContentText("Tento email už používa iný používate¾");
	            registerAlert.show();
	            return;
	    	}
	    	
	    	if(db.nicknameExists(nickname)) {
	    		registerAlert.setAlertType(AlertType.ERROR);
	   		 	registerAlert.setContentText("Tento nickname už používa iný používate¾");
	            registerAlert.show();
	            return;
	    	}
	    	
	    	db.registerUser(email,nickname,this.pswdField.getText());
	    	registerAlert.setAlertType(AlertType.INFORMATION);
   		 	registerAlert.setContentText("Používate¾ bol vytvorený!");
   		 	registerAlert.show();
   		 	this.goBack(event);
    	}
    	catch(Exception e){
    		//...
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
    
    

    public void setLabelText(String text){
        signUpBtn.setText(text);
    }

}
