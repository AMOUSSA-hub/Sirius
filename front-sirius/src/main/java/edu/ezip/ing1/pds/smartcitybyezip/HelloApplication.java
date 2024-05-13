package edu.ezip.ing1.pds.smartcitybyezip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");

        // Obtenir les dimensions de l'écran
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        // Définir la taille de la scène en fonction de la résolution de l'écran
        stage.setScene(scene);
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        // Mettre en plein écran
        stage.setFullScreen(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
