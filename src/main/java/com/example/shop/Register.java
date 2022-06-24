package com.example.shop;

import com.example.shop.DataBase.DBShop;
import com.example.shop.DataBase.Gender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Register {
    Label selectedLbl = new Label();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField confirmpass_field;

    @FXML
    private TextField country_field;

    @FXML
    private Button create_button;

    @FXML
    private RadioButton female_button;

    @FXML
    private Button goback_button;

    @FXML
    private TextField login_field;

    @FXML
    private RadioButton male_button;

    @FXML
    private TextField password_field;

    public void openPage(String str) throws IOException {
        goback_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void returnPage(String str) throws IOException {
        create_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public int genderReturn() {
        if (selectedLbl.getText().equals("Male")) {
            return 0;
        } else if (selectedLbl.getText().equals("Female")) {
            return 1;
        } else {
            return 2;
        }
    }

    @FXML
    void initialize() {
        ToggleGroup group = new ToggleGroup();
        male_button.setToggleGroup(group);
        female_button.setToggleGroup(group);
        male_button.setOnAction(event -> selectedLbl.setText("Male"));
        female_button.setOnAction(event -> selectedLbl.setText("Female"));

        goback_button.setOnAction(x -> {
            try {
                openPage("hello-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        create_button.setOnAction(x -> {
            String confirm = confirmpass_field.getText();
            String country = country_field.getText();
            String password = password_field.getText();
            String login = login_field.getText();


            if (password.equals(confirm)) {
                if (country != null && !country.isEmpty()) {
                    if (login != null && !login.isEmpty() && login.length() > 2) {
                        User user = new User(login, password, Gender.values()[genderReturn()], country);
                        try {
                            DBShop.addUser(user);
                        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Error");
                        errorAlert.setContentText("Username can't be blank and must be at least 2 characters long");
                        errorAlert.showAndWait();
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Country can't be blank");
                    errorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Passwords do not match");
                errorAlert.showAndWait();
            }
        });
    }
}


