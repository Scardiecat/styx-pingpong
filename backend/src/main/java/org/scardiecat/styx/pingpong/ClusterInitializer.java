package org.scardiecat.styx.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.scardiecat.styx.DockerAkkaUtils;
import org.scardiecat.styx.akkaguice.AkkaModule;
import org.scardiecat.styx.akkaguice.GuiceAkkaExtension;
import org.scardiecat.styx.pingpong.api.Definitions;
import org.scardiecat.styx.pingpong.di.ConfigModule;
import org.scardiecat.styx.pingpong.di.PingPongModule;
import org.scardiecat.styx.utils.commandline.Commandline;

import java.net.UnknownHostException;

public class ClusterInitializer {

    ActorSystem system = null;


    public boolean start(Commandline commandline, Config fallbackConfig) throws UnknownHostException {

        String hostAddress = java.net.InetAddress.getLocalHost().getHostAddress();

        Config config  = DockerAkkaUtils.dockerAkkaConfigProvider(fallbackConfig,hostAddress,commandline);

        Module confModule = new ConfigModule(config, commandline.actorSystemName());
        Module akkaModule = new AkkaModule();
        Module pingPongModule = new PingPongModule();
        Injector injector = Guice.createInjector(confModule, akkaModule, pingPongModule);

        system = injector.getInstance(ActorSystem.class);

        ActorRef actor = system.actorOf(FromConfig.getInstance().props(GuiceAkkaExtension.get(system).props(Definitions.PongActorName())), Definitions.PongActorName());


        return (system != null);
    }

    public boolean stop() {
        if (system != null) {
            system.terminate();
            return true;
        }
        return false;
    }
}
