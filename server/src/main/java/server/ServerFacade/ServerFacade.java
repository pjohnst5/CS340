package server.ServerFacade;

import shared.CustomEnumerations.PlayerColor;
import shared.Game;
import shared.JoinRequest;
import shared.Message;
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
    public static IResponse createGame(Game game)
    {
        return GameListFacade.createGame(game);
    }

    public static IResponse joinGame(JoinRequest joinRequest)
    {
        return GameListFacade.joinGame(joinRequest);
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

    public static IResponse sendMessage(Message message)
    {
        return GameLobbyFacade.sendMessage(message);
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