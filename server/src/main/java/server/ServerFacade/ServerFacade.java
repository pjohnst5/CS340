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
    public IResponse login(String username, String password)
    {
        return null;
    }

    public IResponse register(String username, String password)
    {
        return null;
    }




    //Game List functions
    public IResponse createGame(GameRequest gameRequest)
    {
        return GameListFacade.createGame(gameRequest);
    }

    public IResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return GameListFacade.joinGame(username,gameID,color);
    }




    //Game Lobby functions
    public IResponse leaveGame(String userName, String gameID)
    {
        return GameLobbyFacade.leaveGame(userName, gameID);
    }

    public IResponse startGame(String gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public IResponse sendMessage(String gameID, String message)
    {
        return GameLobbyFacade.sendMessage(gameID, message);
    }




    //Poller functions
    public IResponse getCommandList(String gameID, int index)
    {
        return CommandManagerFacade.getCommandList(gameID, index);
    }

    public IResponse getActiveGames()
    {
        return CommandManagerFacade.getActiveGames();
    }
}
