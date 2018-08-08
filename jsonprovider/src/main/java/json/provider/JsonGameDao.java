package json.provider;

import java.util.List;

import shared.exception.DatabaseException;
import shared.model.Game;
import shared.plugin.IGameDao;

public class JsonGameDao implements IGameDao {
    @Override
    public void addGame(Game game) throws DatabaseException {

    }

    @Override
    public void updateGame(Game game) throws DatabaseException {

    }

    @Override
    public List<Game> getGames() throws DatabaseException {
        return null;
    }

    @Override
    public int getIndexOfCompletedCommands(String gameId) throws DatabaseException {
        return 0;
    }
}
