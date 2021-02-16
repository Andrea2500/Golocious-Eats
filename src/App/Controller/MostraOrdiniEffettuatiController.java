package App.Controller;

import App.Objects.Cliente;
import App.Objects.Ordine;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MostraOrdiniEffettuatiController {

    /**********Metodi**********/

    /**********Metodi di funzionalità**********/

    public ObservableList<Ordine> getOrdini() throws SQLException {
        return Cliente.getInstance().getOrdiniDB();
    }

}
