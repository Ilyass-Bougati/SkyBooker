package skybooker.client.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import skybooker.client.HelloApplication;
import skybooker.client.controller.BookPopupController;

import java.io.IOException;

public class GeneralUtils {
    public static void changeView(String viewName){
        try{
            Parent parent = HelloApplication.loadView(viewName);
            HelloApplication.getScene().setRoot(parent);
        }catch(IOException e)
        {
            System.out.println(viewName + " No such view");
        }
    }

    public static void changeView(String viewName , Runnable r){
        try{
            Parent parent = HelloApplication.loadView(viewName);
            r.run();
            HelloApplication.getScene().setRoot(parent);
        }catch(IOException e)
        {
            System.out.println(viewName + " No such view");
        }
    }

    public static void loadBookPopup()
    {
        try{
            Parent parent = HelloApplication.loadView("bookpopup-view.fxml");
            Stage secondaryStage = new Stage();

            secondaryStage.setWidth(573);
            secondaryStage.setHeight(193);

            Scene parentScene = HelloApplication.getScene();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene);

            ColorAdjust dim = new ColorAdjust();
            dim.setBrightness(-0.5);
            dim.setInput(new GaussianBlur(10));

            parentScene.getRoot().setEffect(dim);
            secondaryStage.initOwner(parentScene.getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);

            BookPopupController.window = secondaryStage;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("Book popup : No such view");
        }
    }

    public static void loadHistoryView()
    {
        changeView("history-view.fxml");
    }

    public static void loadMyBookingsView()
    {
        changeView("mybookings-view.fxml");
    }

    public static void loadPreferences()
    {
        changeView("preferences-view.fxml");
    }

    public static void loadLandingPage()
    {
        changeView("landingpage-view.fxml");
    }

    public static void doLogOut() {
        changeView("auth-view.fxml");
    }
}
