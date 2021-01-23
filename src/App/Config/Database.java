package App.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
    }

    public void closeConnection(Connection conn) throws SQLException {
        if(conn != null) {
            conn.close();
        }
    }

}