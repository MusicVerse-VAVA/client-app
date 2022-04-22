package com.musicverse.client.collections.itemActions;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public interface ItemActionDropDown<T> {

    public Menu setMenu(T item, AnchorPane pane, int id, int selectedId, ActionEvent event);

}
