package App.Scenes.Controller;

public class BaseSceneController {
    SceneController sceneController;

    public BaseSceneController(){
        this.sceneController = SceneController.getInstance();
    }

    public void setVisibility(String id, boolean toggle){
        sceneController.getScene().lookup("#"+id).setVisible(toggle);
        sceneController.getScene().lookup("#"+id).setManaged(toggle);

    }

    public boolean getVisibility(String id){
        return sceneController.getScene().lookup("#"+id).isVisible();

    }

}
