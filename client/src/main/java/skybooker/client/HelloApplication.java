package skybooker.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static Scene scene;

    public static FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public static void setFxmlLoader(FXMLLoader fxmlLoader) {
        HelloApplication.fxmlLoader = fxmlLoader;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        HelloApplication.scene = scene;
    }

    private static FXMLLoader fxmlLoader;



    @Override
    public void start(Stage stage) throws IOException {

        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 825, 600 , false , SceneAntialiasing.BALANCED);

        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("assets/images/Vector.png"))));

        stage.setTitle("SkyBooker");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setHeight(600);
        stage.setWidth(825);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}