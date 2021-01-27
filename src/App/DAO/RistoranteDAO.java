package App.DAO;

import App.Config.Database;
import App.Objects.Indirizzo;
import App.Objects.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RistoranteDAO {

    /**********Attributi**********/

    private String table;
    private Database db;
    private Ristorante ristorante;
    private Indirizzo indirizzo;

    /**********Metodi**********/

    /**********Costruttori**********/

    public RistoranteDAO() {
        this.db = new Database();
        this.table = "Ristorante";
    }

    /**********Metodi di funzionalità**********/

    public Ristorante getRistorante(Integer ristoranteId) throws SQLException {
        String where ="ristoranteid = '"+ristoranteId+"'";
        ResultSet rs = this.db.queryBuilder(this.table,where);
        if(rs.next()) {
            this.indirizzo = new Indirizzo(rs.getString("paese"),rs.getString("provincia"),
                    rs.getString("citta"), rs.getString("cap"),rs.getString("indirizzo"));
            return this.ristorante = new Ristorante(rs.getString("nome"),this.indirizzo,rs.getString("telefono"),rs.getDate("datadiapertura").toLocalDate());
        } else {
            return null;
        }
    }
}
