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

    public Gestore() throws SQLException {
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = this.getRistorantiDB();
    }

    /**********Getter e Setter**********/

    public ObservableList<Ristorante> getRistoranti() {
        return ristoranti;
    }

    /**********Metodi di funzionalità**********/

    public String apriRistorante(Ristorante ristorante) throws Exception {
        int idRistoranteAggiunto;
        try {
            idRistoranteAggiunto = creaRistorante(ristorante);
        } catch (Exception e) {
            return e.getMessage();
        }
        if(idRistoranteAggiunto != 0) {
            String messaggio = rendiGestore(Cliente.getInstance().getEmail(), idRistoranteAggiunto);
            if(messaggio.equals("gestore_aggiunto")) {
                this.ristoranti.add(new Ristorante(idRistoranteAggiunto));
                return "ristorante_aperto";
            } else {
                return "ristorante_aperto_gestore_no";
            }
        } else {
            return "ristorante_non_aperto";
        }
    }

    public int creaRistorante(Ristorante ristorante) throws Exception {
        try{
            return ristorante.setRistoranteDB();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String rendiGestore(String email, Integer ristoranteId) throws SQLException {
        return this.gestoreDAO.rendiGestore(email,ristoranteId);
    }

    public ObservableList<Ristorante> getRistorantiDB() throws SQLException {
        return this.gestoreDAO.getRistoranti(Cliente.getInstance().getClienteId());
    }

}
