package org.scardiecat.styx.pingpong.di;

import akka.actor.Actor;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.scardiecat.styx.pingpong.PongActor;
import org.scardiecat.styx.pingpong.api.Definitions;


public class PingPongModule extends AbstractModule {
    protected void configure() {
        //Actor injection
        bind(Actor.class).annotatedWith(Names.named(Definitions.PongActorName())).to(PongActor.class);
    }
}