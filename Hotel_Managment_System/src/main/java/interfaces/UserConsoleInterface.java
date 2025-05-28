package interfaces;

import config.Configurations;
import managers.UserManager;
import models.user.User;
import services.*;

import java.util.Scanner;

public class UserConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final RepoService<User> userRepoService = new UserRepoService(Configurations.USER_FILE_NAME);
    private final UserManager userManager = new UserManager(userRepoService);

    public void start() {
        System.out.println("**********************************************************");
        System.out.println("*   press 1 - For login (if you have account)            *");
        System.out.println("*   press 2 - Registration (if you don't have account)   *");
        System.out.println("**********************************************************");

        String input = scanner.nextLine();
        while (!"1".equals(input) && !"2".equals(input)) {
            input = scanner.nextLine();
        }

        User user;
        switch (input) {
            case "1" -> user = login();
            case "2" -> user = registration();
        }


    }

    private User login() {
        System.out.println("****    You are in LOGIN page!    ****");
        System.out.println("Fill your username: ");
        String username = scanner.nextLine();
        System.out.println("Fill your password: ");
        String password = scanner.nextLine();

        boolean goToRegistration = false;
        User user = userManager.login(username, password);

        while (user == null && !goToRegistration) {
            System.out.println("FAIL TO LOGIN! Please Try again!");
            System.out.println("Fill your username: ");
            username = scanner.nextLine();
            if ("2".equals(username)) {
                goToRegistration = true;
                continue;
            }

            System.out.println("Fill your password: ");
            password = scanner.nextLine();
            user = userManager.login(username, password);
            System.out.println("You can go into the register page when you just press \"2\"");
        }

        if (goToRegistration) {
            user = registration();
        }

        return user;
    }

    private User registration() {
        System.out.println("****    You are in REGISTRATION page!    ****");
        System.out.println("Create your username: ");
        String username = scanner.nextLine();
        System.out.println("Create your username: ");
        String password = scanner.nextLine();

        User user = userManager.register(username, password);
        while (user == null && !isValid(username, password)) {
            System.out.println("FAIL TO REGISTER! Please Try again!");
            System.out.println("Fill your username: ");
            username = scanner.nextLine();
            System.out.println("Fill your username: ");
            password = scanner.nextLine();
            user = userManager.register(username, password);
        }

        return user;
    }

    private boolean isValid(String username, String password) {
        return (username.length() >= 5 && !username.isBlank()) &&
                (password.length() >= 5 && !password.isBlank());
    }
}
