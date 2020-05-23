import models.User;
import services.UserService;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();
        User user = new User();
        userService.deleteUser(userService.findUser(1));
    }
}
