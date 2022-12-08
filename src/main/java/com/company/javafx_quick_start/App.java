package com.company.javafx_quick_start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Fxml/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
