package App.Controllers;

import App.Objects.Ordine;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MostraOrdiniEffettuatiController {

    /**********Attributi**********/

    Ordine ordine;

    /**********Metodi**********/

    /**********Costruttori**********/

    public MostraOrdiniEffettuatiController() {
        this.ordine = new Ordine();
    }

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini() throws SQLException {
        return this.ordine.getOrdini();
    }

}
