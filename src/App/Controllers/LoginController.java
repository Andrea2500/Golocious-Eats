package App.Controllers;

import javafx.stage.Stage;

public class LoginController extends Controller{

    public void action() throws Exception{
        SceneController sc = new SceneController();
        Stage window = new Stage();
        sc.loadScene(window,"Login");
    }

}
