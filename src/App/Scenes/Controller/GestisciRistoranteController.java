package App.Scenes.Controller;

import App.Controllers.GestisciArticoloController;
import App.Controllers.SelezionaIndirizzoController;
import App.Objects.Articolo;
import App.Objects.Ristorante;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    @FXML ComboBox<Articolo> gestisciArticoloBox;
    GestisciArticoloController gestisciArticoloController;
    Ristorante ristoranteSelezionato;

    /**********Metodi**********/

    /**********Costruttori**********/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gestisciArticoloBox.getItems().clear();
        try {
            setGestisciArticoliBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**********Metodi di bottoni**********/

    /*public void inserisciIndirizzoBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        resetErroriIndirizzo();
        sceneController.setVisibile("inserisciIndirizzoHBox", true);
        sceneController.setCliccatoBtn("inserisciIndirizzoBtn");
    }

    public void gestisciIndirizziBtn() throws SQLException {
        resetBtnColor();
        resetBoxManagedAndVisible();
        this.selezionaIndirizzoController = new SelezionaIndirizzoController();
        cliente.setIndirizzi(cliente.getIndirizziDB());
        setListaIndirizzi();
        resetErroriGestisciIndirizzi();
        sceneController.setVisibile("gestisciIndirizziVBox", true);
        sceneController.setCliccatoBtn("gestisciIndirizziBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        resetErroriRider();
        resetCampiRider();
        sceneController.setVisibile("diventaRiderHBox", true);
        sceneController.setCliccatoBtn("diventaRiderBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        resetErroriRider();
        resetCampiRider();
        sceneController.setVisibile("diventaRiderHBox", true);
        sceneController.setCliccatoBtn("diventaRiderBtn");
    }
*/

    /**********Metodi di supporto**********/

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoloController = new GestisciArticoloController();
        this.gestisciArticoloBox.setItems(gestisciArticoloController.getArticoli(3));
    }

    /**********Metodi di ripristino e di errori**********/


}