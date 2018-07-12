package shared;

public class Message {
    private String _message;
    private String _username;
    private String _gameID;

    public Message() {
        _message = new String();
        _username = new String();
        _gameID = new String();
    }

    public String getMessage()
    {
        return _message;
    }

    public String getUsername()
    {
        return _username;
    }

    public String getGameID()
    {
        return _gameID;
    }


    public void setMessage(String s)
    {
        _message = s;
    }

    public void setUsername(String s)
    {
        _username = s;
    }

    public void setGameID(String s)
    {
        _gameID = s;
    }
}
