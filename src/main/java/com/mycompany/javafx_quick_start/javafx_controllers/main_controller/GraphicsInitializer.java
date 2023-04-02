package com.mycompany.javafx_quick_start.javafx_controllers.main_controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GraphicsInitializer {
    public static void initializeGraphics(
            Button btnAdd,
            Button btnUpdate,
            Button btnDelete,
            Button btnReset,
            Button btnRefresh,
            Button btnShowPassword
    ) {
        btnAdd.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/plus-button.png")))));
        btnUpdate.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/pencil-button.png")))));
        btnDelete.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/cross-button.png")))));
        btnReset.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/arrow-circle-135-left.png")))));
        btnRefresh.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/arrow-circle-double.png")))));
        btnShowPassword.setGraphic(new ImageView(new Image(Objects.requireNonNull(GraphicsInitializer.class.getResourceAsStream("/img/fugue_icons/eye.png")))));
    }
}
