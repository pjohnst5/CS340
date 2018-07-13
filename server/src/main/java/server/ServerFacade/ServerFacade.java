package server.ServerFacade;

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
        return LoginFacade.login(username, password);
    }

    public static IResponse register(String username, String password) //setUser
    {
        return LoginFacade.register(username, password);
    }




    //Game List functions
    public static IResponse createGame(Game game) //addGame(Game)
    {
        return GameListFacade.createGame(game);
    }

    public static IResponse joinGame(JoinRequest joinRequest) //addPLayer(Player)
    {
        return GameListFacade.joinGame(joinRequest);
    }




    //Game Lobby functions
    public static IResponse startGame(String gameID) //startGame(gameID)
    {
        return GameLobbyFacade.startGame(gameID);
    }

    public static IResponse sendMessage(Message message)   //sendMessage(Message)
    {
        return GameLobbyFacade.sendMessage(message);
    }






    //Was lobby
//    public static IResponse leaveGame(String userName, String gameID)
//    {
//        return GameLobbyFacade.leaveGame(userName, gameID);
//    }



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
