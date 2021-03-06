package shared.model;


import java.util.UUID;

import shared.exception.InvalidUserException;

public class User {
    private String _userName;
    private String _password;
    private UUID _uuid;
    private String _playerId;


    public User(String username, String password) throws InvalidUserException
    {
        if (username == null || password == null)
            throw new InvalidUserException();
        _userName = username;
        _password = password;
    }

    public String getUserName()
    {
        return _userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public UUID getUUID()
    {
        return _uuid;
    }

    public void setUUID(UUID uuid) throws InvalidUserException
    {
        if (uuid == null)
        {
            throw new InvalidUserException();
        }
        _uuid = uuid;
    }
    public String get_playerId() {
        return _playerId;
    }

    public void set_playerId(String _playerId) {
        this._playerId = _playerId;
    }
}
