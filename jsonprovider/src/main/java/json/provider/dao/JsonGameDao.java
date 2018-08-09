package json.provider.dao;

import java.util.List;

import json.provider.file.management.FileManager;
import json.provider.file.management.GameFilesManager;
import shared.exception.DatabaseException;
import shared.model.Game;
import shared.plugin.IGameDao;
import shared.serialization.Serializer;

public class JsonGameDao implements IGameDao {
    @Override
    public void addGame(Game game) throws DatabaseException {

        String contents = Serializer._serialize(game);
        int cmdIndex = game.getCommandCountSinceSnapshot();

        GameFilesManager.addGameDir(game.getGameID());
        GameFilesManager.addGameFile(game.getGameID(), contents);
        GameFilesManager.addCmdIndex(game.getGameID(), cmdIndex);
    }

    @Override
    public void updateGame(Game game) throws DatabaseException {

        String contents = Serializer._serialize(game);
        int cmdIndex = game.getCommandCountSinceSnapshot();

        GameFilesManager.addGameFile(game.getGameID(), contents, FileManager.OVERWRITE);
        GameFilesManager.addCmdIndex(game.getGameID(), cmdIndex, FileManager.OVERWRITE);

    }

    @Override
    public List<Game> getGames() throws DatabaseException {
        return GameFilesManager.getAllGames();
    }

    @Override
    public int getIndexOfCompletedCommands(String gameId) throws DatabaseException {
        return GameFilesManager.getCmdIndex(gameId);
    }
}
