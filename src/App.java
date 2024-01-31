import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    static String ip = "localhost";
    static String bdd = "playerssirius";
    static String user = "williams";
    static String password = "Ravus77!";
    static Connection connectBDD() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/"+bdd+"?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",user,password);
        return conn;
    }
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
        new Fenetre();
    }
}
