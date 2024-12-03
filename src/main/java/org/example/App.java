package org.example;



import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.model.User;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAOImpl();

        // Создаем таблицу пользователей при старте
        userDAO.createUserTable();

        while (true) {
            System.out.println("\n=== Меню ===");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Показать всех пользователей");
            System.out.println("3. Найти пользователя по ID");
            System.out.println("4. Обновить информацию о пользователе");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Выйти");

            System.out.print("Выберите операцию: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addUser(scanner, userDAO);
                case 2 -> showAllUsers(userDAO);
                case 3 -> findUserById(scanner, userDAO);
                case 4 -> updateUser(scanner, userDAO);
                case 5 -> deleteUser(scanner, userDAO);
                case 6 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                case 7 -> showUserCount(userDAO);
                case 8 -> showUserLogs(userDAO);

                default -> System.out.println("Неверный выбор. Повторите.");
            }
        }
    }

    private static void addUser(Scanner scanner, UserDAO userDAO) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите email пользователя: ");
        String email = scanner.nextLine();

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        userDAO.addUser(user);
        System.out.println("Пользователь успешно добавлен.");
    }

    private static void showAllUsers(UserDAO userDAO) {
        var users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Нет пользователей в базе данных.");
        } else {
            System.out.println("Список пользователей:");
            for (User user : users) {
                System.out.printf("ID: %d, Имя: %s, Email: %s%n", user.getId(), user.getUsername(), user.getEmail());
            }
        }
    }

    private static void findUserById(Scanner scanner, UserDAO userDAO) {
        System.out.print("Введите ID пользователя: ");
        int id = scanner.nextInt();

        User user = userDAO.getUserById(id);
        if (user != null) {
            System.out.printf("Найден пользователь: ID: %d, Имя: %s, Email: %s%n", user.getId(), user.getUsername(), user.getEmail());
        } else {
            System.out.println("Пользователь с таким ID не найден.");
        }
    }

    private static void updateUser(Scanner scanner, UserDAO userDAO) {
        System.out.print("Введите ID пользователя для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        User existingUser = userDAO.getUserById(id);
        if (existingUser == null) {
            System.out.println("Пользователь с таким ID не найден.");
            return;
        }

        System.out.print("Введите новое имя пользователя: ");
        String newUsername = scanner.nextLine();
        System.out.print("Введите новый email пользователя: ");
        String newEmail = scanner.nextLine();

        existingUser.setUsername(newUsername);
        existingUser.setEmail(newEmail);

        userDAO.updateUser(existingUser);
        System.out.println("Информация о пользователе успешно обновлена.");
    }

    private static void deleteUser(Scanner scanner, UserDAO userDAO) {
        System.out.print("Введите ID пользователя для удаления: ");
        int id = scanner.nextInt();

        User user = userDAO.getUserById(id);
        if (user == null) {
            System.out.println("Пользователь с таким ID не найден.");
            return;
        }

        userDAO.deleteUser(id);
        System.out.println("Пользователь успешно удален.");
    }

    private static void showUserCount(UserDAO userDAO) {
        int count = userDAO.getUserCount();
        System.out.println("Общее количество пользователей: " + count);
    }

    private static void showUserLogs(UserDAO userDAO) {
        List<UserLog> logs = userDAO.getUserLogs();
        if (logs.isEmpty()) {
            System.out.println("Логи отсутствуют.");
        } else {
            System.out.println("Логи пользователей:");
            for (UserLog log : logs) {
                System.out.printf("ID: %d, UserID: %d, Activity: %s, Log Time: %s%n",
                        log.getId(), log.getUserId(), log.getActivity(), log.getLogTime());
            }
        }
    }

}
