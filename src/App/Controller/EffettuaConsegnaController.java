package App.Controller;

import App.Objects.Cliente;
import App.Objects.Ordine;
import App.Objects.Rider;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EffettuaConsegnaController {

    private Rider rider;

    public EffettuaConsegnaController() throws SQLException {
        this.rider = new Rider(Cliente.getInstance().getClienteId(), true, true);
    }

    public ObservableList<Ordine> getConsegneAttive() {
        return this.rider.getConsegne();
    }
}
