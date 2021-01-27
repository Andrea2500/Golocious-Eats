package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Rider;
import org.postgresql.util.PSQLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RiderDAO extends ClienteDAO{

    /**********Attributi**********/

    String table;
    Database db;
    ErroriDB edb = new ErroriDB();

    /**********Metodi**********/

    /**********Costruttori**********/

    public RiderDAO() {
        this.db = new Database();
        this.table = "Rider";
    }

    /**********Metodi di funzionalità**********/

    public String diventaRiderConf(Rider rider) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into " + this.table + " values (?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, rider.getId());
            pstmt.setString(2, rider.getPatente());
            pstmt.setString(3, rider.getVeicolo().substring(0,1).toLowerCase());
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "rider_aggiunto";
            }else{
                this.db.closeConnection();
                return "aggiunta_rider_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return edb.getErrorMessage(e.getMessage());
        }
    }

}
