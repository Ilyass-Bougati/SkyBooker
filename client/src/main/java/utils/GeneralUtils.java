package utils;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.Duration;
import skybooker.client.HelloApplication;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.Callable;

public class GeneralUtils {

    public static void loadView(String viewFileName)throws IOException
    {
        HelloApplication.setFxmlLoader(new FXMLLoader(HelloApplication.class.getResource(viewFileName)));
        HelloApplication.getScene().setRoot(HelloApplication.getFxmlLoader().load());
    }

    public static FadeTransition fadeInAnimation(Node node , int milliseconds)
    {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(node);
        fadeIn.setCycleCount(1);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setDuration(new Duration(milliseconds));
        fadeIn.setAutoReverse(false);
        return fadeIn;
    }

    public static FadeTransition fadeOutAnimation(Node node , int milliseconds)
    {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setNode(node);
        fadeOut.setCycleCount(1);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDuration(new Duration(milliseconds));
        fadeOut.setAutoReverse(false);
        return fadeOut;
    }

    public static void initializeDatePicker(DatePicker date , DateVerifier verification)
    {
        date.getEditor().setDisable(true);
        date.setDayCellFactory(getDateCell(verification));

    }

    private static Callback<DatePicker , DateCell> getDateCell(DateVerifier verification) {
        return _ -> new DateCell(){
            @Override
            public void updateItem(LocalDate date , boolean empty){
                super.updateItem(date , empty);
                verification.setCell(this);

                try{
                    if(!verification.call()){
                        setDisable(true);
                    }
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        };
    }

    public static abstract class DateVerifier implements Callable<Boolean> {
        private DateCell cell ;
        public DateCell getCell()
        {
            return cell;
        }
        public void setCell(DateCell cell)
        {
            this.cell = cell;
        }
    }
}
