package App.Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public void display(String title,String msg){
        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setMinWidth(300);
        alert.setMinHeight(200);
        alert.getIcons().add(new javafx.scene.image.Image("../../Stylesheets/logo.jpg"));


        Label text = new Label();
        text.setText(msg);
        Button close = new Button();
        close.setText("Chiudi");
        close.setOnAction(actionEvent -> alert.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(text,close);
        layout.setAlignment(Pos.CENTER);
        layout.getStylesheets().add("../../Stylesheets/Golocious.css");

        Scene scene = new Scene(layout);


        alert.setScene(scene);
        alert.showAndWait();
    }

}