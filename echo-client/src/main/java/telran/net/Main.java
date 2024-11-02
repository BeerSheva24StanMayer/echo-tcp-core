package telran.net;

import telran.view.*;

public class Main {
    static EchoClient echoClient;

    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::exit, true)
      };
      Menu menu = new Menu("Echo Application", items);
      menu.perform(new StandardInputOutput());
    }

    public static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname: ");
        Integer port = io.readNumberRange("Enter port: ", "Wrong port", 3000, 65536).intValue();
        if(echoClient != null) {
            echoClient.close();
        }
        echoClient = new EchoClient(host, port);
        Menu menu = new Menu("Run session", Item.of("Enter string", Main::stringProcessing), Item.ofExit());
        menu.perform(io);

    }

    static void stringProcessing(InputOutput io) {
        String string = io.readString("Enter any string: ");
        String response = echoClient.sendAndReceive(string);
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if(echoClient != null) {
            echoClient.close();
        }
    }
}