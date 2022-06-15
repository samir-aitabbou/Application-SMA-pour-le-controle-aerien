package ____Main;


import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class MainContainer {

    public static void main(String[] args) throws ControllerException {

        //instancie jade
        Runtime runtime = Runtime.instance();

        // ProfileImpl pour configurer GUI
        ProfileImpl profileImpl= new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.GUI,"true");

        // creation du mainContainer
        AgentContainer mainContainer =  runtime.createMainContainer(profileImpl);
        mainContainer.start();

    }
}
