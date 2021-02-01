package App.Scenes.Controller;

import App.Controllers.GestisciArticoliController;
import App.Controllers.InserisciArticoloController;
import App.Objects.Articolo;
import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    Ristorante ristoranteAttivo;
    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;
    Gestore gestore;
    @FXML ComboBox<Ristorante> selezionaRistoranteBox;
    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciRistoranteController() throws SQLException {
        this.gestore = new Gestore(Cliente.getInstance().getClienteId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selezionaRistoranteBox.setItems(this.gestore.getRistoranti());
    }

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
        sceneController.setVisibile("gestisciArticoliVBox", true);
        sceneController.setCliccatoBtn("gestisciArticoliBtn");
    }

    public void aggiungiRistoranteBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("aggiungiRistoranteHBox", true);
        sceneController.setCliccatoBtn("aggiungiRistoranteBtn");
    }

    public void rendiGestoreBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("rendiGestoreVBox", true);
        sceneController.setCliccatoBtn("rendiGestoreBtn");
    }

    public void selezionaRistoranteBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("selezionaRistoranteVBox", true);
        sceneController.setCliccatoBtn("selezionaRistoranteBtn");
    }



    public void aggiungiEsistenteBtn() {
    }

    public void aggiungiManualmenteBtn() {
    }

    public void abilitaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        int index = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedIndex();
        this.gestisciArticoliController.switchDisponibilita(true,this.ristoranteAttivo.getRistoranteId(),articolo.getArticoloid());
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo.getRistoranteId()));
        ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().select(index);
    }

    public void disabilitaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        int index = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedIndex();
        this.gestisciArticoliController.switchDisponibilita(false,this.ristoranteAttivo.getRistoranteId(),articolo.getArticoloid());
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo.getRistoranteId()));
        ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().select(index);
    }

    public void eliminaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        String messaggio = this.gestisciArticoliController.eliminaDaMenu(this.ristoranteAttivo,articolo.getArticoloid());
    }

    public void aggiungiNuovoRistoranteBtn() {
    }

    public void aggiungiGestoreBtn() {
    }

    public void selezionaRistoranteBox(){
        this.ristoranteAttivo = ((ComboBox<Ristorante>) getElementById("selezionaRistoranteBox")).getSelectionModel().getSelectedItem();
    }

    /**********Metodi di supporto**********/

    public void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciArticoloBox")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(this.ristoranteAttivo.getRistoranteId()));//FIXME
    }

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo.getRistoranteId()));//FIXME
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