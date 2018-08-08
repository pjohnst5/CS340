package json.provider;

import java.util.List;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.plugin.ICommandDao;

public class JsonCommandDao implements ICommandDao {
    @Override
    public void storeCommand(String gameID, int index, GenericCommand command) throws DatabaseException {

    }

    @Override
    public List<ICommand> getCommands(String gameID) throws DatabaseException {
        return null;
    }
}
