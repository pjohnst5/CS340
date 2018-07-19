package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.enumeration.GameState;
import shared.exception.InvalidGameException;
import shared.exception.MaxPlayersException;
import shared.exception.ReachedZeroPlayersException;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;

public class Game {
    private String _gameName;
    private String _gameID;
    private List<Player> _players;
    private int _maxPlayers;
    private GameState _state;
    private List<Message> _messages;
    private List<GameAction> _actions;
    private TrainDeck _trainDeck;
    private DestDeck _destDeck;
    private GameMap _map;

    public Game(String gameName, int maxPlayers) throws InvalidGameException
    {
        if (maxPlayers < 2 || maxPlayers > 5)
        {
            throw new InvalidGameException("Invalid number of players : " + maxPlayers);
        }
        if (gameName.isEmpty() || gameName == null){
            throw new InvalidGameException("shared.model.Game must have a name");
        }

        _gameName = gameName;
        _gameID = new String();
        _players = new ArrayList<>();
        _maxPlayers = maxPlayers;
        _state = GameState.NOT_READY;
        _messages = new ArrayList<>();
        _actions = new ArrayList<>();
        _trainDeck = new TrainDeck();
        _destDeck = new DestDeck();
        _map = new GameMap();
    }

    public String getGameName()
    {
        return _gameName;
    }

    public String getGameID()
    {
        return _gameID;
    }

    public List<Player> getPlayers()
    {
        return _players;
    }

    public Player getPlayer(String playerId) throws InvalidGameException {
        for (Player p : _players) {
            if (p.getPlayerID().equals(playerId)) {
                return p;
            }
        }
        throw new InvalidGameException("Can't find player with that ID in the game");
    }

    public int getMaxPlayers()
    {
        return _maxPlayers;
    }

    public GameState get_state()
    {
        return _state;
    }


    public void setGameName(String s) throws InvalidGameException
    {
        if (s.isEmpty() || s == null)
        {
            throw new InvalidGameException("shared.model.Game must have a name");
        }
        _gameName = s;
    }

    public void setMaxPlayers(int i) throws InvalidGameException
    {
        if (i < 2 || i > 5)
        {
            throw new InvalidGameException("Max players must be between 2 and 5 inclusive");
        }
        if (i < _players.size())
        {
            throw new InvalidGameException(_players.size() + " players are already playing, cannot change max num of players to " + i);
        }
        _maxPlayers = i;
    }

    //returns the number of players after adding the player, otherwise throws an exception
    public int addPlayer(Player p) throws MaxPlayersException
    {
        if (_players.size() >= _maxPlayers)
        {
            throw new MaxPlayersException(_maxPlayers);
        }

        for (int i = 0; i < _players.size(); i++){
            if (_players.get(i).getPlayerID().equals(p.getPlayerID())) {
                return _players.size();
            }
        }

        _players.add(p);

        if (_players.size() == _maxPlayers){
            _state = GameState.READY;
        }
        return _players.size();
    }

    //returns the number of players after removing the player
    public int removePlayer(String playerId) throws InvalidGameException
    {
        for( int i = 0; i < _players.size(); i++)
        {
            if (_players.get(i).getPlayerID().equals(playerId))
            {
                _players.remove(i);
                return _players.size();
            }
        }
        throw new InvalidGameException("Couldn't find a player with that ID in this game");
    }

    public void setGameID(String s) throws InvalidGameException
    {
        if (s == null || s.isEmpty()){
            throw new InvalidGameException("GameID cannot be null");
        }

        _gameID = s;
    }

    public List<Message> get_messages() {
        return _messages;
    }

    public void addMessage(Message message){
        this._messages.add(message);
    }
    public void addGameAction(GameAction action) {
        _actions.add(action);
    }

    public void start() throws InvalidGameException {
        if (_state != GameState.READY) {
            throw new InvalidGameException("Game not ready, can't start");
        }
        _state = GameState.STARTED;
    }

    public void setMap(GameMap map)
    {
        _map = map;
    }

    public void setTrainDeck(TrainDeck deck){
        _trainDeck = deck;
    }

    public void setDestDeck(DestDeck deck) {
        _destDeck = deck;
    }
}
