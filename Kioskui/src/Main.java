public class Main {

    public static void main(String[] args) {
        //Test Connection
        MyJDBC.getConnection();

        //can omit it just closes your conn
        MyJDBC.closeConnection();
    }
}