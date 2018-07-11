package shared;


import shared.CustomExceptions.InvalidUserException;

public class User {
    private String _userName;
    private String _password;

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

}
