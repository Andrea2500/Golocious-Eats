package App.Objects;

import App.DAO.GestoreDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class Gestore extends Cliente{

    /**********Attributi**********/

    ObservableList<Ristorante> ristoranti;
    GestoreDAO gestoreDAO;

    /**********Metodi**********/

    /**********Costruttori**********/

    public Gestore(int clienteId) throws SQLException {
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = new Ristorante().getRistorantiDB(clienteId);
    }

    /**********Getter e Setter**********/

    public ObservableList<Ristorante> getRistoranti() {
        return ristoranti;
    }
}
