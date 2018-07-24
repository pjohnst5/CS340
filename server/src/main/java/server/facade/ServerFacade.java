package server.facade;

import shared.model.Game;
import shared.model.Player;
import shared.model.request.ClaimRouteRequest;
import shared.model.request.DestCardRequest;
import shared.model.request.JoinRequest;
import shared.model.Message;
import shared.model.request.LeaveGameRequest;
import shared.model.request.MessageRequest;
import shared.model.request.TrainCardRequest;
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
    public static IResponse createGame(Game game) //setGames(List<Game>) & joinGame(Player)
    {
        return GameListService.createGame(game);
    }

    public static IResponse joinGame(JoinRequest joinRequest) //joinGame(Player)
    {
        return GameListService.joinGame(joinRequest);
    }




    //Game Lobby functions
    public static IResponse startGame(String gameID, String playerID) //startGame(gameID)
    {
        return GameLobbyService.startGame(gameID, playerID);
    }

    public static IResponse sendMessage(MessageRequest request)   //sendMessage(Message)
    {
        return GameLobbyService.sendMessage(request);
    }

        public static IResponse leaveGame(LeaveGameRequest request)  //leaveGame(Player)
    {
        return GameLobbyService.leaveGame(request);
    }



    //Game Map functions
    public static IResponse claimRoute(ClaimRouteRequest request)
    {
        return GameMapService.claimRoute(request);
    }



    //Train Card functions
    public static IResponse updateTrainDeck(TrainCardRequest request)
    {
        return TrainCardService.updateTrainDeck(request);
    }



    //Dest Card functions
    public static IResponse updateDestDeck(DestCardRequest request)
    {
        return DestCardService.updateDestDeck(request);
    }

    public static IResponse getThreeDestCards(Player player){
        return DestCardService.getThreeDestCards(player);
    }





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
