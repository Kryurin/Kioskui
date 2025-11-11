import java.sql.*;

public class MyJDBC {
    //DB Things
    public static final String URL = "jdbc:mysql://localhost:3306/kioskuidb";
    public static final String USER = "root";
    public static final String PASSWORD = "11681128";

    public static Connection connection = null;

    //private the constructor to prevent instances
    private MyJDBC() {};

    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("DB is Successful");
            }
        } catch (SQLException e) {
            System.out.println("DB is not connected");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver is not found");
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(){
        try{
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("DB is succesfully closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
