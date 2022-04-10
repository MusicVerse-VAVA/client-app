package com.musicverse.client.gui;

import com.musicverse.client.InitScreensFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Objects;

public class ArtistSectionScreen {

    @FXML
    private Label albumsLabel;

    @FXML
    private Rectangle albumsRectangle;

    @FXML
    private AnchorPane artistNameLabel;

    @FXML
    private Circle avatarCircle;

    @FXML
    private Label avatarLabel;

    @FXML
    private Label backLabel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label editAvatarLabel;

    @FXML
    private Label editDescriptionLabel;

    @FXML
    private Label editGenreLabel;

    @FXML
    private Label editNameLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label nickLabel;

    @FXML
    private Label playlistsLabel;

    @FXML
    private Rectangle playlistsRectangle;

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
    private Label title;

    @FXML
    private MenuBar menuBarSettings;

    private String from;

    @FXML
    void backLabelClick(MouseEvent event) throws IOException {
        new InitScreensFunctions().initMainScreen(event);
    }

    public void setSettings(String from){
        this.from = from;
        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarSettings.getMenus().add(settingsDropDownImported.menu(2, artistNameLabel, "artist"));
    }


}
