package App.Config;

import java.sql.*;

public class Database {

    /**********Attributi**********/

    private Connection connection;

    /**********Metodi**********/

    /**********Getter e setter**********/

    public Connection getConnection(){
        return this.connection;
    }

    public void setConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOBD", "postgres", "root");
    }

    /**********Metodi di supporto**********/

    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    /**********Metodi di supporto**********/

    public ResultSet queryBuilder(String from, String where) throws SQLException {
        ResultSet rs = null;
        try {
            setConnection();
            String sql = "SELECT * FROM "+from+" WHERE "+where;
            rs = this.connection.createStatement().executeQuery(sql);
            closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            closeConnection();
        }
        return rs;
    }

}