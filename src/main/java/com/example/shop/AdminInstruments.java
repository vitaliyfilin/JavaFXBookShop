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
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminInstruments {
    private ObservableList<Book> booksData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_book;

    @FXML
    private TextField author_field;

    @FXML
    private Button back_button;

    @FXML
    private TextField name_field;

    @FXML
    private TextField price_field;

    @FXML
    private ComboBox<Integer> quantity;
    List<Integer> list = IntStream.range(1, 111).boxed().collect(Collectors.toList());
    ObservableList<Integer> number = FXCollections.observableArrayList(list);

    @FXML
    private TableColumn<Book, String> authorcolumn;

    @FXML
    private TableColumn<Book, Integer> idcolumn;

    @FXML
    private TableColumn<Book, String> namecolumn;

    @FXML
    private TableColumn<Book, BigDecimal> pricecolumn;

    @FXML
    private TableColumn<Book, Integer> quantitycolumn;

    @FXML
    private Button removebutton;

    @FXML
    private TextField removefield;

    @FXML
    private TableView<Book> tablebooks;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        quantity.setItems(number);
        add_book.setOnAction(x -> {
            try {
                DBShop.addBook(new Book(name_field.getText(), author_field.getText(), new BigDecimal(price_field.getText()), quantity.getValue()));
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        back_button.setOnAction(x -> {
            try {
                openPage("hello-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        idcolumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        authorcolumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        pricecolumn.setCellValueFactory(new PropertyValueFactory<Book, BigDecimal>("price"));
        quantitycolumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("quantity"));
        booksData.addAll(DBShop.getCompleteListOfBooks());
        tablebooks.setItems(booksData);

    }

    public void openPage(String str) throws IOException {//функция открытия страницы
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
