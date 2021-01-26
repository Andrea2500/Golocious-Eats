package App.Scenes.Controller;

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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImpostazioniController extends BaseSceneController implements Initializable {

    Cliente cliente;
    @FXML private ComboBox<Indirizzo> indirizzoComboBox;
    AggiungiIndirizzoController aggIndController;
    SelezionaIndirizzoController selIndirizzoController = new SelezionaIndirizzoController();

    public ImpostazioniController() throws SQLException {
        this.cliente = Cliente.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.indirizzoComboBox.getItems().clear();
    }
    DiventaRiderController divRidController;


    public void vuotoVBox() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibility("vuotoVBox",true);
    }

    public void inserisciIndirizzoBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibility("inserisciIndirizzoHBox", true);
        sceneController.setActiveBtn("inserisciIndirizzoBtn");
    }

    public void cambiaIndirizzoAttivoBtn() throws SQLException {
        resetBtnColor();
        resetBoxManagedAndVisible();
        setListaIndirizzi();
        sceneController.setVisibility("cambiaIndirizzoAttivoVBox", true);
        sceneController.setActiveBtn("cambiaIndirizzoAttivoBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibility("diventaRiderHBox", true);
        sceneController.setActiveBtn("diventaRiderBtn");
    }

    public void eliminaAccountBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        sceneController.setVisibility("eliminaAccountVBox", true);
        sceneController.setActiveBtn("eliminaAccountBtn");
    }

    public void aggiungiBtn() throws SQLException {
        resetErroriIndirizzo();
        String paese = getValue("paeseField", "textfield");
        String provincia = getValue("provinciaField", "textfield");
        String cap = getValue("capField", "textfield");
        String citta = getValue("cittaField", "textfield");
        String indirizzo = getValue("indirizzoField", "textfield");
        if(paese.length()>0 && provincia.length()>0 && cap.length()>0 && citta.length()>0 && indirizzo.length()>0){
            aggIndController = new AggiungiIndirizzoController(paese, provincia, citta, cap, indirizzo);
            String messaggio = aggIndController.aggiungiIndirizzo();
            if(messaggio.equals("indirizzo_aggiunto")){
                ((Label)getElementById("correttoLabel")).setText("Indirizzo aggiunto correttamente");
                resetCampiIndirizzo();
            } else if(messaggio.equals("aggiunta_indirizzo_fallita")){
                Label correttoLabel = (Label) getElementById("correttoLabel");
                correttoLabel.setVisible(true);
                correttoLabel.setText("L'indirizzo non è stato aggiunto");
            }
        } else {
            setErroriIndirizzo(paese, provincia, cap, citta, indirizzo);
        }
    }

    public void iniziaAConsegnareBtn() throws SQLException {
        resetErroriConsegna();
        String patente = getValue("patenteField", "textfield");
        String veicolo = getValue("veicoloBox", "combobox");
        if(patente.length()>0 && veicolo != null) {
            divRidController = new DiventaRiderController(patente, veicolo);
            String messaggio = divRidController.diventaRider();
            if(messaggio.equals("rider_aggiunto")) {
                ((Label) getElementById("vuotoLabel")).setText("Congratulazioni! Effettua nuovamente il login per iniziare a lavorare");
                vuotoVBox();
                resetCampiRider();
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriRider(patente, veicolo);
        }
    }
    //TODO refactor method name
    //TODO elimina indirizzo
    public void rendiAttivoBtn() throws SQLException {
        //TODO comboboxvuota
        Integer attivo = indirizzoComboBox.getSelectionModel().getSelectedItem().getId();
        String messaggio = selIndirizzoController.setIndirizzoAttivo(attivo);
        if(messaggio.equals("indirizzo_aggiornato")){
            ((Label) getElementById("indirizzoAttivoLabel")).setText("Indirizzo attivo aggiornato con successo");
        }else{
            setErroriDB(messaggio);
        }
    }

    public void eliminaDefinitivamenteBtn() {
        //TODO alertbox che chiede se si è sicuri
    }

    public void resetBoxManagedAndVisible() {
        sceneController.setVisibility("vuotoVBox", false);
        sceneController.setVisibility("inserisciIndirizzoHBox", false);
        sceneController.setVisibility("cambiaIndirizzoAttivoVBox", false);
        sceneController.setVisibility("diventaRiderHBox", false);
        sceneController.setVisibility("eliminaAccountVBox", false);
    }

    public void resetBtnColor() {
        if(getElementById("inserisciIndirizzoHBox").isVisible()) {
            getElementById("inserisciIndirizzoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("cambiaIndirizzoAttivoVBox").isVisible()) {
            getElementById("cambiaIndirizzoAttivoBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("diventaRiderHBox").isVisible()) {
            getElementById("diventaRiderBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("eliminaAccountVBox").isVisible()) {
            getElementById("eliminaAccountBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    public void resetErroriIndirizzo() {
        inizializzaLabel("errorePaeseLabel", true);
        inizializzaLabel("erroreProvinciaLabel", true);
        inizializzaLabel("erroreCapLabel", true);
        inizializzaLabel("erroreCittaLabel", true);
        inizializzaLabel("erroreIndirizzoLabel", true);
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

    public void resetErroriConsegna() {
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

    public void resetCampiIndirizzo() {
        ((TextField) getElementById("paeseField")).setText("");
        ((TextField) getElementById("provinciaField")).setText("");
        ((TextField) getElementById("cittaField")).setText("");
        ((TextField) getElementById("capField")).setText("");
        ((TextField) getElementById("indirizzoField")).setText("");
    }

    private void resetCampiRider() {
        ((TextField) getElementById("patenteField")).setText("");
        ((ComboBox<String>) getElementById("veicoloBox")).getSelectionModel().clearSelection();
    }

    private void setListaIndirizzi() throws SQLException {
        ObservableList<Indirizzo> indirizzi= this.selIndirizzoController.getIndirizzi(cliente.getId());
        indirizzoComboBox.setItems(indirizzi);
        Integer index = 0;
        Integer indAttivoCliente = Cliente.getInstance().getIndirizzoAttivo();
        ObservableList<Indirizzo> lista = indirizzoComboBox.getItems();
        for(Indirizzo el: lista){
            if (el.getId() == indAttivoCliente){
                break;
            }
            index++;
        }
        indirizzoComboBox.getSelectionModel().select(index);
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
