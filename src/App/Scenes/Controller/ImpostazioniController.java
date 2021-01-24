package App.Scenes.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class ImpostazioniController extends BaseSceneController{

    public void ordinaBtn() throws IOException {
        sceneController.ordina();
    }

    public void ordiniEffettuatiBtn() throws IOException {
        sceneController.ordiniEffettuati();
    }

    public void logoutBtn() throws IOException, SQLException {
        sceneController.logout();
    }
}
