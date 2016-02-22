package org.scardiecat.styx.pingpong;

import akka.cluster.Cluster;
import akka.actor.UntypedActor;
import akka.actor.Address;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.scardiecat.styx.pingpong.api.ChangePongMessage;
import org.scardiecat.styx.pingpong.api.Ping;
import org.scardiecat.styx.pingpong.api.Pong;


public class PongActor extends UntypedActor {
    LoggingAdapter LOGGER = Logging.getLogger(getContext().system(), this);
    final Cluster cluster = Cluster.get(getContext().system());
    final Address selfAddress = cluster.selfAddress();
    Integer counter = 0;
    String pongMessage = "MessageNotSet";

    public static Props props() {
        return Props.create(PongActor.class);
    }

    public PongActor() {
    }

    @Override
    public void preStart() {
        LOGGER.debug("PongActor({}) pre start", selfAddress.toString());
        counter = 0;
    }

    @Override
    public void postStop() {
        LOGGER.debug("PongActor({}) post stop", selfAddress.toString());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Ping) {
            counter++;
            Ping ping = (Ping) message;
            if(counter % 1 == 0) {
                LOGGER.info("In PongActor {} - received message: {} count {}", selfAddress.toString(), ping.txt(), counter);
            }
            getSender().tell(new Pong(pongMessage), getSelf());
        } else if (message instanceof ChangePongMessage){
            pongMessage =  ((ChangePongMessage) message).txt();
        }
        else {
            unhandled(message);
        }
    }
}