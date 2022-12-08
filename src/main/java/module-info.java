module com.company.javafx_quick_start {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.company.javafx_quick_start to javafx.fxml;
    exports com.company.javafx_quick_start;
    opens com.company.javafx_quick_start.Controllers to javafx.fxml;
    exports com.company.javafx_quick_start.Controllers;
    exports com.company.javafx_quick_start.Models;
    exports com.company.javafx_quick_start.Helpers;
}