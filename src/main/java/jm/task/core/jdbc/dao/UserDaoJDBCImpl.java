package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        if (connection == null) return;
        try {
            String sql = "CREATE TABLE IF NOT EXISTS users(" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastname VARCHAR(64) NOT NULL," +
                    "age INT NOT NULL)";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("ОШИБКА - создание таблицы\n" + e.getMessage() + '\n');
        }
    }

    public void dropUsersTable() {
        if (connection == null) return;
        try {
            String sql = "DROP TABLE IF EXISTS users";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("ОШИБКА - удаление таблицы\n" + e.getMessage() + "\n");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (connection == null) return;
        try {
            String sql = String.format("INSERT into users (name, lastname, age) " +
                    "values ('%s','%s',%d)", name, lastName, age);
            connection.prepareStatement(sql).executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Возникла ОШИБКА\n" + e.getMessage() + "\n");
        }
    }

    public void removeUserById(long id) {
        if (connection == null) return;
        try {
            String sql = "DELETE from users WHERE id = " + id;
            connection.prepareStatement(sql).executeUpdate();
        } catch (SQLException e) {
            System.out.println("ОШИБКА - удаление юзера\n" + e.getMessage() + '\n');
        }
    }

    public List<User> getAllUsers() {
        if (connection == null) return null;
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * from users";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            while (resultSet.next()) {
                User newUser = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        (byte) resultSet.getInt("age"));
                newUser.setId(resultSet.getLong("id"));
                users.add(newUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        if (connection == null) return;
        try {
            String sql = "DELETE from users";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("ОШИБКА - очистка\n" + e.getMessage() + '\n');
        }
    }
}
