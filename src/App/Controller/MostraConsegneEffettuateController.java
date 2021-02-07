package App.Controller;

import App.Objects.Cliente;
import App.Objects.Ordine;
import App.Objects.Rider;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class MostraConsegneEffettuateController {

    /**********Attributi**********/

    private Rider rider;

    /**********Metodi**********/

    /**********Costruttori**********/

    public MostraConsegneEffettuateController() throws SQLException {
        this.rider = new Rider(Cliente.getInstance().getClienteId(), true, false);
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getConsegne(){
        return this.rider.getConsegne();
    }

}
