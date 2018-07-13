package shared.model.request;

import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.InvalidGameRequestException;

public class JoinRequest {
    private String _userName;
    private String _displayName;
    private PlayerColor _color;
    private String _gameId;

    public JoinRequest(String userName, String displayName, PlayerColor color, String gameId) throws InvalidGameRequestException
    {
        if (userName == null || displayName == null || color == null || gameId == null)
        {
            throw new InvalidGameRequestException();
        }
        _userName = userName;
        _displayName = displayName;
        _color = color;
        _gameId = gameId;
    }

    public String getUserName() {
        return _userName;
    }

    public String getDisplayName() {
        return _displayName;
    }

    public PlayerColor getColor() {
        return _color;
    }

    public String getGameID() {
        return _gameId;
    }

}
