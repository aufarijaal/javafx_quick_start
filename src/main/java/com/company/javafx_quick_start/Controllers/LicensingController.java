package com.company.javafx_quick_start.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LicensingController {
    @FXML
    private TextField tfInputKodeLisensi;
    @FXML
    private Button btnRegisterAplikasi;

    @FXML
    protected void btnRegisterAplikasi_Click() {
        try {
            // TODO
            // add licensing to firestore.
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
