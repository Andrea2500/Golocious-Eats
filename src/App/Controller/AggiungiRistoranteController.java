package App.Controller;

import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;

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

    public String aggiungiRistorante(Ristorante ristorante) throws Exception {
        int idRistoranteAggiunto = 0;
        try{
           idRistoranteAggiunto = this.ristorante.aggiungiRistorante(ristorante);
        }catch (Exception e){
            return e.getMessage();
        }
        System.out.println(idRistoranteAggiunto);
        if(idRistoranteAggiunto != 0) {
            return gestore.rendiGestore(Cliente.getInstance().getEmail(), idRistoranteAggiunto);
        } else {
            return "ristorante_non_aggiunto";
        }
    }

    /**********Metodi di supporto**********/

    /**********Metodi di ripristino e di errori**********/

}
