package App.Scenes.Controller;

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
        this.gestore = new Gestore();
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
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            resetErroriAggiungiManualmente();
            ((ComboBox) getElementById("inserisciarticoloField")).getItems().clear();
            try {
                setInserisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            sceneController.setVisibile("inserisciArticoloHBox", true);
            sceneController.setCliccatoBtn("inserisciArticoloBtn");
        } else {
            e.consume();
            sceneController.setVisibile("selezionaRistoranteVBox", true);
            sceneController.setCliccatoBtn("selezionaRistoranteBtn");
            setErroriSelezionaRistorante();
        }
    }

    public void aggiungiEsistenteBtn() throws SQLException {
        inizializzaLabel("correttoLabel", false);
        getElementById("inserisciarticoloField").setStyle("-fx-border-color: transparent");
        Articolo articolo = ((ComboBox<Articolo>) getElementById("inserisciarticoloField")).getSelectionModel().getSelectedItem();
        if(articolo != null){
            String messaggio = this.ristoranteAttivo.aggiungiArticoloEsistenteDB(articolo.getArticoloId());
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
            String messaggio = this.aggiungiManualmente(this.ristoranteAttivo,new Articolo(nome, prezzo.toString(), categoria, ingredienti));
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


    public void gestisciArticoliBtn(ActionEvent e) {
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            resetErroriGestisciArticoli();
            try {
                setGestisciArticoliBox();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            sceneController.setVisibile("gestisciArticoliVBox", true);
            sceneController.setCliccatoBtn("gestisciArticoliBtn");
        } else {
            e.consume();
            sceneController.setVisibile("selezionaRistoranteVBox", true);
            sceneController.setCliccatoBtn("selezionaRistoranteBtn");
            setErroriSelezionaRistorante();
        }
    }

    public void abilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int indice = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.ristoranteAttivo.switchDisponibilitaArticoloDB(true, this.ristoranteAttivo.getRistoranteId(), articolo.getArticoloId());
            gestisciArticolo.setItems(this.ristoranteAttivo.getArticoliDB());
            gestisciArticolo.getSelectionModel().select(indice);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void disabilitaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        Articolo articolo = gestisciArticolo.getSelectionModel().getSelectedItem();
        if(articolo != null) {
            int indice = gestisciArticolo.getSelectionModel().getSelectedIndex();
            this.ristoranteAttivo.switchDisponibilitaArticoloDB(false, this.ristoranteAttivo.getRistoranteId(), articolo.getArticoloId());
            gestisciArticolo.setItems(this.ristoranteAttivo.getArticoliDB());
            gestisciArticolo.getSelectionModel().select(indice);
        } else {
            setErroriGestisciArticoli();
        }
    }

    public void eliminaBtn() throws SQLException {
        resetErroriGestisciArticoli();
        ComboBox<Articolo> gestisciArticolo = (ComboBox<Articolo>) getElementById("gestisciarticoloField");
        if(gestisciArticolo.getSelectionModel().getSelectedItem() != null) {
            String messaggio = this.ristoranteAttivo.eliminaArticoloDaMenuDB(gestisciArticolo.getSelectionModel().getSelectedItem().getArticoloId());
            if(messaggio.equals("eliminazione_articolo_fallita")) {
                errore("erroreGestisciarticoloLabel", "L'eliminazione non è riuscita", true);
            }else{
                gestisciArticolo.setItems(this.ristoranteAttivo.getArticoliDB());
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
            String messaggio = this.gestore.apriRistorante(new Ristorante(nome, indirizzo, telefono, dataApertura));
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
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            resetErroriRendiGestore();
            sceneController.setVisibile("rendiGestoreVBox", true);
            sceneController.setCliccatoBtn("rendiGestoreBtn");
        } else {
            e.consume();
            sceneController.setVisibile("selezionaRistoranteVBox", true);
            sceneController.setCliccatoBtn("selezionaRistoranteBtn");
            setErroriSelezionaRistorante();
        }
    }

    public void aggiungiGestoreBtn() throws SQLException {
        resetErroriRendiGestore();
        String email = ((TextField) getElementById("gestoreField")).getText();
        if(email.length() > 0) {
            String messaggio = this.gestore.rendiGestore(email, ristoranteAttivo.getRistoranteId());
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
        resetBtnColor();
        resetVHBoxManagedAndVisible();
        if (selezionaRistoranteField.getSelectionModel().getSelectedItem() != null) {
            sceneController.setVisibile("statisticheHBox", true);
            sceneController.setCliccatoBtn("statisticheBtn");
        } else {
            e.consume();
            sceneController.setVisibile("selezionaRistoranteVBox", true);
            sceneController.setCliccatoBtn("selezionaRistoranteBtn");
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
        resetErroriFiltri();
        Float daPrezzo = null;
        Float aPrezzo = null;
        String daPrezzoString = ((TextField)getElementById("daPrezzoField")).getText().replace(",", ".");
        String aPrezzoString = ((TextField)getElementById("aPrezzoField")).getText().replace(",", ".");
        if(!daPrezzoString.equals("")) {
            try {
                daPrezzo = Float.parseFloat(daPrezzoString);
            } catch (NumberFormatException e) {
                setErroriFiltri(true);
            }
        }
        if(!aPrezzoString.equals("")) {
            try {
                aPrezzo = Float.parseFloat(aPrezzoString);
            } catch (NumberFormatException e) {
                setErroriFiltri(false);
            }
        }
        boolean moto = ((CheckBox)getElementById("motoveicoloCheck")).isSelected();
        boolean bici = ((CheckBox)getElementById("biciclettaCheck")).isSelected();
        boolean auto = ((CheckBox)getElementById("automobileCheck")).isSelected();
        LocalDate daData = ((DatePicker)getElementById("daDataField")).getValue();
        LocalDate aData = ((DatePicker)getElementById("aDataField")).getValue();
        if(daPrezzo != null && aPrezzo != null && daPrezzo > aPrezzo) {
            float tmp = daPrezzo;
            daPrezzo = aPrezzo;
            aPrezzo = tmp;
        }
        if(daData != null && aData != null && daData.isAfter(aData)) {
            LocalDate tmp = daData;
            daData = aData;
            aData = tmp;
        }

        ObservableList<ElementoStatistiche> statistiche = this.getStatistiche(daPrezzo, aPrezzo, moto, bici, auto, daData, aData, this.ristoranteAttivo);
        this.tabellaStatistiche.setItems(statistiche);
        Integer articoliDistinti = 0;
        Double totaleRicavato = Double.valueOf(0);
        Integer articoliVenduti = 0;
        for(ElementoStatistiche e:statistiche){
            articoliDistinti++;
            totaleRicavato += Double.parseDouble(e.getTotale().replace(" €",""));
            articoliVenduti += e.getQuantita();
        }
        ((Label)getElementById("articoliDistintiLabel")).setText("Articoli distinti venduti: "+articoliDistinti.toString());
        ((Label)getElementById("totaleRicavatoLabel")).setText("Totale ricavato: "+totaleRicavato.toString().concat(" €"));
        ((Label)getElementById("totaleQuantitaLabel")).setText("Totale quantità vendute: "+articoliVenduti.toString());
    }


    public void selezionaRistoranteBtn() {
        resetErroriSelezionaRistorante();
        selezionaRistoranteField();
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
        ((ComboBox) getElementById("inserisciarticoloField")).setItems(this.ristoranteAttivo.getArticoliAltriRistorantiDB(this.ristoranteAttivo.getRistoranteId()));
    }

    private void setGestisciArticoliBox() throws SQLException {
        ((ComboBox) getElementById("gestisciarticoloField")).setItems(this.ristoranteAttivo.getArticoliDB());
    }

    private void setLarghezzaColonne(Boolean apri) {
        if(apri) {
            this.nomeColonna.setPrefWidth(173);
            this.prezzoColonna.setPrefWidth(50);
            this.quantitaColonna.setPrefWidth(70);
            this.totaleColonna.setPrefWidth(50);
        } else {
            this.nomeColonna.setPrefWidth(384);
            this.prezzoColonna.setPrefWidth(55);
            this.quantitaColonna.setPrefWidth(70);
            this.totaleColonna.setPrefWidth(60);
        }
    }

    private String aggiungiManualmente(Ristorante ristorante, Articolo articolo) throws Exception {
        String messaggio = articolo.setArticoloDB();
        if(messaggio.equals("articolo_aggiunto")) {
            return ristorante.aggiungiArticoloEsistenteDB(articolo.getArticoloId());
        } else {
            return messaggio;
        }
    }

    private ObservableList<ElementoStatistiche> getStatistiche(Float daPrezzo, Float aPrezzo, boolean moto, boolean bici, boolean auto, LocalDate daData, LocalDate aData, Ristorante ristorante) throws SQLException {
        String veicolo = "('m','b','a')";
        if(!moto)
            veicolo = veicolo.replace("'m'","''");
        if(!bici)
            veicolo = veicolo.replace("'b'","''");
        if(!auto)
            veicolo = veicolo.replace("'a'","''");
        if(!moto && !auto && !bici)
            veicolo = "('m','a','b')";
        return ristorante.getStatisticheDB(daPrezzo, aPrezzo, veicolo, daData, aData, ristorante.getRistoranteId());
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

    private void setErroriFiltri(boolean da) {
        if(da) {
            (getElementById("daPrezzoField")).setStyle("-fx-border-color: #ff0000");
            ((TextField)getElementById("daPrezzoField")).setText("");
            ((TextField)getElementById("daPrezzoField")).setPromptText("Inserisci un prezzo");
        } else {
            (getElementById("aPrezzoField")).setStyle("-fx-border-color: #ff0000");
            ((TextField)getElementById("aPrezzoField")).setText("");
            ((TextField)getElementById("aPrezzoField")).setPromptText("Inserisci un prezzo");
        }
    }

    private void resetErroriFiltri() {
        (getElementById("daPrezzoField")).setStyle("-fx-border-color: transparent");
        ((TextField)getElementById("daPrezzoField")).setPromptText("Da");
        (getElementById("aPrezzoField")).setStyle("-fx-border-color: transparent");
        ((TextField)getElementById("aPrezzoField")).setPromptText("A");
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
            case "ck_telefono_ristorante" -> errore("erroreTelefonoristoranteLabel", "Inserisci un telefono valido", true);
            case "ristorante_nome_key" -> errore("erroreNomeristoranteLabel", "Il nome è già esistente", true);
            case "ristorante_non_aggiunto" -> errore("erroreApriRistoranteLabel", "Il ristorante non è stato aggiunto", false);
            case "troppo_lungo" -> errore("erroreApriRistoranteLabel", "Uno dei campi inseriti è troppo lungo", false);
            case "ck_gestore_non_rider" -> errore("erroreGestoreLabel", "L'utente lavora già come rider", true);
        }
    }

}