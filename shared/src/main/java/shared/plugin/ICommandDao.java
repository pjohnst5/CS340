package shared.plugin;

import java.util.List;

import shared.command.GenericCommand;
import shared.exception.DatabaseException;

public interface ICommandDao {

    void storeCommand(String gameID, int index, GenericCommand command) throws DatabaseException;

    List<GenericCommand> getCommands(String gameID) throws DatabaseException;
}
