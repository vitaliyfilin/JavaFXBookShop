package com.example.shop;

import com.example.shop.DataBase.DBShop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Bookshop {
    private ObservableList<Book> booksData = FXCollections.observableArrayList();

    @FXML
    private Button profile_button;
    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, Integer> idColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TableColumn<Book, BigDecimal> priceColumn;

    @FXML
    private TableView<Book> tableBooks;


    @FXML
    void initialize() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //booksData.clear();
        profile_button.setOnAction(x -> {
            try {
                openPage("profile.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        booksData.addAll(DBShop.getListOfBooks());


        idColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Book, BigDecimal>("price"));


        tableBooks.setItems(booksData);
        addButtonToTable();


    }

    private void addButtonToTable() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Error");
        errorAlert.setContentText("Insufficient funds");

        TableColumn<Book, Void> colBtn = new TableColumn("Button Column");
        BigDecimal zero = new BigDecimal(0);
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                    private final Button btn = new Button("Buy");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book book = getTableView().getItems().get(getIndex());
                            try {
                                if (DBShop.getPrice(book.getId()).compareTo(DBShop.getMoney(HelloController.currentuser)) <= 0) {
                                    if (DBShop.checkQuantity(book.getId())) {
                                        DBShop.updateMoney(HelloController.currentuser, DBShop.getMoney(HelloController.currentuser).subtract(DBShop.getPrice(book.getId())));
                                        DBShop.addJoinedTable(HelloController.currentuser, book.getId());
                                        DBShop.updateQuantity(book.getId(), 1);

                                    } else {
                                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                        errorAlert.setHeaderText("Error");
                                        errorAlert.setContentText("No books left");
                                        errorAlert.showAndWait();
                                    }
                                } else {
                                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                    errorAlert.setHeaderText("Error");
                                    errorAlert.setContentText("Insufficient funds avalaible");
                                    errorAlert.showAndWait();
                                }
                            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableBooks.getColumns().add(colBtn);

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
