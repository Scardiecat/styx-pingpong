package org.scardiecat.styx.pingpong;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.scardiecat.styx.pingpong.api.Definitions;

public class ClusterInitializer {

    ActorSystem system = null;

    public boolean start(String port, String role){
        port = port.isEmpty() ? "0" : port;
        role = role.isEmpty() ? Definitions.BackendRoleName() : role;

        final Config config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.parseString("akka.cluster.roles = ["+ role + "]"))
                .withFallback(ConfigFactory.load("reference"));

        system = ActorSystem.create(Definitions.ActorSystemName(), config);

        if(role == Definitions.BackendRoleName()) {
            system.actorOf(PongActor.props(), Definitions.PongActorName());
        }

        return (system != null);
    }

    public boolean stop(){
        if(system != null ){
            system.shutdown();
            return true;
        }
        return false;
    }
}
