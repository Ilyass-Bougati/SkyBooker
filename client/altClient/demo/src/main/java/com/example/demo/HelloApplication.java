package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Scene scene;
    private static FXMLLoader fxmlLoader;

    public static Scene getScene()
    {
        return scene;
    }

    public static Parent loadView(String viewName) throws IOException{
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewName));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("auth-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 825, 600 , false , SceneAntialiasing.BALANCED);
        stage.setMinWidth(825);
        stage.setMinHeight(600);
        stage.setTitle("Skybooker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}