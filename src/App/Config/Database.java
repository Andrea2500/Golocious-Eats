package App.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    public void Connection() throws SQLException {

        Connection conn = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
        ResultSet resultSet = conn.createStatement().executeQuery("select * from articolo");
        while(resultSet.next())
            System.out.println(resultSet.getString("nome"));

    }

}
