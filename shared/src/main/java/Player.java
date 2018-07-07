import CustomEnumerations.PlayerColor;
import CustomExceptions.InvalidPlayerException;

public class Player {
    private String _userName;
    private String _displayName;
    private int _gameID;
    private PlayerColor _color;
    private int _points;

    public Player(String userName, String displayName, PlayerColor color) throws InvalidPlayerException
    {
        if (userName == null || userName.isEmpty() || displayName == null || displayName.isEmpty() || color == null)
        {
            throw new InvalidPlayerException("Invalid Player parameters");
        }

        _userName = userName;
        _displayName = displayName;
        _gameID = -1;
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

    public int getGameID()
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

    public void setGameID(int id)
    {
        _gameID = id;
    }
}
