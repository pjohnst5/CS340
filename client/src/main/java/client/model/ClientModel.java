package client.model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import shared.exception.InvalidGameException;
import shared.exception.MaxPlayersException;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;
import shared.model.wrapper.GamesWrapper;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;

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
        _currentGame = currentGame;
        setChanged();
        notifyObservers();
    }

    public Map<String, Game> getGames() {
        return _games;
    }

    public void setGames(GamesWrapper games) {
        if(games != null){
            if (games.getGames().size() != this._games.size()){
                this._games = games.getGames();
                setChanged();
                notifyObservers();
            }
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
            if(player.getUserName().equals(this._user.getUserName())){
                setCurrentGame(g);
                _user.set_playerId(player.getPlayerID());
            }
            try {
                g.addPlayer(player);
            } catch (MaxPlayersException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }

            setChanged();
            notifyObservers();
        }
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
            setChanged();
            notifyObservers();
        } catch (InvalidGameException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public void startGame(String gameId){
        if(gameId != null) {
            Game g = this._games.get(gameId);
            try {
                g.start();
                setChanged(); // only need to notify observers if the game actually gets started
                notifyObservers();
            } catch (InvalidGameException e) {
                Log.i(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addMessage(Message message){
        if(message != null) {
            String gameId = message.getGameID();
            Game game = this._games.get(gameId);
            game.addMessage(message);
            setChanged();
            notifyObservers();
        }
    }

    public void addGameAction(GameAction action) {
        _currentGame.addGameAction(action);
    }

    public void updatePlayer(Player player)
    {
        try {
            if (_currentGame.getGameID().equals(player.getGameID()))
            {
                _currentGame.updatePlayer(player);
            }
            _games.get(player.getGameID()).updatePlayer(player);
        } catch(InvalidGameException e)
        {
            System.out.println(e.getMessage());
        }
        setChanged();
        notifyObservers();
    }

    public void setDestDeck(DestDeck deck) {
        _currentGame.setDestDeck(deck);
        _games.get(_currentGame.getGameID()).setDestDeck(deck);


        setChanged();
        notifyObservers();
    }

    public void setTrainDeck(TrainDeck deck){
        _currentGame.setTrainDeck(deck);
        _games.get(_currentGame.getGameID()).setTrainDeck(deck);

        setChanged();
        notifyObservers();
    }

    public void changeTurns()
    {
        try {
            _currentGame.changeTurns();
        } catch(InvalidGameException e) {
            System.out.println(e.getMessage());
        }
    }
}
