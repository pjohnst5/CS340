import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String _gameName;
    private int _gameID;
    private List<User> _users;
    private int _maxUsers;

    public Game(String gameName, User user, int maxPlayers) throws InvalidGameException
    {
        if (maxPlayers < 2 || maxPlayers > 5)
        {
            throw new InvalidGameException("Invalid number of players : " + maxPlayers);
        }
        if (gameName.isEmpty() || gameName == null){
            throw new InvalidGameException("Game must have a name");
        }
        if (user == null) {
            throw new InvalidGameException("Game must have at least one user");
        }
        _gameName = gameName;
        _gameID = -1;
        _users = new ArrayList<User>();
        _users.add(user);
        _maxUsers = maxPlayers;
    }

    public String getGameName()
    {
        return _gameName;
    }

    public int getGameID()
    {
        return _gameID;
    }

    public List<User> getUsers()
    {
        return _users;
    }

    public int getMaxPlayers()
    {
        return _maxUsers;
    }


    public void setGameName(String s) throws InvalidGameException
    {
        if (s.isEmpty() || s == null)
        {
            throw new InvalidGameException("Game must have a name");
        }
        _gameName = s;
    }

    public void set_maxUsers(int i) throws InvalidGameException
    {
        if (i < 2 || i > 5)
        {
            throw new InvalidGameException("Max players must be between 2 and 5 inclusive");
        }
        if (i < _users.size())
        {
            throw new InvalidGameException(_users.size() + " players are already playing, cannot change max num of players to " + i);
        }

        _maxUsers = i;
    }

    //returns the number of players after adding the user, otherwise throws an exception
    public int addUser(User u) throws MaxPlayersException
    {
        if (_users.size() >= _maxUsers)
        {
            throw new MaxPlayersException(_maxUsers);
        }
        _users.add(u);
        return _users.size();
    }

    //returns the number of users after removing the user
    public int removeUser(String userName)
    {
        for( int i = 0; i < _users.size(); i++)
        {
            if (_users.get(i).getUserName().equals(userName))
            {
                _users.remove(i);
                return _users.size();
            }
        }
        return _users.size();
    }
}
