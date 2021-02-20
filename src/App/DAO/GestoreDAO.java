package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Ristorante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestoreDAO extends ClienteDAO{

    /**********Metodi**********/

    String tabella;
    Database db;
    ErroriDB edb;
    ObservableList<Ristorante> ristoranti;
    RistoranteDAO ristoranteDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestoreDAO() {
        this.tabella = "gestore";
        this.db = new Database();
        this.edb = new ErroriDB();
        this.ristoranti = FXCollections.observableArrayList();
        this.ristoranteDAO = new RistoranteDAO();
    }

    /**********Metodi di funzionalità**********/

    public String rendiGestore(String email, Integer ristoranteId) throws SQLException {
        try {
            this.db.setConnection();
            String sql = "SELECT * FROM cliente where email = ?";
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                sql = "INSERT INTO "+this.tabella +" VALUES (?, ?)";
                pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, rs.getInt("clienteid"));
                pstmt.setInt(2, ristoranteId);
                if(pstmt.executeUpdate() > 0) {
                    this.db.closeConnection();
                    return "gestore_aggiunto";
                } else {
                    this.db.closeConnection();
                    return "errore_inserimento_gestore";
                }
            } else {
                this.db.closeConnection();
                return "utente_non_trovato";
            }
        } catch (PSQLException e) {
            this.db.closeConnection();
            return edb.getMessaggioErrore(e.getMessage());
        }
    }

    public ObservableList<Ristorante> getRistoranti(Integer clienteId) throws SQLException {
        this.db.setConnection();
        ResultSet rs = db.queryBuilder("Ristorante NATURAL JOIN Gestore", "clienteid = "+clienteId);
        this.db.closeConnection();
        while(rs.next()) {
            ristoranti.add(new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),
                    rs.getString("indirizzo"),rs.getString("telefono"),
                    rs.getDate("datadiapertura").toLocalDate(), this.ristoranteDAO.getArticoli(rs.getInt("ristoranteid"))));
        }
        return ristoranti;
    }

}
