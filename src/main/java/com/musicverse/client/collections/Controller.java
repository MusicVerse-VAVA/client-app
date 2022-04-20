package com.musicverse.client.collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;

public class Controller <U> {

    public Controller(){
    }

    public TableView<U> initialize(ArrayList<U> records, String[] columns, TableView<U> tableView){

       //TableView<U> tableView = new TableView<U>();
       ObservableList<U> data = FXCollections.observableArrayList(records);

       ArrayList<TableColumn<U,String>> tableColumnArrayList = new ArrayList<>();
        for (String column : columns) {
            TableColumn<U, String> tableColumn = new TableColumn<>(column);
            tableColumn.setCellValueFactory(new PropertyValueFactory<U,String>(column));
            tableColumnArrayList.add(tableColumn);
        }
        tableView.setItems(data);
        boolean x = tableView.getColumns().addAll(tableColumnArrayList);
        System.out.println(x);

        return tableView;
    }

}
