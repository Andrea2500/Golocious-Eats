package App.Scenes.Controller;

import App.Controllers.AggiungiGestoreController;
import App.Controllers.GestisciArticoliController;
import App.Controllers.InserisciArticoloController;
import App.Objects.Articolo;
import App.Objects.Cliente;
import App.Objects.Gestore;
import App.Objects.Ristorante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    Ristorante ristoranteAttivo;
    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;
    AggiungiGestoreController aggiungiGestoreController;
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

    public void inserisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            ((ComboBox) getElementById("inserisciArticoloBox")).getItems().clear();
            try {
                setInserisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("inserisciArticoloHBox", true);
            sceneController.setCliccatoBtn("inserisciArticoloBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void gestisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            resetErroriGestisciArticoli();
            try {
                setGestisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("gestisciArticoliVBox", true);
            sceneController.setCliccatoBtn("gestisciArticoliBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void aggiungiRistoranteBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("aggiungiRistoranteHBox", true);
        sceneController.setCliccatoBtn("aggiungiRistoranteBtn");
    }

    public void rendiGestoreBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("rendiGestoreVBox", true);
            sceneController.setCliccatoBtn("rendiGestoreBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
            errore("erroreSelezionaRistoranteLabel", "Prima di procedere seleziona un ristorante", false);
            getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: #ff0000");
        }
    }

    public void selezionaRistoranteBtn() {
        resetErroriSelezionaRistorante();
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
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int index = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.gestisciArticoliController.switchDisponibilita(true, this.ristoranteAttivo, articolo);
            gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            gestisciArticolo.getSelectionModel().select(index);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void disabilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int index = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.gestisciArticoliController.switchDisponibilita(false, this.ristoranteAttivo, articolo);
            gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            gestisciArticolo.getSelectionModel().select(index);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void eliminaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciarticoloField")).getSelectionModel().getSelectedItem();
        String messaggio = this.gestisciArticoliController.eliminaDaMenu(this.ristoranteAttivo, articolo);
    }

    public void aggiungiNuovoRistoranteBtn() {
    }

    public void aggiungiGestoreBtn() throws SQLException {
        this.aggiungiGestoreController = new AggiungiGestoreController();
        String email = ((TextField) getElementById("gestoreField")).getText();
        String messaggio = this.aggiungiGestoreController.rendiGestore(email,ristoranteAttivo);
    }

    public void selezionaRistoranteBox(){
        this.ristoranteAttivo = ((ComboBox<Ristorante>) getElementById("selezionaristoranteField")).getSelectionModel().getSelectedItem();
    }

    /**********Metodi di supporto**********/

    public void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciarticoloField")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(this.ristoranteAttivo));
    }

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciarticoloField")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
    }

    /**********Metodi di ripristino e di errori**********/

    private void resetVHBoxManagedAndVisible() {
        sceneController.setVisibile("inserisciArticoloHBox", false);
        sceneController.setVisibile("gestisciArticoliVBox", false);
        sceneController.setVisibile("aggiungiRistoranteHBox", false);
        sceneController.setVisibile("rendiGestoreVBox", false);
        sceneController.setVisibile("selezionaRistoranteVBox", false);
    }

    private void resetBtnColor() {
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

    private void setErroriGestisciArticoli() {
        errore("erroreGestisciarticoloLabel", "Seleziona un articolo", true);
    }

    private void resetErroriGestisciArticoli() {
        inizializzaLabel("erroreGestisciarticoloLabel", true);
    }

    private void setErroriSelezionaRistorante() {
        errore("erroreSelezionaristoranteLabel", "Prima di procedere seleziona un ristorante", true);
    }

    private void resetErroriSelezionaRistorante() {
        inizializzaLabel("erroreSelezionaRistoranteLabel", false);
        getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: transparent");
    }

}