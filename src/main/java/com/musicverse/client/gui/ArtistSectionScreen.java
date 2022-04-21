package com.musicverse.client.gui;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.node.ListNode;
import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.api.ServerAPI;
import com.musicverse.client.collections.Controller;
import com.musicverse.client.collections.Utils;
import com.musicverse.client.objects.Album;
import com.musicverse.client.objects.Artist;
import com.musicverse.client.objects.Song;
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

    @FXML
    private TableView<Album> tableAlbums;

    private JsonNode data;

    @FXML
    private TableView<Song> tableSongs;

    @FXML
    private Button backBtn;

    /*@FXML
    void backLabelClick(ActionEvent event) throws IOException {
        InitScreensFunctions initScreensFunctions = new InitScreensFunctions();
        initScreensFunctions.initMainScreen(event);
    }*/

    @FXML
    void backBtnClick(ActionEvent event) throws IOException {
        InitScreensFunctions initScreensFunctions = new InitScreensFunctions();
        initScreensFunctions.initMainScreen(event);
    }

    public void setSettings(String from){
        this.from = from;
        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarSettings.getMenus().add(settingsDropDownImported.menu(2, artistNameLabel, "artist"));
    }



    public void load(int id, int shownTable){

        val api = ServerAPI.getInstance();
        data = api.loadArtist(PreferencesLogin.getPrefs().getId(), id);

        this.artistName.setText(data.getString("name"));
        this.genreLabel.setText(data.getString("genre"));
        this.descriptionArea.setText(data.getString("description"));
        this.id = data.getInt("id");
        if (id > 1)
            editDescriptionLabel.setVisible(false);
        this.descriptionArea.setEditable(false);

        ArrayList<Album> albumArrayList = new ArrayList<>();
        val albums = data.get("albums");

        //String id, String name, String description, String artist_id, String genre_id, String artist, String genre
        for (int i = 0; i < albums.size(); i++){
            albumArrayList.add(
                    new Album(
                            String.valueOf(albums.get(i).getInt("id")),
                            albums.get(i).getString("name"),
                            albums.get(i).getString("description"),
                            String.valueOf(albums.get(i).getInt("artist_id")),
                            String.valueOf(data.getInt("genre_id")),
                            data.getString("name"),
                            data.getString("genre")
                    )

            );
        }

        Controller<Album> controller = new Controller<>();
        tableAlbums.getItems().clear();
        tableAlbums = controller.initialize(albumArrayList, new String[]{"id", "name", "description", "artist_id", "genre_id", "artist", "genre"}, tableAlbums, 2, artistNameLabel);
        tableAlbums.setVisible(true);
        tableAlbums.refresh();

        tableSongs.setVisible(false);
        if (shownTable > 0){
            val songs = api.songsByAlbum(shownTable);
            Utils utils = new Utils();
            tableSongs.setVisible(true);
            tableSongs = utils.generateTableSongs(tableSongs, songs, artistNameLabel);
        }
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
