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

    public Gestore(Cliente cliente) throws SQLException {
        this.gestoreDAO = new GestoreDAO();
        this.ristoranti = this.getRistorantiDB(cliente);
    }

    public Gestore() {
        this.gestoreDAO = new GestoreDAO();
    }

    /**********Getter e Setter**********/

    public ObservableList<Ristorante> getRistoranti() {
        return ristoranti;
    }

    /**********Metodi di funzionalità**********/

    public String apriRistorante(Ristorante ristorante) throws Exception {
        int idRistoranteAggiunto = creaRistorante(ristorante);
        if(idRistoranteAggiunto != 0) {
            String messaggio = rendiGestore(Cliente.getInstance().getEmail(), idRistoranteAggiunto);
            if(messaggio.equals("gestore_aggiunto")) {
                return "ristorante_aperto";
            } else {
                return "ristorante_aggiunto_gestore_no";
            }
        } else {
            return "ristorante_non_aggiunto";
        }
    }

    public int creaRistorante(Ristorante ristorante) throws Exception {
        try{
            return ristorante.setRistoranteDB();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String rendiGestore(String email, Integer ristoranteId) throws SQLException {
        return this.gestoreDAO.rendiGetsore(email,ristoranteId);
    }

    public ObservableList<Ristorante> getRistorantiDB(Cliente cliente) throws SQLException {
        this.ristoranti = new Ristorante().getRistorantiDB(cliente.getClienteId());
        return this.ristoranti;
    }
}
