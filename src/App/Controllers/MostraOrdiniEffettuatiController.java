package App.Controllers;

import App.Objects.Ordine;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MostraOrdiniEffettuatiController {

    Ordine ordine;


    public MostraOrdiniEffettuatiController() {
        this.ordine = new Ordine();
    }

    public ObservableList<Ordine> getOrdini() throws SQLException {
        return this.ordine.getOrdini();
    }

}
