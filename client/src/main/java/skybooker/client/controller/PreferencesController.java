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
import skybooker.client.DTO.CategorieDTO;
import skybooker.client.DTO.ClassDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.util.*;

public class PreferencesController {

    @FXML
    private Button nextButton ;

    @FXML
    private VBox scrollPaneBody ;

    public static boolean isComingFromBookPopup = false;

    private final static List<ReservationDTO.PassagerData> chosenPassagers = new ArrayList<>();

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

            for(PassagerDTO passager : passagers)
            {
                ReservationDTO.PassagerData data = new ReservationDTO.PassagerData();

                HBox container = new HBox();
                container.setAlignment(Pos.CENTER);
                container.setSpacing(20);

                CheckBox checkBox = new CheckBox();
                checkBox.setStyle("-fx-background-color: #EDEDED");

                if (chosenPassagers.stream().anyMatch(p -> p.getPassagerId().equals(passager.getId()))) {
                    checkBox.setSelected(true);
                }

                VBox fnameContainer = new VBox();
                fnameContainer.setAlignment(Pos.CENTER);
                fnameContainer.setMinWidth(90);
                fnameContainer.setMaxWidth(90);

                Text fName = new Text(passager.getPrenom());
                fName.setFont(new Font("Roboto" , 20));

                fnameContainer.getChildren().add(fName);

                VBox lnameContainer = new VBox();
                lnameContainer.setAlignment(Pos.CENTER);
                lnameContainer.setMinWidth(90);
                lnameContainer.setMaxWidth(90);

                Text lName = new Text(passager.getNom());
                lName.setFont(new Font("Roboto" , 20));

                lnameContainer.getChildren().add(lName);

                VBox categoryContainer = new VBox();
                categoryContainer.setAlignment(Pos.CENTER);
                categoryContainer.setMinWidth(77);
                categoryContainer.setMaxWidth(77);

                Text category = new Text(ClientCache.get(passager.getCategorieId() ,CategorieDTO.class).getNom());
                category.setFont(new Font("Roboto" , 20));

                categoryContainer.getChildren().add(category);

                ChoiceBox<String> classe = new ChoiceBox<>();
                List<ReservationDTO.PassagerData> chosenPassager = chosenPassagers.stream().filter(p -> p.getPassagerId().equals(passager.getId())).toList();
                if (chosenPassager.isEmpty()) {
                    classe.setValue("Economy");
                } else {
                    classe.setValue(ClientCache.get(chosenPassager.getFirst().getClassId(), ClassDTO.class).getNom());
                }

                classe.setStyle("-fx-text-fill: rgba(0,0,0,0.5) ;-fx-background-color: #EDEDED  ;-fx-background-radius: 12 ;-fx-border-radius: 12 ;-fx-font-family: 'Roboto Light' ;-fx-font-size: 15 ;");
                classe.setMinHeight(36);
                classe.setMaxHeight(36);
                classe.setMinWidth(100);
                classe.setMaxWidth(100);
                classe.setOnAction(_ ->{
                    chosenPassagers.forEach(p -> {
                        if (p.getPassagerId().equals(passager.getId())) {
                            p.setClassId(classMap.get(classe.getValue()).getId());
                        }
                    });
                });

                for(String key : classMap.keySet()){
                    classe.getItems().add(key);
                }

                checkBox.setOnAction(_ -> {
                    data.setPassagerId(passager.getId());
                    data.setClassId(classMap.get(classe.getValue()).getId());
                    if(checkBox.isSelected()){
                        chosenPassagers.add(data);
                    } else {
                        chosenPassagers.removeIf(p -> p.getPassagerId().equals(passager.getId()));
                    }
                });

                StackPane stackPane = new StackPane();

                URL imageUrl = HelloApplication.class.getResource("assets/icons/Edit.png") ;
                Image image = new Image(imageUrl.toString());
                ImageView icon = new ImageView(image);
                Button button = new Button();
                button.setMaxWidth(16);
                button.setMinWidth(16);
                button.setMaxHeight(16);
                button.setMinHeight(16);
                button.setOpacity(0);
                button.setOnAction(_ -> {
                    PassengerEditorController.setPassagerId(passager.getId());
                    loadPassengerEditor(PassengerEditorController.Mode.EDIT);
                });

                stackPane.getChildren().addAll(icon , button);
                stackPane.setStyle("-fx-cursor: hand;");
                container.getChildren().addAll(checkBox , fnameContainer , lnameContainer , categoryContainer , classe , stackPane);

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
            secondaryStage.setX(parentScene.getWindow().getX() + (parentScene.getWidth()- secondaryStage.getWidth())*0.5 );
            secondaryStage.setY(parentScene.getWindow().getY()  + (parentScene.getHeight()- secondaryStage.getHeight())*0.5);

            PassengerEditorController.window = secondaryStage;
            PassengerEditorController.mode = mode;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("Passager editor No such view");
            e.printStackTrace();
        }
    }

    public static List<ReservationDTO.PassagerData> getChosenPassagers() {
        return chosenPassagers;
    }
}
