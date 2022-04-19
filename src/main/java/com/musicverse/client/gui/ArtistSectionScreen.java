package com.musicverse.client.gui;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.node.ListNode;
import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.val;

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
    private Label artistName;

    private int id;

    private JsonNode data;

    @FXML
    void backLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initMainScreen(event);
    }

    public void setSettings(String from){
        this.from = from;
        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarSettings.getMenus().add(settingsDropDownImported.menu(2, artistNameLabel, "artist"));
    }

    public void load(){
        val api = ServerAPI.getInstance();
        data = api.loadArtistByUser(PreferencesLogin.getPrefs().getId());

        this.artistName.setText(data.getString("name"));
        this.genreLabel.setText(data.getString("genre"));
        this.descriptionArea.setText(data.getString("description"));


    }


    @FXML
    void onEditBtnClick(MouseEvent event) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Edit artist name"));
        dialogVbox.getChildren().add(new TextField());
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }


}
