package App.Scenes.Controller;

import App.Controllers.GestisciArticoliController;
import App.Controllers.InserisciArticoloController;
import javafx.scene.control.ComboBox;
import java.sql.SQLException;

public class GestisciRistoranteController extends BaseSceneController {

    /**********Attributi**********/

    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;

    /**********Metodi**********/

    /**********Metodi di bottoni**********/

    public void inserisciArticoloBtn() {
        ((ComboBox) getElementById("inserisciArticoloBox")).getItems().clear();
        try {
            setInserisciArticoliBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: hand !important");
        sceneController.setVisibile("inserisciArticoloHBox", true);
        sceneController.setCliccatoBtn("inserisciArticoloBtn");
    }

    public void gestisciArticoloBtn() {
        ((ComboBox) getElementById("gestisciArticoloBox")).getItems().clear();
        try {
            setGestisciArticoliBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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



    public void aggiungiEsistenteBtn() {
    }

    public void aggiungiManualmenteBtn() {
    }

    public void abilitaBtn() {
    }

    public void disabilitaBtn() {
    }

    public void eliminaBtn() {
    }

    public void aggiungiNuovoRistoranteBtn() {
    }

    public void aggiungiGestoreBtn() {
    }

    /**********Metodi di supporto**********/

    public void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciArticoloBox")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(3));//FIXME
    }

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(3));//FIXME
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