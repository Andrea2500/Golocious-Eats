package App.DAO;

import App.Config.Database;
import App.Config.ErroriDB;
import App.Objects.Articolo;
import javafx.collections.ObservableList;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class ArticoloDAO {

    /**********Attribbuti**********/

    String tabella;
    Database db;
    ObservableList<Articolo> articoli;
    ErroriDB edb;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ArticoloDAO() {
        this.tabella = "Articolo";
        this.db = new Database();
        this.edb = new ErroriDB();
    }

    /**********Metodi di funzionalità**********/

    public int setArticolo(Articolo articolo) throws Exception {
        try {
            String sql = "INSERT INTO "+this.tabella +" VALUES (?,?,?,?)";
            this.db.setConnection();
            PreparedStatement pstmt = this.db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, articolo.getNome());
            pstmt.setFloat(2, Float.parseFloat(articolo.getPrezzo()));
            pstmt.setString(3, articolo.getCategoria());
            pstmt.setString(4, articolo.getIngredienti());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            this.db.closeConnection();
            if(rs.next()) {
                return rs.getInt("articoloid");
            } else {
                return 0;
            }
        } catch(PSQLException e) {
            this.db.closeConnection();
            throw new Exception(this.edb.getMessaggioErrore(e.getMessage()));
        }
    }

    public Articolo getArticolo(int articoloId) throws SQLException {
        ResultSet rs = this.db.queryBuilder("articolo","articoloid = "+articoloId);
        if(rs.next()){
            return new Articolo(rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("categoria"),
                    rs.getString("ingredienti"), rs.getInt("articoloid"), true);
        }
        return null;
    }
}