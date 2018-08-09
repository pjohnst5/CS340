package server;

import java.util.Scanner;

import server.exception.ServerException;
import shared.GenericFactory;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;
import shared.plugin.IPersistenceProvider;
import shared.plugin.PluginManager;

public class ServerDriver {

    private static final int FAILED_INSTANTIATION = 1;
    private static final String[] permissiblePlugins = { "sql_provider", "json_provider" };

    private static ServerCommunicator createServer(int port, int maxWaitingConnections) throws ServerException {
        if (maxWaitingConnections >= 0)
            return new ServerCommunicator(port, maxWaitingConnections);

        if (port >= 0)
            return new ServerCommunicator(port);

        return new ServerCommunicator();
    }

    private static boolean inputIsCommand(String arg, String[] options) {

        for (int i = 0; i < options.length; i++){
            if (arg.equals(options[i])){
                return true;
            }
        }
        return false;
    }

    private static boolean validatePluginName(String name){
        for (String accept : permissiblePlugins){
            if (accept.equals(name)) return true;
        }
        return false;
    }

    public static void registerPluginType(IPersistenceProvider provider) {


    }

    public static void main(String[] args) throws ServerException{
        ServerCommunicator server;
        Scanner scanner = new Scanner(System.in);

        int port = -1;
        int maxWaitingConnections = -1;

        int argc = args.length;

        switch (argc) {

            case 3:
                int deltaUpdateInterval = Integer.parseInt(args[2]);
                ConfigurationManager.set("delta_update_interval", deltaUpdateInterval);

            case 2:
                String dbType = args[1];
                if (validatePluginName(dbType)) {
                    PluginManager.loadPlugin(dbType);

                } else  {

                    System.err.println("Invalid plugin name: " + dbType );
                    System.err.println("Please choose from the available options:");
                    for (String allowed : permissiblePlugins)
                        System.err.println(allowed);

                    System.exit(FAILED_INSTANTIATION);

                }

            case 1:
                port = Integer.parseInt(args[0]);
                break;
        }

        server =  createServer(port, maxWaitingConnections);
        port = server.start();

        System.out.println("Server running on port " + Integer.toString(port));

        String[] quitCommands = new String[] { "quit", "exit", "close", "q", "e", "c" };

        while(true) {
            String input = scanner.next().toLowerCase();
            if (inputIsCommand(input, quitCommands)) {
                server.stop();
                break;
            }
        }

        scanner.close();
    }
}
