package server.ServerFacade;

import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;
import shared.Response.IResponse;

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



    //Login functions
    public static IResponse login(String username, String password)
    {
        return LoginFacade.login(username, password);
    }

    public static IResponse register(String username, String password)
    {
        return LoginFacade.register(username, password);
    }




    //Game List functions
    public static IResponse createGame(GameRequest gameRequest)
    {
        return GameListFacade.createGame(gameRequest);
    }

    public static IResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return GameListFacade.joinGame(username,gameID,color);
    }




    //Game Lobby functions
    public static IResponse leaveGame(String userName, String gameID)
    {
        return GameLobbyFacade.leaveGame(userName, gameID);
    }

    public static IResponse startGame(String gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public static IResponse sendMessage(String gameID, String message)
    {
        return GameLobbyFacade.sendMessage(gameID, message);
    }




    //Poller functions
    public static IResponse getCommandList(String gameID, int index)
    {
        return CommandManagerFacade.getCommandList(gameID, index);
    }

    public static IResponse getActiveGames()
    {
        return CommandManagerFacade.getActiveGames();
    }
}
