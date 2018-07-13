package shared.model.response;

import java.util.ArrayList;
import java.util.List;

import shared.command.GenericCommand;
import shared.command.ICommand;

public class CommandResponse implements ICommandResponse {
    private List<ICommand> _commands;
    private boolean _success = false;
    private String _errorMessage;

    public CommandResponse()
    {
        _commands = new ArrayList<>();
    }

    public void addCommand(ICommand command)
    {
        _commands.add((GenericCommand) command);
    }

    @Override
    public void setSuccess(boolean s)
    {
        _success = s;
    }

    @Override
    public void setErrorMessage(String s)
    {
        _errorMessage = s;
    }


    @Override
    public List<ICommand> getCommands()
    {
        return _commands;
    }

    @Override
    public boolean getSuccess()
    {
        return _success;
    }

    @Override
    public String getErrorMessage()
    {
        return _errorMessage;
    }
}
