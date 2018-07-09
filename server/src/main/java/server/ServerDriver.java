package server;

import java.util.Scanner;

import server.exception.ServerException;

public class ServerDriver {

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

    public static void main(String[] args) throws ServerException{
        ServerCommunicator server;
        Scanner scanner = new Scanner(System.in);

        int port = -1;
        int maxWaitingConnections = -1;

        int argc = args.length;

        switch (argc) {
            case 2:
                maxWaitingConnections = Integer.parseInt(args[1]);

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
