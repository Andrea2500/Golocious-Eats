package App.Objects;

import App.DAO.GestoreDAO;
import java.sql.SQLException;
import java.util.ArrayList;

public class Gestore extends Cliente{

    /**********Attributi**********/

    ArrayList<Ristorante> ristoranti;
    GestoreDAO gestoreDAO;
    Integer id;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Gestore(Integer id) throws SQLException {
        this.id = id;
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = gestoreDAO.getRistoranti(this.id);
    }
}
