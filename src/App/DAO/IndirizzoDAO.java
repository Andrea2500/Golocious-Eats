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
    private ObservableList<Indirizzo> indirizzi;
    private Database db;

    /**********Metodi**********/

    /**********Costruttori**********/

    public IndirizzoDAO() {
        this.db = new Database();
        this.tabella = "Indirizzo";
    }

    /**********Metodi di funzionalità**********/

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

    public ObservableList<Indirizzo> getIndirizzi(Integer clienteid) throws SQLException {
        this.indirizzi = FXCollections.observableArrayList();
        String where = "clienteid = '"+clienteid+"' AND eliminato = false";
        ResultSet rs = this.db.queryBuilder(this.tabella,where);
        while(rs.next()){
            this.indirizzi.add(new Indirizzo(rs.getInt("indirizzoid"), rs.getString("paese"),
                    rs.getString("provincia"),rs.getString("citta"),
                    rs.getString("cap"),rs.getString("indirizzo")));
        }
        return this.indirizzi;
    }

}
