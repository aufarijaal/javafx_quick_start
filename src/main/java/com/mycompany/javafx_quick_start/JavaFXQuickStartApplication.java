package com.mycompany.javafx_quick_start;

import com.mycompany.javafx_quick_start.stages.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXQuickStartApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        MainStage mainStage = MainStage.getInstance();

        mainStage.show();
    }
}
