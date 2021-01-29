package App.Scenes.Controller;

import App.Controllers.EliminaIndirizzoController;
import App.Controllers.AggiungiIndirizzoController;
import App.Controllers.SelezionaIndirizzoController;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import App.Controllers.DiventaRiderController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImpostazioniController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    private Cliente cliente;
    private AggiungiIndirizzoController aggiungiIndirizzoController;
    private SelezionaIndirizzoController selezionaIndirizzoController;
    private EliminaIndirizzoController eliminaIndirizzoController;
    private DiventaRiderController diventaRiderController;
    @FXML private ComboBox<Indirizzo> indirizzoBox;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ImpostazioniController() {
        this.cliente = Cliente.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.indirizzoBox.getItems().clear();
    }

    /**********Metodi di bottoni**********/

    public void vuotoVBox() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibile("vuotoVBox",true);
    }

    public void inserisciIndirizzoBtn() {
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

    public void aggiungiBtn() throws SQLException {
        resetErroriIndirizzo();
        String paese = getValue("paeseField", "textfield");
        String provincia = getValue("provinciaField", "textfield");
        String cap = getValue("capField", "textfield");
        String citta = getValue("cittaField", "textfield");
        String indirizzo = getValue("indirizzoField", "textfield");
        Indirizzo address = new Indirizzo(paese,provincia,cap,citta,indirizzo);
        if(paese.length()>0 && provincia.length()>0 && cap.length()>0 && citta.length()>0 && indirizzo.length()>0){
            aggiungiIndirizzoController = new AggiungiIndirizzoController(address);
            String messaggio = aggiungiIndirizzoController.aggiungiIndirizzo();
            if(messaggio.equals("indirizzo_aggiunto")){
                resetCampiIndirizzo();
                this.cliente.addIndirizzo(address);
                ((Label)getElementById("correttoLabel")).setText("Indirizzo aggiunto correttamente");
            } else if(messaggio.equals("aggiunta_indirizzo_fallita")){
                ((Label)getElementById("correttoLabel")).setText("L'indirizzo non è stato aggiunto");
            }
        } else {
            setErroriIndirizzo(paese, provincia, cap, citta, indirizzo);
        }
    }

    public void rendiAttivoBtn() throws SQLException {
        resetErroriGestisciIndirizzi();
        Indirizzo attivo = indirizzoBox.getSelectionModel().getSelectedItem();
        if(attivo != null) {
            String messaggio = selezionaIndirizzoController.setIndirizzoAttivo(attivo);
            if (messaggio.equals("indirizzo_aggiornato")) {
                errore("indirizzoAttivoLabel", "Indirizzo attivo aggiornato con successo", false);
                cliente.setIndirizzoAttivo(attivo);
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriGestisciIndirizzi();
        }
    }

    public void eliminaIndirizzoBtn() throws SQLException {
        resetErroriGestisciIndirizzi();
        Indirizzo elimina = indirizzoBox.getSelectionModel().getSelectedItem();
        if(elimina != null) {
            this.eliminaIndirizzoController = new EliminaIndirizzoController();
            String messaggio = eliminaIndirizzoController.eliminaIndirizzo(elimina.getId());
            if (messaggio.equals("indirizzo_eliminato")) {
                errore("indirizzoAttivoLabel", "Indirizzo eliminato con successo", false);
                this.setListaIndirizzi();
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriGestisciIndirizzi();
        }
    }

    public void iniziaAConsegnareBtn() throws SQLException, IOException {
        resetErroriRider();
        String patente = getValue("patenteField", "textfield");
        String veicolo = getValue("veicoloBox", "combobox");
        if(patente.length()>0 && veicolo != null) {
            diventaRiderController = new DiventaRiderController(patente, veicolo);
            setErroriDB(diventaRiderController.diventaRider());
        } else {
            setErroriRider(patente, veicolo);
        }
    }

    /**********Metodi di supporto**********/

    public void resetBoxManagedAndVisible() {
        sceneController.setVisibile("vuotoVBox", false);
        sceneController.setVisibile("inserisciIndirizzoHBox", false);
        sceneController.setVisibile("gestisciIndirizziVBox", false);
        sceneController.setVisibile("diventaRiderHBox", false);
    }

    public void resetBtnColor() {
        if(getElementById("inserisciIndirizzoHBox").isVisible()) {
            getElementById("inserisciIndirizzoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("gestisciIndirizziVBox").isVisible()) {
            getElementById("gestisciIndirizziBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("diventaRiderHBox").isVisible()) {
            getElementById("diventaRiderBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    private void setListaIndirizzi() throws SQLException {
        this.indirizzoBox.getItems().clear();
        cliente.setIndirizzi(cliente.getIndirizziDB());
        ObservableList<Indirizzo> indirizzi = this.cliente.getIndirizzi();
        indirizzoBox.setItems(indirizzi);
        int index = 0;
        Indirizzo indAttivoCliente = this.cliente.getIndirizzoAttivo();
        ObservableList<Indirizzo> lista = indirizzoBox.getItems();
        for(Indirizzo el: lista){
            if (el.getId().equals(indAttivoCliente.getId())){
                break;
            }
            index++;
        }
        indirizzoBox.getSelectionModel().select(index);
    }

    /**********Metodi di ripristino e di errori**********/

    public void resetCampiIndirizzo() {
        ((TextField) getElementById("paeseField")).setText("");
        ((TextField) getElementById("provinciaField")).setText("");
        ((TextField) getElementById("cittaField")).setText("");
        ((TextField) getElementById("capField")).setText("");
        ((TextField) getElementById("indirizzoField")).setText("");
    }

    public void resetErroriIndirizzo() {
        inizializzaLabel("errorePaeseLabel", true);
        inizializzaLabel("erroreProvinciaLabel", true);
        inizializzaLabel("erroreCapLabel", true);
        inizializzaLabel("erroreCittaLabel", true);
        inizializzaLabel("erroreIndirizzoLabel", true);
        inizializzaLabel("correttoLabel", false);
    }

    public void setErroriIndirizzo(String paese, String provincia, String cap, String citta, String indirizzo) {
        if(paese.length()==0){
            errore("errorePaeseLabel", "Inserisci un Paese", true);
        }
        if(provincia.length()==0){
            errore("erroreProvinciaLabel", "Inserisci una provincia", true);
        }
        if(cap.length()==0){
            errore("erroreCapLabel", "Inserisci un CAP", true);
        }
        if(citta.length()==0){
            errore("erroreCittaLabel", "Inserisci una città", true);
        }
        if(indirizzo.length()==0){
            errore("erroreIndirizzoLabel", "Inserisci un indirizzo", true);
        }
    }

    private void resetErroriGestisciIndirizzi() {
        inizializzaLabel("indirizzoAttivoLabel", false);
        getElementById("indirizzoBox").setStyle("-fx-border-color: transparent");
    }

    public void setErroriGestisciIndirizzi() {
        errore("indirizzoAttivoLabel", "Seleziona un indirizzo", false);
        getElementById("indirizzoBox").setStyle("-fx-border-color: red");
    }

    public void resetCampiRider() {
        ((TextField) getElementById("patenteField")).setText("");
        ((ComboBox<String>) getElementById("veicoloBox")).getSelectionModel().clearSelection();
    }

    public void resetErroriRider() {
        inizializzaLabel("errorePatenteLabel", true);
        inizializzaLabel("erroreVeicoloLabel", false);
        inizializzaLabel("erroreRiderLabel", false);
        getElementById("veicoloBox").setStyle("-fx-border-color: transparent");
    }

    public void setErroriRider(String patente, String veicolo) {
        if(patente.length()==0) {
            errore("errorePatenteLabel", "Inserisci una patente", true);
        }
        if(veicolo == null) {
            errore("erroreVeicoloLabel", "Inserisci un veicolo", false);
            getElementById("veicoloBox").setStyle("-fx-border-color: #ff0000");
        }
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "ck_patente" -> errore("errorePatenteLabel", "Inserisci una patente", true);
            case "troppo_lungo" -> errore("errorePatenteLabel", "Patente troppo lunga", true);
            case "ck_indirizzo_attivo_del_cliente" -> errore("indirizzoAttivoLabel","Si è verificato un errore. Riprova.",false);
            case "eliminazione_indirizzo_fallita" -> errore("indirizzoAttivoLabel","Non è stato possibile eliminare l'indirizzo. Riprova.",false);
            case "aggiunta_rider_fallita" -> errore("erroreRiderLabel", "Siamo spiacenti, si è verificato un errore", false);
            case "ck_eta_rider" -> errore("erroreRiderLabel", "Devi essere maggiorenne per iscriverti come rider", false);
            case "rider_patente_key" -> errore("erroreRiderLabel", "La patente è già registrata", false);
        }
    }

}
