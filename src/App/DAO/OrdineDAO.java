package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.text.SimpleDateFormat;

public class OrdineDAO {

    /**********Attributi**********/

    private String table;
    private String fkTable;
    private ObservableList<Ordine> ordini;
    private Database db;
    private ErroriDB edb;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdineDAO() {
        this.db = new Database();
        this.table = "Ordine";
        this.fkTable = "Carrello";
        this.edb = new ErroriDB();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini(Integer id) throws SQLException {
        this.ordini = FXCollections.observableArrayList();
        this.db.setConnection();
        String sql = "SELECT * FROM "+this.table+" o inner join "+this.fkTable+" c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            this.ordini.add(new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"),
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("dataordine")), rs.getFloat("totale"),
                    rs.getInt("riderid"),rs.getBoolean("consegnato")));
        }
        db.closeConnection();
        return this.ordini;
    }

    public Ordine creaOrdine(int carrelloId) throws Exception {
        try {
            this.db.setConnection();
            String sql = "insert into "+this.table+" values (?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, carrelloId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                return new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("dataordine")), rs.getFloat("totale"),
                        rs.getInt("riderid"),rs.getBoolean("consegnato"));
            }
            this.db.closeConnection();
            return null;
        } catch(PSQLException e) {
            this.db.closeConnection();
            throw new Exception(this.edb.getMessaggioErrore(e.getMessage()));
        }
    }

}