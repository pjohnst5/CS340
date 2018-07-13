package client.model;

import android.util.Log;

import java.util.Map;
import java.util.Observable;

import shared.exception.InvalidGameException;
import shared.exception.MaxPlayersException;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

import static android.content.ContentValues.TAG;

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
        if(user != null) {
            this._user = user;
            setChanged();
            notifyObservers();
        }
    }

    public Game getCurrentGame() {
        return _currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        if(currentGame != null){
            this._currentGame = currentGame;
            setChanged();
            notifyObservers();
        }
    }

    public Map<String, Game> getGames() {
        return _games;
    }

    public void setGames(GamesWrapper games) {
        if(games != null){
            this._games = games.getGames();
            setChanged();
            notifyObservers();
        }
    }

    public void addGameToList(Game game){
        if(game != null){
            String key = game.getGameID();
            this._games.put(key, game);
            setChanged();
            notifyObservers();
        }
    }

    public void addPlayer(Player player) {
        if(player != null){
            String gameId = player.getGameID();
            Game g = this._games.get(gameId);
            try {
                g.addPlayer(player);
            } catch (MaxPlayersException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }

            if(player.getPlayerID().equals(this._user.getUserName())){
                setCurrentGame(g);
            }
            setChanged();
            notifyObservers();
        }
    }

    public void startGame(String gameId){
        if(gameId != null) {
            Game g = this._games.get(gameId);
            try {
                g.setStarted(true);
            } catch (InvalidGameException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }
            setChanged();
            notifyObservers();
        }
    }

    public void sendMessage(Message message){
        if(message != null) {
            String gameId = message.getGameID();
            Game game = this._games.get(gameId);
            game.addMessage(message);
            setChanged();
            notifyObservers();
        }
    }
}
