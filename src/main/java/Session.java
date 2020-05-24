import models.User;
import services.UserService;
import java.util.Scanner;


public class Session {
    User user;
    UserService userService = new UserService();

    public Session(User user) {
        this.user = user;
    }

    public void run() {
        System.out.print("Меню:\n1 - Изменить свое имя;\n2 - Удалить аккаунт;\n\n\n0 - Выход;\nКоманда: ");

        try {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    renameUser();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 0:
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
        }
        run();
    }

    void renameUser() {
        System.out.print("Введите новое имя: ");
        Scanner in = new Scanner(System.in);
        String newName = in.nextLine();
        user.setName(newName);
        userService.updateUser(user);
        System.out.println("Операция прошла успешно!");
    }

    void deleteUser() {
        System.out.print("Вы действительно хотите удалить свой аккаунт? 1 - Да; 2 - Нет: ");
        try {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    userService.deleteUser(user);
                    break;
                case 2:
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            deleteUser();
        }
        System.out.println("Операция прошла успешно!");
    }

}
