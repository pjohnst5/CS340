package client;

import java.util.Map;
import java.util.Observable;

import shared.Game;
import shared.Message;
import shared.Player;
import shared.User;

public class ClientModel extends Observable {
    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {}

    private User _user;
    private Map<String, Game> _games;
    private Game _currentGame;


    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        this._user = user;
        notifyObservers();
    }

    public Game getCurrentGame() {
        return _currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this._currentGame = currentGame;
        notifyObservers();
    }

    public Map<String, Game> getGames() {
        return _games;
    }

    public void setGames(Map<String, Game> games) {
        this._games = games;
        notifyObservers();
    }

    public void addGameToList(Game game){
        String key = game.getGameID();
        this._games.put(key, game);
    }

    public void addPlayer(Player player){}

    public void startGame(String gameId){}

    public void sendMessage(Message message){}

}
