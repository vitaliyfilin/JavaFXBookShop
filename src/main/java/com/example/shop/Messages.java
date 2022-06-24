package com.example.shop;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.shop.DataBase.DBShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Messages {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea feedback_field;

    @FXML
    private Button main_button;

    @FXML
    private Button profile_button;

    @FXML
    private Button send_button;

    @FXML
    void initialize() {
        send_button.setOnAction(x -> {
            if (!feedback_field.getText().isEmpty() && feedback_field.getText().length() > 3) {
                try {
                    DBShop.sendMessage(HelloController.currentuser, feedback_field.getText());
                } catch (SQLException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException e) {
                    e.printStackTrace();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Message can't be blank and must be at least 3 characters long");
                errorAlert.showAndWait();
            }
        });
        profile_button.setOnAction(x-> {
            try {
                openPage("Profile.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        main_button.setOnAction(x-> {
            try {
                openPage("bookshop.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void openPage(String str) throws IOException {
        profile_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
