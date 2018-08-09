package shared.plugin;

import java.util.List;

import shared.command.ICommand;
import shared.exception.DatabaseException;

public interface ICommandDao {

    void storeClientCommand(String gameID, int index, ICommand command) throws DatabaseException;
    void storeServerCommand(String gameID, int index, ICommand command) throws DatabaseException;

    List<ICommand> getClientCommands(String gameID) throws DatabaseException;
    List<ICommand> getServerCommands(String gameID) throws DatabaseException;
}
