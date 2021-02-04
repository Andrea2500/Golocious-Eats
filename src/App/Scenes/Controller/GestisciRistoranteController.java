package App.Scenes.Controller;

import App.Controller.*;
import App.Objects.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestisciRistoranteController extends BaseSceneController implements Initializable {

    /**********Attributi**********/

    Ristorante ristoranteAttivo;
    InserisciArticoloController inserisciArticoloController;
    GestisciArticoliController gestisciArticoliController;
    ApriRistoranteController apriRistoranteController;
    AggiungiGestoreController aggiungiGestoreController;
    StatisticheController statisticheController;
    Gestore gestore;
    @FXML ComboBox<Ristorante> selezionaRistoranteField;
    @FXML TableView<ElementoStatistiche> tabellaStatistiche;
    @FXML private TableColumn<ElementoStatistiche,String> nomeColonna;
    @FXML private TableColumn<ElementoStatistiche,String> prezzoColonna;
    @FXML private TableColumn<ElementoStatistiche,Integer> quantitaColonna;
    @FXML private TableColumn<ElementoStatistiche,String> totaleColonna;



    /**********Metodi**********/

    /**********Costruttori**********/

    public GestisciRistoranteController() throws SQLException {
        this.gestore = new Gestore(Cliente.getInstance());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selezionaRistoranteField.setItems(this.gestore.getRistoranti());
        nomeColonna.setCellValueFactory(new PropertyValueFactory<>("nomeArticolo"));
        prezzoColonna.setCellValueFactory(new PropertyValueFactory<>("prezzoArticolo"));
        quantitaColonna.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        totaleColonna.setCellValueFactory(new PropertyValueFactory<>("totale"));
    }

    /**********Metodi di bottoni**********/

    public void inserisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            resetErroriAggiungiManualmente();
            ((ComboBox) getElementById("inserisciarticoloField")).getItems().clear();
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

    public void aggiungiEsistenteBtn() throws SQLException {
        inizializzaLabel("correttoLabel", false);
        getElementById("inserisciarticoloField").setStyle("-fx-border-color: transparent");
        Articolo articolo = ((ComboBox<Articolo>) getElementById("inserisciarticoloField")).getSelectionModel().getSelectedItem();
        if(articolo != null){
            this.inserisciArticoloController = new InserisciArticoloController();
            String messaggio = this.inserisciArticoloController.aggiungiEsistente(this.ristoranteAttivo,articolo);
            if(messaggio.equals("articolo_aggiunto")) {
                ((Label) getElementById("correttoLabel")).setText("Articolo aggiunto con successo");
                ((ComboBox) getElementById("inserisciarticoloField")).getItems().clear();
                setInserisciArticoliBox();
            } else {
                setErroriDB(messaggio);
            }
        }else{
            errore("correttoLabel", "Seleziona un articolo", false);
            getElementById("inserisciarticoloField").setStyle("-fx-border-color: #ff0000");
        }
    }

    public void aggiungiManualmenteBtn() throws Exception {
        inizializzaLabel("correttoLabel", false);
        resetErroriAggiungiManualmente();
        String nome = ((TextField) getElementById("nomeField")).getText();
        Float prezzo = null;
        try {
            prezzo = Float.valueOf(((TextField) getElementById("prezzoField")).getText().replace(",", "."));
        } catch (NumberFormatException e) {
            errore("errorePrezzoLabel", "Inserisci un prezzo valido", true);
        }
        String categoria = ((ComboBox<String>) getElementById("categoriaField")).getSelectionModel().getSelectedItem();
        String ingredienti = ((TextField) getElementById("ingredientiField")).getText();
        if(nome.length() > 0 && prezzo != null && categoria != null) {
            this.inserisciArticoloController = new InserisciArticoloController();
            String messaggio = this.inserisciArticoloController.aggiungiManualmente(this.ristoranteAttivo,new Articolo(nome, prezzo.toString(), categoria, ingredienti));
            if(messaggio.equals("articolo_aggiunto")) {
                ((Label) getElementById("correttoLabel")).setText("Articolo aggiunto con successo");
                resetCampiAggiungiManualmente();
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriAggiungiManualmente(nome, prezzo, categoria);
        }
    }


    public void gestisciArticoloBtn(ActionEvent e) {
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
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

    public void abilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        this.gestisciArticoliController = new GestisciArticoliController();
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int index = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.gestisciArticoliController.switchDisponibilitaArticolo(true, this.ristoranteAttivo, articolo);
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
            this.gestisciArticoliController.switchDisponibilitaArticolo(false, this.ristoranteAttivo, articolo);
            gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            gestisciArticolo.getSelectionModel().select(index);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void eliminaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        this.gestisciArticoliController = new GestisciArticoliController();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        if(gestisciArticolo.getSelectionModel().getSelectedItem() != null) {
            String messaggio = this.gestisciArticoliController.eliminaArticoloDaMenu(this.ristoranteAttivo, gestisciArticolo.getSelectionModel().getSelectedItem());
            if(messaggio.equals("eliminazione_articolo_fallita")) {
                errore("erroreGestisciarticoloLabel", "L'eliminazione non è riuscita", true);
            }else{
                gestisciArticolo.setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
            }
        } else {
            setErroriGestisciArticoli();
        }
    }


    public void apriRistoranteBtn() {
        resetErroriApriRistorante();
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("apriRistoranteHBox", true);
        sceneController.setCliccatoBtn("apriRistoranteBtn");
    }

    public void apriOraBtn() throws Exception {
        resetErroriApriRistorante();
        String nome = ((TextField) getElementById("nomeristoranteField")).getText();
        String indirizzo = ((TextField) getElementById("indirizzoristoranteField")).getText();
        String telefono = ((TextField) getElementById("telefonoristoranteField")).getText();
        LocalDate dataApertura = ((DatePicker) getElementById("dataaperturaristoranteField")).getValue();
        if(nome.length() > 0 && indirizzo.length() > 0 && telefono.length() > 0 && dataApertura != null && dataApertura.isBefore(LocalDate.now())) {
            this.apriRistoranteController = new ApriRistoranteController(this.gestore);
            String messaggio = this.apriRistoranteController.apriRistorante(new Ristorante(nome, indirizzo, telefono, dataApertura));
            if(messaggio.equals("ristorante_aperto")) {
                ((Label) getElementById("erroreApriRistoranteLabel")).setText("Ristorante aggiunto con successo");
                resetCampiApriRistorante();
            } else {
                setErroriDB(messaggio);
            }
        } else {
            setErroriApriRistorante(nome, indirizzo, telefono, dataApertura);
        }
    }


    public void rendiGestoreBtn(ActionEvent e) {
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            resetErroriRendiGestore();
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("rendiGestoreVBox", true);
            sceneController.setCliccatoBtn("rendiGestoreBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void aggiungiGestoreBtn() throws SQLException {
        resetErroriRendiGestore();
        this.aggiungiGestoreController = new AggiungiGestoreController();
        String email = ((TextField) getElementById("gestoreField")).getText();
        if(email.length() > 0) {
            String messaggio = this.aggiungiGestoreController.rendiGestore(email, ristoranteAttivo);
            if (messaggio.equals("gestore_aggiunto")) {
                resetCampiRendiGestore();
                ((Label) getElementById("erroreGestoreLabel")).setText("Gestore aggiunto con successo");
            } else {
                setErroriRendiGestore(messaggio);
                setErroriDB(messaggio);
            }
        } else {
            setErroriRendiGestore("email_vuota");
        }
    }


    public void statisticheBtn(ActionEvent e) {
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            //aggiornaRisultatiBtn();
            resetBtnColor();
            resetVHBoxManagedAndVisible();
            sceneController.setVisibile("statisticheHBox", true);
            sceneController.setCliccatoBtn("statisticheBtn");
        } else {
            e.consume();
            setErroriSelezionaRistorante();
        }
    }

    public void filtriBtn() {
        if(getElementById("filtriVBox").isVisible()) {
            ((Button) getElementById("filtriBtn")).setText("Apri filtri");
            sceneController.setVisibile("filtriVBox", false);
            setLarghezzaColonne(false);
        } else {
            ((Button) getElementById("filtriBtn")).setText("Chiudi filtri");
            sceneController.setVisibile("filtriVBox", true);
            setLarghezzaColonne(true);
        }
    }

    public void aggiornaRisultatiBtn() throws SQLException {
        Float daPrezzo = null;
        Float aPrezzo = null;
        String daPrezzoString = ((TextField)getElementById("daPrezzoField")).getText().replace(",", ".");
        String aPrezzoString = ((TextField)getElementById("aPrezzoField")).getText().replace(",", ".");
        if(!daPrezzoString.equals("")) {
            try {
                daPrezzo = Float.parseFloat(daPrezzoString);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un prezzo"); //FIXME
            }
        }
        if(!aPrezzoString.equals("")) {
            try {
                aPrezzo = Float.parseFloat(aPrezzoString);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un prezzo"); //FIXME
            }
        }
        boolean moto = ((CheckBox)getElementById("motoveicoloCheck")).isSelected();
        boolean bici = ((CheckBox)getElementById("biciclettaCheck")).isSelected();
        boolean auto = ((CheckBox)getElementById("automobileCheck")).isSelected();
        LocalDate daData = ((DatePicker)getElementById("daDataField")).getValue();
        LocalDate aData = ((DatePicker)getElementById("aDataField")).getValue();
        if(((daPrezzo == null || aPrezzo == null) || daPrezzo <= aPrezzo) && ((daData == null || aData == null) || (daData.isBefore(aData) || daData.isEqual(aData)))) {
            //TODO
            this.statisticheController = new StatisticheController();
            ObservableList<ElementoStatistiche> statistiche =this.statisticheController.getStatistiche(daPrezzo,aPrezzo,moto,auto,bici,daData,aData, this.ristoranteAttivo);
            this.tabellaStatistiche.setItems(statistiche);
            System.out.println("ciao");
        }
    }


    public void selezionaRistoranteBtn() {
        resetErroriSelezionaRistorante();
        selezionaRistoranteField.setItems(this.gestore.getRistoranti());
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        sceneController.setVisibile("selezionaRistoranteVBox", true);
        sceneController.setCliccatoBtn("selezionaRistoranteBtn");
    }

    public void selezionaRistoranteField(){
        this.ristoranteAttivo = ((ComboBox<Ristorante>) getElementById("selezionaristoranteField")).getSelectionModel().getSelectedItem();
    }

    /**********Metodi di supporto**********/

    private void setInserisciArticoliBox() throws SQLException{
        this.inserisciArticoloController = new InserisciArticoloController();
        ((ComboBox) getElementById("inserisciarticoloField")).setItems(inserisciArticoloController.getArticoliAltriRistoranti(this.ristoranteAttivo));
    }

    private void setGestisciArticoliBox() throws SQLException {
        this.gestisciArticoliController = new GestisciArticoliController();
        ((ComboBox) getElementById("gestisciarticoloField")).setItems(gestisciArticoliController.getArticoliRistorante(this.ristoranteAttivo));
    }

    private void setLarghezzaColonne(Boolean apri) {
        if(apri) {
            this.nomeColonna.setPrefWidth(89);
            this.prezzoColonna.setPrefWidth(89);
            this.quantitaColonna.setPrefWidth(89);
            this.totaleColonna.setPrefWidth(89);
        } else {
            this.nomeColonna.setPrefWidth(145);
            this.prezzoColonna.setPrefWidth(145);
            this.quantitaColonna.setPrefWidth(145);
            this.totaleColonna.setPrefWidth(146);
        }
    }

    /**********Metodi di ripristino e di errori**********/

    private void resetVHBoxManagedAndVisible() {
        sceneController.setVisibile("inserisciArticoloHBox", false);
        sceneController.setVisibile("gestisciArticoliVBox", false);
        sceneController.setVisibile("apriRistoranteHBox", false);
        sceneController.setVisibile("rendiGestoreVBox", false);
        sceneController.setVisibile("statisticheHBox", false);
        sceneController.setVisibile("selezionaRistoranteVBox", false);
    }

    private void resetBtnColor() {
        if(getElementById("inserisciArticoloHBox").isVisible()) {
            getElementById("inserisciArticoloBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("gestisciArticoliVBox").isVisible()) {
            getElementById("gestisciArticoliBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("apriRistoranteHBox").isVisible()) {
            getElementById("apriRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("rendiGestoreVBox").isVisible()) {
            getElementById("rendiGestoreBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("statisticheHBox").isVisible()) {
            getElementById("statisticheBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        } else if(getElementById("selezionaRistoranteVBox").isVisible()) {
            getElementById("selezionaRistoranteBtn").setStyle("-fx-background-color: #fab338; -fx-hovered-cursor: pointer");
        }
    }

    private void setErroriAggiungiManualmente(String nome, Float prezzo, String categoria) {
        if(nome.length() == 0){
            errore("erroreNomeLabel", "Inserisci un nome", true);
        }
        if(prezzo == null){
            errore("errorePrezzoLabel", "Inserisci un prezzo", true);
        }
        if(categoria == null) {
            errore("erroreCategoriaLabel", "Seleziona una categoria", true);
        }
    }

    private void resetErroriAggiungiManualmente() {
        inizializzaLabel("erroreNomeLabel", true);
        inizializzaLabel("errorePrezzoLabel", true);
        inizializzaLabel("erroreCategoriaLabel", true);
        inizializzaLabel("erroreIngredientiLabel", true);
    }

    private void resetCampiAggiungiManualmente() {
        ((TextField) getElementById("nomeField")).setText("");
        ((TextField) getElementById("prezzoField")).setText("");
        ((ComboBox) getElementById("categoriaField")).getSelectionModel().clearSelection();
        ((TextField) getElementById("ingredientiField")).setText("");
    }

    private void setErroriGestisciArticoli() {
        errore("erroreGestisciarticoloLabel", "Seleziona un articolo", true);
    }

    private void resetErroriGestisciArticoli() {
        inizializzaLabel("erroreGestisciarticoloLabel", true);
    }

    private void setErroriApriRistorante(String nome, String indirizzo, String telefono, LocalDate dataApertura) {
        if(nome.length()==0){
            errore("erroreNomeristoranteLabel", "Inserisci un nome", true);
        }
        if(indirizzo.length()==0){
            errore("erroreIndirizzoristoranteLabel", "Inserisci un indirizzo", true);
        }
        if(telefono.length()==0){
            errore("erroreTelefonoristoranteLabel", "Inserisci un numero di telefono", true);
        }
        if(dataApertura==null) {
            errore("erroreDataaperturaristoranteLabel", "Seleziona una data di apertura", true);
        } else if(dataApertura.isAfter(LocalDate.now())) {
            errore("erroreDataaperturaristoranteLabel", "Seleziona una data passata", true);
        }
    }

    private void resetErroriApriRistorante() {
        inizializzaLabel("erroreNomeristoranteLabel", true);
        inizializzaLabel("erroreIndirizzoristoranteLabel", true);
        inizializzaLabel("erroreTelefonoristoranteLabel", true);
        inizializzaLabel("erroreDataaperturaristoranteLabel", true);
        inizializzaLabel("erroreApriRistoranteLabel", false);
    }

    private void resetCampiApriRistorante() {
        ((TextField) getElementById("nomeristoranteField")).setText("");
        ((TextField) getElementById("indirizzoristoranteField")).setText("");
        ((TextField) getElementById("telefonoristoranteField")).setText("");
        ((DatePicker) getElementById("dataaperturaristoranteField")).setValue(null);
    }

    private void setErroriRendiGestore(String messaggio) {
        switch (messaggio) {
            case "email_vuota" -> errore("erroreGestoreLabel", "Inserisci un'email", true);
            case "errore_inserimento_gestore" -> errore("erroreGestoreLabel", "Si è verificato un errore", true);
            case "utente_non_trovato" -> errore("erroreGestoreLabel", "L'utente non è stato trovato", true);
        }
    }

    private void resetErroriRendiGestore() {
        inizializzaLabel("erroreGestoreLabel", true);
    }

    private void resetCampiRendiGestore() {
        ((TextField) getElementById("gestoreField")).setText("");
    }

    private void setErroriSelezionaRistorante() {
        errore("erroreSelezionaristoranteLabel", "Prima di procedere seleziona un ristorante", true);
    }

    private void resetErroriSelezionaRistorante() {
        inizializzaLabel("erroreSelezionaristoranteLabel", false);
        getElementById("selezionaristoranteField").setStyle("-fx-border-color: transparent");
    }

    private void setErroriDB(String messaggio) {
        switch (messaggio) {
            case "uq_nome" -> errore("erroreNomeLabel", "Il nome è già esistente", false);
            case "uq_gestore" -> errore("erroreGestoreLabel", "L'utente è già un gestore del ristorante", true);
            case "ck_telefono_ristorante" -> errore("erroreTelefonoristoranteLabel", "Inserisci un telefono valido", false);
            case "ristorante_nome_key" -> errore("erroreApriRistoranteLabel", "Il nome è già esistente", false);
            case "ristorante_non_aggiunto" -> errore("erroreApriRistoranteLabel", "Il ristorante non è stato aggiunto", false);
            case "ck_gestore_non_rider" -> errore("erroreGestoreLabel", "L'utente lavora già come rider", true);
            case "troppo_lungo" -> errore("erroreApriRistoranteLabel", "Uno dei campi inseriti è troppo lungo", false);
        }
    }

}