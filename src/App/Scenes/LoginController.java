package App.Scenes;

import App.Controllers.Controller;
import javafx.event.ActionEvent;

public class LoginController extends Controller {

    SceneController sceneController = new SceneController();

    public void loginBtn(ActionEvent e) throws Exception{
        this.sceneController.setHomeCliente(e);
    }

}
