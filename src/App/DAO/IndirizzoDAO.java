package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Cliente;
import org.postgresql.util.PSQLException;
import java.sql.*;

public class IndirizzoDAO {

    Connection conn;
    String table;

    Database db;
    ErroriDB edb = new ErroriDB();


    public IndirizzoDAO() {
        this.db = new Database();
        this.table = "Indirizzo";
    }

    public String aggiungiIndirizzoConf(String paese, String provincia, String citta, String cap, String indirizzo, Cliente cliente) throws SQLException {
        try {
            this.conn = db.getConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getId());
            pstmt.setString(2, paese);
            pstmt.setString(3, provincia);
            pstmt.setString(4, citta);
            pstmt.setString(5, cap);
            pstmt.setString(6, indirizzo);
            if(pstmt.executeUpdate() > 0){
                db.closeConnection(conn);
                return "indirizzo_aggiunto";
            }else{
                db.closeConnection(conn);
                return "aggiunta_indirizzo_fallita";
            }
        } catch(PSQLException e) {
            System.out.println(e.getMessage());
            db.closeConnection(conn);
            return "aggiunta_indirizzo_fallita";
        }
    }

    public ResultSet getIndirizziDelCliente(Integer id) {
        String where = "clienteid = '"+id+"'";
        return this.db.queryBuilder(this.table,where);
    }


}
