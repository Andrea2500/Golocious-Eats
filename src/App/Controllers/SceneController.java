package App.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class SceneController extends Controller{

    private String getScene(String scene) throws Exception {
        File f = new File("../Scenes/"+scene+".fxml");
        if(f.exists())
            return "../Scenes/"+scene+".fxml";
        else throw new Exception("Scena non trovata");
    }

    public void loadScene(Stage primaryStage,String scene) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(this.getScene(scene)));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}
