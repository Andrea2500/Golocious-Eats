package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class OrdineDAO {

    Connection conn;
    String table;
    String fkTable;


    Database db;
    ErroriDB edb = new ErroriDB();

    public OrdineDAO() {
        this.db = new Database();
        this.table = "Ordine";
        this.fkTable = "Carrello";
    }

    public ObservableList<Ordine> getOrders(Integer id) throws SQLException {
        ObservableList<Ordine> data = FXCollections.observableArrayList();
        this.conn = this.db.getConnection();
        String sql = "SELECT * FROM "+this.table+" o inner join "+this.fkTable+" c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            data.add(new Ordine(rs.getInt("ordineid"),((Integer) rs.getInt("ristoranteid")).toString(),rs.getDate("dataordine").toString(), rs.getString("totale"), ((Integer) rs.getInt("riderid")).toString(),rs.getBoolean("consegnato")));
        }
        db.closeConnection(conn);
        return data;
    }

}
