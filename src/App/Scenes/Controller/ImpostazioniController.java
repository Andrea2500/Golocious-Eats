package App.Scenes.Controller;

import App.Objects.Cliente;
import App.Objects.Indirizzo;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;

public class ImpostazioniController extends BaseSceneController {

    /**********Attributi**********/

    private Cliente cliente;

    /**********Metodi**********/

    /**********Costruttori**********/

    public ImpostazioniController() {
        this.cliente = Cliente.getInstance();
    }

    public void inserisciIndirizzoBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        resetErroriIndirizzo();
        sceneController.setVisibile("inserisciIndirizzoHBox", true);
        sceneController.setCliccatoBtn("inserisciIndirizzoBtn");
    }

    public void aggiungiBtn() throws SQLException {
        resetErroriIndirizzo();
        String paese = getValue("paeseField", "textfield");
        String provincia = getValue("provinciaField", "textfield");
        String cap = getValue("capField", "textfield");
        String citta = getValue("cittaField", "textfield");
        String indirizzo = getValue("indirizzoField", "textfield");
        Indirizzo indirizzoCompleto = new Indirizzo(paese,provincia,cap,citta,indirizzo);
        if(paese.length()>0 && provincia.length()>0 && cap.length()>0 && citta.length()>0 && indirizzo.length()>0){
            String messaggio = this.cliente.aggiungiIndirizzoDB(indirizzoCompleto);
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


    public void gestisciIndirizziBtn() throws SQLException {
        this.cliente.setIndirizzi(this.cliente.getIndirizziDB());
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        cliente.setIndirizzi(cliente.getIndirizziDB());
        setListaIndirizzi();
        resetErroriGestisciIndirizzi();
        sceneController.setVisibile("gestisciIndirizziVBox", true);
        sceneController.setCliccatoBtn("gestisciIndirizziBtn");
    }

    public void rendiAttivoBtn() throws SQLException {
        resetErroriGestisciIndirizzi();
        Indirizzo attivo = ((ComboBox<Indirizzo>)getElementById("indirizzoattivoField")).getSelectionModel().getSelectedItem();
        if(attivo != null) {
            String messaggio = this.setIndirizzoAttivo(attivo);
            if (messaggio.equals("indirizzo_aggiornato")) {
                errore("erroreIndirizzoattivoLabel", "Indirizzo attivo aggiornato con successo", false);
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
        Indirizzo elimina = ((ComboBox<Indirizzo>)getElementById("indirizzoattivoField")).getSelectionModel().getSelectedItem();
        if(elimina != null) {
            String messaggio = this.eliminaIndirizzo(elimina);
            if (messaggio.equals("indirizzo_eliminato")) {
                errore("erroreIndirizzoattivoLabel", "Indirizzo eliminato con successo", false);
                setListaIndirizzi();
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriGestisciIndirizzi();
        }
    }


    public void diventaRiderBtn() {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        resetErroriRider();
        //resetCampiRider();
        sceneController.setVisibile("diventaRiderHBox", true);
        sceneController.setCliccatoBtn("diventaRiderBtn");
    }

    public void iniziaAConsegnareBtn() throws SQLException, IOException {
        resetErroriRider();
        String patente = getValue("patenteField", "textfield");
        String veicolo = getValue("veicoloField", "combobox");
        if(patente.length()>0 && veicolo != null) {
            setErroriDB(this.diventaRider(this.cliente, patente, veicolo));
        } else {
            setErroriRider(patente, veicolo);
        }
    }

    /**********Metodi di supporto**********/

    public void resetVHBoxManagedAndVisible() {
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
        ComboBox indirizzoattivoField = ((ComboBox)getElementById("indirizzoattivoField"));
        indirizzoattivoField.getItems().clear();
        cliente.setIndirizzi(cliente.getIndirizziDB());
        ObservableList<Indirizzo> indirizzi = this.cliente.getIndirizzi();
        indirizzoattivoField.setItems(indirizzi);
        int index = 0;
        Indirizzo indAttivoCliente = this.cliente.getIndirizzoAttivo();
        ObservableList<Indirizzo> lista = indirizzoattivoField.getItems();
        if(indAttivoCliente != null) {
            for (Indirizzo el : lista) {
                if (el.getIndirizzoId().equals(indAttivoCliente.getIndirizzoId())) {
                    break;
                }
                index++;
            }
            indirizzoattivoField.getSelectionModel().select(index);
        }
    }

    public String setIndirizzoAttivo(Indirizzo indirizzo) throws SQLException {
        String messaggio = this.cliente.aggiornaIndirizzoAttivoDB(indirizzo.getIndirizzoId());
        if(messaggio.equals("indirizzo_aggiornato")) {
            this.cliente.setIndirizzoAttivo(indirizzo);
        }
        return messaggio;
    }

    public String eliminaIndirizzo(Indirizzo indirizzo) throws SQLException {
        String messaggio = new Indirizzo().eliminaIndirizzo(indirizzo.getIndirizzoId());
        if(this.cliente.getIndirizzoAttivo() != null){
            if (indirizzo.getIndirizzoId().equals(cliente.getIndirizzoAttivo().getIndirizzoId()))
                this.cliente.setIndirizzoAttivo(null);
        }
        return messaggio;
    }

    public String diventaRider(Cliente cliente, String patente, String veicolo) throws SQLException, IOException {
        String messaggio = cliente.diventaRider(patente, veicolo);
        if(messaggio.equals("rider_aggiunto")) {
            SceneController.getInstance().riderLogout();
        }
        return messaggio;
    }

    /**********Metodi di ripristino e di errori**********/

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

    public void resetErroriIndirizzo() {
        inizializzaLabel("errorePaeseLabel", true);
        inizializzaLabel("erroreProvinciaLabel", true);
        inizializzaLabel("erroreCapLabel", true);
        inizializzaLabel("erroreCittaLabel", true);
        inizializzaLabel("erroreIndirizzoLabel", true);
        inizializzaLabel("correttoLabel", false);
    }

    public void resetCampiIndirizzo() {
        ((TextField) getElementById("paeseField")).setText("");
        ((TextField) getElementById("provinciaField")).setText("");
        ((TextField) getElementById("cittaField")).setText("");
        ((TextField) getElementById("capField")).setText("");
        ((TextField) getElementById("indirizzoField")).setText("");
    }

    public void setErroriGestisciIndirizzi() {
        errore("erroreIndirizzoattivoLabel", "Seleziona un indirizzo", true);
    }

    private void resetErroriGestisciIndirizzi() {
        inizializzaLabel("erroreIndirizzoattivoLabel", true);
    }

    public void setErroriRider(String patente, String veicolo) {
        if(patente.length()==0) {
            errore("errorePatenteLabel", "Inserisci una patente", true);
        }
        if(veicolo == null) {
            errore("erroreVeicoloLabel", "Inserisci un veicolo", true);
        }
    }

    public void resetErroriRider() {
        inizializzaLabel("errorePatenteLabel", true);
        inizializzaLabel("erroreVeicoloLabel", false);
        inizializzaLabel("erroreRiderLabel", false);
        inizializzaLabel("erroreVeicoloLabel", true);
    }

    /*
    public void resetCampiRider() {

        ((TextField) getElementById("patenteField")).setText("");
        ((ComboBox<String>) getElementById("veicoloBox")).getSelectionModel().clearSelection();
    }
    */

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "ck_patente" -> errore("errorePatenteLabel", "Inserisci una patente", true);
            case "troppo_lungo" -> errore("errorePatenteLabel", "Patente troppo lunga", true);
            case "ck_indirizzo_attivo_del_cliente" -> errore("erroreIndirizzoattivoLabel","Si è verificato un errore. Riprova.",false);
            case "eliminazione_indirizzo_fallita" -> errore("erroreIndirizzoattivoLabel","Non è stato possibile eliminare l'indirizzo. Riprova.",false);
            case "aggiunta_rider_fallita" -> errore("erroreRiderLabel", "Siamo spiacenti, si è verificato un errore", false);
            case "ck_eta_rider" -> errore("erroreRiderLabel", "Devi essere maggiorenne per iscriverti come rider", false);
            case "rider_patente_key" -> errore("erroreRiderLabel", "La patente è già registrata", false);
        }
    }

}
