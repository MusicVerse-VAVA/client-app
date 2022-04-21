package com.musicverse.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.musicverse.client.InitScreensFunctions;
import com.musicverse.client.collections.Controller;
import com.musicverse.client.collections.Utils;
import com.musicverse.client.objects.Artist;
import com.musicverse.client.objects.Song;
import com.musicverse.client.sessionManagement.PreferencesLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import com.musicverse.client.api.ServerAPI;
import lombok.val;

public class MainScreen {


    @FXML
    public AnchorPane mainPane;

    @FXML
    private Label likedAlbumsLabel1;

    @FXML
    private Label likedArtistsLabel;

    @FXML
    private Label likedSongsLabel1;

    @FXML
    private Label loginMainScreenLabel;

    @FXML
    private Label nickLabel;

    @FXML
    private Label playlistsLabel;

    @FXML
    private ListView listOfPlaylists;


    @FXML
    private Label registerMainScreenLabel;


    @FXML
    private MenuItem settingsMenuItemSettings;

    @FXML
    private MenuButton settingsDropDown;

    @FXML
    private MenuBar menuBarTest;

    @FXML
    private MenuItem settingsMenuItemAA;

    private int role = -1;

    @FXML
    private Label homeLabel;

    private String userName = "Guest";

    private int genre_id = -1;

    private ServerAPI server_api;

    @FXML
    private GridPane rectanglesGrid;

    @FXML
    private TableView<Song> tableSongs;

    private int showType = 0;

    @FXML
    private TableView<Artist> tableView;

    public MainScreen(){
        try {
            this.server_api = ServerAPI.getInstance();
            this.showType = 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    static String randomColor(){

        int red = (int)Math.floor(Math.random()*(255-1+1)+1);
        int green = (int)Math.floor(Math.random()*(255-1+1)+1);
        int blue = (int)Math.floor(Math.random()*(255-1+1)+1);

        String red1 = String.valueOf(red);
        String green1 = String.valueOf(green);
        String blue1 = String.valueOf(blue);

        return "-fx-background-color: rgba(" + red1 +"," + green1 + "," + blue1 + ",0.99); -fx-pref-height: 60px";
    }

    public void setRectangles(ArrayList<String> items){

        int rowNum = (int) Math.ceil((double) items.size() / 6);

        for (int i = 0; i < rowNum; i++) {
            RowConstraints rowConst = new RowConstraints(100);
            rowConst.setPercentHeight(100.0 / rowNum);
            rectanglesGrid.getRowConstraints().add(rowConst);
        }


        int counter = 0;
        for (int row=0; row < rowNum; row++) {
            for (int col=0; col < 6; col++){

                Label labelOfBox = new Label(items.get(counter));
                labelOfBox.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-padding: 5; -fx-font-weight: bold");

                Pane pane = new Pane();
                pane.setStyle(randomColor());

                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().add(labelOfBox);

                pane.setOnMouseReleased(e -> {

                    this.genre_id = setGenreId(labelOfBox.getText(),items);
                    val artists = this.server_api.getArtistsByGenre(genre_id);

                    ArrayList<Artist> artistArrayList = new ArrayList<>();

                    for (int i = 0; i < artists.size(); i++){
                        artistArrayList.add(
                                new Artist(
                                        artists.get(i).getString("name"),
                                        artists.get(i).getString("description"),
                                        String.valueOf(artists.get(i).getInt("id")),
                                        labelOfBox.getText())
                                );
                    }
                    System.out.println(artistArrayList.get(0).getGenre());

                    Controller<Artist> controller = new Controller<>();
                    tableView.getItems().clear();
                    tableView = controller.initialize(artistArrayList, new String[]{"name", "description", "id", "genre"}, tableView, 0, mainPane);
                    tableView.setVisible(true);
                    tableView.refresh();
                    System.out.println(tableView.getColumns().toString());




                   //TEST API
                   //val songs1 = this.server_api.songsByAlbum(1);
                   //val songs2 = this.server_api.songsByPlaylist(1);

                });

                pane.getChildren().add(anchorPane);
                rectanglesGrid.add(pane, col, row);

                if (counter == (items.size() -1)){
                    break;
                }
                counter++;
            }
            if (counter == (items.size() )){
                break;
            }
        }

    }


    public void setRole(int i){

        this.role = i;

        SettingsDropDown settingsDropDownImported = new SettingsDropDown();
        menuBarTest.getMenus().add(settingsDropDownImported.menu(role, mainPane, "main"));

        registerMainScreenLabel.setVisible(false);
        loginMainScreenLabel.setVisible(false);
        nickLabel.setText(userName);

        if (role > 0)
            nickLabel.setText(PreferencesLogin.getPrefs().getNickName());

        switch (role) {
            case -1:
                // here comes code, when user is not verified in case he is atempting to login and he is not saved user
                break;

            case 0:
                playlistsLabel.setText("Only for registered");
                likedAlbumsLabel1.setVisible(false);
                likedArtistsLabel.setVisible(false);
                likedSongsLabel1.setVisible(false);
                registerMainScreenLabel.setVisible(true);
                loginMainScreenLabel.setVisible(true);
                break;

            case 1:
                break;

            case 2:
                break;
            case 3:
            default:
                break;
        }
    }

    public void setPlaylists(HashMap<Integer,String> list){

        for (String name : list.values()){
            listOfPlaylists.getItems().add(name);
        }

        listOfPlaylists.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int result = getKeyByValue(list, listOfPlaylists.getSelectionModel().getSelectedItem());
                val songs2 = server_api.songsByPlaylist(result);

                Utils utils = new Utils();
                tableSongs = utils.generateTableSongs(tableSongs, songs2, mainPane);

                /*ArrayList<Song> songArrayList = new ArrayList<>();


                //String albumId, String data, String description, String duration, String id, String name,
                        //String artistId, String genre_id, String genre, String artist, String album
                for (int i = 0; i < songs2.size(); i++){
                    songArrayList.add(
                            new Song(
                                    String.valueOf(songs2.get(i).getInt("album_id")),
                                    songs2.get(i).getString("data"),
                                    songs2.get(i).getString("description"),
                                    songs2.get(i).getString("name"),
                                    songs2.get(i).getString("album"),
                                    songs2.get(i).getString("artist"),
                                    String.valueOf(songs2.get(i).getInt("duration")),
                                    songs2.get(i).getString("genre"),
                                    String.valueOf(songs2.get(i).getInt("id")),
                                    String.valueOf(songs2.get(i).getInt("artist_id")),
                                    String.valueOf(songs2.get(i).getInt("genre_id"))
                                    )
                    );
                }
               tableSongs.getColumns().clear();
                tableSongs.getItems().clear();
                Controller<Song> controller = new Controller<>();
                tableSongs = controller.initialize(
                        songArrayList,
                        new String[]{"album_id", "data", "description",
                                "name", "album", "artist","duration","genre", "id",  "artist_id", "genre_id"},
                        tableSongs, 1, mainPane
                );
                tableSongs.setVisible(true);
                tableSongs.refresh();

                System.out.println(songs2);*/
            }
        });

    }

    public static <T,E> T getKeyByValue(HashMap<T, E> map, Object value) {
        for (Map.Entry<T,E> entry : map.entrySet()){
            if (Objects.equals(value, entry.getValue())){
                return entry.getKey();
            }
        }
        return (T) Integer.valueOf(0);
    }

    public int setGenreId(String name,ArrayList<String> list){
        for (int i=0;i<list.size();i++){
            if(list.get(i).equals(name))
               return i+1;
        }
        return -1;
    }


    public int getGenreId(){
        return this.genre_id;
    }


    public ArrayList<String> getArtistsNames(){
        val artists = this.server_api.getArtistsByGenre(getGenreId());
        ArrayList<String> filtered = new ArrayList<>();
        for (int i = 0; i < artists.size(); i++)
            filtered.add(artists.get(i).getString("name"));
        return filtered;
    }




    @FXML
    void loginLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initLoginScreen(event);
    }

    @FXML
    void registerLabelClick(ActionEvent event) throws IOException {
        new InitScreensFunctions().initRegistrationScreen(event, 1);
    }


    @FXML
    void onHomeClicked(MouseEvent event) {
        tableView.setVisible(false);
        tableSongs.setVisible(false);
    }

}
