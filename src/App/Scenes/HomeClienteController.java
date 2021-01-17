package App.Scenes;

import App.Controllers.Controller;
import javafx.event.ActionEvent;

public class HomeClienteController extends Controller {

    SceneController sceneController = new SceneController();

    public void backBtn(ActionEvent e) throws Exception {
        this.sceneController.setLogin(e);
    }

}
