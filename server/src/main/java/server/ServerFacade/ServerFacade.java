package server.ServerFacade;

import java.util.List;
import java.util.UUID;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomEnumerations.PlayerColor;
import shared.Game;
import shared.GameRequest;
import shared.Response.GameListResponse;
import shared.Response.GenericResponse;
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

    public GenericResponse createGame(GameRequest gameRequest)
    {
        return GameListFacade.createGame(gameRequest);
    }

    public GenericResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return GameListFacade.joinGame(username,gameID,color);
    }

    public GenericResponse leaveGame(String userName, String gameID)
    {
        return GameLobbyFacade.leaveGame(userName, gameID);
    }

    public GenericResponse startGame(String gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public GenericResponse sendMessage(String gameID, String message)
    {
        return GameLobbyFacade.sendMessage(gameID, message);
    }

    public GameListResponse getActiveGames()
    {
        return CommandManagerFacade.getActiveGames();
    }

    public GenericResponse getCommandList(String gameID, int index)
    {
        return CommandManagerFacade.getCommandList(gameID, index);
    }
}
