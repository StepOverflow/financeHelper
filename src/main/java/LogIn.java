import java.sql.Connection;

class LogIn {
    public static void doLogIn(String email, String password, Connection connection) {
        if (Authorization.doAuthorization(email, password, connection)) {
            System.out.println("Hello " + email + " !");
        } else {
            System.out.println("Access denied!");
        }
    }
}
