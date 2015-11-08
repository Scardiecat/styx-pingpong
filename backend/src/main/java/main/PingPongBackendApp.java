package main;


public class PingPongBackendApp {
    public static void main(String [] args) {
        if (args.length > 0) {
            PingPongBackendMain.main(args);
        } else {
            PingPongBackendMain.main(new String[]{"2551", "seed"});
            PingPongBackendMain.main(new String[]{"2552"});
            PingPongBackendMain.main(new String[]{"0"});
        }
    }
}
