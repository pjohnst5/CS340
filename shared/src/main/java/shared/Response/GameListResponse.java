package shared.Response;

import shared.Game;

public class GameListResponse {

    private Game[] _games;
    private boolean _success = false;
    private String _errorMessage;

    public void setGames(Game[] games)
    {
        _games = games;
    }

    public void setSuccess(boolean s)
    {
        _success = s;
    }

    public void setErrorMessage(String s)
    {
        _errorMessage = s;
    }



    public Game[] getCommands()
    {
        return _games;
    }

    public boolean getSuccess()
    {
        return _success;
    }

    public String getErrorMessage()
    {
        return _errorMessage;
    }
}
