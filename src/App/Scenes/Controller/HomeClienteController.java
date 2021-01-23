package App.Scenes.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class HomeClienteController extends BaseSceneController {

    public void backBtn() throws IOException, SQLException {
        sceneController.Logout();
    }

}
