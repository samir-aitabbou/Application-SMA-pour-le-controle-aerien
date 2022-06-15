package __Gestionnaire_Trafic;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class gestionnaireUI extends Application {

    protected gestionnaireAgent gestionnaireAgent;
    ObservableList<String> observableList;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StartContainer();

        stage.setTitle("Gestionnaire_Trafic");
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        observableList = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<String>(observableList);
        vBox.getChildren().add(listView);
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane,300,400);
        stage.setScene(scene);
        stage.show();
    }

    public void StartContainer() throws StaleProxyException {

        Runtime runtime=Runtime.instance();
        ProfileImpl profileImpl =new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        // creation d'un container
        AgentContainer container = runtime.createAgentContainer(profileImpl);
        //creation d'un agent (deploiement de l'agent dans le container)
        AgentController agentController= container
                .createNewAgent("gestionnaire","__Gestionnaire_Trafic.gestionnaireAgent",new Object[]{this});
        agentController.start();
    }


    public void logMessage(ACLMessage aclMessage) {
        observableList.add(aclMessage.getContent());
    }
}
