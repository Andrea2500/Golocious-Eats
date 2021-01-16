package Main;

import App.Config.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

public class Main extends Application {

    Stage primaryStage = new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../App/Scenes/Login.fxml"));
        primaryStage.setTitle("Golocious");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
        primaryStage.getIcons().add(new javafx.scene.image.Image("/Stylesheets/logo.jpg"));

    }

    public static void main(String[] args) {
        launch(args);

    }

    /*public void setHomeClienteScene() throws IOException {
        this.primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../App/Scenes/Login.fxml")), 800, 400));
    }*/
}
