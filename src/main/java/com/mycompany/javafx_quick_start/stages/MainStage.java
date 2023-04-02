package com.mycompany.javafx_quick_start.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class MainStage extends Stage {
    private static MainStage instance = null;
    private static final Logger logger = LoggerFactory.getLogger(MainStage.class);
    private MainStage() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainStage.class.getResource("/fxml/main/main.fxml"));
            root = loader.load();
        } catch (IOException e) {
            logger.error("Error when loading Main stage, message: " + e.getMessage());
            for(StackTraceElement el : e.getStackTrace()) {
                logger.error(el.toString());
            }
        }

        Scene scene = new Scene(root);

        setTitle("JavaFX Quick Start Application");
        getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/javafx_quick_start_logo.png"))));
        setMinWidth(552);
        setMinHeight(465);
        setMaximized(true);
        setScene(scene);
    }

    public static MainStage getInstance() {
        logger.info("Loading Main stage...");
        if (instance == null) {
            instance = new MainStage();
        }

        return instance;
    }
}
