package App.Scenes.Controller;

import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class BaseSceneController {

    SceneController sceneController;


    public BaseSceneController() {
        this.sceneController = SceneController.getInstance();
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
            default -> "";
        };
    }

    public LocalDate getValue(String id) {
        return ((DatePicker) sceneController.getScene().lookup("#" + id)).getValue();
    }

}