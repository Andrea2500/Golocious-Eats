package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Cliente;
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

    public String RegisterConf(Cliente cliente, String password) throws SQLException {
        try {
            this.conn = db.getConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCognome());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, password);
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setDate(6, Date.valueOf(cliente.getDataNascita()));
            if(pstmt.executeUpdate() > 0){
                db.closeConnection(conn);
                return "cliente_registrato";
            }else{
                db.closeConnection(conn);
                return "signup_fallito";
            }
        } catch(PSQLException e) {
            db.closeConnection(conn);
            return edb.getErrorMessage(e.getMessage());
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

    public String updateIndirizzoAttivo(Integer id) throws SQLException {
        try{
            String sql = "UPDATE "+this.table+" SET indirizzoattivo = '"+id+"' WHERE clienteid = "+ Cliente.getInstance().getId();
            this.conn = this.db.getConnection();
            if(this.conn.createStatement().executeUpdate(sql)==1){
                db.closeConnection(this.conn);
                return "indirizzo_aggiornato";
            }else{
                db.closeConnection(conn);
                return "errore_aggiornamento_indirizzo";
            }
        }catch(PSQLException e){
            db.closeConnection(conn);
            return edb.getErrorMessage(e.getMessage());
        }
    }


}