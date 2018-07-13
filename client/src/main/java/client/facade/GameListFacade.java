package client.facade;

import client.server.communication.ServerProxy;
import shared.command.GenericCommand;
import shared.exception.ServerProxyException;
import shared.model.Game;
import shared.model.request.JoinRequest;
import shared.configuration.ConfigurationManager;

public class GameListFacade {
    static ServerProxy serverProxy = ServerProxy.instance();

    public static void createGame(Game game) throws ServerProxyException {
        String[] paramTypes = {Game.class.getCanonicalName()};
        Object[] paramValues = {game};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_create_game_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);

    }
    public static void joinGame(JoinRequest joinRequest) throws ServerProxyException {
        String[] paramTypes = {JoinRequest.class.getCanonicalName()};
        Object[] paramValues = {joinRequest};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_join_game_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);

    }
}

