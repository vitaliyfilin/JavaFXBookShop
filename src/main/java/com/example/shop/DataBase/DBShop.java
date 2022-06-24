package com.example.shop.DataBase;

import com.example.shop.Book;
import com.example.shop.User;
import com.example.shop.user_book;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBShop {
    public static Connection getConnection() throws
            SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost/shop?allowPublicKeyRetrieval=true&useSSL=false";
        String username = "admin";
        String password = "12345";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void addUser(User user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (checkUser(user) && checkName(user)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO users (name, password, gender, country, money, role) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getGender() + "");
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setBigDecimal(5, new BigDecimal(0));
            preparedStatement.setString(6, "user");
            preparedStatement.executeUpdate();
            Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
            errorAlert.setHeaderText("Success");
            errorAlert.setContentText("User profile created successfully");
            errorAlert.showAndWait();

        }
    }

    public static void addBook(Book book) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO books (name, author, price, quantity) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setBigDecimal(3, book.getPrice());
        preparedStatement.setInt(4, book.getQuantity());
        preparedStatement.executeUpdate();
    }

    public static boolean checkUser(User user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int rs = 0;
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            rs = resultSet.getInt(1);
        }
        if (rs == 1) {
            return false;
        }
        return true;
    }

    public static boolean authorization(User user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users where name = ? and password = ?");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        int counter = 0;
        while (resultSet.next()) {
            counter++;
        }
        if (counter > 0) {
            return true;
        }
        return false;
    }

    public static String getRole(String login) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT role FROM users where name = ?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static List<Book> getListOfBooks() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM books");

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<>();
        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4)));
        }
        return bookList;
    }

    public static List<Book> getCompleteListOfBooks() throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM books");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Book> bookList = new ArrayList<>();
        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4), resultSet.getInt(5)));
        }
        return bookList;
    }

    public static void addJoinedTable(int user_id, int book_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (DBShop.checkUserBooks(user_id, book_id)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE user_books set num = num + 1 where user_id = ? and book_id = ?");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, book_id);
            preparedStatement.executeUpdate();
        } else {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO user_books (user_id, book_id, num) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, book_id);
            preparedStatement.setInt(3, 1);
            preparedStatement.executeUpdate();

        }
    }

    public static boolean checkUserBooks(int user_id, int book_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement prepared = getConnection().prepareStatement("Select * from user_books where user_id = ? and book_id = ?");
        prepared.setInt(1, user_id);
        prepared.setInt(2, book_id);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            return true;
        }
        return false;
    }

    public static int getUserID(String login) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users where name = ?");
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static void addMoney(int user_id, BigDecimal money) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE users SET money = money + ? WHERE ID = ?");
        preparedStatement.setInt(2, user_id);
        preparedStatement.setBigDecimal(1, money);
        preparedStatement.executeUpdate();
    }

    public static List<user_book> getUserBooks(int user_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT b.id, b.name, b.author, b.price, ub.num, ub.id FROM books as b inner join user_books as ub on ub.book_id = b.id where ub.user_id = ?");
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<user_book> userBook = new ArrayList<>();
        while (resultSet.next()) {
            userBook.add(new user_book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4), resultSet.getInt(5), resultSet.getInt(6)));
        }
        return userBook;
    }

    public static List<User> getUserProfile(int user_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users where id = ?");
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            userList.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), Gender.Male, resultSet.getString(5), resultSet.getBigDecimal(6)));
        }
        return userList;
    }

    public static BigDecimal getPrice(int book_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT price FROM books where id = ?");
        preparedStatement.setInt(1, book_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getBigDecimal(1);
        }
        return null;
    }

    public static BigDecimal getMoney(int user_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT money FROM users where id = ?");
        preparedStatement.setInt(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getBigDecimal(1);
        }
        return null;
    }

    public static void editName(String name, int user_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE users SET name = ? WHERE ID = ?");
        preparedStatement.setInt(2, user_id);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }

    public static void editPassword(String password, int user_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE users SET password = ? WHERE ID = ?");
        preparedStatement.setInt(2, user_id);
        preparedStatement.setString(1, password);
        preparedStatement.executeUpdate();
    }

    public static void updateMoney(int user_id, BigDecimal money) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE users SET money = ? WHERE ID = ?");
        preparedStatement.setInt(2, user_id);
        preparedStatement.setBigDecimal(1, money);
        preparedStatement.executeUpdate();
    }

    public static boolean checkName(User user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM users where name = ?");
        if (user.getName() != null && !user.getName().isEmpty()) {
            preparedStatement.setString(1, user.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultSet.getString(2);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Username already exists");
                errorAlert.showAndWait();
                return false;
            }
            return true;
        }
        return true;
    }

    public static List<Book> searchBooks(String name) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM books where name LIKE '%?%'");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Book> bookList = new ArrayList<>();
        while (resultSet.next()) {
            bookList.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBigDecimal(4)));
        }
        return bookList;
    }

    public static void updateQuantity(int book_id, int quantity) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE books SET quantity = quantity - ? WHERE ID = ?");
        preparedStatement.setInt(1, quantity);
        preparedStatement.setInt(2, book_id);
        preparedStatement.executeUpdate();
    }

    public static Boolean checkQuantity(int book_id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PreparedStatement prepared = getConnection().prepareStatement("Select quantity from books where id = ?");
        prepared.setInt(1, book_id);
        ResultSet resultSet = prepared.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt(1) > 0) {
                return true;
            }
            return false;

        }
        return null;

    }
}


//SELECT b.id, b.name, b.author, b.price, ub.num, ub.id FROM books as b inner join user_books as ub on ub.book_id = b.id where ub.user_id = 5;

