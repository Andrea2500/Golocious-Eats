package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Ordine;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.text.SimpleDateFormat;

public class OrdineDAO {

    /**********Attributi**********/

    private String tabella;
    private Database db;
    private ErroriDB edb;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdineDAO() {
        this.db = new Database();
        this.tabella = "Ordine";
        this.edb = new ErroriDB();
    }

    /**********Metodi di funzionalità**********/

    public Ordine creaOrdine(int carrelloId) throws Exception {
        try {
            this.db.setConnection();
            String sql = "insert into "+this.tabella +" values (?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrelloId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            this.db.closeConnection();
            if(rs.next()) {
                return new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("dataordine")), rs.getFloat("totale"),
                        rs.getInt("riderid"), rs.getBoolean("consegnato"));
            }
            return null;
        } catch(PSQLException e) {
            this.db.closeConnection();
            throw new Exception(this.edb.getMessaggioErrore(e.getMessage()));
        }
    }

}