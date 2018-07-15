package client.facade;

import android.util.Log;

import client.model.ClientModel;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

public class CommandFacade {
    private static ClientModel _model = ClientModel.getInstance();

    public static void setUser(User user){
        _model.setUser(user);
    }

    public static void setGames(GamesWrapper games){
        _model.setGames(games);
    }

    public static void createGame(Game game) {
        _model.addGameToList(game);
    }

    public static void joinGame(Player player){
        _model.addPlayer(player);
    }

    public static void leaveGame(Player player) {
        _model.removePlayer(player);
    }

    public static void startGame(String gameId){
        _model.startGame(gameId);
    }

    public static void sendMessage(Message message){
        _model.sendMessage(message);
    }
}
