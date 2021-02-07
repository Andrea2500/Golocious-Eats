package App.DAO;

import App.Config.Database;
import App.Objects.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class OrdineDAO {

    /**********Attributi**********/

    private String table;
    private String fkTable;
    private ObservableList<Ordine> ordini;
    private Database db;

    /**********Metodi**********/

    /**********Costruttori**********/

    public OrdineDAO() {
        this.db = new Database();
        this.table = "Ordine";
        this.fkTable = "Carrello";
    }

    /**********Metodi di supporto**********/

    public ObservableList<Ordine> getOrdini(Integer id) throws SQLException {
        this.ordini = FXCollections.observableArrayList();
        this.db.setConnection();
        String sql = "SELECT * FROM "+this.table+" o inner join "+this.fkTable+" c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            this.ordini.add(new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"),
                    rs.getTimestamp("dataordine").toString(), rs.getFloat("totale"),
                    rs.getInt("riderid"),rs.getBoolean("consegnato")));
        }
        db.closeConnection();
        return this.ordini;
    }

}
