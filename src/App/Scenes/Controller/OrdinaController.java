package App.Scenes.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class OrdinaController extends BaseSceneController {

    public void ordiniEffettuatiBtn() throws IOException {
        sceneController.ordiniEffettuati();
    }

    public void impostazioniBtn() throws IOException {
        sceneController.impostazioni();
    }

    public void logoutBtn() throws IOException, SQLException {
        sceneController.logout();
    }

}
