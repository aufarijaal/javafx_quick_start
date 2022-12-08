package com.company.javafx_quick_start.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainWindowController {
    @FXML
    private TextField tfDemoInput;
    @FXML
    public Label lblDemoText;

    @FXML
    protected void tfDemoInput_KeyReleased() {
        lblDemoText.setText(tfDemoInput.getText());
    }

    public MainWindowController() {
    }

    public void setLblDemoText(String text) {
        this.lblDemoText.setText(text);
    }
}
