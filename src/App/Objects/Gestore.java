package App.Objects;

import App.DAO.GestoreDAO;
import java.sql.SQLException;
import java.util.List;

public class Gestore extends Cliente{

    /**********Attributi**********/

    List<Integer> ristoranti;
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
