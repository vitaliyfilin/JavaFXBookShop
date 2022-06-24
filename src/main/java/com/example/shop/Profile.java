package com.example.shop;

import com.example.shop.DataBase.DBShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Profile {
    private ObservableList<user_book> userBookObservableList = FXCollections.observableArrayList();
    private ObservableList<User> userObservableList = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button edit_pasword;
    @FXML
    private TextField password_field;
    @FXML
    private URL location;

    @FXML
    private Button add_button;

    @FXML
    private Button back_button;

    @FXML
    private TableColumn<User, String> countryColumn;

    @FXML
    private Button edit_button;

    @FXML
    private TableColumn<User, String> genderColumn;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, BigDecimal> moneyColumn;

    @FXML
    private TextField money_field;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TextField name_field;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private Text profile_page;

    @FXML
    private TableView<User> profile_table;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableView<user_book> userbook_table;

    @FXML
    private TableColumn<user_book, String> userbookauthor;

    @FXML
    private TableColumn<user_book, Integer> userbookid;

    @FXML
    private TableColumn<user_book, String> userbookname;

    @FXML
    private TableColumn<user_book, Integer> userbooknum;

    @FXML
    private TableColumn<user_book, BigDecimal> userbookprice;

    @FXML
    private TableColumn<user_book, Integer> userbookpurchaseid;

    @FXML
    private Button feedback_button;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        userBookObservableList.addAll(DBShop.getUserBooks(HelloController.currentuser));
        userObservableList.addAll(DBShop.getUserProfile(HelloController.currentuser));
        DBShop.getGender(HelloController.currentuser);

        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<User, BigDecimal>("money"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));


        userbookid.setCellValueFactory(new PropertyValueFactory<user_book, Integer>("userbookid"));
        userbookname.setCellValueFactory(new PropertyValueFactory<user_book, String>("userbookname"));
        userbookauthor.setCellValueFactory(new PropertyValueFactory<user_book, String>("userbookauthor"));
        userbookprice.setCellValueFactory(new PropertyValueFactory<user_book, BigDecimal>("userbookprice"));
        userbooknum.setCellValueFactory(new PropertyValueFactory<user_book, Integer>("userbooknum"));
        userbookpurchaseid.setCellValueFactory(new PropertyValueFactory<user_book, Integer>("userbookpurchaseid"));


        profile_table.setItems(userObservableList);
        userbook_table.setItems(userBookObservableList);


        add_button.setOnAction(x -> {
            String money = money_field.getText();
            if (Long.parseLong(money) > 0) {
                try {
                    DBShop.addMoney(HelloController.currentuser, BigDecimal.valueOf(Long.parseLong(money)));
                } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        back_button.setOnAction(x -> {
            try {
                openPage("Bookshop.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        edit_button.setOnAction(x -> {
            if (!name_field.getText().isEmpty() && name_field.getText().length() > 3) {
                try {
                    DBShop.editName(name_field.getText(), HelloController.currentuser);
                } catch (SQLException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException e) {
                    e.printStackTrace();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Name can't be blank and must be at least 3 characters long");
                errorAlert.showAndWait();
            }
        });


        edit_pasword.setOnAction(x -> {
            if (!password_field.getText().isEmpty()) {
                try {
                    DBShop.editPassword(password_field.getText(), HelloController.currentuser);
                } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Password can't be blank");
                errorAlert.showAndWait();
            }
        });
        feedback_button.setOnAction(x -> {
            try {
                openPage("Messages.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void openPage(String str) throws IOException {
        back_button.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(str));
        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}


