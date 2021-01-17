package App.Scenes.Controller;

import App.Controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController extends Controller {

    public static SceneController instance;

    public static SceneController getInstance()
    {
        if (instance == null)
            instance = new SceneController();
        return instance;
    }

    private Stage window;

    public void setWindow(Stage window) {
        this.window = window;
    }

    private Stage getWindow() {
        return window;
    }

    public void setScene(String scene) throws IOException {
        Parent tableParent = FXMLLoader.load(getClass().getResource("../"+scene+".fxml"));
        Scene tableScene = new Scene(tableParent, 800, 450);
        this.window.setScene(tableScene);
    }

}
