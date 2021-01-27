package App.Scenes.Controller;

import App.Controllers.aggiungiIndirizzoController;
import App.Controllers.SelezionaIndirizzoController;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import App.Controllers.diventaRiderController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImpostazioniController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    private Cliente cliente;
    private aggiungiIndirizzoController aggIndController;
    private SelezionaIndirizzoController selIndirizzoController;
    private diventaRiderController divRidController;
    @FXML private ComboBox<Indirizzo> indirizzoBox;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ImpostazioniController() throws SQLException {
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
        this.selIndirizzoController = new SelezionaIndirizzoController();
        setListaIndirizzi();
        resetErroriGestisciIndirizzi();
        sceneController.setVisibile("gestisciIndirizziVBox", true);
        sceneController.setCliccatoBtn("gestisciIndirizziBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibile("diventaRiderHBox", true);
        sceneController.setCliccatoBtn("diventaRiderBtn");
    }

    public void eliminaAccountBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibile("eliminaAccountVBox", true);
        sceneController.setCliccatoBtn("eliminaAccountBtn");
    }

    public void aggiungiBtn() throws SQLException {
        resetErroriIndirizzo();
        String paese = getValue("paeseField", "textfield");
        String provincia = getValue("provinciaField", "textfield");
        String cap = getValue("capField", "textfield");
        String citta = getValue("cittaField", "textfield");
        String indirizzo = getValue("indirizzoField", "textfield");
        if(paese.length()>0 && provincia.length()>0 && cap.length()>0 && citta.length()>0 && indirizzo.length()>0){
            aggIndController = new aggiungiIndirizzoController(paese, provincia, citta, cap, indirizzo);
            String messaggio = aggIndController.aggiungiIndirizzo();
            if(messaggio.equals("indirizzo_aggiunto")){
                resetCampiIndirizzo();
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
            String messaggio = selIndirizzoController.setIndirizzoAttivo(attivo.getId());
            if (messaggio.equals("indirizzo_aggiornato")) {
                errore("indirizzoAttivoLabel", "Indirizzo attivo aggiornato con successo", false);
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriGestisciIndirizzi();
        }
    }

    public void eliminaIndirizzoBtn() {
        //TODO elimina indirizzo dal db
    }

    public void iniziaAConsegnareBtn() throws SQLException {
        resetErroriRider();
        String patente = getValue("patenteField", "textfield");
        String veicolo = getValue("veicoloBox", "combobox");
        if(patente.length()>0 && veicolo != null) {
            divRidController = new diventaRiderController(patente, veicolo);
            String messaggio = divRidController.diventaRider();
            if(messaggio.equals("rider_aggiunto")) {
                ((Label) getElementById("vuotoLabel")).setText("Congratulazioni! Effettua nuovamente il login per iniziare a lavorare");
                vuotoVBox(); //FIXME non ha senso modificare il testo alla label e mandare a vuotoVBox!!
                resetCampiRider();
                //TODO logout
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriRider(patente, veicolo);
        }
    }

    public void eliminaDefinitivamenteBtn() {
        //TODO alertbox che chiede se si è sicuri
    }

    /**********Metodi di supporto**********/

    public void resetBoxManagedAndVisible() {
        sceneController.setVisibile("vuotoVBox", false);
        sceneController.setVisibile("inserisciIndirizzoHBox", false);
        sceneController.setVisibile("gestisciIndirizziVBox", false);
        sceneController.setVisibile("diventaRiderHBox", false);
        sceneController.setVisibile("eliminaAccountVBox", false);
    }

    public void resetBtnColor() {
        if(getElementById("inserisciIndirizzoHBox").isVisible()) {
            getElementById("inserisciIndirizzoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("gestisciIndirizziVBox").isVisible()) {
            getElementById("gestisciIndirizziBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("diventaRiderHBox").isVisible()) {
            getElementById("diventaRiderBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("eliminaAccountVBox").isVisible()) {
            getElementById("eliminaAccountBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    private void setListaIndirizzi() throws SQLException {
        ObservableList<Indirizzo> indirizzi= this.selIndirizzoController.getIndirizzi();
        indirizzoBox.setItems(indirizzi);
        int index = 0;
        Integer indAttivoCliente = this.cliente.getIndirizzoAttivo();
        ObservableList<Indirizzo> lista = indirizzoBox.getItems();
        for(Indirizzo el: lista){
            if (el.getId().equals(indAttivoCliente)){
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

    private void resetCampiRider() {
        ((TextField) getElementById("patenteField")).setText("");
        ((ComboBox<String>) getElementById("veicoloBox")).getSelectionModel().clearSelection();
    }

    public void resetErroriRider() {
        inizializzaLabel("errorePatenteLabel", true);
        inizializzaLabel("erroreVeicoloLabel", false);
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

    private void resetErroriGestisciIndirizzi() {
        inizializzaLabel("indirizzoAttivoLabel", false);
        getElementById("indirizzoBox").setStyle("-fx-border-color: transparent");
    }

    public void setErroriGestisciIndirizzi() {
        errore("indirizzoAttivoLabel", "Seleziona un indirizzo", false);
        getElementById("indirizzoBox").setStyle("-fx-border-color: red");
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "ck_patente" -> errore("errorePatenteLabel", "Inserisci una patente", true);
            case "troppo_lungo" -> errore("errorePatenteLabel", "Patente troppo lunga", true);
            case "ck_indirizzo_attivo_del_cliente" -> errore("indirizzoAttivoLabel","Si è verificato un errore. Riprova.",false);
            default -> errore("erroreRiderLabel", "Siamo spiacenti, si è verificato un errore", false);
        }
    }

}
