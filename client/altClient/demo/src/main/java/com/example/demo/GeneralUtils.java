package com.example.demo;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GeneralUtils {
    public static void changeView(String viewName){
        try{
            Parent parent = HelloApplication.loadView(viewName);
            HelloApplication.getScene().setRoot(parent);
        }catch(IOException e)
        {
            System.out.println("No such view");
        }
    }

    public static void changeView(String viewName , Runnable r){
        try{
            Parent parent = HelloApplication.loadView(viewName);
            r.run();
            HelloApplication.getScene().setRoot(parent);
        }catch(IOException e)
        {
            System.out.println("No such view");
        }
    }

    public static void loadBookPopup()
    {
        try{
            Parent parent = HelloApplication.loadView("bookpopup-view.fxml");
            Stage secondaryStage = new Stage();

            secondaryStage.setWidth(573);
            secondaryStage.setHeight(193);

            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(parent));

            secondaryStage.initOwner(HelloApplication.getScene().getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.UNDECORATED);

            BookpopupController.window = secondaryStage;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("No such view");
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
