package ___Pilote;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PiloteAgent extends GuiAgent {

    //gui: objet pour loger les messages de l'agent Pilote
    private transient PiloteUI gui;

    @Override
    protected void setup() {

        if (getArguments().length==1){
            gui= (PiloteUI) getArguments()[0];
            gui.setPiloteAgent(this);
        }

        // donner un comportement au agent
        ParallelBehaviour parallelBehaviour =new ParallelBehaviour();
        addBehaviour(parallelBehaviour);


            parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

                @Override
                public void action() {
                    MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
                    ACLMessage aclMessage = receive(messageTemplate);

                    if (aclMessage!=null){
                        switch(aclMessage.getPerformative()){
                            case ACLMessage.INFORM:
                                gui.logMessage(aclMessage);
                                break;

                            case ACLMessage.PROPOSE:

                                gui.logMessage(aclMessage);

                                System.out.println("000000000000000000000000000"+aclMessage);

                                ACLMessage   AgreeToGst = aclMessage.createReply();
                                AgreeToGst.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                                AgreeToGst.setContent("Ok j'attend");
                                send(AgreeToGst);

                                break;

                            default:
                                break;
                        }
                    }
                }
            });


        }

    @Override
    public void onGuiEvent(GuiEvent message) {

        if (message.getType()==1){

            String msg = message.getParameter(0).toString();

            ACLMessage msgToGst = new ACLMessage(ACLMessage.REQUEST);
            msgToGst.setContent(msg);
            msgToGst.addReceiver(new AID("GESTIONNAIRE",AID.ISLOCALNAME));
            send(msgToGst);
        }

        if (message.getType()==2){

            //int msg = (int) message.getParameter(0);

            ACLMessage msgToCtr = new ACLMessage(ACLMessage.INFORM);
            msgToCtr.setContent("PILOTE: je libere la piste");
            msgToCtr.addReceiver(new AID("CONTROLEUR",AID.ISLOCALNAME));
            send(msgToCtr);
        }

    }
}
