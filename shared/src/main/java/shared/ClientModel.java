package shared;

import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {
    private static final ClientModel ourInstance = new ClientModel();

    public static ClientModel getInstance() {
        return ourInstance;
    }

    private ClientModel() {}

    private User _user = new User();
    private List<Game> _games;
    private Game _currentGame = new Game();


    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        this._user = user;
    }

    public Game getCurrentGame() {
        return _currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this._currentGame = currentGame;
    }

    public List<Game> getGames() {
        return _games;
    }

    public void setGames(List<Game> games) {
        this._games = games;
    }

    public void addGameToList(Game game){
        this._games.add(game);
    }

}
