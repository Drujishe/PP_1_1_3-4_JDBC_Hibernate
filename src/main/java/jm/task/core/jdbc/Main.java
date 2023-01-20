package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Джеймс", "Кэмерон", (byte) 68);
        userService.saveUser("Стивен", "Спилберг", (byte) 76);
        userService.saveUser("Джордж", "Лукас", (byte) 78);
        userService.saveUser("Майкл", "Бэй", (byte) 57);

        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
