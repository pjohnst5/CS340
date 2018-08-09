package json.provider.dao;

import java.util.List;

import json.provider.file.management.CommandFilesManager;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.plugin.ICommandDao;
import shared.serialization.Serializer;

public class JsonCommandDao implements ICommandDao {

    @Override
    public void storeCommand(String gameId, int index, ICommand command) throws DatabaseException {

        String contents = Serializer._serialize(command);
        CommandFilesManager.addCommand(gameId, index, contents);

    }

    @Override
    public List<ICommand> getCommands(String gameId) throws DatabaseException {
        return CommandFilesManager.getAllCommands(gameId);
    }

}
