package com.example.shop;

import com.example.shop.DataBase.DBShop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController {
    static int currentuser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authorization_button;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button register_button;

    @FXML
    void initialize() {
        authorization_button.setOnAction(x -> {
            try {
                if (DBShop.authorization(new User(login_field.getText(), password_field.getText()))) {
                    if (DBShop.getRole(login_field.getText()).equals("user")) {
                        currentuser = DBShop.getUserID(login_field.getText());
                        openPage("bookshop.fxml");
                    } else if (DBShop.getRole(login_field.getText()).equals("admin")) {
                        openPage("admin_instruments.fxml");
                    } else {
                        return;
                    }

                }
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        register_button.setOnAction(x -> {
            try {
                openPage("Register.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void openPage(String str) throws IOException {
        register_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
