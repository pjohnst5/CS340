package client.facade;

import java.util.HashMap;
import java.util.Map;

import client.model.ClientModel;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

public class CommandFacade {
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
        clientModel.sendMessage(message);
    }

    public static void leaveGame(Player player) { clientModel.removePlayer(player); }

    //create game addGame(Game game)
    //join game (adding player to specific game and setting current game if it's you) addPlayer(Player player)
    //start game (bool started checking the current game) start(String GameId)
    //send message sendMessage(Message message)
}
