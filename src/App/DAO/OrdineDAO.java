package App.DAO;

import App.Config.Database;
import java.sql.*;

public class OrdineDAO {

    Connection conn;
    String table;
    String fkTable;


    Database db;

    public OrdineDAO() {
        this.db = new Database();
        this.table = "Ordine";
        this.fkTable = "Carrello";
    }

    public ResultSet getOrders(Integer id) throws SQLException {
        this.conn = this.db.getConnection();
        String sql = "SELECT * FROM "+this.table+" o inner join "+this.fkTable+" c on o.ordineid = c.carrelloid where clienteid = ?";
        PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        db.closeConnection(conn);
        return rs;
    }

}
