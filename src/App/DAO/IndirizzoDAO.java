package App.DAO;

import App.Config.Database;
import App.Objects.Indirizzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IndirizzoDAO {

    /**********Attributi**********/

    private String tabella;
    private Database db;

    /**********Metodi**********/

    /**********Costruttori**********/

    public IndirizzoDAO() {
        this.db = new Database();
        this.tabella = "Indirizzo";
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiIndirizzo(Indirizzo indirizzo) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "insert into "+this.tabella+" values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, indirizzo.getCliente().getClienteId());
            pstmt.setString(2, indirizzo.getPaese());
            pstmt.setString(3, indirizzo.getProvincia());
            pstmt.setString(4, indirizzo.getCitta());
            pstmt.setString(5, indirizzo.getCap());
            pstmt.setString(6, indirizzo.getIndirizzoCivico());
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "indirizzo_aggiunto";
            }else{
                this.db.closeConnection();
                return "aggiunta_indirizzo_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "aggiunta_indirizzo_fallita";
        }
    }

    public String eliminaIndirizzo(Integer indirizzoid) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "UPDATE "+this.tabella +" SET eliminato = true WHERE indirizzoid = ?";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, indirizzoid);
            if(pstmt.executeUpdate() > 0) {
                this.db.closeConnection();
                return "indirizzo_eliminato";
            } else {
                this.db.closeConnection();
                return "eliminazione_indirizzo_fallita";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "eliminazione_indirizzo_fallita";
        }
    }

    public Indirizzo getIndirizzo(int indirizzoId) throws SQLException {
        String where = "indirizzoid = '"+indirizzoId+"'";
        ResultSet rs = this.db.queryBuilder(this.tabella,where);
        if(rs.next()) {
            return new Indirizzo(rs.getInt("indirizzoid"), rs.getString("paese"), rs.getString("provincia"),
                    rs.getString("citta"), rs.getString("cap"), rs.getString("indirizzo"));
        } else {
            return null;
        }
    }

}
