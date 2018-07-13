package shared.model;

import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.PlayerException;

public class Player {
    private String _userName;
    private String _playerID;
    private String _displayName;
    private String _gameID;
    private PlayerColor _color;
    private int _points;


    public Player(String userName, String displayName, PlayerColor color, String gameId, String playerID) throws PlayerException
    {
        if (userName == null ||
                userName.isEmpty() ||
                displayName == null ||
                displayName.isEmpty() ||
                color == null ||
                gameId == null ||
                gameId.isEmpty() ||
                playerID == null ||
                playerID.isEmpty())
        {
            throw new PlayerException("Invalid shared.model.Player parameters");
        }

        _userName = userName;
        _displayName = displayName;
        _gameID = new String();
        _playerID = new String();
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

    public String getPlayerID()
    {
        return _playerID;
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

    public void setPlayerID(String id) throws PlayerException
    {
        if (id == null || id.isEmpty()){
            throw new PlayerException("Player ID cannot be null or empty");
        }
    }
}
