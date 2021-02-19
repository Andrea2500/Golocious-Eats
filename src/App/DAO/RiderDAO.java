package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Ordine;
import App.Objects.Rider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class RiderDAO {

    /**********Attributi**********/

    String tabella;
    Database db;
    ObservableList<Ordine> consegne;
    ErroriDB edb = new ErroriDB();

    /**********Metodi**********/

    /**********Costruttori**********/

    public RiderDAO() {
        this.db = new Database();
        this.tabella = "Rider";
    }

    /**********Metodi di funzionalità**********/

    public void consegna(Integer ordineId) throws SQLException {
        String sql = "UPDATE ordine SET consegnato = 'true' WHERE ordineid = ?";
        this.db.setConnection();
        PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql);
        pstmt.setInt(1,ordineId);
        pstmt.executeUpdate();
    }

    /**********Metodi di supporto**********/

    public String getVeicolo(Integer riderId) throws SQLException {
        String where="riderid = '"+riderId+"'";
        ResultSet rs = this.db.queryBuilder(this.tabella, where);
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

    public ObservableList<Ordine> getConsegne(int riderId, boolean attive) throws SQLException {
        try {
            this.consegne = FXCollections.observableArrayList();
            String where = "riderid =" + riderId + " AND consegnato = " + !attive;
            ResultSet rs = this.db.queryBuilder("ordine inner join carrello on carrelloid = ordineid natural join cliente", where);
            while (rs.next()) {
                this.consegne.add(new Ordine(rs.getInt("ordineid"), rs.getInt("ristoranteid"), rs.getInt("indirizzoid"), rs.getString("nome"), rs.getString("telefono"),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rs.getTimestamp("dataordine")), rs.getFloat("totale"),
                        rs.getInt("riderid"), rs.getBoolean("consegnato")));
            }
            db.closeConnection();
            return this.consegne;
        } catch(PSQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
