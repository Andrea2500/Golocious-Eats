package App.DAO;

import App.Config.Database;
import App.Objects.Indirizzo;
import App.Objects.Ristorante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RistoranteDAO {

    /**********Attributi**********/

    private String table;
    private Database db;
    private ObservableList<Ristorante> ristoranti;
    private Indirizzo indirizzo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public RistoranteDAO() {
        this.db = new Database();
        this.table = "Ristorante";
    }

    /**********Metodi di supporto**********/

    public Ristorante getRistorante(Integer ristoranteId) throws SQLException {
        String where ="ristoranteid = '"+ristoranteId+"'";
        ResultSet rs = this.db.queryBuilder(this.table,where);
        if(rs.next()) {
            this.indirizzo = new Indirizzo(rs.getString("paese"),rs.getString("provincia"),
                    rs.getString("citta"), rs.getString("cap"),rs.getString("indirizzo"));
            return new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),this.indirizzo,rs.getString("telefono"),rs.getDate("datadiapertura").toLocalDate());
        } else {
            return null;
        }
    }

    public ObservableList<Ristorante> getRistoranti(Integer clienteId) throws SQLException {
        this.db.setConnection();
        ristoranti = FXCollections.observableArrayList();
        ResultSet rs = db.queryBuilder(this.table+" NATURAL JOIN Gestore", "clienteid = "+clienteId);
        this.db.closeConnection();
        while(rs.next()) {
            ristoranti.add(new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),this.indirizzo,rs.getString("telefono"),rs.getDate("datadiapertura").toLocalDate()));
        }
        return ristoranti;
    }

    public String eliminaDaMenu(Integer ristoranteId, int articoloId) throws SQLException {
        try{
            String sql = "DELETE FROM menu WHERE ristoranteid = ? AND articoloid = ?";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,ristoranteId);
            pstmt.setInt(2,articoloId);
            if(pstmt.executeUpdate() > 0){
                this.db.closeConnection();
                return "articolo_eliminati";
            }else{
                this.db.closeConnection();
                return "articolo_fallito";
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            return "articolo_fallito";
        }

    }
}
