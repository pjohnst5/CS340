package json.provider.dao;

import java.util.List;

import json.provider.file.management.CommandFilesManager;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.plugin.ICommandDao;
import shared.serialization.Serializer;

public class JsonCommandDao implements ICommandDao {

    @Override
    public void storeClientCommand(String gameId, int index, ICommand command) throws DatabaseException {
        String contents = Serializer._serialize(command);
        CommandFilesManager.addClientCommand(gameId, index, contents);
    }

    @Override
    public void storeServerCommand(String gameId, int index, ICommand command) throws DatabaseException {
        String contents = Serializer._serialize(command);
        CommandFilesManager.addServerCommand(gameId, index, contents);
    }

    @Override
    public List<ICommand> getClientCommands(String gameId) throws DatabaseException {
        return CommandFilesManager.getAllClientCommands(gameId);
    }

    @Override
    public List<ICommand> getServerCommands(String gameId) throws DatabaseException {
        return CommandFilesManager.getAllServerCommands(gameId);
    }

}
