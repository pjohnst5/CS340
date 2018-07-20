package client.facade;

import client.model.ClientModel;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

public class ClientFacade {
    static ClientModel clientModel = ClientModel.getInstance();

    public static void setUser(User user){
        clientModel.setUser(user);
    }

    public static void setGames(GamesWrapper games){
        clientModel.setGames(games);
    }

    public static void createGame(Game game) {
        clientModel.addGameToList(game);
    }

    public static void joinGame(Player player){
        clientModel.addPlayer(player);
    }

    public static void startGame(String gameId){
        clientModel.startGame(gameId);
    }

    public static void sendMessage(Message message){
        clientModel.addMessage(message);
    }

    public static void leaveGame(Player player) { clientModel.removePlayer(player); }

    public static void setMap(){}

    public static void setTrainDeck(){}

    public static void setDestDeck(){}

    public static void addGameAction(){}
}
