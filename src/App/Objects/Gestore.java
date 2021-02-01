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

    public Gestore() {
        this.gestoreDAO = new GestoreDAO();
    }

    /**********Getter e Setter**********/

    public ObservableList<Ristorante> getRistoranti() {
        return ristoranti;
    }

    /**********Metodi di funzionalità**********/

    public String rendiGestore(String email, Integer ristoranteId) throws SQLException {
        return this.gestoreDAO.rendiGetsore(email,ristoranteId);
    }
}
