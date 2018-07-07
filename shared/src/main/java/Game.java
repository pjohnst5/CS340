import java.util.ArrayList;
import java.util.List;

import CustomExceptions.InvalidGameException;
import CustomExceptions.MaxPlayersException;
import CustomExceptions.ReachedZeroPlayersException;

public class Game {
    private String _gameName;
    private int _gameID;
    private List<Player> _players;
    private int _maxPlayers;

    public Game(String gameName, Player player, int maxPlayers) throws InvalidGameException
    {
        if (maxPlayers < 2 || maxPlayers > 5)
        {
            throw new InvalidGameException("Invalid number of players : " + maxPlayers);
        }
        if (gameName.isEmpty() || gameName == null){
            throw new InvalidGameException("Game must have a name");
        }
        if (player == null) {
            throw new InvalidGameException("Game must have at least one player");
        }
        _gameName = gameName;
        _gameID = -1;
        _players = new ArrayList<Player>();
        _players.add(player);
        _maxPlayers = maxPlayers;
    }

    public String getGameName()
    {
        return _gameName;
    }

    public int getGameID()
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


    public void setGameName(String s) throws InvalidGameException
    {
        if (s.isEmpty() || s == null)
        {
            throw new InvalidGameException("Game must have a name");
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

    //returns the number of players after adding the user, otherwise throws an exception
    public int addPlayer(Player p) throws MaxPlayersException
    {
        if (_players.size() >= _maxPlayers)
        {
            throw new MaxPlayersException(_maxPlayers);
        }
        _players.add(p);
        return _players.size();
    }

    //returns the number of users after removing the user
    public int removeUser(String userName) throws ReachedZeroPlayersException
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
}
