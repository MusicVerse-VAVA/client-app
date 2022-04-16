package com.musicverse.client.gui;

import java.io.IOException;

import com.musicverse.client.InitScreensFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SettingsScreen {

    @FXML
    private Label backLabel;

    @FXML
    private PasswordField confirmOldPswdField;

    @FXML
    private PasswordField confirmPswdField;

    @FXML
    private Button discardBtn;

    @FXML
    private ChoiceBox<?> languageOption;

    @FXML
    private PasswordField newPswdField;

    @FXML
    private TextField nickField;

    @FXML
    private Label nickLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private Label title;

    private String from;

    @FXML
    void onBackLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initMainScreen(event);
    }

    @FXML
    void onDiscardBtnClick(ActionEvent event) {

    }

    @FXML
    void onSaveBtnClick(ActionEvent event) {

    }

    public void setFrom(String from){
        this.from = from;
    }

}
