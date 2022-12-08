package com.company.javafx_quick_start.Controllers;

import com.company.javafx_quick_start.App;
import com.company.javafx_quick_start.Helpers.DBConnection;
import com.company.javafx_quick_start.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private TextField tfInputUsername;
    @FXML
    private TextField tfInputPassword;
    @FXML
    private Button btnLogin;

    private PreparedStatement stmt = null;
    private DBConnection dbConnection;
    private Connection c;

    @FXML
    protected void btnLogin_Click() {
        try {
            dbConnection = new DBConnection();
            c = dbConnection.getConnection();
            stmt = c.prepareStatement("SELECT * FROM users WHERE users.username = ? AND users.password = ?");
            stmt.setString(1, tfInputUsername.getText());
            stmt.setString(2, tfInputPassword.getText());
            ResultSet rs = stmt.executeQuery();
            User user = new User();

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String access_level = rs.getString("access_level");
                String username = rs.getString("username");

                user.setId(id);
                user.setUsername(username);
                user.setAccess_level(access_level);
            }

            boolean isFailed = user.getAccess_level() == null
                                && user.getId() == 0
                                && user.getUsername() == null;
            if(!isFailed) {
                stmt = c.prepareStatement("UPDATE loggedIn SET id = ?, username = ?, access_level = ? WHERE loggedIn.id = ?;");
                stmt.setInt(1, user.getId());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getAccess_level());
                stmt.setInt(4, 1);

                stmt.execute();

                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close();
                Stage mainWindowStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Fxml/MainWindow.fxml"));

                mainWindowStage.addEventFilter(WindowEvent.WINDOW_SHOWN, WindowEvent -> {
                    MainWindowController mainWindowController = fxmlLoader.getController();
                    mainWindowController.setLblDemoText(user.toString());
                });

                Scene scene = new Scene(fxmlLoader.load());
                mainWindowStage.setTitle("Application");
                mainWindowStage.setMaximized(true);
                mainWindowStage.setMinHeight(400);
                mainWindowStage.setMinWidth(450);
                mainWindowStage.setScene(scene);
                mainWindowStage.show();

                rs.close();
                stmt.close();
                c.close();
                System.out.println(c.isClosed());
            } else {
                new Alert(Alert.AlertType.ERROR, "Username or Password is incorrect.").showAndWait();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
