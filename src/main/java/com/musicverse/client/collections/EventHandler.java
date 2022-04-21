package com.musicverse.client.collections;

import javafx.scene.layout.AnchorPane;

public interface EventHandler<U> {
    public void handler(U u, AnchorPane mainPane);
}
