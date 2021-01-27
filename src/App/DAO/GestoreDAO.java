package App.DAO;

import App.Objects.Ristorante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreDAO extends ClienteDAO{

    /**********Metodi**********/

    String table = "gestore";
    ArrayList<Ristorante> ristoranti;

    //TODO Aggiungere lista ristoranti e modificare metodo getRistoranti

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public ArrayList<Ristorante> getRistoranti(Integer id) throws SQLException {
        this.db.setConnection();
        ristoranti = new ArrayList<>();
        String sql = "SELECT ristoranteid FROM "+this.table+" WHERE clienteid = '"+id+"'";
        ResultSet rs = this.db.getConnection().createStatement().executeQuery(sql);
        this.db.closeConnection();
        while(rs.next()) {
            ristoranti.add(new Ristorante(rs.getInt("ristoranteid")));
        }
        return ristoranti;
    }

}
