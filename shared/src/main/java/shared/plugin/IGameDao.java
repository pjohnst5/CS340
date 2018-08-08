package shared.plugin;

import java.util.List;

import shared.exception.DatabaseException;
import shared.model.Game;

public interface IGameDao {

    void addGame(Game game) throws DatabaseException;

    void updateGame(Game game) throws DatabaseException;

    List<Game> getGames() throws DatabaseException;
}
