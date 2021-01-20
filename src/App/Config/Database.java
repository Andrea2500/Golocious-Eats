package App.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
        return conn;
    }

}
