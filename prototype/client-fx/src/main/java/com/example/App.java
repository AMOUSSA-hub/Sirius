package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    static Scene scene;
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        
    double screenWidth = primaryScreenBounds.getWidth();
    double screenHeight = primaryScreenBounds.getHeight()-70;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("home"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }



    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}