package shared;

import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.InvalidGameException;
import shared.CustomExceptions.InvalidGameRequestException;

public class GameRequest {
    private String _userName;
    private String _displayName;
    private PlayerColor _color;
    private int _maxPlayers;

    public GameRequest(String userName, String displayName, PlayerColor color, int maxPlayers) throws InvalidGameRequestException
    {
        if (userName == null || displayName == null || color == null || maxPlayers < 2 || maxPlayers > 5)
        {
            throw new InvalidGameRequestException();
        }
        _userName = userName;
        _displayName = displayName;
        _color = color;
        _maxPlayers = maxPlayers;
    }


}
