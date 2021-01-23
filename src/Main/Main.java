package Main;

import App.Scenes.Controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    SceneController sc = SceneController.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        sc.setWindow(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("../App/Scenes/FXML/Login.fxml"));
        primaryStage.setTitle("Golocious");
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("/Stylesheets/logo.jpg"));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
