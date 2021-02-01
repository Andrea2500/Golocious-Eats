package App.Scenes.Controller;

import App.Controllers.AggiungiGestoreController;
import App.Controllers.AggiungiRistoranteController;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    Ristorante ristoranteAttivo;
    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;
    AggiungiGestoreController aggiungiGestoreController;
    AggiungiRistoranteController aggiungiRistoranteController;
    Gestore gestore;
    @FXML ComboBox<Ristorante> selezionaRistoranteBox;

    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciRistoranteController() throws SQLException {
        this.gestore = new Gestore(Cliente.getInstance());
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
            errore("erroreSelezionaRistoranteLabel", "Prima di procedere seleziona un ristorante", false);
            getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: #ff0000");
        }
    }

    public void gestisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteBox.getSelectionModel().getSelectedItem() != null) {
            ((ComboBox) getElementById("gestisciArticoloBox")).getItems().clear();
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
            errore("erroreSelezionaRistoranteLabel", "Prima di procedere seleziona un ristorante", false);
            getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: #ff0000");
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
            errore("erroreSelezionaRistoranteLabel", "Prima di procedere seleziona un ristorante", false);
            getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: #ff0000");
        }
    }

    public void selezionaRistoranteBtn() throws SQLException {
        resetErroriSelezionaRistorante();
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("selezionaRistoranteVBox", true);
        sceneController.setCliccatoBtn("selezionaRistoranteBtn");
        selezionaRistoranteBox.setItems(this.gestore.getRistorantiDB(Cliente.getInstance()));
    }



    public void aggiungiEsistenteBtn() {
    }

    public void aggiungiManualmenteBtn() {
    }

    public void abilitaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        int index = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedIndex();
        this.gestisciArticoliController.switchDisponibilita(true,this.ristoranteAttivo, articolo);
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
        ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().select(index);
    }

    public void disabilitaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        int index = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedIndex();
        this.gestisciArticoliController.switchDisponibilita(false,this.ristoranteAttivo, articolo);
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
        ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().select(index);
    }

    public void eliminaBtn() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = ((ComboBox<Articolo>) getElementById("gestisciArticoloBox")).getSelectionModel().getSelectedItem();
        String messaggio = this.gestisciArticoliController.eliminaDaMenu(this.ristoranteAttivo, articolo);
    }

    public void aggiungiNuovoRistoranteBtn() throws SQLException {
        String nome = ((TextField)getElementById("nomeRistoranteField")).getText();
        String indirizzo = ((TextField)getElementById("indirizzoRistoranteField")).getText();
        String telefono = ((TextField)getElementById("telefonoRistoranteField")).getText();
        LocalDate dataApertura = ((DatePicker) getElementById("dataaperturaRistoranteField")).getValue();

        if(nome.length() > 0 && indirizzo.length() > 0 && telefono.length() > 0 &&dataApertura != null ){
            this.aggiungiRistoranteController = new AggiungiRistoranteController();
            Ristorante ristorante = new Ristorante(nome,indirizzo,telefono,dataApertura);
            String messaggio = this.aggiungiRistoranteController.aggiungiRistorante(ristorante);

        }




    }

    public void aggiungiGestoreBtn() throws SQLException {
        this.aggiungiGestoreController = new AggiungiGestoreController();
        String email = ((TextField) getElementById("gestoreField")).getText();
        String messaggio = this.aggiungiGestoreController.rendiGestore(email,ristoranteAttivo);
    }

    public void selezionaRistoranteBox(){
        this.ristoranteAttivo = ((ComboBox<Ristorante>) getElementById("selezionaRistoranteBox")).getSelectionModel().getSelectedItem();
    }

    /**********Metodi di supporto**********/

    public void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciArticoloBox")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(this.ristoranteAttivo));
    }

    public void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciArticoloBox")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
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

    public void resetErroriSelezionaRistorante() {
        inizializzaLabel("erroreSelezionaRistoranteLabel", false);
        getElementById("selezionaRistoranteBox").setStyle("-fx-border-color: transparent");
    }

}