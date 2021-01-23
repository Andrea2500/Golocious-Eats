package App.Scenes.Controller;

import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.Period;

public class BaseSceneController {

    SceneController sceneController;


    public BaseSceneController() {
        this.sceneController = SceneController.getInstance();
    }

    public Node getElementById(String id) {
        return sceneController.getScene().lookup("#" + id);
    }

    public void setVisibility(String id, boolean toggle) {
        sceneController.getScene().lookup("#" + id).setVisible(toggle);
        sceneController.getScene().lookup("#" + id).setManaged(toggle);

    }

    public boolean getVisibility(String id) {
        return sceneController.getScene().lookup("#" + id).isVisible();

    }

    public String getValue(String id, String control) {
        return switch (control) {
            case "textfield" -> ((TextField) sceneController.getScene().lookup("#" + id)).getText();
            case "passwordfield" -> ((PasswordField) sceneController.getScene().lookup("#" + id)).getText();
            case "label" -> ((Label) sceneController.getScene().lookup("#" + id)).getText();
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
            getElementById(text).setStyle("-fx-border-color: red");
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
        return Period.between(datanascita, LocalDate.now()).getYears();
    }

    public void ResetErrori(){
        inizializzaLabel("erroreNomeLabel", true);
        inizializzaLabel("erroreCognomeLabel", true);
        inizializzaLabel("erroreEmailLabel", true);
        inizializzaLabel("errorePasswordLabel", true);
        inizializzaLabel("erroreTelefonoLabel", true);
        inizializzaLabel("erroreDatanascitaLabel", true);
        inizializzaLabel("erroreLoginLabel", false);
    }

}