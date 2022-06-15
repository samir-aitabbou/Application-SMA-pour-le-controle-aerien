package _Controleur_Trafic;

import ___Pilote.PiloteAgent;
import jade.content.onto.basic.TrueProposition;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class controleurUI extends Application {

    ObservableList<String> observableList;
    protected controleurAgent controleurAgent;

    public void setControleurAgent(_Controleur_Trafic.controleurAgent controleurAgent) {
        this.controleurAgent = controleurAgent;
    }

    public Boolean PisteDispo[] = new Boolean[2];
    Boolean Aide[]={true,true};

    public Boolean[] getPisteDispo() {
        return PisteDispo;
    }

    public void setPisteDispo(Boolean[] pisteDispo) {
        PisteDispo = pisteDispo;
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        StartContainer();

        stage.setTitle("Controleur Du Trafic Aerien");
        stage.setWidth(300);
        stage.setHeight(500);

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(10));


        observableList = FXCollections.observableArrayList();
        ListView<String> listViewMessage =new ListView<String>(observableList);
        vBox.getChildren().add(listViewMessage);

        BorderPane borderPane =new BorderPane();
        borderPane.setTop(vBox);
        Scene scene =new Scene(borderPane,600,400);
        stage.setScene(scene);
        stage.show();

    }

    public  void DisplyData(){

        String value1 = "--";
        String value2 = "--";
        if (getPisteDispo()[0]==true)
        {
            value1="dispo";
        }
        else{
            value1="non dispo";
        }

        if (getPisteDispo()[1]==true)
        {
            value2="dispo";
        }
        else{
            value2="non dispo";
        }
         this.logMessage("Piste 1: "+value1+" ||  Piste 2: "+value2);
    }

    public String GetFreePiste(){

        String pisteDispo="---";

        for (int i =0;i<PisteDispo.length;i++)
        {
            if (getPisteDispo()[i]==true){
                pisteDispo = "CONTROLEUR: Hi! elle ya une piste libre : Piste  "+i+1;
                PisteDispo[i]=false;
                break;
            }
        }

        return pisteDispo;
    }

    public void LibererPiste(){

        for (int i =0;i<PisteDispo.length;i++)
        {
            if (getPisteDispo()[i]==false){
                PisteDispo[i]=true;
                break;
            }
        }

    }


    public void StartContainer() throws ControllerException {
        // rendre les pistes dipos a chaque demarrage du container
        setPisteDispo(Aide);
        //DisplyData();


        Runtime runtime=Runtime.instance();
        ProfileImpl profileImpl =new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        // creation d'un container
        AgentContainer container = runtime.createAgentContainer(profileImpl);
        //creation d'un agent (deploiement de l'agent dans le container)
        AgentController agentController= container
                .createNewAgent("Controleur","_Controleur_Trafic.controleurAgent",new Object[]{this});
        agentController.start();
    }

    public void logMessage(String  Msg) {
        Platform.runLater(()->{
            observableList.add(Msg);
        });

    }


    }
