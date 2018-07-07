public class User {
    private String _userName;
    private String _password;

    public User(String username, String password)
    {
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
