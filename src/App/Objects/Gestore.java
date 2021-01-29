package App.Objects;

import App.DAO.GestoreDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class Gestore extends Cliente{

    /**********Attributi**********/

    ArrayList<Ristorante> ristoranti;
    GestoreDAO gestoreDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Gestore(Integer id) throws SQLException {
        this.clienteId = id;
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = new Ristorante().getRistorantiDB(this.clienteId);
    }

}
