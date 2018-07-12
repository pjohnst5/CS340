package client.ClientFacade;

import client.server.communication.ServerProxy;
import shared.Command.GenericCommand;
import shared.CustomExceptions.ServerProxyException;
import shared.Game;
import shared.JoinRequest;
import shared.configuration.ConfigurationManager;

public class GameListFacade {
    static ServerProxy serverProxy = ServerProxy.instance();

    public static void createGame(Game game) throws ServerProxyException {
        String[] paramTypes = {Game.class.getCanonicalName()};
        Object[] paramValues = {game};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("createGame"),
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
                ConfigurationManager.getString("joinGame"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);

    }
}

