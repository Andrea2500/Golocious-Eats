package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;

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
        int idRistoranteAggiunto = this.ristorante.aggiungiRistorante(ristorante);
        if(idRistoranteAggiunto != 0){
            String messaggio = gestore.rendiGestore(Cliente.getInstance().getEmail(),idRistoranteAggiunto);
            return messaggio;
        }
        return "errore";
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/


}
