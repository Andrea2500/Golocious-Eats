package App.Scenes.Controller;

import App.Controller.EffettuaConsegnaController;
import App.Objects.Ordine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConsegnaController extends BaseSceneController implements Initializable {

    @FXML private VBox consegnaVBox;
    EffettuaConsegnaController effettuaConsegnaController;
    ObservableList<Ordine> consegneAttive;

    public ConsegnaController() throws SQLException {
        this.effettuaConsegnaController = new EffettuaConsegnaController();
        this.consegneAttive = this.effettuaConsegnaController.getConsegneAttive();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showConsegneAttive();
    }

    private void showConsegneAttive() {
        for (Ordine consegna: consegneAttive){
            HBox hBox = new HBox();
            
        }
    }
}
