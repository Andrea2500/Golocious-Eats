package App.Config;

import java.sql.*;

public class Database {

    /**********Attributi**********/

    Connection connection;

    /**********Metodi**********/

    /**********Getter e setter**********/

    public Connection getConnection(){
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public void setConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
    }

    /**********Metodi di supporto**********/

    public ResultSet queryBuilder(String from, String where) {
        ResultSet rs;
        try {
            setConnection();
            String sql = "SELECT * FROM "+from+" WHERE "+where;
            rs = this.connection.createStatement().executeQuery(sql);
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            rs = null;
        }
        return rs;
    }

}