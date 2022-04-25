package com.musicverse.client.gui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.Localozator;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ChoiceBox<String> languageOption;

    @FXML
    private PasswordField newPswdField;

    @FXML
    private TextField nickField;

    @FXML
    private Label nickLabel;

    @FXML
    private Button saveBtn;

    private ServerAPI api;

    @FXML
    private Label title;

    private String from;

    private Alert loginAlert = new Alert(Alert.AlertType.NONE);

    @FXML
    void onBackLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initMainScreen(event);
    }

    @FXML
    void onDiscardBtnClick(ActionEvent event) {

    }

    @FXML
    void onSaveBtnClick(ActionEvent event) throws IOException {
        String nickname = "null", newPswd = "null", confirmPswd = "null", confirmOldPswd = "null";
        nickname = nickField.getText();
        newPswd = newPswdField.getText();
        confirmPswd = confirmPswdField.getText();
        confirmOldPswd = confirmOldPswdField.getText();

        this.api = ServerAPI.getInstance();

        InitScreensFunctions initScreensFunctions = new InitScreensFunctions();

        if (nickname != null || newPswd != null){
            if (newPswd.getBytes(StandardCharsets.UTF_8).length > 0){
                if (!newPswd.equals(confirmPswd)){
                    loginAlert.setAlertType(Alert.AlertType.ERROR);
                    loginAlert.setContentText("Hesla sa nezhoduju!");
                    loginAlert.show();
                } else {
                    Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)");
                    Matcher matcher = pattern.matcher(newPswd);
                    boolean matchFound = matcher.find();
                    if(matchFound && newPswd.getBytes(StandardCharsets.UTF_8).length > 7) {
                        loginAlert.setAlertType(Alert.AlertType.INFORMATION);
                        loginAlert.setContentText("Heslo vyhovuje");
                        loginAlert.show();
                        if (confirmOldPswd.getBytes(StandardCharsets.UTF_8).length > 1) {
                            if (api.updateUser(PreferencesLogin.getPrefs().getId(), newPswd, nickname, confirmOldPswd) == 1){
                                loginAlert.setAlertType(Alert.AlertType.INFORMATION);
                                loginAlert.setContentText("Akcia prebehla uspesne");
                                loginAlert.show();
                                initScreensFunctions.initMainScreen(event);
                            } else {
                                loginAlert.setAlertType(Alert.AlertType.INFORMATION);
                                loginAlert.setContentText("Pravdepodobne ste nezadali spravne vase heslo");
                                loginAlert.show();
                            }
                        }
                        else{
                            loginAlert.setAlertType(Alert.AlertType.ERROR);
                            loginAlert.setContentText("Zadajte svoje aktualne heslo");
                            loginAlert.show();
                        }
                    } else {
                        loginAlert.setAlertType(Alert.AlertType.ERROR);
                        loginAlert.setContentText("Heslo nie je dostatocne silne");
                        loginAlert.show();
                    }
                }
            } else {
                assert nickname != null;
                if (nickname.getBytes(StandardCharsets.UTF_8).length > 0) {
                    if (confirmOldPswd.getBytes(StandardCharsets.UTF_8).length > 1) {
                        if (api.updateUser(PreferencesLogin.getPrefs().getId(), newPswd, nickname, confirmOldPswd) == 1){
                            loginAlert.setAlertType(Alert.AlertType.INFORMATION);
                            loginAlert.setContentText("Akcia prebehla uspesne");
                            loginAlert.show();
                            initScreensFunctions.initMainScreen(event);
                        } else {
                            loginAlert.setAlertType(Alert.AlertType.ERROR);
                            loginAlert.setContentText("Pravdepodobne ste nezadali spravne vase aktualne heslo");
                            loginAlert.show();
                        }
                    } else {
                        loginAlert.setAlertType(Alert.AlertType.ERROR);
                        loginAlert.setContentText("Zadajte svoje aktualne heslo");
                        loginAlert.show();
                    }
                }
            }
        }

        if (languageOption.getValue().equals("English")){
            Localozator.setLocale(new Locale("en", "US"));
        }
        if (languageOption.getValue().equals("Slovenčina")){
            Localozator.setLocale(new Locale("sk", "SK"));
        }
        initScreensFunctions.initMainScreen(event);
    }

    public void setFrom(String from){
        this.from = from;
    }

}
