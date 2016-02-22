package main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.scardiecat.styx.pingpong.ClusterInitializer;
import org.scardiecat.styx.pingpong.api.Definitions;
import org.scardiecat.styx.utils.commandline.Commandline;
import org.scardiecat.styx.utils.commandline.CommandlineParser;

import java.net.UnknownHostException;
import java.util.Arrays;

public class PingPongBackendBoot {

    public static void main(String [] args) throws UnknownHostException {
        Config fallbackConfig = ConfigFactory.load();
        Commandline commandline =  CommandlineParser.parse(args,meta.BuildInfo.name()+":"+meta.BuildInfo.version(),
                fallbackConfig, scala.collection.JavaConversions.asScalaBuffer(Arrays.asList(Definitions.BackendRoleName())).seq());
        if(commandline != null) {
            ClusterInitializer initializer = new ClusterInitializer();
            initializer.start(commandline, fallbackConfig);
        }
    }
}
