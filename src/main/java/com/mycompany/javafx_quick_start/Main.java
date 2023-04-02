package com.mycompany.javafx_quick_start;

import com.mycompany.javafx_quick_start.utils.db.DbSource;

public class Main {
    public static void main(String[] args) {
        DbSource.getInstance();
        JavaFXQuickStartApplication.main(args);
    }
}