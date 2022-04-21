package com.musicverse.client.collections;

import com.musicverse.client.objects.Artist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Objects;

public class Controller <U> {

    public Controller(){
    }

    public TableView<U> initialize(ArrayList<U> records, String[] columns, TableView<U> tableView, int what){

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

        }

        tableView.setItems(data);
        boolean x = tableView.getColumns().addAll(tableColumnArrayList);
        System.out.println(x);

        return tableView;
    }


}
