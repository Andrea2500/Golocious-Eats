package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Rider;
import org.postgresql.util.PSQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RiderDAO extends ClienteDAO{

    Connection conn;
    String table;

    Database db;
    ErroriDB edb = new ErroriDB();


    public RiderDAO() {
        this.db = new Database();
        this.table = "Rider";
    }

    public String diventaRiderConf(Rider rider) throws SQLException {
        try {
            this.conn = db.getConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?)";
            PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, rider.getId());
            pstmt.setString(2, rider.getPatente());
            pstmt.setString(3, rider.getVeicolo().substring(0,1).toLowerCase());
            if(pstmt.executeUpdate() > 0){
                db.closeConnection(conn);
                return "rider_aggiunto";
            }else{
                db.closeConnection(conn);
                return "aggiunta_rider_fallita";
            }
        } catch(PSQLException e) {
            db.closeConnection(conn);
            return edb.getErrorMessage(e.getMessage());
        }
    }

}
