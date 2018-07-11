package server.ServerFacade;

import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;
import shared.Response.GameListResponse;
import shared.Response.CommandResponse;
import shared.Response.LoginResponse;

public class ServerFacade {

    private static ServerFacade _instance;

    private ServerFacade() {

    }

    public static ServerFacade getInstance()
    {
        if (_instance == null)
        {
            _instance = new ServerFacade();
        }
        return _instance;
    }

    public LoginResponse login(String username, String password)
    {
        return null;
    }

    public LoginResponse register(String username, String password)
    {
        return null;
    }

    public CommandResponse createGame(GameRequest gameRequest)
    {
        return GameListFacade.createGame(gameRequest);
    }

    public CommandResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return GameListFacade.joinGame(username,gameID,color);
    }

    public CommandResponse leaveGame(String userName, String gameID)
    {
        return GameLobbyFacade.leaveGame(userName, gameID);
    }

    public CommandResponse startGame(String gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public CommandResponse sendMessage(String gameID, String message)
    {
        return GameLobbyFacade.sendMessage(gameID, message);
    }

    public CommandResponse getCommandList(String gameID, int index)
    {
        return CommandManagerFacade.getCommandList(gameID, index);
    }

    public GameListResponse getActiveGames()
    {
        return CommandManagerFacade.getActiveGames();
    }
}
