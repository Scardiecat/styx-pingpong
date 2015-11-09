package main;

import org.scardiecat.styx.pingpong.ClusterInitializer;
import org.scardiecat.styx.pingpong.api.Definitions;

public class PingPongBackendMain {

    public static void main(String [] args){
        final String port = args.length > 0 ? args[0] : "0";
        final String role = args.length > 1 ? args[1] : Definitions.BackendRoleName();

        ClusterInitializer initializer = new ClusterInitializer();

        initializer.start(port, role);
    }
}
