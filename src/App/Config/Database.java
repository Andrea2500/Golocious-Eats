package App.Config;

import java.sql.*;

public class Database {

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
    }

    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public ResultSet queryBuilder(String from, String where) {
        ResultSet rs;
        try {
            Connection conn = getConnection();
            String sql = "SELECT FROM "+from+" WHERE "+where;
            rs = conn.createStatement().executeQuery(sql);
            closeConnection(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rs = null;
        }
        return rs;
    }

}