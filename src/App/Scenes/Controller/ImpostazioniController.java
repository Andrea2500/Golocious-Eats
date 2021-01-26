package App.Scenes.Controller;

import App.Controllers.AggiungiIndirizzoController;
import App.Controllers.SelezionaIndirizzoController;
import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


    public void vuotoVBox(){
        resetBtnColor();
        resetBoxManagedAndVisible();
        setManagedAndVisible("vuotoVBox",true);
    }

    public void inserisciIndirizzoBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        setManagedAndVisible("inserisciIndirizzoHBox", true);
        sceneController.setActiveBtn("inserisciIndirizzoBtn");
    }

    public void cambiaIndirizzoAttivoBtn() throws SQLException {
        resetBtnColor();
        resetBoxManagedAndVisible();
        setListaIndirizzi();
        setManagedAndVisible("cambiaIndirizzoAttivoVBox", true);
        sceneController.setActiveBtn("cambiaIndirizzoAttivoBtn");
    }

    public void diventaRiderBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        setManagedAndVisible("diventaRiderHBox", true);
        sceneController.setActiveBtn("diventaRiderBtn");
    }

    public void eliminaAccountBtn() {
        resetBtnColor();
        resetBoxManagedAndVisible();
        setManagedAndVisible("eliminaAccountVBox", true);
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

    public void iniziaAConsegnareBtn() {
        resetErroriConsegna();
        String patente = getValue("patenteField", "textfield");
        String veicolo = getValue("veicoloBox", "combobox");
        if(patente.length()>0 && veicolo != null) {
            //TODO diventa rider
        } else {
            setErroriConsegna(patente, veicolo);
        }
    }
    //TODO refactor method name
    //TODO elimina indirizzo
    public void rendiAttivoBtn() throws SQLException {
        Integer attivo = indirizzoComboBox.getSelectionModel().getSelectedItem().getId();
        selIndirizzoController.setIndirizzoAttivo(attivo);
    }

    public void eliminaDefinitivamenteBtn() {
        //TODO alertbox che chiede se si è sicuri
    }

    public void resetBoxManagedAndVisible() {
        getElementById("vuotoVBox").setManaged(false);
        getElementById("vuotoVBox").setVisible(false);
        getElementById("inserisciIndirizzoHBox").setManaged(false);
        getElementById("inserisciIndirizzoHBox").setVisible(false);
        getElementById("cambiaIndirizzoAttivoVBox").setManaged(false);
        getElementById("cambiaIndirizzoAttivoVBox").setVisible(false);
        getElementById("diventaRiderHBox").setManaged(false);
        getElementById("diventaRiderHBox").setVisible(false);
        getElementById("eliminaAccountVBox").setManaged(false);
        getElementById("eliminaAccountVBox").setVisible(false);
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

    public void setErroriConsegna(String patente, String veicolo) {
        if(patente.length()==0) {
            errore("errorePatenteLabel", "Inserisci una patente", true);
        }
        if(veicolo == null) {
            errore("erroreVeicoloLabel", "Inserisci un veicolo", false);
            getElementById("veicoloBox").setStyle("-fx-border-color: #ff0000");
        }
    }

    public void resetCampiIndirizzo(){
        ((TextField)getElementById("paeseField")).setText("");
        ((TextField)getElementById("provinciaField")).setText("");
        ((TextField)getElementById("cittaField")).setText("");
        ((TextField)getElementById("capField")).setText("");
        ((TextField)getElementById("indirizzoField")).setText("");
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


}
