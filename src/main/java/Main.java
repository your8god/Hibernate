import models.User;
import services.UserService;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Main main = new Main();
        while (true) {
            main.run();
        }
    }

    private void run() {
        System.out.print("Введите 1 для входа \nВведите 2 для регистрации \nКОМАНДА: ");
        try {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    login();
                    break;
                case 2:
                    registration();
                    break;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            run();
        }
    }

    private void login() {
        UserService userService = new UserService();
        Scanner in = new Scanner(System.in);

        System.out.print("Логин: ");
        String login = in.nextLine();
        System.out.print("Пароль: ");
        String password = in.nextLine();
        password = String.valueOf(password.hashCode());

        try {
            if (userService.findUserByPasswordAndLogin(password, login) == null)
                throw new ExceptionDB("Неверный логин или пароль! Попробуйте снова.");
            System.out.println("Вход выполнен!");

            Session session = new Session(userService.findUserByLogin(login));
            session.run();
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            login();
        }
    }

    private void registration() {
        UserService userService = new UserService();
        User user = new User();
        Scanner in = new Scanner(System.in);

        System.out.print("Поля со * обязательны для заполнения!\nВведите логин*: ");
        String login = in.nextLine();
        System.out.print("Введите пароль*(не менее 6 знаков): ");
        String password = in.nextLine();
        System.out.print("Введите имя*: ");
        String name = in.nextLine();
        System.out.print("Введите возраст: ");
        String age = in.nextLine();

        try {
            if (password.length() < 6)
                throw new ExceptionDB("Слишком мало символов в пароле!");

            if (!age.isEmpty() && !age.matches("-?\\d+(\\.\\d+)?"))//проверка на число
                throw new ExceptionDB("Неверный ввод! В поле возраста должно находиться число!");

            if (login.isEmpty() || password.isEmpty() || name.isEmpty())
                throw new ExceptionDB("Заполнены не все обязательные поля!");

            if (userService.findUserByLogin(login) != null)
                throw new ExceptionDB("Пользователь с таким логином уже есть! Используйте другой.");

            if (age.isEmpty())
                age = "0";

            user.setName(name);
            user.setPassword(String.valueOf(password.hashCode()));
            user.setLogin(login);
            user.setAge(Integer.parseInt(age));

            userService.saveUser(user);
            System.out.println("Регистрация прошла успешно!");

            Session session = new Session(user);
            session.run();
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            registration();
        }
    }
}