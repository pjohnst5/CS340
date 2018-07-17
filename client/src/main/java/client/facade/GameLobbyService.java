package client.facade;


import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.model.Message;
import shared.configuration.ConfigurationManager;

public class GameLobbyService {

    public static void leaveGame(AsyncServerTask.AsyncCaller caller, String gameID, String playerId) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {gameID, playerId};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("leaveGame"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    public static void startGame(AsyncServerTask.AsyncCaller caller, String gameID, String playerID) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {gameID, playerID};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_start_game_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);

    }

    public static void sendMessage(AsyncServerTask.AsyncCaller caller, Message message) {
        String[] paramTypes = {Message.class.getCanonicalName()};
        Object[] paramValues = {message};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_send_message_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }
}
