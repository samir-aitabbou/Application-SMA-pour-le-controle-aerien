package ___Pilote;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PiloteUI extends Application{

    protected PiloteAgent piloteAgent;
    ObservableList<String> observableList;
    //private int PisteAlloué =0 ; // initialisation par hasard

    /*public int getPisteAlloué() {
        return PisteAlloué;
    }

    public void setPisteAlloué(int pisteAlloué) {
        PisteAlloué = pisteAlloué;
    }*/

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartContainer();

        stage.setTitle("Pilote");
        stage.setWidth(300);
        stage.setHeight(500);

        HBox hBox=new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        Button buttonReserver=new Button("Reserver une piste");
        Button buttonLiberer=new Button("Je libére ma piste");
        hBox.getChildren().addAll(buttonReserver,buttonLiberer);

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(10));
        observableList = FXCollections.observableArrayList();
        ListView<String> listViewMessage =new ListView<String>(observableList);
        vBox.getChildren().add(listViewMessage);

        BorderPane borderPane =new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vBox);
        Scene scene =new Scene(borderPane,600,400);
        stage.setScene(scene);
        stage.show();

        buttonReserver.setOnAction(evt->{
            String Message="PILOTE: Reservez moi une piste s'ils vous plais !";
            // observableList.add(livre);
            GuiEvent event =new GuiEvent(this,1);
            event.addParameter(Message);
            piloteAgent.onGuiEvent(event);

        });

        buttonLiberer.setOnAction(evt->{
            //int Piste_A_Liberer=getPisteAlloué();
            GuiEvent event =new GuiEvent(this,2);
            //event.addParameter("");
            piloteAgent.onGuiEvent(event);

        });

    }

    public void StartContainer() throws StaleProxyException {

        Runtime runtime=Runtime.instance();
        ProfileImpl profileImpl =new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        // creation d'un container
        AgentContainer container = runtime.createAgentContainer(profileImpl);
        //creation d'un agent (deploiement de l'agent dans le container)
        AgentController agentController= container
                .createNewAgent("Pilote","___Pilote.PiloteAgent",new Object[]{this});
        agentController.start();
    }

    public void setPiloteAgent(PiloteAgent piloteAgent) {
        this.piloteAgent = piloteAgent;
    }


    public void logMessage(ACLMessage aclMessage) {
        observableList.add(aclMessage.getContent());
    }

}
