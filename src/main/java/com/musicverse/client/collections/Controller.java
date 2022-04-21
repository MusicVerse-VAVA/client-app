package com.musicverse.client.collections;

import com.musicverse.client.objects.Album;
import com.musicverse.client.objects.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Objects;

public class Controller <U> {

    public Controller(){
    }

    public TableView<U> initialize(ArrayList<U> records, String[] columns, TableView<U> tableView, int what, AnchorPane mainPane){

       ObservableList<U> data = FXCollections.observableArrayList(records);
        tableView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/tableViewCss.css")).toExternalForm());
        U u;

       ArrayList<TableColumn<U,String>> tableColumnArrayList = new ArrayList<>();
        for (String column : columns) {
            TableColumn<U, String> tableColumn = new TableColumn<>(column);
            tableColumn.setCellValueFactory(new PropertyValueFactory<U,String>(column));
            tableColumnArrayList.add(tableColumn);


            //specify column widths... sum must be 940 to take full width of table ARTISTS
            if (what == 0)
                switch (column) {
                    case "id":
                        tableColumn.setVisible(false);
                        break;
                    case "genre":
                        tableColumn.setPrefWidth(100);
                        break;
                    case "description":
                        tableColumn.setPrefWidth(600);
                        break;
                    case "name":
                        tableColumn.setPrefWidth(240);
                        break;
                }

              //SONGS
            if (what == 1)
                switch (column) {
                    case "id":
                    case "description":
                    case "data":
                    case "album_id":
                    case "artist_id":
                    case "genre_id":
                        tableColumn.setVisible(false);
                        break;

                    case "name":
                    case "album":
                        tableColumn.setPrefWidth(250);
                        break;

                    case "artist":
                        tableColumn.setPrefWidth(240);
                        break;

                    case "duration":

                    case "genre":
                        tableColumn.setPrefWidth(100);
                        break;
                }

            //id, name, description, artist_id, genre_id, genre, artist;
            //Album WIDTH 840
            if (what == 2)
                switch (column) {
                    case "id":
                    case "artist_id":
                    case "genre_id":
                        tableColumn.setVisible(false);
                        break;

                    case "name":
                        tableColumn.setPrefWidth(220);
                        break;

                    case "artist":
                        tableColumn.setPrefWidth(210);
                        break;

                    case "description":
                        tableColumn.setPrefWidth(310);
                        break;

                    case "genre":
                        tableColumn.setPrefWidth(100);
                        break;
                }
        }

        tableView.setItems(data);
        boolean x = tableView.getColumns().addAll(tableColumnArrayList);

        tableView.setRowFactory(tv -> {
            TableRow<U> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!row.isEmpty() && mouseEvent.getButton()== MouseButton.PRIMARY
                && mouseEvent.getClickCount() == 1) {
                    U clickedRow = row.getItem();
                    if (what == 0){
                        Artist artist = (Artist) clickedRow;
                        EventHandler<Artist> eventHandler = new ArtistTableHandler();
                        eventHandler.handler(artist, mainPane);
                    }
                    if (what == 2){
                        Album album = (Album) clickedRow;
                        EventHandler<Album> eventHandler = new AlbumTableHandler();
                        eventHandler.handler(album, mainPane);
                    }
                }
            });
            return row;
        });
        System.out.println(x);

        return tableView;
    }


}
