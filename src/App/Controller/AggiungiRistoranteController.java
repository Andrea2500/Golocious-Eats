package App.Controller;

import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class AggiungiRistoranteController {

    /**********Attributi**********/

    Ristorante ristorante;
    Gestore gestore;

    /**********Metodi**********/

    /**********Costruttori**********/

    public AggiungiRistoranteController() {
        this.ristorante = new Ristorante();
        this.gestore = new Gestore();
    }

    /**********Metodi di funzionalità**********/

    public String aggiungiRistorante(Ristorante ristorante) throws SQLException {
        int idRistoranteAggiunto = 0;
        try {
            idRistoranteAggiunto = this.ristorante.aggiungiRistorante(ristorante);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        if(idRistoranteAggiunto != 0) {
            return gestore.rendiGestore(Cliente.getInstance().getEmail(), idRistoranteAggiunto);
        } else {
            return "ristorante_non_aggiunto";
        }
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
