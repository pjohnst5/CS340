package shared.Response;

import java.util.ArrayList;
import java.util.List;

import shared.Command.GenericCommand;
import shared.Command.ICommand;

public class CommandResponse implements IResponse {
    private List<GenericCommand> _commands;
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


    public List<GenericCommand> getCommands()
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
