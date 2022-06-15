package _Controleur_Trafic;

import ___Pilote.PiloteUI;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class controleurAgent extends GuiAgent {

    //gui: objet pour loger les messages de l'agent Controlleur
    private transient controleurUI gui;


    @Override
    protected void setup() {

        if (getArguments().length==1){
            gui= (controleurUI) getArguments()[0];
            gui.setControleurAgent(this);
        }

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);

        parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,3000) {
            @Override
            protected void onTick() {
                gui.DisplyData();

            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                //MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                MessageTemplate messageTemplate =
                        MessageTemplate.or(
                                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
                        );

                ACLMessage msg = receive(messageTemplate);

                if (msg!=null){
                    switch(msg.getPerformative()){
                        case ACLMessage.REQUEST:

                            String PisteDispo = gui.GetFreePiste();

                            ACLMessage msgToGst = msg.createReply();
                            msgToGst.setPerformative(ACLMessage.INFORM);
                            msgToGst.setContent(PisteDispo);
                            send(msgToGst);
                            break;

                        case ACLMessage.INFORM:
                            gui.LibererPiste();

                            break;
                    }
                }
            }
        });

    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
