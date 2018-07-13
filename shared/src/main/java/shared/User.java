package shared;


import java.util.UUID;

import shared.CustomExceptions.InvalidUserException;

public class User {
    private String _userName;
    private String _password;
    private UUID _uuid;

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

}
