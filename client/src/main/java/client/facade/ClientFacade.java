package client.facade;

import client.model.ClientModel;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.GameMap;
import shared.model.Route;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;
import shared.model.wrapper.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;
import shared.model.wrapper.ThreeDestCardWrapper;

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

    public static void claimRoute(Route route) {
        clientModel.claimRoute(route);
    }

    public static void setTrainDeck(TrainDeck deck){
        clientModel.setTrainDeck(deck);
    }

    public static void setDestDeck(DestDeck deck){ clientModel.setDestDeck(deck); }

    public static void addGameAction(GameAction action){ clientModel.addGameAction(action); }

    public static void updatePlayer(Player player) {
        clientModel.updatePlayer(player);
    }

    public static void changeTurns()
    {
        clientModel.changeTurns();
    }

    public static void setDestOptionCards(ThreeDestCardWrapper wrapper, String playerID) {
        clientModel.setDestOptionCards(wrapper.getDestCards(), playerID);
    }

    public static void playerCompletedSetup(){
        clientModel.playerCompletedSetup();
    }
}
