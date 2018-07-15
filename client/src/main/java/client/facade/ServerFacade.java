package client.facade;

import client.server.communication.ServerProxy;
import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.model.Game;
import shared.model.Message;
import shared.model.request.JoinRequest;
import shared.configuration.ConfigurationManager;

public class ServerFacade {

    // -- LOGIN VIEW --

    public static void login(AsyncServerTask.AsyncCaller caller, String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_login_method"),
                paramTypes,
                paramValues,
                null);

        new AsyncServerTask(caller).execute(command);
    }
    public static void register(AsyncServerTask.AsyncCaller caller, String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_register_method"),
                paramTypes,
                paramValues,
                null);

        new AsyncServerTask(caller).execute(command);
    }

    // -- GAME LIST VIEW --

    public static void createGame(AsyncServerTask.AsyncCaller caller, Game game) {
        String[] paramTypes = {Game.class.getCanonicalName()};
        Object[] paramValues = {game};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_create_game_method"),
                paramTypes,
                paramValues,
                null);

        new AsyncServerTask(caller).execute(command);

    }
    public static void joinGame(AsyncServerTask.AsyncCaller caller, JoinRequest joinRequest) {
        String[] paramTypes = {JoinRequest.class.getCanonicalName()};
        Object[] paramValues = {joinRequest};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_join_game_method"),
                paramTypes,
                paramValues,
                null);
        new AsyncServerTask(caller).execute(command);
    }

    // -- GAME LOBBY VIEW --

//    public static void leaveGame(AsyncServerTask.AsyncCaller caller, String userName, String gameID) {
//        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
//        Object[] paramValues = {userName, gameID};
//
//        GenericCommand command = new GenericCommand(
//                ConfigurationManager.getString("server_facade_name"),
//                ConfigurationManager.getString("leaveGame"),
//                paramTypes,
//                paramValues,
//                null);
//        new AsyncServerTask(caller).execute(command);
//    }
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

