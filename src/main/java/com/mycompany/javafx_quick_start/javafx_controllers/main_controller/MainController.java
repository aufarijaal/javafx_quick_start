package com.mycompany.javafx_quick_start.javafx_controllers.main_controller;

import com.mycompany.javafx_quick_start.AppSession;
import com.mycompany.javafx_quick_start.javafx_controllers.IController;
import com.mycompany.javafx_quick_start.models.UserModel;
import com.mycompany.javafx_quick_start.repositories.UserRepository;
import com.mycompany.javafx_quick_start.stages.MainStage;
import com.mycompany.javafx_quick_start.utils.MaterialColors;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable, IController {
    @FXML
    public TableView<UserModel> tblUsers;
    @FXML
    public FlowPane loginPopup;
    @FXML
    public Button btnCancel;
    @FXML
    public Button btnLogin;
    @FXML
    public PasswordField txtPassword;
    @FXML
    public TextField txtUsername;
    @FXML
    public Label lblLoginMessage;
    @FXML
    public Button btnAddUser;
    @FXML
    public Button btnUpdateUser;
    @FXML
    public Button btnDeleteUser;
    @FXML
    public Button btnReset;
    @FXML
    public Button btnRefresh;
    @FXML
    public TextField txtTblUsersUsername;
    @FXML
    public PasswordField txtTblUsersPassword;
    @FXML
    public CheckBox ckTblUsersFullAccess;
    @FXML
    public Label lblTblUsersMessage;
    @FXML
    public Label lblLoginInfo;
    @FXML
    public Button btnSwitchUser;
    @FXML
    public HBox crudUserControls;
    @FXML
    public FlowPane mainLayout;

    ObservableList<UserModel> usersData;

    private Long idForUpdate = 0L;
    private final UserRepository userRepository = new UserRepository();

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    PauseTransition pause = new PauseTransition();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing main scene from controller");
        initializeTable();
        initializeGraphics();
        pause.setDuration(new Duration(2000));
        pause.setOnFinished((event) -> {
            lblTblUsersMessage.setTextFill(Color.BLACK);
            lblTblUsersMessage.setText("Info");
        });
    }

    @FXML
    public void btnLogin_onClick(ActionEvent actionEvent) {
        if(txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            return;
        }

        UserModel findUser = userRepository.findByUsername(txtUsername.getText());

        if(findUser != null) {
            AppSession.loggedInUser = findUser;
            AppSession.loggedInAt = LocalDateTime.now();

            crudUserControls.setDisable(AppSession.loggedInUser.getAccessLevel().equals("basic"));
            lblLoginInfo.setText(AppSession.loggedInUser.getUsername() + ", logged in at: " + AppSession.loggedInAt);
            loginPopup.setVisible(false);
            mainLayout.setDisable(false);

            logger.info("New user logged in, access level: " + AppSession.loggedInUser.getAccessLevel());
        }
    }

    @FXML
    public void btnCancel_onClick(ActionEvent actionEvent) {
        loginPopup.setVisible(false);
    }

    @FXML
    public void btnAddUser_onClick(ActionEvent actionEvent) {
        if (!validateInput()) return;

        int result = userRepository.create(new UserModel(txtTblUsersUsername.getText(), txtTblUsersPassword.getText(), ckTblUsersFullAccess.isSelected() ? "full" : "basic"));
        if (result == 1) {
            refresh("New user added");
            reset();
        } else if (result == 0) {
            showMessage("error", "User creation failed");
        } else if (result == -1) {
            showMessage("error", "Username already exists");
        }
    }

    @FXML
    public void btnUpdateUser_onClick(ActionEvent actionEvent) {
        if (!validateInput()) return;

        int result = userRepository.update(new UserModel(idForUpdate, txtTblUsersUsername.getText(), txtTblUsersPassword.getText(), ckTblUsersFullAccess.isSelected() ? "full" : "basic"));
        if (result == 1) {
            refresh("User data updated");
            reset();
        } else if (result == 0) {
            showMessage("error", "User update failed");
        } else if (result == -1) {
            showMessage("error", "Username already exists");
        }
    }

    @FXML
    public void btnDeleteUser_onClick(ActionEvent actionEvent) {
        if (tblUsers.getSelectionModel().isEmpty()) {
            showMessage("warning", "Please select one data to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmDialog();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int deletedUser = userRepository.delete(tblUsers.getSelectionModel().getSelectedItem().getId());
            if (deletedUser == 0) {
                showMessage("error", "User delete failed");
            } else {
                refresh("User deleted");
            }
            reset();
        }
    }

    @FXML
    public void btnReset_onClick(ActionEvent actionEvent) {
        reset();
    }

    @FXML
    public void btnRefresh_onClick(ActionEvent actionEvent) {
        refresh("Data refreshed");
    }

    @FXML
    public void btnSwitchUser_onClick(ActionEvent actionEvent) {
        txtUsername.setText("");
        txtPassword.setText("");

        loginPopup.setVisible(true);
        btnCancel.setDisable(false);
    }

    @Override
    public void showMessage(String type, String message) {
        lblTblUsersMessage.setTextFill(type.equals("error") ? MaterialColors.red500 : type.equals("warning") ? MaterialColors.orange500 : MaterialColors.blue500);
        lblTblUsersMessage.setText(message);
        pause.playFromStart();
    }

    @Override
    public void refresh(String message) {
        usersData = FXCollections.observableArrayList(userRepository.findAll());

        tblUsers.setItems(usersData);
        showMessage("info", message);
    }

    @Override
    public void reset() {
        txtTblUsersUsername.setText("");
        txtTblUsersPassword.setText("");
        ckTblUsersFullAccess.setSelected(false);
        idForUpdate = 0L;

        btnUpdateUser.setDisable(true);
        btnDeleteUser.setDisable(true);
    }

    private void initializeTable() {
        TableColumn<UserModel, Integer> columnId = new TableColumn<>("Id");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<UserModel, String> columnUsername = new TableColumn<>("Username");
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<UserModel, String> columnPassword = new TableColumn<>("Password");
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<UserModel, String> columnAccessLevel = new TableColumn<>("Access Level");
        columnAccessLevel.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getAccessLevel()));

        usersData = FXCollections.observableArrayList(userRepository.findAll());

        tblUsers.setItems(usersData);
        tblUsers.getColumns().addAll(columnId, columnUsername, columnPassword, columnAccessLevel);
        columnId.setVisible(false);

        tblUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                idForUpdate = newValue.getId();
                ckTblUsersFullAccess.setSelected(newValue.getAccessLevel().equals("full"));
                txtTblUsersUsername.setText(newValue.getUsername());
                txtTblUsersPassword.setText(newValue.getPassword());

                btnDeleteUser.setDisable(false);
                btnUpdateUser.setDisable(false);
            }
        });
    }

    private void initializeGraphics() {
        btnAddUser.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/fugue_icons/plus-button.png")))));
        btnUpdateUser.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/fugue_icons/pencil-button.png")))));
        btnDeleteUser.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/fugue_icons/cross-button.png")))));
        btnReset.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/fugue_icons/arrow-circle-135-left.png")))));
        btnRefresh.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/fugue_icons/arrow-circle-double.png")))));
    }

    private boolean validateInput() {
        if (txtTblUsersUsername.getText().trim().isEmpty() || txtTblUsersPassword.getText().trim().isEmpty()) {
            showMessage("warning", "Fill all the input");
            return false;
        }
        return true;
    }

    private Optional<ButtonType> showConfirmDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(String.format("Are you sure want to delete %s?", tblUsers.getSelectionModel().getSelectedItem().getUsername()));
        alert.initOwner(MainStage.getInstance());
        return alert.showAndWait();
    }
}
