package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import skybooker.client.DTO.ClassDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PreferencesController {

    @FXML
    private Button nextButton ;

    @FXML
    private VBox scrollPaneBody ;

    public static boolean isComingFromBookPopup = false;
    private List<ReservationDTO.PassagerData> chosenPassagers = new ArrayList<>();

    @FXML
    protected void onReturnButton()
    {
        GeneralUtils.changeView("landingpage-view.fxml" , () -> isComingFromBookPopup = false);
    }

    @FXML
    protected void onNextButton()
    {
        GeneralUtils.changeView("searchresults-view.fxml" , () -> isComingFromBookPopup = false);
    }

    @FXML
    protected void onPlusButton()
    {
        loadPassengerEditor(PassengerEditorController.Mode.CREATE);
    }

    @FXML
    protected void onEditButton()
    {
        loadPassengerEditor(PassengerEditorController.Mode.EDIT);
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(()->{
            initializePassengers();
            if(isComingFromBookPopup)
            {
                nextButton.setDisable(false);
                nextButton.setOpacity(1);
            }
        });
    }

    private void initializePassengers()
    {
        // fetching the passagers
        List<PassagerDTO> passagers;
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try {
            String res = Client.get("/passager/");
            passagers = mapper.readValue(res, new TypeReference<>() {});
            for (PassagerDTO passager : passagers) {
                ClientCache.add(passager);
            }
            //fetching the classes ; you chose this route for them
            res = Client.get("/classe/");
            List<ClassDTO> classes = mapper.readValue(res, new TypeReference<>() {});
            HashMap<String , ClassDTO> classMap = new HashMap<>();
            for(ClassDTO classe : classes){
                ClientCache.add(classe);
                classMap.put(classe.getNom() , classe);
            }


            //Show the passagers  ;; Stop ordering me around >:( ;; f u
            for(PassagerDTO passager : passagers)
            {
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER);
                container.setSpacing(20);

                CheckBox checkBox = new CheckBox();
                checkBox.setStyle("-fx-background-color: #EDEDED");

                Text fName = new Text(passager.getPrenom());
                fName.setFont(new Font("Roboto" , 20));

                Text lName = new Text(passager.getNom());
                lName.setFont(new Font("Roboto" , 20));

                Text category = new Text("Category");
                category.setFont(new Font("Roboto" , 20));

                ChoiceBox<String> classe = new ChoiceBox<>();
                classe.setValue("Class");
                classe.setStyle("-fx-text-fill: rgba(0,0,0,0.5) ;-fx-background-color: #EDEDED  ;-fx-background-radius: 12 ;-fx-border-radius: 12 ;-fx-font-family: 'Roboto Light' ;-fx-font-size: 15 ;");
                classe.setMinHeight(36);
                classe.setMaxHeight(36);
                classe.setMinWidth(70);
                classe.setMaxWidth(70);

                for(String key : classMap.keySet()){
                    classe.getItems().add(key);
                }

                checkBox.setOnAction(_ -> {
                    if(checkBox.isSelected()){
                        ReservationDTO.PassagerData data = new ReservationDTO.PassagerData();
                        data.setPassagerId(passager.getId());
                        data.setClassId(classMap.get(classe.getValue()).getId());
                        chosenPassagers.add(data);
                    }else{
                        for( ReservationDTO.PassagerData data : chosenPassagers ){
                            if(data.getPassagerId().equals(passager.getId())){
                                chosenPassagers.remove(data);
                                break;
                            }
                        }
                    }
                });

                //TODO : figure out how to get the category of the passenger

                StackPane stackPane = new StackPane();

                URL imageUrl = HelloApplication.class.getResource(" assets/icons/Edit.png") ;
                Image image = new Image(imageUrl.toString());
                ImageView icon = new ImageView(image);
                Button button = new Button();
                button.setMaxWidth(16);
                button.setMinWidth(16);
                button.setMaxHeight(16);
                button.setMinHeight(16);
                button.setOpacity(0);
                button.setOnAction(_ -> loadPassengerEditor(PassengerEditorController.Mode.EDIT));

                stackPane.getChildren().addAll(icon , button);
                container.getChildren().addAll(checkBox , fName , lName , category , classe , stackPane);

                scrollPaneBody.getChildren().addAll(container , new Separator());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPassengerEditor(PassengerEditorController.Mode mode)
    {
        try{
            Parent parent = HelloApplication.loadView("passengereditor-view.fxml");
            Stage secondaryStage = new Stage();

            secondaryStage.setWidth(738);
            secondaryStage.setHeight(418);

            Scene parentScene = HelloApplication.getScene();

            ColorAdjust dim = new ColorAdjust();
            dim.setBrightness(-0.5);
            dim.setInput(new GaussianBlur(10));

            parentScene.getRoot().setEffect(dim);

            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);

            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene);

            secondaryStage.initOwner(parentScene.getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);

            PassengerEditorController.window = secondaryStage;
            PassengerEditorController.mode = mode;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("Passager editor No such view");
        }
    }
}
