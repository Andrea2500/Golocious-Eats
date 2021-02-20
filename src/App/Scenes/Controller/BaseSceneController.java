package App.Scenes.Controller;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class BaseSceneController {

    /**********Attributi**********/

    protected SceneController sceneController;

    /**********Costruttori**********/

    public BaseSceneController() {
        this.sceneController = SceneController.getInstance();
    }

    /**********Metodi**********/

    /**********Metodi di cambio scene**********/

    public void ordinaBtn() throws IOException {
        sceneController.ordina();
    }

    public void ordiniEffettuatiBtn() throws IOException {
        sceneController.ordiniEffettuati();
    }

    public void consegnaBtn() throws IOException {
        sceneController.consegna();
    }

    public void consegneEffettuateBtn() throws IOException {
        sceneController.consegneEffettuate();
    }

    public void gestisciRistoranteBtn() throws IOException {
        sceneController.gestisciRistorante();
    }

    public void impostazioniBtn() throws IOException {
        sceneController.impostazioni();
    }

    public void logoutBtn() throws IOException {
        sceneController.logout();
    }

    /**********Metodi di supporto**********/

    protected Node getElementById(String id) {
        return sceneController.getScene().lookup("#" + id);
    }

    protected boolean getVisibility(String id) {
        return sceneController.getScene().lookup("#" + id).isVisible();

    }

    protected String getValue(String id, String control) {
        return switch (control) {
            case "textfield" -> ((TextField) sceneController.getScene().lookup("#" + id)).getText();
            case "passwordfield" -> ((PasswordField) sceneController.getScene().lookup("#" + id)).getText();
            case "label" -> ((Label) sceneController.getScene().lookup("#" + id)).getText();
            case "combobox" -> ((ComboBox<String>) sceneController.getScene().lookup("#" + id)).getSelectionModel().getSelectedItem();
            default -> "";
        };
    }

    protected LocalDate getValue(String id) {
        return ((DatePicker) sceneController.getScene().lookup("#" + id)).getValue();
    }

    protected void errore(String id, String errore, boolean field){
        Node node = this.getElementById(id);
        if(field) {
            String text = id.substring(6, id.indexOf("Label")).toLowerCase()+"Field";
            getElementById(text).setStyle("-fx-border-color: #ff0000");
        }
        ((Label) node).setText(errore);
    }

    protected void inizializzaLabel(String id, boolean field) {
        Node node = this.getElementById(id);
        if(field){
            String text = id.substring(6, id.indexOf("Label")).toLowerCase()+"Field";
            getElementById(text).setStyle("-fx-border-color: transparent");
        }
        ((Label) node).setText("");
    }

    protected int eta(LocalDate datanascita) {
        if(datanascita != null)
            return Period.between(datanascita, LocalDate.now()).getYears();
        else return 0;
    }

}