package com.mycompany.javafx_quick_start.javafx_controllers;

import javafx.animation.PauseTransition;
import org.slf4j.Logger;

public interface IController {
    Logger logger = null;
    PauseTransition pause = null;
    PauseTransition pause2 = null;
    PauseTransition pause3 = null;

    int idForUpdate = 0;
    default void showMessage(String type, String message) {};
    default void showFormMessage(String type, String message) {

    };

    default void refresh(String message) {

    }

    default void reset() {}

    default void write() {}
    default void edit() {}
    default void delete() {}
}
