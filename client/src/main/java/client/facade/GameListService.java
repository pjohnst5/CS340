package client.facade;

import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.model.Game;
import shared.model.request.JoinRequest;
import shared.configuration.ConfigurationManager;

public class GameListService {

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
}

