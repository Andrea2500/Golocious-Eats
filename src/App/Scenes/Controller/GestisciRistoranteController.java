package App.Scenes.Controller;

import App.Controllers.GestisciArticoloController;
import App.Objects.Articolo;
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

    public void inserisciArticoloBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("inserisciArticoloHBox", true);
        sceneController.setCliccatoBtn("inserisciArticoloBtn");
    }

    public void gestisciArticoloBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("gestisciArticoliVBox", true);
        sceneController.setCliccatoBtn("gestisciArticoliBtn");
    }

    public void aggiungiRistoranteBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        //FIXME
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("aggiungiRistoranteHBox", true);
        sceneController.setCliccatoBtn("aggiungiRistoranteBtn");
    }

    public void rendiGestoreBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("rendiGestoreVBox", true);
        sceneController.setCliccatoBtn("rendiGestoreBtn");
    }

    public void selezionaRistoranteBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("selezionaRistoranteVBox", true);
        sceneController.setCliccatoBtn("selezionaRistoranteBtn");
    }

    /**********Metodi di supporto**********/

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoloController = new GestisciArticoloController();
        this.gestisciArticoloBox.setItems(gestisciArticoloController.getArticoli(3));
    }

    /**********Metodi di ripristino e di errori**********/

    public void resetVHBoxManagedAndVisible() {
        sceneController.setVisibile("inserisciArticoloHBox", false);
        sceneController.setVisibile("gestisciArticoliVBox", false);
        sceneController.setVisibile("aggiungiRistoranteHBox", false);
        sceneController.setVisibile("rendiGestoreVBox", false);
        sceneController.setVisibile("selezionaRistoranteVBox", false);
    }

    public void resetBtnColor() {
        if(getElementById("inserisciArticoloHBox").isVisible()) {
            getElementById("inserisciArticoloBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("gestisciArticoliVBox").isVisible()) {
            getElementById("gestisciArticoliBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("aggiungiRistoranteHBox").isVisible()) {
            getElementById("aggiungiRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("rendiGestoreVBox").isVisible()) {
            getElementById("rendiGestoreBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("selezionaRistoranteVBox").isVisible()) {
            getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

}