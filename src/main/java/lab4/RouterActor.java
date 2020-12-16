package lab4;

import akka.actor.*;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import java.util.ArrayList;

public class RouterActor extends AbstractActor {
    private ActorRef store;
    private Router router;
    private static final int TRIES = 5;
    private SupervisorStrategy supervisorStrategy = new OneForOneStrateg(
            TRIES, 
    );
    public RouterActor(){
        store = getContext().actorOf(Props.create(ActorSave.class));
        ArrayList<Routee> routees = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            ActorRef actorRef = getContext().actorOf(Props.create(ExecutorActor.class));
            routees.add(new ActorRefRoutee(actorRef));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    public Receive createReceive(){

    }

}
