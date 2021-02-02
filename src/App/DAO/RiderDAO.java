package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Rider;
import org.postgresql.util.PSQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RiderDAO {

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
            pstmt.setInt(1, rider.getClienteId());
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
            return edb.getMessaggioErrore(e.getMessage());
        }
    }

    /**********Metodi di supporto**********/

    public String getVeicolo(Integer riderId) throws SQLException {
        String where="riderid = '"+riderId+"'";
        ResultSet rs = this.db.queryBuilder(this.table, where);
        if(rs.next()) {
            switch (rs.getString("veicolo")){
                case "a" -> { return "automobile"; }
                case "b" -> { return "bicicletta"; }
                case "m" -> { return "motoveicolo"; }
                default -> { return "veicolo_rider_errore"; }
            }
        } else {
            return "veicolo_rider_errore";
        }
    }

}
