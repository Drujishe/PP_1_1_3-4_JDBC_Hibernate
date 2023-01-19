package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS users(" +
                            "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(64) NOT NULL," +
                            "lastname VARCHAR(64) NOT NULL," +
                            "age INT NOT NULL)");
//            System.out.println("УСПЕШНО - создана таблица");
        } catch (SQLException e) {
            System.out.println("ОШИБКА - создание таблицы\n" + e.getMessage() + '\n');
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "DROP TABLE IF EXISTS users"
            );
//            System.out.println("УСПЕШНО - таблица удалена");
        } catch (SQLException e) {
            System.out.println("ОШИБКА - удаление таблицы\n" + e.getMessage() + "\n");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String execute = String.format("INSERT into users (name, lastname, age) " +
                    "values ('%s','%s',%d)", name, lastName, age);
            statement.executeUpdate(execute);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Возникла ОШИБКА\n" + e.getMessage() + "\n");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String execute = "DELETE from users WHERE id = " + id;
            statement.executeUpdate(execute);
//            System.out.println("УСПЕШНО - удаление юзера");
        } catch (SQLException e) {
            System.out.println("ОШИБКА - удаление юзера\n" + e.getMessage() + '\n');
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(
                    "SELECT * from users");
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User newUser = new User(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        (byte) resultSet.getInt(4));
                newUser.setId(resultSet.getLong(1));
                users.add(newUser);
            }
            return users;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE from users");
//            System.out.println("УСПЕШНО - очистка");
        } catch (SQLException e) {
            System.out.println("ОШИБКА - очистка\n" + e.getMessage() + '\n');
        }
    }
}
