package App.DAO;

import App.Config.Database;
import App.Objects.Indirizzo;
import App.Objects.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RistoranteDAO {

    /**********Attributi**********/

    private String table;
    private Database db;
    private ArrayList<Ristorante> ristoranti;
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

    public ArrayList<Ristorante> getRistoranti(Integer clienteId) throws SQLException {
        this.db.setConnection();
        ristoranti = new ArrayList<>();
        ResultSet rs = db.queryBuilder(this.table+" NATURAL JOIN Gestore", "clienteid = '"+clienteId+"'");
        this.db.closeConnection();
        while(rs.next()) {
            ristoranti.add(new Ristorante(rs.getInt("ristoranteid"), rs.getString("nome"),this.indirizzo,rs.getString("telefono"),rs.getDate("datadiapertura").toLocalDate()));
        }
        return ristoranti;
    }

}
