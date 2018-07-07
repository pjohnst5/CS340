import java.util.ArrayList;
import java.util.List;

public class User {
    private String _userName;
    private String _password;
    private List<Player> _players;

    public User(String username, String password)
    {
        _userName = username;
        _password = password;
        _players = new ArrayList<Player>();
    }

    public String getUserName()
    {
        return _userName;
    }

    public String getPassword()
    {
        return _password;
    }

    public List<Player> getPlayers()
    {
        return _players;
    }

    public void addPlayer(Player p)
    {
        _players.add(p);
    }
}
