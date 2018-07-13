package client.facade;


import client.server.communication.ServerProxy;
import shared.command.GenericCommand;
import shared.exception.ServerProxyException;
import shared.model.Message;
import shared.configuration.ConfigurationManager;

public class GameLobbyFacade {
    static ServerProxy serverProxy = ServerProxy.instance();

//    public static void leaveGame(String userName, String gameID) throws ServerProxyException {
//        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
//        Object[] paramValues = {userName, gameID};
//
//        GenericCommand command = new GenericCommand(
//                ConfigurationManager.getString("server_facade_name"),
//                ConfigurationManager.getString("leaveGame"),
//                paramTypes,
//                paramValues,
//                null);
//        serverProxy.sendCommand(command);
//    }

    public static void startGame(String gameID) throws ServerProxyException {
        String[] paramTypes = {String.class.getCanonicalName()};
        Object[] paramValues = {gameID};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_start_game_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);

    }

    public static void sendMessage(Message message) throws ServerProxyException {
        String[] paramTypes = {Message.class.getCanonicalName()};
        Object[] paramValues = {message};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_send_message_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);
    }
}