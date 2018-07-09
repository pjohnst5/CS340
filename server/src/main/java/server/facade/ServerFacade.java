package server.facade;

import java.util.UUID;

import shared.Command.ICommand;
import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;

public class ServerFacade {

    public UUID login(String username, String password)
    {
        return LoginFacade.login(username, password);
    }

    public UUID register(String username, String password)
    {
        return LoginFacade.register(username, password);
    }

    public ICommand createGame(GameRequest gameRequest)
    {
        return GameListFacade.createGame(gameRequest);
    }

    public ICommand joinGame(String username, String gameID, PlayerColor color)
    {
        return GameListFacade.joinGame(username,gameID,color);
    }

    public ICommand leaveGame(String userName, String gameID)
    {
        return GameLobbyFacade.leaveGame(userName, gameID);
    }

    public ICommand startGame(String gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public ICommand sendMessage(String gameID, String message)
    {
        return GameLobbyFacade.sendMessage(gameID, message);
    }
}
