package com.musicverse.client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.Objects;

public class MainScreen {

    @FXML
    private Label bluesBoxLabel;

    @FXML
    private Rectangle bluesGenreBox;

    @FXML
    private Label chillBoxLabel;

    @FXML
    private Rectangle chillGenreBox;

    @FXML
    private Label christmasBoxLabel;

    @FXML
    private Rectangle christmasGenreBox;

    @FXML
    private Label classicBoxLabel;

    @FXML
    private Rectangle classicGenreBox;

    @FXML
    private Label countryBoxLabel;

    @FXML
    private Rectangle countryGenreBox;

    @FXML
    private Label currentArtistLabel;

    @FXML
    private Label currentSongLabel;

    @FXML
    private Rectangle genresBoxes;

    @FXML
    private Label hiphopBoxLabel;

    @FXML
    private Rectangle hiphopGenreBox;

    @FXML
    private Label kidsBoxLabel;

    @FXML
    private Rectangle kidsGenreBox;

    @FXML
    private Label likedAlbumsLabel1;

    @FXML
    private Label likedArtistsLabel;

    @FXML
    private Label likedSongsLabel1;

    @FXML
    private Label loginMainScreenLabel;

    @FXML
    private Label mixBoxLabel;

    @FXML
    private Rectangle mixGenreBox;

    @FXML
    private Label nickLabel;

    @FXML
    private Label oldiesBoxLabel;

    @FXML
    private Rectangle oldiesGenreBox;

    @FXML
    private Label partyBoxLabel;

    @FXML
    private Rectangle partyGenreBox;

    @FXML
    private Rectangle playerBox;

    @FXML
    private Rectangle playerLine;

    @FXML
    private Label playlistsLabel;

    @FXML
    private Label popBoxLabel;

    @FXML
    private Rectangle popGenreBox;

    @FXML
    private Label registerMainScreenLabel;

    @FXML
    private Label rockBoxLabel;

    @FXML
    private Rectangle rockGenreBox;

    @FXML
    private Label searchMusicLabel;

    @FXML
    private Label settingsLabel;

    @FXML
    private Label title;

    @FXML
    private MenuButton settingsDropDown;

    @FXML
    private MenuItem settingsMenuItemAA;

    @FXML
    private MenuItem settingsMenuItemLogOut;

    @FXML
    private MenuItem settingsMenuItemProfile;

    @FXML
    private MenuItem settingsMenuItemSettings;

    @FXML
    private TextField searchField;

    private int role = -1;

    private String userName = "Guest";

    void setRole(int i){

        this.role = i;
        registerMainScreenLabel.setVisible(false);
        loginMainScreenLabel.setVisible(false);
        nickLabel.setText(userName);

        if (role > 0)
            nickLabel.setText("Simon Drienik");

        switch (role) {
            case -1:
                // here comes code, when user is not verified in case he is atempting to login and he is not saved user
                break;

            case 0:
                settingsDropDown.setVisible(false);
                playlistsLabel.setText("Only for registered");
                likedAlbumsLabel1.setVisible(false);
                likedArtistsLabel.setVisible(false);
                likedSongsLabel1.setVisible(false);
                registerMainScreenLabel.setVisible(true);
                loginMainScreenLabel.setVisible(true);
                break;

            case 1:
                settingsMenuItemAA.setVisible(false);
                break;

            case 2:
                settingsMenuItemAA.setText("Artist section");

            case 3:
                settingsMenuItemAA.setText("Admin section");
            default:
                break;
        }
    }

    @SneakyThrows
    void loadFxml(MouseEvent event, String fxml, String title){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
        Parent root = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200,600);
        window.setTitle(title);
        window.setScene(scene);
        window.show();
    }

    @FXML
    void loginLabelClick(MouseEvent event) {
        loadFxml(event,"/LoginScreen.fxml", "Login");
    }

    @FXML
    void registerLabelClick(MouseEvent event) {
        loadFxml(event,"/RegisterScreen.fxml", "Register");
    }




}
