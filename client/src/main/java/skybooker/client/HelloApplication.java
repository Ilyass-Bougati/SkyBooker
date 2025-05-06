package skybooker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1171, 708 , false , SceneAntialiasing.BALANCED);

        stage.setTitle("Authentication for SkyBooker");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setX(0);
        stage.setY(0);

        stage.setHeight(600);
        stage.setWidth(825);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}