package shared.model;

public class Message {
    private String _message;
    private String _displayName;
    private String _gameID;

    public Message() {
        _message = new String();
        _displayName = new String();
        _gameID = new String();
    }

    public String getMessage()
    {
        return _message;
    }

    public String getDisplayName()
    {
        return _displayName;
    }

    public String getGameID()
    {
        return _gameID;
    }


    public void setMessage(String s)
    {
        _message = s;
    }

    public void setDisplayName(String s)
    {
        _displayName = s;
    }

    public void setGameID(String s)
    {
        _gameID = s;
    }
}
