package App.Scenes.Controller;

public class BaseSceneController {
    SceneController sceneController;

    public BaseSceneController() {
        this.sceneController = SceneController.getInstance();
    }
}
