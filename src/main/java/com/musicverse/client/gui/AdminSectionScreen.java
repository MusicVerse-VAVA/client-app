package com.musicverse.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class AdminSectionScreen {

    @FXML
    private Label NickNameValueLabel;

    @FXML
    private Label backLabel;

    @FXML
    private Button blockUserBtn;

    @FXML
    private Label dateOfRegistrationLabel;

    @FXML
    private Label dateOfRegistrationValueLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label emailValueLabel;

    @FXML
    private Rectangle lab;

    @FXML
    private MenuBar menuBarSettings;

    @FXML
    private Label nickLabel;

    @FXML
    private Label nickNameLabel;

    @FXML
    private ChoiceBox<?> roleIDchoiceBox;

    @FXML
    private Label roleIDlabel;

    @FXML
    private Button safeButton;

    @FXML
    private TextField searchuserField;

    @FXML
    private Button showAccountBtn;

    @FXML
    private Label title;

    @FXML
    private AnchorPane adminPane;

    @FXML
    void backLabelClick(MouseEvent event) {

    }

    @FXML
    void onBlockUserBtnClick(ActionEvent event) {

    }

    @FXML
    void onShowAccountBtnClick(ActionEvent event) {

    }

    private String from;

    public void setSettings(String from){
        this.from = from;
        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarSettings.getMenus().add(settingsDropDownImported.menu(3, adminPane, "admin"));
    }

}
