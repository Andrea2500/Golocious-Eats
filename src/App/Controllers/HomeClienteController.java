package App.Controllers;

import javafx.event.ActionEvent;

public class HomeClienteController extends Controller{

    SceneController sceneController = new SceneController();

    public void backBtn(ActionEvent e) throws Exception{
        this.sceneController.setLogin(e);
    }

}
