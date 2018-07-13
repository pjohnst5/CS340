package client.ClientFacade;

import client.ClientModel;
import shared.Game;
import shared.Message;
import shared.Player;
import shared.User;

public class CommandFacade {
    static ClientModel clientModel = ClientModel.getInstance();

    public static void setUser(User user){
        clientModel.setUser(user);
    }

    public static void addGame(Game game) {
        clientModel.addGameToList(game);
    }

    public static void addPlayer(Player player){
        clientModel.addPlayer(player);
    }

    public static void startGame(String gameId){
        clientModel.startGame(gameId);
    }

    public static void sendMessage(Message message){
        clientModel.sendMessage(message);
    }

    //create game addGame(Game game)
    //join game (adding player to specific and setting current game if it's you) addPlayer(Player player)
    //start game (bool started checking the current game) start(String GameId)
    //send message sendMessage(Message message)
}
