package com.musicverse.client.gui;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.node.ListNode;
import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import java.util.ArrayList;
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
        this.id = data.getInt("id");


    }


    @FXML
    void onEditBtnClick(MouseEvent event) {
        val api = ServerAPI.getInstance();
        JsonNode jn = api.getGenres();
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < jn.size(); i++) {
            genres.add(jn.get(i).getString("genre"));
        }

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialog.setHeight(500);

        TextField nameField = new TextField(artistName.getText());
        TextField descriptionField = new TextField(descriptionArea.getText());
        Button buttonSave = new Button("Save");
        Button buttonCancel = new Button("Cancel");

        dialogVbox.getChildren().add(new Text("Edit artist name"));
        dialogVbox.getChildren().add(nameField);

        dialogVbox.getChildren().add(new Text("Edit genre"));
        ComboBox combo_box =
                new ComboBox(FXCollections
                        .observableArrayList(genres));
        dialogVbox.getChildren().add(combo_box);
        combo_box.getSelectionModel().select(genreLabel.getText());

        dialogVbox.getChildren().add(new Text("Edit description"));
        dialogVbox.getChildren().add(descriptionField);

        dialogVbox.getChildren().add(buttonSave);
        dialogVbox.getChildren().add(buttonCancel);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                api.editArtist(id, nameField.getText(), descriptionField.getText(), combo_box.getSelectionModel().getSelectedItem().toString());
                dialog.close();
            }
        });
    }


}
