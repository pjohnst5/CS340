package shared;

import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.PlayerException;

public class Player {
    private String _userName;
    private String _displayName;
    private String _gameID;
    private PlayerColor _color;
    private int _points;


    public Player(String userName, String displayName, PlayerColor color) throws PlayerException
    {
        if (userName == null || userName.isEmpty() || displayName == null || displayName.isEmpty() || color == null)
        {
            throw new PlayerException("Invalid shared.Player parameters");
        }

        _userName = userName;
        _displayName = displayName;
        _gameID = new String();
        _color = color;
        _points = 0;
    }

    public String getUserName()
    {
        return _userName;
    }

    public String getDisplayName()
    {
        return _displayName;
    }

    public String getGameID()
    {
        return _gameID;
    }

    public PlayerColor getColor() {
        return _color;
    }

    public int getPoints()
    {
        return _points;
    }

    public void addPoints(int add)
    {
        _points += add;
    }

    public void setGameID(String id) throws PlayerException
    {
        if (id == null || id.isEmpty())
        {
            throw new PlayerException("Player gameID cannot be null or empty");
        }

        _gameID = id;
    }
}
