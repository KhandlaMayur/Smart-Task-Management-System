import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class AuthFlowSmokeTest {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        String email =
                "smoke_" + System.currentTimeMillis() + "@example.com";

        String password = "Test@123";

        User user = new User();

        user.setName("Smoke Test");

        user.setEmail(email);

        user.setPassword(PasswordUtil.hashPassword(password));

        user.setProfileImage("default.png");

        boolean registered =
                userDAO.registerUser(user);

        if (!registered) {

            throw new IllegalStateException(
                    "Registration insert failed"
            );
        }

        User savedUser =
                userDAO.getUserByEmail(email);

        if (savedUser == null) {

            throw new IllegalStateException(
                    "Saved user not found by email"
            );
        }

        if (!userDAO.isPasswordValid(savedUser, password)) {

            throw new IllegalStateException(
                    "Valid password was rejected"
            );
        }

        if (userDAO.isPasswordValid(savedUser, "Wrong@123")) {

            throw new IllegalStateException(
                    "Invalid password was accepted"
            );
        }

        System.out.println(
                "Auth flow smoke test passed for " + email
        );
    }
}
