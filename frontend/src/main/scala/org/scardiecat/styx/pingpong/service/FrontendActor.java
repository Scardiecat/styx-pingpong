package org.scardiecat.styx.pingpong.service;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.FromConfig;
import org.scardiecat.styx.pingpong.api.Definitions;

public class FrontendActor extends UntypedActor {
    LoggingAdapter LOGGER = Logging.getLogger(getContext().system(), this);

    final ActorRef backendRouter = getContext().actorOf(FromConfig.getInstance().props(), Definitions.PongActorName());

    public static Props props() {
        return Props.create(FrontendActor.class);
    }

    @Override
    public void preStart() {
        getContext().system().log().debug("FrontendActor pre start {}",self().path().toString());
    }

    public void onReceive(Object message) throws Exception {
        backendRouter.forward(message,getContext());
    }
}
