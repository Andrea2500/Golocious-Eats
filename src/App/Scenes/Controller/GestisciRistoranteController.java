package App.Scenes.Controller;

import App.Controllers.GestisciArticoloController;
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



    /**********Metodi di supporto**********/

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoloController = new GestisciArticoloController();
        this.gestisciArticoloBox.setItems(gestisciArticoloController.getArticoli(3));
    }



    /**********Metodi di ripristino e di errori**********/


}