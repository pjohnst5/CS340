package server.facade;

import shared.model.Game;
import shared.model.request.JoinRequest;
import shared.model.Message;
import shared.model.response.IResponse;

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
    public static IResponse login(String username, String password)  //setUser
    {
        return LoginService.login(username, password);
    }

    public static IResponse register(String username, String password) //setUser
    {
        return LoginService.register(username, password);
    }




    //Game List functions
    public static IResponse createGame(Game game) //addGame(Game)
    {
        return GameListService.createGame(game);
    }

    public static IResponse joinGame(JoinRequest joinRequest) //addPLayer(Player)
    {
        return GameListService.joinGame(joinRequest);
    }




    //Game Lobby functions
    public static IResponse startGame(String gameID, String playerID) //startGame(gameID)
    {
        return GameLobbyService.startGame(gameID, playerID);
    }

    public static IResponse sendMessage(Message message)   //sendMessage(Message)
    {
        return GameLobbyService.sendMessage(message);
    }






    //Was lobby
//    public static IResponse leaveGame(String userName, String gameID)
//    {
//        return GameLobbyService.leaveGame(userName, gameID);
//    }



    // Poller functions
    public static IResponse getCommandList(String gameID, String playerID)
    {
        return CommandManagerService.getCommandList(gameID, playerID);
    }

    public static IResponse getActiveGames()
    {
        return CommandManagerService.getActiveGames();
    }
}
