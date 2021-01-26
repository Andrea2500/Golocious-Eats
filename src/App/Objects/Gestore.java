package App.Objects;

import App.DAO.GestoreDAO;
import java.sql.SQLException;
import java.util.List;

public class Gestore extends Cliente{

    List<Integer> ristoranti;
    GestoreDAO gestoreDAO;
    Integer id;

    public Gestore(Integer id) throws SQLException {
        this.id = id;
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = gestoreDAO.getRistoranti(this.id);
        System.out.println(ristoranti.toString());
    }
}
