package com.mycompany.javafx_quick_start.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DbTest {
    @Test
    public void appFolderPathExistenceTest() {
        File path = new File(System.getProperty("user.home") + File.separator + "javafx_quick_start");

        if(!path.exists()) {
            // Do something...
        }
    }
}
