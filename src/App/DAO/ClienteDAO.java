package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import org.postgresql.util.PSQLException;
import java.sql.*;
import java.time.LocalDate;

public class ClienteDAO {

    Connection conn;
    String table;

    Database db;
    ErroriDB edb = new ErroriDB();


    public ClienteDAO() {
        this.db = new Database();
        this.table = "Cliente";
    }

    public ResultSet LoginConf(String email, String password) throws SQLException {
        this.conn = db.getConnection();
        String sql = "select * from "+this.table+" where email = ? and password = ?";
        PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        db.closeConnection(conn);
        return rs;
    }

    public boolean RegisterConf(String nome, String cognome, String email, String password, String telefono, LocalDate dataNascita) throws SQLException {
        try {
            this.conn = db.getConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nome);
            pstmt.setString(2, cognome);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, telefono);
            pstmt.setDate(6, Date.valueOf(dataNascita));
            if(pstmt.executeUpdate() > 0){
                db.closeConnection(conn);
                return true;
            }else{
                new ErroriDB().getErrorMessage("signup_fallito");
                db.closeConnection(conn);
                return false;
            }
        } catch(PSQLException e) {
            edb.getErrorMessage(e.getMessage());
            db.closeConnection(conn);
            return false;
        }
    }

    public String getRole(Integer id) throws SQLException {
        ResultSet role;
        String where = "clienteid = '"+id+"'";
        role = db.queryBuilder("gestore",where);
        if(role.next())
            return "gestore";
        where = "riderid = '"+id+"'";
        role = db.queryBuilder("rider",where);
        if(role.next())
            return "rider";

        return "cliente";
    }


}