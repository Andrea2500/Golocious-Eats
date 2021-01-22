package App.Scenes.Controller;

import App.Objects.Cliente;

public class HomeClienteController extends BaseSceneController {

    public void backBtn() throws Exception {
        Cliente.getInstance().Reset();
        sceneController.setScene("Login");
    }

}
