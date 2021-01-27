package App.Scenes.Controller;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class BaseSceneController {

    /**********Attributi**********/

    SceneController sceneController;

    /**********Costruttori**********/

    public BaseSceneController() {
        this.sceneController = SceneController.getInstance();
    }

    /**********Metodi**********/

    /**********Metodi di cambio scene**********/

    public void ordinaBtn() throws IOException, SQLException {
        sceneController.ordina();
    }

    public void ordiniEffettuatiBtn() throws IOException, SQLException {
        sceneController.ordiniEffettuati();
    }

    public void consegnaBtn() throws IOException, SQLException {
        sceneController.consegna();
    }

    public void consegneEffettuateBtn() throws IOException, SQLException {
        sceneController.consegneEffettuate();
    }

    public void gestisciRistoranteBtn() throws IOException, SQLException {
        sceneController.gestisciRistorante();
    }

    public void impostazioniBtn() throws IOException, SQLException {
        sceneController.impostazioni();
    }

    public void logoutBtn() throws IOException, SQLException {
        sceneController.logout();
    }

    /**********Metodi di supporto**********/

    public Node getElementById(String id) {
        return sceneController.getScene().lookup("#" + id);
    }

    public boolean getVisibility(String id) {
        return sceneController.getScene().lookup("#" + id).isVisible();

    }

    public String getValue(String id, String control) {
        return switch (control) {
            case "textfield" -> ((TextField) sceneController.getScene().lookup("#" + id)).getText();
            case "passwordfield" -> ((PasswordField) sceneController.getScene().lookup("#" + id)).getText();
            case "label" -> ((Label) sceneController.getScene().lookup("#" + id)).getText();
            case "combobox" -> ((ComboBox<String>) sceneController.getScene().lookup("#" + id)).getSelectionModel().getSelectedItem();
            default -> "";
        };
    }

    public LocalDate getValue(String id) {
        return ((DatePicker) sceneController.getScene().lookup("#" + id)).getValue();
    }

    public void errore(String id, String errore, boolean field){
        Node node = this.getElementById(id);
        if(field) {
            String text = id.substring(6, id.indexOf("Label")).toLowerCase()+"Field";
            getElementById(text).setStyle("-fx-border-color: #ff0000");
        }
        ((Label) node).setText(errore);
    }

    public void inizializzaLabel(String id, boolean field) {
        Node node = this.getElementById(id);
        if(field){
            String text = id.substring(6, id.indexOf("Label")).toLowerCase()+"Field";
            getElementById(text).setStyle("-fx-border-color: transparent");
        }
        ((Label) node).setText("");
    }

    public int eta(LocalDate datanascita) {
        if(datanascita != null)
            return Period.between(datanascita, LocalDate.now()).getYears();
        else return 0;
    }

}