package client.model;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import shared.exception.InvalidGameException;
import shared.exception.MaxPlayersException;
import shared.exception.ReachedZeroPlayersException;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

import static android.content.ContentValues.TAG;

public class ClientModel extends Observable {
    private static final String TAG = "client.ClientModel";
    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {
        _games = new HashMap<>();
    }

    private User _user;
    private Map<String, Game> _games;
    private Game _currentGame;


    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        if (user == null) {
            Log.e(TAG, "Attempt to set user with null User object)");
            return;
        }
        _user = user;
        setChanged();
        notifyObservers();
    }

    public Game getCurrentGame() {
        return _currentGame;
    }

    private void setCurrentGame(Game currentGame) {
        _currentGame = currentGame;
        setChanged();
        notifyObservers();
    }

    public Map<String, Game> getGames() {
        return _games;
    }

    public void setGames(GamesWrapper games) {
        if(games != null){
            if (games.getGames().size() != _games.size()){
                _games = games.getGames();
                setChanged();
                notifyObservers();
            }
        }
    }

    public void addGameToList(Game game) {
        if (game == null) {
            Log.w(TAG, "Attempt to add null Game to _games");
            return;
        }
        String key = game.getGameID();
        if (_games.containsKey(key)) {
            Log.e(TAG, "Call to addGameToList: game is already in thie list!");
            return;
        }
        _games.put(key, game);
        setChanged();
        notifyObservers();
    }

    public void addPlayer(Player player) {
        if (player == null) {
            Log.e(TAG, "Attempt to add null player object");
            return;
        }
        Game game = _games.get(player.getGameID());
        if (game == null) {
            Log.e(TAG, "Can't find game to add new player to");
            return;
        }

        if (player.getUserName().equals(_user.getUserName())) {
            setCurrentGame(game);
            _user.set_playerId(player.getPlayerID());
        }
        try {
            game.addPlayer(player);
        } catch (MaxPlayersException e) {
            Log.w(TAG, e.getMessage());
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    public void removePlayer(Player player) {
        if (player == null) {
            Log.e(TAG, "Attempt to remove with null player reference");
            return;
        }
        Game game = _games.get(player.getGameID());
        if (game == null) {
            Log.e(TAG, "Can't find game to remove player from");
            return;
        }

        try {
            game.removePlayer(player.getPlayerID());
        } catch (ReachedZeroPlayersException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public void startGame(String gameId){
        if (gameId == null) {
            Log.e(TAG, "Call to start game with null gameId");
            return;
        }
        Game g = this._games.get(gameId);
        try {
            g.setStarted(true);
        } catch (InvalidGameException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
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
