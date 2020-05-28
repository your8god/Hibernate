import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import services.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Session {
    private User user;
    private UserService userService = new UserService();
    private DriverService driverService = new DriverService();

    public Session(User user) {
        this.user = user;
    }

    public void run() {
        System.out.print("Меню:\n1 - Изменить свое имя\n2 - Удалить аккаунт\n3 - Добавить водителя\n4 - Удалить водителя" +
                "\n5 - Найти водителя\n6 - Переименовать водителя\n7 - Добавить авто" +
                "\n8 - Удалить авто\n9 - Показать всех водителей\n10 - Показать все авто\n0 - Выход\nКоманда: ");

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
                    return;
                case 3:
                    addDriver();
                    break;
                case 4:
                    removeDriver();
                    break;
                case 5:
                    findDriver();
                    break;
                case 6:
                    renameDriver();
                    break;
                case 7:
                    addAuto();
                    break;
                case 8:
                    removeAuto();
                    break;
                case 9:
                    printAllDrivers();
                    break;
                case 10:
                    printAllAutos();
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

    private void renameUser() {
        System.out.print("Введите новое имя: ");
        Scanner in = new Scanner(System.in);
        String newName = in.nextLine();
        user.setName(newName);
        userService.updateUser(user);
        System.out.println("Операция прошла успешно!");
    }

    private void deleteUser() {
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

    private void addDriver() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите имя: ");
        String name = in.nextLine();
        System.out.print("Введите возраст: ");
        String age = in.nextLine();
        System.out.print("Введите стаж: ");
        String experience = in.nextLine();

        try {
            if (!age.matches("-?\\d+(\\.\\d+)?") || !experience.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова");
            if (Stream.of(age, experience, name).anyMatch(""::equals))
                throw new ExceptionDB("Заполнены не все обязательные поля!");

            Driver driver = new Driver(name, Integer.parseInt(age), Integer.parseInt(experience));
            driverService.saveDriver(driver);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            addDriver();
        }
        System.out.println("Операция прошла успешно!");
    }

    private void removeDriver() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите id водителя, которого хотите удалить: ");
        String s_id = in.nextLine();

        try {
            if (!s_id.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова");

            int id = Integer.parseInt(s_id);
            Driver driver = driverService.findDriverById(id);
            if (driver == null)
                throw new ExceptionDB("Не существует водителя с данный id! Попробуйте снова");

            driverService.closeSessionAfterFind();

            System.out.print("Вы действительно хотите удалить водителя с id = " + id + "? 1 - Да; 2 - Нет: ");
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    driverService.deleteDriver(driver);
                    break;
                case 2:
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            removeDriver();
        }
        System.out.println("Операция прошла успешно!");
    }

    private void findDriver() {
        System.out.print("Найти водителя(-ей) по ? 1 - id; 2 - имени: ");
        try {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    findDriverById();
                    break;
                case 2:
                    findDriversByName();
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            findDriver();
        }
        System.out.println("Операция прошла успешно!");
        driverService.closeSessionAfterFind();
    }

    private void findDriverById() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите id водителя: ");
        String s_id = in.nextLine();

        try {
            if (!s_id.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова.");

            int id = Integer.parseInt(s_id);
            Driver driver = driverService.findDriverById(id);
            if (driver == null)
                throw new ExceptionDB("Водитель с данным id не найден");

            ObjectMapper mapper = new ObjectMapper();
            StringWriter w = new StringWriter();
            mapper.writeValue(w, driver);
            String res = w.toString();

            System.out.println(res);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            findDriverById();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findDriversByName() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите имя водителя(ей): ");
        String name = in.nextLine();

        try {
            List<Driver> drivers= driverService.findDriverByName(name);
            if (drivers.isEmpty())
                throw new ExceptionDB("Водитель(ли) с данным именем не найден(ы)");

            ObjectMapper mapper = new ObjectMapper();
            StringWriter w = new StringWriter();
            mapper.writeValue(w, drivers);
            String res = w.toString();

            System.out.println(res);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            findDriversByName();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void renameDriver() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите id водителя, которого хотите переименовать: ");
        String s_id = in.nextLine();

        try {
            if (!s_id.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова");

            int id = Integer.parseInt(s_id);
            Driver driver = driverService.findDriverById(id);
            if (driver == null)
                throw new ExceptionDB("Не существует водителя с данный id! Попробуйте снова");

            driverService.closeSessionAfterFind();

            System.out.print("Введите новое имя: ");
            String newName = in.nextLine();

            System.out.print("Вы действительно хотите переименовать водителя с " + driver.getName() + " на " +
                    newName + "? 1 - Да; 2 - Нет: ");
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    driver.setName(newName);
                    driverService.updateDriver(driver);
                    break;
                case 2:
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            renameDriver();
        }
        System.out.println("Операция прошла успешно!");
    }

    private void addAuto() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите модель: ");
        String model = in.nextLine();
        System.out.print("Введите цвет: ");
        String color = in.nextLine();
        System.out.print("Введите id водителя, которому будет принадлежать авто: ");
        String driverId = in.nextLine();

        try {
            if (!driverId.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова.");

            if (Stream.of(model, color, driverId).anyMatch(""::equals))
                throw new ExceptionDB("Заполнены не все обязательные поля!");

            int id = Integer.parseInt(driverId);
            Driver driver = driverService.findDriverById(id);
            if (driver == null)
                throw new ExceptionDB("Водителя с таким id не существует! Попробуйте снова.");
            Auto auto = new Auto(model, color);
            auto.setDriver(driver);
            driver.addAuto(auto);
            driverService.closeSessionAfterFind();
            driverService.updateDriver(driver);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            addAuto();
        }
        System.out.println("Операция прошла успешно!");
    }

    private void removeAuto() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите id авто, которое хотите удалить: ");
        String s_id = in.nextLine();

        try {
            if (!s_id.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверный ввод! Попробуйте снова");

            int id = Integer.parseInt(s_id);
            Auto auto = driverService.findAutoById(id);
            if (auto == null)
                throw new ExceptionDB("Не существует водителя с данный id! Попробуйте снова");

            System.out.print("Вы действительно хотите удалить авто с id = " + id + "? 1 - Да; 2 - Нет: ");
            String command = in.nextLine();
            if (!command.matches("-?\\d+(\\.\\d+)?"))
                throw new ExceptionDB("Неверная комманда! Попробуйте снова.");

            switch (Integer.parseInt(command)) {
                case 1:
                    Driver driver = auto.getDriver();
                    driver.removeAuto(auto);
                    driverService.closeSessionAfterFind();
                    driverService.updateDriver(driver);
                    break;
                case 2:
                    return;
                default:
                    throw new ExceptionDB("Неверная комманда! Попробуйте снова.");
            }
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            removeDriver();
        }
        System.out.println("Операция прошла успешно!");
    }

    private void printAllDrivers() {
        try {
            List<Driver> drivers = driverService.findAllDriver();
            if (drivers.isEmpty())
                throw new ExceptionDB("Водители не найдены");

            ObjectMapper mapper = new ObjectMapper();
            StringWriter w = new StringWriter();
            mapper.writeValue(w, drivers);
            String res = w.toString();

            System.out.println(res);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printAllAutos() {
        try {
            List<Auto> autos = driverService.findAllAuto();
            if (autos.isEmpty())
                throw new ExceptionDB("Авто не найдены");

            ObjectMapper mapper = new ObjectMapper();
            StringWriter w = new StringWriter();
            mapper.writeValue(w, autos);
            String res = w.toString();

            System.out.println(res);
        }
        catch (ExceptionDB e) {
            System.out.println(e.getMessage());
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
