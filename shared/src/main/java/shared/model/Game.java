package shared.model;

import java.util.ArrayList;
import java.util.List;

import shared.exception.InvalidGameException;
import shared.exception.InvalidGameRequestException;
import shared.exception.MaxPlayersException;
import shared.exception.ReachedZeroPlayersException;

public class Game {
    private String _gameName;
    private String _gameID;
    private List<Player> _players;
    private int _maxPlayers;
    private boolean _started;
    private boolean _readyToStart;
    private List<Message> _messages;

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
        _started = false;
        _readyToStart = false;
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

    public int getMaxPlayers()
    {
        return _maxPlayers;
    }

    public boolean getStarted()
    {
        return _started;
    }

    public boolean getReady()
    {
        return _readyToStart;
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
        setIfReady();
        return _players.size();
    }

    //returns the number of players after removing the player
    public int removePlayer(String playerID) throws ReachedZeroPlayersException// FIXME: GameAlreadyStartedException
    {
        if (_started) {
            // FIXME: throw new GameAlreadyStartedException("Can't remove player; game has already started");
        }
        // FIXME: check for _players.size() == 0

        for( int i = 0; i < _players.size(); i++)
        {
            if (_players.get(i).getPlayerID().equals(playerID))
            {
                _players.remove(i);
                setIfReady();
                return _players.size();
            }
        }
        // if method hasn't returned yet, player wasn't found
        // FIXME: throw new PlayerNotFoundException or whatever
        return -1;
    }

    public void setGameID(String s) throws InvalidGameException
    {
        if (s == null || s.isEmpty()){
            throw new InvalidGameException("GameID cannot be null");
        }

        _gameID = s;
    }

    public void start() throws InvalidGameException {
        setIfReady();
        if (!_readyToStart) {
            throw new InvalidGameException("Still waiting for players to join");
        }
        _started = true;
    }

    public void setStarted(boolean b) throws InvalidGameException // FIXME: use start() instead
    {

        if (_started && b) {
            throw new InvalidGameException("game has already started, can't end it again");
        }

        if (!_started && !b) {
            throw new InvalidGameException("Game has already ended, can't end it again");
        }

        _started = b;
    }

    private void setIfReady() {
        if (_players.size() < _maxPlayers) {
            _readyToStart = false;
        } else {
            _readyToStart = true;
        }
    }

    public List<Message> get_messages() {
        return _messages;
    }

    public void set_messages(List<Message> _messages) {
        this._messages = _messages;
    }
    public void addMessage(Message message){
        this._messages.add(message);
    }
}
