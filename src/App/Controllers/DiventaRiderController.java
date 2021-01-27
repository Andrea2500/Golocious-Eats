package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Rider;
import java.sql.SQLException;

public class DiventaRiderController {

    Rider rider;


    public DiventaRiderController(String patente, String veicolo) throws SQLException {
        this.rider = new Rider(Cliente.getInstance().getId(), patente, veicolo);
    }

    public String diventaRider() throws SQLException {
        return this.rider.diventaRider(this.rider);
    }

}