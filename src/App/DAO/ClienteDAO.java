package App.DAO;

import App.Config.Database;
import org.postgresql.util.PSQLException;
import java.sql.*;
import java.time.LocalDate;

public class ClienteDAO {

    Connection conn;
    String table;

    Database db;


    public ClienteDAO() throws SQLException {
        this.db = new Database();
        this.conn = db.getConnection();
        this.table = "Cliente";
    }

    public ResultSet LoginConf(String email, String password) throws SQLException {
        String sql = "select * from "+this.table+" where email = ? and password = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        return pstmt.executeQuery();
    }

    public boolean RegisterConf(String nome, String cognome, String email, String password, String telefono, LocalDate dataNascita) throws SQLException {
        try {
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, telefono);
            pstmt.setDate(6, Date.valueOf(dataNascita));
            return pstmt.executeUpdate() > 0;
        } catch (PSQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}