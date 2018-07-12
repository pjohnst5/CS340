package shared;

import java.util.ArrayList;
import java.util.List;

import shared.CustomExceptions.InvalidGameException;
import shared.CustomExceptions.MaxPlayersException;
import shared.CustomExceptions.ReachedZeroPlayersException;

public class Game {
    private String _gameName;
    private String _gameID;
    private List<Player> _players;
    private int _maxPlayers;
    private boolean _started;

    public Game(String gameName, int maxPlayers) throws InvalidGameException
    {
        if (maxPlayers < 2 || maxPlayers > 5)
        {
            throw new InvalidGameException("Invalid number of players : " + maxPlayers);
        }
        if (gameName.isEmpty() || gameName == null){
            throw new InvalidGameException("shared.Game must have a name");
        }

        _gameName = gameName;
        _gameID = new String();
        _players = new ArrayList<>();
        _maxPlayers = maxPlayers;
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


    public void setGameName(String s) throws InvalidGameException
    {
        if (s.isEmpty() || s == null)
        {
            throw new InvalidGameException("shared.Game must have a name");
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
        _players.add(p);
        return _players.size();
    }

    //returns the number of players after removing the player
    public int removePlayer(String userName) throws ReachedZeroPlayersException
    {
        for( int i = 0; i < _players.size(); i++)
        {
            if (_players.get(i).getUserName().equals(userName))
            {
                _players.remove(i);
                return _players.size();
            }
        }
        return _players.size();
    }

    public void setGameID(String s) throws InvalidGameException
    {
        if (s == null || s.isEmpty()){
            throw new InvalidGameException("GameID cannot be null");
        }

        _gameID = s;
    }
}
