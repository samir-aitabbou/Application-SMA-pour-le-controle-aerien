package __Gestionnaire_Trafic;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class gestionnaireAgent extends GuiAgent {

    protected  gestionnaireUI gui;
    protected String PisteDispo="--";

    @Override
    protected void setup() {

        if (getArguments().length==1)
        {
            gui= (gestionnaireUI) getArguments()[0];
            gui.gestionnaireAgent=this;
        }

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        //*********************************************
       /* parallelBehaviour.addSubBehaviour(new TickerBehaviour(this,5000) {
            @Override
            protected void onTick() {

                ACLMessage msgToCntr = new ACLMessage(ACLMessage.REQUEST);
                msgToCntr.setContent("allouer moi une piste libre ");
                msgToCntr.addReceiver(new AID("CONTROLEUR",AID.ISLOCALNAME));
                send(msgToCntr);
            }
        });*/
        //*********************************************
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

                MessageTemplate messageTemplate =
                        MessageTemplate.or(
                                MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                MessageTemplate.MatchPerformative(ACLMessage.INFORM)
                                         );

                ACLMessage Demand = receive(messageTemplate);

                if (Demand!=null){
                    switch(Demand.getPerformative()){
                        case ACLMessage.REQUEST:

                            String demande = Demand.getContent();
                            gui.logMessage(Demand);

                          /*  ACLMessage msgToPilote = Demand.createReply();
                            msgToPilote.setPerformative(ACLMessage.PROPOSE);
                            msgToPilote.setContent("Daccord une minute !");
                            send(msgToPilote);*/

                            ACLMessage RequestAgreToPilote = new ACLMessage(ACLMessage.PROPOSE);
                            RequestAgreToPilote.setContent("GESTIONNAIRE: Daccord une minute !");
                            RequestAgreToPilote.addReceiver(new AID("PILOTE",AID.ISLOCALNAME));
                            send(RequestAgreToPilote);

                            ACLMessage msgToCntr = new ACLMessage(ACLMessage.REQUEST);
                            msgToCntr.setContent("GESTIONNAIRE: allouer moi une piste libre ");
                            msgToCntr.addReceiver(new AID("CONTROLEUR",AID.ISLOCALNAME));
                            send(msgToCntr);

                            break;

                        case ACLMessage.INFORM:

                            String demande1 = Demand.getContent();
                            gui.logMessage(Demand);

                            PisteDispo=Demand.getContent();

                            ACLMessage ReplyToPilote = new ACLMessage(ACLMessage.INFORM);
                            ReplyToPilote.setContent(PisteDispo);
                            ReplyToPilote.addReceiver(new AID("PILOTE",AID.ISLOCALNAME));
                            send(ReplyToPilote);
                            break;

                        case ACLMessage.ACCEPT_PROPOSAL:
                            //gui.logMessage(Demand);
                            String demande2 = Demand.getContent();
                            gui.logMessage(Demand);
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
