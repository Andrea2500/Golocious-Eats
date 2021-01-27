package App.Controllers;

import App.Objects.Cliente;
import App.Objects.Rider;
import App.Scenes.Controller.SceneController;

import java.io.IOException;
import java.sql.SQLException;

public class DiventaRiderController {

    /**********Attributi**********/

    private Rider rider;

    /**********Metodi**********/

    /**********Costruttori**********/

    public DiventaRiderController(String patente, String veicolo) {
        this.rider = new Rider(Cliente.getInstance().getId(), patente, veicolo);
    }

    /**********Metodi di funzionalità**********/

    public String diventaRider() throws SQLException, IOException {
        String messaggio = this.rider.diventaRider(this.rider);
        if(messaggio.equals("rider_aggiunto")){
            SceneController.getInstance().riderLogout();
        }
        return messaggio;
    }

}